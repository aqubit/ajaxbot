package com.ccx.ingeominasapp.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
import com.ccx.ingeominasapp.vo.CoordenadaVO;
import com.ccx.ingeominasapp.vo.DepartamentoVO;
import com.ccx.ingeominasapp.vo.LocalizacionVO;
import com.ccx.ingeominasapp.vo.MunicipioVO;

@Stateful
@SessionScoped
@Named("registrarLocalizacion")
public class RegistrarLocalizacion {

	@Inject
	private ReferenceDAO dao;
	@Inject
	MessageSingleton message;
	private Boolean poligonoVisualizado = false;
	private Boolean viewerRendered = false;
	private Boolean renderedPaginatorCoordenada = false;
	private Boolean selectedGroup = true;
	private Boolean renderedPaginatorDepMunicipio = false;
	private Boolean renderedDeleteDepMunicipio = false;
	private Boolean renderedDeleteCoordenada = false;
	private Boolean renderedEditCoordenada = false;
	private Boolean showEditCoordenada = false;
	private Boolean showAcceptCoordenada = false;
	private Map<Long, List<Long>> municipiosAddedMap = new LinkedHashMap<Long, List<Long>>();
	private Map<Long, String> municipioMap = null;
	private List<DepartamentoVO> deparMunTableList = null;
	private List<DepartamentoVO> emptyTableDeptos = new ArrayList<DepartamentoVO>();
	private List<CoordenadaVO> emptyTableCoords = new ArrayList<CoordenadaVO>();
	private List<CoordenadaVO> radCoordenadas = null;
	private LocalizacionVO localizacion = new LocalizacionVO();
	private CoordenadaVO coordenada = new CoordenadaVO();
	private CoordenadaVO coordenadaBackup = new CoordenadaVO();
	private String repositorioDocumentosTmpPath = "";
	private String booleanLocalizacion = "0";
	private Long deptoId = null;
	private Long codigoMunicipio = null;
	private Long municipioToDeleteId = null;
	List<SelectItem> booleanoListGroup = new ArrayList<SelectItem>();

	public RegistrarLocalizacion() {

	}

	@PostConstruct
	public void init() {
		SelectItem item = new SelectItem();
		item.setValue("0");
		item.setLabel("Manual");
		booleanoListGroup.add(item);
		item = new SelectItem();
		item.setValue("1");
		item.setLabel("Con Archivo");
		booleanoListGroup.add(item);
		for (int i = 0; i < ConfigSingleton.NUM_EMPTY_LINES; i++) {
			emptyTableDeptos.add(new DepartamentoVO());
			emptyTableCoords.add(new CoordenadaVO());
		}
		deparMunTableList = emptyTableDeptos;
		radCoordenadas = emptyTableCoords;

	}

	public Boolean getPoligonoVisualizado() {
		return poligonoVisualizado;
	}

	public void setPoligonoVisualizado(Boolean poligonoVisualizado) {
		this.poligonoVisualizado = poligonoVisualizado;
	}

	public Long getDeptoId() {
		return deptoId;
	}

	public void setDeptoId(Long deptoId) {
		this.deptoId = deptoId;
	}

	public Boolean getShowAcceptCoordenada() {
		return showAcceptCoordenada;
	}

	public void setShowAcceptCoordenada(Boolean showAcceptCoordenada) {
		this.showAcceptCoordenada = showAcceptCoordenada;
	}

	public Boolean getRenderedEditCoordenada() {
		return renderedEditCoordenada;
	}

	public void setRenderedEditCoordenada(Boolean renderedEditCoordenada) {
		this.renderedEditCoordenada = renderedEditCoordenada;
	}

	public Boolean getRenderedDeleteCoordenada() {
		return renderedDeleteCoordenada;
	}

	public void setRenderedDeleteCoordenada(Boolean renderedDeleteCoordenada) {
		this.renderedDeleteCoordenada = renderedDeleteCoordenada;
	}

	public Boolean getShowEditCoordenada() {
		return showEditCoordenada;
	}

	public void setShowEditCoordenada(Boolean showEditCoordenada) {
		this.showEditCoordenada = showEditCoordenada;
	}

	public Long getMunicipioToDeleteId() {
		return municipioToDeleteId;
	}

	public void setMunicipioToDeleteId(Long municipioToDeleteId) {
		this.municipioToDeleteId = municipioToDeleteId;
	}

	public Boolean getRenderedDeleteDepMunicipio() {
		return renderedDeleteDepMunicipio;
	}

	public void setRenderedDeleteDepMunicipio(Boolean renderedDeleteDepMunicipio) {
		this.renderedDeleteDepMunicipio = renderedDeleteDepMunicipio;
	}

	public Long getCodigoMunicipio() {
		return codigoMunicipio;
	}

	public void setCodigoMunicipio(Long codigoMunicipio) {
		this.codigoMunicipio = codigoMunicipio;
	}

	public String getBooleanLocalizacion() {
		return booleanLocalizacion;
	}

