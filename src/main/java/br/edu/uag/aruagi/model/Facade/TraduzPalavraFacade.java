/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uag.aruagi.model.Facade;

import br.edu.uag.aruagi.model.TraduzPalavra;
import br.edu.uag.aruagi.model.TraduzPalavraId;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 *
 * @author Israel Araújo
 */
@Stateless
public class TraduzPalavraFacade extends AbstractFacade<TraduzPalavra, TraduzPalavraId> implements Serializable{

    public TraduzPalavraFacade() {
        super(TraduzPalavra.class);
    }

}
