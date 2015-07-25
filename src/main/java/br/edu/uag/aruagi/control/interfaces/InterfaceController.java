/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uag.aruagi.control.interfaces;

import br.edu.uag.aruagi.control.abstracts.Paginator;
import java.io.Serializable;
import javax.faces.model.DataModel;
import javax.faces.model.SelectItem;

/**
 *
 * @author Israel Araújo
 * @param <T>
 */
public interface InterfaceController<T> {

    // daqui
    T getSelected();

    InterfaceFacade<T> getFacade();

    Paginator getPagination();

    String prepareList();

    String prepareView();

    String prepareCreate();

    String create();

    String prepareEdit();

    String update();

    String destroy();

    String destroyAndView();

    void performDestroy();

    void updateCurrentItem();

    DataModel getItems();

    void recreateModel();

    void recreatePagination();

    String next();

    String previous();

    String first();

    String last();

    SelectItem[] getItemsAvailableSelectMany();

    SelectItem[] getItemsAvailableSelectOne();

    T get(Serializable id);

}
