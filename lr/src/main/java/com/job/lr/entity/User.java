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
@Table(name = "lr_user")
public class User extends IdEntity {
	private String loginName;
	private String name;
	private String plainPassword;
	private String password;
	private String salt;
	private String roles;// 角色列表在数据库中实际以逗号分隔字符串存储，因此返回不能修改的List.
	private String phonenumber;
	@JsonIgnore  //不在json的返回值中显示
	private String captchacode;
	private Date registerDate;
	private String picpathBig;//大头像  
	private String picpathMid;//中头像  
	private String picpathSmall;//小头像 
	
	private String tempToken;//临时Token 用于找回密码
	private Date   tempTokenDate; //临时Token 的产生日期  后期进行时间比对  #二期
	
	/** 与正在使用的 角色一对一，UserRole 中的  useing=1，
		以直接调用正在使用的UserRole **/	
	private Long userroleId; //
	
	/** 与正在使用头像的 一对一，UserHeadimg 中的  useing=1，
	以直接调用正在使用的UserHeadimg **/	
	private Long userheadimgId; //

	public User() {
	}

	public User(Long id) {
		this.id = id;
	}

	@NotBlank
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// 不持久化到数据库，也不显示在Restful接口的属性.
	@Transient
	@JsonIgnore
	public String getPlainPassword() {
		return plainPassword;
	}

	public void setPlainPassword(String plainPassword) {
		this.plainPassword = plainPassword;
	}
	@JsonIgnore  //不在json的返回值中显示
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	@JsonIgnore  //不在json的返回值中显示
	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}
	
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

	@Transient
	@JsonIgnore
	public List<String> getRoleList() {
		// 角色列表在数据库中实际以逗号分隔字符串存储，因此返回不能修改的List.
		return ImmutableList.copyOf(StringUtils.split(roles, ","));
	}

	// 设定JSON序列化时的日期格式
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	
	@JsonIgnore  //不在json的返回值中显示
	public String getTempToken() {
		return tempToken;
	}

	public void setTempToken(String tempToken) {
		this.tempToken = tempToken;
	}
	// 设定JSON序列化时的日期格式
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getTempTokenDate() {
		return tempTokenDate;
	}

	public void setTempTokenDate(Date tempTokenDate) {
		this.tempTokenDate = tempTokenDate;
	}

	public String getPicpathBig() {
		return picpathBig;
	}

	public void setPicpathBig(String picpathBig) {
		this.picpathBig = picpathBig;
	}

	public String getPicpathMid() {
		return picpathMid;
	}

	public void setPicpathMid(String picpathMid) {
		this.picpathMid = picpathMid;
	}

	public String getPicpathSmall() {
		return picpathSmall;
	}

	public void setPicpathSmall(String picpathSmall) {
		this.picpathSmall = picpathSmall;
	}

	
	
	
	public Long getUserheadimgId() {
		return userheadimgId;
	}

	public void setUserheadimgId(Long userheadimgId) {
		this.userheadimgId = userheadimgId;
	}

	public Long getUserroleId() {
		return userroleId;
	}

	public void setUserroleId(Long userroleId) {
		this.userroleId = userroleId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}