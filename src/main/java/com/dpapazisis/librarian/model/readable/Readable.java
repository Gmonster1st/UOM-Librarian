package com.dpapazisis.librarian.model.readable;

import com.dpapazisis.librarian.categories.DeweyCode;
import com.dpapazisis.librarian.categories.Subject;

import java.time.Year;
import java.util.Objects;

public abstract class Readable {
    private String title;
    private Year year;
    private int pages;
    private int copies; //Might not be used like that
    protected final Subject subject;
    protected DeweyCode referenceCode;
    private boolean lendStatus;
    private String copyId = "00";   //TODO: Implement a way to generate the copy Id

    protected Readable(String title, Year year, int pages, Subject subject) {
        this.title = title;
        this.year = year;
        this.pages = pages;
        this.lendStatus = false;
        this.subject = subject;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = validYear(year);
    }

    private Year validYear(Year year) {
        if (year.getValue() > Year.now().getValue()) {
            throw new IllegalArgumentException("The Year of the Item can not be later than the current Year!");
        }
        return year;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = validPages(pages);
    }

    private int validPages(int pages) {
        if (pages <= 0) {
            throw new IllegalArgumentException("The items pages can not be zero or less!");
        }
        return pages;
    }

    //region Field is still in debate
    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }
    //endregion


    public Subject getSubject() {
        return subject;
    }

    public DeweyCode getReferenceCode() {
        return referenceCode;
    }

    protected abstract void setReferenceCode();

    public boolean isLend() {
        return lendStatus;
    }

    public void isReturned() {
        this.lendStatus = false;
    }

    public String getCopyId() {
        return copyId;
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
