package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.model.AtividadeQuestao;
import br.edu.uag.aruagi.control.Facade.AtividadeQuestaoFacade;
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

public class AtividadeQuestaoController implements Serializable, InterfaceController<AtividadeQuestao, Integer> {

    private final AtividadeQuestaoFacade facade = new AtividadeQuestaoFacade();
    private List<AtividadeQuestao> items = null;
    private AtividadeQuestao selected;

    public AtividadeQuestaoController() {
    }

    public AtividadeQuestao getSelected() {
        return selected;
    }

    public void setSelected(AtividadeQuestao selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private AtividadeQuestaoFacade getFacade() {
        return facade;
    }

    @Override
    public AtividadeQuestao prepareCreate() {
        selected = new AtividadeQuestao();
        initializeEmbeddableKey();
        return selected;
    }

    @Override
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("MensagemAtividadeQuestaoCriada"));
    }

    @Override
    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("MensagemAtividadeQuestaoAtualizada"));
    }

    @Override
    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("MensagemAtividadeQuestaoExcluida"));
    }

    @Override
    public List<AtividadeQuestao> getItems() {
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
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
        getFacade().end();
    }

    public AtividadeQuestao getAtividadeQuestao(int id) {
        getFacade().begin();
        AtividadeQuestao aq = getFacade().find(id);
        getFacade().end();
        return aq;
    }

    @Override
    public List<AtividadeQuestao> getItemsAvailableSelectMany() {
        getFacade().begin();
        items = getFacade().findAll();
        getFacade().end();
        return items;
    }

    @Override
    public List<AtividadeQuestao> getItemsAvailableSelectOne() {
        getFacade().begin();
        items = getFacade().findAll();
        getFacade().end();
        return items;
    }

    @FacesConverter(forClass = AtividadeQuestao.class)
    public static class AtividadeQuestaoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AtividadeQuestaoController controller = (AtividadeQuestaoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "atividadeQuestaoController");
            return controller.getAtividadeQuestao(getKey(value));
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
            if (object instanceof AtividadeQuestao) {
                AtividadeQuestao o = (AtividadeQuestao) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), AtividadeQuestao.class.getName()});
                return null;
            }
        }

    }

}
