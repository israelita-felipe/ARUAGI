package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.model.Facade.NivelAcessoFacade;
import br.edu.uag.aruagi.model.NivelAcesso;
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

public class NivelAcessoController implements Serializable {

    private NivelAcessoFacade facade = new NivelAcessoFacade();
    private List<NivelAcesso> items = null;
    private NivelAcesso selected;

    public NivelAcessoController() {
    }

    public NivelAcesso getSelected() {
        return selected;
    }

    public void setSelected(NivelAcesso selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private NivelAcessoFacade getFacade() {
        return facade;
    }

    public NivelAcesso prepareCreate() {
        selected = new NivelAcesso();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("NivelAcessoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("NivelAcessoUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("NivelAcessoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<NivelAcesso> getItems() {
        getFacade().begin();
        items = getFacade().findAll();
        getFacade().end();
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

    public NivelAcesso getNivelAcesso(int id) {
        getFacade().begin();
        NivelAcesso na = getFacade().find(id);
        getFacade().end();
        return na;
    }

    public List<NivelAcesso> getItemsAvailableSelectMany() {
        return getItems();
    }

    public List<NivelAcesso> getItemsAvailableSelectOne() {
        return getItems();
    }

    @FacesConverter(forClass = NivelAcesso.class)
    public static class NivelAcessoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            NivelAcessoController controller = (NivelAcessoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "nivelAcessoController");
            return controller.getNivelAcesso(getKey(value));
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
            if (object instanceof NivelAcesso) {
                NivelAcesso o = (NivelAcesso) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), NivelAcesso.class.getName()});
                return null;
            }
        }

    }

}
