package dataAccess;

import domain.Item;
import domain.Book;
import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class BookDAO implements DAO<Book, Integer> {

    public BookDAO(){
    }
    
    @Override
    public void create(Book book) {
        String sql =
            "INSERT INTO Book (author, title, isbn) " +
            "VALUES (:author, :title, :isbn);";

        try (Connection con = DB.sql2o.open()) {
            Integer key = con.createQuery(sql, true)
                .addParameter("author", book.getAuthor())
                .addParameter("title", book.getTitle())
                .addParameter("isbn", book.getIsbn())
                .executeUpdate()
                .getKey(Integer.class);
            book.setId(key);
        }
    }

    @Override
    public List<Book> list() {
        String sql =
            "SELECT author, title, isbn FROM Book;";
        return null;
    }

    @Override
    public boolean remove(Integer id) {
        return false;   
    }
    
    @Override
    public Book read(Integer id){
        return null;
    }
}
