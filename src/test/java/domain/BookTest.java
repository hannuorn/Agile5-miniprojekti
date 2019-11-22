
import domain.Book;

import org.junit.Test;
import static org.junit.Assert.*;

public class BookTest {

    @Test
    public void constructorCreatesNewBook() {
        Book book = new Book("Robert Martin", "Clean Code: A Handbook of Agile Software Craftsmanship", "978-0132350884", "Ohjelmointi, design patterns", "TKT20006 Ohjelmistotuotanto");

        String string = book.getInfo();
        String expected = "Kirjoittaja: Robert Martin" + "\n"
                + "Otsikko: Clean Code: A Handbook of Agile Software Craftsmanship" + "\n"
                + "Tyyppi: " + "kirja" + "\n"
                + "ISBN: 978-0132350884" + "\n"
                + "Tagit: Ohjelmointi, design patterns" + "\n"
                + "Kommentti: TKT20006 Ohjelmistotuotanto";

        assertEquals(expected, string);
    }
}