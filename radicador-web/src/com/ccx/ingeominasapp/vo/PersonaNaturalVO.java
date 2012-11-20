package com.ccx.ingeominasapp.vo;

import java.util.Date;

public class PersonaNaturalVO {

	Long domTipoPersona;
	Long domTipoDocumento;
	String tipoDocumento;
	String tipoPersona;
	String numeroDocumento;
	String nombres;
	String primerApellido;
	String segundoApellido;
	Date fechaNacimiento;
	String celular;
	String correoElectronico;
	DomicilioVO radPersonaDomicilio = new DomicilioVO();

	public Long getDomTipoPersona() {
		return domTipoPersona;
	}

	public void setDomTipoPersona(Long domTipoPersona) {
		this.domTipoPersona = domTipoPersona;
	}

	public Long getDomTipoDocumento() {
		return domTipoDocumento;
	}

	public void setDomTipoDocumento(Long domTipoDocumento) {
		this.domTipoDocumento = domTipoDocumento;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numDocumento) {
		this.numeroDocumento = numDocumento;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getSegundoApellido() {
		return segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String numCelular) {
		this.celular = numCelular;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public DomicilioVO getRadPersonaDomicilio() {
		return radPersonaDomicilio;
	}

	public void setRadPersonaDomicilio(DomicilioVO radPersonaDomicilio) {
		this.radPersonaDomicilio = radPersonaDomicilio;
	}
}
