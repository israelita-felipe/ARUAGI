package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.control.abstracts.AbstractController;
import br.edu.uag.aruagi.model.NivelQuestao;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

public class NivelQuestaoController extends AbstractController<NivelQuestao> implements Serializable {

    public NivelQuestaoController() {
        super(NivelQuestao.class);

    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    @Override
    public NivelQuestao getSelected() {
        if (getCurrent() == null) {
            setCurrent(new NivelQuestao());
            initializeEmbeddableKey();
            setSelectedItemIndex(-1);
        }
        return getCurrent();
    }

    @Override
    public String prepareCreate() {
        setCurrent(new NivelQuestao());
        getCurrent().setStatus(Boolean.TRUE);
        getCurrent().setUsuario(UsuarioSessionController.getUserLogged().getId());
        initializeEmbeddableKey();
        setSelectedItemIndex(-1);
        return "Create";
    }

    public static class NivelQuestaoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            NivelQuestaoController controller = (NivelQuestaoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "nivelQuestaoController");
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
            if (object instanceof NivelQuestao) {
                NivelQuestao o = (NivelQuestao) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), NivelQuestao.class.getName()});
                return null;
            }
        }

    }

}
