package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.control.Facade.LinksQuestaoFacade;
import br.edu.uag.aruagi.control.interfaces.InterfaceController;
import br.edu.uag.aruagi.model.LinksQuestao;
import br.edu.uag.aruagi.model.LinksQuestaoId;
import br.edu.uag.aruagi.control.util.jsf.JsfUtil;
import br.edu.uag.aruagi.control.util.jsf.JsfUtil.PersistAction;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

public class LinksQuestaoController implements Serializable, InterfaceController<LinksQuestao, LinksQuestaoId> {

    private final LinksQuestaoFacade facade = new LinksQuestaoFacade();
    private List<LinksQuestao> items = null;
    private LinksQuestao selected;

    public LinksQuestaoController() {
    }

    public LinksQuestao getSelected() {
        return selected;
    }

    public void setSelected(LinksQuestao selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
        selected.setId(new LinksQuestaoId());
    }

    private LinksQuestaoFacade getFacade() {
        return facade;
    }

    @Override
    public LinksQuestao prepareCreate() {
        selected = new LinksQuestao();
        initializeEmbeddableKey();
        return selected;
    }

    @Override
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("LinksQuestaoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    @Override
    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("LinksQuestaoUpdated"));
    }

    @Override
    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("LinksQuestaoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    @Override
    public List<LinksQuestao> getItems() {
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
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
        getFacade().end();
    }

    public LinksQuestao getLinksQuestao(LinksQuestaoId id) {
        getFacade().begin();
        LinksQuestao lq = getFacade().find(id);
        getFacade().end();
        return lq;
    }

    @Override
    public List<LinksQuestao> getItemsAvailableSelectMany() {
        return getItems();
    }

    @Override
    public List<LinksQuestao> getItemsAvailableSelectOne() {
        return getItems();
    }

    @FacesConverter(forClass = LinksQuestao.class)
    public static class LinksQuestaoControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            LinksQuestaoController controller = (LinksQuestaoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "linksQuestaoController");
            return controller.getLinksQuestao(getKey(value));
        }

        LinksQuestaoId getKey(String value) {
            LinksQuestaoId key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new LinksQuestaoId();
            key.setListaQuestoes(Integer.parseInt(values[0]));
            key.setPostagem(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(LinksQuestaoId value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getListaQuestoes());
            sb.append(SEPARATOR);
            sb.append(value.getPostagem());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof LinksQuestao) {
                LinksQuestao o = (LinksQuestao) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), LinksQuestao.class.getName()});
                return null;
            }
        }

    }

}
