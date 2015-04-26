/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uag.aruagi.control.abstracts;

import br.edu.uag.aruagi.control.Facade.Facade;
import br.edu.uag.aruagi.control.interfaces.InterfaceController;
import br.edu.uag.aruagi.control.interfaces.InterfaceFacade;
import br.edu.uag.aruagi.control.util.jsf.JsfUtil;
import br.edu.uag.aruagi.control.util.jsf.Paginator;
import br.edu.uag.aruagi.model.Postagem;
import br.edu.uag.aruagi.model.Usuario;
import java.io.Serializable;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;

/**
 *
 * @author israel
 * @param <T>
 */
public abstract class AbstractController<T> implements InterfaceController<T> {

    private final InterfaceFacade<T> ejbFacade;
    private T current;
    private DataModel items = null;
    private Paginator pagination;
    private int selectedItemIndex;

    public AbstractController(Class<T> clazz) {
        ejbFacade = new Facade<T>(clazz);
    }

    @Override
    public InterfaceFacade<T> getFacade() {
        return ejbFacade;
    }

    @Override
    public Paginator getPagination() {
        if (pagination == null) {
            pagination = new Paginator(10) {

                @Override
                public int getItemsCount() {
                    if (getFacade().getFacadeClass().equals(Usuario.class)) {
                        Criteria q = getFacade().getSession().createCriteria(Usuario.class);
                        return q.list().size();
                    }
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    if (getFacade().getFacadeClass().equals(Postagem.class)) {
                        Criteria q = getFacade().getSession().createCriteria(Postagem.class);
                        q.add(Property.forName("status").eq(Boolean.TRUE))
                                .addOrder(Order.desc("data"))
                                .addOrder(Order.desc("id"))
                                .setMaxResults(getPageFirstItem() + getPageSize() - getPageFirstItem() + 1)
                                .setFirstResult(getPageFirstItem());
                        return new ListDataModel(q.list());
                    } else if (getFacade().getFacadeClass().equals(Usuario.class)) {
                        Criteria q = getFacade().getSession().createCriteria(Usuario.class);
                        q.setMaxResults(getPageFirstItem() + getPageSize() - getPageFirstItem() + 1)
                                .setFirstResult(getPageFirstItem());
                        return new ListDataModel(q.list());
                    }
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    @Override
    public String prepareList() {
        recreateModel();
        return "List";
    }

    @Override
    public String prepareView() {
        current = (T) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    @Override
    public String create() {
        try {
            getFacade().create(current);
            //JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AgenciaCreated"));
            return prepareList();
        } catch (Exception e) {
            //JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    @Override
    public String prepareEdit() {
        current = (T) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    @Override
    public String update() {
        try {
            getFacade().edit(current);
            //JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AgenciaUpdated"));
            return "View";
        } catch (Exception e) {
            //JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    /**
     *
     * @return
     */
    @Override
    public String destroy() {
        current = (T) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    @Override
    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    /**
     *
     */
    @Override
    public void performDestroy() {
        try {
            getFacade().remove(current);
            //JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AgenciaDeleted"));
        } catch (Exception e) {
            //JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    @Override
    public void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    @Override
    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    @Override
    public void recreateModel() {
        items = null;
    }

    @Override
    public void recreatePagination() {
        pagination = null;
    }

    @Override
    public String first() {
        getPagination().firstPage();
        recreateModel();
        return "List";
    }

    @Override
    public String last() {
        getPagination().lastPage();
        recreateModel();
        return "List";
    }

    @Override
    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    @Override
    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    @Override
    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    @Override
    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    @Override
    public T get(Serializable id) {
        return ejbFacade.find(id);
    }

    public int getSelectedItemIndex() {
        return selectedItemIndex;
    }

    public void setSelectedItemIndex(int selectedItemIndex) {
        this.selectedItemIndex = selectedItemIndex;
    }

    public T getCurrent() {
        return current;
    }

    public void setCurrent(T current) {
        this.current = current;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.current != null ? this.current.hashCode() : 0);
        hash = 97 * hash + (this.items != null ? this.items.hashCode() : 0);
        hash = 97 * hash + (this.ejbFacade != null ? this.ejbFacade.hashCode() : 0);
        hash = 97 * hash + (this.pagination != null ? this.pagination.hashCode() : 0);
        hash = 97 * hash + this.selectedItemIndex;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AbstractController<?> other = (AbstractController<?>) obj;
        if (this.current != other.current && (this.current == null || !this.current.equals(other.current))) {
            return false;
        }
        if (this.items != other.items && (this.items == null || !this.items.equals(other.items))) {
            return false;
        }
        if (this.ejbFacade != other.ejbFacade && (this.ejbFacade == null || !this.ejbFacade.equals(other.ejbFacade))) {
            return false;
        }
        if (this.pagination != other.pagination && (this.pagination == null || !this.pagination.equals(other.pagination))) {
            return false;
        }
        return this.selectedItemIndex == other.selectedItemIndex;
    }

}
