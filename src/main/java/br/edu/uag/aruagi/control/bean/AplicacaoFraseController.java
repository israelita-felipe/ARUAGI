package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.model.AplicacaoFrase;
import br.edu.uag.aruagi.model.AplicacaoFraseId;
import br.edu.uag.aruagi.control.Facade.AplicacaoFraseFacade;
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

public class AplicacaoFraseController implements Serializable, InterfaceController<AplicacaoFrase, AplicacaoFraseId> {

    private final AplicacaoFraseFacade facade = new AplicacaoFraseFacade();
    private List<AplicacaoFrase> items = null;
    private AplicacaoFrase selected;

    public AplicacaoFraseController() {
    }

    public AplicacaoFrase getSelected() {
        return selected;
    }

    public void setSelected(AplicacaoFrase selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
        selected.setId(new AplicacaoFraseId());
    }

    private AplicacaoFraseFacade getFacade() {
        return facade;
    }

    @Override
    public AplicacaoFrase prepareCreate() {
        selected = new AplicacaoFrase();
        initializeEmbeddableKey();
        return selected;
    }

    @Override
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("MensagemAplicacaoFraseCriada"));
    }

    @Override
    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("MensagemAplicacaoFraseAtualizada"));
    }

    @Override
    public void destroy() {
        getSelected().setStatus(Boolean.FALSE);
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("MensagemAplicacaoFraseExcluida"));
    }

    @Override
    public List<AplicacaoFrase> getItems() {
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

    public AplicacaoFrase getAplicacaoFrase(AplicacaoFraseId id) {
        getFacade().begin();
        AplicacaoFrase af = getFacade().find(id);
        getFacade().end();
        return af;
    }

    @Override
    public List<AplicacaoFrase> getItemsAvailableSelectMany() {
        return getItems();
    }

    @Override
    public List<AplicacaoFrase> getItemsAvailableSelectOne() {
        return getItems();
    }

    @FacesConverter(forClass = AplicacaoFrase.class)
    public static class AplicacaoFraseControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AplicacaoFraseController controller = (AplicacaoFraseController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "aplicacaoFraseController");
            return controller.getAplicacaoFrase(getKey(value));
        }

        AplicacaoFraseId getKey(String value) {
            AplicacaoFraseId key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new AplicacaoFraseId();
            key.setPalavraLatim(Integer.parseInt(values[0]));
            key.setFraseLatim(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(AplicacaoFraseId value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getPalavraLatim());
            sb.append(SEPARATOR);
            sb.append(value.getFraseLatim());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof AplicacaoFrase) {
                AplicacaoFrase o = (AplicacaoFrase) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), AplicacaoFrase.class.getName()});
                return null;
            }
        }

    }

}
