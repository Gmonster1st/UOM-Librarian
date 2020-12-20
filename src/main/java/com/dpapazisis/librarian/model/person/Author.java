/*
 * Copyright (c) 2020.
 * Dimitrios Papazisis
 * This Software is to be used for educational purposes only.
 * All rights Reserved.
 */

package com.dpapazisis.librarian.model.person;

import com.dpapazisis.librarian.model.publisher.Publisher;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Author class that extends {@link Person} and defines an Author
 */
public final class Author extends Person {
    private int numberOfBooks;
    private Set<Publisher> publishers = new HashSet<>(1);

    /**
     * Constructor that created an Author object with the complete data of a person
     * as defined in {@link Person} superclass and the number of Documents published and one ore more publishers
     *
     * @param name          {@link String} the name of the Author
     * @param surname       {@link String} the surname of the Author
     * @param birthDate     {@link LocalDate} the date of birth of the Author
     * @param shortBio      {@link String} a short description of the Authors achievements
     * @param email         {@link String} the email address of the Author
     * @param numberOfBooks <tt>int</tt> number of Documents that the Author has published
     * @param publisher     {@link Publisher} one or more parameters of Publishers that the Author has collaborated
     * @throws IllegalArgumentException if the email address does not comply with the standard email format
     *                                  <p>Example: <pre>email@host.com</pre></p>
     */
    public Author(String name, String surname, LocalDate birthDate, String shortBio, String email,
                  int numberOfBooks, Publisher... publisher) {
        this(name, surname, birthDate, shortBio, email, numberOfBooks);
        if (publisher != null) {
            this.publishers = new HashSet<>(Arrays.asList(publisher));
        }
    }

    /**
     * Constructor that created an Author object with the complete data of a person
     * as defined in {@link Person} superclass and the number of Documents published
     *
     * @param name          {@link String} the name of the Author
     * @param surname       {@link String} the surname of the Author
     * @param birthDate     {@link LocalDate} the date of birth of the Author
     * @param shortBio      {@link String} a short description of the Authors achievements
     * @param email         {@link String} the email address of the Author
     * @param numberOfBooks <tt>int</tt> number of Documents that the Author has published
     * @throws IllegalArgumentException if the email address does not comply with the standard email format
     *                                  <p>Example: <pre>email@host.com</pre></p>
     */
    public Author(String name, String surname, LocalDate birthDate, String shortBio, String email,
                  int numberOfBooks) {
        this(name, surname, birthDate, shortBio, email);
        this.numberOfBooks = numberOfBooks;
    }

    /**
     * Constructor that created an Author object with the complete data of a person
     * as defined in {@link Person} superclass
     *
     * @param name      {@link String} the name of the Author
     * @param surname   {@link String} the surname of the Author
     * @param birthDate {@link LocalDate} the date of birth of the Author
     * @param shortBio  {@link String} a short description of the Authors achievements
     * @param email     {@link String} the email address of the Author
     * @throws IllegalArgumentException if the email address does not comply with the standard email format
     *                                  <p>Example: <pre>email@host.com</pre></p>
     */
    public Author(String name, String surname, LocalDate birthDate, String shortBio, String email) {
        super(name, surname, birthDate, shortBio, email);
    }

    /**
     * Constructor that created an Author object with the minimum data as defined in {@link Person} superclass
     *
     * @param name      {@link String} the name of the Author
     * @param surname   {@link String} the surname of the Author
     * @param birthDate {@link LocalDate} the date of birth of the Author
     */
    public Author(String name, String surname, LocalDate birthDate) {
        super(name, surname, birthDate);
    }

    /**
     * Returns the number of Documents the Author has published
     *
     * @return <tt>int</tt> the number of Documents
     */
    public int getNumberOfBooks() {
        return numberOfBooks;
    }

    /**
     * Sets the number of Documents the Author has published
     *
     * @param numberOfBooks <tt>int</tt> the number of Published Documents
     */
    public void setNumberOfBooks(int numberOfBooks) {
        this.numberOfBooks = numberOfBooks;
    }

    /**
     * Adds a {@link Publisher} object to the Collection of publishers the Author has worked with
     *
     * @param publisher {@link Publisher} the Publisher to add
     * @return <tt>true</tt> if the publisher was added or <tt>false</tt> if it did not
     */
    public boolean addPublisher(Publisher publisher) {
        return publishers.add(publisher);
    }

    /**
     * Removes a {@link Publisher} object from the Collection of publisher of the Author
     *
     * @param publisher {@link Publisher} the publisher object to remove
     * @return <tt>true</tt> if the Publisher existed in the Collection and was removed and
     * <tt>false</tt> if the object did not exist in the Collection
     */
    public boolean removePublisher(Publisher publisher) {
        return publishers.remove(publisher);
    }

    /**
     * Returns a {@link Set<Publisher>} set with the Publishers that the Author has collaborated with
     *
     * @return {@link Set<Publisher>} the collection of publishers
     */
    public Set<Publisher> getPublishers() {
        return publishers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Author author = (Author) o;
        return getNumberOfBooks() == author.getNumberOfBooks() && getPublishers().equals(author.getPublishers());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getNumberOfBooks(), getPublishers());
    }
}
