package com.ccx.ingeominasapp.view;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("FormValidator")
public class FormValidator implements Validator {

	public FormValidator() {
	}

	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		UIInput input = (UIInput) component;
		String validationMessage= input.getValidatorMessage();
		if (value == null || value.toString().isEmpty()) {
			FacesMessage msg = new FacesMessage(validationMessage, validationMessage);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(null, msg);
		}
	}
}
