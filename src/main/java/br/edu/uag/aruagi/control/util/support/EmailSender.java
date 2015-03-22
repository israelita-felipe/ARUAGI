/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uag.aruagi.control.util.support;

import br.edu.uag.aruagi.control.bean.ResolucaoTraduzPalavraController;
import br.edu.uag.aruagi.model.Usuario;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author israel
 */
public class EmailSender {

    /**
     * utilizado para gerenciamento de redefinição de senhas do aruagi
     *
     * @param user
     * @return
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    public static boolean sendPreparedEmail(Usuario user) throws MessagingException, UnsupportedEncodingException {
        SmtpMail email = new SmtpMail();

        email.setHost("smtp.gmail.com");
        email.setPorta(465);
        email.setAutenticacao(true);
        email.setConexaoSegura(true);

        //dados do remetente
        email.setUsuario("ARUAGI.UFRPE.UAG.SENHAS");
        email.setSenha("ARUAGI741852963");
        email.setEmailRemetente("ARUAGI.UFRPE.UAG.SENHAS@gmail.com");
        email.setNomeRemetente("ARUAGI UFRPE");
        //dados do email
        email.setAssunto("REDEFINIÇÃO DE SENHA DO ARUAGI");
        email.setCorpo(user.getNome() + " Sua nova senha do ARUAGI é: " + user.getSenha());
        email.setTypeText(SmtpMail.TYPE_TEXT_PLAIN);
        //dados do destinatário
        email.setNomeDestinatario(user.getNome());
        email.setEmailDestinatario(user.getLogin());

        return send(email);
    }

    /**
     * utilizado para envio de relatório de erros do aruagi
     *
     * @param mensagem
     * @return
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    public static boolean sendPreparedEmail(String mensagem) throws MessagingException, UnsupportedEncodingException {
        SmtpMail email = new SmtpMail();

        email.setHost("smtp.gmail.com");
        email.setPorta(465);
        email.setAutenticacao(true);
        email.setConexaoSegura(true);

        //dados do remetente
        email.setUsuario("ARUAGI.UFRPE.UAG.ERROS");
        email.setSenha("ARUAGI741852963");
        email.setEmailRemetente("ARUAGI.UFRPE.UAG.ERROS@GMAIL.COM");
        email.setNomeRemetente("ARUAGI UFRPE");
        //dados do email
        email.setAssunto("ERRO");
        email.setCorpo(mensagem);
        email.setTypeText(SmtpMail.TYPE_TEXT_PLAIN);
        //dados do destinatário
        email.setNomeDestinatario("ARUAGI UFRPE");
        email.setEmailDestinatario("ARUAGI.UFRPE.UAG.ERROS@GMAIL.COM");

        return send(email);
    }

    /**
     * Envia um e-mail para a destinatário especificado
     *
     * @param titulo
     * @param emailToSend
     * @param conteudo
     * @param nome
     * @return
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    public static boolean sendPreparedEmail(String titulo, String emailToSend, String conteudo, String nome) throws MessagingException, UnsupportedEncodingException {
        SmtpMail email = new SmtpMail();

        email.setHost("smtp.gmail.com");
        email.setPorta(465);
        email.setAutenticacao(true);
        email.setConexaoSegura(true);

        //dados do remetente
        email.setUsuario("ARUAGI.UFRPE.UAG.ERROS");
        email.setSenha("ARUAGI741852963");
        email.setEmailRemetente("ARUAGI.UFRPE.UAG.ERROS@GMAIL.COM");
        email.setNomeRemetente("ARUAGI UFRPE");
        //dados do email
        email.setAssunto(titulo);
        email.setCorpo(emailToSend+"\n"+conteudo);
        email.setTypeText(SmtpMail.TYPE_TEXT_PLAIN);
        //dados do destinatário
        email.setNomeDestinatario(nome);
        email.setEmailDestinatario(emailToSend);

        return send(email);
    }

    /**
     * envia um relatório de de uma atividade respondida pelo aluno
     *
     * @param respostas
     * @return
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    public static boolean sendPreparedEmail(ResolucaoTraduzPalavraController.RespostaTraduzPalavra[] respostas, String destino) throws MessagingException, UnsupportedEncodingException {
        SmtpMail email = new SmtpMail();

        email.setHost("smtp.gmail.com");
        email.setPorta(465);
        email.setAutenticacao(true);
        email.setConexaoSegura(true);

        //dados do remetente
        email.setUsuario("ARUAGI.UFRPE.UAG.SENHAS");
        email.setSenha("ARUAGI741852963");
        email.setEmailRemetente("ARUAGI.UFRPE.UAG.SENHAS@gmail.com");
        email.setNomeRemetente("ARUAGI UFRPE");
        //dados do email
        /*
         email.setAssunto("REDEFINIÇÃO DE SENHA DO ARUAGI");
         email.setCorpo(user.getNome() + " Sua nova senha do ARUAGI é: " + user.getSenha());
         email.setTypeText(SmtpMail.TYPE_TEXT_PLAIN);
         */
        //dados do destinatário
        email.setEmailDestinatario(destino);

