package com.ccx.ingeominasapp.vo;

import java.util.Date;

public class PersonaJuridicaVO {

	Long domTipoPersona;
	Long domTipoDocumento;
	String tipoPersona;
	String tipoDocumento;
	String numeroDocumento;
	String razonSocial;
	String primerApellido;
	String segundoApellido;
	Date fechaNacimiento;
	String numCelular;
	String correoElectronico;
	Boolean renderedDeleteJuridica = true;
	PersonaNaturalVO radRepresentanteLegal = new PersonaNaturalVO();
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

	public Boolean getRenderedDeleteJuridica() {
		return renderedDeleteJuridica;
	}

	public void setRenderedDeleteJuridica(Boolean renderedDeleteJuridica) {
		this.renderedDeleteJuridica = renderedDeleteJuridica;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numDocumento) {
		this.numeroDocumento = numDocumento;
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
		return numCelular;
	}

	public void setCelular(String numCelular) {
		this.numCelular = numCelular;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public PersonaNaturalVO getRadRepresentanteLegal() {
		return radRepresentanteLegal;
	}

	public void setRadRepresentanteLegal(PersonaNaturalVO radRepresentanteLegal) {
		this.radRepresentanteLegal = radRepresentanteLegal;
	}

	public DomicilioVO getRadPersonaDomicilio() {
		return radPersonaDomicilio;
	}

	public void setRadPersonaDomicilio(DomicilioVO radPersonaDomicilio) {
		this.radPersonaDomicilio = radPersonaDomicilio;
	}
}
