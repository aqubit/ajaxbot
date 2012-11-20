package com.ccx.radicador.svc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import com.ccx.radicador.vo.Actor;
import com.ccx.radicador.vo.Scenario;
import com.ccx.radicador.vo.Workflow;

public class HTTPDataSource extends DataSource {

	@Override
	public void init(String key, String verifCode, JFrame frmRadicador)
			throws Exception {
		// Cargar data
		String inputLine = null;
		String configHost = System.getenv("CCXSERVER");
		if (configHost == null) {
			JOptionPane.showMessageDialog(frmRadicador,
					"El servidor de configuración no fue definido",
					"Radicador EBX", JOptionPane.ERROR_MESSAGE);
			throw new Exception("No config server defined");
		}
		BufferedReader in = null;
		try {
			URL dataURL = new URL("http://" + configHost
					+ "/radicador-admin/data?key=" + key);
			in = new BufferedReader(new InputStreamReader(dataURL.openStream()));
			inputLine = in.readLine();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(frmRadicador,
					"No se pudo contactar el servicio de carga de datos",
					"Radicador EBX", JOptionPane.ERROR_MESSAGE);
			throw new Exception("Unreachable data servlet");
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if (inputLine.equals("-1")) {
			JOptionPane.showMessageDialog(frmRadicador,
					"La llave no existe. Intente de nuevo", "Radicador EBX",
					JOptionPane.ERROR_MESSAGE);
			throw new Exception("Llave no existe");
		}
		String[] row = inputLine.split(",");
		String rowCode = row[row.length - 1].toLowerCase().trim();
		if (!verifCode.toLowerCase().trim().equals(rowCode)) {
			JOptionPane
					.showMessageDialog(
							frmRadicador,
							"Código de verificación incorrecto. Por favor intente de nuevo",
							"Radicador EBX", JOptionPane.ERROR_MESSAGE);
			throw new Exception("Código de verificación incorrecto");
		}
		// Read config file
		JAXBContext jaxbContext = JAXBContext.newInstance(Workflow.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		try {
			play = (Workflow) jaxbUnmarshaller.unmarshal(new URL("http://"
					+ configHost + "/radicador-admin/config"));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"No se pudo contactar el servicio de configuración",
					"Radicador EBX", JOptionPane.ERROR_MESSAGE);
			throw new Exception("Unreachable config servlet");
		}
		loadScenarios(row, key, play.getScenarios());
		System.out.println(play);
	}

	@Override
	public void newPIN(JFrame frmRadicador) {
		Scenario loginScenario = play.getScenarios().get(0);
		String key = loginScenario.getRadicacionId();
		String newPIN = null;
		String configHost = System.getenv("CCXSERVER");
		if (configHost == null) {
			JOptionPane.showMessageDialog(frmRadicador,
					"El servidor de configuración no fue definido",
					"Radicador EBX", JOptionPane.ERROR_MESSAGE);
		}
		BufferedReader in = null;
		try {
			URL dataURL = new URL("http://" + configHost
					+ "/radicador-admin/pin?key=" + key);
			in = new BufferedReader(new InputStreamReader(dataURL.openStream()));
			newPIN = in.readLine().trim();
			if (newPIN.equals("-1")) {
				JOptionPane.showMessageDialog(frmRadicador,
						"No hay un PIN disponible. Contacte a Milena Varón.",
						"Radicador EBX", JOptionPane.ERROR_MESSAGE);
			} else {
				for (Actor a : loginScenario.getActores()) {
					if (a.getName().contains("PIN")) {
						a.setData(newPIN);
					}
				}
				JOptionPane
						.showMessageDialog(
								frmRadicador,
								"Nuevo PIN solicitado con éxito. Presione el botón 'Login' para continuar con el proceso de radicación.",
								"Radicador EBX",
								JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(frmRadicador,
					"No se pudo contactar el servicio de PIN", "Radicador EBX",
					JOptionPane.ERROR_MESSAGE);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}