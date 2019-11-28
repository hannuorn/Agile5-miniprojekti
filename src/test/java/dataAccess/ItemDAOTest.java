package dataAccess;

import dataAccess.MemoryItemDAO;
import domain.Book;
import org.junit.Test;
import static org.junit.Assert.*;

public class ItemDAOTest {
    
    @Test
    public void createAddsBookToList() {
        
        MemoryItemDAO dao = new MemoryItemDAO();
        dao.create(new Book("Robert Martin", "Clean Code: A Handbook of Agile Software Craftsmanship", "978-0132350884", "Ohjelmointi, design patterns", "TKT20006 Ohjelmistotuotanto"));
        
        assertEquals(1, dao.list().size());
    }
    
}
