/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.control.util.jsf.JsfUtil;
import br.edu.uag.aruagi.model.FrasePortugues;
import br.edu.uag.aruagi.model.NivelQuestao;
import br.edu.uag.aruagi.model.QuestaoTraduzFrase;
import br.edu.uag.aruagi.model.TraduzFrase;
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
    private RespostaTraduzFrase[] respostasTemp;
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
    private boolean hideAvaliar = true;

    public ResolucaoTraduzFraseController() {

    }

    public boolean isHideToolBar() {
        return hideToolBar;
    }

    public int getRestante() {
        if (this.quantidade > 0) {
            return (this.quantidade - this.getPosition());
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
            setPontuacao(0.0);
            setPosition(1);
            for (RespostaTraduzFrase resposta : respostas) {
                //tradução 100% se a frase for igual a indicada pelo professor
                if (resposta != null) {
                    String FRASE_RESPOSTA = null;
                    if(resposta.getFrasePortugues() != null){
                        FRASE_RESPOSTA = resposta.getFrasePortugues().getFrase().replace(" ", "").toUpperCase();
                    }
                    String FRASE_TRADUCAO = null;
                    if(resposta.getQuestaoTraduzFrase() != null){
                        FRASE_TRADUCAO = resposta.getQuestaoTraduzFrase().getFrasePortugues().getFrase().replace(" ", "").toUpperCase();
                    }
                    if(FRASE_RESPOSTA == null && FRASE_TRADUCAO == null){
                        if(FRASE_RESPOSTA.equals(FRASE_TRADUCAO)){
                            setPontuacao(pontuacao + (10 / quantidade));
                        }
                    }
                }
                setPosition(getPosition() + 1);
            }
            setPosition(getPosition() - 1);
        } else {
            setPontuacao(0);
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
        this.setPontuacao(pontuacao);
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
                    if (questoesPorNivel.indexOf(nivel.getQuestaoTraduzFrases().get(index)) == -1) {
                        this.questoesPorNivel.add((QuestaoTraduzFrase) nivel.getQuestaoTraduzFrases().get(index));
                    } else {
                        i--;
                    }
                }
            }
        } else {
            this.questoesPorNivel = nivel.getQuestaoTraduzFrases();
        }
        //setando a primeira questao
        if (!this.questoesPorNivel.isEmpty()) {
            questaoAtual = this.questoesPorNivel.get(0);
            setPosition(1);
            //this.quantidade = this.questoesPorNivel.size();
            respostas = new RespostaTraduzFrase[quantidade];
            for (int i = 0; i < quantidade; i++) {
                respostas[i] = new RespostaTraduzFrase();
                //respostas[i].setQuestaoTraduzFrase(this.questoesPorNivel.get(i));
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
        respostas[getPosition() - 1].setFrasePortugues(new FrasePortugues(0, selected));
        respostas[getPosition() - 1].setQuestaoTraduzFrase(questaoAtual);
        //se houver traducao escolhida pelo usuario status é true
        boolean status = selected.toUpperCase().equals(respostas[getPosition() - 1].questaoTraduzFrase.getFrasePortugues().getFrase().toUpperCase());
        if (!status) {
            //status = find(respostas[position - 1].getFrasePortugues());
        }
        respostas[getPosition() - 1].setStatus(status);
        if (getPosition() == quantidade) {
            JsfUtil.addSuccessMessage("Questões finalizadas, pode clicar no botão avaliar do lado direito");
            return null;
        } else {
            setPosition(getPosition() + 1);
            this.questaoAtual = this.questoesPorNivel.get(getPosition() - 1);
            this.selected = respostas[getPosition() - 1].getFrasePortugues().getFrase();
            return null;
        }
    }

    /**
     * pula uma questao
     *
     * @return
     */
    public String pular() {
        respostas[getPosition() - 1].setFrasePortugues(new FrasePortugues(0, ""));
        respostas[getPosition() - 1].setQuestaoTraduzFrase(questaoAtual);
        if (getPosition() == quantidade) {
            JsfUtil.addSuccessMessage("Questões finalizadas, pode clicar no botão avaliar do lado direito");
            return null;
        } else {
            setPosition(getPosition() + 1);
            this.questaoAtual = this.questoesPorNivel.get(getPosition() - 1);
            this.selected = respostas[getPosition() - 1].getFrasePortugues().getFrase();
            return null;
        }
    }

    /**
     * retorna a uma questao
     *
     */
    public void previous() {
        if (getPosition() > 1) {
            respostas[getPosition() - 1].setFrasePortugues(new FrasePortugues(0, selected));
            respostas[getPosition() - 1].setQuestaoTraduzFrase(questaoAtual);
            //se houver traducao escolhida pelo usuario status é true
            boolean status = selected.toUpperCase().equals(respostas[getPosition() - 1].questaoTraduzFrase.getFrasePortugues().getFrase().toUpperCase());
            if (!status) {
                //status = find(respostas[position - 1].getFrasePortugues());
            }
            respostas[getPosition() - 1].setStatus(status);
            setPosition(getPosition() - 1);
            this.questaoAtual = this.questoesPorNivel.get(getPosition() - 1);
            this.selected = respostas[getPosition() - 1].getFrasePortugues().getFrase();
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
        this.setRespostasTemp(this.respostas);
        this.setPontuacao(getPontuacao());
        reset();
        this.hideAvaliar = false;
        return "/public/questoes/frase/Avaliacao.xhtml?faces-redirect=true";
    }

    /**
     * finaliza a avalição
     *
     * @return
     */
    public String done() {
        reset();
        return "/public/questoes/frase/Resolucao.xhtml?faces-redirect=true";
    }

    /**
     * reseta os campos
     */
    public void reset() {
        this.position = 1;
        this.quantidade = 0;
        this.hideAvaliar = false;
        this.questoesPorNivel = new ArrayList<QuestaoTraduzFrase>();
        this.respostas = new RespostaTraduzFrase[quantidade];
        this.questaoAtual = null;
        this.selected = null;
    }
    
    /**
     * encontra uma palavra
     *
     * @param p
     * @return
     */
    private boolean find(FrasePortugues p) {
        if (p != null) {
            List<TraduzFrase> traducoes = (List<TraduzFrase>) this.questoesPorNivel.get(getPosition() - 1).getFraseLatim().getTraduzFrases();
            for (TraduzFrase tp : traducoes) {
                if (tp.getFrasePortugues().getFrase().toUpperCase().equals(p.getFrase().toUpperCase())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @return the respostasTemp
     */
    public RespostaTraduzFrase[] getRespostasTemp() {
        return respostasTemp;
    }

    /**
     * @param respostasTemp the respostasTemp to set
     */
    public void setRespostasTemp(RespostaTraduzFrase[] respostasTemp) {
        this.respostasTemp = respostasTemp;
    }

    /**
     * @return the position
     */
    public int getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * @param pontuacao the pontuacao to set
     */
    public void setPontuacao(double pontuacao) {
        this.pontuacao = pontuacao;
    }

    /**
     * @param hideToolBar the hideToolBar to set
     */
    public void setHideToolBar(boolean hideToolBar) {
        this.hideToolBar = hideToolBar;
    }

    /**
     * subclasse de respostas de tradução de frase
     */
    public class RespostaTraduzFrase implements Serializable {
        
        private FrasePortugues frasePortugues;
        private QuestaoTraduzFrase questaoTraduzFrase;
        private boolean status;

        public RespostaTraduzFrase() {
            this.frasePortugues = new FrasePortugues();
            this.questaoTraduzFrase = new QuestaoTraduzFrase();
        }

        public RespostaTraduzFrase(FrasePortugues frasePortugues, QuestaoTraduzFrase questaoTraduzFrase, boolean status) {
            this.frasePortugues = frasePortugues;
            this.questaoTraduzFrase = questaoTraduzFrase;
            this.status = status;
        }

        public FrasePortugues getFrasePortugues() {
            return frasePortugues;
        }

        private void setFrasePortugues(FrasePortugues frasePortugues) {
            this.frasePortugues = frasePortugues;
        }

        public boolean isStatus() {
            return status;
        }

        private void setStatus(boolean status) {
            this.status = status;
        }

        public QuestaoTraduzFrase getQuestaoTraduzFrase() {
            return questaoTraduzFrase;
        }

        private void setQuestaoTraduzFrase(QuestaoTraduzFrase questaoTraduzFrase) {
            this.questaoTraduzFrase = questaoTraduzFrase;
        }

        public String getCorrecao() {
            if (this.status) {
                return "Certo";
            }
            return "Errado";
        }
    }
}
