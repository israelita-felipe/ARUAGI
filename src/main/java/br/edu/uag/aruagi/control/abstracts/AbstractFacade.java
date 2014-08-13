/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uag.aruagi.control.abstracts;

import br.edu.uag.aruagi.control.interfaces.InterfaceFacade;
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
public abstract class AbstractFacade<T, ID extends Serializable> implements Serializable, InterfaceFacade<T, ID> {
    
    private Class<T> entityClass;
    
    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    
    @Override
    public Session getSession() {
        return FacesContextUtil.getRequestSession();
    }
    
    @Override
    public void create(T entity) {
        getSession().persist(entity);
    }
    
    @Override
    public void begin() {
        FacesContextUtil.setRequestSession(HibernateUtil.getSessionFactory().openSession());
        FacesContextUtil.getRequestSession().beginTransaction();
    }
    
    @Override
    public void end() {
        FacesContextUtil.getRequestSession().getTransaction().commit();
        FacesContextUtil.getRequestSession().close();
    }
    
    @Override
    public void edit(T entity) {
        getSession().update(entity);
    }
    
    @Override
    public void remove(T entity) {
        getSession().delete(getSession().merge(entity));
    }
    
    @Override
    public T find(ID id) {
        return (T) getSession().get(entityClass, id);
    }
    
    @Override
    public List<T> findAll() {
        return (List<T>) getSession().createCriteria(entityClass).list();
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
        return criteria.getExecutableCriteria(getSession()).list();
    }
}
