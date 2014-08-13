package br.edu.uag.aruagi.control.util.hibernate;

import javax.faces.context.FacesContext;
import org.hibernate.Session;

/**
 *
 * @author Israel Araújo
 */
public class FacesContextUtil {

    // ATRIBUTOS
    private static final String HIBERNATE_SESSION = "hibernate_session";

    // METODOS
    public static void setRequestSession(Session session) {
        FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put(HIBERNATE_SESSION, session);
    }

    public static Session getRequestSession() {
        return (Session) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get(HIBERNATE_SESSION);
    }

    public static Object get(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get(key);
    }

    public static void set(String key, Object object) {
        FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put(key, object);
    }
}
