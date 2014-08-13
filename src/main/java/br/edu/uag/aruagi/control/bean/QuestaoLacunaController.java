package ufrpe.uag.control;

import br.edu.uag.aruagi.control.Facade.QuestaoLacunaFacade;
import br.edu.uag.aruagi.model.QuestaoLacuna;
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

public class QuestaoLacunaController implements Serializable {

    private QuestaoLacunaFacade facade = new QuestaoLacunaFacade();
    private List<QuestaoLacuna> items = null;
    private QuestaoLacuna selected;

    public QuestaoLacunaController() {
    }

    public QuestaoLacuna getSelected() {
        return selected;
    }

    public void setSelected(QuestaoLacuna selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private QuestaoLacunaFacade getFacade() {
        return facade;
    }

    public QuestaoLacuna prepareCreate() {
        selected = new QuestaoLacuna();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("QuestaoLacunaCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("QuestaoLacunaUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("QuestaoLacunaDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<QuestaoLacuna> getItems() {
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

    public QuestaoLacuna getQuestaoLacuna(int id) {
        getFacade().begin();
        QuestaoLacuna ql = getFacade().find(id);
        getFacade().end();
        return ql;
    }

    public List<QuestaoLacuna> getItemsAvailableSelectMany() {
        return getItems();
    }

    public List<QuestaoLacuna> getItemsAvailableSelectOne() {
        return getItems();
    }

    @FacesConverter(forClass = QuestaoLacuna.class)
    public static class QuestaoLacunaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            QuestaoLacunaController controller = (QuestaoLacunaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "questaoLacunaController");
            return controller.getQuestaoLacuna(getKey(value));
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
            if (object instanceof QuestaoLacuna) {
                QuestaoLacuna o = (QuestaoLacuna) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), QuestaoLacuna.class.getName()});
                return null;
            }
        }

    }

}
