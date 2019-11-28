package dataAccess;

import domain.Book;
import static org.junit.Assert.*;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.Before;


public class Stepdefs {

    MemoryItemDAO dao;
    Book book;
    int id;

    @Before
    public void setup() {
        this.dao = new MemoryItemDAO();
        dao.create(new Book("Mikkihiiri", "Jeejee", "113456AAAH", "tagi", "TKT112 Help"));
    }


    @Given("new book with title is added")
    public void bookIsAdded() {
        book = new Book("Robert Martin", "Clean Code: A Handbook of Agile Software Craftsmanship", "978-0132350884", "Ohjelmointi, design patterns", "TKT20006 Ohjelmistotuotanto");
    }

    @Then("system returns correct title")
    public void correctTitleIsReturned() {
        assertEquals("Clean Code: A Handbook of Agile Software Craftsmanship", book.getTitle());
    }

    @Given("command remove is selected")
    public void commandRemoveIsSelected() {
        id = dao.list().get(0).getId();
        dao.remove(id);
    }

    @Then("item is removed")
    public void itemIsRemoved() {
        assertEquals(dao.read(id), null);
    }

//    @Given("command remove is selected")
//    public void commandRemoveIsSelected() {
//       //List<Item> list = dao.list();
//       id = dao.list().get(0).getId();
//       dao.remove(id);
//    }
//    
//    @Then("item is removed") 
//    public void itemIsRemoved() {
//        assertEquals(dao.read(id), null);
//    }
}
