package ui;

import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.velocity.VelocityTemplateEngine;

import dataAccess.DAO;
import domain.Item;
import domain.Book;
import domain.Link;

public class UI {

    private DAO<Item, Integer> itemDao;
    static String LAYOUT = "templates/layout.html";

    
    private int parseId(String idString) {
        int id;
        try {
            id = Integer.parseInt(idString);
        } catch (NumberFormatException e) {
            id = -1;
        }
        return id;
    }

    public UI(DAO<Item, Integer> itemDao) {
        this.itemDao = itemDao;

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

            int id = parseId(request.params(":id"));
            if (id < 0) {
                response.redirect("/all");
            } else {
                Item searchResult = itemDao.read(id);
                if (searchResult == null) {
                    response.redirect("/all");
                } else {
                    model.put("searchResult", searchResult);
                    model.put("template", "templates/single_item.html");
                }
            }
            return new ModelAndView(model, LAYOUT);

        }, new VelocityTemplateEngine());

        post("/item/delete/:id", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            int id = parseId(request.params(":id"));
            if (id >= 0) {
                itemDao.remove(id);
            }
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

        get("/update/1/:id", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            
            int id = parseId(request.params(":id"));
            if (id < 0) {
                response.redirect("/all");
            } else {
                Book searchResult = (Book) itemDao.read(id);

                if (searchResult == null) {
                    response.redirect("/all");
                } else {
                    model.put("searchResult", searchResult);
                    model.put("author", searchResult.getAuthor());
                    model.put("title", searchResult.getTitle());
                    model.put("isbn", searchResult.getIsbn());
                    model.put("tags", searchResult.getTags());
                    model.put("description", searchResult.getDescription());
                    model.put("template", "templates/update_book.html");
                }
            }
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());

        post("/update/1/:id", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            response.redirect("/all");
            int id = parseId(request.params(":id"));
            if (id >= 0) {
                Book searchResult = (Book) itemDao.read(id);
                if (searchResult != null) {
                    String author = request.queryParams("author");
                    String title = request.queryParams("title");
                    String isbn = request.queryParams("isbn");
                    String tags = request.queryParams("tags");
                    String desc = request.queryParams("description");

                    searchResult.setAuthor(author);
                    searchResult.setTitle(title);
                    searchResult.setIsbn(isbn);
                    searchResult.setTags(tags);
                    searchResult.setDescription(desc);
                    itemDao.update((Item) searchResult);
                }
            }
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

        get("/update/2/:id", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();

            int id = parseId(request.params(":id"));
            if (id < 0) {
                response.redirect("/all");
            } else {
                Link searchResult = (Link) itemDao.read(id);
                if (searchResult == null) {
                    response.redirect("/all");
                }
                model.put("searchResult", searchResult);
                model.put("author", searchResult.getAuthor());
                model.put("title", searchResult.getTitle());
                model.put("url", searchResult.getUrl());
                model.put("description", searchResult.getDescription());
                model.put("template", "templates/update_link.html");
            }
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());

        post("/update/2/:id", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();

            response.redirect("/all");
            int id = parseId(request.params(":id"));
            if (id >= 0) {
                Link searchResult = (Link) itemDao.read(id);
                if (searchResult != null) {
                    String author = request.queryParams("author");
                    String title = request.queryParams("title");
                    String url = request.queryParams("url");
                    String desc = request.queryParams("description");

                    searchResult.setAuthor(author);
                    searchResult.setTitle(title);
                    searchResult.setUrl(url);
                    searchResult.setDescription(desc);

                    itemDao.update((Item) searchResult);
                }
            }
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());

        get("/choose_item_type", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            model.put("template", "templates/choose_item_type.html");
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());

        post("/changeReadStatus/:id", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            
            int id = parseId(request.params(":id"));
            if (id >= 0) {
                Item item = itemDao.read(id);
                if (item != null) {
                    item.changeRead();
                    itemDao.update(item);
                    response.redirect("/item/"+id);
                }
            }
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());

    }

}
