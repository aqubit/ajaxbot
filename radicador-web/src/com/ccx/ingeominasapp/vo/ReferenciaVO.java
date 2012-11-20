package com.ccx.ingeominasapp.vo;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Cacheable(true)
@Table(name = "Referencias")
public class ReferenciaVO {

	@Id
	@Column(name = "id")
	private Long id;
	@Column(name = "id_referencia")
	private Long id_referencia;
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "value")
	private Long value;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Long getId_referencia() {
		return id_referencia;
	}

	public void setId_referencia(Long id_referencia) {
		this.id_referencia = id_referencia;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

}
