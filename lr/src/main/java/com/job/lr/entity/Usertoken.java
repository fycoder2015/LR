package com.job.lr.entity;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableList;

@Entity
@Table(name = "lr_usertoken")
public class Usertoken extends IdEntity {
	
	private Long  userId;

	private String usertoken;
	private String usertokenold;
	private String usertokenold2;
	private Date startDate;
	private String newtoken;
	
	public Usertoken() {
	}

	public Usertoken(Long id) {
		this.id = id;
	}
	
	@NotNull
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsertoken() {
		return usertoken;
	}

	public void setUsertoken(String usertoken) {
		this.usertoken = usertoken;
	}
	// 设定JSON序列化时的日期格式
	//@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	
	
	public String getUsertokenold() {
		return usertokenold;
	}

	public void setUsertokenold(String usertokenold) {
		this.usertokenold = usertokenold;
	}

	public String getUsertokenold2() {
		return usertokenold2;
	}

	public void setUsertokenold2(String usertokenold2) {
		this.usertokenold2 = usertokenold2;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	/**
	 * 生成唯一的uuid型token串
	 * @return uuid型token串
	 * 
	 * */
	@Transient
	public String getNewtoken() {
		UUID uuid = UUID.randomUUID();  
		UUID uuid2 = UUID.randomUUID();  
        String str = uuid.toString(); 
        String str2 = uuid2.toString(); 
        //去掉"-"符号  
        String temp = str.substring(0, 8) + str.substring(9, 13) + 
        			str.substring(14, 18) + str.substring(19, 23) + str.substring(24); 
        String temp2 = str2.substring(0, 8) + str2.substring(9, 13) + 
    			str2.substring(14, 18) + str2.substring(19, 23) + str2.substring(24); 
        newtoken =  temp+temp2 ;
		return newtoken;
	}
	
	@Transient
	public void setNewtoken(String newtoken) {
		this.newtoken = newtoken;
	}
}