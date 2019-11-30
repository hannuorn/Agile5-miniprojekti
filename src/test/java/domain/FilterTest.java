
package domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import dataAccess.MemoryItemDAO;
import java.util.List;

public class FilterTest {
    
    MemoryItemDAO dao;
    Filter filter;
    
    @Before
    public void setUp() {
        dao = new MemoryItemDAO();
        dao.create(new Book("Robert Martin", "Clean Code: A Handbook of Agile Software Craftsmanship", "978-0132350884", "Ohjelmointi, design patterns", "TKT20006 Ohjelmistotuotanto"));
        dao.create(new Book("Kirjoittaja", "Testikirja", "123456", "ohjelmointi", "Tämä on kirja."));
        dao.create(new Link("Kirjoittaja", "Testilinkki", "www.testisivu.net", "Tässä linkkinen!"));
        filter = new Filter(dao);
    }
    
    @Test
    public void filterReturnsCorrectNumberOfBooks() {
        assertEquals(2, filter.filterType(ItemType.BOOK).size());
    }
    
    @Test
    public void filterReturnsCorrectNumberOfLinks() {
        assertEquals(1, filter.filterType(ItemType.LINK).size());
    }
    
    @Test
    public void filteringBooksReturnsBookItems() {
        List<Item> lista = filter.filterType(ItemType.BOOK);
        
        for (Item i : lista) {
            assertEquals(ItemType.BOOK, i.getType());
        }
    }
    
    @Test
    public void filteringLinksReturnsLinkItems() {
        List<Item> lista = filter.filterType(ItemType.LINK);
        
        for (Item i : lista) {
            assertEquals(ItemType.LINK, i.getType());
        }
    }
    
    @Test
    public void filteringReadItemsReturnCorrectNumberOfItems() {
        dao.read(dao.list().get(0).getId()).changeRead();
        assertEquals(1, filter.filterReadStatus(true).size());
    }
    
    @Test
    public void filteringUnreadItemsReturnCorrectNumberOfItems() {
        dao.read(dao.list().get(0).getId()).changeRead();
        assertEquals(2, filter.filterReadStatus(false).size());
    }
    
    @Test
    public void filteringReadItemsReturnsReadItems() {
        dao.read(dao.list().get(0).getId()).changeRead();
        dao.read(dao.list().get(1).getId()).changeRead();
        
        List<Item> lista = filter.filterReadStatus(true);
        
        for (Item i : lista) {
            assertEquals(true, i.isRead());
        }
    }
}