	public void setBooleanLocalizacion(String booleanLocalizacion) {
		this.booleanLocalizacion = booleanLocalizacion;
	}

	public List<SelectItem> getBooleanoListGroup() {
		return booleanoListGroup;
	}

	public void setBooleanoListGroup(List<SelectItem> booleanoListGroup) {
		this.booleanoListGroup = booleanoListGroup;
	}

	public String getRepositorioDocumentosTmpPath() {
		return repositorioDocumentosTmpPath;
	}

	public void setRepositorioDocumentosTmpPath(
			String repositorioDocumentosTmpPath) {
		this.repositorioDocumentosTmpPath = repositorioDocumentosTmpPath;
	}

	public Boolean getRenderedPaginatorCoordenada() {
		return renderedPaginatorCoordenada;
	}

	public void setRenderedPaginatorCoordenada(
			Boolean renderedPaginatorCoordenada) {
		this.renderedPaginatorCoordenada = renderedPaginatorCoordenada;
	}

	public List<CoordenadaVO> getRadCoordenadas() {
		return radCoordenadas;
	}

	public void setRadCoordenadas(List<CoordenadaVO> radCoordenadas) {
		this.radCoordenadas = radCoordenadas;
	}

	public CoordenadaVO getCoordenada() {
		return coordenada;
	}

	public void setCoordenada(CoordenadaVO coordenada) {
		this.coordenada = coordenada;
	}

	public Boolean getSelectedGroup() {
		return selectedGroup;
	}

	public void setSelectedGroup(Boolean selectedGroup) {
		this.selectedGroup = selectedGroup;
	}

	public Boolean getRenderedPaginatorDepMunicipio() {
		return renderedPaginatorDepMunicipio;
	}

	public void setRenderedPaginatorDepMunicipio(
			Boolean renderedPaginatorDepMunicipio) {
		this.renderedPaginatorDepMunicipio = renderedPaginatorDepMunicipio;
	}

	public Boolean getViewerRendered() {
		return viewerRendered;
	}

	public void setViewerRendered(Boolean viewerRendered) {
		this.viewerRendered = viewerRendered;
	}

	public List<DepartamentoVO> getDeparMunTableList() {
		return deparMunTableList;
	}

	public void setDeparMunTableList(List<DepartamentoVO> deparMunTableList) {
		this.deparMunTableList = deparMunTableList;
	}

	public LocalizacionVO getLocalizacion() {
		return localizacion;
	}

	public void setLocalizacion(LocalizacionVO localizacion) {
		this.localizacion = localizacion;
	}

	public Boolean getIsEmptyTableCoordenadas() {
		Boolean result = false;
		result = radCoordenadas == emptyTableCoords;
		result |= (radCoordenadas.size() < 4);
		return result;
	}

	public Boolean getIsIncompleteForm() {
		Boolean result = false;
		result |= deparMunTableList == emptyTableDeptos;
		result |= !poligonoVisualizado ;
		return result;
	}

	public Boolean getIsEmptyTableMunicipios() {
		return deparMunTableList == emptyTableDeptos;
	}

	// ----------------------------------------------------------------
	// ACTION LISTENERS
	// ----------------------------------------------------------------

	public String editCoordenadaAction() {
		coordenadaBackup.setCoordenadaX(coordenada.getCoordenadaX());
		coordenadaBackup.setCoordenadaY(coordenada.getCoordenadaY());
		showAcceptCoordenada = true;
		showEditCoordenada = true;
		return null;
	}

	public void cancelCoordenadaAction(ActionEvent event) {
		coordenada.setCoordenadaX(coordenadaBackup.getCoordenadaX());
		coordenada.setCoordenadaY(coordenadaBackup.getCoordenadaY());
		showAcceptCoordenada = false;
		showEditCoordenada = false;
	}

	public void acceptCoordenadaAction(ActionEvent event) {
		showAcceptCoordenada = false;
		showEditCoordenada = false;
	}

	public String deleteCoordenadaActionListener() {
		radCoordenadas.remove(coordenada);
		if (radCoordenadas.isEmpty()) {
			renderedDeleteCoordenada = false;
			renderedEditCoordenada = false;
			showAcceptCoordenada = false;
			showEditCoordenada = false;
			radCoordenadas = emptyTableCoords;
		}
		if (radCoordenadas.size() <= ConfigSingleton.PAGINATOR_LOW_LIMIT) {
			renderedPaginatorCoordenada = false;
		}
		return null;
	}

	public void addCoordenada(ActionEvent event) {
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
		renderedEditCoordenada = true;
	}

