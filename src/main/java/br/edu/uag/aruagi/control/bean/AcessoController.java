package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.control.util.support.DateTime;
import br.edu.uag.aruagi.model.Acesso;
import br.edu.uag.aruagi.model.Facade.AcessoFacade;
import br.edu.uag.aruagi.model.controller.util.JsfUtil;
import br.edu.uag.aruagi.model.controller.util.JsfUtil.PersistAction;

import java.io.Serializable;
import java.text.ParseException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

public class AcessoController implements Serializable {

    private AcessoFacade facade = new AcessoFacade();
    private List<Acesso> items = null;
    private Acesso selected;

    public AcessoController() throws ParseException {
        setSelected(atualizaAcesso());
    }

    public Acesso getSelected() {
        return selected;
    }

    public void setSelected(Acesso selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private AcessoFacade getFacade() {
        if (this.facade == null) {
            this.facade = new AcessoFacade();
        }
        return facade;
    }

    /**
     * Metodo que retorna o acesso atual
     *
     * @return Acesso
     */
    private Acesso atualizaAcesso() throws ParseException {
        Acesso a = getAcesso(DateTime.getCurrentDate());
        if (a == null) {
            a = new Acesso(DateTime.getCurrentDate());
            a.setAcessos(1);
            setSelected(a);
            create();
        } else {
            setSelected(a);
            a.setAcessos(a.getAcessos() + 1);
            update();
        }
        return a;
    }

    /**
     * Calcula o total de acesso
     *
     * @return Integer, total de acessos ate a data atual
     */
    public Integer totalAcessos() {
        int i = 0;
        for(Acesso a:getItems()){
            i+=a.getAcessos();
        }
        return (i + 1);
    }

    public Acesso prepareCreate() {
        selected = new Acesso();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("AcessoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("AcessoUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("AcessoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Acesso> getItems() {
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

    public Acesso getAcesso(java.util.Date id) {
        getFacade().begin();
        Acesso a = getFacade().find(id);
        getFacade().end();
        return a;
    }

    public List<Acesso> getItemsAvailableSelectMany() {
        getFacade().begin();
        List<Acesso> a = getFacade().findAll();
        getFacade().end();
        return a;
    }

    public List<Acesso> getItemsAvailableSelectOne() {
        getFacade().begin();
        List<Acesso> a = getFacade().findAll();
        getFacade().end();
        return a;
    }

    @FacesConverter(forClass = Acesso.class)
    public static class AcessoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AcessoController controller = (AcessoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "acessoController");
            return controller.getAcesso(getKey(value));
        }

        java.util.Date getKey(String value) {
            java.util.Date key;
            key = java.sql.Date.valueOf(value);
            return key;
        }

        String getStringKey(java.util.Date value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Acesso) {
                Acesso o = (Acesso) object;
                return getStringKey(o.getData());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Acesso.class.getName()});
                return null;
            }
        }

    }

}
