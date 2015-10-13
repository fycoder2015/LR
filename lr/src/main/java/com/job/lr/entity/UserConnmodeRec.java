package com.job.lr.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springside.modules.utils.Clock;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 用户和联系方式的关系表
 * User 和  UserConnmode 的对应关系表
 * 
 * */
@Entity
@Table(name = "lr_user_connmode_rec")
public class UserConnmodeRec extends IdEntity {

	private Long userId;
	
	private Long userconnmodeId;
	
	private Date viewDate;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getViewDate() {
		return viewDate;
	}

	public void setViewDate(Date viewDate) {
		this.viewDate = viewDate;
	}

	public UserConnmodeRec(Long userId, Long userconnmodeId) {
		super();
		this.userId = userId;
		this.userconnmodeId = userconnmodeId;
		this.viewDate = Clock.DEFAULT.getCurrentDate();
	}

	public UserConnmodeRec() {
		super();
	}

	public Long getUserconnmodeId() {
		return userconnmodeId;
	}

	public void setUserconnmodeId(Long userconnmodeId) {
		this.userconnmodeId = userconnmodeId;
	}
	
	
}
