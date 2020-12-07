/*
 * Copyright (c) 2020.
 * Dimitrios Papazisis
 * This Software is to be used for educational purposes only.
 * All rights Reserved.
 */

package com.dpapazisis.librarian.model.publisher;

import com.dpapazisis.librarian.utils.Validator;

import java.net.URL;
import java.util.Objects;
//TODO:Documentation

public class Publisher {
    private final String name;
    private URL website;
    private String email;

    public Publisher(String name, URL website, String email) {
        this.name = name;
        this.website = website;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public URL getWebsite() {
        return website;
    }

    public void setWebsite(URL website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

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
        return "name='" + name + '\'' +
                ", website=" + website +
                ", email='" + email + '\'';
    }
}
