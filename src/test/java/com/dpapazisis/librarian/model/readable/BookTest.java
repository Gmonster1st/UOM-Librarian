package com.dpapazisis.librarian.model.readable;

import com.dpapazisis.librarian.model.person.Author;
import com.dpapazisis.librarian.model.publisher.Publisher;
import org.junit.*;

import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

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
    public void getTitle_OnCalled_ReturnsString() {
        assertThat(book.getTitle(), is("TestTitle"));
    }

    @Test
    public void setTitle_OnCalled_ShouldChangeTheTitleString() {
        book.setTitle("TestSetTitle");
        assertThat(book.getTitle(), is("TestSetTitle"));
    }

    @Test
    public void getYear_OnCalled_ReturnsYear() {
        assertThat(book.getYear(), is(equalTo(Year.parse("2020"))));
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
                .authors(new ArrayList<>(Arrays.asList(
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
    public void getNumberOfAuthors_OnCalled_ReturnsTheNumberOfAuthors(){
        assertThat(book.getNumberOfAuthors(), is(0));
    }

    @Test
    public void getISBN() {
        assertThat(book.getISBN(), is("978-3-16-148410-0"));
    }

    @Test
    public void setISBN_OnGivenValidFormat_SetTheISBN() {
        book.setISBN("978-3-16-148410-0");
        assertThat(book.getISBN(), is("978-3-16-148410-0"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void setISBN_OnGivenInvalidFormat_ThrowsIllegalArgumentException() {
        book.setISBN("978-3-16-148410-0");
    }

    @Test
    public void getPublisher() {
        Assert.fail();
    }

    @Test
    public void setPublisher() {
        Assert.fail();
    }
}