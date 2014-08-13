package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.control.Facade.QuestaoTraduzPalavraFacade;
import br.edu.uag.aruagi.model.QuestaoTraduzPalavra;
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

public class QuestaoTraduzPalavraController implements Serializable {

    private QuestaoTraduzPalavraFacade facade = new QuestaoTraduzPalavraFacade();
    private List<QuestaoTraduzPalavra> items = null;
    private QuestaoTraduzPalavra selected;

    public QuestaoTraduzPalavraController() {
    }

    public QuestaoTraduzPalavra getSelected() {
        return selected;
    }

    public void setSelected(QuestaoTraduzPalavra selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private QuestaoTraduzPalavraFacade getFacade() {
        return facade;
    }

    public QuestaoTraduzPalavra prepareCreate() {
        selected = new QuestaoTraduzPalavra();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("QuestaoTraduzPalavraCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("QuestaoTraduzPalavraUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("QuestaoTraduzPalavraDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<QuestaoTraduzPalavra> getItems() {
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

    public QuestaoTraduzPalavra getQuestaoTraduzPalavra(int id) {
        getFacade().begin();
        QuestaoTraduzPalavra qtp = getFacade().find(id);
        getFacade().end();
        return qtp;
    }

    public List<QuestaoTraduzPalavra> getItemsAvailableSelectMany() {
        return getItems();
    }

    public List<QuestaoTraduzPalavra> getItemsAvailableSelectOne() {
        return getItems();
    }

    @FacesConverter(forClass = QuestaoTraduzPalavra.class)
    public static class QuestaoTraduzPalavraControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            QuestaoTraduzPalavraController controller = (QuestaoTraduzPalavraController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "questaoTraduzPalavraController");
            return controller.getQuestaoTraduzPalavra(getKey(value));
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
            if (object instanceof QuestaoTraduzPalavra) {
                QuestaoTraduzPalavra o = (QuestaoTraduzPalavra) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), QuestaoTraduzPalavra.class.getName()});
                return null;
            }
        }

    }

}
