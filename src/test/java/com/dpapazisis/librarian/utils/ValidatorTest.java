package com.dpapazisis.librarian.utils;

import org.junit.Test;

import static com.dpapazisis.librarian.utils.Validator.email;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ValidatorTest {
    @Test
    public void validEmail_OnGivenValidFormat_ReturnsTheSameString() {
        assertThat("test@test.com", is(email("test@test.com")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void validEmail_OnGivenInvalidFormat1_ThrowsIllegalArgumentException() {
        email("bad@email");
    }

    @Test(expected = IllegalArgumentException.class)
    public void validEmail_OnGivenInvalidFormat2_ThrowsIllegalArgumentException() {
        email("@bademail.com");
    }

    @Test(expected = IllegalArgumentException.class)
    public void validEmail_OnGivenInvalidFormat3_ThrowsIllegalArgumentException() {
        email("bademail.com");
    }

    @Test(expected = IllegalArgumentException.class)
    public void validEmail_OnGivenInvalidFormat4_ThrowsIllegalArgumentException() {
        email("bademail");
    }

    @Test(expected = IllegalArgumentException.class)
    public void validEmail_OnGivenInvalidFormat5_ThrowsIllegalArgumentException() {
        email("");
    }

}