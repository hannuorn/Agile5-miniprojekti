package dataAccess;

/**
 *
 * @author hannu
 */

import domain.Item;
import domain.ItemType;
import domain.Link;
import java.util.List;
import org.sql2o.*;

public class LinkDAO implements DAO<Link, Integer> {

    public LinkDAO(){
    }
    
    @Override
    public void create(Link link) {
        String sql =
            "INSERT INTO Item (type, author, title, url, description) " +
            "VALUES (:type, :author, :title, :url, :description);";

        try (Connection con = DB.sql2o.open()) {
            Integer key = con.createQuery(sql, true)
                .addParameter("type", ItemType.itemTypeToInteger(ItemType.LINK))
                .addParameter("author", link.getAuthor())
                .addParameter("title", link.getTitle())
                .addParameter("url", link.getUrl())
                .addParameter("description", link.getDescription())
                .executeUpdate()
                .getKey(Integer.class);
            link.setId(key);
        }
    }

    @Override
    public List<Link> list() {
        String sql =
            "SELECT author, title, isbn FROM Link;";
        
        try (Connection con = DB.sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Link.class);
        }
    }

    @Override
    public boolean remove(Integer id) {
        return false;   
    }
    
    @Override
    public Link read(Integer id){
        return null;
    }
}