package com.job.lr.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springside.modules.utils.Clock;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 用户和地址的关系表
 * User 和  UserAddress 的对应关系表
 * 
 * */
@Entity
@Table(name = "lr_user_address_rec")
public class UserAddressRec extends IdEntity {

	private Long userId;
	
	private Long useraddressId;
	
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

	public UserAddressRec(Long userId, Long useraddressId) {
		super();
		this.userId = userId;
		this.useraddressId = useraddressId;
		this.viewDate = Clock.DEFAULT.getCurrentDate();
	}

	public UserAddressRec() {
		super();
	}

	public Long getUseraddressId() {
		return useraddressId;
	}

	public void setUseraddressId(Long useraddressId) {
		this.useraddressId = useraddressId;
	}
	
	
	
}
