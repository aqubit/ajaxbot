package com.ccx.ingeominasapp.view;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("BooleanValidator")
public class BooleanValidator implements Validator {

	public BooleanValidator() {
	}

	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		Boolean bBoolean = (Boolean) value;
		if (bBoolean == null || !bBoolean) {
			FacesMessage msg = null;
			msg = new FacesMessage("Falta info.", null);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}

	}
}
