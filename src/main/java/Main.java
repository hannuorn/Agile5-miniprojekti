import dataAccess.ItemDAO;
import domain.Book;
import ui.UI;

public class Main {
    
    static String LAYOUT = "templates/layout.html";
    
    public static void main(String[] args) {
        
        ItemDAO dao = new ItemDAO();
        UI ui = new UI(dao);
        
    }
}
