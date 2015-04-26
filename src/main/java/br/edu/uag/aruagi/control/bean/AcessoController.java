package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.control.abstracts.AbstractController;
import br.edu.uag.aruagi.control.util.support.DateTime;
import br.edu.uag.aruagi.model.Acesso;
import br.edu.uag.aruagi.control.util.jsf.JsfUtil;

import java.io.Serializable;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

public class AcessoController extends AbstractController<Acesso> implements Serializable {

    public AcessoController() throws ParseException {
        super(Acesso.class);
        atualizaAcesso();
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    @Override
    public String create() {
        super.create();
        return null;
    }

    @Override
    public String update() {
        super.update();
        return null; //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Metodo que retorna o acesso atual
     *
     * @return Acesso
     * @throws java.text.ParseException
     */
    public Acesso atualizaAcesso() throws ParseException {
        prepareCreate();
        if (getCurrent().getAcessos() == 1) {
            create();
        } else {
            update();
        }
        return getCurrent();
    }

    /**
     * Calcula o total de acesso
     *
     * @return Integer, total de acessos ate a data atual
     */
    public Integer totalAcessos() {
        int total = 0;
        for(Acesso a:getFacade().findAll()){
            total+=a.getAcessos();
        }
        return total;
    }

    @Override
    public Acesso getSelected() {
        if (getCurrent() == null) {
            try {
                atualizaAcesso();
            } catch (ParseException ex) {
                JsfUtil.addErrorMessage("Erro ao acessar aplicação");
            }
        }
        return getCurrent();
    }

    @Override
    public String prepareCreate() {
        Acesso atual;
        try {
            atual = getFacade().find(DateTime.getCurrentDate());
            atual.setAcessos(atual.getAcessos() + 1);
        } catch (Exception ex) {
            atual = new Acesso(DateTime.getCurrentDate(), 1);
            atual.setStatus(Boolean.TRUE);
        }
        setCurrent(atual);
        return null;
    }

    @FacesConverter(forClass = Acesso.class)
    public static class AcessoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AcessoController controller = (AcessoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "acessoController");
            return controller.get(getKey(value));
        }

        java.util.Date getKey(String value) {
            java.util.Date key;
            key = java.sql.Date.valueOf(value);
            return key;
        }

        String getStringKey(java.util.Date value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Acesso) {
                Acesso o = (Acesso) object;
                return getStringKey(o.getData());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Acesso.class.getName()});
                return null;
            }
        }

    }

}
