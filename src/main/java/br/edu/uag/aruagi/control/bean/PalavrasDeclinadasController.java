package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.control.abstracts.AbstractController;
import br.edu.uag.aruagi.model.PalavrasDeclinadas;
import br.edu.uag.aruagi.model.PalavrasDeclinadasId;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

public class PalavrasDeclinadasController extends AbstractController<PalavrasDeclinadas> implements Serializable {
    
    public PalavrasDeclinadasController() {
        super(PalavrasDeclinadas.class);
    }
    
    protected void setEmbeddableKeys() {
    }
    
    protected void initializeEmbeddableKey() {
        getCurrent().setId(new PalavrasDeclinadasId());
    }
    
    @Override
    public PalavrasDeclinadas getSelected() {
        if (getCurrent() == null) {
            setCurrent(new PalavrasDeclinadas());
            initializeEmbeddableKey();
            setSelectedItemIndex(-1);
        }
        return getCurrent();
    }
    
    @Override
    public String prepareCreate() {
        setCurrent(new PalavrasDeclinadas());
        getCurrent().setStatus(Boolean.TRUE);
        getCurrent().setUsuario(UsuarioSessionController.getUserLogged().getId());
        initializeEmbeddableKey();
        setSelectedItemIndex(-1);
        return "Create";
    }
    
    @Override
    public void performDestroy() {
        getCurrent().setStatus(false);
        super.performDestroy(); //To change body of generated methods, choose Tools | Templates.
    }
    
    @FacesConverter(forClass = PalavrasDeclinadas.class)
    public static class PalavrasDeclinadasControllerConverter implements Converter {
        
        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";
        
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PalavrasDeclinadasController controller = (PalavrasDeclinadasController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "palavrasDeclinadasController");
            return controller.get(getKey(value));
        }
        
        PalavrasDeclinadasId getKey(String value) {
            PalavrasDeclinadasId key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new PalavrasDeclinadasId();
            key.setPalavraLatim(Integer.parseInt(values[0]));
            key.setPalavraDeclinada(Integer.parseInt(values[1]));
            return key;
        }
        
        String getStringKey(PalavrasDeclinadasId value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getPalavraLatim());
            sb.append(SEPARATOR);
            sb.append(value.getPalavraDeclinada());
            return sb.toString();
        }
        
        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof PalavrasDeclinadas) {
                PalavrasDeclinadas o = (PalavrasDeclinadas) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), PalavrasDeclinadas.class.getName()});
                return null;
            }
        }
        
    }
    
}
