/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uag.aruagi.control.Facade;

import br.edu.uag.aruagi.control.abstracts.AbstractFacade;
import br.edu.uag.aruagi.model.Postagem;
import java.io.Serializable;

/**
 *
 * @author Israel Araújo
 */
public class PostagemFacade extends AbstractFacade<Postagem, Integer> implements Serializable {

    public PostagemFacade() {
        super(Postagem.class);
    }
}
