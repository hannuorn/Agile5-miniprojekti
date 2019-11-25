package dataAccess;

import java.util.List;

public interface DAO<T, K> {
    
    void create(T object);
    
    List<T> list();
    
}
