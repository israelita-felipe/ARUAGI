package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.control.Facade.QuestaoLacunaFacade;
import br.edu.uag.aruagi.control.interfaces.InterfaceController;
import br.edu.uag.aruagi.model.QuestaoLacuna;
import br.edu.uag.aruagi.control.util.jsf.JsfUtil;
import br.edu.uag.aruagi.control.util.jsf.JsfUtil.PersistAction;
import br.edu.uag.aruagi.control.util.validator.LacunaValidator;
import br.edu.uag.aruagi.model.Lacuna;
import br.edu.uag.aruagi.model.PalavraLatim;
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
import org.primefaces.event.DragDropEvent;

public class QuestaoLacunaController implements Serializable, InterfaceController<QuestaoLacuna, Integer> {

    private final QuestaoLacunaFacade facade = new QuestaoLacunaFacade();
    private List<QuestaoLacuna> items = null;
    private QuestaoLacuna selected;
    //composição da lacuna
    private List<PalavraLatim> palavrasSelecionadas = new ArrayList<PalavraLatim>();
    private PalavraLatim palavraEscolhida;

    public QuestaoLacunaController() {
    }

    public void add(DragDropEvent ddEvent) {
        PalavraLatim palavra = ((PalavraLatim) ddEvent.getData());
        this.palavrasSelecionadas.add(palavra);
        this.palavraEscolhida = null;
    }

    public void add() {
        this.palavrasSelecionadas.add(palavraEscolhida);
        this.palavraEscolhida = null;
    }

    public void setItems(List<QuestaoLacuna> items) {
        this.items = items;
    }

    public void remove() {
        this.palavrasSelecionadas.remove(palavraEscolhida);
        this.palavraEscolhida = null;
    }

    public void reset() {
        this.palavrasSelecionadas = new ArrayList<PalavraLatim>();
    }

    public void parse() {
        this.selected.setEnunciado(this.selected.getFraseLatim().getFrase());
    }

    public PalavraLatim getPalavraEscolhida() {
        return palavraEscolhida;
    }

    public void setPalavraEscolhida(PalavraLatim palavraEscolhida) {
        this.palavraEscolhida = palavraEscolhida;
    }

    public List<PalavraLatim> getPalavrasSelecionadas() {
        return palavrasSelecionadas;
    }

    public void setPalavrasSelecionadas(List<PalavraLatim> palavrasSelecionadas) {
        this.palavrasSelecionadas = palavrasSelecionadas;
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
        this.palavrasSelecionadas = new ArrayList<PalavraLatim>();
        selected = new QuestaoLacuna();
        initializeEmbeddableKey();
        return selected;
    }

    /**
     * prepara a lista de lacunar cadastradas para a questão
     */
    public void prepareEdit() {
        this.palavrasSelecionadas = new ArrayList<PalavraLatim>();
        for (Lacuna l : this.selected.getLacunas()) {
            this.palavrasSelecionadas.add(l.getPalavraLatim());
        }
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
        getSelected().setStatus(Boolean.FALSE);
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("MensagemQuestaoLacunaExcluida"));
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
        if (selected != null && !this.palavrasSelecionadas.isEmpty() && this.palavrasSelecionadas.size() == LacunaValidator.count(this.selected.getEnunciado())) {
            setEmbeddableKeys();
            try {
                if (persistAction == PersistAction.CREATE) {
                    selected.setStatus(true);
                    selected.setUsuario(UsuarioSessionController.getUserLogged().getId());
                    selected.setStatus(Boolean.TRUE);
                    getFacade().create(selected);
                    LacunaController lc = new LacunaController();
                    for (int i = 0; i < this.palavrasSelecionadas.size(); i++) {
                        //criando uma nova lacuna
                        Lacuna l = new Lacuna();
                        //setando seus valores
                        l.setUsuario(selected.getUsuario());
                        l.setIndex(i);
                        l.setPalavraLatim(getPalavrasSelecionadas().get(i));
                        l.setQuestaoLacuna(selected);
                        //selecionando a lacuna criada
                        lc.setSelected(l);
                        //criando a lacuna
                        lc.create();
                    }
                } else if (persistAction == PersistAction.UPDATE) {
                    LacunaController lc = new LacunaController();
                    //limpando as lacunas antigas
                    for (Lacuna l : selected.getLacunas()) {
                        lc.setSelected(l);
                        lc.destroy();
                    }
                    //gravando as lacunas atuais
                    for (int i = 0; i < this.palavrasSelecionadas.size(); i++) {
                        //criando uma nova lacuna
                        Lacuna l = new Lacuna();
                        //setando seus valores
                        l.setUsuario(selected.getUsuario());
                        l.setIndex(i);
                        l.setPalavraLatim(getPalavrasSelecionadas().get(i));
                        l.setQuestaoLacuna(selected);
                        //selecionando a lacuna criada
                        lc.setSelected(l);
                        //criando a lacuna
                        lc.create();
                    }
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        } else {
            JsfUtil.addErrorMessage("Verifique se o número de lacunas corresponde ao número de palavras escolhidas");
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
