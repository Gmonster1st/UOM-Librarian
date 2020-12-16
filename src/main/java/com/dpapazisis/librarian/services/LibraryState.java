/*
 * Copyright (c) 2020.
 * Dimitrios Papazisis
 * This Software is to be used for educational purposes only.
 * All rights Reserved.
 */

package com.dpapazisis.librarian.services;

import com.dpapazisis.librarian.model.person.Author;
import com.dpapazisis.librarian.model.publisher.Publisher;
import com.dpapazisis.librarian.model.readable.Readable;

import java.io.Serializable;
import java.util.Set;

//TODO: Documentation
public class LibraryState implements Serializable {
    private final Set<Readable> library;
    private final Set<Author> authors;
    private final Set<Publisher> publishers;

    public LibraryState(Set<Readable> library, Set<Author> authors, Set<Publisher> publishers) {
        this.library = library;
        this.authors = authors;
        this.publishers = publishers;
    }

    public Set<Readable> getLibrary() {
        return library;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public Set<Publisher> getPublishers() {
        return publishers;
    }
}
