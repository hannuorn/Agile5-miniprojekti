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

        return "Kirjoittaja: " + this.author + "\n"
                + "Otsikko: " + this.title + "\n"
                + "Tyyppi: " + "kirja" + "\n"
                + "ISBN: " + this.isbn + "\n"
                + "Tagit: " + this.tags + "\n"
                + "Kommentti: " + this.description;
    }
}

