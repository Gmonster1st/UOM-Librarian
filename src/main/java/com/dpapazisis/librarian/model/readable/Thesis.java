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

/**
 * Thesis type that extends {@link Readable} and hold the data for a Thesis object
 * Use of Thesis constructor is restricted please utilize the inner {@link Thesis.Builder} class.
 * <p>
 * Usage example:<br>
 * <pre>
 * <code>new Thesis.Builder(title,year,pages,subject).build();</code>
 * </pre>
 * </p>
 */
public final class Thesis extends Readable {
    private Author author;
    private Professor supervisor;
    private ThesisType type;
    private String university;
    private String department;

    /**
     * Usage of the Constructor is restricted, please use the inner {@link Thesis.Builder} class
     *
     * @param builder {@link Thesis.Builder}
     *                <p>Usage example:<br>
     *                <pre><code>new Thesis.Builder(title,year,pages,subject).build();</code></pre>
     *                </p>
     */
    private Thesis(Builder builder) {
        super(builder.title, builder.year, builder.pages, builder.subject);
        this.author = builder.author;
        this.supervisor = builder.supervisor;
        this.type = builder.type;
        this.university = builder.university;
        this.department = builder.department;
        setReferenceCode();
    }

    /**
     * Returns the {@link Author} object of the Thesis object
     *
     * @return {@link Author} the Author object
     */
    public Author getAuthor() {
        return author;
    }

    /**
     * Sets the {@link Author} object for this Thesis object
     *
     * @param author {@link Author} the Author object to be set
     */
    public void setAuthor(Author author) {
        this.author = author;
    }

    /**
     * Returns the {@link Professor} object that specifies the supervisor of this Thesis object
     *
     * @return {@link Professor} the Thesis supervisor
     */
    public Professor getSupervisor() {
        return supervisor;
    }

    /**
     * Sets the {@link Professor} that corresponds to the Thesis supervisor
     *
     * @param supervisor {@link Professor} the supervisor to be set
     */
    public void setSupervisor(Professor supervisor) {
        this.supervisor = supervisor;
    }

    /**
     * Returns the enum {@link ThesisType} of the Thesis object
     *
     * @return {@link ThesisType} the Type of Thesis
     */
    public ThesisType getType() {
        return type;
    }

    /**
     * Sets the enum {@link ThesisType} for the Thesis Object
     *
     * @param type {@link ThesisType} the Type of Thesis
     */
    public void setType(ThesisType type) {
        this.type = type;
    }

    /**
     * Returns a {@link String} that is the name of the University that the Thesis belongs to
     *
     * @return {@link String} the University name
     */
    public String getUniversity() {
        return university;
    }

    /**
     * Sets the name of the University that the Thesis belongs to
     *
     * @param university {@link String} the name of the University
     */
    public void setUniversity(String university) {
        this.university = university;
    }

    /**
     * Returns the department name of which the Thesis was created for
     *
     * @return {@link String} the name of the Department
     */
    public String getDepartment() {
        return department;
    }

    /**
     * Sets the department name for which the Thesis was created for
     *
     * @param department {@link String} the name of the Department
     */
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

    /**
     * Inner Builder class that extends {@link ReadableBuilder}
     * that builds {@link Thesis} objects
     */
    public static class Builder extends ReadableBuilder {
        private Author author;
        private Professor supervisor;
        private ThesisType type;
        private String university;
        private String department;

        /**
         * Constructor of the Builder that requires the minimum data
         * for creating a {@link Thesis} object
         *
         * @param title   {@link String} the title of the Book
         * @param year    {@link Year} the year of the Book
         * @param pages   <tt>int</tt> the number of pages
         * @param subject {@link Subject} the subject of the Book
         */
        public Builder(String title, Year year, int pages, Subject subject) {
            super(title, year, pages, subject);
        }

        /**
         * Adds the {@link Author} object to the {@link Thesis} object to be created
         *
         * @param author {@link Author} the Author of the Book
         * @return {@link Thesis.Builder}
         */
        public Builder withAuthor(Author author) {
            this.author = author;
            return this;
        }

        /**
         * Adds the {@link Professor} object that specifies the supervisor of the {@link Thesis}
         * object to be created
         *
         * @param professor {@link Professor} the Professor(supervisor)
         * @return {@link Thesis.Builder}
         */
        public Builder withSupervisor(Professor professor) {
            this.supervisor = professor;
            return this;
        }


        /**
         * Sets the {@link ThesisType} enum that specifies the Type of Thesis to be created
         *
         * @param type {@link ThesisType} the Thesis Type
         * @return {@link Thesis.Builder}
         */
        public Builder ofType(ThesisType type) {
            this.type = type;
            return this;
        }

        /**
         * Adds the name of the University that the Thesis belongs to
         *
         * @param university {@link String} the name of the University
         * @return {@link Thesis.Builder}
         */
        public Builder fromUniversity(String university) {
            this.university = university;
            return this;
        }

        /**
         * Adds the name of the Department that the Thesis was created for
         *
         * @param department {@link String} the name of the Department
         * @return {@link Thesis.Builder}
         */
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
