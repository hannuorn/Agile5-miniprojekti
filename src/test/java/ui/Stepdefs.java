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

    WebDriver driver = new HtmlUnitDriver();

    String baseUrl = "http://localhost:4567";

    @Given("There are books stored in the application and single view of book is clicked")
    public void thereAreBooksStoredInTheApplicationAndSingleViewOfBookIsClicked() {
        createInitialBooks();
        WebElement element = driver.findElement(By.linkText("Ohjelmistotuotanto"));
        element.click();
    }

    @When("User cliks edit and inputs new title {string} and presses submit")
    public void userCliksEditAndInputsNewTitleAndPressesSubmit(String title) {
        WebElement element = driver.findElement(By.name("edit"));
        element.submit();
        element = driver.findElement(By.name("title"));
        element.clear();
        element.sendKeys(title);
        element = driver.findElement(By.name("muokkaa"));
        element.submit();
    }

    @Then("Edited title {string} is stored in the application")
    public void editedTitleIsStoredInTheApplication(String title) {
        assertTrue(driver.getPageSource().contains(title));
    }

    @Given("There are books stored in the application")
    public void thereAreBooksStoredInTheApplication() {
        createInitialBooks();
    }

    @Given("There are links stored in the application")
    public void thereAreLinksStoredInTheApplication() {
        createInitialLinks();
    }

    @When("User opens the application")
    public void userOpensTheApplication() {
        driver.get(baseUrl);
    }

    @Then("User can see a list of all books")
    public void userCanSeeAListOfAllBooks() {
        assertTrue(driver.getPageSource().contains("Ohjelmistotuotanto"));
        assertTrue(driver.getPageSource().contains("Tietorakenteet ja algoritmit"));
    }

    @Given("Page for entering new book is selected")
    public void newBookIsSelected() {
        createNewBookIsSelected();
    }

    @Given("Page for entering new link is selected")
    public void newLinkIsSelected() {
        createNewLinkIsSelected();
    }

    @When("valid writer {string} and Title {string} and ISBN {string} and Tag {string} and Description {string} are entered")
    public void correctBookIsEntered(String writer, String title, String isbn, String tags, String desc) {
        createBookWith(writer, title, isbn, tags, desc);
    }

    @When("writer {string} and Title {string} and URL {string} and Description {string} and isVideo {string} for link are entered")
    public void correctLinkIsEntered(String writer, String title, String url, String desc, String isVideo) {
        boolean video = false;
        if (isVideo.equals("enabled")) {
            video = true;
        }
        createLinkWith(writer, title, url, desc, video);
    }

    @When("valid isbn {string} is added to text box and search button is clicked")
    public void bookSearchedByISBN(String isbn) {
        WebElement element = driver.findElement(By.id("isbn2"));
        element.sendKeys(isbn);
        element = driver.findElement(By.name("etsiISBN"));
        element.click();
    }

    @Then("Listing of all books shows the title {string}")
    public void bookIsAdded(String title) {
        assertTrue(driver.getPageSource().contains(title));
    }

    private void pageHasContent(String content) {
        assertTrue(driver.getPageSource().contains(content));
    }

    @When("valid writer {string} and invalid Title {string} and ISBN {string} and Tag {string} and Description {string} are entered")
    public void validWriterAndInvalidTitleAndISBNAndTagAndDescriptionAreEntered(String writer, String title, String isbn, String tags, String desc) {
        createBookWith(writer, title, isbn, tags, desc);
    }

    @Then("Listing of all books does not show the title {string}")
    public void listingOfAllBooksDoesNotShowTheTitle(String title) {
        assertTrue(!driver.getPageSource().contains(title));
    }

    @Then("addition form shows the author {string}")
    public void additionFormShowsTheAuthor(String author) {
        WebElement element = driver.findElement(By.id("author"));
        String textbox = element.getAttribute("value");
        assertTrue(textbox.contains(author));
    }

    @When("Searched string {string} is written in the search field")
    public void titleIsWrittenInTheSearchFiled(String string) {
        WebElement element = driver.findElement(By.id("search"));
        element.sendKeys(string);
    }

    @Then("Listing of searched items shows the title {string}")
    public void searchedTitleIsShown(String title) {
        assertTrue(driver.getPageSource().contains(title));
    }
    
    @After
    public void tearDown() {
        driver.quit();
    }

    private void createInitialBooks() {
        createNewBookIsSelected();
        createBookWith("Matti", "Ohjelmistotuotanto", "111AA", "syksy2019", "Tärkeää");
        createNewBookIsSelected();
        createBookWith("Pekka", "Tietorakenteet ja algoritmit", "2AAAA", "syksy 2019", "vaikeaa");
    }

    private void createInitialLinks() {
        createNewLinkIsSelected();
        createLinkWith("Matti", "Blogiteksti", "www.testi.net", "havainnollistava", false);
        createNewLinkIsSelected();
        createLinkWith("Maija", "Artikkeli", "www.artikkeli.com", "vaikuttaa hyvältä", false);
    }

    private void createNewBookIsSelected() {
        driver.get(baseUrl + "/new_book");
    }

    private void createNewLinkIsSelected() {
        driver.get(baseUrl + "/new_link");
    }

    private void sleep(int n) {
        try {
            Thread.sleep(n * 1000);
        } catch (Exception e) {
        }
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

    private void createLinkWith(String writer, String title, String url, String desc, boolean isVideo) {
        WebElement element = driver.findElement(By.name("author"));
        element.sendKeys(writer);
        element = driver.findElement(By.name("title"));
        element.sendKeys(title);
        element = driver.findElement(By.name("url"));
        element.sendKeys(url);
        element = driver.findElement(By.name("description"));
        element.sendKeys(desc);
        if (isVideo) {
            element = driver.findElement(By.name("isVideo"));
            element.click();
        }
        element = driver.findElement(By.name("lisaa"));
        sleep(2);
        element.submit();
    }
}
