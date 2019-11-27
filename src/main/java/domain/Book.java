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
    
   
    @Override
    public boolean isRead(){
        return this.read;
    }
    
    @Override
    public String getId(){
        return this.id;
    }
    
    @Override
    public String getTitle() {
        return this.title;
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

    @Override
    public Type getType() {
        return Type.BOOK;
    }
}

