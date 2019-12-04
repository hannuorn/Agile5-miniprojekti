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
import java.util.List;

public class UI {

    private DAO<Item, Integer> itemDao;
    static String LAYOUT = "templates/layout.html";
    private Filter filter;

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
                    if(searchResult.getClass().toString().equals("class domain.Link")) {
                        model.put("video", "on");
                    } else {
                        model.put("video", "off");
                    }
                    
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

        //Update book
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

        //Update book
        post("/update/1/:id", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            //response.redirect("/all");
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
                    model.put("searchResult", searchResult);
                    model.put("template", "templates/single_item.html");
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
            String checkboxData = request.queryParams("isVideo");
            Boolean isVideo = false;
            
            System.out.println(checkboxData);
            
            if(checkboxData != null && checkboxData.equals("on")) {
                isVideo = true;
            }

            Link newLink = new Link(author, title, url, desc, isVideo);
            itemDao.create(newLink);

            response.redirect("/all");

            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());

        //Update link
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
                if(searchResult.getIsVideo() == true) {
                    System.out.println("cheese");
                    model.put("checked", "true");
                }
                model.put("template", "templates/update_link.html");
            }
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());

        //Update link
        post("/update/2/:id", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();

            //response.redirect("/all");
            int id = parseId(request.params(":id"));
            if (id >= 0) {
                Link searchResult = (Link) itemDao.read(id);
                if (searchResult != null) {
                    String author = request.queryParams("author");
                    String title = request.queryParams("title");
                    String url = request.queryParams("url");
                    String desc = request.queryParams("description");
                    String checkboxData = request.queryParams("isVideo");
                    Boolean isVideo = false;
                    
                    if(checkboxData != null && checkboxData.equals("on")) {
                        isVideo = true;
                    }

                    searchResult.setAuthor(author);
                    searchResult.setTitle(title);
                    searchResult.setUrl(url);
                    searchResult.setDescription(desc);
                    searchResult.setIsVideo(isVideo);

                    itemDao.update((Item) searchResult);
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
