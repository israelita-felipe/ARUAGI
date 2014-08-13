package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.control.Facade.IndicadorPessoaGramaticalFacade;
import br.edu.uag.aruagi.model.IndicadorPessoaGramatical;
import br.edu.uag.aruagi.model.IndicadorPessoaGramaticalId;
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

public class IndicadorPessoaGramaticalController implements Serializable {

    private final IndicadorPessoaGramaticalFacade facade = new IndicadorPessoaGramaticalFacade();
    private List<IndicadorPessoaGramatical> items = null;
    private IndicadorPessoaGramatical selected;

    public IndicadorPessoaGramaticalController() {
    }

    public IndicadorPessoaGramatical getSelected() {
        return selected;
    }

    public void setSelected(IndicadorPessoaGramatical selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
        selected.setId(new IndicadorPessoaGramaticalId());
    }

    private IndicadorPessoaGramaticalFacade getFacade() {
        return facade;
    }

    public IndicadorPessoaGramatical prepareCreate() {
        selected = new IndicadorPessoaGramatical();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("IndicadorPessoaGramaticalCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("IndicadorPessoaGramaticalUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("IndicadorPessoaGramaticalDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<IndicadorPessoaGramatical> getItems() {
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
                    getSelected().setUsuario(UsuarioSessionController.getUserLogged().getId());
                    getSelected().setStatus(Boolean.TRUE);
                    getFacade().create(selected);
                }else if (persistAction == PersistAction.UPDATE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            }catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
        getFacade().end();
    }

    public IndicadorPessoaGramatical getIndicadorPessoaGramatical(IndicadorPessoaGramaticalId id) {
        getFacade().begin();
        IndicadorPessoaGramatical ipg = getFacade().find(id);
        getFacade().end();
        return ipg;
    }

    public List<IndicadorPessoaGramatical> getItemsAvailableSelectMany() {
        return getItems();
    }

    public List<IndicadorPessoaGramatical> getItemsAvailableSelectOne() {
        return getItems();
    }

    @FacesConverter(forClass = IndicadorPessoaGramatical.class)
    public static class IndicadorPessoaGramaticalControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            IndicadorPessoaGramaticalController controller = (IndicadorPessoaGramaticalController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "indicadorPessoaGramaticalController");
            return controller.getIndicadorPessoaGramatical(getKey(value));
        }

        IndicadorPessoaGramaticalId getKey(String value) {
            IndicadorPessoaGramaticalId key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new IndicadorPessoaGramaticalId();
            key.setTempoVerbal(Integer.parseInt(values[0]));
            key.setPessoaGramatical(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(IndicadorPessoaGramaticalId value) {
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
            if (object instanceof IndicadorPessoaGramatical) {
                IndicadorPessoaGramatical o = (IndicadorPessoaGramatical) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), IndicadorPessoaGramatical.class.getName()});
                return null;
            }
        }

    }

}
