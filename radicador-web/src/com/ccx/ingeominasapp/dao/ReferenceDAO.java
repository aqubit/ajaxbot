package com.ccx.ingeominasapp.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ccx.ingeominasapp.ConfigSingleton;
import com.ccx.ingeominasapp.vo.DepartamentoVO;
import com.ccx.ingeominasapp.vo.MunicipioVO;
import com.ccx.ingeominasapp.vo.PinVO;
import com.ccx.ingeominasapp.vo.ReferenciaVO;

@Singleton
@Startup
@ApplicationScoped
public class ReferenceDAO {

	private Map<Long, String> tipoDomicilioMap = new LinkedHashMap<Long, String>();
	private Map<Long, String> tipoPersonaNaturalMap = new LinkedHashMap<Long, String>();
	private Map<Long, String> tipoDocumentoNaturalMap = new LinkedHashMap<Long, String>();
	private Map<Long, String> tipoPersonaJuridicaMap = new LinkedHashMap<Long, String>();
	private Map<Long, String> tipoDocumentoJuridicaMap = new LinkedHashMap<Long, String>();
	private Map<Long, String> departamentoMap = new LinkedHashMap<Long, String>();
	private Map<Long, String> planchaIGACMap = new LinkedHashMap<Long, String>();
	private Map<Long, String> mineralesMap = new LinkedHashMap<Long, String>();
	private Map<Long, String> profesionMap = new LinkedHashMap<Long, String>();
	private Map<Long, String> autoridadAmbientalMap = new LinkedHashMap<Long, String>();
	private Map<Long, String> grupoEtnicoMap = new LinkedHashMap<Long, String>();
	private Map<Long, String> comunidadesMap = new LinkedHashMap<Long, String>();
	private Map<Long, Map<Long, String>> municipiosMap = new LinkedHashMap<Long, Map<Long, String>>();
	private List<ReferenciaVO> emptyTable = new ArrayList<ReferenciaVO>();

	@PersistenceContext(unitName = "ccxPU")
	EntityManager em;

	@PostConstruct
	public void init() {
		// Empty table
		for (int i = 0; i < ConfigSingleton.NUM_EMPTY_LINES; i++) {
			emptyTable.add(new ReferenciaVO());
		}
		// Minerales
		initializeReferenceMap("1", mineralesMap);
		// CAR
		initializeReferenceMap("2", autoridadAmbientalMap);
		// Comunidades
		initializeReferenceMap("3", grupoEtnicoMap);
		// Plancha IGAC
		initializeReferenceMap("4", planchaIGACMap);
		// Profesión
		initializeReferenceMap("5", profesionMap);
		// ComunidadesNegrasIndigenas
		initializeReferenceMap("6", comunidadesMap);
		// Tipo domicilio
		initializeReferenceMap("7", tipoDomicilioMap);
		// Tipo persona juridica
		initializeReferenceMap("11", tipoPersonaJuridicaMap);
		// Tipo documento juridica
		initializeReferenceMap("10", tipoDocumentoJuridicaMap);
		// Tipo persona natural
		initializeReferenceMap("8", tipoPersonaNaturalMap);
		// Tipo documento natural
		initializeReferenceMap("9", tipoDocumentoNaturalMap);
		// Departamentos
		TypedQuery<DepartamentoVO> query2 = em.createQuery(
				"select d from DepartamentoVO d", DepartamentoVO.class);
		List<DepartamentoVO> deptos = query2.getResultList();
		for (DepartamentoVO d : deptos) {
			departamentoMap.put(d.getId(), d.getNombre());
		}
		// Municipios
		TypedQuery<MunicipioVO> query3 = em.createQuery(
				"select m from MunicipioVO m", MunicipioVO.class);
		List<MunicipioVO> municipios = query3.getResultList();
		Iterator<MunicipioVO> it = municipios.iterator();
		Long currDptoId = null;
		Map<Long, String> deptoMunicipioMap = null;
		while (it.hasNext()) {
			MunicipioVO m = it.next();
			Long deptoId = m.getId_departamento();
			if (deptoId != currDptoId) {
				deptoMunicipioMap = new LinkedHashMap<Long, String>();
				municipiosMap.put(deptoId, deptoMunicipioMap);
				currDptoId = deptoId;
			}
			deptoMunicipioMap.put(m.getId(), m.getNombre());
		}
	}

	private void initializeReferenceMap(String id, Map<Long, String> map) {
		TypedQuery<ReferenciaVO> query = em.createQuery(
				"select r from ReferenciaVO r where r.id_referencia = " + id,
				ReferenciaVO.class);
		List<ReferenciaVO> referenceList = query.getResultList();
		for (ReferenciaVO r : referenceList) {
			if( r.getValue() != null){
				map.put(r.getValue(), r.getNombre());
			}else{
				map.put(r.getId(), r.getNombre());
			}
		}

	}

	public PinVO getPIN(String PIN, Long tipoDocumento, String numeroDocumento) {
		PinVO result = null;
		TypedQuery<PinVO> query = em
				.createQuery(
						"select p from PinVO p where p.PIN = ?1 and p.tipoDocumento = ?2 and p.numeroDocumento = ?3",
						PinVO.class);
		query.setParameter(1, PIN);
		query.setParameter(2, tipoDocumento);
		query.setParameter(3, numeroDocumento);
		try {
			result = query.getSingleResult();
		} catch (Exception e) {
			// do nothing
		}
		return result;
	}

	public Map<Long, String> getMunicipioMap(Long deptoId) {
		return municipiosMap.get(deptoId);
	}

	public Map<Long, String> getDepartamentoMap() {
		return departamentoMap;
	}

	public Map<Long, String> getTipoDomicilioMap() {
		return tipoDomicilioMap;
	}

	public Map<Long, String> getTipoPersonaNaturalMap() {
		return tipoPersonaNaturalMap;
	}

	public Map<Long, String> getTipoDocumentoNaturalMap() {
		return tipoDocumentoNaturalMap;
	}

	public Map<Long, String> getTipoPersonaJuridicaMap() {
		return tipoPersonaJuridicaMap;
	}

	public Map<Long, String> getTipoDocumentoJuridicaMap() {
		return tipoDocumentoJuridicaMap;
	}

	public Map<Long, String> getPlanchaIGACMap() {
		return planchaIGACMap;
	}

	public Map<Long, String> getMineralesMap() {
		return mineralesMap;
	}

	public Map<Long, String> getProfesionMap() {
		return profesionMap;
	}

	public Map<Long, String> getAutoridadAmbientalMap() {
		return autoridadAmbientalMap;
	}

	public Map<Long, String> getGrupoEtnicoMap() {
		return grupoEtnicoMap;
	}

	public Map<Long, String> getComunidadesMap() {
		return comunidadesMap;
	}

	public Map<Long, Map<Long, String>> getMunicipiosMap() {
		return municipiosMap;
	}

	public List<ReferenciaVO> getEmptyTable() {
		return emptyTable;
	}
}
