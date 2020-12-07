/*
 * Copyright (c) 2020.
 * Dimitrios Papazisis
 * This Software is to be used for educational purposes only.
 * All rights Reserved.
 */

package com.dpapazisis.librarian.utils;

import java.util.regex.Pattern;
//TODO:Documentation

public final class Validator {
    private Validator() {
    }

    public static String email(String email) {
        String emailPattern = "^(.+)@(.+)\\.(.+)$";
        if (Pattern.matches(emailPattern, email)) {
            return email;
        } else {
            throw new IllegalArgumentException("You need to put a valid Email format!");
        }
    }
}
