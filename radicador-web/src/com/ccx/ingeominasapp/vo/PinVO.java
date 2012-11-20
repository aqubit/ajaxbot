package com.ccx.ingeominasapp.vo;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Cacheable(false)
@Table(name = "PIN")
public class PinVO {
	@Id
	@Column(name = "PIN")
	private String PIN;
	@Column(name = "TIPO_DOCUMENTO")
	private Long tipoDocumento;
	@Column(name = "TIPO_PERSONA")
	private Long tipoPersona;
	@Column(name = "MODALIDAD")
	private Long modalidad;
	@Column(name = "NUMERO_DOCUMENTO")
	private String numeroDocumento;

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

	public String getPIN() {
		return PIN;
	}

	public void setPIN(String pIN) {
		PIN = pIN;
	}

	public Long getModalidad() {
		return modalidad;
	}

	public void setModalidad(Long modalidad) {
		this.modalidad = modalidad;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

}