	public void addSelectedDptoMunOntable(ActionEvent event) {
		if (codigoMunicipio != null) {
			if (deparMunTableList == emptyTableDeptos) {
				deparMunTableList = new ArrayList<DepartamentoVO>();
			}
			String nombreDpto = dao.getDepartamentoMap().get(deptoId);
			String nombreMunicipio = municipioMap.get(codigoMunicipio);
			DepartamentoVO dpto = new DepartamentoVO();
			dpto.setId(deptoId);
			dpto.setNombre(nombreDpto);
			MunicipioVO m = new MunicipioVO();
			m.setId(codigoMunicipio);
			m.setId_departamento(deptoId);
			m.setNombre(nombreMunicipio);
			dpto.getRadMunicipios().add(m);
			deparMunTableList.add(dpto);
			municipioMap.remove(codigoMunicipio);
			List<Long> municipiosAlreadyAdded = municipiosAddedMap.get(deptoId);
			if (municipiosAlreadyAdded == null) {
				municipiosAlreadyAdded = new ArrayList<Long>();
				municipiosAddedMap.put(deptoId, municipiosAlreadyAdded);
			}
			municipiosAlreadyAdded.add(codigoMunicipio);
			if (deparMunTableList.size() > ConfigSingleton.PAGINATOR_LOW_LIMIT) {
				renderedPaginatorDepMunicipio = true;
			}
			renderedDeleteDepMunicipio = true;
		}
	}

	public String removeDptoMunTable() {
		DepartamentoVO dptoToDelete = null;
		for (DepartamentoVO d : deparMunTableList) {
			if (d.getRadMunicipios().get(0).getId()
					.compareTo(municipioToDeleteId) == 0) {
				dptoToDelete = d;
				break;
			}
		}
		deparMunTableList.remove(dptoToDelete);
		MunicipioVO m = dptoToDelete.getRadMunicipios().get(0);
		List<Long> municipiosAlreadyAdded = municipiosAddedMap.get(dptoToDelete
				.getId());
		municipiosAlreadyAdded.remove(m.getId());
		municipioMap = new LinkedHashMap<Long, String>(
				dao.getMunicipioMap(deptoId));
		for (Long key : municipiosAlreadyAdded) {
			municipioMap.remove(key);
		}
		if (deparMunTableList.isEmpty()) {
			renderedDeleteDepMunicipio = false;
			deparMunTableList = emptyTableDeptos;
		}
		if (deparMunTableList.size() <= ConfigSingleton.PAGINATOR_LOW_LIMIT) {
			renderedPaginatorDepMunicipio = false;
		}
		return null;
	}

	public void changeDepartamento(ValueChangeEvent e) {
		try {
			deptoId = (Long) e.getNewValue();
			municipioMap = new LinkedHashMap<Long, String>(
					dao.getMunicipioMap(deptoId));
			List<Long> alreadyAdded = municipiosAddedMap.get(deptoId);
			if (alreadyAdded != null) {
				for (Long key : alreadyAdded) {
					municipioMap.remove(key);
				}
			}

		} catch (NumberFormatException ex) {
			// do nothing - empty selection
		}
	}

	public void onSourceTypeValueChange(ValueChangeEvent e) {
		selectedGroup = !selectedGroup;
		poligonoVisualizado = false;
		radCoordenadas = emptyTableCoords;
		renderedPaginatorCoordenada = false;
	}

	public Map<Long, String> getDepartamentoMap() {
		return dao.getDepartamentoMap();
	}

	public Map<Long, String> getMunicipioMap() {
		return municipioMap;
	}

	public Map<Long, String> getPlanchaIGACMap() {
		return dao.getPlanchaIGACMap();
	}

	public void uploadFileActionListener(FileEntryEvent e) {
		FileEntry fe = (FileEntry) e.getComponent();
		FileEntryResults results = fe.getResults();
		FileEntryResults.FileInfo fileInfo = results.getFiles().get(0);
		File file = fileInfo.getFile();
		BufferedReader br = null;
		poligonoVisualizado = false;
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
			renderedEditCoordenada = true;
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

	public void setViewerRendered(ActionEvent event) {
	}

	public String cancel() {
		deptoId = null;
		codigoMunicipio = null;
		renderedPaginatorCoordenada = false;
		selectedGroup = true;
		renderedPaginatorDepMunicipio = false;
		renderedDeleteDepMunicipio = false;
		renderedDeleteCoordenada = false;
		renderedEditCoordenada = false;
		showEditCoordenada = false;
		showAcceptCoordenada = false;
		municipiosAddedMap.clear();
		deparMunTableList = emptyTableDeptos;
		radCoordenadas = emptyTableCoords;
		localizacion = new LocalizacionVO();
		coordenada = new CoordenadaVO();
		booleanLocalizacion = "0";
		poligonoVisualizado = false;
		return null;
	}

	public void viewerRendered(ActionEvent event) {
	}

	public void showMap(ActionEvent event) {
		poligonoVisualizado = true;
		viewerRendered = true;
	}

	public void hideMap(ActionEvent event) {
		viewerRendered = false;
	}

	public void upCoordenada(ActionEvent event) {
	}

	public void downCoordenada(ActionEvent event) {
	}

	public void planchaIGACChange(ValueChangeEvent e) {
	}

}
