/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.control.Facade.PostagemFacade;
import br.edu.uag.aruagi.model.Postagem;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import org.hibernate.criterion.Order;

/**
 *
 * @author Renan Leandro Fernandes
 */
@Stateless
public class IndexController implements Serializable {
    private List<Postagem> items = null;
    private Postagem selected;
    private final PostagemFacade facade = new PostagemFacade();

    /**
     * @param items the items to set
     */
    public void setItems(List<Postagem> items) {
        this.items = items;
    }

    /**
     * @return the selected
     */
    public Postagem getSelected() {
        return selected;
    }

    /**
     * @param selected the selected to set
     */
    public void setSelected(Postagem selected) {
        this.selected = selected;
    }

    /**
     * @return the facade
     */
    public PostagemFacade getFacade() {
        return facade;
    }
    
    public List<Postagem> getItems() {
        getFacade().begin();
        items = getFacade().findAll();
        getFacade().end();
        return items;
    }
    
    public List<Postagem> getTimeLine() {
        getFacade().begin();
        List<Postagem> timeLine = getFacade().getSession().createCriteria(Postagem.class).addOrder(Order.desc("data")).addOrder(Order.desc("id")).list();
        getFacade().end();
        return timeLine;
    }
}
