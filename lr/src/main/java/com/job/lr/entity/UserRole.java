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


/**
 * 用户角色、积分
 * 
 * @author ly
 * 
 * */
@Entity
@Table(name = "lr_userrole")
public class UserRole extends IdEntity {
	//http://www.cnblogs.com/jifeng/p/4500410.html
	
	private String  rolename;//用户角色名称
	private Integer userpoint;//用户积分
	private Integer userpoint2;//用户积分  备用
	private Integer usercredit ; //用户信用
	private String  roledescription;
	private Date roledate; 	

	public UserRole() {
	}

	public UserRole(Long id) {
		this.id = id;
	}

//	@Transient
//	@JsonIgnore
//	public List<String> getRoleList() {
//		// 角色列表在数据库中实际以逗号分隔字符串存储，因此返回不能修改的List.
//		return ImmutableList.copyOf(StringUtils.split(roles, ","));
//	}
	
	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}



	public String getRoledescription() {
		return roledescription;
	}

	public void setRoledescription(String roledescription) {
		this.roledescription = roledescription;
	}
	// 设定JSON序列化时的日期格式
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getRoledate() {
		return roledate;
	}

	public void setRoledate(Date roledate) {
		this.roledate = roledate;
	}

	
	public Integer getUserpoint() {
		return userpoint;
	}

	public void setUserpoint(Integer userpoint) {
		this.userpoint = userpoint;
	}

	public Integer getUserpoint2() {
		return userpoint2;
	}

	public void setUserpoint2(Integer userpoint2) {
		this.userpoint2 = userpoint2;
	}

	public Integer getUsercredit() {
		return usercredit;
	}

	public void setUsercredit(Integer usercredit) {
		this.usercredit = usercredit;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}