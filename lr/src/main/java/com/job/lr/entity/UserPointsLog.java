package com.job.lr.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
//用户积分日志
@Entity
@Table(name = "lr_userpointsLog")
public class UserPointsLog extends IdEntity {

	private Long  userId;	
	private Integer userpoint;	
	private String userpointevent;//简单介绍	
	@JsonIgnore  //不在json的返回值中显示
	private String userpointdesc;  //描述	
	private Date userpointDate;

	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getUserpoint() {
		return userpoint;
	}

	public void setUserpoint(Integer userpoint) {
		this.userpoint = userpoint;
	}

	public String getUserpointevent() {
		return userpointevent;
	}

	public void setUserpointevent(String userpointevent) {
		this.userpointevent = userpointevent;
	}

	public String getUserpointdesc() {
		return userpointdesc;
	}

	public void setUserpointdesc(String userpointdesc) {
		this.userpointdesc = userpointdesc;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getUserpointDate() {
		return userpointDate;
	}

	public void setUserpointDate(Date userpointDate) {
		this.userpointDate = userpointDate;
	}	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
