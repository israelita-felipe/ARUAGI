/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.edu.uag.aruagi.control.Facade;

import br.edu.uag.aruagi.control.abstracts.AbstractFacade;
import br.edu.uag.aruagi.model.LinksQuestao;
import br.edu.uag.aruagi.model.LinksQuestaoId;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 *
 * @author Israel Araújo
 */
@Stateless
public class LinksQuestaoFacade extends AbstractFacade<LinksQuestao, LinksQuestaoId> implements Serializable{
   
    public LinksQuestaoFacade() {
        super(LinksQuestao.class);
    }
    
}
