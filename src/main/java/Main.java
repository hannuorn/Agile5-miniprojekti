import dataAccess.ItemsDAO;
import domain.Book;
import ui.UI;

public class Main {
    
    static String LAYOUT = "templates/layout.html";
    
    public static void main(String[] args) {
        
        ItemsDAO dao = new ItemsDAO();
        UI ui = new UI(dao);
        
             
    }
}
