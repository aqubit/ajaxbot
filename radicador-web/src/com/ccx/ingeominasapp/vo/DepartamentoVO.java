package com.ccx.ingeominasapp.vo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Cacheable(true)
@Table(name = "Departamentos")
public class DepartamentoVO {

	@Id
	@Column(name = "id")
	private Long id;
	@Column(name = "nombre")
	private String nombre;
	@Transient
	List<MunicipioVO> radMunicipios = new ArrayList<MunicipioVO>();

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

	public List<MunicipioVO> getRadMunicipios() {
		return radMunicipios;
	}

	public void setRadMunicipios(List<MunicipioVO> radMunicipios) {
		this.radMunicipios = radMunicipios;
	}
}
