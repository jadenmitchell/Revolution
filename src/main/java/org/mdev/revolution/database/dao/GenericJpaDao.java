package org.mdev.revolution.database.dao;

import org.mdev.revolution.Revolution;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

@SuppressWarnings("unchecked")
public abstract class GenericJpaDao<T, K extends Serializable> {
    @Inject
    @PersistenceContext
    private EntityManager entityManager;

    protected EntityManager getEntityManager() {
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
        CriteriaQuery<T> cq = getEntityManager().getCriteriaBuilder()
                .createQuery(clazz);
        cq.select(cq.from(clazz));

        return getEntityManager().createQuery(cq).getResultList();
    }

    @SuppressWarnings("unchecked")
    private TypedQuery<T> find(String property, final Object value) {
        CriteriaBuilder critBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery crit = critBuilder.createQuery();
        Root<T> entity = crit.from(clazz);
        crit.where(critBuilder.equal(entity.get(property), value));
        return entityManager.createQuery(crit);
    }

    @SuppressWarnings("unchecked")
    public List<T> findByProperty(String property, final Object value) {
        return find(property, value).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<T> findByPropertyLimit(String property, final Object value, int maxLimit) {
        return find(property, value)
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
}
