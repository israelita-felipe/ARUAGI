/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uag.aruagi.control.Facade;

import br.edu.uag.aruagi.control.abstracts.AbstractFacade;
import br.edu.uag.aruagi.model.Acesso;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Israel Araújo
 */
public class AcessoFacade extends AbstractFacade<Acesso, Date> implements Serializable{
    
    public AcessoFacade() {
        super(Acesso.class);
    }
}
