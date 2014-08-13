/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uag.aruagi.control.Facade;

import br.edu.uag.aruagi.control.abstracts.AbstractFacade;
import br.edu.uag.aruagi.model.QuestaoDeclinacao;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 *
 * @author Israel Araújo
 */
@Stateless
public class QuestaoDeclinacaoFacade extends AbstractFacade<QuestaoDeclinacao, Integer> implements Serializable{

    public QuestaoDeclinacaoFacade() {
        super(QuestaoDeclinacao.class);
    }

}
