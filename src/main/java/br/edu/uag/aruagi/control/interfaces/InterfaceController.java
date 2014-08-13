/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uag.aruagi.control.interfaces;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Israel Araújo
 * @param <T>
 * @param <ID>
 */
public interface InterfaceController<T, ID extends Serializable> {

    /**
     * Prepara um objeto <T> para ser inserido no banco
     *
     * @return
     */
    T prepareCreate();

    /**
     * Cria um objeto do tipo <T> preparado pelo método <prepareCreate()>
     */
    void create();

    /**
     * Atualiza um objeto selecionado
     */
    void update();

    /**
     * Delete um objeto selecionado
     */
    void destroy();

    /**
     * Lista os objetos do tipo <T> presente no banco
     *
     * @return
     */
    List<T> getItems();

    /**
     * Lista os objetos possíveis para multi-seleção
     *
     * @return
     */
    List<T> getItemsAvailableSelectMany();

    /**
     * Lista os objetos possíveis para uni-seleção
     *
     * @return
     */
    List<T> getItemsAvailableSelectOne();

}
