/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uag.aruagi.model.Facade;

import br.edu.uag.aruagi.model.TempoVerbal;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 *
 * @author Israel Araújo
 */
@Stateless
public class TempoVerbalFacade extends AbstractFacade<TempoVerbal, Integer> implements Serializable{

    public TempoVerbalFacade() {
        super(TempoVerbal.class);
    }

}
