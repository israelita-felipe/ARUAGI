package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.control.abstracts.AbstractController;
import br.edu.uag.aruagi.model.TraduzFrase;
import br.edu.uag.aruagi.model.TraduzFraseId;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

public class TraduzFraseController extends AbstractController<TraduzFrase> implements Serializable {
    
    public TraduzFraseController() {
        super(TraduzFrase.class);
    }
    
    protected void setEmbeddableKeys() {
    }
    
    protected void initializeEmbeddableKey() {
        getCurrent().setId(new TraduzFraseId());
    }
    
    @Override
    public TraduzFrase getSelected() {
        if (getCurrent() == null) {
            setCurrent(new TraduzFrase());
            initializeEmbeddableKey();
            setSelectedItemIndex(-1);
        }
        return getCurrent();
    }
    
    @Override
    public String prepareCreate() {
        setCurrent(new TraduzFrase());
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
    
    @FacesConverter(forClass = TraduzFrase.class)
    public static class TraduzFraseControllerConverter implements Converter {
        
        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";
        
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TraduzFraseController controller = (TraduzFraseController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "traduzFraseController");
            return controller.get(getKey(value));
        }
        
        TraduzFraseId getKey(String value) {
            TraduzFraseId key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new TraduzFraseId();
            key.setFraseLatim(Integer.parseInt(values[0]));
            key.setFrasePortugues(Integer.parseInt(values[1]));
            return key;
        }
        
        String getStringKey(TraduzFraseId value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getFraseLatim());
            sb.append(SEPARATOR);
            sb.append(value.getFrasePortugues());
            return sb.toString();
        }
        
        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof TraduzFrase) {
                TraduzFrase o = (TraduzFrase) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), TraduzFrase.class.getName()});
                return null;
            }
        }
        
    }
    
}
