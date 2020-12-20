/*
 * Copyright (c) 2020.
 * Dimitrios Papazisis
 * This Software is to be used for educational purposes only.
 * All rights Reserved.
 */

package com.dpapazisis.librarian.gui.componentmodels;

import com.dpapazisis.librarian.model.person.Author;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorListModel extends AbstractListModel<Author> {
    private List<Author> authors;

    public AuthorListModel() {
        authors = new ArrayList<>();
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
}
