package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.control.abstracts.AbstractController;
import br.edu.uag.aruagi.model.PalavraLatim;
import br.edu.uag.aruagi.model.PessoaGramatical;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.SelectItem;

public class PessoaGramaticalController extends AbstractController<PessoaGramatical> implements Serializable {

    public PessoaGramaticalController() {
        super(PessoaGramatical.class);
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    @Override
    public PessoaGramatical getSelected() {
        if (getCurrent() == null) {
            setCurrent(new PessoaGramatical());
            initializeEmbeddableKey();
            setSelectedItemIndex(-1);
        }
        return getCurrent();
    }

    @Override
    public String prepareCreate() {
        setCurrent(new PessoaGramatical());
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
        for (PessoaGramatical x : getFacade().findAll()) {
            items[i++] = new SelectItem(x, x.getId() + " - " + x.getFlexao());
        }
        return items;
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
