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
import java.util.Set;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class ReadableTableModel extends AbstractTableModel implements LibraryObserver {
    private final transient LibraryService libraryService = LibraryService.getInstance();
    private Set<Readable> library;
    private List<Readable> libraryView;

    public ReadableTableModel() {
        libraryService.addObserver(this);
        getData();
    }

    @Override
    public int getRowCount() {
        return libraryView.size();
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
                return "Copies Available";
            default:
                return null;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        var readable = libraryView.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return readable.getTitle();
            case 1:
                return readable.getYear();
            case 2:
                return readable.getPages();
            case 3:
                return readable.getSubject().getName();
            case 4:
                return readable.getReferenceCode().getCode();
            case 5:
                return readable.isLend();
            case 6:
                return library
                        .stream()
                        .collect(groupingBy(Readable::isCopy, counting()))
                        .get(readable.isCopy());
            default:
                return null;
        }
    }

    public Readable getReadableAt(int row) {
        return libraryView.get(row);
    }

    public long getCopiesCount(int row) {
        return (long) getValueAt(row, 6);
    }

    private void getData() {
        this.library = libraryService.getLibrary();
        this.libraryView = libraryService.getLibraryGroupedByCopy();
    }

    @Override
    public void update(LibraryAction action) {
        getData();
        fireTableDataChanged();
    }
}

