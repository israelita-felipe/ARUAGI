/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.control.util.jsf.JsfUtil;
import br.edu.uag.aruagi.model.NivelQuestao;
import br.edu.uag.aruagi.model.PalavraPortugues;
import br.edu.uag.aruagi.model.QuestaoTraduzPalavra;
import br.edu.uag.aruagi.model.TraduzPalavra;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author israel
 */
public class ResolucaoTraduzPalavraController implements Serializable {

    //colecoes
    private List<QuestaoTraduzPalavra> questoesPorNivel;
    private RespostaTraduzPalavra[] respostas;
    //objetos
    private QuestaoTraduzPalavra questaoAtual;
    private NivelQuestao nivel;
    private PalavraPortugues selected;
    //tipos primitivos int
    private int quantidade = 0;
    private int position = 1;
    private double pontuacao;
    //tipos primitivos string
    private String nome;
    //tipos primitivos boolean
    private boolean hideToolBar;

    public ResolucaoTraduzPalavraController() {

    }

    public boolean isHideToolBar() {
        return hideToolBar;
    }

    public int getRestante() {
        if (this.quantidade > 0) {
            return (this.quantidade - this.position);
        }
        return -1;
    }

    public RespostaTraduzPalavra[] getRespostas() {
        return respostas;
    }

    public void setQuestaoAtual(QuestaoTraduzPalavra questaoAtual) {
        this.questaoAtual = questaoAtual;
    }

    public QuestaoTraduzPalavra getQuestaoAtual() {
        return questaoAtual;
    }

    public List<QuestaoTraduzPalavra> getQuestoesPorNivel() {
        return questoesPorNivel;
    }

    public void setQuestoesPorNivel(List<QuestaoTraduzPalavra> questoesPorNivel) {
        this.questoesPorNivel = questoesPorNivel;
    }

    /**
     * pontuação
     *
     * @return
     */
    public double getPontuacao() {
        if (quantidade > 0) {
            pontuacao = 0.0;
            position = 1;
            for (RespostaTraduzPalavra resposta : respostas) {
                if (find(resposta.palavraPortugues)) {
                    pontuacao = pontuacao + (10 / quantidade);
                }
                position++;
            }
            position--;
        } else {
            pontuacao = 0;
        }
        return pontuacao;
    }

    public NivelQuestao getNivel() {
        return nivel;
    }

