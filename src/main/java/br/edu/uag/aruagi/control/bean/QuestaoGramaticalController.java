package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.control.Facade.QuestaoGramaticalFacade;
import br.edu.uag.aruagi.control.interfaces.InterfaceController;
import br.edu.uag.aruagi.model.QuestaoGramatical;
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

public class QuestaoGramaticalController implements Serializable, InterfaceController<QuestaoGramatical, Integer> {

    private final QuestaoGramaticalFacade facade = new QuestaoGramaticalFacade();
    private List<QuestaoGramatical> items = null;
    private QuestaoGramatical selected;

    public QuestaoGramaticalController() {
    }

    public QuestaoGramatical getSelected() {
        return selected;
    }

    public void setSelected(QuestaoGramatical selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private QuestaoGramaticalFacade getFacade() {
        return facade;
    }

    @Override
    public QuestaoGramatical prepareCreate() {
        selected = new QuestaoGramatical();
        initializeEmbeddableKey();
        return selected;
    }

    @Override
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("MensagemQuestaoGramaticalCriada"));
    }

    @Override
    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("MensagemQuestaoGramaticalAtualizada"));
    }

    @Override
    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("MensagemQuestaoGramaticalExcluida"));
    }

    @Override
    public List<QuestaoGramatical> getItems() {
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

    public QuestaoGramatical getQuestaoGramatical(int id) {
        getFacade().begin();
        QuestaoGramatical qg = getFacade().find(id);
        getFacade().end();
        return qg;
    }

    @Override
    public List<QuestaoGramatical> getItemsAvailableSelectMany() {
        return getItems();
    }

    @Override
    public List<QuestaoGramatical> getItemsAvailableSelectOne() {
        return getItems();
    }

    @FacesConverter(forClass = QuestaoGramatical.class)
    public static class QuestaoGramaticalControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            QuestaoGramaticalController controller = (QuestaoGramaticalController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "questaoGramaticalController");
            return controller.getQuestaoGramatical(getKey(value));
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
            if (object instanceof QuestaoGramatical) {
                QuestaoGramatical o = (QuestaoGramatical) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), QuestaoGramatical.class.getName()});
                return null;
            }
        }

    }

}
