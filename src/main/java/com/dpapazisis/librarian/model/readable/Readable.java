package com.dpapazisis.librarian.model.readable;

import com.dpapazisis.librarian.categories.DeweyCode;

import java.time.Year;
import java.util.Objects;

public abstract class Readable {
    private String title;
    private Year year;
    private int pages;
    private int copies; //Might not be used like that
    protected DeweyCode referenceCode;
    private boolean lendStatus;

    protected Readable(String title, Year year, int pages) {
        this.title = title;
        this.year = year;
        this.pages = pages;
        this.lendStatus = false;
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
        this.year = year;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    //region Field is still in debate
    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }
    //endregion

    public DeweyCode getReferenceCode() {
        return referenceCode;
    }

    protected abstract void setReferenceCode(DeweyCode deweyCode);

    public boolean isLend() {
        return lendStatus;
    }

    public void isReturned() {
        this.lendStatus = false;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Readable readable = (Readable) o;
//        return getTitle().equals(readable.getTitle()) &&
//                getYear().equals(readable.getYear()) &&
//                getReferenceCode().equals(readable.getReferenceCode());
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(getTitle(), getYear(), getReferenceCode());
//    }


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
