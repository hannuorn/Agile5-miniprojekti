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
        this.id = 0;
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

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRead(boolean read) {
        this.read = read;
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
    
    @Override
    public void changeRead(){
        this.read = !this.read;
    }

    @Override
    public Boolean getIsVideo() {
        return false; 
    }
}
