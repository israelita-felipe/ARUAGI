/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.control.util.jsf.JsfUtil;
import br.edu.uag.aruagi.model.Lacuna;
import br.edu.uag.aruagi.model.NivelQuestao;
import br.edu.uag.aruagi.model.PalavraLatim;
import br.edu.uag.aruagi.model.QuestaoLacuna;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.primefaces.event.DragDropEvent;

/**
 *
 * @author israel
 */
public class ResolucaoQuestaoLacunaController implements Serializable {

    //colecoes
    private List<QuestaoLacuna> questoesPorNivel = new ArrayList<QuestaoLacuna>();
    private RespostaLacuna[] respostas;
    private RespostaLacuna[] respostasTemp;
    //objetos
    private QuestaoLacuna questaoAtual;
    private NivelQuestao nivel;
    private ArrayList<PalavraLatim> selected = new ArrayList<PalavraLatim>();
    private PalavraLatim palavraSelecionada;
    private List<String> list = new ArrayList<String>();
    //tipos primitivos int
    private int quantidade = 0;
    private int position = 1;
    private double pontuacao;
    //tipos primitivos string
    private String nome;
    //tipos primitivos boolean
    private boolean hideAvaliar = true;

    public ResolucaoQuestaoLacunaController() {

    }

    public boolean isHideToolBar() {
        return hideAvaliar;
    }