        return send(email);
    }

    public static boolean send(final SmtpMail mail) throws MessagingException, UnsupportedEncodingException {
        Properties prop = new Properties();
        prop.setProperty("mail.transport.protocol", "smtp");
        prop.put("mail.smtp.host", mail.getHost());
        prop.put("mail.smtp.auth", mail.isAutenticacao());
        prop.put("mail.smtp.port", mail.getPorta().toString());
        prop.put("mail.smtp.starttls.enable", mail.isConexaoSegura());
        prop.put("mail.smtp.socketFactory.port", mail.getPorta().toString());
        prop.put("mail.smtp.socketFactory.fallback", "false");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        // criando uma nova autenticação
        Authenticator auth = new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mail.getUsuario(), mail.getSenha());
            }
        };

        Session session = Session.getInstance(prop, auth);

        //caso queira habilitar o modo de Debug
        //session.setDebug(true);
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(mail.getEmailRemetente(), mail.getNomeRemetente()));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(mail.getEmailDestinatario(), mail.getNomeDestinatario()));
        message.setSubject(mail.getAssunto());
        message.setContent(mail.getCorpo(), mail.getTypeText());
        //enviando
        Transport.send(message);
        return true;
    }

    public static class SmtpMail {

        //indica se o formato de texto será texto ou html
        public static final String TYPE_TEXT_PLAIN = "text/plain";
        public static final String TYPE_TEXT_HTML = "text/html";
        //indica qual será o servidor de email(gmail, hotmail...)
        private String host;
        //indica a porta de acesso ao servidor
        private Integer porta;
        //indica se há a necessidade de autenticação no servidor
        private Boolean autenticacao;
        //indica se o servidor exige conexão segura (https)
        private Boolean conexaoSegura;
        //usuario do email (o mesmo que você usa para acessar no navegador)
        private String usuario;
        //senha do email
        private String senha;
        //email do remetente
        private String emailRemetente;
        //senha do email do remetente
        private String nomeRemetente;
        //assunto do email
        private String assunto;
        //corpo do email, onde estará o email propriamente dito
        private String corpo;
        //nome do destinatário
        private String nomeDestinatario;
        //email do destinatário
        private String emailDestinatario;
        //tipo do formato da mensagem, texto ou html
        private String typeText;

        /*
         * ENCAPSULAMENTO
         */
        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public Integer getPorta() {
            return porta;
        }

        public void setPorta(Integer porta) {
            this.porta = porta;
        }

        public Boolean isAutenticacao() {
            return autenticacao;
        }

        public void setAutenticacao(Boolean autenticacao) {
            this.autenticacao = autenticacao;
        }

        public Boolean isConexaoSegura() {
            return conexaoSegura;
        }

        public void setConexaoSegura(Boolean conexaoSegura) {
            this.conexaoSegura = conexaoSegura;
        }

        public String getUsuario() {
            return usuario;
        }

        public void setUsuario(String usuario) {
            this.usuario = usuario;
        }

        public String getSenha() {
            return senha;
        }

        public void setSenha(String senha) {
            this.senha = senha;
        }

        public String getEmailRemetente() {
            return emailRemetente;
        }

        public void setEmailRemetente(String emailRemetente) {
            this.emailRemetente = emailRemetente;
        }

        public String getNomeRemetente() {
            return nomeRemetente;
        }

        public void setNomeRemetente(String nomeRemetente) {
            this.nomeRemetente = nomeRemetente;
        }

        public String getAssunto() {
            return assunto;
        }

        public void setAssunto(String assunto) {
            this.assunto = assunto;
        }

        public String getCorpo() {
            return corpo;
        }

        public void setCorpo(String corpo) {
            this.corpo = corpo;
        }

        public String getNomeDestinatario() {
            return nomeDestinatario;
        }

        public void setNomeDestinatario(String nomeDestinatario) {
            this.nomeDestinatario = nomeDestinatario;
        }

        public String getEmailDestinatario() {
            return emailDestinatario;
        }

        public void setEmailDestinatario(String emailDestinatario) {
            this.emailDestinatario = emailDestinatario;
        }

        public String getTypeText() {
            return typeText;
        }

        public void setTypeText(String typeText) {
            this.typeText = typeText;
        }
    }
}
