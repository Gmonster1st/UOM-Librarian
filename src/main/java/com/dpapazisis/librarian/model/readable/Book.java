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
 * Book type that extends {@link Readable} and hold the data for a Book object
 * Use of Book constructor is restricted please utilize the inner {@link Builder} class.
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
     * Usage of the Constructor is restricted, please use the inner {@link Builder} class
     *
     * @param builder {@link Builder}
     *
     *                <p>
     *                Usage example:<br>
     *                <pre>
     *                 <code>new Book.Builder(title,year,pages,subject).build();</code>
     *                 </pre>
     *                </p>
     */
    private Book(Builder builder) {
        super(builder.title, builder.year, builder.pages, builder.subject);
        this.isbn = builder.isbn;
        this.publisher = builder.publisher;
        this.authors = builder.authors;
        setReferenceCode();
    }
//TODO:Documentation
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

    public List<Author> getAuthors() {
        return authors;
    }

    public Author getAuthorAt(int index) {
        return authors.get(index);
    }

    public boolean addAuthor(Author author) {
        if (authors.size() != MAX_AUTHORS) {
            authors.add(author);
            return true;
        } else {
            return false;
        }
    }

    public void removeAuthor(Author author) {
        if (!authors.isEmpty()) {
            authors.remove(author);
        }
    }

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

    public static class Builder extends ReadableBuilder {
        private String isbn;
        private Publisher publisher;
        private List<Author> authors = new ArrayList<>(5);

        public Builder(String title, Year year, int pages, Subject subject) {
            super(title, year, pages, subject);
        }

        public Builder withISBN(String isbn) {
            this.isbn = validateISBN(isbn);
            return this;
        }

        public Builder withAuthors(List<Author> authors) {
            if (authors.size() > MAX_AUTHORS) {
                throw new IllegalArgumentException("Authors should not exceed the number of 5!");
            }
            this.authors = authors;
            return this;
        }

        public Builder withAuthor(Author author) {
            authors.add(author);
            return this;
        }

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
