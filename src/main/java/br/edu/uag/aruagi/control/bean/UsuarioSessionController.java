package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.control.util.cript.SHA256;
import br.edu.uag.aruagi.control.Facade.UsuarioFacade;
import br.edu.uag.aruagi.control.util.jsf.JsfUtil;
import br.edu.uag.aruagi.model.Usuario;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;

/**
 *
 * @author Israel Araújo
 */
public class UsuarioSessionController implements Serializable {

    private final UsuarioFacade facade = new UsuarioFacade();
    private static Usuario selected = null;
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
        if (u != null) {
            if (u.getSenha().equals(SHA256.encode(getUserLogged().getSenha()))) {
                /**
                 * retornamos a pagina de dados
                 */
                setLogged(true);
                setSelected(u);
                return "/private/homePrivate.xhtml?faces-redirect=true";
            } else {
                JsfUtil.addErrorMessage("Senhas não conferem");
            }
        } else {
            JsfUtil.addErrorMessage("Usuário não cadastrado");
        }
        return null;
    }

    public String doLogout() throws Exception {
        setLogged(false);
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
        UsuarioSessionController.selected = selected;
    }

    public UsuarioFacade getFacade() {
        return facade;
    }

    public Usuario getUsuario(int id) {
        getFacade().begin();
        Usuario u = getFacade().find(id);
        getFacade().end();
        return u;
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
