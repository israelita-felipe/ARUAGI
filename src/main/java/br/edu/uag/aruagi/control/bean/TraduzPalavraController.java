package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.control.Facade.TraduzPalavraFacade;
import br.edu.uag.aruagi.control.interfaces.InterfaceController;
import br.edu.uag.aruagi.model.TraduzPalavra;
import br.edu.uag.aruagi.model.TraduzPalavraId;
import br.edu.uag.aruagi.control.util.jsf.JsfUtil;
import br.edu.uag.aruagi.control.util.jsf.JsfUtil.PersistAction;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

public class TraduzPalavraController implements Serializable, InterfaceController<TraduzPalavra, TraduzPalavraId> {

    private final TraduzPalavraFacade facade = new TraduzPalavraFacade();
    private List<TraduzPalavra> items = null;
    private TraduzPalavra selected;

    public TraduzPalavraController() {
    }

    public TraduzPalavra getSelected() {
        return selected;
    }

    public void setSelected(TraduzPalavra selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
        selected.setId(new TraduzPalavraId());
    }

    private TraduzPalavraFacade getFacade() {
        return facade;
    }

    @Override
    public TraduzPalavra prepareCreate() {
        selected = new TraduzPalavra();
        initializeEmbeddableKey();
        return selected;
    }

    @Override
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("TraduzPalavraCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    @Override
    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("TraduzPalavraUpdated"));
    }

    @Override
    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("TraduzPalavraDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    @Override
    public List<TraduzPalavra> getItems() {
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
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
        getFacade().end();
    }

    public TraduzPalavra getTraduzPalavra(TraduzPalavraId id) {
        getFacade().begin();
        TraduzPalavra tp = getFacade().find(id);
        getFacade().end();
        return tp;
    }

    @Override
    public List<TraduzPalavra> getItemsAvailableSelectMany() {
        return getItems();
    }

    @Override
    public List<TraduzPalavra> getItemsAvailableSelectOne() {
        return getItems();
    }

    @FacesConverter(forClass = TraduzPalavra.class)
    public static class TraduzPalavraControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TraduzPalavraController controller = (TraduzPalavraController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "traduzPalavraController");
            return controller.getTraduzPalavra(getKey(value));
        }

        TraduzPalavraId getKey(String value) {
            TraduzPalavraId key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new TraduzPalavraId();
            key.setPalavraLatim(Integer.parseInt(values[0]));
            key.setPalavraPortugues(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(TraduzPalavraId value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getPalavraLatim());
            sb.append(SEPARATOR);
            sb.append(value.getPalavraPortugues());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof TraduzPalavra) {
                TraduzPalavra o = (TraduzPalavra) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), TraduzPalavra.class.getName()});
                return null;
            }
        }

    }

}
