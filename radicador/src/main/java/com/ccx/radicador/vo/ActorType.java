package com.ccx.radicador.vo;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "type")
@XmlEnum
public enum ActorType {
	@XmlEnumValue("file")
	FILE("file"), 
	@XmlEnumValue("text")
	TEXT("text"), 
	@XmlEnumValue("select")
	SELECT("select"), 
	@XmlEnumValue("radio")
	RADIO("radio"), 
	@XmlEnumValue("checkbox")
	CHECKBOX("checkbox"), 
	@XmlEnumValue("button")
	BUTTON("button"), 
	@XmlEnumValue("select2")
	SELECT2("select2"), 
	@XmlEnumValue("link")
	LINK("link");

	private final String value;

	ActorType(String v) {
		value = v;
	}

    public String value() {
        return value;
    }
 
	public static ActorType fromValue(String v) {
        for (ActorType c: ActorType.values()) {  
            if (c.value.equals(v)) {  
                return c;  
            }  
        }  
        throw new IllegalArgumentException(v);  
	}
}