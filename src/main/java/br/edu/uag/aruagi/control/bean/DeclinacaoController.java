package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.model.Declinacao;
import br.edu.uag.aruagi.control.abstracts.AbstractController;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.SelectItem;

public class DeclinacaoController extends AbstractController<Declinacao> implements Serializable {
    
    public DeclinacaoController() {
        super(Declinacao.class);
    }
    
    protected void setEmbeddableKeys() {
    }
    
    protected void initializeEmbeddableKey() {
    }
    
    @Override
    public Declinacao getSelected() {
        if (getCurrent() == null) {
            setCurrent(new Declinacao());
            initializeEmbeddableKey();
            setSelectedItemIndex(-1);
        }
        return getCurrent();
    }
    
    @Override
    public String prepareCreate() {
        setCurrent(new Declinacao());
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
    
        @Override
    public SelectItem[] getItemsAvailableSelectOne() {
        int size = getFacade().count() + 1;
        SelectItem[] items = new SelectItem[size];
        int i = 1;
        items[0] = new SelectItem("", "---");
        for (Declinacao x : getFacade().findAll()) {
            items[i++] = new SelectItem(x, x.getDescricao()+" - "+x.getId()+" declinação");
        }
        return items;
    }
    
    @FacesConverter(forClass = Declinacao.class)
    public static class DeclinacaoControllerConverter implements Converter {
        
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DeclinacaoController controller = (DeclinacaoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "declinacaoController");
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
            if (object instanceof Declinacao) {
                Declinacao o = (Declinacao) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Declinacao.class.getName()});
                return null;
            }
        }
        
    }
    
}
