package com.dpapazisis.librarian.categories;

import com.dpapazisis.librarian.model.person.Author;
import com.dpapazisis.librarian.model.publisher.Publisher;
import com.dpapazisis.librarian.model.readable.Book;
import com.dpapazisis.librarian.model.readable.Periodical;
import com.dpapazisis.librarian.model.readable.Thesis;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Year;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ClassifierTest {
    private static Subject subject;
    private static Author testAuthor;
    private static Publisher publisher;
    private static final String TITLE = "TestTitle";
    private static final Year YEAR = Year.parse("2020");
    private static final int PAGES = 500;

    @BeforeClass
    public static void setup() {
        URL url = null;
        try {
            url = new URL("http://www.example.com");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        publisher = new Publisher("Editions", url, "edit@myeditions.com");

        subject = new Subject("ComputerScience", "100");

        testAuthor = new Author("Dimitrios", "Papazisis", LocalDate.parse("1982-12-29"));
    }

    @Test
    public void generateDeweyCode_ForBook() {
        var book = new Book.Builder(TITLE, YEAR, PAGES, subject)
                .withAuthor(testAuthor)
                .andPublisher(publisher)
                .build();

        assertThat(Classifier.generateDeweyCode(book),
                is(equalTo(new DeweyCode(subject.getName(), "100.00 DIP"))));
    }

    @Test
    public void generateDeweyCode_ForPeriodical() {
        var periodical = new Periodical.Builder(TITLE, YEAR, PAGES, subject)
                .andPublisher(publisher)
                .build();

        assertThat(Classifier.generateDeweyCode(periodical),
                is(equalTo(new DeweyCode(subject.getName(), "100.00 TEE"))));
    }

    @Test
    public void generateDeweyCode_ForThesis() {
        var thesis = new Thesis.Builder(TITLE, YEAR, PAGES, subject)
                .withAuthor(testAuthor)
                .build();

        assertThat(Classifier.generateDeweyCode(thesis),
                is(equalTo(new DeweyCode(subject.getName(), "100.00 DIP"))));
    }

    @Test
    public void generateDeweyCode_WhenMultipleCopies_GeneratesProperCode() {
        var magazines = new Periodical.Builder(TITLE, YEAR, PAGES, subject)
                .andPublisher(publisher)
                .build(3);

        var magazinesIterator = magazines.stream().iterator();
        int i = 0;
        while (magazinesIterator.hasNext()) {
            var magazine = magazinesIterator.next();
            System.out.println(magazine.getReferenceCode().toString());
            assertThat(magazine.getReferenceCode().getReferenceCode(), is(equalTo("100.0" + i++ + " TEE")));
        }
    }
}