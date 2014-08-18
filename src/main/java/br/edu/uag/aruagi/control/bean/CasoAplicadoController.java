package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.model.CasoAplicado;
import br.edu.uag.aruagi.model.CasoAplicadoId;
import br.edu.uag.aruagi.control.Facade.CasoAplicadoFacade;
import br.edu.uag.aruagi.control.interfaces.InterfaceController;
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

public class CasoAplicadoController implements Serializable, InterfaceController<CasoAplicado, CasoAplicadoId> {

    private final CasoAplicadoFacade facade = new CasoAplicadoFacade();
    private List<CasoAplicado> items = null;
    private CasoAplicado selected;

    public CasoAplicadoController() {
    }

    public CasoAplicado getSelected() {
        return selected;
    }

    public void setSelected(CasoAplicado selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
        selected.setId(new CasoAplicadoId());
    }

    private CasoAplicadoFacade getFacade() {
        return facade;
    }

    @Override
    public CasoAplicado prepareCreate() {
        selected = new CasoAplicado();
        initializeEmbeddableKey();
        return selected;
    }

    @Override
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("CasoAplicadoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    @Override
    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("CasoAplicadoUpdated"));
    }

    @Override
    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("CasoAplicadoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    @Override
    public List<CasoAplicado> getItems() {
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

    public CasoAplicado getCasoAplicado(CasoAplicadoId id) {
        getFacade().begin();
        CasoAplicado ca = getFacade().find(id);
        getFacade().end();
        return ca;
    }

    @Override
    public List<CasoAplicado> getItemsAvailableSelectMany() {
        return getItems();
    }

    @Override
    public List<CasoAplicado> getItemsAvailableSelectOne() {
        return getItems();
    }

    @FacesConverter(forClass = CasoAplicado.class)
    public static class CasoAplicadoControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CasoAplicadoController controller = (CasoAplicadoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "casoAplicadoController");
            return controller.getCasoAplicado(getKey(value));
        }

        CasoAplicadoId getKey(String value) {
            CasoAplicadoId key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new CasoAplicadoId();
            key.setPalavraLatim(Integer.parseInt(values[0]));
            key.setCaso(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(CasoAplicadoId value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getPalavraLatim());
            sb.append(SEPARATOR);
            sb.append(value.getCaso());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof CasoAplicado) {
                CasoAplicado o = (CasoAplicado) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), CasoAplicado.class.getName()});
                return null;
            }
        }

    }

}
