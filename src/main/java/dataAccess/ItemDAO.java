
package dataAccess;

import java.util.List;

public interface ItemDAO<Item, Integer> {
    void create(Item item);
    List<Item> list();
}
