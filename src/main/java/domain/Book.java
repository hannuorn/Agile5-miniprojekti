package domain;

public class Book implements Item {

    private Integer id;
    private String author;
    private String title;
    private String isbn;
    private String tags;
    private String description;
    private boolean read;

    public Book(String author, String title, String isbn, String tags, String description) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.isbn = isbn;
        this.tags = tags;
        this.description = description;
        this.read = false;
    }

    @Override
    public boolean isRead(){
        return this.read;
    }

    @Override
    public Integer getId(){
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthor() {
        return this.author;
    }
    
    @Override
    public String getTitle() {
        return this.title;
    }
    
    public String getIsbn() {
        return this.isbn;
    }
    
    public String getTags() {
        return this.tags;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public String getInfo() {
        return "Kirjoittaja: " + this.author + "<br/>"
                + "Otsikko: " + this.title + "<br/>"
                + "Tyyppi: " + "Kirja" + "<br/>"
                + "ISBN: " + this.isbn + "<br/>"
                + "Tagit: " + this.tags + "<br/>"
                + "Kommentti: " + this.description;
    }

    @Override
    public ItemType getType() {
        return ItemType.BOOK;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTags() {
        return tags;
    }

    public String getDescription() {
        return description;
    }
}
