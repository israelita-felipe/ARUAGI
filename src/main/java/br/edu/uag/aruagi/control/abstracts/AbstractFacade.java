/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uag.aruagi.control.abstracts;

import br.edu.uag.aruagi.control.interfaces.InterfaceFacade;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;

/**
 *
 * @author Israel Araújo
 * @param <T>
 */
public abstract class AbstractFacade<T> implements Serializable, InterfaceFacade<T> {

    private final Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public void create(T entity) {
        getSession().persist(entity);
    }

    @Override
    public void edit(T entity) {
        getSession().merge(entity);
    }

    @Override
    public void remove(T entity) {
        edit(entity);
    }

    @Override
    public T find(Serializable id) {
        return (T) getSession().load(entityClass, id);
    }

    @Override
    public List<T> findAll() {
        DetachedCriteria query = DetachedCriteria.forClass(entityClass);
        query.add(Property.forName("status").eq(Boolean.TRUE));
        return getEntitiesByDetachedCriteria(query);
    }

    @Override
    public List<T> findRange(int[] range) {
        Criteria q = getSession().createCriteria(entityClass);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        q.add(Property.forName("status").eq(Boolean.TRUE));
        return q.list();
    }

    @Override
    public int count() {
        return findAll().size();
    }

    @Override
    public T getEntityByDetachedCriteria(DetachedCriteria criteria) {
        return (T) criteria.getExecutableCriteria(getSession()).uniqueResult();
    }

    @Override
    public List<T> getEntitiesByDetachedCriteria(DetachedCriteria criteria) {
        return (List<T>) criteria.getExecutableCriteria(getSession()).list();
    }

    @Override
    public Class<T> getFacadeClass(){
        return entityClass;
    }
        
}
