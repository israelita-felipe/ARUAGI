/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uag.aruagi.control.abstracts;

import br.edu.uag.aruagi.control.Facade.Facade;
import br.edu.uag.aruagi.control.interfaces.InterfaceFacade;
import br.edu.uag.aruagi.control.interfaces.InterfaceQuestao;
import br.edu.uag.aruagi.control.util.jsf.JsfUtil;
import br.edu.uag.aruagi.control.util.jsf.Paginator;
import br.edu.uag.aruagi.model.NivelQuestao;
import br.edu.uag.aruagi.model.QuestaoTraduzPalavra;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

/**
 *
 * @author israel
 * @param <T>
 */
public abstract class AbstractQuestaoController<T> {

    private final InterfaceFacade<T> ejbFacade;
    private T current;
    private DataModel items = null;
    private Paginator pagination;

    private boolean respondendo = false;
    private double pontuacao;
    private int quantidade = 1;
    private String nome;
    private Boolean correcao[];
    private String respostas[];//melhor criar objeto para guardar boolean,resposta,questao
    private String resposta;
    List<T> paraResponder;
    private NivelQuestao nivel;
    private int pageSize = 1;

    public AbstractQuestaoController(Class<T> clazz) {
        ejbFacade = new Facade<T>(clazz);
        paraResponder = new LinkedList<T>();
    }

    public InterfaceFacade<T> getFacade() {
        return ejbFacade;
    }

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
                        correcao = new Boolean[quantidade];
                        respostas = new String[quantidade];
                        Random r = new Random();
                        while (paraResponder.size() < quantidade) {
                            try {
                                int id = 0;
                                while (id == 0) {
                                    id = r.nextInt(quantidade + 1);
                                }
                                T q = getFacade().find(id);
                                if (q != null && !paraResponder.contains(q)) {
                                    paraResponder.add(q);
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                    T questao = paraResponder.subList(getPageFirstItem(), getPageFirstItem() + getPageSize()).get(0);
                    setCurrent(questao);
                    return new ListDataModel(Arrays.asList(questao));
                }
            };
        }
        return pagination;
    }

    public DataModel<T> getItems() {
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
        return "List";
    }

    abstract public void corrige();

    public String find(T toFind){
        JsfUtil.addErrorMessage("Erro: "+this.paraResponder.indexOf(toFind));
        return respostas[this.paraResponder.indexOf(toFind)];
    }
    private void preparePage() {
        if (getPagination() != null && getPagination().getPage() > -1 && respostas != null) {
            this.resposta = respostas[getPagination().getPage()] == null ? "" : respostas[getPagination().getPage()];
        }
    }

    public String next() {
        corrige();
        if (getPagination().getItemsCount() == getPagination().getPageFirstItem() + getPagination().getPageSize()) {
            avaliar();
            return "View";
        }
        getPagination().nextPage();
        preparePage();
        recreateModel();
        return "List";
    }

    public String previous() {
        corrige();
        getPagination().previousPage();
        preparePage();
        recreateModel();
        return "List";
    }

    public String avaliar() {
        int acertos = 0;
        for (Boolean a : correcao) {
            if (a != null && a) {
                acertos++;
            }
        }
        pontuacao = ((100 * acertos) / quantidade) / 10;
        pageSize = quantidade;
        recreateModel();
        return "View";
    }

    public void reset() {
        pageSize = 1;
        respondendo = false;
        pontuacao = 0;
        quantidade = 1;
        nome = "";
        correcao = null;
        resposta = "";
        paraResponder = new LinkedList<T>();
        recreatePagination();
        recreateModel();
    }

    public T getCurrent() {
        return current;
    }

    public void setCurrent(T current) {
        this.current = current;
    }

    public void setSelected(T current) {
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

    public void setCorrecao(Boolean toSet, int index,String resposta) {
        if (index > -1 && index < correcao.length) {
            correcao[index] = toSet;
            respostas[index] = resposta;
        }
    }

}
