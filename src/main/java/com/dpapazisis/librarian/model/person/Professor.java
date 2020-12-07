/*
 * Copyright (c) 2020.
 * Dimitrios Papazisis
 * This Software is to be used for educational purposes only.
 * All rights Reserved.
 */

package com.dpapazisis.librarian.model.person;

import com.dpapazisis.librarian.utils.Validator;

import java.time.LocalDate;
//TODO:Documentation

public final class Professor extends Person {
    public Professor(String name, String surname, LocalDate birthDate, String shortBio, String email) {
        super(name, surname, birthDate, shortBio, Validator.email(email));
    }

    public Professor(String name, String surname, LocalDate birthDate) {
        super(name, surname, birthDate);
    }
}
