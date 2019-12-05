
package dataAccess;

import domain.Book;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class BookFinderTest {
    BookFinder bookFinder;
    public BookFinderTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        bookFinder = new BookFinder();
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    
    @Test
    public void bookFinderReturnsCorrectTitle() {
        Book book = null;
        try {
            book = bookFinder.findBookByISBN("9780321573513");
        } catch (Exception ex) {
            Logger.getLogger(BookFinderTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        assertEquals(book.getTitle(), "Algorithms");
    }
}
