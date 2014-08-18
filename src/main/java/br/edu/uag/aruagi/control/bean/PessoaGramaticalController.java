package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.control.Facade.PessoaGramaticalFacade;
import br.edu.uag.aruagi.control.interfaces.InterfaceController;
import br.edu.uag.aruagi.model.PessoaGramatical;
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

public class PessoaGramaticalController implements Serializable, InterfaceController<PessoaGramatical, Integer> {
    
    private final PessoaGramaticalFacade facade = new PessoaGramaticalFacade();
    private List<PessoaGramatical> items = null;
    private PessoaGramatical selected;
    
    public PessoaGramaticalController() {
    }
    
    public PessoaGramatical getSelected() {
        return selected;
    }
    
    public void setSelected(PessoaGramatical selected) {
        this.selected = selected;
    }
    
    protected void setEmbeddableKeys() {
    }
    
    protected void initializeEmbeddableKey() {
    }
    
    private PessoaGramaticalFacade getFacade() {
        return facade;
    }
    
    @Override
    public PessoaGramatical prepareCreate() {
        selected = new PessoaGramatical();
        initializeEmbeddableKey();
        return selected;
    }
    
    @Override
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PessoaGramaticalCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    @Override
    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PessoaGramaticalUpdated"));
    }
    
    @Override
    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("PessoaGramaticalDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    @Override
    public List<PessoaGramatical> getItems() {
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
    
    public PessoaGramatical getPessoaGramatical(int id) {
        getFacade().begin();
        PessoaGramatical pg = getFacade().find(id);
        getFacade().end();
        return pg;
    }
    
    @Override
    public List<PessoaGramatical> getItemsAvailableSelectMany() {
        return getItems();
    }
    
    @Override
    public List<PessoaGramatical> getItemsAvailableSelectOne() {
        return getItems();
    }
    
    @FacesConverter(forClass = PessoaGramatical.class)
    public static class PessoaGramaticalControllerConverter implements Converter {
        
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PessoaGramaticalController controller = (PessoaGramaticalController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "pessoaGramaticalController");
            return controller.getPessoaGramatical(getKey(value));
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
            if (object instanceof PessoaGramatical) {
                PessoaGramatical o = (PessoaGramatical) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), PessoaGramatical.class.getName()});
                return null;
            }
        }
        
    }
    
}
