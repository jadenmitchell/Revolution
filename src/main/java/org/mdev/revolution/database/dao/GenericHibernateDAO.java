package org.mdev.revolution.database.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import org.mdev.revolution.utilities.HibernateUtil;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("unchecked")
public abstract class GenericHibernateDao<T, K extends Serializable> implements GenericDao<T, K> {
    private Class clazz;

    public Session getSession() {
        return HibernateUtil.currentSession();
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    @Override
    public K create(Object entity) {
        return (K) getSession().save(entity);
    }

    @Override
    public T read(K id) {
        Object entity = getSession().get(clazz, id);
        return (T) entity;
    }

    @Override
    public void update(T object) {
        getSession().update(object);
    }

    @Override
    public void delete(T object) {
        getSession().delete(object);
    }

    @Override
    public void save(T object) {
        getSession().save(object);
    }

    @Override
    public void flush() {
        getSession().flush();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> find(final DetachedCriteria criteria) {
        return criteria.getExecutableCriteria(getSession()).list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> findByExample(final Object value) {
        Criteria criteria = getSession().createCriteria(clazz);
        Example example = Example.create(value).ignoreCase();
        criteria.add(example);
        return criteria.list();
    }

    @Override
    public List<T> findByProperty(String property, final Object value) {
        Criteria criteria = getSession().createCriteria(clazz);
        criteria.add(Restrictions.eq(property, value));
        return criteria.list();
    }

    @Override
    public T findByPropertyUnique(String property, final Object value) {
        return (T) getSession().createCriteria(clazz).add(
                Restrictions.eq(property, value)
        ).uniqueResult();
    }
}
