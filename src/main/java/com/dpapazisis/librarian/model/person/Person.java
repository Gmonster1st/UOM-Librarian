/*
 * Copyright (c) 2020.
 * Dimitrios Papazisis
 * This Software is to be used for educational purposes only.
 * All rights Reserved.
 */

package com.dpapazisis.librarian.model.person;

import com.dpapazisis.librarian.utils.Validator;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Person is an abstract class that defines a person object. It is meant to be used as
 * a prototype for the subclasses that fit the Person profile
 */
public abstract class Person implements Serializable {
    private final String name;
    private final String surname;
    private final LocalDate birthDate;
    private final String shortBio;
    private final String email;

    /**
     * Constructor of a Person object that creates the full Person object
     *
     * @param name      {@link String} the Name of the Person
     * @param surname   {@link String} the Surname of the Person
     * @param birthDate {@link LocalDate} the date of birth of the Person
     * @param shortBio  {@link String} a short description of the Persons achievements
     * @param email     {@link String} the email of the Person
     * @throws IllegalArgumentException if the email given does not comply with the basic email format
     *                                  <p>Example: <pre>email@host.com</pre></p>
     */
    protected Person(String name, String surname, LocalDate birthDate, String shortBio, String email) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.shortBio = shortBio;
        this.email = Validator.email(email);
    }

    /**
     * Constructor of a Person object that creates the minimum required data of the Person object
     *
     * @param name      {@link String} the Name of the Person
     * @param surname   {@link String} the Surname of the Person
     * @param birthDate {@link LocalDate} the date of birth of the Person
     */
    protected Person(String name, String surname, LocalDate birthDate) {
        this(name, surname, birthDate, "", "example@email.com");
    }

    /**
     * Returns the name of the Person
     *
     * @return {@link String} the Name of the Person
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the Surname of the Person
     *
     * @return {@link String} the Surname of the Person
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Returns the date of birth of the Person
     *
     * @return {@link LocalDate} the date of birth of the Person
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * Returns the short Description of a Persons achievements
     *
     * @return {@link String} a short description of the Persons achievements
     */
    public String getShortBio() {
        return shortBio;
    }

    /**
     * Returns the email address of the Person
     *
     * @return {@link String} the email of the Person
     */
    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return getName().equals(person.getName()) && getSurname().equals(person.getSurname()) && getBirthDate().equals(person.getBirthDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getSurname(), getBirthDate());
    }

    @Override
    public String toString() {
        return "{" + "name: '" + name + '\'' +
                ", surname: '" + surname + '\'' +
                ", birthDate: " + birthDate +
                ", shortBio: '" + shortBio + '\'' +
                ", email: '" + email + '\'';
    }
}
