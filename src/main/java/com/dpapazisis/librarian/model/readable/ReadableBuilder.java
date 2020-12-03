package com.dpapazisis.librarian.model.readable;

import java.time.Year;

public abstract class ReadableBuilder {
    protected final String title;
    protected final Year year;
    protected final int pages;

    public ReadableBuilder(String title, Year year, int pages) {
        this.title = title;
        this.year = year;
        this.pages = pages;
    }

    public abstract Readable build();
}
