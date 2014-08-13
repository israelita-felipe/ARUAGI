package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.model.Declinacao;
import br.edu.uag.aruagi.model.Facade.DeclinacaoFacade;
import br.edu.uag.aruagi.model.controller.util.JsfUtil;
import br.edu.uag.aruagi.model.controller.util.JsfUtil.PersistAction;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

public class DeclinacaoController implements Serializable {

    private DeclinacaoFacade facade = new DeclinacaoFacade();
    private List<Declinacao> items = null;
    private Declinacao selected;

    public DeclinacaoController() {
    }

    public Declinacao getSelected() {
        return selected;
    }

    public void setSelected(Declinacao selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private DeclinacaoFacade getFacade() {
        return facade;
    }

    public Declinacao prepareCreate() {
        selected = new Declinacao();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("DeclinacaoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("DeclinacaoUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("DeclinacaoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Declinacao> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        getFacade().begin();
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
        getFacade().end();
    }

    public Declinacao getDeclinacao(int id) {
        getFacade().begin();
        Declinacao d = getFacade().find(id);
        getFacade().end();
        return d;
    }

    public List<Declinacao> getItemsAvailableSelectMany() {
        getFacade().begin();
        items = getFacade().findAll();
        getFacade().end();
        return items;
    }

    public List<Declinacao> getItemsAvailableSelectOne() {
        getFacade().begin();
        items = getFacade().findAll();
        getFacade().end();
        return items;
    }

    @FacesConverter(forClass = Declinacao.class)
    public static class DeclinacaoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DeclinacaoController controller = (DeclinacaoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "declinacaoController");
            return controller.getDeclinacao(getKey(value));
        }

        int getKey(String value) {
            int key;
            key = Integer.parseInt(value);
            return key;
        }

        String getStringKey(int value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Declinacao) {
                Declinacao o = (Declinacao) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Declinacao.class.getName()});
                return null;
            }
        }

    }

}
