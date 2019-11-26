import dataAccess.ItemDAO;
import domain.HexadecimalGenerator;
import ui.UI;

public class Main {
    
    static String LAYOUT = "templates/layout.html";
    
    public static void main(String[] args) {
        ItemDAO dao = new ItemDAO();
        HexadecimalGenerator generator = new HexadecimalGenerator();
        UI ui = new UI(dao,generator);
    }
}
