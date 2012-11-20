package com.ccx.ingeominasapp.view;

import java.math.BigDecimal;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("MoneyValidator")
public class MoneyValidator implements Validator {

	private final static BigDecimal ZERO = new BigDecimal(0);

	public MoneyValidator() {
	}

	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		BigDecimal lValue = (BigDecimal) value;
		if (lValue == null || lValue.compareTo(ZERO) == 0) {
			FacesMessage msg = null;
			msg = new FacesMessage("Falta info.", null);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}

	}
}
