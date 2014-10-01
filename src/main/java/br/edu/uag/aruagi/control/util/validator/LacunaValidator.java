/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uag.aruagi.control.util.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Israel Araújo
 */
public class LacunaValidator implements Validator {

    private static final String LACUNA = " _ ";

    public LacunaValidator() {
    }

    @Override
    public void validate(FacesContext context,
            UIComponent component,
            Object value)
            throws ValidatorException {

        if (find(String.valueOf(value), 0) == -1) {
            FacesMessage message = new FacesMessage("Não há lacuna",
                    "Por favor insira uma lacuna na frase");

            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }

    /**
     * encontra uma lacuna de acordo um index
     *
     * @param frase
     * @param index
     * @return
     */
    private static int find(String frase, int index) {
        return frase.indexOf(LacunaValidator.LACUNA, index);
    }

    /**
     * conta as lacunas de uma palavra
     *
     * @param frase
     * @return
     */
    public static int count(String frase) {
        int index = find(frase, 0);
        int count = 0;
        while (index != -1 && index < frase.length()) {
            count++;
            index = find(frase, index);
        }
        return count;
    }
}
