package com.ccx.radicador.admin.vo;

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Cacheable(false)
@Table(name = "PIN")
public class PinVO {
	@Id
	@Column(name = "PIN")
	private String PIN;
	@Column(name = "IS_CCX")
	private Boolean isCCX;
	@Column(name = "USED_BY")
	private String usedBy;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_TIME")
	private Date updatedTime;

	public String getPIN() {
		return PIN;
	}

	public void setPIN(String pIN) {
		PIN = pIN;
	}

	public Boolean getIsCCX() {
		return isCCX;
	}

	public void setIsCCX(Boolean isCCX) {
		this.isCCX = isCCX;
	}

	public String getUsedBy() {
		return usedBy;
	}

	public void setUsedBy(String usedBy) {
		this.usedBy = usedBy;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

}
