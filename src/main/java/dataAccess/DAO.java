package dataAccess;

import java.util.List;

public interface DAO<T, K> {

    void create(T object);

    T read(K key);

    boolean remove(K key);

    List<T> list();
}
