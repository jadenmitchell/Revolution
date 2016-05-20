package org.mdev.revolution.database.dao;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("unchecked")
public interface GenericDAO<T, K extends Serializable> {
    K create(T object);
    T read(K id);
    void update(T object);
    void delete(T object);
    void flush();
    List<T> getByProperty(String property, Object value);
    T getByPropertyUnique(String property, Object value);
}