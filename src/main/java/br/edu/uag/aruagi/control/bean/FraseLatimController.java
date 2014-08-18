package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.control.Facade.FraseLatimFacade;
import br.edu.uag.aruagi.control.interfaces.InterfaceController;
import br.edu.uag.aruagi.model.FraseLatim;
import br.edu.uag.aruagi.control.util.jsf.JsfUtil;
import br.edu.uag.aruagi.control.util.jsf.JsfUtil.PersistAction;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

public class FraseLatimController implements Serializable, InterfaceController<FraseLatim, Integer> {

    private final FraseLatimFacade facade = new FraseLatimFacade();
    private List<FraseLatim> items = null;
    private FraseLatim selected;

    public FraseLatimController() {
    }

    public FraseLatim getSelected() {
        return selected;
    }

    public void setSelected(FraseLatim selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private FraseLatimFacade getFacade() {
        return facade;
    }

    @Override
    public FraseLatim prepareCreate() {
        selected = new FraseLatim();
        initializeEmbeddableKey();
        return selected;
    }

    @Override
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("FraseLatimCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    @Override
    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("FraseLatimUpdated"));
    }

    @Override
    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("FraseLatimDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    @Override
    public List<FraseLatim> getItems() {
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
                    getSelected().setStatus(Boolean.TRUE);
                    getSelected().setUsuario(UsuarioSessionController.getUserLogged().getId());
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

    public FraseLatim getFraseLatim(int id) {
        getFacade().begin();
        FraseLatim fl = getFacade().find(id);
        getFacade().end();
        return fl;
    }

    @Override
    public List<FraseLatim> getItemsAvailableSelectMany() {
        return getItems();
    }

    @Override
    public List<FraseLatim> getItemsAvailableSelectOne() {
        return getItems();
    }

    @FacesConverter(forClass = FraseLatim.class)
    public static class FraseLatimControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            FraseLatimController controller = (FraseLatimController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "fraseLatimController");
            return controller.getFraseLatim(getKey(value));
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
            if (object instanceof FraseLatim) {
                FraseLatim o = (FraseLatim) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), FraseLatim.class.getName()});
                return null;
            }
        }

    }

}
