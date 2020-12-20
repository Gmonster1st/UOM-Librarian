/*
 * Copyright (c) 2020.
 * Dimitrios Papazisis
 * This Software is to be used for educational purposes only.
 * All rights Reserved.
 */

package com.dpapazisis.librarian.services;

import com.dpapazisis.librarian.model.person.Author;
import com.dpapazisis.librarian.model.person.Professor;
import com.dpapazisis.librarian.model.publisher.Publisher;
import com.dpapazisis.librarian.model.readable.Readable;

import java.io.Serializable;
import java.util.Set;

/**
 * A Class made for collecting the data from all the data structures and saving it as one object.
 * Makes it easier to pass the values in the file
 */
public class LibraryState implements Serializable {
    private final Set<Readable> library;
    private final Set<Author> authors;
    private final Set<Publisher> publishers;
    private final Set<Professor> professors;

    public LibraryState(Set<Readable> library, Set<Author> authors, Set<Publisher> publishers, Set<Professor> professors) {
        this.library = library;
        this.authors = authors;
        this.publishers = publishers;
        this.professors = professors;
    }

    /**
     * Gets the Readable Set
     *
     * @return {@link Set}
     */
    public Set<Readable> getLibrary() {
        return library;
    }

    /**
     * Gets the Author Set
     *
     * @return {@link Set}
     */
    public Set<Author> getAuthors() {
        return authors;
    }

    /**
     * Gets the Publisher Set
     *
     * @return {@link Set}
     */
    public Set<Publisher> getPublishers() {
        return publishers;
    }

    /**
     * Gets the Professor Set
     *
     * @return {@link Set}
     */
    public Set<Professor> getProfessors() {
        return professors;
    }
}
