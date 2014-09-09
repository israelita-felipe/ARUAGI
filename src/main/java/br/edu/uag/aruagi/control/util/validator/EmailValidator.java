/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uag.aruagi.control.util.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Israel Araújo
 */
public class EmailValidator implements Validator {

    private static final String EMAIL_REGEX = "(.+@.+\\.[a-zA-Z]+)?";
    private Pattern pattern;
    private Matcher matcher;

    public EmailValidator() {
        pattern = Pattern.compile(EMAIL_REGEX);
    }

    @Override
    public void validate(FacesContext context,
            UIComponent component,
            Object value)
            throws ValidatorException {

        matcher = pattern.matcher(value.toString());

        if (!matcher.matches()) {
            FacesMessage message = new FacesMessage("E-mail incorreto",
                    "Use: nome@mail.com.br");

            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }
}
