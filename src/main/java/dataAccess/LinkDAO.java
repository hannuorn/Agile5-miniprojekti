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
            "INSERT INTO Item (type, read, author, title, url, description, video) " +
            "VALUES (:type, :read, :author, :title, :url, :description, :video);";
        
        int video = 0;
        if (link.getIsVideo()) {
            video = 1;
        }

        try (Connection con = DB.sql2o.open()) {
            Integer key = con.createQuery(sql, true)
                .addParameter("type", ItemType.itemTypeToInteger(ItemType.LINK))
                .addParameter("read", 0)
                .addParameter("author", link.getAuthor())
                .addParameter("title", link.getTitle())
                .addParameter("url", link.getUrl())
                .addParameter("description", link.getDescription())
                .addParameter("video", video)
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
    
    @Override
    public boolean update(Link link) {
        String sql =
            "UPDATE Item SET " +
            "author = :author, " +
            "title = :title, " +
            "url = :url, " +
            "description = :description, " + 
            "read = :read, " +
            "video = :video " +
            "WHERE (id = :id);"; 
        
        int read = 0;
        if (link.isRead()) {
            read = 1;
        }
        
        int video = 0;
        if (link.getIsVideo()) {
            video = 1;
        }
        
        try (Connection con = DB.sql2o.open()) {
            con.createQuery(sql)
                .addParameter("id", link.getId())
                .addParameter("author", link.getAuthor())
                .addParameter("title", link.getTitle())
                .addParameter("url", link.getUrl())
                .addParameter("description", link.getDescription())
                .addParameter("read", read)
                .addParameter("video", video)
                    
                .executeUpdate();
        }
        return true;
    }
}
