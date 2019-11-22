
package dataAccess;

import domain.Item;
import java.util.ArrayList;
import java.util.List;

public class ItemsDAO implements ItemDAO {

    private ArrayList<Item> items;
    
    public ItemsDAO(){
        items = new ArrayList<>();
    }
    
    public void create(Object item) {
        Item newItem = (Item)item;
        items.add(newItem);
    }

    public List list() {
        return items;
    }
}
