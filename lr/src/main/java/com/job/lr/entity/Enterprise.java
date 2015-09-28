package com.job.lr.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "lr_enterprise")
public class Enterprise extends IdEntity {

	private String entName;
	
	private String entManager;
	
	private String entAddress;
	
	private String phoneCall;
	
	private Date regDate;

	public String getEntName() {
		return entName;
	}

	public void setEntName(String entName) {
		this.entName = entName;
	}

	public String getEntManager() {
		return entManager;
	}

	public void setEntManager(String entManager) {
		this.entManager = entManager;
	}

	public String getEntAddress() {
		return entAddress;
	}

	public void setEntAddress(String entAddress) {
		this.entAddress = entAddress;
	}

	public String getPhoneCall() {
		return phoneCall;
	}

	public void setPhoneCall(String phoneCall) {
		this.phoneCall = phoneCall;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}	
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
