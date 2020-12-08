/*
 * Copyright (c) 2020.
 * Dimitrios Papazisis
 * This Software is to be used for educational purposes only.
 * All rights Reserved.
 */

package com.dpapazisis.librarian.model.readable;

import com.dpapazisis.librarian.categories.Classifier;
import com.dpapazisis.librarian.categories.Subject;
import com.dpapazisis.librarian.model.publisher.Publisher;

import java.time.Year;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import static com.dpapazisis.librarian.model.readable.PublisherProtected.validateISBN;

/**
 * Periodical type that extends {@link Readable} and implements {@link PublisherProtected} interface
 * the data for a Periodical object
 * Use of Periodical constructor is restricted please utilize the inner {@link Periodical.Builder} class.
 * <p>
 * Usage example:<br>
 * <pre>
 * <code>new Periodical.Builder(title,year,pages,subject).build();</code>
 * </pre>
 * </p>
 */
public final class Periodical extends Readable implements PublisherProtected {
    private String isbn;
    private Publisher publisher;
    private int volume;
    private int issue;

    /**
     * Usage of the Constructor is restricted, please use the inner {@link Periodical.Builder} class
     *
     * @param builder {@link Periodical.Builder}
     *                <p>Usage example:<br>
     *                <pre><code>new Periodical.Builder(title,year,pages,subject).build();</code></pre>
     *                </p>
     */
    private Periodical(Builder builder) {
        super(builder.title, builder.year, builder.pages, builder.subject);
        this.isbn = builder.isbn;
        this.publisher = builder.publisher;
        this.volume = builder.volume;
        this.issue = builder.issue;
        setReferenceCode();
    }

    @Override
    public String getISBN() {
        return isbn;
    }

    @Override
    public void setISBN(String isbn) {
        this.isbn = validateISBN(isbn);
    }

    @Override
    public Publisher getPublisher() {
        return publisher;
    }

    @Override
    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    /**
     * Returns the volume number of the Periodical
     *
     * @return <tt>int</tt> the volume number
     */
    public int getVolume() {
        return volume;
    }

    /**
     * Sets the volume number of the Periodical
     *
     * @param volume <tt>int</tt> the volume number
     */
    public void setVolume(int volume) {
        this.volume = volume;
    }

    /**
     * Returns the issue number of the Periodical
     *
     * @return <tt>int</tt> the issue number
     */
    public int getIssue() {
        return issue;
    }

    /**
     * Sets the issue number of the Periodical
     *
     * @param issue <tt>int</tt> the issue number
     */
    public void setIssue(int issue) {
        this.issue = issue;
    }

    @Override
    protected void setReferenceCode() {
        this.referenceCode = Classifier.generateDeweyCode(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Periodical that = (Periodical) o;
        return getVolume() == that.getVolume() && getIssue() == that.getIssue() && isbn.equals(that.isbn) && getPublisher().equals(that.getPublisher());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), isbn, getPublisher(), getVolume(), getIssue());
    }

    @Override
    public String toString() {
        return super.toString() +
                ", isbn='" + isbn + '\'' +
                ", publisher=" + publisher +
                ", volume=" + volume +
                ", issue=" + issue;
    }

    /**
     * Inner Builder class that extends {@link ReadableBuilder}
     * that builds {@link Periodical} objects
     */
    public static class Builder extends ReadableBuilder {
        private String isbn;
        private Publisher publisher;
        private int volume;
        private int issue;

        /**
         * Constructor of the Builder that requires the minimum data
         * for creating a {@link Periodical} object
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
         * Sets the ISBN code for the current {@link Periodical} to be created
         *
         * @param isbn {@link String} the ISBN code
         * @return {@link Periodical.Builder}
         * @throws IllegalArgumentException if the code does not comply with
         *                                  the formats of 10Digit ISBN and 13Digit ISBN
         */
        public Builder withISBN(String isbn) {
            this.isbn = validateISBN(isbn);
            return this;
        }

        /**
         * Adds the {@link Publisher} object that is the Publisher of the {@link Periodical}
         *
         * @param publisher {@link Publisher} the publisher of the Book
         * @return {@link Periodical.Builder}
         */
        public Builder andPublisher(Publisher publisher) {
            this.publisher = publisher;
            return this;
        }

        /**
         * Adds the volume number of the {@link Periodical} object to be created
         *
         * @param volume <tt>int</tt> the volume number
         * @return {@link Periodical.Builder}
         */
        public Builder isVolume(int volume) {
            this.volume = volume;
            return this;
        }

        /**
         * Adds the issue number of the {@link Periodical} object to be created
         *
         * @param issue <tt>int</tt> the issue number
         * @return {@link Periodical.Builder}
         */
        public Builder isIssue(int issue) {
            this.issue = issue;
            return this;
        }

        @Override
        public Periodical build() {
            return new Periodical(this);
        }

        @Override
        public Set<Readable> build(int copies) {
            Set<Readable> multipleCopies = new LinkedHashSet<>();
            for (int i = 0; i < copies; i++) {
                var periodical = new Periodical(this);
                periodical.setCopyId((i < 10) ? "0" + i : String.valueOf(i));
                periodical.setReferenceCode();
                multipleCopies.add(periodical);
            }
            return multipleCopies;
        }
    }
}
