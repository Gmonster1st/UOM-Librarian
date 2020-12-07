/*
 * Copyright (c) 2020.
 * Dimitrios Papazisis
 * This Software is to be used for educational purposes only.
 * All rights Reserved.
 */

package com.dpapazisis.librarian.model.person;

import com.dpapazisis.librarian.model.publisher.Publisher;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//TODO:Documentation

public final class Author extends Person {
    private int numberOfBooks;
    private List<Publisher> publishers = new ArrayList<>(1);

    //TODO:Revisit this class
    public Author(String name, String surname, LocalDate birthDate, String shortBio, String email,
                  int numberOfBooks, Publisher... publisher) {
        this(name, surname, birthDate, shortBio, email, numberOfBooks);
        if (publisher != null) {
            this.publishers = new ArrayList<>(Arrays.asList(publisher));
        }
    }

    public Author(String name, String surname, LocalDate birthDate, String shortBio, String email,
                  int numberOfBooks) {
        this(name, surname, birthDate, shortBio, email);
        this.numberOfBooks = numberOfBooks;
    }

    public Author(String name, String surname, LocalDate birthDate, String shortBio, String email) {
        super(name, surname, birthDate, shortBio, email);
    }

    public Author(String name, String surname, LocalDate birthDate) {
        super(name, surname, birthDate);
    }

    public int getNumberOfBooks() {
        return numberOfBooks;
    }

    public void setNumberOfBooks(int numberOfBooks) {
        this.numberOfBooks = numberOfBooks;
    }

    public boolean addPublisher(Publisher publisher) {
        return publishers.add(publisher);
    }

    public boolean removePublisher(Publisher publisher) {
        return publishers.remove(publisher);
    }

    @Override
    public String toString() {
        return super.toString() +
                "numberOfBooks=" + numberOfBooks +
                ", publishers=" + publishers;
    }
}
