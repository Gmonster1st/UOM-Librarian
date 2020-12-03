package com.dpapazisis.librarian.model.readable;

import com.dpapazisis.librarian.model.publisher.Publisher;

public interface PublisherProtected {
    String getISBN();

    void setISBN(String isbn);

    Publisher getPublisher();

    void setPublisher(Publisher publisher);

    static String validateISBN(String isbn) throws IllegalArgumentException {
        //TODO: isbn validation should be for both 13 and 10 digit formats
        //        TODO:Logic Implementation

        if (isbn.equals("")) {
            return isbn;
        } else {
            throw new IllegalArgumentException("ISBN should match the 10 or 13 digit formats!");
        }
    }
}
