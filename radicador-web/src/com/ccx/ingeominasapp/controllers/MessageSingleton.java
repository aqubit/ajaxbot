package com.ccx.ingeominasapp.controllers;

import java.util.ResourceBundle;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@RequestScoped
@Named("message")
public class MessageSingleton {


	public void addMessage(String key) {
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, "msg");
		FacesMessage message = new FacesMessage();
		message.setSummary(bundle.getString(key));
		message.setDetail(bundle.getString(key));
		context.addMessage(null, message);
	}

	public void addError(String key) {
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, "msg");
		FacesMessage message = new FacesMessage();
		message.setSummary(bundle.getString(key));
		message.setDetail(bundle.getString(key));
		message.setSeverity(FacesMessage.SEVERITY_ERROR);
		context.addMessage(null, message);
	}

	public void addMessage(String clientId, String key) {
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, "msg");
		FacesMessage message = new FacesMessage();
		message.setSummary(bundle.getString(key));
		context.addMessage(clientId, message);
	}

}
