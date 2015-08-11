/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.job.lr.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

//JPA标识
@Entity
@Table(name = "lr_task")
public class Task extends IdEntity {

	//兼职标题
	private String title;
	
	//工作描述
	private String description;
	
	private User user;
	
	//性别：1 男性 2女性 3不限
	private Integer gender;
	
	//时间要求
	private String timeRquirement;
	
	//任务所在区 通过区域对照码表定义
	private Integer district;
	
	//工资计算方式
	private String paymentCalcWay;
	
	//薪酬
	private Integer payment;
	
	//岗位要求
	private String jobRequirements;
	
	//联系电话
	private String phoneCall;
	
	//任务状态 
	private String jobSts;
	
	

	// JSR303 BeanValidator的校验规则
	@NotBlank
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getTimeRquirement() {
		return timeRquirement;
	}

	public void setTimeRquirement(String timeRquirement) {
		this.timeRquirement = timeRquirement;
	}

	public Integer getDistrict() {
		return district;
	}

	public void setDistrict(Integer district) {
		this.district = district;
	}

	public String getPaymentCalcWay() {
		return paymentCalcWay;
	}

	public void setPaymentCalcWay(String paymentCalcWay) {
		this.paymentCalcWay = paymentCalcWay;
	}

	public Integer getPayment() {
		return payment;
	}

	public void setPayment(Integer payment) {
		this.payment = payment;
	}

	public String getJobRequirements() {
		return jobRequirements;
	}

	public void setJobRequirements(String jobRequirements) {
		this.jobRequirements = jobRequirements;
	}

	public String getPhoneCall() {
		return phoneCall;
	}

	public void setPhoneCall(String phoneCall) {
		this.phoneCall = phoneCall;
	}

	public String getJobSts() {
		return jobSts;
	}

	public void setJobSts(String jobSts) {
		this.jobSts = jobSts;
	}

	// JPA 基于USER_ID列的多对一关系定义
	@ManyToOne
	@JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
