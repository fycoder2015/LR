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

//每日签到 总共 有多少连续的天数 
@Entity
@Table(name = "lr_daysignin")
public class Daysignin extends IdEntity {
	
	private Long userId;
	
	private Integer severaldays; //连续几天 一旦不连续 清零
	
	private Integer maxseveraldays; //最大连续签到天数
	
	private Integer totaldays; //总的签到几天
	
	private Date lastsignday; //最近一次的签到日
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Integer getSeveraldays() {
		return severaldays;
	}
	public void setSeveraldays(Integer severaldays) {
		this.severaldays = severaldays;
	}
	// 设定JSON序列化时的日期格式
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getLastsignday() {
		return lastsignday;
	}
	public void setLastsignday(Date lastsignday) {
		this.lastsignday = lastsignday;
	}
	public Integer getMaxseveraldays() {
		return maxseveraldays;
	}
	public void setMaxseveraldays(Integer maxseveraldays) {
		this.maxseveraldays = maxseveraldays;
	}
	public Integer getTotaldays() {
		return totaldays;
	}
	public void setTotaldays(Integer totaldays) {
		this.totaldays = totaldays;
	}
	
	
	
}
