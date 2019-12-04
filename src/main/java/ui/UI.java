package ui;

import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.velocity.VelocityTemplateEngine;

import dataAccess.DAO;
import domain.Item;
import domain.Book;
import domain.Filter;
import domain.Link;
import domain.Validator;
import java.util.List;

public class UI {

    private DAO<Item, Integer> itemDao;
    static String LAYOUT = "templates/layout.html";
    private Filter filter;
    private Validator validator;

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
        this.filter = new Filter(itemDao);
        this.validator = new Validator();
        
        get("/", (request, response) -> {
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
            model.put("message", validator.getErrorMessage());
            model.put("error", validator.getValid());
            model.put("template", "templates/new_book.html");
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());

        post("/new_book", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            
            Boolean failed = false;
            
            String author = request.queryParams("author");
            if(!validator.isValid("Kirjoittaja", author)) {failed = true;}
            String title = request.queryParams("title");
            if(!validator.isValid("Otsikko", title)) {failed = true;}
            String isbn = request.queryParams("isbn");
            if(!validator.isValid("ISBN", isbn)) {failed = true;}
            String tags = request.queryParams("tags");
            if(!validator.isValid("Tagit", tags)) {failed = true;}
            String desc = request.queryParams("description");
            if(!validator.isValid("Kuvaus", desc)) {failed = true;}

            if(!failed) {
                Book newBook = new Book(author, title, isbn, tags, desc);
                itemDao.create(newBook);
                response.redirect("/all");
            } else {
                response.redirect("/new_book");
            }

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
                    model.put("message", validator.getErrorMessage());
                    model.put("error", validator.getValid());
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
            
            boolean failed = false;
                    
            int id = parseId(request.params(":id"));
            if (id >= 0) {
                Book searchResult = (Book) itemDao.read(id);
                if (searchResult != null) {
                    String author = request.queryParams("author");
                    if(!validator.isValid("Kirjoittaja", author)) {failed = true;}
                    String title = request.queryParams("title");
                    if(!validator.isValid("Otsikko", title)) {failed = true;}
                    String isbn = request.queryParams("isbn");
                    if(!validator.isValid("ISBN", isbn)) {failed = true;}
                    String tags = request.queryParams("tags");
                    if(!validator.isValid("Tagit", tags)) {failed = true;}
                    String desc = request.queryParams("description");
                    if(!validator.isValid("Kuvaus", desc)) {failed = true;}

                    if(!failed) {
                        searchResult.setAuthor(author);
                        searchResult.setTitle(title);
                        searchResult.setIsbn(isbn);
                        searchResult.setTags(tags);
                        searchResult.setDescription(desc);
                        itemDao.update((Item) searchResult);
                    } else {
                        response.redirect("/update/1/" + request.params(":id"));
                    }
                    model.put("searchResult", searchResult);
                    model.put("template", "templates/single_item.html");
                }
            }
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());

        get("/new_link", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            model.put("message", validator.getErrorMessage());
            model.put("error", validator.getValid());
            model.put("template", "templates/new_link.html");
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());

        post("/new_link", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            
            boolean failed = false;

            String author = request.queryParams("author");
            if(!validator.isValid("Kirjoittaja", author)) {failed = true;}
            String title = request.queryParams("title");
            if(!validator.isValid("Otsikko", title)) {failed = true;}
            String url = request.queryParams("url");
            if(!validator.isValid("URL", url)) {failed = true;}
            String desc = request.queryParams("description");
            if(!validator.isValid("Kuvaus", desc)) {failed = true;}

            if(!failed) {
                Link newLink = new Link(author, title, url, desc);
                itemDao.create(newLink);
                response.redirect("/all");
            } else {
                response.redirect("/new_link");
            }

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
                model.put("message", validator.getErrorMessage());
                model.put("error", validator.getValid());
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
            
            boolean failed = false;

            //response.redirect("/all");
            int id = parseId(request.params(":id"));
            if (id >= 0) {
                Link searchResult = (Link) itemDao.read(id);
                if (searchResult != null) {
                    String author = request.queryParams("author");
                    if(!validator.isValid("Kirjoittaja", author)) {failed = true;}
                    String title = request.queryParams("title");
                    if(!validator.isValid("Otsikko", title)) {failed = true;}
                    String url = request.queryParams("url");
                    if(!validator.isValid("URL", url)) {failed = true;}
                    String desc = request.queryParams("description");
                    if(!validator.isValid("Kuvaus", desc)) {failed = true;}

                    if(!failed) {
                        searchResult.setAuthor(author);
                        searchResult.setTitle(title);
                        searchResult.setUrl(url);
                        searchResult.setDescription(desc);
                        itemDao.update((Item) searchResult);
                    } else {
                        response.redirect("/update/2/" + request.params(":id"));
                    }
                    model.put("searchResult", searchResult);
                    model.put("template", "templates/single_item.html");
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
                    response.redirect("/item/" + id);
                }
            }
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());

        get("/filter_items", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            model.put("template", "templates/filter_items.html");
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());

        post("/filter_items/:type", (request, response) -> {
            int type = parseId(request.params(":type"));
            
            HashMap<String, Object> model = new HashMap<>();
            List<Item> filteredItems = filter.filter(type);
            model.put("list", filteredItems);
            model.put("template", "templates/filter_items.html");
            
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());
    }
}
