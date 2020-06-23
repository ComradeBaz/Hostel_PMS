/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author ComradeBaz
 */
@FacesValidator("nameValidator")
public class NameValidator implements Validator {

    // Match a name with unicode letters, , ., ', or - 
    //private static final String REGEX_NAME = "^[\\p{L} .'-]+$";
    private static final String REGEX_NAME = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern PATTERN = Pattern.compile(REGEX_NAME, Pattern.CASE_INSENSITIVE);
    private static Matcher matcher;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        matcher = PATTERN.matcher(value.toString());

        if (!(matcher.matches())) {
            context.addMessage(component.getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, null, "Username must be an email address - eg email@example.com."));

            FacesMessage msg
                    = new FacesMessage(" Name validation failed.",
                            "Username must be an email address - eg email@example.com.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);

            throw new ValidatorException(msg);
        }
    }

}
