package com.job.lr.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

//专业
@Entity
@Table(name = "lr_subject")
public class Subject extends IdEntity {
	
	private String subject;
	@JsonIgnore  //不在json的返回值中显示
	private String sts;


	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}



	
	
	
}
