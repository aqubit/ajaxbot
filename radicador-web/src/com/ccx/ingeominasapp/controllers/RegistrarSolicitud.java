package com.ccx.ingeominasapp.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.icefaces.ace.component.fileentry.FileEntry;
import org.icefaces.ace.component.fileentry.FileEntryEvent;
import org.icefaces.ace.component.fileentry.FileEntryResults;

import com.ccx.ingeominasapp.ConfigSingleton;
import com.ccx.ingeominasapp.dao.ReferenceDAO;
import com.ccx.ingeominasapp.vo.AutorPlanoVO;
import com.ccx.ingeominasapp.vo.CoordenadaVO;
import com.ccx.ingeominasapp.vo.RecursosVO;
import com.ccx.ingeominasapp.vo.ReferenciaVO;

@Stateful
@SessionScoped
@Named("registrarSolicitud")
public class RegistrarSolicitud {
	@Inject
	private ReferenceDAO dao;

	private final static BigDecimal ZERO = new BigDecimal(0);
	List<SelectItem> booleanoList = new ArrayList<SelectItem>();
	private List<ReferenciaVO> minerales = null;
	private List<ReferenciaVO> autoAmbientales = null;
	private List<ReferenciaVO> grupoEtnicos = null;
	private Map<Long, String> mineralesMap = null;
	private Map<Long, String> autoridadAmbientalMap = null;
	private Map<Long, String> grupoEtnicoMap = null;
	private Long codigoMineral;
	private Long codigoAutoAmbiental;
	private String seleccionExplotacion = "2";
	private String seleccionGrupoEtnico = "2";
	private String seleccionZonasMineras = "2";
	private String seleccionMineriaRestringida = "2";
	private String[] mineriaRestringida;
	private Long ubicacionArea;
	private Long codigoGrupoEtnico;
	private String metodologia;
	private String anexoMetodologia;
	private Boolean readOnlyMineral = false;
	private Boolean renderedDeleteMineral = false;
	private Boolean renderedPaginatorMineral = false;
	private Boolean renderedDeleteAutoAmbiental = false;
	private Boolean renderedPaginatorAutoAmbiental = false;
	private Boolean renderedDeleteGrupoEtnico = false;
	private Boolean renderedDeleteDocumento = false;
	private Boolean renderedDeleteCoordenada = false;
	private Boolean renderedPaginatorCoordenada = false;
	private Boolean terminosReferencia = false;
	private String selectedGroup = "groupOne";
	private Boolean renderedPaginatorGrupoEtnico = false;
	private RecursosVO radSoliEstanual = new RecursosVO();
	private CoordenadaVO coordenada = new CoordenadaVO();
	private AutorPlanoVO autorPlano = new AutorPlanoVO();
	private List<CoordenadaVO> emptyTableCoords = new ArrayList<CoordenadaVO>();
	private List<CoordenadaVO> radCoordenadas = null;
	private String repositorioDocumentosTmpPath = "";
	private String repositorioDocumentosPath = "";

	public RegistrarSolicitud() {
	}

	@PostConstruct
	public void init() {
		SelectItem item = new SelectItem();
		item.setLabel("No");
		item.setValue("2");
		booleanoList.add(item);
		item = new SelectItem();
		item.setLabel("Si");
		item.setValue("1");
		booleanoList.add(item);
		mineralesMap = new LinkedHashMap<Long, String>(dao.getMineralesMap());
		autoridadAmbientalMap = new LinkedHashMap<Long, String>(
				dao.getAutoridadAmbientalMap());
		grupoEtnicoMap = new LinkedHashMap<Long, String>(
				dao.getGrupoEtnicoMap());

		minerales = dao.getEmptyTable();
		autoAmbientales = dao.getEmptyTable();
		grupoEtnicos = dao.getEmptyTable();
		for (int i = 0; i < ConfigSingleton.NUM_EMPTY_LINES; i++) {
			emptyTableCoords.add(new CoordenadaVO());
		}
		radCoordenadas = emptyTableCoords;
	}

