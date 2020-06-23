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
@FacesValidator("wordValidator")
public class WordValidator implements Validator {
    
    // Match word and whitespace
    private static final String REGEX_NAME = "^[\\p{L}\\s]+$";
    private static final Pattern PATTERN = Pattern.compile(REGEX_NAME, Pattern.CASE_INSENSITIVE);
    private static Matcher matcher;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        matcher = PATTERN.matcher(value.toString());

        if (!(matcher.matches())) {
            context.addMessage(component.getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, null, "Enter valid country name."));

            FacesMessage msg
                    = new FacesMessage(" Country validation failed.",
                            "Country validation failed - try again.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);

            throw new ValidatorException(msg);
        }
    }
}
