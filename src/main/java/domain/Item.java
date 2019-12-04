package domain;

public interface Item {
    
    public String getInfo(); 
    
    public Integer getId();
    
    public String getTitle();
    
    public String getAuthor();
    
    public String getDescription();
    
    public boolean isRead();
    
    public void changeRead();
    
    public ItemType getType();
    
}
