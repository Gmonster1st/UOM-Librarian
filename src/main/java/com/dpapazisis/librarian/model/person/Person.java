/*
 * Copyright (c) 2020.
 * Dimitrios Papazisis
 * This Software is to be used for educational purposes only.
 * All rights Reserved.
 */

package com.dpapazisis.librarian.model.person;

import java.time.LocalDate;
import java.util.Objects;
//TODO:Documentation

public abstract class Person {
    private final String name;
    private final String surname;
    private final LocalDate birthDate;
    private final String shortBio;
    private final String email;

    protected Person(String name, String surname, LocalDate birthDate, String shortBio, String email) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.shortBio = shortBio;
        this.email = email;
    }

    protected Person(String name, String surname, LocalDate birthDate) {
        this(name, surname, birthDate, "", "");
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getShortBio() {
        return shortBio;
    }

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
        return "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthDate=" + birthDate +
                ", shortBio='" + shortBio + '\'' +
                ", email='" + email + '\'';
    }
}
