package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.control.abstracts.AbstractController;
import br.edu.uag.aruagi.control.util.support.DateTime;
import br.edu.uag.aruagi.model.Postagem;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

public class PostagemController extends AbstractController<Postagem> implements Serializable {

    public PostagemController() {
        super(Postagem.class);
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    @Override
    public Postagem getSelected() {
        if (getCurrent() == null) {
            setCurrent(new Postagem());
            initializeEmbeddableKey();
            setSelectedItemIndex(-1);
        }
        return getCurrent();
    }

    @Override
    public String prepareCreate() {
        setCurrent(new Postagem());
        getCurrent().setStatus(Boolean.TRUE);
        getCurrent().setUsuario(UsuarioSessionController.getUserLogged().getId());
        getCurrent().setData(DateTime.getCurrentDate());
        initializeEmbeddableKey();
        setSelectedItemIndex(-1);
        return "/private/professor/postagem/Create.uag";
    }

    @Override
    public String prepareList() {
        super.prepareList();
        return "/public/postagem/List.uag";
    }

    @Override
    public void performDestroy() {
        getCurrent().setStatus(Boolean.FALSE);
        super.performDestroy();
    }        

    @Override
    public String prepareEdit() {
        super.prepareEdit();
        return "/private/professor/postagem/Edit.uag";
    }

    @Override
    public String update() {
        super.update();
        return "/public/postagem/View.uag";
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
