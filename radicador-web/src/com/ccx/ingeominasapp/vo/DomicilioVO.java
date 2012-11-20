package com.ccx.ingeominasapp.vo;

public class DomicilioVO {
	String direccion;
	Integer tipoDomicilio;
	Long codigoDepartamentoJuridica;
	Long codigoMunicipioJuridica;
	Long codigoDepartamentoNatural;
	Long codigoMunicipioNatural;
	String telefono;
	String telefonoAlterno;
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public Integer getTipoDomicilio() {
		return tipoDomicilio;
	}
	public void setTipoDomicilio(Integer tipoDomicilio) {
		this.tipoDomicilio = tipoDomicilio;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getTelefonoAlterno() {
		return telefonoAlterno;
	}
	public void setTelefonoAlterno(String telefonoAlterno) {
		this.telefonoAlterno = telefonoAlterno;
	}
	public Long getCodigoDepartamentoJuridica() {
		return codigoDepartamentoJuridica;
	}
	public void setCodigoDepartamentoJuridica(Long codigoDepartamentoJuridica) {
		this.codigoDepartamentoJuridica = codigoDepartamentoJuridica;
	}
	public Long getCodigoMunicipioJuridica() {
		return codigoMunicipioJuridica;
	}
	public void setCodigoMunicipioJuridica(Long codigoMunicipioJuridica) {
		this.codigoMunicipioJuridica = codigoMunicipioJuridica;
	}
	public Long getCodigoDepartamentoNatural() {
		return codigoDepartamentoNatural;
	}
	public void setCodigoDepartamentoNatural(Long codigoDepartamentoNatural) {
		this.codigoDepartamentoNatural = codigoDepartamentoNatural;
	}
	public Long getCodigoMunicipioNatural() {
		return codigoMunicipioNatural;
	}
	public void setCodigoMunicipioNatural(Long codigoMunicipioNatural) {
		this.codigoMunicipioNatural = codigoMunicipioNatural;
	}
}
