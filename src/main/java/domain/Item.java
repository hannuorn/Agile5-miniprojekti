package domain;

public interface Item {
    public String getInfo();     
    public String getId();
    public String getTitle();
    public boolean isRead();
    public Type getType();
}
