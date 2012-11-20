package com.ccx.radicador.vo;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Actor {
	
	private String xpath;
	private String xpath2;
	private ActorType type;
	private String name;
	private String data;
	private Boolean doesSubmit;
	private Boolean multiple;
	private Integer column;

	public Actor() {
		doesSubmit = false;
	}

	
	@XmlElement
	public Boolean getMultiple() {
		return multiple;
	}

	public void setMultiple(Boolean multiple) {
		this.multiple = multiple;
	}


	@XmlElement
	public String getXpath() {
		return xpath;
	}

	public void setXpath(String xpath) {
		this.xpath = xpath;
	}
	
	@XmlElement
	public String getXpath2() {
		return xpath2;
	}

	public void setXpath2(String xpath2) {
		this.xpath2 = xpath2;
	}
	
	@XmlElement	
	public Integer getColumn() {
		return column;
	}

	public void setColumn(Integer column) {
		this.column = column;
	}

	@XmlAttribute
	public Boolean getDoesSubmit() {
		return doesSubmit;
	}
	
	public void setDoesSubmit(Boolean doesSubmit) {
		this.doesSubmit = doesSubmit;
	}


	
	@XmlElement
	public ActorType getType() {
		return type;
	}

	public void setType(ActorType type) {
		this.type = type;
	}

	@XmlElement
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Actor clonar(){
		Actor actor = new Actor();
		actor.column = this.column;
		actor.data = this.data;
		actor.doesSubmit = this.doesSubmit;
		actor.name = this.name;
		actor.type = this.type;
		actor.xpath = this.xpath;
		actor.xpath2 = this.xpath2;
		return actor;
	}

	@Override
	public String toString() {
		StringBuffer sbr = new StringBuffer();
		sbr.append("xpath: ");
		sbr.append(xpath);
		sbr.append(" | ");
		sbr.append("Name: ");
		sbr.append(name);
		sbr.append(" | ");
		sbr.append("Type: ");
		sbr.append(type);
		sbr.append(" | ");
		sbr.append("Data: ");
		sbr.append(data);
		sbr.append(" | ");
		sbr.append("Ajax submit: ");
		sbr.append(doesSubmit);
		return sbr.toString();
	}

}
