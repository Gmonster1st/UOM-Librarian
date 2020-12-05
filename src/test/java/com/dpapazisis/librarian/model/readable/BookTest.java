package com.dpapazisis.librarian.model.readable;

import com.dpapazisis.librarian.categories.Subject;
import com.dpapazisis.librarian.model.person.Author;
import com.dpapazisis.librarian.model.publisher.Publisher;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BookTest {

    private Book book;
    private static Subject subject;

    @BeforeClass
    public static void setupForAll() {
        subject = new Subject("ComputerScience", "100");
    }

    @Before
    public void setup() {
        URL url = null;
        try {
            url = new URL("http://www.example.com");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        book = new Book.Builder("TestTitle", Year.parse("2020"), 500, subject)
                .andPublisher(new Publisher("Editions", url, "edit@myeditions.com"))
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
        new Book.Builder("TestTitle", Year.parse("2010"), 100, subject)
                .withAuthors(new ArrayList<>(Arrays.asList(
                        new Author("TestName1", "Surname1", LocalDate.parse("1982-12-29")),
                        new Author("TestName2", "Surname2", LocalDate.parse("1980-12-29")),
                        new Author("TestName3", "Surname3", LocalDate.parse("1983-12-29")),
                        new Author("TestName4", "Surname4", LocalDate.parse("1942-12-29")),
                        new Author("TestName5", "Surname5", LocalDate.parse("1982-12-25")),
                        new Author("TestName6", "Surname6", LocalDate.parse("1986-12-26"))
                )))
                .build();
    }

    @Test
    public void onAddAuthor_GivenAuthorsListIsFull_ReturnsFalse() {
        for (int i = 0; i < 5; i++) {
            book.addAuthor(new Author("TestName" + i, "Surname" + i, LocalDate.parse("198" + i + "-12-29")));
        }
        assertThat(book.addAuthor(new Author("TestName6", "Surname6", LocalDate.parse("1986-12-29"))), is(false));
    }

    @Test
    public void getNumberOfAuthors_OnCalled_ReturnsTheNumberOfAuthors() {
        assertThat(book.getNumberOfAuthors(), is(0));
    }
}