package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.control.abstracts.AbstractController;
import br.edu.uag.aruagi.model.NivelAcesso;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

public class NivelAcessoController extends AbstractController<NivelAcesso> implements Serializable {

    public NivelAcessoController() {
        super(NivelAcesso.class);
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    @Override
    public NivelAcesso getSelected() {
        if (getCurrent() == null) {
            setCurrent(new NivelAcesso());
            initializeEmbeddableKey();
            setSelectedItemIndex(-1);
        }
        return getCurrent();
    }

    @Override
    public String prepareCreate() {
        setCurrent(new NivelAcesso());
        getCurrent().setStatus(Boolean.TRUE);
        initializeEmbeddableKey();
        setSelectedItemIndex(-1);
        return "Create";
    }

    @FacesConverter(forClass = NivelAcesso.class)
    public static class NivelAcessoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            NivelAcessoController controller = (NivelAcessoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "nivelAcessoController");
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
            if (object instanceof NivelAcesso) {
                NivelAcesso o = (NivelAcesso) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), NivelAcesso.class.getName()});
                return null;
            }
        }

    }

}
