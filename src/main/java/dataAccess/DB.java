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
    public static final Logger logger = LoggerFactory.getLogger(DB.class);

    static private void executeQuery(String sql) {
        try (Connection con = DB.sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        }
    }
    
    static private void createTablesHeroku() {
        String sql = 
            "DROP TABLE IF EXISTS Item;";
        executeQuery(sql);
        
        sql = 
            "CREATE TABLE Item(" +
                "id SERIAL PRIMARY KEY, " +
                "type INTEGER," +
                "read INTEGER," +
                "author VARCHAR(80)," +
                "title VARCHAR(80)," +
                "isbn VARCHAR(80)," +
                "tags VARCHAR(80)," +
                "description VARCHAR(80)," +
                "url VARCHAR(80)," +
                "video INTEGER);";
        executeQuery(sql);
    }
    
    static private void createTablesSqlite() {
        String sql = 
            "DROP TABLE IF EXISTS Item;";
        executeQuery(sql);
        
        sql = 
            "CREATE TABLE Item(" +
                "id INTEGER PRIMARY KEY, " +
                "type INTEGER," +
                "read INTEGER," +
                "author VARCHAR(80)," +
                "title VARCHAR(80)," +
                "isbn VARCHAR(80)," +
                "tags VARCHAR(80)," +
                "description VARCHAR(80)," +
                "url VARCHAR(80)," +
                "video INTEGER);";
        executeQuery(sql);
    }
    
    static {

        try {
            itemDao = new SQLItemDAO();
            
            if (System.getenv("DATABASE_URL") == null) {
                sql2o = new Sql2o("jdbc:sqlite:vinkkikirjasto.db", null, null);

                //createTablesSqlite();

            } else {
                dbUri = new URI(System.getenv("DATABASE_URL"));
                final int port = dbUri.getPort();
                final String host = dbUri.getHost();
                final String path = dbUri.getPath();
                final String username = (dbUri.getUserInfo() == null) ? null : dbUri.getUserInfo().split(":")[0];
                final String password = (dbUri.getUserInfo() == null) ? null : dbUri.getUserInfo().split(":")[1];
                sql2o = new Sql2o("jdbc:postgresql://" + host + ":" + port + path, username, password);
//                createTablesHeroku();
            }
        } catch (URISyntaxException e ) { 
            logger.error("Unable to connect to database.");
        }
    }
    
}
