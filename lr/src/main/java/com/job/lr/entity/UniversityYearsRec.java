package com.job.lr.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springside.modules.utils.Clock;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 大学和入学年份的关系表
 *  
 * University 和  Years 的对应关系表
 * 
 * */
@Entity
@Table(name = "lr_university_years_rec")
public class UniversityYearsRec extends IdEntity {

	private Long universityId;
	
	private Long yearsId;
	
	private Date viewDate;





	public Long getUniversityId() {
		return universityId;
	}

	public void setUniversityId(Long universityId) {
		this.universityId = universityId;
	}



	public Long getYearsId() {
		return yearsId;
	}

	public void setYearsId(Long yearsId) {
		this.yearsId = yearsId;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getViewDate() {
		return viewDate;
	}

	public void setViewDate(Date viewDate) {
		this.viewDate = viewDate;
	}

	public UniversityYearsRec(Long universityId, Long yearsId) {
		super();
		this.universityId = universityId;
		this.yearsId = yearsId;
		this.viewDate = Clock.DEFAULT.getCurrentDate();
	}

	public UniversityYearsRec() {
		super();
	}
	
}
