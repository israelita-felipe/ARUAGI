package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.model.CasoAplicado;
import br.edu.uag.aruagi.model.CasoAplicadoId;
import br.edu.uag.aruagi.control.abstracts.AbstractController;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

public class CasoAplicadoController extends AbstractController<CasoAplicado> implements Serializable {
    
    public CasoAplicadoController() {
        super(CasoAplicado.class);
    }
    
    protected void setEmbeddableKeys() {
    }
    
    protected void initializeEmbeddableKey() {
        getSelected().setId(new CasoAplicadoId());
    }
    
    @Override
    public CasoAplicado getSelected() {
        if (getCurrent() == null) {
            setCurrent(new CasoAplicado());
            initializeEmbeddableKey();
            setSelectedItemIndex(-1);
        }
        return getCurrent();
    }
    
    @Override
    public String prepareCreate() {
        setCurrent(new CasoAplicado());
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
    
    @FacesConverter(forClass = CasoAplicado.class)
    public static class CasoAplicadoControllerConverter implements Converter {
        
        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";
        
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CasoAplicadoController controller = (CasoAplicadoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "casoAplicadoController");
            return controller.get(getKey(value));
        }
        
        CasoAplicadoId getKey(String value) {
            CasoAplicadoId key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new CasoAplicadoId();
            key.setPalavraLatim(Integer.parseInt(values[0]));
            key.setCaso(Integer.parseInt(values[1]));
            return key;
        }
        
        String getStringKey(CasoAplicadoId value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getPalavraLatim());
            sb.append(SEPARATOR);
            sb.append(value.getCaso());
            return sb.toString();
        }
        
        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof CasoAplicado) {
                CasoAplicado o = (CasoAplicado) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), CasoAplicado.class.getName()});
                return null;
            }
        }
        
    }
    
}
