package com.dpapazisis.librarian.model.readable;

import com.dpapazisis.librarian.categories.Classifier;
import com.dpapazisis.librarian.model.person.Author;
import com.dpapazisis.librarian.model.publisher.Publisher;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import static com.dpapazisis.librarian.model.readable.PublisherProtected.validateISBN;

public final class Book extends Readable implements PublisherProtected {
    private static final int MAX_AUTHORS = 5;

    private String isbn;
    private Publisher publisher;
    private final List<Author> authors;

    private Book(Builder builder) {
        super(builder.title, builder.year, builder.pages);
        this.isbn = builder.isbn;
        this.publisher = builder.publisher;
        this.authors = builder.authors;
        setReferenceCode();
    }

    @Override
    public String getISBN() {
        return this.isbn;
    }

    @Override
    public void setISBN(String isbn) {
        this.isbn = validateISBN(isbn);
    }

    @Override
    public Publisher getPublisher() {
        return this.publisher;
    }

    @Override
    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public boolean addAuthor(Author author) {
        if (authors.size() != MAX_AUTHORS) {
            authors.add(author);
            return true;
        } else {
            return false;
        }
    }

    public void removeAuthor(Author author) {
        if (authors.size() != 0) {
            authors.remove(author);
        }
    }

    public int getNumberOfAuthors() {
        return authors.size();
    }

    @Override
    public String toString() {
        return super.toString() +
                "isbn='" + isbn + '\'' +
                ", publisher=" + publisher +
                ", authors=" + authors;
    }

    @Override
    protected void setReferenceCode() {
        this.referenceCode = Classifier.generateDeweyCode(this);
    }

    public static class Builder extends ReadableBuilder {
        private String isbn;
        private Publisher publisher;
        private List<Author> authors = new ArrayList<>(5);

        public Builder(String title, Year year, int pages) {
            super(title, year, pages);
        }

        public Builder withISBN(String isbn) {
            this.isbn = validateISBN(isbn);
            return this;
        }

        public Builder withAuthors(List<Author> authors) throws IllegalArgumentException {
            if (authors.size() > MAX_AUTHORS) {
                throw new IllegalArgumentException("Authors should not exceed the number of 5!");
            }
            this.authors = authors;
            return this;
        }

        public Builder andPublisher(Publisher publisher) {
            this.publisher = publisher;
            return this;
        }

        @Override
        public Book build() {
            return new Book(this);
        }
    }
}
