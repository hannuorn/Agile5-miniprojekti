import domain.Book;
import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.velocity.VelocityTemplateEngine;

public class Main {
    
    static String LAYOUT = "templates/layout.html";
    
    public static void main(String[] args) {
        Book book = new Book("Matti Luukkainen", "Ohjelmistotuonto", "1111BBBBSSSS", "Ohtu", "Testi");
        System.out.println(book.getInfo());
        
        get("/hello", (request, response) -> {
            HashMap<String, String> model = new HashMap<>();
            model.put("info", book.getInfo());
            model.put("template", "templates/index.html");
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());   
            
             
    }
}
