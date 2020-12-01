package com.dpapazisis.librarian.model.readable;

import com.dpapazisis.librarian.categories.DeweyCode;
import com.dpapazisis.librarian.model.publisher.Publisher;

import java.time.Year;

public class Periodical extends Readable implements PublisherProtected {
    protected Periodical(String title, Year year, int pages) {
        super(title, year, pages);
    }

    @Override
    public String getISBN() {
        return null;
    }

    @Override
    public void setISBN(String isbn) {

    }

    @Override
    public Publisher getPublisher() {
        return null;
    }

    @Override
    public void setPublisher(Publisher publisher) {

    }

    @Override
    protected void setReferenceCode(DeweyCode deweyCode) {

    }
}
