/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.control.abstracts.AbstractQuestaoController;
import br.edu.uag.aruagi.model.Gabarito.Correcao;
import br.edu.uag.aruagi.model.PalavraLatim;
import br.edu.uag.aruagi.model.PalavraPortugues;
import br.edu.uag.aruagi.model.QuestaoTraduzPalavra;
import br.edu.uag.aruagi.model.TraduzPalavra;
import java.io.Serializable;
import java.util.Random;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author israel
 */
@ManagedBean(name = "resolucaoQuestaoTraduzPalavraController")
@SessionScoped
public class ResolucaoQuestaoTraduzPalavraController extends AbstractQuestaoController<QuestaoTraduzPalavra, PalavraLatim, PalavraPortugues> implements Serializable {

    /**
     * Creates a new instance of ResolucaoQuestaoTraduzPalavraController
     */
    public ResolucaoQuestaoTraduzPalavraController() {
        super(QuestaoTraduzPalavra.class);
    }

    /**
     * Realiza a correção de uma questão com uma resposta dada
     */
    @Override
    public void corrige() {

        getGabarito().getCorrecao().set(getPagination().getPage(), getGabarito().new Correcao(getCurrent().getPalavraLatim(), new PalavraPortugues(0, getResposta())) {

            @Override
            public boolean corrige() {
                try {
                    for (TraduzPalavra tp : getCorreto().getTraduzPalavras()) {
                        if (tp.getPalavraPortugues().getPalavra().trim().toUpperCase().equals(getResposta().getPalavra().trim().toUpperCase())) {
                            return true;
                        }
                    }
                } catch (Exception ex) {
                    return false;
                }
                return false;
            }
        });

        setResposta("");
    }

    /**
     * Sorteia as questões por nível
     */
    @Override
    public void sortQuestoes() {
        if (getQuantidade() > getNivel().getQuestaoTraduzPalavras().size()) {
            setQuantidade(getNivel().getQuestaoTraduzPalavras().size());
            getParaResponder().addAll(getNivel().getQuestaoTraduzPalavras());
        }
        Random r = new Random();
        int id = -1;
        while (getParaResponder().size() < getQuantidade()) {
            try {
                while (id == -1) {
                    id = r.nextInt(getQuantidade() + 1);
                }
                QuestaoTraduzPalavra q = getNivel().getQuestaoTraduzPalavras().get(id);
                if (q != null && !getParaResponder().contains(q)) {
                    getParaResponder().add(q);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                id = -1;
            }
        }
    }

    @Override
    public Correcao findCorrecao(QuestaoTraduzPalavra questao) {
        /**
         * A correção da questão estará no mesmo índice da correção ou seja se
         * na lista de questões para responder a questão a ser buscada é 3,
         * então o gabarito na posição 3 é referente a tal questão
         */
        return getGabarito().getCorrecao().get(getParaResponder().indexOf(questao));
    }

}
