package com.dpapazisis.librarian.model.readable;

import com.dpapazisis.librarian.categories.Subject;

import java.time.Year;

public abstract class ReadableBuilder {
    protected final String title;
    protected final Year year;
    protected final int pages;
    protected final Subject subject;

    public ReadableBuilder(String title, Year year, int pages, Subject subject) {
        this.title = title;
        this.year = year;
        this.pages = pages;
        this.subject = subject;
    }

    public abstract Readable build();
}