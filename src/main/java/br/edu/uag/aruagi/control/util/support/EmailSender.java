/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uag.aruagi.control.util.support;

import br.edu.uag.aruagi.model.Usuario;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    public static boolean sendPreparedEmail(Usuario user) throws MessagingException, UnsupportedEncodingException {
        SmtpMail email = new SmtpMail();

        email.setHost("smtp.gmail.com");
        email.setPorta(465);
        email.setAutenticacao(true);
        email.setConexaoSegura(true);

        //dados do remetente
        email.setUsuario("israelita.felipe");
        email.setSenha("1509199215091992");
        email.setEmailRemetente("israelita.felipe@gmail.com");
        email.setNomeRemetente("Israel Araújo");
        //dados do email
        email.setAssunto("REDEFINIÇÃO DE SENHA DO ARUAGI");
        email.setCorpo(user.getNome()+" SUA NOVA SENHA DO ARUAGI E: "+user.getSenha());
        email.setTypeText(SmtpMail.TYPE_TEXT_PLAIN);
        //dados do destinatário
        email.setNomeDestinatario(user.getNome());
        email.setEmailDestinatario(user.getLogin());

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
        session.setDebug(true);
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

    public static void main(String[] args) {
        try {
            EmailSender.sendPreparedEmail(new Usuario());
        } catch (MessagingException ex) {
            Logger.getLogger(EmailSender.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(EmailSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
