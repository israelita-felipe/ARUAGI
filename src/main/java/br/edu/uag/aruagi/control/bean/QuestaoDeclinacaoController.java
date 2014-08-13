package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.model.Facade.QuestaoDeclinacaoFacade;
import br.edu.uag.aruagi.model.QuestaoDeclinacao;
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

public class QuestaoDeclinacaoController implements Serializable {

    private QuestaoDeclinacaoFacade facade = new QuestaoDeclinacaoFacade();
    private List<QuestaoDeclinacao> items = null;
    private QuestaoDeclinacao selected;

    public QuestaoDeclinacaoController() {
    }

    public QuestaoDeclinacao getSelected() {
        return selected;
    }

    public void setSelected(QuestaoDeclinacao selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private QuestaoDeclinacaoFacade getFacade() {
        return facade;
    }

    public QuestaoDeclinacao prepareCreate() {
        selected = new QuestaoDeclinacao();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("QuestaoDeclinacaoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("QuestaoDeclinacaoUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("QuestaoDeclinacaoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<QuestaoDeclinacao> getItems() {
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

    public QuestaoDeclinacao getQuestaoDeclinacao(int id) {
        getFacade().begin();
        QuestaoDeclinacao qd = getFacade().find(id);
        getFacade().end();
        return qd;
    }

    public List<QuestaoDeclinacao> getItemsAvailableSelectMany() {
        return getItems();
    }

    public List<QuestaoDeclinacao> getItemsAvailableSelectOne() {
        return getItems();
    }

    @FacesConverter(forClass = QuestaoDeclinacao.class)
    public static class QuestaoDeclinacaoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            QuestaoDeclinacaoController controller = (QuestaoDeclinacaoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "questaoDeclinacaoController");
            return controller.getQuestaoDeclinacao(getKey(value));
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
            if (object instanceof QuestaoDeclinacao) {
                QuestaoDeclinacao o = (QuestaoDeclinacao) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), QuestaoDeclinacao.class.getName()});
                return null;
            }
        }

    }

}
