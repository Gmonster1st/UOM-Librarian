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
//TODO:Documentation

public class Classifier {
    private static final String BOOK = "Book";
    private static final String PERIODICAL = "Periodical";
    private static final String THESIS = "Thesis";

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

    private static String codeBuilder(Book book, int maxAuthors) {
        StringBuilder codeBuilder = new StringBuilder();
        codeBuilder.append(book.getSubject().getCode()).append(".").append(book.getCopyId()).append(" ");
        for (int i = 0; i < maxAuthors; i++) {
            var author = book.getAuthorAt(i);
            if (maxAuthors == 1) {
                codeBuilder.append(author.getName().toUpperCase().substring(0, 2));
            }
            codeBuilder.append(author.getSurname().toUpperCase().charAt(0));
        }
        return codeBuilder.toString();
    }

    private static String codeBuilder(Thesis thesis) {
        StringBuilder codeBuilder = new StringBuilder();
        codeBuilder.append(thesis.getSubject().getCode()).append(".").append(thesis.getCopyId()).append(" ");
        var author = thesis.getAuthor();
        codeBuilder.append(author.getName().toUpperCase().substring(0, 2));
        codeBuilder.append(author.getSurname().toUpperCase().charAt(0));
        return codeBuilder.toString();
    }

    private static String codeBuilder(Periodical periodical) {
        StringBuilder codeBuilder = new StringBuilder();
        codeBuilder.append(periodical.getSubject().getCode()).append(".").append(periodical.getCopyId()).append(" ");
        codeBuilder.append(periodical.getTitle().toUpperCase().substring(0, 2));
        codeBuilder.append(periodical.getPublisher().getName().toUpperCase().charAt(0));
        return codeBuilder.toString();
    }
}
