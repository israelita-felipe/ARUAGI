package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.control.Facade.PalavrasClassificadasFacade;
import br.edu.uag.aruagi.control.interfaces.InterfaceController;
import br.edu.uag.aruagi.model.PalavrasClassificadas;
import br.edu.uag.aruagi.model.PalavrasClassificadasId;
import br.edu.uag.aruagi.control.util.jsf.JsfUtil;
import br.edu.uag.aruagi.control.util.jsf.JsfUtil.PersistAction;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

public class PalavrasClassificadasController implements Serializable, InterfaceController<PalavrasClassificadas, PalavrasClassificadasId> {

    private final PalavrasClassificadasFacade facade = new PalavrasClassificadasFacade();
    private List<PalavrasClassificadas> items = null;
    private PalavrasClassificadas selected;
    
    public PalavrasClassificadasController() {
    }
    
    public PalavrasClassificadas getSelected() {
        return selected;
    }
    
    public void setSelected(PalavrasClassificadas selected) {
        this.selected = selected;
    }
    
    protected void setEmbeddableKeys() {
    }
    
    protected void initializeEmbeddableKey() {
        selected.setId(new PalavrasClassificadasId());
    }
    
    private PalavrasClassificadasFacade getFacade() {
        return facade;
    }
    
    @Override
    public PalavrasClassificadas prepareCreate() {
        selected = new PalavrasClassificadas();
        initializeEmbeddableKey();
        return selected;
    }
    
    @Override
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("MensagemPalavraClassificadaCriada"));
    }
    
    @Override
    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("MensagemPalavraClassificadaAtualizada"));
    }
    
    @Override
    public void destroy() {
        getSelected().setStatus(Boolean.FALSE);
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("MensagemPalavraClassificadaExcluida"));
    }
    
    @Override
    public List<PalavrasClassificadas> getItems() {
        getFacade().begin();
        items = getFacade().findAll();
        getFacade().end();
        return items;
    }
    
    private void persist(PersistAction persistAction, String successMessage) {
        getFacade().begin();
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction == PersistAction.CREATE) {
                    getSelected().setStatus(Boolean.TRUE);
                    getSelected().setUsuario(UsuarioSessionController.getUserLogged().getId());
                    getFacade().create(selected);
                } else if (persistAction == PersistAction.UPDATE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
        getFacade().end();
    }
    
    public PalavrasClassificadas getPalavrasClassificadas(PalavrasClassificadasId id) {
        getFacade().begin();
        PalavrasClassificadas pc = getFacade().find(id);
        getFacade().end();
        return pc;
    }
    
    @Override
    public List<PalavrasClassificadas> getItemsAvailableSelectMany() {
        return getItems();
    }
    
    @Override
    public List<PalavrasClassificadas> getItemsAvailableSelectOne() {
        return getItems();
    }
    
    @FacesConverter(forClass = PalavrasClassificadas.class)
    public static class PalavrasClassificadasControllerConverter implements Converter {
        
        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";
        
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PalavrasClassificadasController controller = (PalavrasClassificadasController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "palavrasClassificadasController");
            return controller.getPalavrasClassificadas(getKey(value));
        }
        
        PalavrasClassificadasId getKey(String value) {
            PalavrasClassificadasId key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new PalavrasClassificadasId();
            key.setPalavraLatim(Integer.parseInt(values[0]));
            key.setClassificacao(Integer.parseInt(values[1]));
            return key;
        }
        
        String getStringKey(PalavrasClassificadasId value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getPalavraLatim());
            sb.append(SEPARATOR);
            sb.append(value.getClassificacao());
            return sb.toString();
        }
        
        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof PalavrasClassificadas) {
                PalavrasClassificadas o = (PalavrasClassificadas) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), PalavrasClassificadas.class.getName()});
                return null;
            }
        }
        
    }
    
}
