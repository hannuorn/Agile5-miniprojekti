import dataAccess.DAO;
import dataAccess.SQLItemDAO;
import domain.Item;
import ui.UI;
import static spark.Spark.*;

public class Main {
    
    public static void main(String[] args) {
        ProcessBuilder process = new ProcessBuilder();
        Integer port;
        
        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 4567;
        }

        port(port);

        staticFiles.location("/public");

        DAO<Item, Integer> itemDao = new SQLItemDAO();
        
        new UI(itemDao);
    }
}
