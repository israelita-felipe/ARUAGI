/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.control.abstracts.AbstractQuestaoController;
import br.edu.uag.aruagi.model.QuestaoTraduzPalavra;
import java.io.Serializable;

/**
 *
 * @author israel
 */
public class ResolucaoQuestaoTraduzPalavraController extends AbstractQuestaoController<QuestaoTraduzPalavra> implements Serializable {

    /**
     * Creates a new instance of ResolucaoQuestaoTraduzPalavraController
     */
    public ResolucaoQuestaoTraduzPalavraController() {
        super(QuestaoTraduzPalavra.class);
    }

    @Override
    public void corrige() {
        if (getResposta() != null && !getResposta().trim().toUpperCase().equals("")) {
            if (getResposta().trim().toUpperCase().equals(getCurrent().getPalavraPortugues().getPalavra().trim().toUpperCase())) {
                setCorrecao(Boolean.TRUE, getPagination().getPageFirstItem() + getItems().getRowIndex(), getResposta());
            }
        } else {
            setCorrecao(Boolean.FALSE, getPagination().getPageFirstItem() + getItems().getRowIndex(), getResposta());
        }
        setResposta("");
    }

}
