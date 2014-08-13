/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uag.aruagi.control.Facade;

import br.edu.uag.aruagi.control.abstracts.AbstractFacade;
import br.edu.uag.aruagi.model.FraseLatim;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 *
 * @author Israel Araújo
 */
public class FraseLatimFacade extends AbstractFacade<FraseLatim, Integer> implements Serializable{

    public FraseLatimFacade() {
        super(FraseLatim.class);
    }

}
