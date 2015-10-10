package com.job.lr.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springside.modules.utils.Clock;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 用户和角色的关系表
 * 
 * 
 * */
@Entity
@Table(name = "lr_user_role_rec")
public class UserRoleRec extends IdEntity {

	private Long userId;
	
	private Long roleId;
	
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

	public UserRoleRec(Long userId, Long roleId) {
		super();
		this.userId = userId;
		this.roleId = roleId;
		this.viewDate = Clock.DEFAULT.getCurrentDate();
	}

	public UserRoleRec() {
		super();
	}
	
}
