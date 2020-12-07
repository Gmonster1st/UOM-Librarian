/*
 * Copyright (c) 2020.
 * Dimitrios Papazisis
 * This Software is to be used for educational purposes only.
 * All rights Reserved.
 */

package com.dpapazisis.librarian.model.readable;

import com.dpapazisis.librarian.model.publisher.Publisher;

import java.util.regex.Pattern;

/**
 * An Interface for describing some methods, that can be used to specify
 * the functionality of an object, that needs to hold data for the types of
 * ISBN code and {@link Publisher}.
 * Also a static method that validates the ISBN type
 */
public interface PublisherProtected {

    /**
     * Returns a {@link String} that is the ISBN code of a Readable
     *
     * @return {@link String} that is the ISBN code
     */
    String getISBN();

    /**
     * Sets the ISBN code of the Readable object
     *
     * @param isbn {@link String} the ISBN code
     */
    void setISBN(String isbn);

    /**
     * Returns the {@link Publisher} Object that is referenced in the Readable
     *
     * @return {@link Publisher} the Publisher of the Readable
     */
    Publisher getPublisher();

    /**
     * Sets the {@link Publisher} object to be referenced in the Readable
     *
     * @param publisher {@link Publisher} a Publisher object
     */
    void setPublisher(Publisher publisher);

    /**
     * Static method to be used for validating the format of an ISBN code.
     * It handles 10-digit ISBN format - 1970 as international standard ISO 2108
     * and 13-digit ISBN format - 2007
     *
     * @param isbn {@link String} the ISBN code
     * @return {@link String} the accepted code
     * @throws IllegalArgumentException if the code does not comply with the 2 formats of 10 and 13 digit
     */
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