	public Map<Long, String> getMineralesMap() {
		return mineralesMap;
	}

	public Map<Long, String> getProfesionMap() {
		return dao.getProfesionMap();
	}

	public Map<Long, String> getAutoridadAmbientalMap() {
		return autoridadAmbientalMap;
	}

	public Map<Long, String> getGrupoEtnicoMap() {
		return grupoEtnicoMap;
	}

	public Map<Long, String> getComunidadesMap() {
		return dao.getComunidadesMap();
	}

	public String[] getMineriaRestringida() {
		return mineriaRestringida;
	}

	public Boolean getIsEmptyMineriaRestringida() {
		Boolean result = false;
		result = mineriaRestringida == null;
		if (mineriaRestringida != null) {
			result |= (mineriaRestringida.length == 0);
		}
		return result;
	}

	public void setMineriaRestringida(String[] mineriaRestringida) {
		this.mineriaRestringida = mineriaRestringida;
	}

	public List<CoordenadaVO> getRadCoordenadas() {
		return radCoordenadas;
	}

	public void setRadCoordenadas(List<CoordenadaVO> radCoordenadas) {
		this.radCoordenadas = radCoordenadas;
	}

	public String getRepositorioDocumentosPath() {
		return repositorioDocumentosPath;
	}

	public void setRepositorioDocumentosPath(String repositorioDocumentosPath) {
		this.repositorioDocumentosPath = repositorioDocumentosPath;
	}

	public AutorPlanoVO getAutorPlano() {
		return autorPlano;
	}

	public void setAutorPlano(AutorPlanoVO autorPlano) {
		this.autorPlano = autorPlano;
	}

	public Long getCodigoAutoAmbiental() {
		return codigoAutoAmbiental;
	}

	public void setCodigoAutoAmbiental(Long codigoAutoAmbiental) {
		this.codigoAutoAmbiental = codigoAutoAmbiental;
	}

	public Long getCodigoMineral() {
		return codigoMineral;
	}

	public void setCodigoMineral(Long codigoMineral) {
		this.codigoMineral = codigoMineral;
	}

	public Long getUbicacionArea() {
		return ubicacionArea;
	}

	public void setUbicacionArea(Long ubicacionArea) {
		this.ubicacionArea = ubicacionArea;
	}

	public Long getCodigoGrupoEtnico() {
		return codigoGrupoEtnico;
	}

	public void setCodigoGrupoEtnico(Long codigoGrupoEtnico) {
		this.codigoGrupoEtnico = codigoGrupoEtnico;
	}

	public String getSeleccionGrupoEtnico() {
		return seleccionGrupoEtnico;
	}

	public void setSeleccionGrupoEtnico(String seleccionGrupoEtnico) {
		this.seleccionGrupoEtnico = seleccionGrupoEtnico;
	}

	public String getSeleccionZonasMineras() {
		return seleccionZonasMineras;
	}

	public void setSeleccionZonasMineras(String seleccionZonasMineras) {
		this.seleccionZonasMineras = seleccionZonasMineras;
	}

	public String getSeleccionMineriaRestringida() {
		return seleccionMineriaRestringida;
	}

	public void setSeleccionMineriaRestringida(
			String seleccionMineriaRestringida) {
		this.seleccionMineriaRestringida = seleccionMineriaRestringida;
	}

	public String getSeleccionExplotacion() {
		return seleccionExplotacion;
	}

	public void setSeleccionExplotacion(String seleccionExplotacion) {
		this.seleccionExplotacion = seleccionExplotacion;
	}

	public Boolean getReadOnlyMineral() {
		return readOnlyMineral;
	}

	public void setReadOnlyMineral(Boolean readOnlyMineral) {
		this.readOnlyMineral = readOnlyMineral;
	}

	public Boolean getRenderedDeleteMineral() {
		return renderedDeleteMineral;
	}

