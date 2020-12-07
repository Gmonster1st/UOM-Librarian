/*
 * Copyright (c) 2020.
 * Dimitrios Papazisis
 * This Software is to be used for educational purposes only.
 * All rights Reserved.
 */

package com.dpapazisis.librarian.model.readable;

import com.dpapazisis.librarian.categories.Classifier;
import com.dpapazisis.librarian.categories.Subject;
import com.dpapazisis.librarian.model.person.Author;
import com.dpapazisis.librarian.model.person.Professor;

import java.time.Year;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
//TODO:Documentation

public final class Thesis extends Readable {
    private Author author;
    private Professor supervisor;
    private ThesisType type;
    private String university;
    private String department;

    private Thesis(Builder builder) {
        super(builder.title, builder.year, builder.pages, builder.subject);
        this.author = builder.author;
        this.supervisor = builder.supervisor;
        this.type = builder.type;
        this.university = builder.university;
        this.department = builder.department;
        setReferenceCode();
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Professor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Professor supervisor) {
        this.supervisor = supervisor;
    }

    public ThesisType getType() {
        return type;
    }

    public void setType(ThesisType type) {
        this.type = type;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    protected void setReferenceCode() {
        this.referenceCode = Classifier.generateDeweyCode(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Thesis thesis = (Thesis) o;
        return getAuthor().equals(thesis.getAuthor()) && getSupervisor().equals(thesis.getSupervisor()) && getType() == thesis.getType() && getUniversity().equals(thesis.getUniversity()) && getDepartment().equals(thesis.getDepartment());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getAuthor(), getSupervisor(), getType(), getUniversity(), getDepartment());
    }

    @Override
    public String toString() {
        return super.toString() +
                "author=" + author +
                ", supervisor=" + supervisor +
                ", type=" + type.name().toLowerCase(Locale.ROOT) +
                ", department='" + department + '\'' +
                ", university='" + university + '\'';
    }

    public static class Builder extends ReadableBuilder {
        private Author author;
        private Professor supervisor;
        private ThesisType type;
        private String university;
        private String department;

        public Builder(String title, Year year, int pages, Subject subject) {
            super(title, year, pages, subject);
        }

        public Builder withAuthor(Author author) {
            this.author = author;
            return this;
        }

        public Builder withSupervisor(Professor professor) {
            this.supervisor = professor;
            return this;
        }

        public Builder ofType(ThesisType type) {
            this.type = type;
            return this;
        }

        public Builder fromUniversity(String university) {
            this.university = university;
            return this;
        }

        public Builder andDepartment(String department) {
            this.department = department;
            return this;
        }

        @Override
        public Thesis build() {
            return new Thesis(this);
        }

        @Override
        public List<Readable> build(int copies) {
            List<Readable> multipleCopies = new LinkedList<>();
            for (int i = 0; i < copies; i++) {
                var thesis = new Thesis(this);
                thesis.setCopyId((i < 10) ? "0" + i : String.valueOf(i));
                thesis.setReferenceCode();
                multipleCopies.add(thesis);
            }
            return multipleCopies;
        }
    }
}
