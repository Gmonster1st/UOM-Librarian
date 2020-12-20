/*
 * Copyright (c) 2020.
 * Dimitrios Papazisis
 * This Software is to be used for educational purposes only.
 * All rights Reserved.
 */

package com.dpapazisis.librarian.gui.componentmodels;

import com.dpapazisis.librarian.model.person.Author;
import com.dpapazisis.librarian.services.LibraryAction;
import com.dpapazisis.librarian.services.LibraryObserver;
import com.dpapazisis.librarian.services.LibraryService;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorComboBoxModel extends AbstractListModel<Author> implements ComboBoxModel<Author>, LibraryObserver {
    private final transient LibraryService libraryService = LibraryService.getInstance();
    private List<Author> authors;
    private Author selection;

    public AuthorComboBoxModel() {
        libraryService.addObserver(this);
        getData();
    }

    private void getData() {
        authors = new ArrayList<>(libraryService.getAuthors());
    }

    @Override
    public void setSelectedItem(Object anItem) {
        selection = (Author) anItem;
    }

    @Override
    public Object getSelectedItem() {
        return selection;
    }

    @Override
    public int getSize() {
        return authors.size();
    }

    @Override
    public Author getElementAt(int index) {
        return authors.get(index);
    }

    @Override
    public void update(LibraryAction action) {
        getData();
        fireContentsChanged(this, 0, getSize());
    }
}
