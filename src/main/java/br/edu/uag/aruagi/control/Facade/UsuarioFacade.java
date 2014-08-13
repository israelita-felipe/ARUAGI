/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uag.aruagi.control.Facade;

import br.edu.uag.aruagi.control.abstracts.AbstractFacade;
import br.edu.uag.aruagi.model.Usuario;
import java.io.Serializable;

/**
 *
 * @author Israel Araújo
 */
public class UsuarioFacade extends AbstractFacade<Usuario, Integer> implements Serializable{

    public UsuarioFacade() {
        super(Usuario.class);
    }
}