    public void setNivel(NivelQuestao nivel) {
        this.nivel = nivel;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public PalavraPortugues getSelected() {
        return selected;
    }

    public void setSelected(PalavraPortugues selected) {
        this.selected = selected;
    }

    /**
     * prepara uma lista de questoes a serem resolvidas
     */
    public void prepare() {
        this.questaoAtual = null;
        this.questoesPorNivel = new ArrayList<QuestaoTraduzPalavra>();
        respostas = new RespostaTraduzPalavra[quantidade];
        for (int i = 0; i < quantidade; i++) {
            respostas[i] = new RespostaTraduzPalavra();
        }
        //nível        
        // se a quantidade for 0, lista tudo
        if (quantidade > 0) {
            Random r = new Random(0);
            int tamanho = nivel.getQuestaoTraduzPalavras().size();
            int index = 0;
            //se existirem menos questoes que o número escolhido
            if (tamanho <= quantidade) {
                this.questoesPorNivel = nivel.getQuestaoTraduzPalavras();
                JsfUtil.addErrorMessage("Listadas todas as questões possíveis");
            } else {
                this.questoesPorNivel = new ArrayList<QuestaoTraduzPalavra>();
                //sorteando quantidade-questoes para serem resolvidas
                for (int i = 0; i < this.quantidade && questoesPorNivel.size() < this.quantidade; i++) {
                    index = r.nextInt(tamanho - 1);
                    if (questoesPorNivel.indexOf(nivel.getQuestaoTraduzPalavras().get(index)) == -1) {
                        this.questoesPorNivel.add((QuestaoTraduzPalavra) nivel.getQuestaoTraduzPalavras().get(index));
                    } else {
                        i--;
                    }
                }
            }
        } else {
            this.questoesPorNivel = nivel.getQuestaoTraduzPalavras();
        }
        //setando a primeira questao
        if (!this.questoesPorNivel.isEmpty()) {
            questaoAtual = this.questoesPorNivel.get(0);
            JsfUtil.addSuccessMessage("Tudo finalizado, pode responder as questões");
        }
    }

    /**
     * responde a uma questao
     *
     * @return
     */
    public String next() {
        respostas[position - 1].setPalavraPortugues(selected);
        respostas[position - 1].setQuestaoTraduzPalavra(questaoAtual);
        //se houver traducao escolhida pelo usuario status é true
        respostas[position - 1].setStatus(find(selected));
        if (position == quantidade) {
            return avaliar();
        } else {
            position++;
            this.questaoAtual = this.questoesPorNivel.get(position - 1);
            this.selected = respostas[position - 1].getPalavraPortugues();
            return null;
        }
    }

    /**
     * pula uma questao
     *
     * @return
     */
    public String pular() {
        respostas[position - 1] = null;
        respostas[position - 1].setQuestaoTraduzPalavra(questaoAtual);
        if (position == quantidade) {
            return avaliar();
        } else {
            position++;
            this.questaoAtual = this.questoesPorNivel.get(position - 1);
            this.selected = respostas[position - 1].getPalavraPortugues();
            return null;
        }
    }

    /**
     * retorna a uma questao
     *
     */
    public void previous() {
        if (position > 1) {
            respostas[position - 1].setPalavraPortugues(selected);
            respostas[position - 1].setQuestaoTraduzPalavra(questaoAtual);
            //se houver traducao escolhida pelo usuario status é true
            respostas[position - 1].setStatus(find(selected));
            position--;
            this.questaoAtual = this.questoesPorNivel.get(position - 1);
            this.selected = respostas[position - 1].getPalavraPortugues();
        } else {
            JsfUtil.addErrorMessage("essa é a primeira questão");
        }
    }

    /**
     * avalia as questões
     *
     * @return
     */
    public String avaliar() {
        this.hideToolBar = false;
        return "/public/questoes/palavra/Avaliacao.xhtml?faces-redirect=true";
    }

    /**
     * finaliza a avalição
     *
     * @return
     */
    public String done() {
        reset();
        return "/public/questoes/palavra/Resolucao.xhtml?faces-redirect=true";
    }

    /**
     * reseta os campos
     */
    private void reset() {
        this.pontuacao = 0;
        this.position = 1;
        this.quantidade = 0;
        this.hideToolBar = false;
        this.questoesPorNivel = new ArrayList<QuestaoTraduzPalavra>();
        this.respostas = new RespostaTraduzPalavra[quantidade];
        this.questaoAtual = null;
        this.selected = null;
    }

    /**
     * encontra uma palavra
     *
     * @param p
     * @return
     */
    private boolean find(PalavraPortugues p) {
        if (p != null) {
            List<TraduzPalavra> traducoes = (List<TraduzPalavra>) this.questoesPorNivel.get(position - 1).getPalavraLatim().getTraduzPalavras();
            for (TraduzPalavra tp : traducoes) {
                if (tp.getPalavraPortugues().equals(p)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * subclasse de respostas de tradução de palavras
     */
    public class RespostaTraduzPalavra implements Serializable {

        private PalavraPortugues palavraPortugues;
        private QuestaoTraduzPalavra questaoTraduzPalavra;
        private boolean status;

        public RespostaTraduzPalavra() {
        }

        public PalavraPortugues getPalavraPortugues() {
            return palavraPortugues;
        }

        private void setPalavraPortugues(PalavraPortugues palavraPortugues) {
            this.palavraPortugues = palavraPortugues;
        }

        public boolean isStatus() {
            return status;
        }

        private void setStatus(boolean status) {
            this.status = status;
        }

        public QuestaoTraduzPalavra getQuestaoTraduzPalavra() {
            return questaoTraduzPalavra;
        }

        private void setQuestaoTraduzPalavra(QuestaoTraduzPalavra questaoTraduzPalavra) {
            this.questaoTraduzPalavra = questaoTraduzPalavra;
        }

        public String getCorrecao() {
            if (this.status) {
                return "Certo";
            }
            return "Errado";
        }
    }
}
