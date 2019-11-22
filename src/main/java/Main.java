import domain.Book;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.velocity.VelocityTemplateEngine;

public class Main {
    public static void main(String[] args) {
        Book book = new Book("Matti Luukkainen", "Ohjelmistotuonto", "1111BBBBSSSS", "Ohtu", "Testi");
        System.out.println(book.getInfo());
        get("/hello", (req, res) -> book.getInfo());
            
        
        
        
       
    }
}
