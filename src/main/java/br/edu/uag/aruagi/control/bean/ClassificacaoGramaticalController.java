package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.model.ClassificacaoGramatical;
import br.edu.uag.aruagi.control.abstracts.AbstractController;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

public class ClassificacaoGramaticalController extends AbstractController<ClassificacaoGramatical> implements Serializable {

    public ClassificacaoGramaticalController() {
        super(ClassificacaoGramatical.class);
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    @Override
    public ClassificacaoGramatical getSelected() {
        if (getCurrent() == null) {
            setCurrent(new ClassificacaoGramatical());
            initializeEmbeddableKey();
            setSelectedItemIndex(-1);
        }
        return getCurrent();
    }

    @Override
    public String prepareCreate() {
        setCurrent(new ClassificacaoGramatical());
        getCurrent().setStatus(Boolean.TRUE);
        getCurrent().setUsuario(UsuarioSessionController.getUserLogged().getId());
        initializeEmbeddableKey();
        setSelectedItemIndex(-1);
        return "Create";
    }

    @Override
    public void performDestroy() {
        getCurrent().setStatus(Boolean.FALSE);
        super.performDestroy(); //To change body of generated methods, choose Tools | Templates.
    }

    @FacesConverter(forClass = ClassificacaoGramatical.class)
    public static class ClassificacaoGramaticalControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ClassificacaoGramaticalController controller = (ClassificacaoGramaticalController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "classificacaoGramaticalController");
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
            if (object instanceof ClassificacaoGramatical) {
                ClassificacaoGramatical o = (ClassificacaoGramatical) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ClassificacaoGramatical.class.getName()});
                return null;
            }
        }

    }

}
