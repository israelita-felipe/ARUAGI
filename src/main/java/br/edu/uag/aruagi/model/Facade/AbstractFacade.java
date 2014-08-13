/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uag.aruagi.model.Facade;

import br.edu.uag.aruagi.control.util.hibernate.FacesContextUtil;
import br.edu.uag.aruagi.control.util.hibernate.HibernateUtil;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;

/**
 *
 * @author Israel Araújo
 * @param <T>
 * @param <ID>
 */
public abstract class AbstractFacade<T, ID extends Serializable> implements Serializable{

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public Session getSession() {
        return FacesContextUtil.getRequestSession();
    }

    public void create(T entity) {
        getSession().persist(entity);
    }

    public void begin() {
        FacesContextUtil.setRequestSession(HibernateUtil.getSessionFactory().openSession());
        FacesContextUtil.getRequestSession().beginTransaction();
    }

    public void end() {
        FacesContextUtil.getRequestSession().getTransaction().commit();
        FacesContextUtil.getRequestSession().close();
    }

    public void edit(T entity) {
        getSession().update(entity);
    }

    public void remove(T entity) {
        getSession().delete(getSession().merge(entity));
    }

    public T find(ID id) {
        return (T) getSession().get(entityClass, id);
    }

    public List<T> findAll() {
        return (List<T>) getSession().createCriteria(entityClass).list();
    }

    public int count() {
        return findAll().size();
    }

    public T getEntityByDetachedCriteria(DetachedCriteria criteria) {
        return (T) criteria.getExecutableCriteria(getSession()).uniqueResult();
    }

    public List<T> getEntitiesByDetachedCriteria(DetachedCriteria criteria) {
        return criteria.getExecutableCriteria(getSession()).list();
    }
}
