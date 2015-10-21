package com.job.lr.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springside.modules.utils.Clock;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 大学和专业的关系表
 *  
 * University 和  Subject 的对应关系表
 * 
 * */
@Entity
@Table(name = "lr_university_subject_rec")
public class UniversitySubjectRec extends IdEntity {

	private Long universityId;
	
	private Long subjectId;
	
	private Date viewDate;





	public Long getUniversityId() {
		return universityId;
	}

	public void setUniversityId(Long universityId) {
		this.universityId = universityId;
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getViewDate() {
		return viewDate;
	}

	public void setViewDate(Date viewDate) {
		this.viewDate = viewDate;
	}

	public UniversitySubjectRec(Long universityId, Long subjectId) {
		super();
		this.universityId = universityId;
		this.subjectId = subjectId;
		this.viewDate = Clock.DEFAULT.getCurrentDate();
	}

	public UniversitySubjectRec() {
		super();
	}
	
}
