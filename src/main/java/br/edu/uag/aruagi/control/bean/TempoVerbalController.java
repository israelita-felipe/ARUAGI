package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.control.Facade.TempoVerbalFacade;
import br.edu.uag.aruagi.control.interfaces.InterfaceController;
import br.edu.uag.aruagi.model.TempoVerbal;
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

public class TempoVerbalController implements Serializable, InterfaceController<TempoVerbal, Integer> {

    private final TempoVerbalFacade facade = new TempoVerbalFacade();
    private List<TempoVerbal> items = null;
    private TempoVerbal selected;

    public TempoVerbalController() {
    }

    public TempoVerbal getSelected() {
        return selected;
    }

    public void setSelected(TempoVerbal selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private TempoVerbalFacade getFacade() {
        return facade;
    }

    @Override
    public TempoVerbal prepareCreate() {
        selected = new TempoVerbal();
        initializeEmbeddableKey();
        return selected;
    }

    @Override
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("MensagemTempoVerbalCriado"));
    }

    @Override
    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("MensagemTempoVerbalAtualizado"));
    }

    @Override
    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("MensagemTempoVerbalExcluido"));
    }

    @Override
    public List<TempoVerbal> getItems() {
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
                if(persistAction == PersistAction.CREATE){
                    getSelected().setStatus(Boolean.TRUE);
                    getSelected().setUsuario(UsuarioSessionController.getUserLogged().getId());
                    getFacade().create(selected);
                }else if (persistAction == PersistAction.UPDATE) {
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

    public TempoVerbal getTempoVerbal(int id) {
        getFacade().begin();
        TempoVerbal tv = getFacade().find(id);
        getFacade().end();
        return tv;
    }

    @Override
    public List<TempoVerbal> getItemsAvailableSelectMany() {
        return getItems();
    }

    @Override
    public List<TempoVerbal> getItemsAvailableSelectOne() {
        return getItems();
    }

    @FacesConverter(forClass = TempoVerbal.class)
    public static class TempoVerbalControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TempoVerbalController controller = (TempoVerbalController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tempoVerbalController");
            return controller.getTempoVerbal(getKey(value));
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
            if (object instanceof TempoVerbal) {
                TempoVerbal o = (TempoVerbal) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), TempoVerbal.class.getName()});
                return null;
            }
        }

    }

}
