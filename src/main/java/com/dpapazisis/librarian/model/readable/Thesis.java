package com.dpapazisis.librarian.model.readable;

import com.dpapazisis.librarian.categories.DeweyCode;

import java.time.Year;

public class Thesis extends Readable {
    protected Thesis(String title, Year year, int pages) {
        super(title, year, pages);
    }

    @Override
    protected void setReferenceCode(DeweyCode deweyCode) {

    }
}
