package dataAccess;

import domain.Item;
import org.sql2o.*;
import java.net.URI;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DB {

    public static DAO<Item, Integer> itemDao;
    private static URI dbUri;
    public static Sql2o sql2o;
    public static Logger logger = LoggerFactory.getLogger(DB.class);

    static void executeQuery(String sql) {
        try (Connection con = DB.sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        }
    }
    
    static void createTables() {
        String sql = 
            "DROP TABLE Item;";
        executeQuery(sql);
        
        sql = 
            "CREATE TABLE Item(" +
                "id SERIAL PRIMARY KEY, " +
                "type VARCHAR(20)," +
                "read BOOLEAN," +
                "author VARCHAR(40)," +
                "title VARCHAR(40)," +
                "isbn VARCHAR(40)," +
                "tags VARCHAR(40)," +
                "description VARCHAR(40)," +
                "url VARCHAR(40));";
        executeQuery(sql);
    }
    
    static {

        try {
            if (System.getenv("DATABASE_URL") == null) {
                dbUri = new URI("postgres://localhost:5432/vinkkikirjasto");
                itemDao = new MemoryItemDAO();
            } else {
                dbUri = new URI(System.getenv("DATABASE_URL"));
                itemDao = new SQLItemDAO();
            }
            int port = dbUri.getPort();
            String host = dbUri.getHost();
            String path = dbUri.getPath();
            String username = (dbUri.getUserInfo() == null) ? null : dbUri.getUserInfo().split(":")[0];
            String password = (dbUri.getUserInfo() == null) ? null : dbUri.getUserInfo().split(":")[1];
            sql2o = new Sql2o("jdbc:postgresql://" + host + ":" + port + path, username, password);
            createTables();
        } catch (URISyntaxException e ) { 
            logger.error("Unable to connect to database.");
        }
    }
    
}
