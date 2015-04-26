package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.control.abstracts.AbstractController;
import br.edu.uag.aruagi.model.QuestaoTraduzPalavra;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

public class QuestaoTraduzPalavraController extends AbstractController<QuestaoTraduzPalavra> implements Serializable {

    public QuestaoTraduzPalavraController() {
        super(QuestaoTraduzPalavra.class);
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    @Override
    public QuestaoTraduzPalavra getSelected() {
        if (getCurrent() == null) {
            setCurrent(new QuestaoTraduzPalavra());
            initializeEmbeddableKey();
            setSelectedItemIndex(-1);
        }
        return getCurrent();
    }

    @Override
    public String prepareCreate() {
        setCurrent(new QuestaoTraduzPalavra());
        getCurrent().setStatus(Boolean.TRUE);
        getCurrent().setUsuario(UsuarioSessionController.getUserLogged().getId());
        initializeEmbeddableKey();
        setSelectedItemIndex(-1);
        return "Create";
    }

    public static class QuestaoTraduzPalavraControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            QuestaoTraduzPalavraController controller = (QuestaoTraduzPalavraController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "questaoTraduzPalavraController");
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
            if (object instanceof QuestaoTraduzPalavra) {
                QuestaoTraduzPalavra o = (QuestaoTraduzPalavra) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), QuestaoTraduzPalavra.class.getName()});
                return null;
            }
        }

    }

}
