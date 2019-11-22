/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import dataAccess.ItemDAO;
import domain.Book;
import domain.Item;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.velocity.VelocityTemplateEngine;

/**
 *
 * @author htomi
 */
public class UI {
    private ItemDAO dao;
    static String LAYOUT = "templates/layout.html";
    
    
    public UI(ItemDAO dao){
        this.dao = dao;
        
        Book book = new Book("Matti Luukkainen", "Ohjelmistotuonto", "1111BBBBSSSS", "Ohtu", "Testi");
        System.out.println(book.getInfo());
        dao.create(book);
        List<Item> books = dao.list();
        
        get("/index", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            model.put("list", books);
            model.put("template", "templates/index.html");
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());  
        
    }
    
    
    
}
