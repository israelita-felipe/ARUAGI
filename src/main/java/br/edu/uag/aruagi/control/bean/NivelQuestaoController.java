package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.control.Facade.NivelQuestaoFacade;
import br.edu.uag.aruagi.control.interfaces.InterfaceController;
import br.edu.uag.aruagi.model.NivelQuestao;
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

public class NivelQuestaoController implements Serializable, InterfaceController<NivelQuestao, Integer> {

    private final NivelQuestaoFacade facade = new NivelQuestaoFacade();
    private List<NivelQuestao> items = null;
    private NivelQuestao selected;

    public NivelQuestaoController() {
    }

    public NivelQuestao getSelected() {
        return selected;
    }

    public void setSelected(NivelQuestao selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private NivelQuestaoFacade getFacade() {
        return facade;
    }

    @Override
    public NivelQuestao prepareCreate() {
        selected = new NivelQuestao();
        initializeEmbeddableKey();
        return selected;
    }

    @Override
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("NivelQuestaoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    @Override
    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("NivelQuestaoUpdated"));
    }

    @Override
    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("NivelQuestaoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    @Override
    public List<NivelQuestao> getItems() {
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

    public NivelQuestao getNivelQuestao(int id) {
        getFacade().begin();
        NivelQuestao nq = getFacade().find(id);
        getFacade().end();
        return nq;
    }

    @Override
    public List<NivelQuestao> getItemsAvailableSelectMany() {
        return getItems();
    }

    @Override
    public List<NivelQuestao> getItemsAvailableSelectOne() {
        return getItems();
    }

    @FacesConverter(forClass = NivelQuestao.class)
    public static class NivelQuestaoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            NivelQuestaoController controller = (NivelQuestaoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "nivelQuestaoController");
            return controller.getNivelQuestao(getKey(value));
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
            if (object instanceof NivelQuestao) {
                NivelQuestao o = (NivelQuestao) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), NivelQuestao.class.getName()});
                return null;
            }
        }

    }

}
