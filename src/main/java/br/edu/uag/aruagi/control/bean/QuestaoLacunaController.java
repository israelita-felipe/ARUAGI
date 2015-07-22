package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.control.abstracts.AbstractController;
import br.edu.uag.aruagi.model.QuestaoLacuna;
import br.edu.uag.aruagi.control.util.validator.LacunaValidator;
import br.edu.uag.aruagi.model.Lacuna;
import br.edu.uag.aruagi.model.PalavraLatim;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.primefaces.event.DragDropEvent;

public class QuestaoLacunaController extends AbstractController<QuestaoLacuna> implements Serializable {

    //composição da lacuna
    private List<PalavraLatim> palavrasSelecionadas = new ArrayList<PalavraLatim>();
    private PalavraLatim palavraEscolhida;

    public QuestaoLacunaController() {
        super(QuestaoLacuna.class);
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

    public void remove() {
        this.palavrasSelecionadas.remove(palavraEscolhida);
        this.palavraEscolhida = null;
    }

    public void reset() {
        this.palavrasSelecionadas = new ArrayList<PalavraLatim>();
    }
    
    public void parse() {
        this.getCurrent().setEnunciado(this.getCurrent().getFraseLatim().getFrase());
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

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    /**
     * prepara a lista de lacunar cadastradas para a questão
     *
     * @return
     */
    @Override
    public String prepareEdit() {
        this.palavrasSelecionadas = new ArrayList<PalavraLatim>();
        for (Lacuna l : getSelected().getLacunas()) {
            this.palavrasSelecionadas.add(l.getPalavraLatim());
        }
        return super.prepareEdit();
    }

    @Override
    public String create() {
        String retorno = null;
        if (getSelected() != null && !this.palavrasSelecionadas.isEmpty() && this.palavrasSelecionadas.size() == LacunaValidator.count(getSelected().getEnunciado())) {
            retorno = super.create();
            LacunaController lc = new LacunaController();
            for (int i = 0; i < this.palavrasSelecionadas.size(); i++) {
                //criando uma nova lacuna
                Lacuna l = new Lacuna();
                //setando seus valores
                l.setUsuario(getSelected().getUsuario());
                l.setIndex(i);
                l.setPalavraLatim(getPalavrasSelecionadas().get(i));
                l.setQuestaoLacuna(getSelected());
                //selecionando a lacuna criada
                lc.setCurrent(l);
                //criando a lacuna
                lc.create();
            }
        }
        return retorno;
    }

    @Override
    public String update() {
        String retorno = null;
        if (getSelected() != null && !this.palavrasSelecionadas.isEmpty() && this.palavrasSelecionadas.size() == LacunaValidator.count(getSelected().getEnunciado())) {
            LacunaController lc = new LacunaController();
            //limpando as lacunas antigas
            for (Lacuna l : getSelected().getLacunas()) {
                lc.setCurrent(l);
                lc.destroy();
            }
            //gravando as lacunas atuais
            for (int i = 0; i < this.palavrasSelecionadas.size(); i++) {
                //criando uma nova lacuna
                Lacuna l = new Lacuna();
                //setando seus valores
                l.setUsuario(getSelected().getUsuario());
                l.setIndex(i);
                l.setPalavraLatim(getPalavrasSelecionadas().get(i));
                l.setQuestaoLacuna(getSelected());
                //selecionando a lacuna criada
                lc.setCurrent(l);
                //criando a lacuna
                lc.create();
            }
            retorno = super.update();
        }
        return retorno;
    }

    @Override
    public QuestaoLacuna getSelected() {
        if (getCurrent() == null) {
            setCurrent(new QuestaoLacuna());
            initializeEmbeddableKey();
            setSelectedItemIndex(-1);
        }
        return getCurrent();
    }

    @Override
    public String prepareCreate() {
        setCurrent(new QuestaoLacuna());
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

    public static class QuestaoLacunaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            QuestaoLacunaController controller = (QuestaoLacunaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "questaoLacunaController");
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
