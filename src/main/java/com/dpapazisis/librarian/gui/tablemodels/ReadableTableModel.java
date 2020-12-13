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
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

//TODO: Probably needs to be more generic so it can be used to display all sets of Readable objects
public class ReadableTableModel extends AbstractTableModel {
    private final Repository repository = Repository.getInstance();
    private Set<Readable> library;
    private List<Readable> libraryView;

    public ReadableTableModel() {
        this.library = repository.getLibrary();
        this.libraryView = library
                .stream()
                .collect(groupingBy(Readable::getTitle))
                .values()
                .stream()
                .map(l -> l.get(0))
                .collect(Collectors.toList());
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
                        .collect(groupingBy(Readable::getTitle, counting()))
                        .get(readable.getTitle());
            default:
                return null;
        }
    }

    public Readable getReadableAt(int row) {
        return libraryView.get(row);
    }

    public void addReadable(Readable readable) {
        if (repository.addReadable(readable)) {
            fireTableDataChanged();
        }
    }

    public void removeReadable(Readable readable) {
        if (repository.removeReadable(readable)) {
            fireTableDataChanged();
        }
    }
}

