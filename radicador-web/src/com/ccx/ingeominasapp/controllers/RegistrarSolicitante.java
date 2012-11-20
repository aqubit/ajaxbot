package com.ccx.ingeominasapp.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

import com.ccx.ingeominasapp.ConfigSingleton;
import com.ccx.ingeominasapp.dao.ReferenceDAO;
import com.ccx.ingeominasapp.vo.DomicilioVO;
import com.ccx.ingeominasapp.vo.PersonaJuridicaVO;
import com.ccx.ingeominasapp.vo.PersonaNaturalVO;
import com.ccx.ingeominasapp.vo.PinVO;

@Stateful
@SessionScoped
@Named("registrarSolicitante")
public class RegistrarSolicitante {

	@Inject
	private ReferenceDAO dao;

	private boolean editMode = false;
	private Boolean renderedEditNatural;
	private Boolean renderedDeleteNatural;
	private Boolean renderedDeleteJuridica;
	private Boolean modalRenderedJuridica;
	private Boolean modalRenderedNatural;
	private Boolean readOnlyPersonaNaturalPIN = false;
	private Boolean readOnly = false;
	private Boolean renderedPaginatorNatural;
	private Boolean renderedPaginatorJuridica;

	List<PersonaNaturalVO> personasNaturales = new ArrayList<PersonaNaturalVO>();
	private List<PersonaNaturalVO> emptyTable = new ArrayList<PersonaNaturalVO>();
	List<PersonaJuridicaVO> personasJuridicas = new ArrayList<PersonaJuridicaVO>();

	PersonaJuridicaVO personaJuridica = new PersonaJuridicaVO();
	PersonaNaturalVO personaNatural = new PersonaNaturalVO();

	public RegistrarSolicitante() {
	}

	@PostConstruct
	public void init() {
		// Personas Naturales
		for (int i = 0; i < ConfigSingleton.NUM_EMPTY_LINES; i++) {
			emptyTable.add(new PersonaNaturalVO());
		}
		personasNaturales = emptyTable;
	}

	public Boolean getRenderedDeleteJuridica() {
		return renderedDeleteJuridica;
	}

	public Boolean getModalRenderedNatural() {
		return modalRenderedNatural;
	}

	public void setModalRenderedNatural(Boolean modalRenderedNatural) {
		this.modalRenderedNatural = modalRenderedNatural;
	}

	public Boolean getRenderedEditNatural() {
		return renderedEditNatural;
	}

	public void setRenderedEditNatural(Boolean renderedEditNatural) {
		this.renderedEditNatural = renderedEditNatural;
	}

	public Boolean getRenderedDeleteNatural() {
		return renderedDeleteNatural;
	}

	public void setRenderedDeleteNatural(Boolean renderedNatural) {
		this.renderedDeleteNatural = renderedNatural;
	}

	public Boolean getModalRenderedJuridica() {
		return modalRenderedJuridica;
	}

	public void setModalRenderedJuridica(Boolean modalRenderedJuridica) {
		this.modalRenderedJuridica = modalRenderedJuridica;
	}

	public PersonaJuridicaVO getPersonaJuridica() {
		return personaJuridica;
	}

	public void setPersonaJuridica(PersonaJuridicaVO personaJuridica) {
		this.personaJuridica = personaJuridica;
	}

	public PersonaNaturalVO getPersonaNatural() {
		return personaNatural;
	}

	public void setPersonaNatural(PersonaNaturalVO personaNatural) {
		this.personaNatural = personaNatural;
	}

