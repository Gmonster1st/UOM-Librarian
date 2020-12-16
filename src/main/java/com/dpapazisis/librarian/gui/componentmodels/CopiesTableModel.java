/*
 * Copyright (c) 2020.
 * Dimitrios Papazisis
 * This Software is to be used for educational purposes only.
 * All rights Reserved.
 */

package com.dpapazisis.librarian.gui.componentmodels;

import com.dpapazisis.librarian.model.readable.Readable;
import com.dpapazisis.librarian.services.LibraryAction;
import com.dpapazisis.librarian.services.LibraryObserver;
import com.dpapazisis.librarian.services.LibraryService;

import javax.swing.table.AbstractTableModel;
import java.util.List;

import static java.util.stream.Collectors.groupingBy;

public class CopiesTableModel extends AbstractTableModel implements LibraryObserver {
    private final LibraryService libraryService = LibraryService.getInstance();
    private final Readable readable;
    private List<Readable> copies;

    public CopiesTableModel(Readable readable) {
        super();
        libraryService.addObserver(this);
        this.readable = readable;
        getCopies();
    }

    private void getCopies() {
        copies = libraryService.getLibrary()
                .stream()
                .collect(groupingBy(Readable::getTitle))
                .get(readable.getTitle());
    }

    @Override
    public int getRowCount() {
        return copies.size();
    }

    @Override
    public int getColumnCount() {
        return Readable.class.getDeclaredFields().length;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Title";
            case 1:
                return "Year";
            case 2:
                return "Pages";
            case 3:
                return "Subject";
            case 4:
                return "Reference Code";
            case 5:
                return "Lend Status";
            case 6:
                return "Copy Id";
            default:
                return null;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        var selectedReadable = copies.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return selectedReadable.getTitle();
            case 1:
                return selectedReadable.getYear();
            case 2:
                return selectedReadable.getPages();
            case 3:
                return selectedReadable.getSubject().getName();
            case 4:
                return selectedReadable.getReferenceCode().getCode();
            case 5:
                return selectedReadable.isLend();
            case 6:
                return selectedReadable.getCopyId();
            default:
                return null;
        }
    }

    public Readable getReadableAt(int row) {
        return copies.get(row);
    }

    @Override
    public void update(LibraryAction action) {
        getCopies();
        fireTableDataChanged();
    }
}
