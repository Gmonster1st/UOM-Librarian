/*
 * Copyright (c) 2020.
 * Dimitrios Papazisis
 * This Software is to be used for educational purposes only.
 * All rights Reserved.
 */

package com.dpapazisis.librarian.services;

import com.dpapazisis.librarian.categories.Subject;
import com.dpapazisis.librarian.model.person.Author;
import com.dpapazisis.librarian.model.publisher.Publisher;
import com.dpapazisis.librarian.model.readable.Readable;
import com.dpapazisis.librarian.utils.FileHelper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;
import java.util.Set;

class Repository implements Serializable {
    private Properties properties;
    private Set<Subject> subjects;
    private Set<Readable> library;
    private Set<Author> authors;
    private Set<Publisher> publishers;

    protected Repository() {
        loadProperties();
        loadSubjects();
        loadLibrary();
    }

//TODO: Implement this and check what else need to ne done by this class

    private void loadLibrary() {
        LibraryState libraryState = null;
        try {
            libraryState = FileHelper.loadFromDisk();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (libraryState != null) {
            publishers = libraryState.getPublishers();
            authors = libraryState.getAuthors();
            library = libraryState.getLibrary();
        }

    } // This method should be private

    protected void saveLibrary() {
        FileHelper.saveToDisk(new LibraryState(library, authors, publishers));
    }

    protected Set<Subject> getSubjects() {
        return subjects;
    }

    protected Set<Readable> getLibrary() {
        return library;
    }

    protected Set<Author> getAuthors() {
        return authors;
    }

    protected Set<Publisher> getPublishers() {
        return publishers;
    }

    protected boolean addReadable(Readable readable) {
        return library.add(readable);
    }

    protected boolean addAuthor(Author author) {
        return authors.add(author);
    }

    protected boolean addPublisher(Publisher publisher) {
        return publishers.add(publisher);
    }

    protected boolean removeReadable(Readable readable) {
        return library.remove(readable);
    }

    private void loadProperties() {
        properties = new Properties();
        try {
            properties.load(getClass()
                    .getClassLoader()
                    .getResourceAsStream("application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadSubjects() {
        String path = properties.getProperty("subjectsUri");
        if (path != null) {
            try {
                subjects = FileHelper.loadCsv(path);
            } catch (FileNotFoundException e) {
                e.printStackTrace(); //For now
                //            TODO: Load message window
            }
        }
    }
}
