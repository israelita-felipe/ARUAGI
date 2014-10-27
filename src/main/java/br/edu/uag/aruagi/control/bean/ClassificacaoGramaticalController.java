package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.model.ClassificacaoGramatical;
import br.edu.uag.aruagi.control.Facade.ClassificacaoGramaticalFacade;
import br.edu.uag.aruagi.control.interfaces.InterfaceController;
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

public class ClassificacaoGramaticalController implements Serializable, InterfaceController<ClassificacaoGramatical, Integer> {
    
    private final ClassificacaoGramaticalFacade facade = new ClassificacaoGramaticalFacade();
    private List<ClassificacaoGramatical> items = null;
    private ClassificacaoGramatical selected;
    
    public ClassificacaoGramaticalController() {
    }
    
    public ClassificacaoGramatical getSelected() {
        return selected;
    }
    
    public void setSelected(ClassificacaoGramatical selected) {
        this.selected = selected;
    }
    
    protected void setEmbeddableKeys() {
    }
    
    protected void initializeEmbeddableKey() {
    }
    
    private ClassificacaoGramaticalFacade getFacade() {
        return facade;
    }
    
    @Override
    public ClassificacaoGramatical prepareCreate() {
        selected = new ClassificacaoGramatical();
        initializeEmbeddableKey();
        return selected;
    }
    
    @Override
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("MensagemClassificacaoGramaticalCriada"));
    }
    
    @Override
    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("MensagemClassificacaoGramaticalAtualizada"));
    }
    
    @Override
    public void destroy() {
        getSelected().setStatus(Boolean.FALSE);
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("MensagemClassificacaoGramaticalExcluida"));
    }
    
    @Override
    public List<ClassificacaoGramatical> getItems() {
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
    
    public ClassificacaoGramatical getClassificacaoGramatical(int id) {
        getFacade().begin();
        ClassificacaoGramatical cg = getFacade().find(id);
        getFacade().end();
        return cg;
    }
    
    @Override
    public List<ClassificacaoGramatical> getItemsAvailableSelectMany() {
        return getItems();
    }
    
    @Override
    public List<ClassificacaoGramatical> getItemsAvailableSelectOne() {
        return getItems();
    }
    
    @FacesConverter(forClass = ClassificacaoGramatical.class)
    public static class ClassificacaoGramaticalControllerConverter implements Converter {
        
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ClassificacaoGramaticalController controller = (ClassificacaoGramaticalController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "classificacaoGramaticalController");
            return controller.getClassificacaoGramatical(getKey(value));
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
            if (object instanceof ClassificacaoGramatical) {
                ClassificacaoGramatical o = (ClassificacaoGramatical) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ClassificacaoGramatical.class.getName()});
                return null;
            }
        }
        
    }
    
}
