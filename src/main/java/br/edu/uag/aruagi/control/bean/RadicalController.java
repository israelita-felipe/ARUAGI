package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.control.Facade.RadicalFacade;
import br.edu.uag.aruagi.control.interfaces.InterfaceController;
import br.edu.uag.aruagi.model.Radical;
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

public class RadicalController implements Serializable, InterfaceController<Radical, Integer> {

    private final RadicalFacade facade = new RadicalFacade();
    private List<Radical> items = null;
    private Radical selected;

    public RadicalController() {
    }

    public Radical getSelected() {
        return selected;
    }

    public void setSelected(Radical selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private RadicalFacade getFacade() {
        return facade;
    }

    @Override
    public Radical prepareCreate() {
        selected = new Radical();
        initializeEmbeddableKey();
        return selected;
    }

    @Override
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("MensagemRadicalCriado"));
    }

    @Override
    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("MensagemRadicalAtualizado"));
    }

    @Override
    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("MensagemRadicalExcluido"));
    }

    @Override
    public List<Radical> getItems() {
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

    public Radical getRadical(int id) {
        getFacade().begin();
        Radical r = getFacade().find(id);
        getFacade().end();
        return r;
    }

    @Override
    public List<Radical> getItemsAvailableSelectMany() {
        return getItems();
    }

    @Override
    public List<Radical> getItemsAvailableSelectOne() {
        return getItems();
    }

    @FacesConverter(forClass = Radical.class)
    public static class RadicalControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RadicalController controller = (RadicalController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "radicalController");
            return controller.getRadical(getKey(value));
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
            if (object instanceof Radical) {
                Radical o = (Radical) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Radical.class.getName()});
                return null;
            }
        }

    }

}
