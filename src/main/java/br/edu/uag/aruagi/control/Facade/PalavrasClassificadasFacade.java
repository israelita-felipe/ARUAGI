/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uag.aruagi.control.Facade;

import br.edu.uag.aruagi.control.abstracts.AbstractFacade;
import br.edu.uag.aruagi.model.PalavrasClassificadas;
import br.edu.uag.aruagi.model.PalavrasClassificadasId;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 *
 * @author Israel Araújo
 */
@Stateless
public class PalavrasClassificadasFacade extends AbstractFacade<PalavrasClassificadas, PalavrasClassificadasId> implements Serializable{

    public PalavrasClassificadasFacade() {
        super(PalavrasClassificadas.class);
    }

}
