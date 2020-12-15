/*
 * Copyright (c) 2020.
 * Dimitrios Papazisis
 * This Software is to be used for educational purposes only.
 * All rights Reserved.
 */

package com.dpapazisis.librarian.gui.tablemodels;

import com.dpapazisis.librarian.model.readable.Readable;
import com.dpapazisis.librarian.repository.Repository;

import javax.swing.table.AbstractTableModel;
import java.util.List;

import static java.util.stream.Collectors.groupingBy;

public class CopiesTableModel extends AbstractTableModel {
    private final Repository repository = Repository.getInstance();
    private final Readable readable;
    private final List<Readable> copies;

    public CopiesTableModel(Readable readable) {
        super();
        this.readable = readable;
        copies = repository.getLibrary()
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
}
