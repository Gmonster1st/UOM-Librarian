/*
 * Copyright (c) 2020.
 * Dimitrios Papazisis
 * This Software is to be used for educational purposes only.
 * All rights Reserved.
 */

package com.dpapazisis.librarian.model.readable;

import com.dpapazisis.librarian.categories.Classifier;
import com.dpapazisis.librarian.categories.Subject;
import com.dpapazisis.librarian.model.person.Author;
import com.dpapazisis.librarian.model.publisher.Publisher;

import java.time.Year;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static com.dpapazisis.librarian.model.readable.PublisherProtected.validateISBN;

/**
 * Book type that extends {@link Readable} and implements {@link PublisherProtected} interface
 * the data for a Book object
 * Use of Book constructor is restricted please utilize the inner {@link Book.Builder} class.
 * <p>
 * Usage example:<br>
 * <pre>
 * <code>new Book.Builder(title,year,pages,subject).build();</code>
 * </pre>
 * </p>
 */
public final class Book extends Readable implements PublisherProtected {
    private static final int MAX_AUTHORS = 5;

    private String isbn;
    private Publisher publisher;
    private final List<Author> authors;

    /**
     * Usage of the Constructor is restricted, please use the inner {@link Book.Builder} class
     *
     * @param builder {@link Book.Builder}
     *                <p>Usage example:<br>
     *                <pre><code>new Book.Builder(title,year,pages,subject).build();</code></pre>
     *                </p>
     */
    private Book(Builder builder) {
        super(builder.title, builder.year, builder.pages, builder.subject);
        this.isbn = builder.isbn;
        this.publisher = builder.publisher;
        this.authors = builder.authors;
        setReferenceCode();
    }

    @Override
    public String getISBN() {
        return this.isbn;
    }

    @Override
    public void setISBN(String isbn) {
        this.isbn = validateISBN(isbn);
    }

    @Override
    public Publisher getPublisher() {
        return this.publisher;
    }

    @Override
    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    /**
     * Returns the list of authors that are specified for this Book
     *
     * @return {@link List<Author>} the author list
     */
    public List<Author> getAuthors() {
        return authors;
    }

    /**
     * Returns the {@link Author} object from the list at the given index
     *
     * @param index <tt>int</tt> the index of the list
     * @return {@link Author} the Author object at the index
     * @throws IllegalArgumentException if the index is more than the size of the list
     */
    public Author getAuthorAt(int index) {
        if (index > authors.size() - 1) {
            throw new IllegalArgumentException("There is no Author at index " + "(" + index + ")");
        }
        return authors.get(index);
    }

    /**
     * Adds an {@link Author} object to the list of authors of this Readable
     *
     * @param author {@link Author} the Author object to be added
     * @return <tt>true</tt> if the addition was successful and
     * <tt>false</tt> if the list was full to the max number of 5
     */
    public boolean addAuthor(Author author) {
        if (authors.size() != MAX_AUTHORS) {
            authors.add(author);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes an {@link Author} object from the authors list
     *
     * @param author {@link Author} the object to remove
     */
    public void removeAuthor(Author author) {
        if (!authors.isEmpty()) {
            authors.remove(author);
        }
    }

    /**
     * Returns the number of authors in the Readable object
     *
     * @return <tt>int</tt> the number of authors
     */
    public int getNumberOfAuthors() {
        return authors.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Book book = (Book) o;
        return isbn.equals(book.isbn) && getPublisher().equals(book.getPublisher()) && getAuthors().equals(book.getAuthors());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), isbn, getPublisher(), getAuthors());
    }

    @Override
    public String toString() {
        return super.toString() +
                "isbn='" + isbn + '\'' +
                ", publisher=" + publisher +
                ", authors=" + authors;
    }

    @Override
    protected void setReferenceCode() {
        this.referenceCode = Classifier.generateDeweyCode(this);
    }

    /**
     * Inner Builder class that extends {@link ReadableBuilder}
     * that builds {@link Book} objects
     */
    public static class Builder extends ReadableBuilder {
        private String isbn;
        private Publisher publisher;
        private List<Author> authors = new ArrayList<>(5);

        /**
         * Constructor of the Builder that requires the minimum data
         * for creating a {@link Book} object
         *
         * @param title   {@link String} the title of the Book
         * @param year    {@link Year} the year of the Book
         * @param pages   <tt>int</tt> the number of pages
         * @param subject {@link Subject} the subject of the Book
         */
        public Builder(String title, Year year, int pages, Subject subject) {
            super(title, year, pages, subject);
        }

        /**
         * Sets the ISBN code for the current {@link Book} to be created
         *
         * @param isbn {@link String} the ISBN code
         * @return {@link Book.Builder}
         * @throws IllegalArgumentException if the code does not comply with
         *                                  the formats of 10Digit ISBN and 13Digit ISBN
         */
        public Builder withISBN(String isbn) {
            this.isbn = validateISBN(isbn);
            return this;
        }

        /**
         * Sets the authors of the specified {@link Book} to be created
         *
         * @param authors {@link List<Author>} the list of authors
         * @return {@link Book.Builder}
         * @throws IllegalArgumentException if the list contains more than 5 authors
         */
        public Builder withAuthors(List<Author> authors) {
            if (authors.size() > MAX_AUTHORS) {
                throw new IllegalArgumentException("Authors should not exceed the number of 5!");
            }
            this.authors = authors;
            return this;
        }

        /**
         * Adds one {@link Author} object to the {@link Book}
         *
         * @param author {@link Author} the Author of the Book
         * @return {@link Book.Builder}
         */
        public Builder withAuthor(Author author) {
            authors.add(author);
            return this;
        }

        /**
         * Adds the {@link Publisher} object that is the Publisher of the {@link Book}
         *
         * @param publisher {@link Publisher} the publisher of the Book
         * @return {@link Book.Builder}
         */
        public Builder andPublisher(Publisher publisher) {
            this.publisher = publisher;
            return this;
        }

        @Override
        public Book build() {
            return new Book(this);
        }

        @Override
        public List<Readable> build(int copies) {
            List<Readable> multipleCopies = new LinkedList<>();
            for (int i = 0; i < copies; i++) {
                var book = new Book(this);
                book.setCopyId((i < 10) ? "0" + i : String.valueOf(i));
                book.setReferenceCode();
                multipleCopies.add(book);
            }
            return multipleCopies;
        }
    }
}
