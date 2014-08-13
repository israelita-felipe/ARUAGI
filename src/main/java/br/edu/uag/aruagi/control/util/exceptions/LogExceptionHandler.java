/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uag.aruagi.control.util.exceptions;

import java.util.Iterator;
import java.util.Map;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

/**
 *
 * @author Israel Araújo
 */
public class LogExceptionHandler extends ExceptionHandlerWrapper {

    private ExceptionHandler exceptionHandler;

    
    public LogExceptionHandler(ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    @Override
    public ExceptionHandler getWrapped() {
        return this.exceptionHandler;
    }

    @Override
    public void handle() throws FacesException {
        for (Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator(); i.hasNext();) {
            ExceptionQueuedEvent exceptionQueuedEvent = i.next();

            ExceptionQueuedEventContext exceptionQueuedEventContext = (ExceptionQueuedEventContext) exceptionQueuedEvent.getSource();

            Throwable throwable = exceptionQueuedEventContext.getException();

            if (throwable instanceof Throwable) {
                Throwable t = (Throwable) throwable;
                FacesContext facesContext
                        = FacesContext.getCurrentInstance();

                Map<String, Object> requestMap
                        = facesContext.getExternalContext().getRequestMap();

                try {

                    requestMap.put("currentView", t.getMessage());
                    facesContext.getExternalContext().getFlash().put("exceptioniNFO",
                            t.getCause());
                    facesContext.renderResponse();
                } finally {
                    facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ops! Algo aconteceu", t.getCause().getMessage()));
                    i.remove();
                }
            }

        }
        getWrapped().handle();
    }
}
