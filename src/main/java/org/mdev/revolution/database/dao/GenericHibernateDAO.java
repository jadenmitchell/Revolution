package org.mdev.revolution.database.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("unchecked")
public class GenericHibernateDAO<T, K extends Serializable> implements GenericDAO<T, K> {
    private SessionFactory sessionFactory;
    private Class clazz;

    public GenericHibernateDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public K create(Object entity) {
        return (K) sessionFactory.getCurrentSession().save(entity);
    }

    public T read(K id) {
        Object entity = sessionFactory.getCurrentSession().get(clazz, id);
        return (T) entity;
    }

    public void update(T object) {
        sessionFactory.getCurrentSession().update(object);
    }

    public void delete(T object) {
        sessionFactory.getCurrentSession().delete(object);
    }

    public void flush() {
        sessionFactory.getCurrentSession().flush();
    }

    public List<T> getByProperty(String property, Object value) {
        Session hSession = sessionFactory.getCurrentSession();
        Criteria criteria = hSession.createCriteria(clazz);
        criteria.add(Restrictions.eq(property, value));
        return criteria.list();
    }

    public T getByPropertyUnique(String property, Object value) {
        return (T) sessionFactory.getCurrentSession().createCriteria(clazz).add(
                Restrictions.eq(property, value)
        ).uniqueResult();
    }
}
