import dataAccess.ItemDAO;
import domain.HexadecimalGenerator;
import ui.UI;
import static spark.Spark.*;

public class Main {
    
    static String LAYOUT = "templates/layout.html";
    
    public static void main(String[] args) {
        staticFiles.location("/public");
        ItemDAO dao = new ItemDAO();
        HexadecimalGenerator generator = new HexadecimalGenerator();
        UI ui = new UI(dao,generator);
    }
}
