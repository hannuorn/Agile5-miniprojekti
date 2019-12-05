package domain;

public interface Item {
    
    public String getInfo(); 
    
    public Integer getId();
    
    public String getTitle();
    
    public boolean isRead();
    
    public void changeRead();
    
    public ItemType getType();
    
    public Boolean getIsVideo();
}
