package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.control.Facade.PalavrasDeclinadasFacade;
import br.edu.uag.aruagi.model.PalavrasDeclinadas;
import br.edu.uag.aruagi.model.PalavrasDeclinadasId;
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

public class PalavrasDeclinadasController implements Serializable {

    private final PalavrasDeclinadasFacade facade = new PalavrasDeclinadasFacade();
    private List<PalavrasDeclinadas> items = null;
    private PalavrasDeclinadas selected;

    public PalavrasDeclinadasController() {
    }

    public PalavrasDeclinadas getSelected() {
        return selected;
    }

    public void setSelected(PalavrasDeclinadas selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
        selected.setId(new PalavrasDeclinadasId());
    }

    private PalavrasDeclinadasFacade getFacade() {
        return facade;
    }

    public PalavrasDeclinadas prepareCreate() {
        selected = new PalavrasDeclinadas();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PalavrasDeclinadasCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PalavrasDeclinadasUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("PalavrasDeclinadasDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<PalavrasDeclinadas> getItems() {
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

    public PalavrasDeclinadas getPalavrasDeclinadas(PalavrasDeclinadasId id) {
        getFacade().begin();
        PalavrasDeclinadas pd = getFacade().find(id);
        getFacade().end();
        return pd;
    }

    public List<PalavrasDeclinadas> getItemsAvailableSelectMany() {
        return getItems();
    }

    public List<PalavrasDeclinadas> getItemsAvailableSelectOne() {
        return getItems();
    }

    @FacesConverter(forClass = PalavrasDeclinadas.class)
    public static class PalavrasDeclinadasControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PalavrasDeclinadasController controller = (PalavrasDeclinadasController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "palavrasDeclinadasController");
            return controller.getPalavrasDeclinadas(getKey(value));
        }

        PalavrasDeclinadasId getKey(String value) {
            PalavrasDeclinadasId key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new PalavrasDeclinadasId();
            key.setPalavraLatim(Integer.parseInt(values[0]));
            key.setPalavraDeclinada(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(PalavrasDeclinadasId value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getPalavraLatim());
            sb.append(SEPARATOR);
            sb.append(value.getPalavraDeclinada());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof PalavrasDeclinadas) {
                PalavrasDeclinadas o = (PalavrasDeclinadas) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), PalavrasDeclinadas.class.getName()});
                return null;
            }
        }

    }

}
