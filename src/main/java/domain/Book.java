package domain;

public class Book implements Item {

    private String id;
    private String author;
    private String title;
    private String isbn;
    private String tags;
    private String description;
    private boolean read;

    public Book(String id, String author, String title, String isbn, String tags, String description) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.isbn = isbn;
        this.tags = tags;
        this.description = description;
        this.read = false;
    }

    public boolean isRead(){
        return this.read;
    }

    public String getId(){
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getInfo() {
        return "Kirjoittaja: " + this.author + "<br/>"
                + "Otsikko: " + this.title + "<br/>"
                + "Tyyppi: " + "Kirja" + "<br/>"
                + "ISBN: " + this.isbn + "<br/>"
                + "Tagit: " + this.tags + "<br/>"
                + "Kommentti: " + this.description;
    }

    public Type getType() {
        return Type.BOOK;
    }
}
