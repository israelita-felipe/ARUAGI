package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.control.Facade.IndicadorTempoVerbalFacade;
import br.edu.uag.aruagi.control.interfaces.InterfaceController;
import br.edu.uag.aruagi.model.IndicadorTempoVerbal;
import br.edu.uag.aruagi.model.IndicadorTempoVerbalId;
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

public class IndicadorTempoVerbalController implements Serializable, InterfaceController<IndicadorTempoVerbal, IndicadorTempoVerbalId> {

    private final IndicadorTempoVerbalFacade facade = new IndicadorTempoVerbalFacade();
    private List<IndicadorTempoVerbal> items = null;
    private IndicadorTempoVerbal selected;

    public IndicadorTempoVerbalController() {
    }

    public IndicadorTempoVerbal getSelected() {
        return selected;
    }

    public void setSelected(IndicadorTempoVerbal selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
        selected.setId(new IndicadorTempoVerbalId());
    }

    private IndicadorTempoVerbalFacade getFacade() {
        return facade;
    }

    @Override
    public IndicadorTempoVerbal prepareCreate() {
        selected = new IndicadorTempoVerbal();
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
    public List<IndicadorTempoVerbal> getItems() {
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

    public IndicadorTempoVerbal getIndicadorTempoVerbal(IndicadorTempoVerbalId id) {
        return getFacade().find(id);
    }

    @Override
    public List<IndicadorTempoVerbal> getItemsAvailableSelectMany() {
        return getItems();
    }

    @Override
    public List<IndicadorTempoVerbal> getItemsAvailableSelectOne() {
        return getItems();
    }

    @FacesConverter(forClass = IndicadorTempoVerbal.class)
    public static class IndicadorTempoVerbalControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            IndicadorTempoVerbalController controller = (IndicadorTempoVerbalController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "indicadorTempoVerbalController");
            return controller.getIndicadorTempoVerbal(getKey(value));
        }

        IndicadorTempoVerbalId getKey(String value) {
            IndicadorTempoVerbalId key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new IndicadorTempoVerbalId();
            key.setTempoVerbal(Integer.parseInt(values[0]));
            key.setPessoaGramatical(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(IndicadorTempoVerbalId value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getTempoVerbal());
            sb.append(SEPARATOR);
            sb.append(value.getPessoaGramatical());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof IndicadorTempoVerbal) {
                IndicadorTempoVerbal o = (IndicadorTempoVerbal) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), IndicadorTempoVerbal.class.getName()});
                return null;
            }
        }

    }

}
