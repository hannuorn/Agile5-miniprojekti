
package domain;

public enum ItemType {
    BOOK, LINK;
    
    public static int itemTypeToInteger(ItemType itemType) {
        switch (itemType) {
            case BOOK:      return 1;
            case LINK:      return 2;
            default:        return -1;
        }
    }
    
    public static ItemType integerToItemType(int i) {
        switch (i) {
            case 1:         return BOOK;
            case 2:         return LINK;
            default:        return BOOK;
        }
    }
}
