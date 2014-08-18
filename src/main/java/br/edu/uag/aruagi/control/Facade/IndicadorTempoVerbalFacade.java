/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uag.aruagi.control.Facade;

import br.edu.uag.aruagi.control.abstracts.AbstractFacade;
import br.edu.uag.aruagi.model.IndicadorTempoVerbal;
import br.edu.uag.aruagi.model.IndicadorTempoVerbalId;
import java.io.Serializable;

/**
 *
 * @author Israel Araújo
 */
public class IndicadorTempoVerbalFacade extends AbstractFacade<IndicadorTempoVerbal, IndicadorTempoVerbalId> implements Serializable {

    public IndicadorTempoVerbalFacade() {
        super(IndicadorTempoVerbal.class);
    }

}
