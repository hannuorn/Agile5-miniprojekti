package domain;

public class Book implements Item {

    private String author;
    private String title;
    private String isbn;
    private String tags;
    private String description;

    public Book(String author, String title, String isbn, String tags, String description) {
        this.author = author;
        this.title = title;
        this.isbn = isbn;
        this.tags = tags;
        this.description = description;
    }

    @Override
    public String getInfo() {
        return "Kirjoittaja: " + this.author + "<br/>"
                + "Otsikko: " + this.title + "<br/>"
                + "Tyyppi: " + "kirja" + "<br/>"
                + "ISBN: " + this.isbn + "<br/>"
                + "Tagit: " + this.tags + "<br/>"
                + "Kommentti: " + this.description;
    }
}

