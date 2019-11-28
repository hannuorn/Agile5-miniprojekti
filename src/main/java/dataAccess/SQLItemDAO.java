package dataAccess;

import domain.Item;
import domain.Book;
import domain.Link;
import domain.ItemType;

import java.util.ArrayList;
import java.util.List;
import org.sql2o.Connection;
import org.sql2o.data.Row;

public class SQLItemDAO implements DAO<Item, Integer> {

    private BookDAO bookDao;
    private LinkDAO linkDao;
    
    public SQLItemDAO(){
        bookDao = new BookDAO();
        linkDao = new LinkDAO();
    }
    
    @Override
    public void create(Item item) {
        switch (item.getType()) {
            case BOOK:      bookDao.create((Book) item);        break;
            case LINK:      linkDao.create((Link) item);        break;
        }
    }

    @Override
    public List<Item> list() {
        String sql =
            "SELECT * FROM Item;";
        
        ArrayList<Item> items = new ArrayList<>();
        
        try (Connection con = DB.sql2o.open()) {
            List<Row> rows = con.createQuery(sql).executeAndFetchTable().rows();
            for (Row row : rows) {
                int id = row.getInteger("id");
                ItemType type = ItemType.integerToItemType(row.getInteger("type"));
                String author = row.getString("author");
                String title = row.getString("title");
                String description = row.getString("description");
                switch (type) {
                    case BOOK:
                        String isbn = row.getString("isbn");
                        String tags = row.getString("tags");
                        Book book = new Book(author, title, isbn, tags, description);
                        book.setId(id);
                        items.add(book);
                        break;
                    case LINK:
                        String url = row.getString("url");
                        Link link = new Link(author, title, url, description);
                        link.setId(id);
                        items.add(link);
                        break;
                }
            }
        }
        return items;
    }

    @Override
    public boolean remove(Integer id) {
        return false;   
    }
    
    @Override
    public Item read(Integer id){
        String sql =
            "SELECT * FROM Item " +
            "WHERE id = :id;";
        
        Item item = null;
        
        try (Connection con = DB.sql2o.open()) {
            List<Row> rows = con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchTable()
                    .rows();
            if (rows.size() == 1) {
                Row row = rows.get(0);
                ItemType type = ItemType.integerToItemType(row.getInteger("type"));
                String author = row.getString("author");
                String title = row.getString("title");
                String description = row.getString("description");
                switch (type) {
                    case BOOK:
                        String isbn = row.getString("isbn");
                        String tags = row.getString("tags");
                        Book book = new Book(author, title, isbn, tags, description);
                        book.setId(id);
                        item = book;
                        break;
                    case LINK:
                        String url = row.getString("url");
                        Link link = new Link(author, title, url, description);
                        link.setId(id);
                        item = link;
                        break;
                }
                
            }
        }
        return item;
    }
}
