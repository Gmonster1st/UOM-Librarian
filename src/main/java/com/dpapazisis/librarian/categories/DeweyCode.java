/*
 * Copyright (c) 2020.
 * Dimitrios Papazisis
 * This Software is to be used for educational purposes only.
 * All rights Reserved.
 */

package com.dpapazisis.librarian.categories;

import java.util.Objects;
//TODO:Documentation

public class DeweyCode {
    private final String category;
    private final String referenceCode;

    protected DeweyCode(String category, String referenceCode) {
        this.category = category;
        this.referenceCode = referenceCode;
    }

    public String getCategory() {
        return category;
    }

    public String getReferenceCode() {
        return referenceCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeweyCode deweyCode = (DeweyCode) o;
        return getCategory().equals(deweyCode.getCategory()) && getReferenceCode().equals(deweyCode.getReferenceCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCategory(), getReferenceCode());
    }

    @Override
    public String toString() {
        return "category='" + category + '\'' +
                ", referenceCode='" + referenceCode + '\'';
    }
}
