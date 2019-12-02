package domain;

public class Link implements Item {

    private Integer id;
    private String author;
    private String title;
    private String url;
    private String description;
    private Boolean read;
    //private String linkType; ??

    public Link(String author, String title, String url, String description) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.url = url;
        this.description = description;
        this.read = false;
    }

    @Override
    public String getInfo() {
        return "Kirjoittaja: " + this.author + "<br/>"
                + "Otsikko: " + this.title + "<br/>"
                + "Tyyppi: " + "Linkki" + "<br/>"
                + "URL: <a href=\"" + this.url + "\" target=\"_blank\">"
                + this.url + "</a><br/>"
                + "Kommentti: " + this.description;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }
    
    @Override
    public boolean isRead() {
        return this.read;
    }
    
    @Override
    public void changeRead(){
        this.read = !this.read;
    }

    @Override
    public ItemType getType() {
        return ItemType.LINK;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
