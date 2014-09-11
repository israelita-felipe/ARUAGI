/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uag.aruagi.control.util.exceptions;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

/**
 *
 * @author Israel Araújo
 */
public class LogExceptionFactory extends ExceptionHandlerFactory {

    /**
     * fábrica de captura de exceção
     */
    private ExceptionHandlerFactory parent;

    public LogExceptionFactory(ExceptionHandlerFactory parent) {
        this.parent = parent;
    }

    /**
     * sobreescrita da captura
     *
     * @return
     */
    @Override
    public ExceptionHandler getExceptionHandler() {
        ExceptionHandler handler = new LogExceptionHandler(parent.getExceptionHandler());
        return handler;
    }

}