	public void setRenderedDeleteMineral(Boolean renderedDeleteMineral) {
		this.renderedDeleteMineral = renderedDeleteMineral;
	}

	public Boolean getRenderedPaginatorMineral() {
		return renderedPaginatorMineral;
	}

	public void setRenderedPaginatorMineral(Boolean renderedPaginatorMineral) {
		this.renderedPaginatorMineral = renderedPaginatorMineral;
	}

	public Boolean getRenderedDeleteAutoAmbiental() {
		return renderedDeleteAutoAmbiental;
	}

	public void setRenderedDeleteAutoAmbiental(
			Boolean renderedDeleteAutoAmbiental) {
		this.renderedDeleteAutoAmbiental = renderedDeleteAutoAmbiental;
	}

	public Boolean getRenderedPaginatorAutoAmbiental() {
		return renderedPaginatorAutoAmbiental;
	}

	public void setRenderedPaginatorAutoAmbiental(
			Boolean renderedPaginatorAutoAmbiental) {
		this.renderedPaginatorAutoAmbiental = renderedPaginatorAutoAmbiental;
	}

	public Boolean getRenderedDeleteGrupoEtnico() {
		return renderedDeleteGrupoEtnico;
	}

	public void setRenderedDeleteGrupoEtnico(Boolean renderedDeleteGrupoEtnico) {
		this.renderedDeleteGrupoEtnico = renderedDeleteGrupoEtnico;
	}

	public Boolean getRenderedPaginatorGrupoEtnico() {
		return renderedPaginatorGrupoEtnico;
	}

	public void setRenderedPaginatorGrupoEtnico(
			Boolean renderedPaginatorGrupoEtnico) {
		this.renderedPaginatorGrupoEtnico = renderedPaginatorGrupoEtnico;
	}

	public List<SelectItem> getBooleanoList() {
		return booleanoList;
	}

	public List<ReferenciaVO> getAutoAmbientales() {
		return autoAmbientales;
	}

	public List<ReferenciaVO> getGrupoEtnicos() {
		return grupoEtnicos;
	}

	public List<ReferenciaVO> getMinerales() {
		return minerales;
	}

	public String getMetodologia() {
		return metodologia;
	}

	public void setMetodologia(String metodologia) {
		this.metodologia = metodologia;
	}

	public String getAnexoMetodologia() {
		return anexoMetodologia;
	}

	public void setAnexoMetodologia(String anexoMetodologia) {
		this.anexoMetodologia = anexoMetodologia;
	}

	public Boolean getRenderedDeleteDocumento() {
		return renderedDeleteDocumento;
	}

	public void setRenderedDeleteDocumento(Boolean renderedDeleteDocumento) {
		this.renderedDeleteDocumento = renderedDeleteDocumento;
	}

	public Boolean getRenderedDeleteCoordenada() {
		return renderedDeleteCoordenada;
	}

	public void setRenderedDeleteCoordenada(Boolean renderedDeleteCoordenada) {
		this.renderedDeleteCoordenada = renderedDeleteCoordenada;
	}

	public Boolean getRenderedPaginatorCoordenada() {
		return renderedPaginatorCoordenada;
	}

	public void setRenderedPaginatorCoordenada(
			Boolean renderedPaginatorCoordenada) {
		this.renderedPaginatorCoordenada = renderedPaginatorCoordenada;
	}

	public Boolean getTerminosReferencia() {
		return terminosReferencia;
	}

	public void setTerminosReferencia(Boolean terminosReferencia) {
		this.terminosReferencia = terminosReferencia;
	}

	public String getSelectedGroup() {
		return selectedGroup;
	}

	public void setSelectedGroup(String selectedGroup) {
		this.selectedGroup = selectedGroup;
	}

	public RecursosVO getRadSoliEstanual() {
		return radSoliEstanual;
	}

	public void setRadSoliEstanual(RecursosVO radSoliEstanual) {
		this.radSoliEstanual = radSoliEstanual;
	}

	public CoordenadaVO getCoordenada() {
		return coordenada;
	}

