package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.control.abstracts.AbstractController;
import br.edu.uag.aruagi.model.PalavrasClassificadas;
import br.edu.uag.aruagi.model.PalavrasClassificadasId;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

public class PalavrasClassificadasController extends AbstractController<PalavrasClassificadas> implements Serializable {

    public PalavrasClassificadasController() {
        super(PalavrasClassificadas.class);
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
        getCurrent().setId(new PalavrasClassificadasId());
    }

    @Override
    public PalavrasClassificadas getSelected() {
        if (getCurrent() == null) {
            setCurrent(new PalavrasClassificadas());
            initializeEmbeddableKey();
            setSelectedItemIndex(-1);
        }
        return getCurrent();
    }

    @Override
    public String prepareCreate() {
        setCurrent(new PalavrasClassificadas());
        getCurrent().setStatus(Boolean.TRUE);
        getCurrent().setUsuario(UsuarioSessionController.getUserLogged().getId());
        initializeEmbeddableKey();
        setSelectedItemIndex(-1);
        return "Create";
    }

    public static class PalavrasClassificadasControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PalavrasClassificadasController controller = (PalavrasClassificadasController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "palavrasClassificadasController");
            return controller.get(getKey(value));
        }

        PalavrasClassificadasId getKey(String value) {
            PalavrasClassificadasId key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new PalavrasClassificadasId();
            key.setPalavraLatim(Integer.parseInt(values[0]));
            key.setClassificacao(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(PalavrasClassificadasId value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getPalavraLatim());
            sb.append(SEPARATOR);
            sb.append(value.getClassificacao());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof PalavrasClassificadas) {
                PalavrasClassificadas o = (PalavrasClassificadas) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), PalavrasClassificadas.class.getName()});
                return null;
            }
        }

    }

}
