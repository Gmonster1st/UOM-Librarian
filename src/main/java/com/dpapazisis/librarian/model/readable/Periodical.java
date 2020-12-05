package com.dpapazisis.librarian.model.readable;

import com.dpapazisis.librarian.categories.Classifier;
import com.dpapazisis.librarian.categories.Subject;
import com.dpapazisis.librarian.model.publisher.Publisher;

import java.time.Year;
import java.util.Objects;

import static com.dpapazisis.librarian.model.readable.PublisherProtected.validateISBN;

public final class Periodical extends Readable implements PublisherProtected {
    private String isbn;
    private Publisher publisher;
    private int volume;
    private int issue;

    private Periodical(Builder builder) {
        super(builder.title, builder.year, builder.pages, builder.subject );
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

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getIssue() {
        return issue;
    }

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
                "isbn='" + isbn + '\'' +
                ", publisher=" + publisher +
                ", volume=" + volume +
                ", issue=" + issue;
    }

    public static class Builder extends ReadableBuilder {
        private String isbn;
        private Publisher publisher;
        private int volume;
        private int issue;

        public Builder(String title, Year year, int pages, Subject subject) {
            super(title, year, pages, subject);
        }

        public Builder withISBN(String isbn) {
            this.isbn = validateISBN(isbn);
            return this;
        }

        public Builder andPublisher(Publisher publisher) {
            this.publisher = publisher;
            return this;
        }

        public Builder isVolume(int volume) {
            this.volume = volume;
            return this;
        }

        public Builder isIssue(int issue) {
            this.issue = issue;
            return this;
        }

        @Override
        public Periodical build() {
            return new Periodical(this);
        }
    }
}
