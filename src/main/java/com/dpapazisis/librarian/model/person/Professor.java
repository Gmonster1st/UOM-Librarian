package com.dpapazisis.librarian.model.person;

import com.dpapazisis.librarian.utils.Validator;

import java.time.LocalDate;

public final class Professor extends Person {
    public Professor(String name, String surname, LocalDate birthDate, String shortBio, String email) {
        super(name, surname, birthDate, shortBio, Validator.email(email));
    }

    public Professor(String name, String surname, LocalDate birthDate) {
        super(name, surname, birthDate);
    }
}
