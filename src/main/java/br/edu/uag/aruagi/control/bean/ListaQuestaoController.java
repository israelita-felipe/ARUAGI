package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.control.Facade.ListaQuestaoFacade;
import br.edu.uag.aruagi.control.interfaces.InterfaceController;
import br.edu.uag.aruagi.model.ListaQuestao;
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

public class ListaQuestaoController implements Serializable, InterfaceController<ListaQuestao, Integer> {

    private final ListaQuestaoFacade facade = new ListaQuestaoFacade();
    private List<ListaQuestao> items = null;
    private ListaQuestao selected;

    public ListaQuestaoController() {
    }

    public ListaQuestao getSelected() {
        return selected;
    }

    public void setSelected(ListaQuestao selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ListaQuestaoFacade getFacade() {
        return facade;
    }

    @Override
    public ListaQuestao prepareCreate() {
        selected = new ListaQuestao();
        initializeEmbeddableKey();
        return selected;
    }

    @Override
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("MensagemListaQuestaoCriada"));
    }

    @Override
    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("MensagemListaQuestaoAtualizada"));
    }

    @Override
    public void destroy() {
        getSelected().setStatus(Boolean.FALSE);
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("MensagemListaQuestaoExcluida"));
    }

    @Override
    public List<ListaQuestao> getItems() {
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

    public ListaQuestao getListaQuestao(int id) {
        getFacade().begin();
        ListaQuestao lq = getFacade().find(id);
        getFacade().end();
        return lq;
    }

    @Override
    public List<ListaQuestao> getItemsAvailableSelectMany() {
        return getItems();
    }

    @Override
    public List<ListaQuestao> getItemsAvailableSelectOne() {
        return getItems();
    }

    @FacesConverter(forClass = ListaQuestao.class)
    public static class ListaQuestaoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ListaQuestaoController controller = (ListaQuestaoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "listaQuestaoController");
            return controller.getListaQuestao(getKey(value));
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
            if (object instanceof ListaQuestao) {
                ListaQuestao o = (ListaQuestao) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ListaQuestao.class.getName()});
                return null;
            }
        }

    }

}
