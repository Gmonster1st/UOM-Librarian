/*
 * Copyright (c) 2020.
 * Dimitrios Papazisis
 * This Software is to be used for educational purposes only.
 * All rights Reserved.
 */

package com.dpapazisis.librarian.model.person;

import com.dpapazisis.librarian.utils.Validator;

import java.time.LocalDate;

/**
 * Professor class that extends {@link Person} is a masking class that just defines a person as a Professor
 */
public final class Professor extends Person {

    /**
     * Constructor that created an Professor object with the complete data of a person
     * as defined in {@link Person} superclass
     *
     * @param name      {@link String} the name of the Professor
     * @param surname   {@link String} the surname of the Professor
     * @param birthDate {@link LocalDate} the date of birth of the Professor
     * @param shortBio  {@link String} a short description of the Professors achievements
     * @param email     {@link String} the email address of the Professor
     * @throws IllegalArgumentException if the email address does not comply with the standard email format
     *                                  <p>Example: <pre>email@host.com</pre></p>
     */
    public Professor(String name, String surname, LocalDate birthDate, String shortBio, String email) {
        super(name, surname, birthDate, shortBio, Validator.email(email));
    }

    /**
     * Constructor that created an Professor object with the minimum data as defined in {@link Person} superclass
     *
     * @param name      {@link String} the name of the Professor
     * @param surname   {@link String} the surname of the Professor
     * @param birthDate {@link LocalDate} the date of birth of the Professor
     */
    public Professor(String name, String surname, LocalDate birthDate) {
        super(name, surname, birthDate);
    }
}
