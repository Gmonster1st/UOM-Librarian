package com.dpapazisis.librarian.model.readable;

import com.dpapazisis.librarian.model.publisher.Publisher;

public class Book extends Readable implements PublisherProtected {
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
}
