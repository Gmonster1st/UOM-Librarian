/*
 * Copyright (c) 2020.
 * Dimitrios Papazisis
 * This Software is to be used for educational purposes only.
 * All rights Reserved.
 */

package com.dpapazisis.librarian.services;

/**
 * Interface that implemented Observer and applies the functionality of
 * receiving events from the {@link LibraryService} class
 */
public interface LibraryObserver {
    /**
     * Update event
     *
     * @param action {@link LibraryAction} enum
     */
    void update(LibraryAction action);
}
