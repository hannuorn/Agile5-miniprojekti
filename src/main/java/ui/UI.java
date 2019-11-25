/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import domain.Book;
import domain.Item;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.velocity.VelocityTemplateEngine;
import dataAccess.ItemDAO;

/**
 *
 * @author htomi
 */
public class UI {
    private ItemDAO itemDao;
    static String LAYOUT = "templates/layout.html";
    
    
    public UI(ItemDAO itemDao){
        this.itemDao = itemDao;
        
        Book book = new Book("Matti Luukkainen", "Ohjelmistotuotanto", "1111BBBBSSSS", "Ohtu", "Testi");
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
            
            Book newBook = new Book(author,title,isbn,tags,desc);
            itemDao.create(newBook);
            
            response.redirect("/all");
         
           return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());
        
    }
    
}
