/*
 * Copyright (c) 2020.
 * Dimitrios Papazisis
 * This Software is to be used for educational purposes only.
 * All rights Reserved.
 */

package com.dpapazisis.librarian.utils;

import java.util.regex.Pattern;

/**
 * Validator class is a static utility class that is used for validation purposes
 */
public final class Validator {
    private Validator() {
    }

    /**
     * Email format validator
     *
     * @param email {@link String} the email address for validation
     * @return {@link String} the email as is
     * @throws IllegalArgumentException if the does not address of an emails address
     *                                  <p>Example: <pre>email@host.com</pre></p>
     */
    public static String email(String email) {
        String emailPattern = "^(.+)@(.+)\\.(.+)$";
        if (Pattern.matches(emailPattern, email)) {
            return email;
        } else {
            throw new IllegalArgumentException("You need to put a valid Email format!");
        }
    }
}
