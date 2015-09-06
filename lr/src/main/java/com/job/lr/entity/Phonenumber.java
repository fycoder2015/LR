package com.job.lr.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableList;

@Entity
@Table(name = "lr_phonenumber")
public class Phonenumber extends IdEntity {

	private String phonenumber; //唯一
	private String captchacode;
	private Integer phonestatus;  // 0 ,未激活 ； 1，已激活 ； 2，解绑
	private Date registerDate;	

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getCaptchacode() {
		return captchacode;
	}

	public void setCaptchacode(String captchacode) {
		this.captchacode = captchacode;
	}

	public Integer getPhonestatus() {
		return phonestatus;
	}

	public void setPhonestatus(Integer phonestatus) {
		this.phonestatus = phonestatus;
	}

	public Phonenumber() {
	}

	public Phonenumber(Long id) {
		this.id = id;
	}
	// 设定JSON序列化时的日期格式
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}