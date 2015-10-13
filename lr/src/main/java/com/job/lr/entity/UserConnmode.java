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
 * 用户通讯方式管理类
 * 
 * @author ly
 * 
 * */
@Entity
@Table(name = "lr_userconnmode")
public class UserConnmode extends IdEntity {

	private String	connmodename;	//通讯方式名称
	private String  connmodevalue1;	//通讯方式值1
	private String	connmodevalue2;	//通讯方式值2
	private String  connmodevalue3;	//通讯方式值3	
	private Integer	bedefault;	//是否为默认
	private Integer	order;		//排序
	private Date	indate;		//写入日期
	
	public UserConnmode() {
	}

	public UserConnmode(Long id) {
		this.id = id;
	}


	public Integer getBedefault() {
		return bedefault;
	}

	public void setBedefault(Integer bedefault) {
		this.bedefault = bedefault;
	}
	// 设定JSON序列化时的日期格式
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getIndate() {
		return indate;
	}

	public void setIndate(Date indate) {
		this.indate = indate;
	}		
	

	public String getConnmodename() {
		return connmodename;
	}

	public void setConnmodename(String connmodename) {
		this.connmodename = connmodename;
	}

	public String getConnmodevalue1() {
		return connmodevalue1;
	}

	public void setConnmodevalue1(String connmodevalue1) {
		this.connmodevalue1 = connmodevalue1;
	}

	public String getConnmodevalue2() {
		return connmodevalue2;
	}

	public void setConnmodevalue2(String connmodevalue2) {
		this.connmodevalue2 = connmodevalue2;
	}

	public String getConnmodevalue3() {
		return connmodevalue3;
	}

	public void setConnmodevalue3(String connmodevalue3) {
		this.connmodevalue3 = connmodevalue3;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}