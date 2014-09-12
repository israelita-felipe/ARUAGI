package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.control.Facade.TraduzFraseFacade;
import br.edu.uag.aruagi.control.interfaces.InterfaceController;
import br.edu.uag.aruagi.model.TraduzFrase;
import br.edu.uag.aruagi.model.TraduzFraseId;
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

public class TraduzFraseController implements Serializable, InterfaceController<TraduzFrase, TraduzFraseId> {

    private final TraduzFraseFacade facade = new TraduzFraseFacade();
    private List<TraduzFrase> items = null;
    private TraduzFrase selected;

    public TraduzFraseController() {
    }

    public TraduzFrase getSelected() {
        return selected;
    }

    public void setSelected(TraduzFrase selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
        selected.setId(new TraduzFraseId());
    }

    private TraduzFraseFacade getFacade() {
        return facade;
    }

    @Override
    public TraduzFrase prepareCreate() {
        selected = new TraduzFrase();
        initializeEmbeddableKey();
        return selected;
    }

    @Override
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("MensagemTraduzFraseCriada"));
    }

    @Override
    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("MensagemTraduzFraseAtualizada"));
    }

    @Override
    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("MensagemTraduzFraseExcluida"));
    }

    @Override
    public List<TraduzFrase> getItems() {
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

    public TraduzFrase getTraduzFrase(TraduzFraseId id) {
        getFacade().begin();
        TraduzFrase tf = getFacade().find(id);
        getFacade().end();
        return tf;
    }

    @Override
    public List<TraduzFrase> getItemsAvailableSelectMany() {
        return getItems();
    }

    @Override
    public List<TraduzFrase> getItemsAvailableSelectOne() {
        return getItems();
    }

    @FacesConverter(forClass = TraduzFrase.class)
    public static class TraduzFraseControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TraduzFraseController controller = (TraduzFraseController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "traduzFraseController");
            return controller.getTraduzFrase(getKey(value));
        }

        TraduzFraseId getKey(String value) {
            TraduzFraseId key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new TraduzFraseId();
            key.setFraseLatim(Integer.parseInt(values[0]));
            key.setFrasePortugues(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(TraduzFraseId value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getFraseLatim());
            sb.append(SEPARATOR);
            sb.append(value.getFrasePortugues());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof TraduzFrase) {
                TraduzFrase o = (TraduzFrase) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), TraduzFrase.class.getName()});
                return null;
            }
        }

    }

}
