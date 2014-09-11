/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uag.aruagi.control.util.validator;

import br.edu.uag.aruagi.model.PalavraLatim;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Israel Araújo
 */
public class TraducaoValidator implements Validator {

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        PalavraLatim palavra = (PalavraLatim) o;
        if (palavra.getTraduzPalavras().isEmpty()) {
            FacesMessage message = new FacesMessage("Palavra não possui traduções disponíveis",
                    "Palavra " + palavra.getPalavra() + " precisa de pelo menos uma tradução");

            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }

}
