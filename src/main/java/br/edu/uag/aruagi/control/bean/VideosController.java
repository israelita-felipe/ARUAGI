package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.control.abstracts.AbstractController;
import br.edu.uag.aruagi.model.Videos;
import br.edu.uag.aruagi.control.util.support.StringManager;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class VideosController extends AbstractController<Videos> implements Serializable {

    public VideosController() {
        super(Videos.class);
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    @Override
    public String create() {
        getSelected().setLink(StringManager.prepareLinkVideoIFrame(getSelected().getLink()));
        getSelected().setUsuario(UsuarioSessionController.getUserLogged().getId());
        getSelected().setStatus(Boolean.TRUE);
        AutoPostagemController.create(getSelected());
        return super.create(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String update() {
        getSelected().setLink(StringManager.prepareLinkVideoIFrame(getSelected().getLink()));
        return super.update(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Videos getSelected() {
        if (getCurrent() == null) {
            setCurrent(new Videos());
            initializeEmbeddableKey();
            setSelectedItemIndex(-1);
        }
        return getCurrent();
    }

    @Override
    public String prepareCreate() {
        setCurrent(new Videos());
        getCurrent().setStatus(Boolean.TRUE);
        getCurrent().setUsuario(UsuarioSessionController.getUserLogged().getId());
        initializeEmbeddableKey();
        setSelectedItemIndex(-1);
        return "Create";
    }

    
    public static class VideosControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            VideosController controller = (VideosController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "videosController");
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
            if (object instanceof Videos) {
                Videos o = (Videos) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Videos.class.getName()});
                return null;
            }
        }

    }

}
