/*
 * Copyright (c) 2020.
 * Dimitrios Papazisis
 * This Software is to be used for educational purposes only.
 * All rights Reserved.
 */

package com.dpapazisis.librarian.services;

import com.dpapazisis.librarian.categories.Subject;
import com.dpapazisis.librarian.model.person.Author;
import com.dpapazisis.librarian.model.person.Professor;
import com.dpapazisis.librarian.model.publisher.Publisher;
import com.dpapazisis.librarian.model.readable.Readable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LibraryService {
    private static final LibraryService INSTANCE = new LibraryService();

    private final List<LibraryObserver> observers;

    private final Repository repository;

    private LibraryService() {
        this.observers = new ArrayList<>();
        this.repository = new Repository();
    }

    public static LibraryService getInstance() {
        return INSTANCE;
    }

    public void addObserver(LibraryObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(LibraryObserver observer) {
        observers.remove(observer);
    }

    public boolean addNewReadable(Readable readable) {
        if (repository.addReadable(readable)) {
            notifyObservers(LibraryAction.ADD_NEW);
            return true;
        }
        return false;
    }

    public Set<Subject> getSubjects() {
        return repository.getSubjects();
    }

    public Set<Readable> getLibrary() {
        return repository.getLibrary();
    }

    public Set<Author> getAuthors() {
        return repository.getAuthors();
    }

    public Set<Publisher> getPublishers() {
        return repository.getPublishers();
    }

    public Set<Professor> getProfessors() {
        return repository.getProfessors();
    }

    public boolean addAuthor(Author author) {
        if (repository.addAuthor(author)) {
            notifyObservers(LibraryAction.ADD_NEW);
            return true;
        }
        return false;
    }

    public boolean addPublisher(Publisher publisher) {
        if (repository.addPublisher(publisher)) {
            notifyObservers(LibraryAction.ADD_NEW);
            return true;
        }
        return false;
    }

    public boolean addProfessor(Professor professor) {
        if (repository.addProfessor(professor)) {
            notifyObservers(LibraryAction.ADD_NEW);
            return true;
        }
        return false;
    }

    public boolean removeReadable(Readable readable) {
        if (repository.removeReadable(readable)) {
            notifyObservers(LibraryAction.DELETE);
            return true;
        }
        return false;
    }

    public void notifyObservers(LibraryAction action) {
        for (var observer : observers) {
            observer.update(action);
        }
        repository.saveLibrary();
    }
}
