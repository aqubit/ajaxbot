package com.ccx.radicador.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Workflow {

	private Long timeout;
	private Long polling;
	private String url;
	private List<Scenario> scenarios;
	private String strCoordsFileName;
	private String strAjaxcondition;
	private String strIcefacesscript;

	public Workflow() {
	}

	public void setCoordsFileName(String fileName) {
		this.strCoordsFileName = fileName;
	}
	
	public String getCoordsFileName() {
		return strCoordsFileName;
	}

	@XmlElement
	public Long getTimeout() {
		return timeout;
	}

	public void setTimeout(Long timeout) {
		this.timeout = timeout;
	}

	@XmlElement
	public Long getPolling() {
		return polling;
	}

	public void setPolling(Long polling) {
		this.polling = polling;
	}

	@XmlElement
	public String getUrl() {
		return url;
	}

	@XmlElement
	public String getAjaxcondition() {
		return strAjaxcondition;
	}

	public void setAjaxcondition(String ajaxcondition) {
		this.strAjaxcondition = ajaxcondition;
	}
	
	@XmlElement
	public String getIcefacesscript() {
		return strIcefacesscript;
	}

	public void setIcefacesscript(String icefacesscript) {
		this.strIcefacesscript = icefacesscript;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@XmlElementWrapper(name = "scenarios")
	@XmlElement(name = "scenario")
	public List<Scenario> getScenarios() {
		return scenarios;
	}

	public void setScenarios(List<Scenario> scenarios) {
		this.scenarios = scenarios;
	}
	
	@Override
	public String toString() {
		StringBuffer sbr = new StringBuffer();
		sbr.append("Url: ");
		sbr.append(url);
		sbr.append(" | ");
		sbr.append("Timeout: ");
		sbr.append(timeout);
		sbr.append(" | ");
		sbr.append("Polling: ");
		sbr.append(polling);
		sbr.append("\n");
		sbr.append("Coords file: ");
		sbr.append(strCoordsFileName);
		sbr.append("\n");
		sbr.append("Ajax condition: ");
		sbr.append(strAjaxcondition);
		sbr.append("\n");
		sbr.append("Icefaces script: ");
		sbr.append(strIcefacesscript);
		sbr.append("\n");
		sbr.append("\n");
		for (Scenario s : scenarios) {
			sbr.append(s.toString());
		}
		return sbr.toString();
	}
}
