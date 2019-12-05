package ui;

import dataAccess.BookFinder;
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
import domain.VideoParser;
import java.util.List;

public class UI {

    private DAO<Item, Integer> itemDao;
    static String LAYOUT = "templates/layout.html";
    private Filter filter;
    private Validator validator;
    private BookFinder bookFinder;
    private VideoParser videoParser;

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
        this.validator = new Validator(3, 80);
        this.bookFinder = new BookFinder();
        this.videoParser = new VideoParser();
        
        get("/", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            Book testi = bookFinder.findBookByISBN("9781118951309");
            System.out.println(testi.getInfo());
            
            model.put("list", itemDao.list());
            model.put("template", "templates/all.html");
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());
        
        post("/search", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            List<Item> foundItems = filter.searchAll(request.queryParams("search"));
            model.put("list", foundItems);
            model.put("template", "templates/all.html");
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());

        get("/item/:id", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();

            int id = parseId(request.params(":id"));
            if (id < 0) {
                response.redirect("/");
            } else {
                Item searchResult = itemDao.read(id);
                if (searchResult == null) {
                    response.redirect("/");
                } else {
                    if(searchResult.getIsVideo()) {
                        model.put("video", "on");
                        Link link = (Link)searchResult;
                        String url = link.getUrl();
                        String embed = videoParser.parse(url);
                        model.put("url", embed);
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
            response.redirect("/");
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());

        get("/new_book", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            model.put("message", validator.getErrorMessage());
            model.put("error", validator.getValid());
            model.put("author", "");
            model.put("title", "");
            model.put("isbn", "");
            model.put("tags", "");
            model.put("description", "");
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
                response.redirect("/");
            } else {
                model.put("message", validator.getErrorMessage());
                model.put("error", validator.getValid());
                model.put("author", author);
                model.put("title", title);
                model.put("isbn", isbn);
                model.put("tags", tags);
                model.put("description", desc);
                model.put("template", "templates/new_book.html");
            }

            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());

        //Update book
        get("/update/1/:id", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();

            int id = parseId(request.params(":id"));
            if (id < 0) {
                response.redirect("/");
            } else {
                Book searchResult = (Book) itemDao.read(id);

                if (searchResult == null) {
                    response.redirect("/");
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

        //Update book
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
            model.put("author", "");
            model.put("title", "");
            model.put("url", "");
            model.put("description", "");
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
            String checkboxData = request.queryParams("isVideo");
            Boolean isVideo = false;
            
            if(checkboxData != null && checkboxData.equals("on")) {
                isVideo = true;
            }

            if(!failed) {
                Link newLink = new Link(author, title, url, desc, isVideo);
                itemDao.create(newLink);
                response.redirect("/");
            } else {
                model.put("message", validator.getErrorMessage());
                model.put("error", validator.getValid());
                model.put("author", author);
                model.put("title", title);
                model.put("url", url);
                model.put("description", desc);
                model.put("template", "templates/new_link.html");
            }
            
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());

        //Update link
        get("/update/2/:id", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();

            int id = parseId(request.params(":id"));
            if (id < 0) {
                response.redirect("/");
            } else {
                Link searchResult = (Link) itemDao.read(id);
                if (searchResult == null) {
                    response.redirect("/");
                }
                model.put("message", validator.getErrorMessage());
                model.put("error", validator.getValid());
                model.put("searchResult", searchResult);
                model.put("author", searchResult.getAuthor());
                model.put("title", searchResult.getTitle());
                model.put("url", searchResult.getUrl());
                model.put("description", searchResult.getDescription());
                System.out.println(searchResult.getIsVideo());
                if(searchResult.getIsVideo()) {
                    model.put("checked", "checked");
                }
                model.put("template", "templates/update_link.html");
            }
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());

        //Update link
        post("/update/2/:id", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            
            boolean failed = false;

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
                    
                    String checkboxData = request.queryParams("isVideo");
                    Boolean isVideo = false;
                    
                    if(checkboxData != null && checkboxData.equals("on")) {
                        isVideo = true;
                    }

                    if(!failed) {
                        searchResult.setAuthor(author);
                        searchResult.setTitle(title);
                        searchResult.setUrl(url);
                        searchResult.setDescription(desc);
                        searchResult.setIsVideo(isVideo);
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
        
        post("/autofill", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            String isbn = request.queryParams("isbn");
            Book book = bookFinder.findBookByISBN(isbn);
            itemDao.create(book);
            String url = "/update/1/" + book.getId();
            response.redirect(url);
            
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());
    }
}
