/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uag.aruagi.control.bean;

import br.edu.uag.aruagi.control.util.hibernate.FacesContextUtil;
import br.edu.uag.aruagi.control.util.jsf.JsfUtil;
import br.edu.uag.aruagi.control.util.support.EmailSender;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;

/**
 *
 * @author Israel Araújo
 */
public class ContatoController implements Serializable {

    private String email;
    private String mensagem;
    private String nome;
    private String titulo;

    /**
     * Creates a new instance of ContatoController
     */
    public ContatoController() {
    }

    /**
     * Envia um e-mail em thread para não sobrecarregar o servidor
     * @return String contendo a página de redirecionamento
     * @throws java.io.IOException
     */
    public String send() throws IOException {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    EmailSender.sendPreparedEmail(titulo, email, mensagem, nome);
                    JsfUtil.addSuccessMessage("Mensagem enviada com sucesso");
                } catch (MessagingException ex) {
                    Logger.getLogger(ContatoController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(ContatoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
        return "/index.xhtml?faces-redirect=true";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

}
