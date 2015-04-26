package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.control.abstracts.AbstractController;
import br.edu.uag.aruagi.model.LinksQuestao;
import br.edu.uag.aruagi.model.LinksQuestaoId;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

public class LinksQuestaoController extends AbstractController<LinksQuestao> implements Serializable {

    public LinksQuestaoController() {
        super(LinksQuestao.class);
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
        getSelected().setId(new LinksQuestaoId());
    }

    @Override
    public LinksQuestao getSelected() {
        if (getCurrent() == null) {
            setCurrent(new LinksQuestao());
            initializeEmbeddableKey();
            setSelectedItemIndex(-1);
        }
        return getCurrent();
    }

    @Override
    public String prepareCreate() {
        setCurrent(new LinksQuestao());
        getCurrent().setStatus(Boolean.TRUE);
        getCurrent().setUsuario(UsuarioSessionController.getUserLogged().getId());
        initializeEmbeddableKey();
        setSelectedItemIndex(-1);
        return "Create";
    }

    public static class LinksQuestaoControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            LinksQuestaoController controller = (LinksQuestaoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "linksQuestaoController");
            return controller.get(getKey(value));
        }

        LinksQuestaoId getKey(String value) {
            LinksQuestaoId key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new LinksQuestaoId();
            key.setListaQuestoes(Integer.parseInt(values[0]));
            key.setPostagem(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(LinksQuestaoId value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getListaQuestoes());
            sb.append(SEPARATOR);
            sb.append(value.getPostagem());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof LinksQuestao) {
                LinksQuestao o = (LinksQuestao) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), LinksQuestao.class.getName()});
                return null;
            }
        }

    }

}
