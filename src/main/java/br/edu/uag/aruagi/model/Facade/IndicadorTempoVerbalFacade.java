/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uag.aruagi.model.Facade;

import br.edu.uag.aruagi.model.IndicadorTempoVerbal;
import br.edu.uag.aruagi.model.IndicadorTempoVerbalId;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 *
 * @author Israel Araújo
 */
@Stateless
public class IndicadorTempoVerbalFacade extends AbstractFacade<IndicadorTempoVerbal, IndicadorTempoVerbalId> implements Serializable{

    public IndicadorTempoVerbalFacade() {
        super(IndicadorTempoVerbal.class);
    }

}
