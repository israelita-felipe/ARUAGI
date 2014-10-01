/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.control.util.jsf.JsfUtil;
import br.edu.uag.aruagi.model.NivelQuestao;
import br.edu.uag.aruagi.model.QuestaoTraduzFrase;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author israel
 */
public class ResolucaoTraduzFraseController implements Serializable {

    //colecoes
    private List<QuestaoTraduzFrase> questoesPorNivel = new ArrayList<QuestaoTraduzFrase>();
    private RespostaTraduzFrase[] respostas;
    //objetos
    private QuestaoTraduzFrase questaoAtual;
    private NivelQuestao nivel;
    private String selected;
    //tipos primitivos int
    private int quantidade = 0;
    private int position = 1;
    private double pontuacao;
    //tipos primitivos string
    private String nome;
    //tipos primitivos boolean
    private boolean hideToolBar;

    public ResolucaoTraduzFraseController() {

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

    public RespostaTraduzFrase[] getRespostas() {
        return respostas;
    }

    public void setQuestaoAtual(QuestaoTraduzFrase questaoAtual) {
        this.questaoAtual = questaoAtual;
    }

    public QuestaoTraduzFrase getQuestaoAtual() {
        return questaoAtual;
    }

    public List<QuestaoTraduzFrase> getQuestoesPorNivel() {
        return questoesPorNivel;
    }

    public void setQuestoesPorNivel(List<QuestaoTraduzFrase> questoesPorNivel) {
        this.questoesPorNivel = questoesPorNivel;
    }

    /**
     * pontuação: calcula a pontuação conforme respostas do usuário
     *
     * @return
     */
    public double getPontuacao() {
        if (quantidade > 0) {
            pontuacao = 0.0;
            position = 1;
            for (RespostaTraduzFrase resposta : respostas) {
                //tradução 100% se a palavra for igual a indicada pelo professor
                if (resposta != null) {
                    /*
                    implementar a comparação
                    */
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

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    /**
     * prepara uma lista de questoes a serem resolvidas
     */
    public void prepare() {
        this.questaoAtual = null;
        this.questoesPorNivel = new ArrayList<QuestaoTraduzFrase>();
        //nível        
        // se a quantidade for 0, lista tudo
        if (quantidade > 0) {
            Random r = new Random(0);
            int tamanho = nivel.getQuestaoTraduzFrases().size();
            int index = 0;
            //se existirem menos questoes que o número escolhido
            if (tamanho <= quantidade) {
                quantidade = tamanho;
                this.questoesPorNivel = nivel.getQuestaoTraduzFrases();
            } else {
                this.questoesPorNivel = new ArrayList<QuestaoTraduzFrase>();
                //sorteando quantidade-questoes para serem resolvidas
                for (int i = 0; i < this.quantidade && questoesPorNivel.size() < this.quantidade; i++) {
                    index = r.nextInt(tamanho - 1);
                    if (questoesPorNivel.indexOf(nivel.getQuestaoTraduzPalavras().get(index)) == -1) {
                        this.questoesPorNivel.add((QuestaoTraduzFrase) nivel.getQuestaoTraduzFrases().get(index));
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
            position = 1;
            //this.quantidade = this.questoesPorNivel.size();
            respostas = new RespostaTraduzFrase[quantidade];
            for (int i = 0; i < quantidade; i++) {
                respostas[i] = new RespostaTraduzFrase();
                //respostas[i].setQuestaoTraduzPalavra(this.questoesPorNivel.get(i));
            }
            JsfUtil.addSuccessMessage("Tudo finalizado, pode responder as " + this.questoesPorNivel.size() + " questões");
        }
    }

    /**
     * responde a uma questao
     *
     * @return
     */
    public String next() {
        return null;
    }

    /**
     * pula uma questao
     *
     * @return
     */
    public String pular() {
        return null;
    }

    /**
     * retorna a uma questao
     *
     */
    public void previous() {
        
    }

    /**
     * subclasse de respostas de tradução de frase
     */
    public class RespostaTraduzFrase implements Serializable {
        
        private boolean status;

        public RespostaTraduzFrase() {
        }

       
        public String getCorrecao() {
            if (this.status) {
                return "Certo";
            }
            return "Errado";
        }
    }
}
