package com.dpapazisis.librarian.categories;

import java.util.Objects;

public class Subject {
    private final String name;
    private final String code;

    public Subject(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

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
        return "name='" + name + '\'' +
                ", code='" + code + '\'';
    }
}
