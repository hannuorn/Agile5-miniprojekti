import dataAccess.DAO;
import dataAccess.SQLItemDAO;
import dataAccess.MemoryItemDAO;
import domain.HexadecimalGenerator;
import domain.Item;
import ui.UI;
import static spark.Spark.*;

public class Main {
    
    public static void main(String[] args) {
        ProcessBuilder process = new ProcessBuilder();
        Integer port;

        // This tells our app that if Heroku sets a port for us, we need to use that port.
        // Otherwise, if they do not, continue using port 4567.

        DAO<Item, Integer> dao;
        
        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
            dao = new SQLItemDAO();
        } else {
            port = 4567;
            dao = new MemoryItemDAO();
        }

        port(port);

        staticFiles.location("/public");
        HexadecimalGenerator generator = new HexadecimalGenerator();
        UI ui = new UI(dao, generator);
    }
}
