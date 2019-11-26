package ui;

import domain.Book;
import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.velocity.VelocityTemplateEngine;
import dataAccess.ItemDAO;
import domain.IdGenerator;
import domain.Item;

public class UI {

    private ItemDAO itemDao;
    private IdGenerator idGenerator;
    static String LAYOUT = "templates/layout.html";

    public UI(ItemDAO itemDao, IdGenerator idgenerator) {
        this.itemDao = itemDao;
        this.idGenerator = idgenerator;

        Book book = new Book("123A", "Matti Luukkainen", "Ohjelmistotuotanto", "1111BBBBSSSS", "Ohtu", "Testi");
        System.out.println(book.getInfo());
        itemDao.create(book);

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
            
            String id = request.params(":id");
            
            Item searchResult = itemDao.read(id);
            
            if (searchResult == null) {
                response.redirect("/all");
            }

            model.put("searchResult", searchResult);
            model.put("template", "templates/singleItem.html");
            
            return new ModelAndView(model, LAYOUT);
            
        }, new VelocityTemplateEngine());

        get("/new", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            model.put("template", "templates/new.html");
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());

        post("/new", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            String author = request.queryParams("author");
            String title = request.queryParams("title");
            String isbn = request.queryParams("isbn");
            String tags = request.queryParams("tags");
            String desc = request.queryParams("description");

            Book newBook = new Book(idGenerator.getId(), author, title, isbn, tags, desc);
            itemDao.create(newBook);

            response.redirect("/all");

            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());

        

    }

}
