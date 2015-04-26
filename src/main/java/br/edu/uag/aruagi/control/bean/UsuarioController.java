package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.control.abstracts.AbstractController;
import br.edu.uag.aruagi.control.util.cript.SHA256;
import br.edu.uag.aruagi.model.Usuario;
import br.edu.uag.aruagi.control.util.jsf.JsfUtil;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;

public class UsuarioController extends AbstractController<Usuario> implements Serializable {

    private String confirmPass;

    public UsuarioController() {
        super(Usuario.class);
    }

    public String getConfirmPass() {
        return confirmPass;
    }

    public void setConfirmPass(String confirmPass) {
        this.confirmPass = confirmPass;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    @Override
    public String create() {
        // buscando um usuário com o mesmo login
        DetachedCriteria query = DetachedCriteria.forClass(Usuario.class)
                .add(Property.forName("login").eq(getCurrent().getLogin()));
        Usuario u = getFacade().getEntityByDetachedCriteria(query);
        // se não houver cria, caso contrário informa que já existe
        if (u == null) {
            if (confirmPass.equals(getCurrent().getSenha())) {
                try {
                    getCurrent().setSenha(SHA256.encode(getCurrent().getSenha()));
                    getCurrent().setUsuario(UsuarioSessionController.getUserLogged());
                    return super.create();
                } catch (NoSuchAlgorithmException ex) {
                    JsfUtil.addErrorMessage("Erro de algoritmo ao gerar senha");
                } catch (UnsupportedEncodingException ex) {
                    JsfUtil.addErrorMessage("Erro de codificação ao gerar senha");
                }
            } else {
                JsfUtil.addErrorMessage("Senhas não conferem");
            }

        } else {
            JsfUtil.addErrorMessage("E-mail já cadastrado");
        }
        return null;
    }

    @Override
    public void performDestroy() {
        getCurrent().setStatus(Boolean.FALSE);
        super.performDestroy(); //To change body of generated methods, choose Tools | Templates.
    }        

    @Override
    public Usuario getSelected() {
       if (getCurrent() == null) {
            setCurrent(new Usuario());
            setSelectedItemIndex(-1);
        }
        return getCurrent();
    }

    @Override
    public String prepareCreate() {
        setCurrent(new Usuario());
        getCurrent().setStatus(Boolean.TRUE);
        getCurrent().setUsuario(UsuarioSessionController.getUserLogged());
        initializeEmbeddableKey();
        setSelectedItemIndex(-1);
        return "Create";
    }

    public static class UsuarioControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UsuarioController controller = (UsuarioController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "usuarioController");
            return controller.get(getKey(value));
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
