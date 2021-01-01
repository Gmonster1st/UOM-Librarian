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
import com.dpapazisis.librarian.model.readable.*;

import java.util.*;
import java.util.stream.Collectors;

import static com.dpapazisis.librarian.model.readable.ThesisType.*;
import static java.util.Map.entry;
import static java.util.stream.Collectors.groupingBy;

/**
 * The Library service class is a Singleton class that acts as the mediator between
 * the repository of the app and the GUI.
 */
public class LibraryService {
    private static final LibraryService INSTANCE = new LibraryService();

    private final List<LibraryObserver> observers;

    private final Repository repository;

    private LibraryService() {
        this.observers = new ArrayList<>();
        this.repository = new Repository();
    }

    /**
     * @return The singleton instance of the {@link LibraryService} class
     */
    public static LibraryService getInstance() {
        return INSTANCE;
    }

    /**
     * Adds a {@link LibraryObserver} instance to the list of observers
     *
     * @param observer Object that implements the {@link LibraryObserver} interface
     */
    public void addObserver(LibraryObserver observer) {
        observers.add(observer);
    }

    /**
     * Removes a {@link LibraryObserver} instance from the list of observers
     *
     * @param observer Object that implements the {@link LibraryObserver} interface
     */
    public void removeObserver(LibraryObserver observer) {
        observers.remove(observer);
    }

    /**
     * @return the {@link Set}<{@link Subject}> from the repository
     */
    public Set<Subject> getSubjects() {
        return repository.getSubjects();
    }

    /**
     * @return the {@link Set}<{@link Readable}> from the repository
     */
    public Set<Readable> getLibrary() {
        return repository.getLibrary();
    }

    /**
     * @return the {@link Set}<{@link Author}> from the repository
     */
    public Set<Author> getAuthors() {
        return repository.getAuthors();
    }

    /**
     * @return the {@link Set}<{@link Publisher}> from the repository
     */
    public Set<Publisher> getPublishers() {
        return repository.getPublishers();
    }

    /**
     * @return the {@link Set}<{@link Professor}> from the repository
     */
    public Set<Professor> getProfessors() {
        return repository.getProfessors();
    }

    /**
     * Adds a new {@link Readable} in the repository
     *
     * @param readable the {@link Readable} to be added
     * @return <tt>true</tt> if the operation was successful
     */
    public boolean addNewReadable(Readable readable) {
        if (repository.addReadable(readable)) {
            notifyObservers(LibraryAction.ADD_NEW);
            return true;
        }
        return false;
    }

    public boolean addMultiCopyReadable(Set<Readable> copies) {
        for (var readable : copies) {
            if (!repository.addReadable(readable)) {
                return false;
            }
        }
        notifyObservers(LibraryAction.ADD_NEW);
        return true;
    }

    /**
     * Adds a new {@link Author} in the repository
     *
     * @param author the {@link Author} to be added
     * @return <tt>true</tt> if the operation was successful
     */
    public boolean addAuthor(Author author) {
        if (repository.addAuthor(author)) {
            notifyObservers(LibraryAction.ADD_NEW);
            return true;
        }
        return false;
    }

    /**
     * Adds a new {@link Publisher} in the repository
     *
     * @param publisher the {@link Publisher} to be added
     * @return <tt>true</tt> if the operation was successful
     */
    public boolean addPublisher(Publisher publisher) {
        if (repository.addPublisher(publisher)) {
            notifyObservers(LibraryAction.ADD_NEW);
            return true;
        }
        return false;
    }

    /**
     * Adds a new {@link Professor} in the repository
     *
     * @param professor the {@link Professor} to be added
     * @return <tt>true</tt> if the operation was successful
     */
    public boolean addProfessor(Professor professor) {
        if (repository.addProfessor(professor)) {
            notifyObservers(LibraryAction.ADD_NEW);
            return true;
        }
        return false;
    }

    /**
     * Removes a {@link Readable} object from the library
     *
     * @param readable the {@link Readable} object to remove
     * @return <tt>true</tt> if the operation was successful
     */
    public boolean removeReadable(Readable readable) {
        if (repository.removeReadable(readable)) {
            notifyObservers(LibraryAction.DELETE);
            return true;
        }
        return false;
    }

