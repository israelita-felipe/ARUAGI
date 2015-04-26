package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.control.abstracts.AbstractController;
import br.edu.uag.aruagi.model.PalavraLatim;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.SelectItem;

public class PalavraLatimController extends AbstractController<PalavraLatim> implements Serializable {

    public PalavraLatimController() {
        super(PalavraLatim.class);
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    @Override
    public PalavraLatim getSelected() {
        if (getCurrent() == null) {
            setCurrent(new PalavraLatim());
            initializeEmbeddableKey();
            setSelectedItemIndex(-1);
        }
        return getCurrent();
    }

    @Override
    public String prepareCreate() {
        setCurrent(new PalavraLatim());
        getCurrent().setStatus(Boolean.TRUE);
        getCurrent().setUsuario(UsuarioSessionController.getUserLogged().getId());
        initializeEmbeddableKey();
        setSelectedItemIndex(-1);
        return "Create";
    }

    @Override
    public void performDestroy() {
        getCurrent().setStatus(Boolean.FALSE);
        super.performDestroy();
    }

    @Override
    public SelectItem[] getItemsAvailableSelectOne() {
        int size = getFacade().count() + 1;
        SelectItem[] items = new SelectItem[size];
        int i = 1;
        items[0] = new SelectItem("", "---");
        for (PalavraLatim x : getFacade().findAll()) {
            items[i++] = new SelectItem(x, x.getPalavra());
        }
        return items;
    }

    @FacesConverter(forClass = PalavraLatim.class)
    public static class PalavraLatimControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PalavraLatimController controller = (PalavraLatimController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "palavraLatimController");
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
            if (object instanceof PalavraLatim) {
                PalavraLatim o = (PalavraLatim) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), PalavraLatim.class.getName()});
                return null;
            }
        }

    }

}
