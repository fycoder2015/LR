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
 * 用户地址管理类
 * 
 * @author ly
 * 
 * */
@Entity
@Table(name = "lr_useraddress")
public class UserAddress extends IdEntity {

	private String	province;//省份
	private String  city;//城市
	private String	district;//区县
	private String  street;//街道
	private String  street2;//街道2
	private String  street3;//街道3
	private String  street4;//街道4
	private String 	mailname;//收件人姓名
	private String  connectphone;//联系电话
	private String  zipcode;//邮编
	private String  remark;//备注
	private Integer	bedefault;//是否为默认
	private Date	indate ;//写入日期
	
	private Integer	order;		//排序
	
	public UserAddress() {
	}

	public UserAddress(Long id) {
		this.id = id;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getMailname() {
		return mailname;
	}

	public void setMailname(String mailname) {
		this.mailname = mailname;
	}

	public String getConnectphone() {
		return connectphone;
	}

	public void setConnectphone(String connectphone) {
		this.connectphone = connectphone;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
	
	
	public String getStreet2() {
		return street2;
	}

	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	public String getStreet3() {
		return street3;
	}

	public void setStreet3(String street3) {
		this.street3 = street3;
	}

	public String getStreet4() {
		return street4;
	}

	public void setStreet4(String street4) {
		this.street4 = street4;
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