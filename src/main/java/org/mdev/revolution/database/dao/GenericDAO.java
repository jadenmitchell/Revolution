package org.mdev.revolution.database.dao;

import org.hibernate.criterion.DetachedCriteria;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("unchecked")
public interface GenericDao<T, K extends Serializable> {
    K create(T object);
    T read(K id);
    void update(T object);
    void delete(T object);
    void save(T object);
    void flush();
    List<T> find(DetachedCriteria criteria);
    List<T> findByExample(T object);
    List<T> getByProperty(String property, Object value);
    T getByPropertyUnique(String property, Object value);
}