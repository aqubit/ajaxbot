package com.ccx.ingeominasapp.vo;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Cacheable(true)
@Table(name = "Municipios")
public class MunicipioVO {

	@Id
	@Column(name = "id")
	private Long id;
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "id_departamento")
	private Long id_departamento;
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
	public Long getId_departamento() {
		return id_departamento;
	}
	public void setId_departamento(Long id_departamento) {
		this.id_departamento = id_departamento;
	}
}
