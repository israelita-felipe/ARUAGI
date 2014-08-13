package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.model.Facade.PalavraLatimFacade;
import br.edu.uag.aruagi.model.PalavraLatim;
import br.edu.uag.aruagi.model.controller.util.JsfUtil;
import br.edu.uag.aruagi.model.controller.util.JsfUtil.PersistAction;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

public class PalavraLatimController implements Serializable {

    private PalavraLatimFacade facade = new PalavraLatimFacade();
    private List<PalavraLatim> items = null;
    private PalavraLatim selected;

    public PalavraLatimController() {
    }

    public PalavraLatim getSelected() {
        return selected;
    }

    public void setSelected(PalavraLatim selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private PalavraLatimFacade getFacade() {
        return facade;
    }

    public PalavraLatim prepareCreate() {
        selected = new PalavraLatim();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PalavraLatimCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PalavraLatimUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("PalavraLatimDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<PalavraLatim> getItems() {
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
                if (persistAction == PersistAction.CREATE) {
                    selected.setStatus(Boolean.TRUE);
                    selected.setUsuario(UsuarioSessionController.getUserLogged().getId());
                    getFacade().create(selected);
                } else if (persistAction == PersistAction.UPDATE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
        getFacade().end();
    }

    public PalavraLatim getPalavraLatim(int id) {
        getFacade().begin();
        PalavraLatim pl = getFacade().find(id);
        getFacade().end();
        return pl;
    }

    public List<PalavraLatim> getItemsAvailableSelectMany() {
        return getItems();
    }

    public List<PalavraLatim> getItemsAvailableSelectOne() {
        return getItems();
    }

    @FacesConverter(forClass = PalavraLatim.class)
    public static class PalavraLatimControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PalavraLatimController controller = (PalavraLatimController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "palavraLatimController");
            return controller.getPalavraLatim(getKey(value));
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
            if (object instanceof PalavraLatim) {
                PalavraLatim o = (PalavraLatim) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), PalavraLatim.class.getName()});
                return null;
            }
        }

    }

}
