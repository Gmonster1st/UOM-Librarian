/*
 * Copyright (c) 2020.
 * Dimitrios Papazisis
 * This Software is to be used for educational purposes only.
 * All rights Reserved.
 */

package com.dpapazisis.librarian.categories;

import java.io.Serializable;
import java.util.Objects;

/**
 * Subject class defines an object that hold information for the Subject that a document involves.
 * It hold the name of the subject and the category code that corresponds to the Dewey Decimal system
 */
public class Subject implements Serializable {
    private final String name;
    private final String code;

    /**
     * Constructor that creates a Subject object that corresponds to a Dewey Category
     *
     * @param name {@link String} the name of the category
     * @param code {@link String} the main 3 digit code that reflects in the Dewey Decimal System
     */
    public Subject(String name, String code) {
        this.name = name;
        this.code = code;
    }

    /**
     * Returns the name of the category
     *
     * @return {@link String} the category
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the 3 digit code that reflects the category in the Dewey system
     *
     * @return {@link String} the 3 digit code
     */
    public String getCode() {
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return getName().equals(subject.getName()) && getCode().equals(subject.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getCode());
    }

    @Override
    public String toString() {
        return name;
    }
}
