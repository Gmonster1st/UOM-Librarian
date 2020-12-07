/*
 * Copyright (c) 2020.
 * Dimitrios Papazisis
 * This Software is to be used for educational purposes only.
 * All rights Reserved.
 */

package com.dpapazisis.librarian.model.readable;

import com.dpapazisis.librarian.categories.DeweyCode;
import com.dpapazisis.librarian.categories.Subject;

import java.time.Year;
import java.util.Objects;

/**
 * Readable abstract class that holds the general type of objects
 * to use in a Library application. Use one of its subclasses
 */
public abstract class Readable {
    private String title;
    private Year year;
    private int pages;
    protected final Subject subject;
    protected DeweyCode referenceCode;
    private boolean lendStatus;
    private String copyId = "00";

    /**
     * Constructor method to be used from subclasses as a blueprint for object creation
     *
     * @param title   {@link String} the title of the Readable object
     * @param year    {@link Year} the year the item was published
     * @param pages   <tt>int</tt> the number of pages this object has
     * @param subject {@link Subject} the subject that this object refers to
     * @throws IllegalArgumentException if the year set is later than the current year and/or
     *                                  if the pages are set to 0 or less
     */
    protected Readable(String title, Year year, int pages, Subject subject) {
        this.title = title;
        this.year = validYear(year);
        this.pages = validPages(pages);
        this.lendStatus = false;
        this.subject = subject;
    }

    /**
     * Returns the title of the Readable
     *
     * @return {@link String} Returns the title of the Readable
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the title of the Readable object
     *
     * @param title {@link String}
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the year of the the Readable object
     *
     * @return {@link Year} Returns the year of the the Readable object
     */
    public Year getYear() {
        return year;
    }

    /**
     * Sets the year that this readable object was published
     *
     * @param year {@link Year}
     * @throws IllegalArgumentException if the year set is later than the current year
     */
    public void setYear(Year year) {
        this.year = validYear(year);
    }

    /**
     * Validates that the year passed to the object was not later than
     * the current year and returns the Year as is. If the year is invalid
     * it throws an {@link IllegalArgumentException}
     *
     * @param year {@link Year} the year object to validate
     * @return {@link Year}
     * @throws IllegalArgumentException if the year set is later than the current year
     */
    private Year validYear(Year year) {
        if (year.getValue() > Year.now().getValue()) {
            throw new IllegalArgumentException("The Year of the Item can not be later than the current Year!");
        }
        return year;
    }

    /**
     * Returns the number of pages that the current Readable has
     *
     * @return <tt>int</tt> Returns the number of pages
     */
    public int getPages() {
        return pages;
    }

    /**
     * Set the number of pages for the Readable object.
     * It will throw an {@link IllegalArgumentException} if the pages are 0 or less
     *
     * @param pages <tt>int</tt>
     * @throws IllegalArgumentException if the pages are 0 or less
     */
    public void setPages(int pages) {
        this.pages = validPages(pages);
    }

    /**
     * Validates the number of pages for the current object
     *
     * @param pages <tt>int</tt> the number to validate
     * @return <tt>int</tt> the number as is
     */
    private int validPages(int pages) {
        if (pages <= 0) {
            throw new IllegalArgumentException("The items pages can not be zero or less!");
        }
        return pages;
    }

    /**
     * Return the subject for the Readable object
     *
     * @return {@link Subject} for the current object
     */
    public Subject getSubject() {
        return subject;
    }

    /**
     * Returns the reference code for the Readable object
     *
     * @return {@link DeweyCode} returns the reference code of the current object
     */
    public DeweyCode getReferenceCode() {
        return referenceCode;
    }

    /**
     * Set the reference code for the readable object
     */
    protected abstract void setReferenceCode();

    /**
     * Returns the status of the Readable object in the Library
     *
     * @return <tt>true</tt> if is lend and <tt>false</tt> if is in the Library
     */
    public boolean isLend() {
        return lendStatus;
    }

    /**
     * Sets the status of the Readable object to <tt>true</tt> or <tt>false</tt>
     */
    public void setLendStatus(boolean lendStatus) {
        this.lendStatus = lendStatus;
    }

    /**
     * Returns the copy id of the Readable object
     *
     * @return {@link String} the copy id
     */
    public String getCopyId() {
        return copyId;
    }

    /**
     * Sets the copy id for the Readable object
     *
     * @param copyId {@link String} the copy id
     */
    protected void setCopyId(String copyId) {
        this.copyId = copyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Readable)) return false;
        Readable readable = (Readable) o;
        return getPages() == readable.getPages() &&
                getTitle().equals(readable.getTitle()) &&
                getYear().equals(readable.getYear()) &&
                getReferenceCode().equals(readable.getReferenceCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getYear(), getPages(), getReferenceCode());
    }

    @Override
    public String toString() {
        return "title='" + title + '\'' +
                ", year=" + year +
                ", pages=" + pages +
                ", referenceCode=" + referenceCode +
                ", isLend=" + lendStatus;
    }
}