    public List<String> getList() {
        this.list = new ArrayList<String>();
        if (questaoAtual != null) {
            for (Lacuna l : questaoAtual.getLacunas()) {
                if (!l.getPalavraLatim().getTraduzPalavras().isEmpty()) {
                    this.list.add(l.getPalavraLatim().getTraduzPalavras().get(0).getPalavraLatim().getPalavra());
                } else {
                    this.list.add(l.getPalavraLatim().getPalavra());
                }
            }
        }
        return this.list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public int getRestante() {
        if (this.quantidade > 0) {
            return (this.quantidade - this.position);
        }
        return -1;
    }

    public PalavraLatim getPalavraSelecionada() {
        return palavraSelecionada;
    }

    public void setPalavraSelecionada(PalavraLatim palavraSelecionada) {
        this.palavraSelecionada = palavraSelecionada;
    }

    public int getPosition() {
        return position;
    }

    public RespostaLacuna[] getRespostas() {
        return respostas;
    }

    public void resetSelection() {
        this.selected = new ArrayList<PalavraLatim>();
    }

    public void remove() {
        this.selected.remove(palavraSelecionada);
        this.palavraSelecionada = null;
    }

    public void add(DragDropEvent ddEvent) {
        PalavraLatim palavra = ((PalavraLatim) ddEvent.getData());
        this.selected.add(palavra);
        this.palavraSelecionada = null;
    }

    public void add() {
        this.selected.add(palavraSelecionada);
        this.palavraSelecionada = null;
    }

    /**
     * pontuação: calcula a pontuação conforme respostas do usuário
     *
     * @return
     */
    public double getPontuacao() {
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

    /**
     * prepara uma lista de questoes a serem resolvidas de acordo as opções
     * escolhidas pelo usuário
     */
    public void prepare() {
        this.questaoAtual = null;
        this.questoesPorNivel = new ArrayList<QuestaoLacuna>();
        //nível        
        // se a quantidade for 0, lista tudo
        if (quantidade > 0) {
            Random r = new Random(0);
            int tamanho = nivel.getQuestaoLacunas().size();
            int index = 0;
            //se existirem menos questoes que o número escolhido
            if (tamanho <= quantidade) {
                quantidade = tamanho;
                this.questoesPorNivel = nivel.getQuestaoLacunas();
            } else {
                this.questoesPorNivel = new ArrayList<QuestaoLacuna>();
                //sorteando quantidade-questoes para serem resolvidas
                for (int i = 0; i < this.quantidade && questoesPorNivel.size() < this.quantidade; i++) {
                    index = r.nextInt(tamanho - 1);
                    if (questoesPorNivel.indexOf(nivel.getQuestaoLacunas().get(index)) == -1) {
                        this.questoesPorNivel.add((QuestaoLacuna) nivel.getQuestaoLacunas().get(index));
                    } else {
                        i--;
                    }
                }
            }
        } else {
            this.questoesPorNivel = nivel.getQuestaoLacunas();
        }
        if (!this.questoesPorNivel.isEmpty()) {
            //setando a primeira questao
            questaoAtual = this.questoesPorNivel.get(0);
            position = 1;
            //this.quantidade = this.questoesPorNivel.size();
            respostas = new RespostaLacuna[quantidade];
            for (int i = 0; i < quantidade; i++) {
                respostas[i] = new RespostaLacuna();
                respostas[i].setQuestaoLacuna(this.questoesPorNivel.get(i));
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
        respostas[position - 1].setPalavrasLatim(selected);
        respostas[position - 1].setQuestaoLacuna(questaoAtual);
        ArrayList<PalavraLatim> corretas = new ArrayList<PalavraLatim>();
        for (Lacuna l : questaoAtual.getLacunas()) {
            corretas.add(l.getPalavraLatim());
        }
        /**
         * se as opções escolhidas pelo usuário for igual das respostas o status
         * é true
         */
        respostas[position - 1].setStatus(selected.equals(corretas));
        if (position == quantidade) {
            JsfUtil.addSuccessMessage("Questões finalizadas, pode clicar no botão avaliar do lado direito");
        } else {
            position++;
            this.questaoAtual = this.questoesPorNivel.get(position - 1);
            this.selected = respostas[position - 1].getPalavrasLatim();
        }
        return null;
    }

    /**
     * pula uma questao
     *
     * @return
     */
    public String pular() {
        respostas[position - 1].setPalavrasLatim(selected);
        respostas[position - 1].setQuestaoLacuna(questaoAtual);
        if (position == quantidade) {
            JsfUtil.addSuccessMessage("Questões finalizadas, pode clicar no botão avaliar");
        } else {
            position++;
            this.questaoAtual = this.questoesPorNivel.get(position - 1);
            this.selected = respostas[position - 1].getPalavrasLatim();
        }
        return null;
    }

    /**
     * retorna a uma questao
     *
     */
    public void previous() {
        if (position > 1) {
            respostas[position - 1].setQuestaoLacuna(questaoAtual);
            ArrayList<PalavraLatim> corretas = new ArrayList<PalavraLatim>();
            for (Lacuna l : questaoAtual.getLacunas()) {
                corretas.add(l.getPalavraLatim());
            }
            /**
             * se as opções escolhidas pelo usuário for igual das respostas o
             * status é true
             */
            respostas[position - 1].setStatus(selected.equals(corretas));
            position--;
            this.questaoAtual = this.questoesPorNivel.get(position - 1);
            this.selected = respostas[position - 1].getPalavrasLatim();
        } else {
            JsfUtil.addErrorMessage("Essa é a primeira questão");
        }
    }

    /**
     * avalia as questões
     *
     * @return
     */
    public String avaliar() {
        this.respostasTemp = this.respostas;
        this.pontuacao = getPontuacao();
        reset();
        this.hideAvaliar = false;
        return "/public/questoes/lacuna/Avaliacao.xhtml?faces-redirect=true";
    }

    /**
     * finaliza a avalição
     *
     * @return
     */
    public String done() {
        reset();
        return "/public/questoes/lacuna/Resolucao.xhtml?faces-redirect=true";
    }

    /**
     * reseta os campos
     */
    public void reset() {
        this.position = 1;
        this.quantidade = 0;
        this.hideAvaliar = false;
        this.questoesPorNivel = new ArrayList<QuestaoLacuna>();
        this.respostas = new RespostaLacuna[quantidade];
        this.questaoAtual = null;
        this.selected = new ArrayList<PalavraLatim>();
    }

    public RespostaLacuna[] getRespostasTemp() {
        return respostasTemp;
    }

    public void setRespostasTemp(RespostaLacuna[] respostasTemp) {
        this.respostasTemp = respostasTemp;
    }

    public List<QuestaoLacuna> getQuestoesPorNivel() {
        return questoesPorNivel;
    }

    public void setQuestoesPorNivel(List<QuestaoLacuna> questoesPorNivel) {
        this.questoesPorNivel = questoesPorNivel;
    }

    public QuestaoLacuna getQuestaoAtual() {
        return questaoAtual;
    }

    public void setQuestaoAtual(QuestaoLacuna questaoAtual) {
        this.questaoAtual = questaoAtual;
    }

    public ArrayList<PalavraLatim> getSelected() {
        return selected;
    }

    public void setSelected(ArrayList<PalavraLatim> selected) {
        this.selected = selected;
    }

    public boolean isHideAvaliar() {
        return hideAvaliar;
    }

    public void setHideAvaliar(boolean hideAvaliar) {
        this.hideAvaliar = hideAvaliar;
    }

    /**
     * subclasse de respostas de tradução de palavras
     */
    public class RespostaLacuna implements Serializable {

        private ArrayList<PalavraLatim> palavrasLatim;
        private QuestaoLacuna questaoLacuna;
        private boolean status;

        public RespostaLacuna() {
            this.palavrasLatim = new ArrayList<PalavraLatim>();
            this.questaoLacuna = new QuestaoLacuna();
        }

        public RespostaLacuna(ArrayList palavrasLatim, QuestaoLacuna questaoLacuna, boolean status) {
            this.palavrasLatim = palavrasLatim;
            this.questaoLacuna = questaoLacuna;
            this.status = status;
        }

        public boolean isStatus() {
            return status;
        }

        private void setStatus(boolean status) {
            this.status = status;
        }

        public ArrayList<PalavraLatim> getPalavrasLatim() {
            return palavrasLatim;
        }

        public void setPalavrasLatim(ArrayList<PalavraLatim> palavrasLatim) {
            this.palavrasLatim = palavrasLatim;
        }

        public QuestaoLacuna getQuestaoLacuna() {
            return questaoLacuna;
        }

        public void setQuestaoLacuna(QuestaoLacuna questaoLacuna) {
            this.questaoLacuna = questaoLacuna;
        }

        public String getCorrecao() {
            if (this.status) {
                return "Certo";
            }
            return "Errado";
        }
    }

}
