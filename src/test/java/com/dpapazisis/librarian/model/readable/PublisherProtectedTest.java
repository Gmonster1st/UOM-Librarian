package com.dpapazisis.librarian.model.readable;

import org.junit.Test;

import static com.dpapazisis.librarian.model.readable.PublisherProtected.validateISBN;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PublisherProtectedTest {

    @Test
    public void setISBN_OnGivenValid13DigitFormat_SetTheISBN() {
        assertThat(validateISBN("978-3-16-148410-0"), is("978-3-16-148410-0"));
    }

    @Test
    public void setISBN_OnGivenValid10DigitFormat_SetTheISBN() {
        assertThat(validateISBN("0-19-852663-6"), is("0-19-852663-6"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void setISBN_OnGivenInvalidFormat_ThrowsIllegalArgumentException() {
        validateISBN("978-3-16-14410-0");
    }
}