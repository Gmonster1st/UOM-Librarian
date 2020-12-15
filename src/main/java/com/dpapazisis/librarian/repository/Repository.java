/*
 * Copyright (c) 2020.
 * Dimitrios Papazisis
 * This Software is to be used for educational purposes only.
 * All rights Reserved.
 */

package com.dpapazisis.librarian.repository;

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

public class Repository implements Serializable {
    private static Repository instance = null;
    private Properties properties;
    private Set<Subject> subjects;
    private Set<Readable> library;
    private Set<Author> authors;
    private Set<Publisher> publishers;

    private Repository() {
        loadProperties();
        loadSubjects();
        loadLibrary();
    }

//TODO: Implement this and check what else need to ne done by this class

    public void loadLibrary() {
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

    public void saveLibrary() {
        FileHelper.saveToDisk(new LibraryState(library, authors, publishers));
    }

    public static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }
        return instance;
    }

    public Set<Subject> getSubjects() {
        return subjects;
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

    public boolean addReadable(Readable readable) {
        return library.add(readable);
    }

    public boolean addAuthor(Author author) {
        return authors.add(author);
    }

    public boolean addPublisher(Publisher publisher) {
        return publishers.add(publisher);
    }

    public boolean removeReadable(Readable object) {
        return library.remove(object);
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
