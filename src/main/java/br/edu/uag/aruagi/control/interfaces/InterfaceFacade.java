/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uag.aruagi.control.interfaces;

import java.io.Serializable;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;

/**
 *
 * @author Israel Araújo
 */
public interface InterfaceFacade<T, ID extends Serializable> {

    /**
     * Recupera a sessao
     *
     * @return
     */
    Session getSession();

    /**
     * Grava no banco um objeto do tipo <T>
     *
     * @param entity
     */
    void create(T entity);

    /**
     * Inicia uma transação com o banco
     */
    void begin();

    /**
     * Finaliza uma transação com o banco
     */
    void end();

    /**
     * Edita um objeto do tipo <T>
     *
     * @param entity
     */
    void edit(T entity);

    /**
     * Remove um objeto do tipo <T>
     *
     * @param entity
     */
    void remove(T entity);

    /**
     * Retorna um objeto do tipo <T> de acordo seu id do tipo <ID>
     *
     * @param id
     * @return
     */
    T find(ID id);

    /**
     * Lista todos os objetos do tipo <T>
     *
     * @return
     */
    List<T> findAll();

    /**
     * Conta os objetos gravados do banco
     *
     * @return
     */
    int count();

    /**
     * Retorna um objeto do tipo <T> de acordo uma <DetachedCriteria> como
     * parâmetro
     *
     * @param criteria
     * @return
     */
    T getEntityByDetachedCriteria(DetachedCriteria criteria);

    /**
     * Retorna uma lista de Objetos do tipo <T> de acordo uma <DetachedCriteria>
     *
     * @param criteria
     * @return
     */
    List<T> getEntitiesByDetachedCriteria(DetachedCriteria criteria);
}
