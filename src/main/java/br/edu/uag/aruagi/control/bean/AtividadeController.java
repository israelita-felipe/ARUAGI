/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.control.abstracts.AbstractFacade;
import java.util.ArrayList;

/**
 *
 * @author Israel Araújo
 */
public class AtividadeController {

    private AbstractFacade facade;
    private final ArrayList<QuestaoDeclinacaoController> questaoDeclinacao = new ArrayList<QuestaoDeclinacaoController>();
    private final ArrayList<QuestaoGramaticalController> questaoGramatical = new ArrayList<QuestaoGramaticalController>();
    private final ArrayList<QuestaoLacunaController> questaoLacuna = new ArrayList<QuestaoLacunaController>();
    private final ArrayList<QuestaoTraduzFraseController> questaoTraduzFrase = new ArrayList<QuestaoTraduzFraseController>();
    private final ArrayList<QuestaoTraduzPalavraController> questaoTraduzPalavra = new ArrayList<QuestaoTraduzPalavraController>();

    public AtividadeController() {
    }

    public AbstractFacade getFacade() {
        return facade;
    }

    public void setFacade(AbstractFacade facade) {
        this.facade = facade;
    }

    public ArrayList<QuestaoDeclinacaoController> getQuestaoDeclinacao() {
        return questaoDeclinacao;
    }

    public ArrayList<QuestaoGramaticalController> getQuestaoGramatical() {
        return questaoGramatical;
    }

    public ArrayList<QuestaoLacunaController> getQuestaoLacuna() {
        return questaoLacuna;
    }

    public ArrayList<QuestaoTraduzFraseController> getQuestaoTraduzFrase() {
        return questaoTraduzFrase;
    }

    public ArrayList<QuestaoTraduzPalavraController> getQuestaoTraduzPalavra() {
        return questaoTraduzPalavra;
    }

}
