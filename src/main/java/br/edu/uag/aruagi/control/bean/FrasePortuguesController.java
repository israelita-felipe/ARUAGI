package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.model.Facade.FrasePortuguesFacade;
import br.edu.uag.aruagi.model.FrasePortugues;
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

public class FrasePortuguesController implements Serializable {

    private FrasePortuguesFacade facade = new FrasePortuguesFacade();
    private List<FrasePortugues> items = null;
    private FrasePortugues selected;

    public FrasePortuguesController() {
    }

    public FrasePortugues getSelected() {
        return selected;
    }

    public void setSelected(FrasePortugues selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private FrasePortuguesFacade getFacade() {
        return facade;
    }

    public FrasePortugues prepareCreate() {
        selected = new FrasePortugues();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("FrasePortuguesCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("FrasePortuguesUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("FrasePortuguesDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<FrasePortugues> getItems() {
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

    public FrasePortugues getFrasePortugues(int id) {
        getFacade().begin();
        FrasePortugues fp = getFacade().find(id);
        getFacade().end();
        return fp;
    }

    public List<FrasePortugues> getItemsAvailableSelectMany() {
        getFacade().begin();
        items = getFacade().findAll();
        getFacade().end();
        return items;
    }

    public List<FrasePortugues> getItemsAvailableSelectOne() {
        getFacade().begin();
        items = getFacade().findAll();
        getFacade().end();
        return items;
    }

    @FacesConverter(forClass = FrasePortugues.class)
    public static class FrasePortuguesControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            FrasePortuguesController controller = (FrasePortuguesController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "frasePortuguesController");
            return controller.getFrasePortugues(getKey(value));
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
            if (object instanceof FrasePortugues) {
                FrasePortugues o = (FrasePortugues) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), FrasePortugues.class.getName()});
                return null;
            }
        }

    }

}
