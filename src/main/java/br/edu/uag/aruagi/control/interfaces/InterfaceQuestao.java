/**
 * Esta interface tem como objetivo unificar todas as questoes para que possam
 * ser manipuladas independente de seu tipo, permitindo acesso igual atraves dos
 * metodos abaixo descritos
 */
package br.edu.uag.aruagi.control.interfaces;

import br.edu.uag.aruagi.model.NivelQuestao;

/**
 *
 * @author israel
 */
public interface InterfaceQuestao {

    /**
     * Nível da questão
     *
     * @return
     */
    NivelQuestao getNivelQuestao();

    /**
     * Nível da questao
     *
     * @param nivel
     */
    void setNivelQuestao(NivelQuestao nivel);

    /**
     * Id da questao
     *
     * @return
     */
    int getId();

    /**
     * Ida da questao
     *
     * @param id
     */
    void setId(int id);

    /**
     * Status da questao
     *
     * @return
     */
    boolean getStatus();

    /**
     *
     * @param status
     */
    void setStatus(boolean status);

    /**
     * Autor da questao
     *
     * @return
     */
    Integer getUsuario();

    /**
     * Atribuindo o autor da questao
     *
     * @param autor
     */
    void setUsuario(Integer autor);
}