    /**
     * Notifies all {@link LibraryObserver} classes for changes in the library
     *
     * @param action {@link LibraryAction}
     */
    public void notifyObservers(LibraryAction action) {
        for (var observer : observers) {
            observer.update(action);
        }
        repository.saveLibrary();
    }

    /**
     * Returns the total number of {@link Book} objects in the library
     *
     * @return <tt>int</tt>
     */
    public int getNumberOfBooks() {
        int count = 0;
        for (var readable : repository.getLibrary()) {
            if (readable instanceof Book) {
                count++;
            }
        }
        return count;
    }

    /**
     * Returns the total number of {@link Periodical} objects in the library
     *
     * @return <tt>int</tt>
     */
    public int getNumberOfPeriodicals() {
        int count = 0;
        for (var readable : repository.getLibrary()) {
            if (readable instanceof Periodical) {
                count++;
            }
        }
        return count;
    }

    /**
     * Returns the total number of {@link Thesis} objects in the library
     *
     * @return <tt>int</tt>
     */
    public int getNumberOfThesis() {
        int count = 0;
        for (var readable : repository.getLibrary()) {
            if (readable instanceof Thesis) {
                count++;
            }
        }
        return count;
    }

    /**
     * Returns the statistics of Thesis type object in a {@link Map}
     *
     * @return {@link Map}<{@link ThesisType}, {@link Integer}>
     */
    public Map<ThesisType, Integer> getNumbersOfTypesOfThesis() {
        Map<ThesisType, Integer> count = new EnumMap<>(Map.ofEntries(
                entry(GRADUATE, 0),
                entry(POST_GRADUATE, 0),
                entry(DOCTORAL, 0)
        ));
        for (var readable : repository.getLibrary()) {
            if (readable instanceof Thesis) {
                var thesis = (Thesis) readable;
                switch (thesis.getType()) {
                    case GRADUATE:
                        count.replace(GRADUATE, count.get(GRADUATE) + 1);
                        break;
                    case POST_GRADUATE:
                        count.replace(POST_GRADUATE, count.get(POST_GRADUATE) + 1);
                        break;
                    case DOCTORAL:
                        count.replace(DOCTORAL, count.get(DOCTORAL) + 1);
                        break;
                }
            }
        }
        return count;
    }

    /**
     * Returns a {@link Set}<{@link Readable}> objects that equal to the search term
     *
     * @param title The full title of the Readable
     * @return {@link Set}<{@link Readable}>
     */
    public Set<Readable> getSearchByTitle(String title) {
        return getLibrary().stream().filter(readable -> readable.getTitle().equals(title)).collect(Collectors.toSet());
    }

    /**
     * Returns a {@link Set}<{@link Readable}> objects that contain the search term
     *
     * @param author the name of an author
     * @return {@link Set}<{@link Readable}>
     */
    public Set<Readable> getSearchByAuthor(String author) {
        var library = getLibrary()
                .stream()
                .filter(readable -> readable instanceof Book || readable instanceof Thesis)
                .collect(Collectors.toSet());
        var results = new HashSet<Readable>();
        for (var readable : library) {
            if (readable instanceof Thesis) {
                var thesis = (Thesis) readable;
                if (thesis.getAuthor().toString().contains(author)) {
                    results.add(readable);
                }
            } else {
                var book = (Book) readable;
                for (var bookAuthor : book.getAuthors()) {
                    if (bookAuthor.toString().contains(author)) {
                        results.add(readable);
                        break;
                    }
                }
            }
        }

        return results;
    }

    /**
     * Returns a {@link Set}<{@link Readable}> objects that contain the search term
     *
     * @param keyWord the key word in the title
     * @return {@link Set}<{@link Readable}>
     */
    public Set<Readable> getSearchByKeyWord(String keyWord) {
        return getLibrary().stream().filter(readable -> readable.getTitle().contains(keyWord)).collect(Collectors.toSet());
    }

    /**
     * Returns a {@link List}<{@link Readable}> that groups copies of a {@link Readable} in one row
     *
     * @return {@link List}<{@link Readable}>
     */
    public List<Readable> getLibraryGroupedByCopy() {
        return getLibrary().stream()
                .collect(groupingBy(Readable::isCopy))
                .values()
                .stream()
                .map(l -> l.get(0))
                .collect(Collectors.toList());
    }
}


