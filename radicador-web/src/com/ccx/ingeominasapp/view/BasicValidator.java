package com.ccx.ingeominasapp.view;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("BasicValidator")
public class BasicValidator implements Validator {

	public BasicValidator() {
	}

	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, "msg");
		if (value == null || value.toString().isEmpty()) {

			FacesMessage msg = null;
			if (component.getFamily().equals("javax.faces.SelectOne")) {
				msg = new FacesMessage(
						"Falta info.",
						bundle.getString("page.validation.missing.data.dropdown"));
			} else {
				msg = new FacesMessage("Falta info.",
						bundle.getString("page.validation.missing.data.txtbox"));
			}
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
	}
}
