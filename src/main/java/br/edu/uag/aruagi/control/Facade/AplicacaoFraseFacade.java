/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uag.aruagi.control.Facade;

import br.edu.uag.aruagi.control.abstracts.AbstractFacade;
import br.edu.uag.aruagi.model.AplicacaoFrase;
import br.edu.uag.aruagi.model.AplicacaoFraseId;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 *
 * @author Israel Araújo
 */
@Stateless
public class AplicacaoFraseFacade extends AbstractFacade<AplicacaoFrase, AplicacaoFraseId> implements Serializable{

    public AplicacaoFraseFacade() {
        super(AplicacaoFrase.class);
    }

}
