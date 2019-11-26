
package domain;

import static org.junit.Assert.*;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class Stepdefs {
    
    Book book;

    @Given ("new book with title is added")
    public void bookIsAdded() {
        book = new Book("testiId","Robert Martin", "Clean Code: A Handbook of Agile Software Craftsmanship", "978-0132350884", "Ohjelmointi, design patterns", "TKT20006 Ohjelmistotuotanto");
    }
    
    @Then("system returns correct title")
    public void correctTitleIsReturned() {
        assertEquals("Clean Code: A Handbook of Agile Software Craftsmanship", book.getTitle());
    }
}
