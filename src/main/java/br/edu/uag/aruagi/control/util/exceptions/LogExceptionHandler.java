/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uag.aruagi.control.util.exceptions;

import br.edu.uag.aruagi.control.util.jsf.JsfUtil;
import br.edu.uag.aruagi.control.util.support.EmailSender;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.mail.MessagingException;

/**
 *
 * @author Israel Araújo
 */
public class LogExceptionHandler extends ExceptionHandlerWrapper {

    private final ExceptionHandler wrapped;

    LogExceptionHandler(ExceptionHandler exception) {
        this.wrapped = exception;
    }

    @Override
    public ExceptionHandler getWrapped() {
        return wrapped;
    }

    @Override
    public void handle() throws FacesException {
        final Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator();
        while (i.hasNext()) {
            ExceptionQueuedEvent event = i.next();
            ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();
            // capturando a exceção do contexto
            final Throwable t = context.getException();
            final FacesContext fc = FacesContext.getCurrentInstance();
            final Map<String, Object> requestMap = fc.getExternalContext().getRequestMap();
            final NavigationHandler nav = fc.getApplication().getNavigationHandler();
            //fazer os devidos tratamentos
            try {
                //tratando o erro
                /**
                 * enviando emai de erro
                 */
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            EmailSender.sendPreparedEmail("DATA: "+String.valueOf(new Date()) + "\nMENSAGEM: " + t.getMessage()+"\nCAUSA: "+t.getCause()+"\nCOMPLETO: \n"+t.getStackTrace().toString());
                        } catch (MessagingException ex) {
                            Logger.getLogger(LogExceptionHandler.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (UnsupportedEncodingException ex) {
                            Logger.getLogger(LogExceptionHandler.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }).start();
                requestMap.put("exceptionMessage", t.getMessage());                
                nav.handleNavigation(fc, null, "/public/advertencia/Erro.uag");
                fc.renderResponse();
                // avisando do erro
                JsfUtil.addErrorMessage(t.getMessage());
            } finally {
                //removendo o erro
                i.remove();
            }
        }
        getWrapped().handle();
    }
}
