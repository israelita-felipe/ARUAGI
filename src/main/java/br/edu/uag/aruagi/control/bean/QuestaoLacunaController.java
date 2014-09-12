package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.control.Facade.QuestaoLacunaFacade;
import br.edu.uag.aruagi.control.interfaces.InterfaceController;
import br.edu.uag.aruagi.model.QuestaoLacuna;
import br.edu.uag.aruagi.control.util.jsf.JsfUtil;
import br.edu.uag.aruagi.control.util.jsf.JsfUtil.PersistAction;
import br.edu.uag.aruagi.model.Lacuna;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

public class QuestaoLacunaController implements Serializable, InterfaceController<QuestaoLacuna, Integer> {

    private final QuestaoLacunaFacade facade = new QuestaoLacunaFacade();
    private List<QuestaoLacuna> items = null;
    private QuestaoLacuna selected;

    private Lacuna lacuna;
    private int last = 0;

    public QuestaoLacunaController() {
    }

    /**
     * adiciona uma nova lacuna
     */
    public void add() {
        System.out.println(last);
        boolean add = true;
        lacuna.setInicio(last + 1);
        if (selected.getLacunas() == null) {
            selected.setLacunas(new ArrayList<Lacuna>());
        }
        if (!selected.getLacunas().isEmpty()) {
            if (lacuna.getInicio() <= last) {
                add = false;

            }
        }
        if (add) {
            selected.getLacunas().add(lacuna);
            last = lacuna.getFim();
        }
    }

    public int getLast() {
        return last;
    }

    public void remove() {
        if (lacuna != null) {
            int t = selected.getLacunas().size();
            for (int i = 0; i < t; i++) {
                if (selected.getLacunas().get(i).equals(lacuna)) {
                    selected.getLacunas().remove(i);
                    i = t;
                }
            }
        }
    }

    public void setLacuna(Lacuna lacuna) {
        this.lacuna = lacuna;
    }

    public Lacuna getLacuna() {
        return lacuna;
    }

    public QuestaoLacuna getSelected() {
        return selected;
    }

    public void setSelected(QuestaoLacuna selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private QuestaoLacunaFacade getFacade() {
        return facade;
    }

    @Override
    public QuestaoLacuna prepareCreate() {
        lacuna = new Lacuna();
        selected = new QuestaoLacuna();
        initializeEmbeddableKey();
        return selected;
    }

    @Override
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("MensagemQuestaoLacunaCriada"));
    }

    @Override
    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("MensagemQuestaoLacunaAtualizada"));
    }

    @Override
    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("MensagemQuestaoLacunaExcluida"));
    }

    @Override
    public List<QuestaoLacuna> getItems() {
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
                    selected.setUsuario(UsuarioSessionController.getUserLogged().getId());
                    selected.setStatus(Boolean.TRUE);
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

    public QuestaoLacuna getQuestaoLacuna(int id) {
        getFacade().begin();
        QuestaoLacuna ql = getFacade().find(id);
        getFacade().end();
        return ql;
    }

    @Override
    public List<QuestaoLacuna> getItemsAvailableSelectMany() {
        return getItems();
    }

    @Override
    public List<QuestaoLacuna> getItemsAvailableSelectOne() {
        return getItems();
    }

    @FacesConverter(forClass = QuestaoLacuna.class)
    public static class QuestaoLacunaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            QuestaoLacunaController controller = (QuestaoLacunaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "questaoLacunaController");
            return controller.getQuestaoLacuna(getKey(value));
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
            if (object instanceof QuestaoLacuna) {
                QuestaoLacuna o = (QuestaoLacuna) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), QuestaoLacuna.class.getName()});
                return null;
            }
        }

    }

}
