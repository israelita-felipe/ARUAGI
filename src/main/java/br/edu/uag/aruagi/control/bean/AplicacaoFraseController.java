package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.model.AplicacaoFrase;
import br.edu.uag.aruagi.model.AplicacaoFraseId;
import br.edu.uag.aruagi.control.abstracts.AbstractController;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

public class AplicacaoFraseController extends AbstractController<AplicacaoFrase> implements Serializable {
    
    public AplicacaoFraseController() {
        super(AplicacaoFrase.class);
    }
    
    protected void setEmbeddableKeys() {
    }
    
    protected void initializeEmbeddableKey() {
        getSelected().setId(new AplicacaoFraseId());
    }
    
    @Override
    public AplicacaoFrase getSelected() {
        if (getCurrent() == null) {
            setCurrent(new AplicacaoFrase());
            initializeEmbeddableKey();
            setSelectedItemIndex(-1);
        }
        return getCurrent();
    }
    
    @Override
    public String prepareCreate() {
        setCurrent(new AplicacaoFrase());
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
    
    @FacesConverter(forClass = AplicacaoFrase.class)
    public static class AplicacaoFraseControllerConverter implements Converter {
        
        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";
        
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AplicacaoFraseController controller = (AplicacaoFraseController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "aplicacaoFraseController");
            return controller.get(getKey(value));
        }
        
        AplicacaoFraseId getKey(String value) {
            AplicacaoFraseId key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new AplicacaoFraseId();
            key.setPalavraLatim(Integer.parseInt(values[0]));
            key.setFraseLatim(Integer.parseInt(values[1]));
            return key;
        }
        
        String getStringKey(AplicacaoFraseId value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getPalavraLatim());
            sb.append(SEPARATOR);
            sb.append(value.getFraseLatim());
            return sb.toString();
        }
        
        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof AplicacaoFrase) {
                AplicacaoFrase o = (AplicacaoFrase) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), AplicacaoFrase.class.getName()});
                return null;
            }
        }
        
    }
    
}
