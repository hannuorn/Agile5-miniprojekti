package dataAccess;

import domain.Item;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO implements DAO<Item, String> {

    private ArrayList<Item> items;
    
    public ItemDAO(){
        items = new ArrayList<>();
    }
    
    public void create(Item item) {
        items.add(item);
    }

    public List<Item> list() {
        return items;
    }

    public boolean remove(String id) {
        for(int i = 0; i < items.size(); i++) {
            if(items.get(i).getId().equals(id)) {
                this.items.remove(i);
                return true;
            }    
        }
        return false;   
    }
    
    @Override
    public Item read(String id){
        for(Item i: items){
            if(i.getId().equals(id)){
                return i;
            }
        }
        return null;
    }
}
