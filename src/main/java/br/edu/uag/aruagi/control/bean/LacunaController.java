package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.control.Facade.LacunaFacade;
import br.edu.uag.aruagi.control.interfaces.InterfaceController;
import br.edu.uag.aruagi.model.Lacuna;
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

public class LacunaController implements Serializable, InterfaceController<Lacuna, Integer> {

    private final LacunaFacade facade = new LacunaFacade();
    private List<Lacuna> items = null;
    private Lacuna selected;

    public LacunaController() {
    }

    public Lacuna getSelected() {
        return selected;
    }

    public void setSelected(Lacuna selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private LacunaFacade getFacade() {
        return facade;
    }

    @Override
    public Lacuna prepareCreate() {
        selected = new Lacuna();
        initializeEmbeddableKey();
        return selected;
    }

    @Override
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("MensagemLacunaCriada"));
    }

    @Override
    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("MensagemLacunaAtualizada"));
    }

    @Override
    public void destroy() {
        getSelected().setStatus(Boolean.FALSE);
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("MensagemLacunaExcluida"));
    }

    @Override
    public List<Lacuna> getItems() {
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
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            }
        }
        getFacade().end();
    }

    public Lacuna getLacuna(int id) {
        getFacade().begin();
        Lacuna l = getFacade().find(id);
        getFacade().end();
        return l;
    }

    @Override
    public List<Lacuna> getItemsAvailableSelectMany() {
        return getItems();
    }

    @Override
    public List<Lacuna> getItemsAvailableSelectOne() {
        return getItems();
    }

    @FacesConverter(forClass = Lacuna.class)
    public static class LacunaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            LacunaController controller = (LacunaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "lacunaController");
            return controller.getLacuna(getKey(value));
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
            if (object instanceof Lacuna) {
                Lacuna o = (Lacuna) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Lacuna.class.getName()});
                return null;
            }
        }

    }

}
