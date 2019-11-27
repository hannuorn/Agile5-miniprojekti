package domain;

public class Link implements Item {

    private String id;
    private String author;
    private String title;
    private String url;
    private String description;
    private Boolean read;
    //private String linkType; ??

    public Link(String id, String author, String title, String url, String description) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.url = url;
        this.description = description;
        this.read = false;
    }

    public String getInfo() {
        return "Kirjoittaja: " + this.author + "<br/>"
                + "Otsikko: " + this.title + "<br/>"
                + "Tyyppi: " + "Linkki" + "<br/>"
                + "URL: " + this.url + "<br/>"
                + "Kommentti: " + this.description;
    }

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public boolean isRead() {
        return this.read;
    }

    public Type getType() {
        return Type.LINK;
    }
}