	public void setCoordenada(CoordenadaVO coordenada) {
		this.coordenada = coordenada;
	}

	public String getRepositorioDocumentosTmpPath() {
		return repositorioDocumentosTmpPath;
	}

	public Boolean getIsEmptyTableMinerales() {
		return minerales == dao.getEmptyTable();
	}

	public Boolean getIsEmptyTableAutoridades() {
		return autoAmbientales == dao.getEmptyTable();
	}

	public Boolean getIsEmptyTableCoordenadas() {
		return radCoordenadas == emptyTableCoords;
	}

	public Boolean getIsEmptyTableEtnicos() {
		return grupoEtnicos == dao.getEmptyTable();
	}

	public Boolean getIsIncompleteForm() {
		Boolean result = false;
		result |= minerales == dao.getEmptyTable();
		result |= autoAmbientales == dao.getEmptyTable();
		result |= autorPlano.getNombres() == null;
		if (autorPlano.getNombres() != null) {
			result |= autorPlano.getNombres().trim().isEmpty();
		}
		result |= autorPlano.getPrimerApellido() == null;
		if (autorPlano.getPrimerApellido() != null) {
			result |= autorPlano.getPrimerApellido().trim().isEmpty();
		}
		result |= autorPlano.getMatriculaProfesional() == null;
		if (autorPlano.getMatriculaProfesional() != null) {
			result |= autorPlano.getMatriculaProfesional().trim().isEmpty();
		}
		result |= autorPlano.getProfesion() == null;
		if (autorPlano.getProfesion() != null) {
			result |= autorPlano.getProfesion().trim().isEmpty();
		}
		if (("1").equals(seleccionGrupoEtnico)) {
			result |= grupoEtnicos == dao.getEmptyTable();
		}
		if (("1").equals(seleccionGrupoEtnico)) {
			result |= grupoEtnicos == dao.getEmptyTable();
		}
		if (("1").equals(seleccionMineriaRestringida)) {
			result |= mineriaRestringida == null;
			if (mineriaRestringida != null) {
				result |= (mineriaRestringida.length == 0);
			}
		}
		if (("1").equals(seleccionZonasMineras)) {
			result |= ubicacionArea == null;
		}
		result |= (ZERO.compareTo(radSoliEstanual.getRecursoPropioAno1().add(radSoliEstanual
				.getOtrasFuentesAno1()))) == 0;
		result |= (ZERO.compareTo(radSoliEstanual.getRecursoPropioAno2().add(radSoliEstanual
				.getOtrasFuentesAno2()))) == 0;
		result |= (ZERO.compareTo(radSoliEstanual.getRecursoPropioAno3().add(radSoliEstanual
				.getOtrasFuentesAno3()))) == 0;

		if (("1").equals(seleccionExplotacion)) {
			result |= radCoordenadas == emptyTableCoords;
			result |= metodologia == null;
			if( metodologia != null ){
				result |= metodologia.isEmpty();
			}
		}
		result |= !terminosReferencia;
		return result;
	}

	// ----------------------------------------------------------------
	// ACTION LISTENERS
	// ----------------------------------------------------------------
	public String deleteGrupoEtnicoActionListener() {
		ReferenciaVO toDelete = null;
		grupoEtnicoMap = new LinkedHashMap<Long, String>(
				dao.getGrupoEtnicoMap());
		for (ReferenciaVO r : grupoEtnicos) {
			if (r.getId().compareTo(codigoGrupoEtnico) == 0) {
				toDelete = r;
			} else {
				grupoEtnicoMap.remove(r.getId());
			}
		}
		grupoEtnicos.remove(toDelete);
		if (grupoEtnicos.isEmpty()) {
			renderedDeleteGrupoEtnico = false;
			grupoEtnicos = dao.getEmptyTable();
		}
		if (grupoEtnicos.size() <= ConfigSingleton.PAGINATOR_LOW_LIMIT) {
			renderedPaginatorGrupoEtnico = false;
		}
		return null;
	}

