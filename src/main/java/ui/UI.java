package ui;

import domain.Book;
import domain.Link;
import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.velocity.VelocityTemplateEngine;
import dataAccess.DAO;
import domain.IdGenerator;
import domain.Item;

public class UI {

    private DAO<Item, Integer> itemDao;
    private IdGenerator idGenerator;
    static String LAYOUT = "templates/layout.html";

    public UI(DAO<Item, Integer> itemDao, IdGenerator idgenerator) {
        this.itemDao = itemDao;
        this.idGenerator = idgenerator;

        Book book = new Book("Matti Luukkainen", "Ohjelmistotuotanto", "1111BBBBSSSS", "Ohtu", "Testi");
        Book book2 = new Book("Maija Mallikas", "Testi", "111222", "testi", "TKT10001");
        System.out.println(book.getInfo());
        itemDao.create(book);
        itemDao.create(book2);

        get("/", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            model.put("template", "templates/index.html");
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());

        get("/all", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            model.put("list", itemDao.list());
            model.put("template", "templates/all.html");
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());

        get("/item/:id", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            
            Integer id = Integer.parseInt(request.params(":id"));
            
            Item searchResult = itemDao.read(id);
            
            if (searchResult == null) {
                response.redirect("/all");
            } 

            model.put("searchResult", searchResult);
            model.put("template", "templates/single_item.html");
            
            return new ModelAndView(model, LAYOUT);
            
        }, new VelocityTemplateEngine());

        post("/item/delete/:id", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            int id = Integer.parseInt(request.params(":id"));

            itemDao.remove(id);
            response.redirect("/all");
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());

        get("/new_book", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            model.put("template", "templates/new_book.html");
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());

        post("/new_book", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            String author = request.queryParams("author");
            String title = request.queryParams("title");
            String isbn = request.queryParams("isbn");
            String tags = request.queryParams("tags");
            String desc = request.queryParams("description");

            Book newBook = new Book(author, title, isbn, tags, desc);
            itemDao.create(newBook);

            response.redirect("/all");

            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());
        
        get("/update_book/:id", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            
            int id = Integer.parseInt(request.params(":id"));
            
            Item searchResult = itemDao.read(id);
            
            if (searchResult == null) {
                response.redirect("/all");
            }
            
            model.put("searchResult", searchResult);
            model.put("template", "templates/update_book.html");
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());

        post("/update_book/:id", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            /*String author = request.queryParams("author");
            String title = request.queryParams("title");
            String isbn = request.queryParams("isbn");
            String tags = request.queryParams("tags");
            String desc = request.queryParams("description");

            Book newBook = new Book(idGenerator.getId(), author, title, isbn, tags, desc);
            itemDao.create(newBook);*/

            response.redirect("/all");

            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());
        

        get("/new_link", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            model.put("template", "templates/new_link.html");
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());

        post("/new_link", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            String author = request.queryParams("author");
            String title = request.queryParams("title");
            String url = request.queryParams("url");
            String desc = request.queryParams("description");

            Link newLink = new Link(author, title, url, desc);
            itemDao.create(newLink);

            response.redirect("/all");

            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());
        
        get("/update_link/:id", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            
            int id = Integer.parseInt(request.params(":id"));
            
            Item searchResult = itemDao.read(id);
            
            if (searchResult == null) {
                response.redirect("/all");
            }
            
            model.put("searchResult", searchResult);
            model.put("template", "templates/update_link.html");
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());

        post("/update_link/:id", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
           
            response.redirect("/all");

            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());

        get("/choose_item_type", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            model.put("template", "templates/choose_item_type.html");
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());
    }

}
