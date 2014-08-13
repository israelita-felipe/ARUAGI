package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.model.Facade.PessoaGramaticalFacade;
import br.edu.uag.aruagi.model.PessoaGramatical;
import br.edu.uag.aruagi.model.controller.util.JsfUtil;
import br.edu.uag.aruagi.model.controller.util.JsfUtil.PersistAction;
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

public class PessoaGramaticalController implements Serializable {

    private PessoaGramaticalFacade facade = new PessoaGramaticalFacade();
    private List<PessoaGramatical> items = null;
    private PessoaGramatical selected;

    public PessoaGramaticalController() {
    }

    public PessoaGramatical getSelected() {
        return selected;
    }

    public void setSelected(PessoaGramatical selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private PessoaGramaticalFacade getFacade() {
        return facade;
    }

    public PessoaGramatical prepareCreate() {
        selected = new PessoaGramatical();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PessoaGramaticalCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PessoaGramaticalUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("PessoaGramaticalDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<PessoaGramatical> getItems() {
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

    public PessoaGramatical getPessoaGramatical(int id) {
        getFacade().begin();
        PessoaGramatical pg = getFacade().find(id);
        getFacade().end();
        return pg;
    }

    public List<PessoaGramatical> getItemsAvailableSelectMany() {
        return getItems();
    }

    public List<PessoaGramatical> getItemsAvailableSelectOne() {
        return getItems();
    }

    @FacesConverter(forClass = PessoaGramatical.class)
    public static class PessoaGramaticalControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PessoaGramaticalController controller = (PessoaGramaticalController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "pessoaGramaticalController");
            return controller.getPessoaGramatical(getKey(value));
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
            if (object instanceof PessoaGramatical) {
                PessoaGramatical o = (PessoaGramatical) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), PessoaGramatical.class.getName()});
                return null;
            }
        }

    }

}
