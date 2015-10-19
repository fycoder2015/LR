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
@Table(name = "lr_years")
public class Years extends IdEntity {
	
	private String year;
	@JsonIgnore  //不在json的返回值中显示
	private String sts;


	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	
}
