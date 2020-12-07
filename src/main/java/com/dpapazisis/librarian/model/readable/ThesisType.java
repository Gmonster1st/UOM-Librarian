/*
 * Copyright (c) 2020.
 * Dimitrios Papazisis
 * This Software is to be used for educational purposes only.
 * All rights Reserved.
 */

package com.dpapazisis.librarian.model.readable;

/**
 * The types of Thesis that can be declared
 */
public enum ThesisType {
    GRADUATE {
        @Override
        public String toString() {
            return "Graduate";
        }
    },
    POST_GRADUATE {
        @Override
        public String toString() {
            return "Post-Graduate";
        }
    },
    DOCTORAL {
        @Override
        public String toString() {
            return "Doctoral";
        }
    }
}
