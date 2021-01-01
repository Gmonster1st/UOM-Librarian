/*
 * Copyright (c) 2020.
 * Dimitrios Papazisis
 * This Software is to be used for educational purposes only.
 * All rights Reserved.
 */

package com.dpapazisis.librarian.gui.componentmodels;

import com.dpapazisis.librarian.model.person.Author;
import com.dpapazisis.librarian.model.readable.Book;
import com.dpapazisis.librarian.services.LibraryAction;
import com.dpapazisis.librarian.services.LibraryObserver;
import com.dpapazisis.librarian.services.LibraryService;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorListModel extends AbstractListModel<Author> implements LibraryObserver {
    private final transient LibraryService libraryService = LibraryService.getInstance();
    private Book book;
    private List<Author> authors;
    private boolean edit = false;

    public AuthorListModel() {
        libraryService.addObserver(this);
        getData();
    }

    public AuthorListModel(Book book) {
        this.book = book;
        getItemData();
        edit = true;
        libraryService.addObserver(this);
    }

    private void getItemData() {
        this.authors = new ArrayList<>(book.getAuthors());
    }

    private void getData() {
        authors = new ArrayList<>(libraryService.getAuthors());
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

    public void setAuthorsInView(List<Author> authors) {
        this.authors = authors;
        update();
    }

    public void addAuthorToView(Author author) {
        if (authors.size() >= 5) {
            return;
        }
        for (var item : authors) {
            if (item.equals(author)) {
                return;
            }
        }
        authors.add(author);
        update();
    }

    public void removeAuthorFromView(int index) {
        if (index >= 0) {
            authors.remove(index);
            update();
        }
    }

    @Override
    public void update(LibraryAction action) {
        if (!edit) {
            getData();
        } else {
            getItemData();
        }
        update();
    }

    private void update() {
        fireContentsChanged(this, 0, getSize());
    }
}
