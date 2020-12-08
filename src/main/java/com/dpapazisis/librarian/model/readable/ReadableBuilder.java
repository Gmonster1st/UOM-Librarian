/*
 * Copyright (c) 2020.
 * Dimitrios Papazisis
 * This Software is to be used for educational purposes only.
 * All rights Reserved.
 */

package com.dpapazisis.librarian.model.readable;

import com.dpapazisis.librarian.categories.Subject;

import java.time.Year;
import java.util.List;
import java.util.Set;

/**
 * ReadableBuilder abstract class that is the prototype for the inner builder classes
 * of every {@link Readable} subclass. It specifies the minimum data allowed for the creation
 * of a new Readable object
 */
public abstract class ReadableBuilder {
    protected final String title;
    protected final Year year;
    protected final int pages;
    protected final Subject subject;

    /**
     * Constructor of the Builder that requires the minimum data
     * for creating a {@link Readable} object
     *
     * @param title   {@link String} the title of the Readable
     * @param year    {@link Year} the year of the Readable
     * @param pages   <tt>int</tt> the number of pages
     * @param subject {@link Subject} the subject of the Readable
     */
    protected ReadableBuilder(String title, Year year, int pages, Subject subject) {
        this.title = title;
        this.year = year;
        this.pages = pages;
        this.subject = subject;
    }

    /**
     * Builds a {@link Readable} object and subclasses
     *
     * @return {@link Readable} or subclass
     */
    public abstract Readable build();

    /**
     * Builds a number of {@link Readable} object and subclasses
     * that represent the copies of the same Readable
     *
     * @param copies <tt>int</tt> the number of copies to create
     * @return {@link List<Readable>} objects created by the subclass
     */
    public abstract Set<Readable> build(int copies);
}
