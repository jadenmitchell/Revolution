package org.mdev.revolution.database.dao;

import org.hibernate.CacheMode;
import org.mdev.revolution.Revolution;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.SingularAttribute;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;

@SuppressWarnings("unchecked")
public abstract class GenericJpaDao<T, K extends Serializable> {
    @Inject
    @PersistenceContext
    private EntityManager entityManager;

    protected EntityManager getEntityManager() {
        if (entityManager == null) {
            entityManager = Revolution.getInstance().getDatabaseManager().getInjector().getInstance(EntityManager.class);
        }
        return entityManager;
    }

    private Class clazz;

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public GenericJpaDao() {
        entityManager = Revolution.getInstance().getDatabaseManager().createEntityManager();
    }

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void update(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(K id) {
        Object entity = getEntityManager().find(clazz, id);
        remove((T) entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public void delete(T object) {
        getEntityManager().detach(object);
    }

    public void save(T object) {
        if (entityManager.contains(object)) {
            update(object);
        } else {
            create(object);
        }
    }

    public void flush() {
        getEntityManager().flush();
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        final CriteriaQuery<T> criteriaQuery = getEntityManager().getCriteriaBuilder()
                .createQuery(clazz);
        criteriaQuery.select(criteriaQuery.from(clazz));
        return getEntityManager().createQuery(criteriaQuery).getResultList();
    }

    @SuppressWarnings("unchecked")
    private TypedQuery<T> findTypedQueryByProperty(String property, Object value) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery criteriaQuery = criteriaBuilder
                .createQuery(clazz);
        final Root<T> root = criteriaQuery.from(clazz);
        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get(property), value));
        return entityManager.createQuery(criteriaQuery);
    }

    @SuppressWarnings("unchecked")
    private TypedQuery<T> findTypedQueryByProperty(final SingularAttribute<T, ? extends Object> property, final Object value) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<T> criteriaQuery = criteriaBuilder
                .createQuery(clazz);
        final Root<T> root = criteriaQuery.from(clazz);
        criteriaQuery.select(root);
        /*if (orderByProperty != null) {
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get(orderByProperty)));
        }*/
        if (value instanceof String) {
            final Expression<String> propertyObject = (Expression<String>) root.get(property);
            final Predicate condition = criteriaBuilder.equal(criteriaBuilder.upper(propertyObject), ((String) value).toUpperCase(Locale.ENGLISH));
            criteriaQuery.where(condition);
        } else {
            final Predicate condition = criteriaBuilder.equal(root.get(property), value);
            criteriaQuery.where(condition);
        }
        return entityManager
                .createQuery(criteriaQuery);
    }

    @SuppressWarnings("unchecked")
    public List<T> findByProperty(String property, final Object value) {
        return findTypedQueryByProperty(property, value).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<T> findByPropertyLimit(String property, final Object value, int maxLimit) {
        return findTypedQueryByProperty(property, value)
                .setFirstResult(0)
                .setMaxResults(maxLimit)
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    public T findByPropertyUnique(String property, final Object value) {
        return findByPropertyLimit(property, value, 1).get(0);
    }

    public void dispose() {
        entityManager.close();
    }

    private static void addCacheHints(final TypedQuery<?> typedQuery, final String comment) {
        typedQuery.setHint("org.hibernate.cacheMode", CacheMode.NORMAL);
        typedQuery.setHint("org.hibernate.cacheable", Boolean.TRUE);
        typedQuery.setHint("org.hibernate.comment", comment);
    }
}
