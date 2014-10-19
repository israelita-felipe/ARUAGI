package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.control.Facade.VideosFacade;
import br.edu.uag.aruagi.control.interfaces.InterfaceController;
import br.edu.uag.aruagi.model.Videos;
import br.edu.uag.aruagi.control.util.jsf.JsfUtil;
import br.edu.uag.aruagi.control.util.jsf.JsfUtil.PersistAction;
import br.edu.uag.aruagi.control.util.support.StringManager;
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
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

public class VideosController implements Serializable, InterfaceController<Videos, Integer> {

    private final VideosFacade facade = new VideosFacade();
    private List<Videos> items = null;
    private Videos selected;
    private String pesquisa;

    public VideosController() {
        myVideos(0);
    }

    public String getPesquisa() {
        return pesquisa;
    }

    public void setPesquisa(String pesquisa) {
        this.pesquisa = pesquisa;
    }

    public void add() {
        if (this.items == null) {
            this.items = new ArrayList<Videos>();
        }
        items.add(selected);
    }

    public void remove() {
        this.items.remove(selected);
    }

    public void resetList() {
        this.items = null;
    }

    public Videos getSelected() {
        return selected;
    }

    public void setSelected(Videos selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private VideosFacade getFacade() {
        return facade;
    }

    @Override
    public Videos prepareCreate() {
        selected = new Videos();
        initializeEmbeddableKey();
        return selected;
    }

    @Override
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("MensagemVideoCriado"));
    }

    @Override
    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("MensagemVideoAtualizado"));
    }

    @Override
    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("MensagemVideoExcluido"));
    }

    /**
     *
     * @return
     */
    @Override
    public List<Videos> getItems() {
        //retorna apenas os item sem consulta
        return this.items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        getFacade().begin();
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction == PersistAction.CREATE) {
                    /**
                     * prepara o link do vídeo para ser adicionado e utilizado
                     * em um iFrame
                     */
                    getSelected().setLink(StringManager.prepareLinkVideoIFrame(getSelected().getLink()));
                    getSelected().setUsuario(UsuarioSessionController.getUserLogged().getId());
                    getSelected().setStatus(Boolean.TRUE);
                    getFacade().create(selected);

                } else if (persistAction == PersistAction.UPDATE) {
                    getSelected().setLink(StringManager.prepareLinkVideoIFrame(getSelected().getLink()));
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
        if (getSelected() != null && persistAction == PersistAction.CREATE) {
            if (getSelected().getId() != 0) {
                /**
                 * criando uma postagem automática para o vídeo
                 */
                AutoPostagemController.create(selected);
            }
        }
    }

    public List<Videos> pesquisar() {
        getFacade().begin();
        if (!this.pesquisa.trim().equals("") || this.pesquisa != null) {
            DetachedCriteria query = DetachedCriteria.forClass(Videos.class);
            query.add(Restrictions.like("descricao", "%"+this.pesquisa+"%").ignoreCase());
            this.items = getFacade().getEntitiesByDetachedCriteria(query);
            return this.items;
        } else {
            getFacade().end();
            return getItems();
        }
    }

    public Videos getVideos(int id) {
        getFacade().begin();
        Videos v = getFacade().find(id);
        getFacade().end();
        return v;
    }

    public void myVideos(int id) {
        getFacade().begin();
        if (id == 1) {
            DetachedCriteria query = DetachedCriteria.forClass(Videos.class);
            query.add(Property.forName("usuario").eq(UsuarioSessionController.getUserLogged().getId()));

            this.items = getFacade().getEntitiesByDetachedCriteria(query);
        } else {
            this.items = getFacade().findAll();
        }
        getFacade().end();
    }

    @Override
    public List<Videos> getItemsAvailableSelectMany() {
        return getItems();
    }

    @Override
    public List<Videos> getItemsAvailableSelectOne() {
        getFacade().begin();
        this.items = getFacade().findAll();
        getFacade().end();
        return this.items;
    }

    @FacesConverter(forClass = Videos.class)
    public static class VideosControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            VideosController controller = (VideosController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "videosController");
            return controller.getVideos(getKey(value));
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
            if (object instanceof Videos) {
                Videos o = (Videos) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Videos.class.getName()});
                return null;
            }
        }

    }

}
