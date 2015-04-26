/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uag.aruagi.control.Facade;

import br.edu.uag.aruagi.control.abstracts.AbstractFacade;
import br.edu.uag.aruagi.control.util.hibernate.FacesContextUtil;
import javax.enterprise.inject.Produces;
import org.hibernate.Session;

/**
 *
 * @author israel
 */
public class Facade<T> extends AbstractFacade<T>{
    
    @Override
    public Session getSession() {
        return FacesContextUtil.getRequestSession();
    }

    public Facade(Class<T> clazz) {
        super(clazz);
    }

    @Produces
    public Facade<T> getInstance() {
        return this;
    }
        
}
