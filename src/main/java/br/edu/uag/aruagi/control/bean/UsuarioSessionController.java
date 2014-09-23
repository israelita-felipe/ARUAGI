package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.control.util.cript.SHA256;
import br.edu.uag.aruagi.control.Facade.UsuarioFacade;
import br.edu.uag.aruagi.control.util.jsf.JsfUtil;
import br.edu.uag.aruagi.control.util.support.EmailSender;
import br.edu.uag.aruagi.control.util.support.StringManager;
import br.edu.uag.aruagi.model.Usuario;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.mail.MessagingException;
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
    private boolean admin = false;

    /**
     * campos para redefinição de senha
     */
    private String currentPass;
    private String newPass;
    private String confirmNewPass;

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
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/public/Login.xhtml?faces-redirect=true";
    }

    /**
     * Atualiza a senha do usuário atual
     *
     * Update the current user's password
     *
     * @return
     */
    public String updatePassword() {
        try {
            String s = SHA256.encode(this.currentPass);
            String nP = SHA256.encode(this.newPass);
            String cNP = SHA256.encode(this.confirmNewPass);
            if (s.equals(UsuarioSessionController.getUserLogged().getSenha())) {
                if (nP.equals(cNP)) {
                    getSelected().setSenha(nP);
                    getFacade().edit(getSelected());
                    getFacade().commit();
                    return doLogout();
                } else {
                    JsfUtil.addErrorMessage("nova senha e confirmação não correspondem");
                }
            } else {
                JsfUtil.addErrorMessage("Sua senha de usuário incorreta");
            }
        } catch (NoSuchAlgorithmException e) {
            JsfUtil.addErrorMessage("Erro:\n" + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            JsfUtil.addErrorMessage("Erro:\n" + e.getMessage());
        } catch (Exception ex) {
            JsfUtil.addErrorMessage("Erro desconhecido:\n" + ex.getMessage());
        }
        return null;
    }

    public void requestNewPassword() {
        DetachedCriteria query = DetachedCriteria.forClass(Usuario.class)
                .add(Property.forName("login").eq(getUserLogged().getLogin()));
        getFacade().begin();
        Usuario u = getFacade().getEntityByDetachedCriteria(query);
        getFacade().end();
        if (u != null) {
            try {
                //abrindo uma nova transação
                getFacade().begin();
                //gerando uma nova senha
                u.setSenha(StringManager.sortPassword(10));
                //encriptado a senha com SHA256
                String cP = SHA256.encode(u.getSenha());
                //enviando um email
                EmailSender.sendPreparedEmail(u);
                //setando a nova senha
                u.setSenha(cP);
                //gravando alterações no banco                
                getFacade().edit(u);
                //finalizando a transação
                getFacade().end();
                JsfUtil.addSuccessMessage("Uma nova senha foi enviada para " + u.getLogin() + ", caso não tenha recebido repita o processo.");

            } catch (UnsupportedEncodingException e) {
                JsfUtil.addErrorMessage("[EM-UEE] Algo de errado na redefinição de senha ocorreu, nenhuma mudança foi feita.");
            } catch (MessagingException e) {
                JsfUtil.addErrorMessage("[EM-ME] Algo de errado na redefinição de senha ocorreu, nenhuma mudança foi feita.");
            } catch (Exception e) {
                JsfUtil.addErrorMessage("[EX-E] Ops! algo ocorreu, nada foi modificado");
            }
        } else {
            JsfUtil.addErrorMessage("Usuário não é mebro do ARUAGI");
        }
    }

    /**
     *
     * @return
     */
    public String getCurrentPass() {
        return currentPass;
    }

    /**
     *
     * @param currentPass
     */
    public void setCurrentPass(String currentPass) {
        this.currentPass = currentPass;
    }

    /**
     *
     * @return
     */
    public String getNewPass() {
        return newPass;
    }

    /**
     *
     * @param newPass
     */
    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    /**
     *
     * @return
     */
    public String getConfirmNewPass() {
        return confirmNewPass;
    }

    /**
     *
     * @param confirmNewPass
     */
    public void setConfirmNewPass(String confirmNewPass) {
        this.confirmNewPass = confirmNewPass;
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

    /**
     * verifica se é administrador
     *
     * @return
     */
    public boolean isAdmin() {
        this.admin = false;
        if (getSelected() != null) {
            if (getSelected().getNivelAcesso().getLinkAcesso().equals("administrador")) {
                this.admin = true;
            }
        }
        return this.admin;
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
