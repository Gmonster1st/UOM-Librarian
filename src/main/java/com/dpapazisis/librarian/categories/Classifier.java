/*
 * Copyright (c) 2020.
 * Dimitrios Papazisis
 * This Software is to be used for educational purposes only.
 * All rights Reserved.
 */

package com.dpapazisis.librarian.categories;

import com.dpapazisis.librarian.model.readable.Book;
import com.dpapazisis.librarian.model.readable.Periodical;
import com.dpapazisis.librarian.model.readable.Readable;
import com.dpapazisis.librarian.model.readable.Thesis;

/**
 * Classifier is a static utility class that is used to auto generate Dewey reference codes for the Documents
 * in this application
 */
public class Classifier {
    public static final String BOOK = "Book";
    public static final String PERIODICAL = "Periodical";
    public static final String THESIS = "Thesis";

    private Classifier() {
    }

    /**
     * Generates a Dewey Code from a {@link Readable} object
     * implemented by me <tt>for Demonstrating purposes only</tt>
     *
     * @param readable {@link Readable} any readable and subclasses
     * @return {@link String} a dewey code for the Readable object
     */
    public static DeweyCode generateDeweyCode(Readable readable) {
        switch (readable.getClass().getSimpleName()) {
            case BOOK:
                var book = (Book) readable;
                int maxAuthors = Math.min(book.getNumberOfAuthors(), 3);
                return new DeweyCode(book.getSubject().getName(), codeBuilder(book, maxAuthors));
            case PERIODICAL:
                var periodical = (Periodical) readable;
                return new DeweyCode(periodical.getSubject().getName(), codeBuilder(periodical));
            case THESIS:
                var thesis = (Thesis) readable;
                return new DeweyCode(thesis.getSubject().getName(), codeBuilder(thesis));
            default:
                return null;
        }
    }

    /**
     * Code Builder overload that works for {@link Book} objects
     *
     * @param book       {@link Book} object passed
     * @param maxAuthors <tt>int</tt> the number of Authors the book has assigned to it
     * @return {@link String} a generated code for the Book passed in the method
     */
    private static String codeBuilder(Book book, int maxAuthors) {
        StringBuilder codeBuilder = new StringBuilder();
        codeBuilder.append("B-").append(book.getSubject().getCode()).append(".").append(book.getCopyId()).append(" ");
        for (int i = 0; i < maxAuthors; i++) {
            var author = book.getAuthorAt(i);
            if (maxAuthors == 1) {
                codeBuilder.append(author.getName().toUpperCase().substring(0, 2));
            }
            codeBuilder.append(author.getSurname().toUpperCase().charAt(0));
        }
        return codeBuilder.toString();
    }

    /**
     * Code Builder overload that works for {@link Thesis} objects
     *
     * @param thesis {@link Thesis} object passed
     * @return {@link String} a generated code for the Thesis passed in the method
     */
    private static String codeBuilder(Thesis thesis) {
        StringBuilder codeBuilder = new StringBuilder();
        codeBuilder.append("T-").append(thesis.getSubject().getCode()).append(".").append(thesis.getCopyId()).append(" ");
        var author = thesis.getAuthor();
        codeBuilder.append(author.getName().toUpperCase().substring(0, 2));
        codeBuilder.append(author.getSurname().toUpperCase().charAt(0));
        return codeBuilder.toString();
    }

    /**
     * Code Builder overload that works for {@link Periodical} objects
     *
     * @param periodical {@link Periodical} object passed
     * @return {@link String} a generated code for the Periodical passed in the method
     */
    private static String codeBuilder(Periodical periodical) {
        StringBuilder codeBuilder = new StringBuilder();
        codeBuilder.append("P-").append(periodical.getSubject().getCode()).append(".").append(periodical.getCopyId()).append(" ");
        codeBuilder.append(periodical.getTitle().toUpperCase().substring(0, 2));
        codeBuilder.append(periodical.getPublisher().getName().toUpperCase().charAt(0));
        return codeBuilder.toString();
    }
}
