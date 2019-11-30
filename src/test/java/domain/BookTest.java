package domain;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class BookTest {

    Book book;

    @Before
    public void setUp() {
        book = new Book("Robert Martin", "Clean Code: A Handbook of Agile Software Craftsmanship", "978-0132350884", "Ohjelmointi, design patterns", "TKT20006 Ohjelmistotuotanto");
    }

    @Test
    public void constructorCreatesNewBook() {
        String string = book.getInfo();
        String expected = "Kirjoittaja: Robert Martin" + "<br/>"
                + "Otsikko: Clean Code: A Handbook of Agile Software Craftsmanship" + "<br/>"
                + "Tyyppi: " + "Kirja" + "<br/>"
                + "ISBN: 978-0132350884" + "<br/>"
                + "Tagit: Ohjelmointi, design patterns" + "<br/>"
                + "Kommentti: TKT20006 Ohjelmistotuotanto";

        assertEquals(expected, string);
    }

    @Test
    public void bookHasCorrectType() {
        assertEquals(ItemType.BOOK, book.getType());
    }
}
