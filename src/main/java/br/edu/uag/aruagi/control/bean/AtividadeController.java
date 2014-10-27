/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.control.abstracts.AbstractFacade;
import br.edu.uag.aruagi.control.interfaces.InterfaceQuestao;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Israel Araújo
 */
public class AtividadeController implements Serializable {

    private AbstractFacade facade;
    private final ArrayList<InterfaceQuestao> questoes = new ArrayList<InterfaceQuestao>();
    private InterfaceQuestao selected;
    //posicao
    private int posicao = 0;

    public AtividadeController() {
    }

    public AbstractFacade getFacade() {
        return facade;
    }

    public void setFacade(AbstractFacade facade) {
        this.facade = facade;
    }

    public String next() {
        return null;
    }
}
