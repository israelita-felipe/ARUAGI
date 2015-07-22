package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.control.abstracts.AbstractController;
import br.edu.uag.aruagi.control.util.jsf.JsfUtil;
import br.edu.uag.aruagi.control.util.jsf.Paginator;
import br.edu.uag.aruagi.model.PalavraLatim;
import br.edu.uag.aruagi.model.TraduzPalavra;
import br.edu.uag.aruagi.model.TraduzPalavraId;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

@ManagedBean(name = "traduzPalavraController")
@SessionScoped
public class TraduzPalavraController extends AbstractController<TraduzPalavra> implements Serializable {
    private String toFind;

    public TraduzPalavraController() {
        super(TraduzPalavra.class);
    }

    @Override
    public TraduzPalavra getSelected() {
        if (getCurrent() == null) {
            setCurrent(new TraduzPalavra());
            initializeEmbeddableKey();
            setSelectedItemIndex(-1);
        }
        return getCurrent();
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
        getSelected().setId(new TraduzPalavraId());
    }

    @Override
    public String prepareCreate() {
        setCurrent(new TraduzPalavra());
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
    
    public String reset(){
        recreateModel();
        recreatePagination();
        return "index.uag?faces-redirect=true";
    }
    
    public String search() {

        DetachedCriteria q = DetachedCriteria.forClass(TraduzPalavra.class);
        q.add(Property.forName("status").eq(Boolean.TRUE));
        q.createAlias("palavraLatim", "pl");
        q.add(Restrictions.like("pl.palavra", "%"+toFind+"%"));

        final List<TraduzPalavra> pl = getFacade().getEntitiesByDetachedCriteria(q);
        System.out.println(pl);
        recreateModel();
        if (pl != null) {
            setPagination(new Paginator(15) {

                @Override
                public int getItemsCount() {
                    return pl.size();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(pl);
                }
            });
        } else {            
            JsfUtil.addErrorMessage(toFind + " não encontrada");
        }
        setItems(getPagination().createPageDataModel());
        return "index.uag?faces-redirect=true";
    }

    public void setToFind(String toFind) {
        this.toFind = toFind;
    }

    public String getToFind() {
        return toFind;
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
            return controller.get(getKey(value));
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
