/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uag.aruagi.control.abstracts;

import br.edu.uag.aruagi.control.Facade.Facade;
import br.edu.uag.aruagi.control.interfaces.InterfaceFacade;
import br.edu.uag.aruagi.model.Gabarito;
import br.edu.uag.aruagi.model.NivelQuestao;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

/**
 *
 * @author israel
 * @param <TipoQuestao>
 * @param <TipoPergunta>
 * @param <TipoResposta>
 */
public abstract class AbstractQuestaoController<TipoQuestao, TipoPergunta, TipoResposta> {

    private final InterfaceFacade<TipoQuestao> ejbFacade;
    private TipoQuestao current;
    private DataModel items = null;
    private Paginator pagination;

    private boolean respondendo = false;
    private double pontuacao;
    private int quantidade = 1;
    private String nome;

    private String resposta;

    List<TipoQuestao> paraResponder;

    private NivelQuestao nivel;
    private int pageSize = 1;

    // guarda as respostas
    private Gabarito<TipoQuestao, TipoPergunta, TipoResposta> gabarito;

    public AbstractQuestaoController(Class<TipoQuestao> clazz) {
        ejbFacade = new Facade<TipoQuestao>(clazz);
        paraResponder = new LinkedList<TipoQuestao>();
    }

    public InterfaceFacade<TipoQuestao> getFacade() {
        return ejbFacade;
    }

    public abstract void sortQuestoes();

    public abstract Gabarito.Correcao findCorrecao(TipoQuestao questao);

    public Paginator getPagination() {
        if (pagination == null) {

            pagination = new Paginator(this.pageSize) {

                @Override
                public int getItemsCount() {
                    return paraResponder.size();
                }

                @Override
                public DataModel createPageDataModel() {
                    if (paraResponder.isEmpty()) {

                        gabarito = new Gabarito<TipoQuestao, TipoPergunta, TipoResposta>(quantidade) {

                            @Override
                            public Gabarito.Correcao find(TipoQuestao questao) {
                                return findCorrecao(questao);
                            }
                        };
                        sortQuestoes();
                    }
                    TipoQuestao questao = paraResponder.subList(getPageFirstItem(), getPageFirstItem() + getPageSize()).get(0);
                    setCurrent(questao);
                    return new ListDataModel(Arrays.asList(questao));
                }
            };
        }
        return pagination;
    }

    public DataModel<TipoQuestao> getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    public void recreateModel() {
        items = null;
    }

    public void recreatePagination() {
        pagination = null;
    }

    public String prepareCreate() {
        items = getPagination().createPageDataModel();
        respondendo = true;
        return "List.uag?faces-redirect=true";
    }

    abstract public void corrige();

    private void preparePage() {
        if (getPagination() != null && getPagination().getPage() > -1 && !gabarito.getCorrecao().isEmpty()) {
            this.resposta = gabarito.getCorrecao().get(getPagination().getPage()) == null ? "" : gabarito.getCorrecao().get(getPagination().getPage()).getResposta().toString();
        }
    }

    public String next() {
        corrige();
        if (getPagination().getItemsCount() == getPagination().getPageFirstItem() + getPagination().getPageSize()) {
            avaliar();
            return "View.uag?faces-redirect=true";
        }
        getPagination().nextPage();
        preparePage();
        recreateModel();
        return "List.uag?faces-redirect=true";
    }

    public String previous() {
        corrige();
        getPagination().previousPage();
        preparePage();
        recreateModel();
        return "List.uag?faces-redirect=true";
    }

    public String avaliar() {
        int acertos = 0;
        int vazios = 0;
        for (Gabarito.Correcao c : gabarito.getCorrecao()) {
            try {
                if (c.corrige()) {
                    acertos++;
                }
            } catch (Exception ex) {
                vazios++;
            }
        }
        // perde 0.01 por resposta não feita
        pontuacao = (((100 * acertos) / quantidade) / 10) - (vazios * 0.01);
        if(pontuacao<0){
            pontuacao = 0;
        }
        pageSize = quantidade;
        recreateModel();
        return "View.uag?faces-redirect=true";
    }

    public void reset() {
        pageSize = 1;
        respondendo = false;
        pontuacao = 0;
        quantidade = 1;
        nome = "";
        resposta = "";
        paraResponder = new LinkedList<TipoQuestao>();
        recreatePagination();
        recreateModel();
    }

    public TipoQuestao getCurrent() {
        return current;
    }

    public void setCurrent(TipoQuestao current) {
        this.current = current;
    }

    public void setSelected(TipoQuestao current) {
        this.current = current;
    }

    public double getPontuacao() {
        avaliar();
        return pontuacao;
    }

    public void setPontuacao(double pontuacao) {
        this.pontuacao = pontuacao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public NivelQuestao getNivel() {
        return nivel;
    }

    public void setNivel(NivelQuestao nivel) {
        this.nivel = nivel;
    }

    public boolean isRespondendo() {
        return respondendo;
    }

    public Gabarito<TipoQuestao, TipoPergunta, TipoResposta> getGabarito() {
        return gabarito;
    }

    public List<TipoQuestao> getParaResponder() {
        return paraResponder;
    }

    public void setParaResponder(List<TipoQuestao> paraResponder) {
        this.paraResponder = paraResponder;
    }

}
