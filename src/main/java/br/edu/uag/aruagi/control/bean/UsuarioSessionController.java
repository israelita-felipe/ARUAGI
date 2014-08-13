package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.control.util.cript.SHA256;
import br.edu.uag.aruagi.model.Facade.UsuarioFacade;
import br.edu.uag.aruagi.model.Usuario;
import br.edu.uag.aruagi.model.controller.util.JsfUtil;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.servlet.http.HttpSession;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;

/**
 *
 * @author Israel Araújo
 */
public class UsuarioSessionController {

    private UsuarioFacade facade = new UsuarioFacade();
    private List<Usuario> items = null;
    private static Usuario selected;
    private boolean loged = false;

    public UsuarioSessionController() {
        setSelected(new Usuario());
    }

    /**
     * Realiza o login na aplicacao
     *
     * @return
     * @throws Exception
     */
    public String doLogin() throws Exception {
        DetachedCriteria query = DetachedCriteria.forClass(Usuario.class)
                .add(Property.forName("login").eq(getUserLogged().getLogin()));
        getFacade().begin();
        Usuario u = getFacade().getEntityByDetachedCriteria(query);
        getFacade().end();
        /**
         * verificando se o usuario existe e sua senha confere
         */
        if (u == null || !u.getSenha().equals(SHA256.encode(getUserLogged().getSenha()))) {
            FacesContext.getCurrentInstance().validationFailed();
            return "/public/Login.xhtml?faces-redirect=true";
        }
        /**
         * retornamos a pagina de dados
         */
        setLogged(true);
        setSelected(u);
        return "/private/homePrivate.xhtml?faces-redirect=true";
    }

    public String doLogout() throws Exception {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.invalidate();
        return "/public/Login.xhtml?faces-redirect=true";
    }

    /**
     * @return the loged
     */
    public boolean isLogged() {
        return loged;
    }

    /**
     * @param isLoged the loged to set
     */
    public void setLogged(boolean isLoged) {
        this.loged = isLoged;
    }

    public static Usuario getUserLogged() {
        return selected;
    }

    public Usuario getSelected() {
        return selected;
    }

    public void setSelected(Usuario selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    public UsuarioFacade getFacade() {
        return facade;
    }

    public Usuario prepareCreate() {
        selected = new Usuario();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(JsfUtil.PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("UsuarioCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(JsfUtil.PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("UsuarioUpdated"));
    }

    public void destroy() {
        persist(JsfUtil.PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("UsuarioDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Usuario> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(JsfUtil.PersistAction persistAction, String successMessage) {
        getFacade().begin();
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != JsfUtil.PersistAction.DELETE) {
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

    public Usuario getUsuario(int id) {
        this.facade.begin();
        Usuario u = this.facade.find(id);
        this.facade.end();
        return u;
    }

    public List<Usuario> getItemsAvailableSelectMany() {
        this.facade.begin();
        items = this.facade.findAll();
        this.facade.end();
        return items;
    }

    public List<Usuario> getItemsAvailableSelectOne() {
        this.facade.begin();
        items = this.facade.findAll();
        this.facade.end();
        return items;
    }

    @FacesConverter(forClass = Usuario.class)
    public static class UsuarioControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UsuarioController controller = (UsuarioController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "usuarioController");
            return controller.getUsuario(getKey(value));
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
            if (object instanceof Usuario) {
                Usuario o = (Usuario) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Usuario.class.getName()});
                return null;
            }
        }

    }

}
