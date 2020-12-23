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

public class AuthorListModel extends AbstractListModel<Author> implements LibraryObserver {
    private final transient LibraryService libraryService = LibraryService.getInstance();
    private List<Author> authors;

    public AuthorListModel() {
        libraryService.addObserver(this);
        getData();
    }

    private void getData() {
        authors = new ArrayList<>(libraryService.getAuthors());
    }

    public AuthorListModel(List<Author> authors) {
        this.authors = authors;
    }

    @Override
    public int getSize() {
        return authors.size();
    }

    @Override
    public Author getElementAt(int index) {
        return authors.get(index);
    }

    public List<Author> getAuthors() {
        return authors;
    }

    @Override
    public void update(LibraryAction action) {
        getData();
        fireContentsChanged(this, 0, getSize());
    }
}
