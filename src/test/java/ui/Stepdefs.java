/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

/**
 *
 * @author htomi
 */
import domain.Book;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import java.util.Random;
import static org.junit.Assert.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class Stepdefs {

    WebDriver driver = new ChromeDriver();
    
    String baseUrl = "http://localhost:4567";

    @Given("Page for entering new book is selected")
    public void newBookIsSelected() {
        driver.get(baseUrl + "/new_book");
        sleep(2);
    }

    @When("valid writer {string} and Title {string} and ISBN {string} and Tag {string} and Description {string} are entered")
    public void correctBookIsEntered(String writer, String title, String isbn, String tags, String desc) {
        createBookWith(writer, title, isbn, tags, desc);
        sleep(2);
    }

    private void createBookWith(String writer, String title, String isbn, String tags, String desc) {
        WebElement element = driver.findElement(By.name("author"));
        element.sendKeys(writer);
        element = driver.findElement(By.name("title"));
        element.sendKeys(title);
        element = driver.findElement(By.name("isbn"));
        element.sendKeys(isbn);
        element = driver.findElement(By.name("tags"));
        element.sendKeys(tags);
        element = driver.findElement(By.name("description"));
        element.sendKeys(desc);
        element = driver.findElement(By.name("lisaa"));
        sleep(2);
        element.submit();

    }

    @Then("Listing of all books shows the title {string}")
    public void bookIsAdded(String title) {
        sleep(2);
        assertTrue(driver.getPageSource().contains(title));
    }

    private void pageHasContent(String content) {
        assertTrue(driver.getPageSource().contains(content));
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    private void sleep(int n) {
        try {
            Thread.sleep(n * 1000);
        } catch (Exception e) {
        }
    }

}
