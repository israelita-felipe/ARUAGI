/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uag.aruagi.control.bean;

import java.io.Serializable;
import javax.ejb.Stateless;

/**
 *
 * @author Renan Leandro Fernandes
 */
@Stateless
public class IndexController extends PostagemController implements Serializable {

    public IndexController() {
        super();
    }

}
