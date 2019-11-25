package dataAccess;

import domain.Item;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO implements DAO<Item, Integer> {

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
}
