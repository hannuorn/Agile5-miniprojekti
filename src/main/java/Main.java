import dataAccess.ItemDAO;
import domain.HexadecimalGenerator;
import ui.UI;
import static spark.Spark.*;

public class Main {
    
    public static void main(String[] args) {
        ProcessBuilder process = new ProcessBuilder();
        Integer port;

        // This tells our app that if Heroku sets a port for us, we need to use that port.
        // Otherwise, if they do not, continue using port 4567.

        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 4567;
        }

        port(port);

        staticFiles.location("/public");
        ItemDAO dao = new ItemDAO();
        HexadecimalGenerator generator = new HexadecimalGenerator();
        UI ui = new UI(dao,generator);
    }
}