	public void addGrupoEtnicoActionListener(ActionEvent event) {
		if (codigoGrupoEtnico != null) {
			if (grupoEtnicos == dao.getEmptyTable()) {
				grupoEtnicos = new ArrayList<ReferenciaVO>();
			}
			String label = grupoEtnicoMap.get(codigoGrupoEtnico);
			ReferenciaVO m = new ReferenciaVO();
			m.setId(codigoGrupoEtnico);
			m.setNombre(label);
			grupoEtnicos.add(m);
			grupoEtnicoMap.remove(codigoGrupoEtnico);
			renderedDeleteGrupoEtnico = true;
			if (grupoEtnicos.size() > ConfigSingleton.PAGINATOR_LOW_LIMIT) {
				renderedPaginatorGrupoEtnico = true;
			}

		}
	}

	public String deleteAutoAmbientalActionListener() {
		ReferenciaVO toDelete = null;
		autoridadAmbientalMap = new LinkedHashMap<Long, String>(
				dao.getAutoridadAmbientalMap());
		for (ReferenciaVO r : autoAmbientales) {
			if (r.getId().compareTo(codigoAutoAmbiental) == 0) {
				toDelete = r;
			} else {
				autoridadAmbientalMap.remove(r.getId());
			}
		}
		autoAmbientales.remove(toDelete);
		if (autoAmbientales.isEmpty()) {
			renderedDeleteAutoAmbiental = false;
			autoAmbientales = dao.getEmptyTable();
		}
		if (autoAmbientales.size() <= ConfigSingleton.PAGINATOR_LOW_LIMIT) {
			renderedPaginatorAutoAmbiental = false;
		}
		return null;
	}

	public void addAutoAmbientalActionListener(ActionEvent event) {
		if (codigoAutoAmbiental != null) {
			if (autoAmbientales == dao.getEmptyTable()) {
				autoAmbientales = new ArrayList<ReferenciaVO>();
			}
			String label = autoridadAmbientalMap.get(codigoAutoAmbiental);
			ReferenciaVO m = new ReferenciaVO();
			m.setId(codigoAutoAmbiental);
			m.setNombre(label);
			autoAmbientales.add(m);
			autoridadAmbientalMap.remove(codigoAutoAmbiental);
			renderedDeleteAutoAmbiental = true;
			if (autoAmbientales.size() > ConfigSingleton.PAGINATOR_LOW_LIMIT) {
				renderedPaginatorAutoAmbiental = true;
			}

		}
	}

	public String deleteMineralActionListener() {
		ReferenciaVO toDelete = null;
		mineralesMap = new LinkedHashMap<Long, String>(dao.getMineralesMap());
		for (ReferenciaVO r : minerales) {
			if (r.getId().compareTo(codigoMineral) == 0) {
				toDelete = r;
			} else {
				mineralesMap.remove(r.getId());
			}
		}
		minerales.remove(toDelete);
		if (minerales.isEmpty()) {
			renderedDeleteMineral = false;
			minerales = dao.getEmptyTable();
		}
		if (minerales.size() <= ConfigSingleton.PAGINATOR_LOW_LIMIT) {
			renderedPaginatorMineral = false;
		}
		return null;
	}

	public void addMineralActionListener(ActionEvent event) {
		if (codigoMineral != null) {
			if (minerales == dao.getEmptyTable()) {
				minerales = new ArrayList<ReferenciaVO>();
			}
			String labelMineral = mineralesMap.get(codigoMineral);
			ReferenciaVO m = new ReferenciaVO();
			m.setId(codigoMineral);
			m.setNombre(labelMineral);
			minerales.add(m);
			mineralesMap.remove(codigoMineral);
			renderedDeleteMineral = true;
			if (minerales.size() > ConfigSingleton.PAGINATOR_LOW_LIMIT) {
				renderedPaginatorMineral = true;
			}
		}
	}

