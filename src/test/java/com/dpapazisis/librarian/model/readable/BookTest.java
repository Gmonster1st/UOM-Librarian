package com.dpapazisis.librarian.model.readable;

import com.dpapazisis.librarian.model.person.Author;
import com.dpapazisis.librarian.model.publisher.Publisher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BookTest {

    private Book book;

    @Before
    public void setup() {
        book = new Book.Builder("TestTitle", Year.parse("2020"), 500)
                .andPublisher(new Publisher())
                .build();
    }

    @After
    public void tearDown() {
        book = null;
    }

    @Test
    public void setYear_OnGivenAValidYear_ShouldChangeTheYear() {
        book.setYear(Year.parse("2005"));
        assertThat(book.getYear(), is(equalTo(Year.parse("2005"))));
    }

    @Test(expected = IllegalArgumentException.class)
    public void setYear_OnGivenInvalidYear_ShouldThrowIllegalArgumentException() {
        book.setYear(Year.parse("2025"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void setPages_OnGivenNegativeNumber_ShouldThrowIllegalArgumentException() {
        book.setPages(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setPages_OnGivenNumber0_ShouldThrowIllegalArgumentException() {
        book.setPages(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void onNewBookBuilder_GivenMoreAuthorsThan5_ThrowsIllegalArgumentException() {
        new Book.Builder("TestTitle", Year.parse("2010"), 100)
                .withAuthors(new ArrayList<>(Arrays.asList(
                        new Author(),
                        new Author(),
                        new Author(),
                        new Author(),
                        new Author(),
                        new Author()
                )))
                .build();
    }

    @Test
    public void onAddAuthor_GivenAuthorsListIsFull_ReturnsFalse() {
        for (int i = 0; i < 5; i++) {
            book.addAuthor(new Author());
        }
        assertThat(book.addAuthor(new Author()), is(false));
    }

    @Test
    public void getNumberOfAuthors_OnCalled_ReturnsTheNumberOfAuthors() {
        assertThat(book.getNumberOfAuthors(), is(0));
    }
}