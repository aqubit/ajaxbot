package com.ccx.radicador.svc;

import java.io.File;
import java.io.FileReader;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import au.com.bytecode.opencsv.CSVReader;

import com.ccx.radicador.vo.Workflow;

public class FileDataSource extends DataSource {

	@Override
	public void init(String key, String verifCode, JFrame frmRadicador)
			throws Exception {
		String configFile = System.getProperty("ccx.config");
		if (configFile == null) {
			configFile = "config.xml";
		}
		String dataFile = System.getProperty("ccx.data");
		if (dataFile == null) {
			dataFile = "data.csv";
		}
		// Read data file
		CSVReader reader = new CSVReader(new FileReader(dataFile));
		List<String[]> rows = reader.readAll();
		String[] row = null;
		for (String[] s : rows) {
			if (s[0].equals(key)) {
				row = s;
				break;
			}
		}
		reader.close();
		if (row == null) {
			JOptionPane.showMessageDialog(null,
					"La llave no existe. Intente de nuevo", "Radicador EBX",
					JOptionPane.ERROR_MESSAGE);
			throw new Exception("La llave no existe");
		}
		// Read config file
		File file = new File(configFile);
		JAXBContext jaxbContext = JAXBContext.newInstance(Workflow.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		play = (Workflow) jaxbUnmarshaller.unmarshal(file);
		loadScenarios(row, key, play.getScenarios());
		System.out.println(play);
	}

}