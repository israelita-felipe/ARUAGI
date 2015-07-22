package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.control.abstracts.AbstractController;
import br.edu.uag.aruagi.model.IndicadorPessoaGramatical;
import br.edu.uag.aruagi.model.IndicadorPessoaGramaticalId;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

public class IndicadorPessoaGramaticalController extends AbstractController<IndicadorPessoaGramatical> implements Serializable {
    
    public IndicadorPessoaGramaticalController() {
        super(IndicadorPessoaGramatical.class);
    }
    
    protected void setEmbeddableKeys() {
    }
    
    protected void initializeEmbeddableKey() {
        getCurrent().setId(new IndicadorPessoaGramaticalId());
    }
    
    @Override
    public IndicadorPessoaGramatical getSelected() {
        if (getCurrent() == null) {
            setCurrent(new IndicadorPessoaGramatical());
            initializeEmbeddableKey();
            setSelectedItemIndex(-1);
        }
        return getCurrent();
    }
    
    @Override
    public String prepareCreate() {
        setCurrent(new IndicadorPessoaGramatical());
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
    
    @FacesConverter(forClass = IndicadorPessoaGramatical.class)
    public static class IndicadorPessoaGramaticalControllerConverter implements Converter {
        
        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";
        
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            IndicadorPessoaGramaticalController controller = (IndicadorPessoaGramaticalController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "indicadorPessoaGramaticalController");
            return controller.get(getKey(value));
        }
        
        IndicadorPessoaGramaticalId getKey(String value) {
            IndicadorPessoaGramaticalId key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new IndicadorPessoaGramaticalId();
            key.setTempoVerbal(Integer.parseInt(values[0]));
            key.setPessoaGramatical(Integer.parseInt(values[1]));
            return key;
        }
        
        String getStringKey(IndicadorPessoaGramaticalId value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getTempoVerbal());
            sb.append(SEPARATOR);
            sb.append(value.getPessoaGramatical());
            return sb.toString();
        }
        
        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof IndicadorPessoaGramatical) {
                IndicadorPessoaGramatical o = (IndicadorPessoaGramatical) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), IndicadorPessoaGramatical.class.getName()});
                return null;
            }
        }
        
    }
    
}
