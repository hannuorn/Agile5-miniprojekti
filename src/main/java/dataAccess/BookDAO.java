package dataAccess;

import domain.Item;
import domain.Book;
import domain.ItemType;
import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class BookDAO implements DAO<Book, Integer> {

    public BookDAO(){
    }
    
    @Override
    public void create(Book book) {
        String sql =
            "INSERT INTO Item (type, author, title, isbn, tags, description) " +
            "VALUES (:type, :author, :title, :isbn, :tags, :description);";

        try (Connection con = DB.sql2o.open()) {
            Integer key = con.createQuery(sql, true)
                .addParameter("type", ItemType.itemTypeToInteger(ItemType.BOOK))
                .addParameter("author", book.getAuthor())
                .addParameter("title", book.getTitle())
                .addParameter("isbn", book.getIsbn())
                .addParameter("tags", book.getTags())
                .addParameter("description", book.getDescription())
                .executeUpdate()
                .getKey(Integer.class);
            book.setId(key);
        }
    }

    @Override
    public List<Book> list() {
        String sql =
            "SELECT author, title, isbn FROM Book;";
        
        try (Connection con = DB.sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Book.class);
        }
    }

    @Override
    public boolean remove(Integer id) {
        return false;   
    }
    
    @Override
    public Book read(Integer id) {
        /*
        String sql =
            "SELECT * FROM Item " +
            "WHERE id = :id;";
        try (Connection con = DB.sql2o.open()) {
            con.createQuery(sql)
                .addParameter("author", Integer.toString(id))
                .executeUpdate();
        }
*/
        return null;
    }

    @Override
    public boolean update(Book book) {
        String sql =
            "UPDATE Item SET " +
            "author = ':author', " +
            "title = ':title', " +
            "isbn = ':isbn', " +
            "tags = ':tags', " +
            "description = ':description', " + 
            "read = ':read' " +
            "WHERE (id = :id);";
        
        try (Connection con = DB.sql2o.open()) {
            con.createQuery(sql)
                .addParameter("id", book.getId())
                .addParameter("author", book.getAuthor())
                .addParameter("title", book.getTitle())
                .addParameter("isbn", book.getIsbn())
                .addParameter("tags", book.getTags())
                .addParameter("description", book.getDescription())
                .addParameter("read", book.isRead())
                .executeUpdate();
        }
        return true;
    }
}
