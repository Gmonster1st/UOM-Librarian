/*
 * Copyright (c) 2020.
 * Dimitrios Papazisis
 * This Software is to be used for educational purposes only.
 * All rights Reserved.
 */

package com.dpapazisis.librarian.services;

import com.dpapazisis.librarian.model.readable.Readable;
import com.dpapazisis.librarian.repository.Repository;

//TODO: Implement this class
public class LibraryService {
    private Repository repository;

    public LibraryService() {
        repository = Repository.getInstance();
    }

    public void addNewReadable(Readable readable){

    }



}
