/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.control.util.jsf.JsfUtil;
import br.edu.uag.aruagi.model.Videos;
import br.edu.uag.aruagi.model.VideosPostagem;
import java.io.Serializable;

/**
 *
 * @author israel
 */
public class AutoPostagemController {

    private final static PostagemController postagemController = new PostagemController();

    /**
     * Método para criação automática de postagens
     *
     * @param entity
     */
    public static void create(Serializable entity) {
        try {
            /**
             * preparando uma nova postagem
             */
            postagemController.prepareCreate();
            if (entity instanceof Videos) {
                Videos v = (Videos) entity;
                /**
                 * setando as atualizações dos campos
                 */
                postagemController.getSelected().setTitulo("Novo vídeo adicionado: " + v.getDescricao());
                postagemController.getSelected().setConteudo("Fique atualizado sobre os novos vídeos do aruagi através da aba multimídia");
                postagemController.create();
                /**
                 * controlador de vídeos de postagem
                 */
                VideosPostagemController videosPostagem = new VideosPostagemController();
                /**
                 * granvando o vídeo na postagem atual
                 */
                videosPostagem.setCurrent(new VideosPostagem(0, v, postagemController.getSelected()));
                videosPostagem.create();
            }
        } catch (Exception ex) {
            JsfUtil.addErrorMessage("Seu vídeo foi adicionado, mas nenhuma postagem foi feita");
        }
    }
}
