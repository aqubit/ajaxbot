package com.ccx.radicador.svc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;

import com.ccx.radicador.vo.Actor;
import com.ccx.radicador.vo.ActorType;
import com.ccx.radicador.vo.Scenario;
import com.ccx.radicador.vo.Workflow;

public abstract class DataSource {

	protected Workflow play = null;
	protected static String SEPARATOR = "/";
	protected String fileNameSolicitud = null;
	protected String fileNameLocalizacion = null;

	public abstract void init(String key, String verifCode, JFrame frmRadicador)
			throws Exception;

	public void newPIN(JFrame frmRadicador) {
	}

	public String getFileNameSolicitud() {
		return fileNameSolicitud;
	}

	public String getFileNameLocalizacion() {
		return fileNameLocalizacion;
	}

	public Long getPollingTime() {
		return play.getPolling();
	}

	public Long getTimeout() {
		return play.getTimeout();
	}

	public String getSiteURL() {
		return play.getUrl();
	}

	public Workflow getWorkFlow() {
		return play;
	}

	public List<Scenario> getScenarios() {
		return play.getScenarios();
	}

	public Scenario getScenario(String radicacionId, String scenarioId) {
		for (Scenario s : play.getScenarios()) {
			if (s.getId().equals(scenarioId)) {
				return s;
			}
		}
		return null;
	}

	protected String createCoordsFile(String input) {
		String fileName = null;
		try {
			File temp = File.createTempFile("ccx", ".txt");
			temp.deleteOnExit();
			BufferedWriter out = new BufferedWriter(new FileWriter(temp));
			String[] arrCoords = input.split(SEPARATOR);
			for (String s : arrCoords) {
				out.write(s);
				out.write("\n");
			}
			out.close();
			fileName = temp.getAbsolutePath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileName;
	}

	protected void loadScenarios(String[] data, String key,
			List<Scenario> scenarios) {
		for (Scenario scenario : play.getScenarios()) {
			List<Actor> finalList = new ArrayList<Actor>();
			scenario.setRadicacionId(key);
			Iterator<Actor> it = scenario.getActores().iterator();
			while (it.hasNext()) {
				Actor actor = it.next();
				Integer columna = actor.getColumn();
				// SELECT2
				if (actor.getType() == ActorType.SELECT2) {
					Actor nextSelect = it.next();
					Actor nextButton = it.next();
					Integer columnaDetail = nextSelect.getColumn();
					String rawMaster = data[columna];
					String rawDetail = data[columnaDetail];
					String[] arrMaster = rawMaster.split(SEPARATOR);
					String[] arrDetail = rawDetail.split(SEPARATOR);
					int index = 0;
					for (String master : arrMaster) {
						Actor newActorMaster = actor.clonar();
						Actor newActorDetail = nextSelect.clonar();
						newActorMaster.setData(master);
						newActorDetail.setData(arrDetail[index++]);
						newActorMaster.setType(ActorType.SELECT);
						finalList.add(newActorMaster);
						finalList.add(newActorDetail);
						finalList.add(nextButton);
					}
				}// FILE
				else if (actor.getType() == ActorType.FILE) {
					Actor next = it.next();
					String dato = data[columna];
					if (dato != null && !dato.isEmpty()) {
						String fileName = createCoordsFile(dato);
						actor.setData(fileName);
						finalList.add(actor);
						finalList.add(next);
						if(actor.getName().contains("Solicitud")){
							fileNameSolicitud = fileName;
						}else{
							fileNameLocalizacion = fileName;
						}
					}
				}// SELECT
				else if (actor.getType() == ActorType.SELECT) {
					if (columna != -1) {
						String dato = data[columna];
						// Single value without "Add" button
						if (actor.getMultiple() != null && actor.getMultiple()) {
							Actor next = it.next();
							if (dato != null && !dato.isEmpty()) {
								if (dato.contains(SEPARATOR)) {
									String[] datos = dato.split(SEPARATOR);
									for (String s : datos) {
										Actor newActor = actor.clonar();
										newActor.setData(s);
										finalList.add(newActor);
										finalList.add(next);
									}
								} else {
									actor.setData(data[columna]);
									finalList.add(actor);
									finalList.add(next);
								}
							}
							// Single value
						} else {
							if (dato != null && !dato.isEmpty()) {
								actor.setData(dato);
								finalList.add(actor);
							}
						}
					}// Default data in config file
					else {
						finalList.add(actor);
					}
					// RADIO
				} else if (actor.getType() == ActorType.RADIO) {
					String dato = data[columna];
					if (dato != null && !dato.isEmpty()) {
						actor.setXpath(actor.getXpath2());
						finalList.add(actor);
					}
					// CHECKBOX
				} else if (actor.getType() == ActorType.CHECKBOX) {
					if (columna != null && columna != -1) {
						String dato = data[columna];
						if (dato != null && !dato.isEmpty()) {
							finalList.add(actor);
						}
					} else {
						finalList.add(actor);
					}
					// Otros
				} else {
					if (columna != null && columna != -1) {
						String dato = data[columna];
						if (dato != null && !dato.isEmpty()) {
							actor.setData(data[columna]);
							finalList.add(actor);
						}
					} else {
						finalList.add(actor);
					}
				}
			}
			scenario.setActores(finalList);
			scenario.init(play.getIcefacesscript(), play.getAjaxcondition());
		}
	}
}
