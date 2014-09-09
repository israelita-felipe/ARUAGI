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

    private QuestaoTraduzPalavra questaoAtual;
    private NivelQuestao nivel;
    private List<QuestaoTraduzPalavra> questoesPorNivel;
    private int quantidade = 0;
    private String nome;
    private PalavraPortugues[] respostas;
    private int position = 1;
    private PalavraPortugues selected;
    private int pontuacao;

    public ResolucaoTraduzPalavraController() {

    }

    public int getRestante() {
        if (this.quantidade > 0) {
            return (this.quantidade - this.position);
        }
        return -1;
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

    public int getPontuacao() {
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
        respostas = new PalavraPortugues[quantidade];
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
        if (find(selected)) {
            respostas[position - 1] = selected;
        } else {
            JsfUtil.addErrorMessage("não há tradução");
        }
        if (position == quantidade) {
            return avaliar();
        } else {
            position++;
            this.questaoAtual = this.questoesPorNivel.get(position - 1);
            this.selected = respostas[position - 1];
            JsfUtil.addErrorMessage(String.valueOf(selected));
        }
        return null;
    }

    /**
     * pula uma questao
     *
     * @return
     */
    public String pular() {
        respostas[position - 1] = null;
        if (position == quantidade) {
            return avaliar();
        } else {
            position++;
            this.questaoAtual = this.questoesPorNivel.get(position - 1);
            this.selected = respostas[position - 1];
            JsfUtil.addErrorMessage(String.valueOf(selected));
        }
        return null;
    }

    /**
     * retorna a uma questao
     *
     */
    public void previous() {
        if (position > 1) {
            if (find(selected)) {
                respostas[position - 1] = selected;
            } else {
                JsfUtil.addErrorMessage("não há tradução");
            }
            position--;
            this.questaoAtual = this.questoesPorNivel.get(position - 1);
            this.selected = respostas[position - 1];
            JsfUtil.addErrorMessage(String.valueOf(selected));
        } else {
            JsfUtil.addErrorMessage("essa é a primeira questão");
        }
    }

    public String avaliar() {
        print();
        JsfUtil.addSuccessMessage("avaliou");
        return null;
    }

    private boolean find(PalavraPortugues p) {
        List<TraduzPalavra> traducoes = (List<TraduzPalavra>) this.questoesPorNivel.get(position - 1).getPalavraLatim().getTraduzPalavras();
        JsfUtil.addSuccessMessage(String.valueOf(traducoes.size()));
        for (TraduzPalavra tp : traducoes) {
            if (tp.getPalavraPortugues().equals(p)) {
                return true;
            }
        }
        return false;
    }

    private void print() {
        System.out.println(respostas.length);
    }
}
