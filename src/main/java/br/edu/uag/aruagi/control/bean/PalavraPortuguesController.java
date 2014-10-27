package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.control.Facade.PalavraPortuguesFacade;
import br.edu.uag.aruagi.control.interfaces.InterfaceController;
import br.edu.uag.aruagi.model.PalavraPortugues;
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

public class PalavraPortuguesController implements Serializable, InterfaceController<PalavraPortugues, Integer>{

    private final PalavraPortuguesFacade facade = new PalavraPortuguesFacade();
    private List<PalavraPortugues> items = null;
    private PalavraPortugues selected;

    public PalavraPortuguesController() {
    }

    public PalavraPortugues getSelected() {
        return selected;
    }

    public void setSelected(PalavraPortugues selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private PalavraPortuguesFacade getFacade() {
        return facade;
    }

    @Override
    public PalavraPortugues prepareCreate() {
        selected = new PalavraPortugues();
        initializeEmbeddableKey();
        return selected;
    }

    @Override
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("MensagemPalavraPortuguesCriada"));
    }

    @Override
    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("MensagemPalavraPortuguesAtualizada"));
    }

    @Override
    public void destroy() {
        getSelected().setStatus(Boolean.FALSE);
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("MensagemPalavraPortuguesExcluida"));
    }

    @Override
    public List<PalavraPortugues> getItems() {
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

    public PalavraPortugues getPalavraPortugues(int id) {
        getFacade().begin();
        PalavraPortugues pp = getFacade().find(id);
        getFacade().end();
        return pp;
    }

    @Override
    public List<PalavraPortugues> getItemsAvailableSelectMany() {
        return getItems();
    }

    @Override
    public List<PalavraPortugues> getItemsAvailableSelectOne() {
        return getItems();
    }

    @FacesConverter(forClass = PalavraPortugues.class)
    public static class PalavraPortuguesControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PalavraPortuguesController controller = (PalavraPortuguesController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "palavraPortuguesController");
            return controller.getPalavraPortugues(getKey(value));
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
            if (object instanceof PalavraPortugues) {
                PalavraPortugues o = (PalavraPortugues) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), PalavraPortugues.class.getName()});
                return null;
            }
        }

    }

}
