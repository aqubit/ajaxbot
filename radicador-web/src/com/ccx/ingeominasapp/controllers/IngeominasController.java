package com.ccx.ingeominasapp.controllers;

import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import com.ccx.ingeominasapp.dao.ReferenceDAO;
import com.ccx.ingeominasapp.vo.PinVO;

@Stateful
@SessionScoped
@Named("radweb")
public class IngeominasController {
	@Inject
	private CaptchaController captcha;
	@Inject
	private ReferenceDAO dao;
	@Inject
	MessageSingleton message;
	@Inject
	RegistrarLocalizacion registrarLocalizacion;
	@Inject
	RegistrarSolicitante registrarSolicitante;
	@Inject
	RegistrarSolicitud registrarSolicitud;

	private int _iCurrentTab = 1;
	private Boolean enRadicacion = false;
	private Boolean guardado = false;
	private String numeroDocumento;
	private Long tipoDocumento;
	private Long tipoPersona;
	private PinVO pin;

	@Inject
	@LoginEvent
	private Event<PinVO> event;

	public Boolean getGuardado() {
		return guardado;
	}

	public void setGuardado(Boolean guardado) {
		this.guardado = guardado;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public Long getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(Long tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Long getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(Long tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public Boolean getEnRadicacion() {
		return enRadicacion;
	}

	public void setEnRadicacion(Boolean enRadicacion) {
		this.enRadicacion = enRadicacion;
	}

	public void doDatosSolicitantePressed(ActionEvent event) {
		_iCurrentTab = 1;
	}

	public void doDatosSolicitudPressed(ActionEvent event) {
		_iCurrentTab = 2;
	}

	public void doDatosLocalizacionPressed(ActionEvent event) {
		_iCurrentTab = 3;
	}

	public int getCurrentTab() {
		return _iCurrentTab;
	}

	public String getDomModalidad() {
		return pin.getModalidad().toString();
	}

	public String visualizarSolicitante() {
		_iCurrentTab = 1;
		return null;
	}

	public String visualizarLocalizacion() {
		_iCurrentTab = 3;
		return null;
	}

	public String visualizarSolicitud() {
		_iCurrentTab = 2;
		return null;
	}

	// ----------------------------------------------------------------
	// ACTION LISTENERS
	// ----------------------------------------------------------------

	public boolean validarLogin(String PIN, Long tipoDocumento,
			String numeroDocumento) {
		pin = dao.getPIN(PIN, tipoDocumento, numeroDocumento);
		if (pin != null) {
			event.fire(pin);
			return true;
		}
		return false;
	}

	public String prepareRadicar() {
		boolean bFormsComplete = true;
		if (!guardado) {
			message.addError("page.validation.sin.guardar");
		} else {
			if (registrarSolicitante.getIsIncompleteForm()) {
				bFormsComplete = false;
				message.addError("page.validation.missing.data.solicitante.incomplete.resumen");
			}
			if (registrarSolicitud.getIsIncompleteForm()) {
				bFormsComplete = false;
				message.addError("page.validation.missing.data.solicitud");
			}
			if (registrarLocalizacion.getIsIncompleteForm()) {
				bFormsComplete = false;
				message.addError("page.validation.missing.data.localizacion.resumen");
			}
			if (bFormsComplete) {
				enRadicacion = true;
				captcha.setCaptchaRendered(true);
			}
		}
		return null;
	}

	public String logout() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		session.invalidate();
		return null;
	}

	public String prepareSave() {
		captcha.setCaptchaRendered(true);
		return null;
	}
}
