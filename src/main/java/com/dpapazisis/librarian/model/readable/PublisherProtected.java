package com.dpapazisis.librarian.model.readable;

import com.dpapazisis.librarian.model.publisher.Publisher;

import java.util.regex.Pattern;

public interface PublisherProtected {
    String getISBN();

    void setISBN(String isbn);

    Publisher getPublisher();

    void setPublisher(Publisher publisher);

    static String validateISBN(String isbn) {
        String tenDigitPattern = "^(\\d)-(\\d{2})-(\\d{6})-(\\d)$";
        String thirteenDigitPattern = "^(\\d{3})-(\\d)-(\\d{2})-(\\d{6})-(\\d)$";

        if (Pattern.matches(tenDigitPattern, isbn)) {
            return isbn;
        } else if (Pattern.matches(thirteenDigitPattern, isbn)) {
            return isbn;
        } else {
            throw new IllegalArgumentException("ISBN should match the 10 or 13 digit formats!");
        }
    }
}