	public Boolean getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}

	public Boolean getRenderedPaginatorNatural() {
		return renderedPaginatorNatural;
	}

	public Boolean getRenderedPaginatorJuridica() {
		return renderedPaginatorJuridica;
	}

	public Boolean getReadOnlyPersonaNaturalPIN() {
		return readOnlyPersonaNaturalPIN;
	}

	public Boolean getReadOnlyPersonaJuridicaPIN() {
		return (personaJuridica == personasJuridicas.get(0));
	}

	public List<PersonaNaturalVO> getTableNatural() {
		return personasNaturales;
	}

	public List<PersonaJuridicaVO> getTableJuridica() {
		return personasJuridicas;
	}

	public Boolean getIsIncompleteForm() {
		boolean bIncomplete = false;
		if (!personasJuridicas.isEmpty()) {
			PersonaJuridicaVO p = personasJuridicas.get(0);
			DomicilioVO d = p.getRadPersonaDomicilio();
			PersonaNaturalVO r = p.getRadRepresentanteLegal();
			bIncomplete |= (p.getRazonSocial() == null || p
					.getRazonSocial().isEmpty());
			bIncomplete |= (p.getNumeroDocumento() == null || p
					.getNumeroDocumento().isEmpty());
			bIncomplete |= d.getTipoDomicilio() == null;
			bIncomplete |= d.getCodigoDepartamentoJuridica() == null;
			bIncomplete |= d.getCodigoMunicipioJuridica() == null;
			bIncomplete |= (d.getDireccion() == null || d
					.getDireccion().isEmpty());
			bIncomplete |= (d.getTelefono() == null || d
					.getTelefono().isEmpty());
			bIncomplete |= (r.getNumeroDocumento()== null || r
					.getNumeroDocumento().isEmpty());
			bIncomplete |= (r.getNombres()== null || r
					.getNombres().isEmpty());
			bIncomplete |= (r.getPrimerApellido()== null || r
					.getPrimerApellido().isEmpty());
		} else {
			bIncomplete = true;
		}
		return bIncomplete;
	}

	// Action Events Jurídica

	public String addJuridicaAction() {
		modalRenderedJuridica = true;
		personaJuridica = new PersonaJuridicaVO();
		return null;
	}

	public void cancelJuridicaAction(ActionEvent event) {
		modalRenderedJuridica = false;
		editMode = false;
	}

	public void confirmJuridicaActionListener(ActionEvent event) {
		modalRenderedJuridica = false;
		String label = dao.getTipoDocumentoJuridicaMap().get(
				personaJuridica.getDomTipoDocumento());
		personaJuridica.setTipoDocumento(label);
		label = dao.getTipoPersonaJuridicaMap().get(
				personaJuridica.getDomTipoPersona());
		personaJuridica.setTipoPersona(label);
		if (!editMode) {
			personasJuridicas.add(personaJuridica);
		}
		if (personasJuridicas.size() > ConfigSingleton.PAGINATOR_LOW_LIMIT) {
			renderedPaginatorJuridica = true;
		}
		editMode = false;

	}

	public String deleteJuridicaAction() {
		if (personasJuridicas.size() > 1) {
			personasJuridicas.remove(personaJuridica);
		}
		if (personasJuridicas.size() <= ConfigSingleton.PAGINATOR_LOW_LIMIT) {
			renderedPaginatorJuridica = false;
		}
		return null;
	}

	public String editJuridicaAction() {
		modalRenderedJuridica = true;
		editMode = true;
 		return null;
	}

	// ----------------------------------------------------------------
	// ACTION LISTENERS
	// ----------------------------------------------------------------

	public void onRegistration(@Observes @LoginEvent PinVO pinObject) {
		if (pinObject != null) {
			PersonaJuridicaVO p = new PersonaJuridicaVO();
			p.setTipoDocumento("NIT");
			p.setTipoPersona("Jurídica");
			p.setDomTipoDocumento(pinObject.getTipoDocumento());
			p.setDomTipoPersona(pinObject.getTipoPersona());
			p.setNumeroDocumento(pinObject.getNumeroDocumento());
			p.setRenderedDeleteJuridica(false);
			personasJuridicas.add(p);
		}
	}

	public void cancelNaturalAction(ActionEvent event) {
		modalRenderedNatural = false;
		editMode = false;
	}

	public void confirmNaturalActionListener(ActionEvent event) {
		if (personasNaturales == emptyTable) {
			personasNaturales = new ArrayList<PersonaNaturalVO>();
		}
		modalRenderedNatural = false;
		String label = dao.getTipoDocumentoNaturalMap().get(
				personaNatural.getDomTipoDocumento());
		personaNatural.setTipoDocumento(label);
		label = dao.getTipoPersonaNaturalMap().get(
				personaNatural.getDomTipoPersona());
		personaNatural.setTipoPersona(label);
		if (!editMode) {
			personasNaturales.add(personaNatural);
		}
		if (personasNaturales.size() > ConfigSingleton.PAGINATOR_LOW_LIMIT) {
			renderedPaginatorNatural = true;
		}
		renderedEditNatural = true;
		renderedDeleteNatural = true;
		editMode = false;
	}

	public void addNaturalAction(ActionEvent event) {
		modalRenderedNatural = true;
		personaNatural = new PersonaNaturalVO();
	}

	public String editNaturalAction() {
		modalRenderedNatural = true;
		editMode = true;
		return null;
	}

	public String deleteNaturalAction() {
		personasNaturales.remove(personaNatural);
		if (personasNaturales.isEmpty()) {
			personasNaturales = emptyTable;
			renderedEditNatural = false;
			renderedDeleteNatural = false;
		}
		if (personasNaturales.size() <= ConfigSingleton.PAGINATOR_LOW_LIMIT) {
			renderedPaginatorNatural = false;
		}
		return null;
	}

	public String cancel() {
		editMode = false;
		renderedEditNatural = false;
		renderedDeleteNatural = false;
		renderedDeleteJuridica = false;
		modalRenderedJuridica = false;
		modalRenderedNatural = false;
		readOnlyPersonaNaturalPIN = false;
		readOnly = false;
		renderedPaginatorNatural = false;
		renderedPaginatorJuridica = false;
		personasNaturales = emptyTable;
		personaJuridica = new PersonaJuridicaVO();
		personaNatural = new PersonaNaturalVO();
		if (personasJuridicas.size() > 1) {
			for (int i = personasJuridicas.size() - 1; i >= 1; i--) {
				personasJuridicas.remove(i);
			}
		}
		return null;
	}

	public void valueChangeDepartamentoJuridica(ValueChangeEvent e) {
	}

	public void valueChangeMunicipioJuridica(ValueChangeEvent e) {
	}

	public void valueChangeDepartamentoNatural(ValueChangeEvent e) {
	}

	// Data providers

	public Map<Long, String> getMunicipioNaturalMap() {
		return dao.getMunicipioMap(personaNatural.getRadPersonaDomicilio()
				.getCodigoDepartamentoNatural());
	}

	public Map<Long, String> getMunicipioJuridicaMap() {
		return dao.getMunicipioMap(personaJuridica.getRadPersonaDomicilio()
				.getCodigoDepartamentoJuridica());
	}

	public Map<Long, String> getTipoDomicilioMap() {
		return dao.getTipoDomicilioMap();
	}

	public Map<Long, String> getTipoPersonaNaturalMap() {
		return dao.getTipoPersonaNaturalMap();
	}

	public Map<Long, String> getTipoDocumentoNaturalMap() {
		return dao.getTipoDocumentoNaturalMap();
	}

	public Map<Long, String> getDepartamentoMap() {
		return dao.getDepartamentoMap();
	}

	public Map<Long, String> getTipoPersonaJuridicaMap() {
		return dao.getTipoPersonaJuridicaMap();
	}

	public Map<Long, String> getTipoDocumentoJuridicaMap() {
		return dao.getTipoDocumentoJuridicaMap();
	}

}
