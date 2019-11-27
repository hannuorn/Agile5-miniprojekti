package dataAccess;

import domain.Item;
import domain.Book;
import domain.Type;

import java.util.ArrayList;
import java.util.List;

public class ItemDAO implements DAO<Item, String> {

    private ArrayList<Item> items;
    private BookDAO bookDao;
    
    public ItemDAO(){
        items = new ArrayList<>();
        bookDao = new BookDAO();
    }
    
    public void create(Item item) {
        items.add(item);
        if (item.getType() == Type.BOOK) {
            Book book = (Book) item;
            bookDao.create(book);
        }
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
