/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.edu.uag.aruagi.model.interfaces;

/**
 *
 * @author Israel Araújo
 */
public interface InterfaceEntity {

    /**
     * status de uso no banco de dados
     * @return boolean
     */
    Boolean getStatus();
    
    /**
     * entrada do status
     * @param status 
     */
    void setStatus(Boolean status);

    /**
     * id do usuario autor da entidade
     * @return Integer
     */
    Integer getUsuario();

    /**
     * insercao do id do usuario autor da entidade
     * @param id 
     */
    void setUsuario(Integer id);
}
