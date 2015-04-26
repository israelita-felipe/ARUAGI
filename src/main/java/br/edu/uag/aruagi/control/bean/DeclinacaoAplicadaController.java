package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.model.DeclinacaoAplicada;
import br.edu.uag.aruagi.model.DeclinacaoAplicadaId;
import br.edu.uag.aruagi.control.abstracts.AbstractController;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

public class DeclinacaoAplicadaController extends AbstractController<DeclinacaoAplicada> implements Serializable {

    public DeclinacaoAplicadaController() {
        super(DeclinacaoAplicada.class);
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
        getSelected().setId(new DeclinacaoAplicadaId());
    }

    @Override
    public DeclinacaoAplicada getSelected() {
        if (getCurrent() == null) {
            setCurrent(new DeclinacaoAplicada());
            initializeEmbeddableKey();
            setSelectedItemIndex(-1);
        }
        return getCurrent();
    }

    @Override
    public String prepareCreate() {
        setCurrent(new DeclinacaoAplicada());
        getCurrent().setStatus(Boolean.TRUE);
        getCurrent().setUsuario(UsuarioSessionController.getUserLogged().getId());
        initializeEmbeddableKey();
        setSelectedItemIndex(-1);
        return "Create";
    }

    public static class DeclinacaoAplicadaControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DeclinacaoAplicadaController controller = (DeclinacaoAplicadaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "declinacaoAplicadaController");
            return controller.get(getKey(value));
        }

        DeclinacaoAplicadaId getKey(String value) {
            DeclinacaoAplicadaId key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new DeclinacaoAplicadaId();
            key.setDeclinacao(Integer.parseInt(values[0]));
            key.setPalavra(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(DeclinacaoAplicadaId value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getDeclinacao());
            sb.append(SEPARATOR);
            sb.append(value.getPalavra());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof DeclinacaoAplicada) {
                DeclinacaoAplicada o = (DeclinacaoAplicada) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), DeclinacaoAplicada.class.getName()});
                return null;
            }
        }

    }

}
