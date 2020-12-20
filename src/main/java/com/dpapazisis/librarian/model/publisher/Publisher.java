/*
 * Copyright (c) 2020.
 * Dimitrios Papazisis
 * This Software is to be used for educational purposes only.
 * All rights Reserved.
 */

package com.dpapazisis.librarian.model.publisher;

import com.dpapazisis.librarian.utils.Validator;

import java.io.Serializable;
import java.net.URL;
import java.util.Objects;

/**
 * Publisher class that correspond to a publisher of any type of Readable object
 */
public class Publisher implements Serializable {
    private final String name;
    private URL website;
    private String email;

    /**
     * Constructor of a Publisher object
     *
     * @param name    {@link String} the name of the Publisher
     * @param website {@link URL} the website of the Publisher
     * @param email   {@link String} the email of the Publisher
     * @throws IllegalArgumentException if the does not address of an emails address
     *                                  <p>Example: <pre>email@host.com</pre></p>
     */
    public Publisher(String name, URL website, String email) {
        this.name = name;
        this.website = website;
        this.email = Validator.email(email);
    }

    /**
     * Returns the name of the Publisher object
     *
     * @return {@link String} the name of the Publisher
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the {@link URL} that represents the website address of the Publisher
     *
     * @return {@link URL} the website address
     */
    public URL getWebsite() {
        return website;
    }

    /**
     * Sets the {@link URL} address for the Publishers website
     *
     * @param website {@link URL} the website address
     */
    public void setWebsite(URL website) {
        this.website = website;
    }

    /**
     * Returns a {@link String} that represents the email address of the Publisher
     *
     * @return {@link String} the email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the Publisher
     *
     * @param email {@link String} the email address
     * @throws IllegalArgumentException if the does not address of an emails address
     *                                  <p>Example: <pre>email@host.com</pre></p>
     */
    public void setEmail(String email) {
        this.email = Validator.email(email);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Publisher)) return false;
        Publisher publisher = (Publisher) o;
        return getName().equals(publisher.getName()) && Objects.equals(getWebsite(),
                publisher.getWebsite()) && Objects.equals(getEmail(),
                publisher.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getWebsite(), getEmail());
    }

    @Override
    public String toString() {
        return name;
    }
}