	public String deleteCoordenadaActionListener() {
		radCoordenadas.remove(coordenada);
		if (radCoordenadas.isEmpty()) {
			renderedDeleteCoordenada = false;
			radCoordenadas = emptyTableCoords;
		}
		if (radCoordenadas.size() <= ConfigSingleton.PAGINATOR_LOW_LIMIT) {
			renderedPaginatorCoordenada = false;
		}
		return null;

	}

	public String addCoordenada() {
		CoordenadaVO c = new CoordenadaVO();
		c.setCoordenadaX(coordenada.getCoordenadaX());
		c.setCoordenadaY(coordenada.getCoordenadaY());
		if (radCoordenadas == emptyTableCoords) {
			radCoordenadas = new ArrayList<CoordenadaVO>();
		}
		radCoordenadas.add(c);
		if (radCoordenadas.size() > ConfigSingleton.PAGINATOR_LOW_LIMIT) {
			renderedPaginatorCoordenada = true;
		}
		renderedDeleteCoordenada = true;
		return null;
	}

	public String cancel() {
		codigoMineral = null;
		codigoAutoAmbiental = null;
		seleccionExplotacion = "2";
		seleccionGrupoEtnico = "2";
		seleccionZonasMineras = "2";
		seleccionMineriaRestringida = "2";
		ubicacionArea = null;
		codigoGrupoEtnico = null;
		metodologia = null;
		anexoMetodologia = null;
		readOnlyMineral = false;
		renderedDeleteMineral = false;
		renderedPaginatorMineral = false;
		renderedDeleteAutoAmbiental = false;
		renderedPaginatorAutoAmbiental = false;
		renderedDeleteGrupoEtnico = false;
		renderedDeleteDocumento = false;
		renderedDeleteCoordenada = false;
		renderedPaginatorCoordenada = false;
		terminosReferencia = false;
		selectedGroup = "groupOne";
		renderedPaginatorGrupoEtnico = false;
		radSoliEstanual = new RecursosVO();
		coordenada = new CoordenadaVO();
		autorPlano = new AutorPlanoVO();
		mineralesMap = new LinkedHashMap<Long, String>(dao.getMineralesMap());
		autoridadAmbientalMap = new LinkedHashMap<Long, String>(
				dao.getAutoridadAmbientalMap());
		grupoEtnicoMap = new LinkedHashMap<Long, String>(
				dao.getGrupoEtnicoMap());
		minerales = dao.getEmptyTable();
		autoAmbientales = dao.getEmptyTable();
		grupoEtnicos = dao.getEmptyTable();
		radCoordenadas = emptyTableCoords;
		return null;
	}

	public void deleteFileMetodogia(ActionEvent event) {
	}

	public void uploadFileCoordenada(FileEntryEvent e) {
		FileEntry fe = (FileEntry) e.getComponent();
		FileEntryResults results = fe.getResults();
		FileEntryResults.FileInfo fileInfo = results.getFiles().get(0);
		File file = fileInfo.getFile();
		BufferedReader br = null;
		radCoordenadas = new ArrayList<CoordenadaVO>();
		try {
			String sCurrentLine;
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					file)));
			while ((sCurrentLine = br.readLine()) != null) {
				sCurrentLine = sCurrentLine.substring(0,
						sCurrentLine.length() - 1);
				String coords[] = sCurrentLine.split(":");
				CoordenadaVO c = new CoordenadaVO();
				c.setCoordenadaX(Long.valueOf(coords[0]));
				c.setCoordenadaY(Long.valueOf(coords[1]));
				radCoordenadas.add(c);
			}
			if (radCoordenadas.size() > ConfigSingleton.PAGINATOR_LOW_LIMIT) {
				renderedPaginatorCoordenada = true;
			}
			renderedDeleteCoordenada = true;
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public void uploadFileMetodologia(FileEntryEvent e) {
	}

	public void onChangeMineriaRestringida(ValueChangeEvent e) {
	}

	public void onSourceTypeValueChange(ValueChangeEvent e) {
	}

}
