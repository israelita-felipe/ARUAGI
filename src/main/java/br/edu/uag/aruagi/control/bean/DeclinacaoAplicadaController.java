package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.model.DeclinacaoAplicada;
import br.edu.uag.aruagi.model.DeclinacaoAplicadaId;
import br.edu.uag.aruagi.control.Facade.DeclinacaoAplicadaFacade;
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

public class DeclinacaoAplicadaController implements Serializable, InterfaceController<DeclinacaoAplicada, DeclinacaoAplicadaId> {

    private final DeclinacaoAplicadaFacade facade = new DeclinacaoAplicadaFacade();
    private List<DeclinacaoAplicada> items = null;
    private DeclinacaoAplicada selected;

    public DeclinacaoAplicadaController() {
    }

    public DeclinacaoAplicada getSelected() {
        return selected;
    }

    public void setSelected(DeclinacaoAplicada selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
        selected.setId(new DeclinacaoAplicadaId());
    }

    private DeclinacaoAplicadaFacade getFacade() {
        return facade;
    }

    @Override
    public DeclinacaoAplicada prepareCreate() {
        selected = new DeclinacaoAplicada();
        initializeEmbeddableKey();
        return selected;
    }

    @Override
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("MensagemDeclinacaoAplicadaCriada"));
    }

    @Override
    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("MensagemDeclinacaoAplicadaAtualizada"));
    }

    @Override
    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("MensagemDeclinacaoAplicadaExcluida"));
    }

    @Override
    public List<DeclinacaoAplicada> getItems() {
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

    public DeclinacaoAplicada getDeclinacaoAplicada(DeclinacaoAplicadaId id) {
        getFacade().begin();
        DeclinacaoAplicada da = getFacade().find(id);
        getFacade().end();
        return da;
    }

    @Override
    public List<DeclinacaoAplicada> getItemsAvailableSelectMany() {
        return getItems();
    }

    @Override
    public List<DeclinacaoAplicada> getItemsAvailableSelectOne() {
        return getItems();
    }

    @FacesConverter(forClass = DeclinacaoAplicada.class)
    public static class DeclinacaoAplicadaControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DeclinacaoAplicadaController controller = (DeclinacaoAplicadaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "declinacaoAplicadaController");
            return controller.getDeclinacaoAplicada(getKey(value));
        }

        DeclinacaoAplicadaId getKey(String value) {
            DeclinacaoAplicadaId key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new DeclinacaoAplicadaId();
            key.setDeclinacao(Integer.parseInt(values[0]));
            key.setPalavra(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(DeclinacaoAplicadaId value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getDeclinacao());
            sb.append(SEPARATOR);
            sb.append(value.getPalavra());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof DeclinacaoAplicada) {
                DeclinacaoAplicada o = (DeclinacaoAplicada) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), DeclinacaoAplicada.class.getName()});
                return null;
            }
        }

    }

}
