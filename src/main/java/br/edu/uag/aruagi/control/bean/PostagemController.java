package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.control.util.support.DateTime;
import br.edu.uag.aruagi.model.Facade.PostagemFacade;
import br.edu.uag.aruagi.model.Postagem;
import br.edu.uag.aruagi.model.controller.util.JsfUtil;
import br.edu.uag.aruagi.model.controller.util.JsfUtil.PersistAction;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.hibernate.criterion.Order;

public class PostagemController implements Serializable {

    private final PostagemFacade facade = new PostagemFacade();
    private List<Postagem> items = null;
    private Postagem selected;

    public PostagemController() {
        prepareCreate();
    }

    public Postagem getSelected() {
        return selected;
    }

    public void setSelected(Postagem selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private PostagemFacade getFacade() {
        return facade;
    }

    public Postagem prepareCreate() {
        selected = new Postagem();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        if (getSelected().getData() == null) {
            persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PostagemCreated"));
            if (!JsfUtil.isValidationFailed()) {
                items = null;    // Invalidate list of items to trigger re-query.
            }
        } else {
            update();
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PostagemUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("PostagemDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Postagem> getItems() {
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
                    getSelected().setData(DateTime.getCurrentDate());
                    getSelected().setUsuario(UsuarioSessionController.getUserLogged().getId());
                    getSelected().setStatus(Boolean.TRUE);
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
        prepareCreate();
        getFacade().end();
    }

    public Postagem getPostagem(int id) {
        getFacade().begin();
        Postagem p = getFacade().find(id);
        getFacade().end();
        return p;
    }

    public List<Postagem> getTimeLine() {
        getFacade().begin();
        List<Postagem> timeLine = getFacade().getSession().createCriteria(Postagem.class).addOrder(Order.desc("data")).list();
        getFacade().end();
        return timeLine;
    }

    public List<Postagem> getItemsAvailableSelectMany() {
        return getItems();
    }

    public List<Postagem> getItemsAvailableSelectOne() {
        return getItems();
    }

    @FacesConverter(forClass = Postagem.class)
    public static class PostagemControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PostagemController controller = (PostagemController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "postagemController");
            return controller.getPostagem(getKey(value));
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
            if (object instanceof Postagem) {
                Postagem o = (Postagem) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Postagem.class.getName()});
                return null;
            }
        }

    }

}
