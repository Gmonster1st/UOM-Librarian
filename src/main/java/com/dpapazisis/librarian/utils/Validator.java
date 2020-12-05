package com.dpapazisis.librarian.utils;

import java.util.regex.Pattern;

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
