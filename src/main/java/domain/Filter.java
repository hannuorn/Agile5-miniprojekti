package domain;

import dataAccess.DAO;
import java.util.ArrayList;
import java.util.List;

public class Filter {

    private DAO<Item, Integer> itemDao;

    public Filter(DAO<Item, Integer> itemDao) {
        this.itemDao = itemDao;
    }

    public List<Item> filter(int number) {
        switch (number) {
            case 1:
                return filterType(ItemType.BOOK);
            case 2:
                return filterType(ItemType.LINK);
            case 3:
                return filterReadStatus(true);
            default:
                return filterReadStatus(false);
        }
    }

    public List<Item> filterType(ItemType type) {
        List<Item> allItems = itemDao.list();
        List<Item> filteredItems = new ArrayList<>();
        for (int i = 0; i < allItems.size(); i++) {
            if (allItems.get(i).getType() == type) {
                filteredItems.add(allItems.get(i));
            }
        }
        return filteredItems;
    }

    public List<Item> filterReadStatus(boolean status) {
        List<Item> allItems = itemDao.list();
        List<Item> filteredItems = new ArrayList<>();
        for (int i = 0; i < allItems.size(); i++) {
            if (allItems.get(i).isRead() == status) {
                filteredItems.add(allItems.get(i));
            }
        }
        return filteredItems;
    }
}
