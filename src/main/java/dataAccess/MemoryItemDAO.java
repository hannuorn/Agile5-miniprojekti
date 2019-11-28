package dataAccess;

import domain.Book;
import domain.Item;
import domain.Link;
import java.util.ArrayList;
import java.util.List;

public class MemoryItemDAO implements DAO<Item, Integer> {

    private ArrayList<Item> items;
    private int nextId = 1;
    
    public MemoryItemDAO(){
        items = new ArrayList<>();
    }
    
    @Override
    public void create(Item item) {
        items.add(item);
        switch (item.getType()) {
            case BOOK:      ((Book) item).setId(nextId);        break;
            case LINK:      ((Link) item).setId(nextId);        break;
        }
        this.nextId++;
    }

    @Override
    public List<Item> list() {
        return items;
    }

    @Override
    public boolean remove(Integer id) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(id)) {
                this.items.remove(i);
                return true;
            }    
        }
        return false;   
    }
    
    @Override
    public Item read(Integer id){
        for (Item i : items){
            if (i.getId().equals(id)){
                return i;
            }
        }
        return null;
    }
    
    @Override
    public boolean update(Item item) {
        return false;
    }
}
