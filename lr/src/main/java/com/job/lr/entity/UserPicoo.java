package com.job.lr.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

//用户图片存储
@Entity
@Table(name = "lr_userpicoo")
public class UserPicoo extends IdEntity {
	
	private Integer	useing;//正在用   usering= 1 ; no use= 0
	private String	picname;//名称
	private String	picpath;//存放位置	
	private String	picpathsmall;//存放位置2  缩略图	
	private String	picpathbig;//存放位置2	
	private String  piclocation;//位置	
	private String  picdescribe;//描述
	private Date	picindate ;//写入日期
	private Integer	picorder;//排序
	private String  piclongitude; //经度
	private String  piclatitude ; //纬度
	private Integer picstars ; //星星数
	private String	pictypename;//类型名称
	private Integer	pictypeint;//类型		
	@JsonIgnore  //不在json的返回值中显示
	private String sts;


	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	public Integer getUseing() {
		return useing;
	}

	public void setUseing(Integer useing) {
		this.useing = useing;
	}

	public String getPicname() {
		return picname;
	}

	public void setPicname(String picname) {
		this.picname = picname;
	}

	public String getPicpath() {
		return picpath;
	}

	public void setPicpath(String picpath) {
		this.picpath = picpath;
	}

	public String getPicpathsmall() {
		return picpathsmall;
	}

	public void setPicpathsmall(String picpathsmall) {
		this.picpathsmall = picpathsmall;
	}

	public String getPicpathbig() {
		return picpathbig;
	}

	public void setPicpathbig(String picpathbig) {
		this.picpathbig = picpathbig;
	}

	public String getPiclocation() {
		return piclocation;
	}

	public void setPiclocation(String piclocation) {
		this.piclocation = piclocation;
	}

	public String getPicdescribe() {
		return picdescribe;
	}

	public void setPicdescribe(String picdescribe) {
		this.picdescribe = picdescribe;
	}

	public Date getPicindate() {
		return picindate;
	}

	public void setPicindate(Date picindate) {
		this.picindate = picindate;
	}

	public Integer getPicorder() {
		return picorder;
	}

	public void setPicorder(Integer picorder) {
		this.picorder = picorder;
	}

	public String getPiclongitude() {
		return piclongitude;
	}

	public void setPiclongitude(String piclongitude) {
		this.piclongitude = piclongitude;
	}

	public String getPiclatitude() {
		return piclatitude;
	}

	public void setPiclatitude(String piclatitude) {
		this.piclatitude = piclatitude;
	}

	public Integer getPicstars() {
		return picstars;
	}

	public void setPicstars(Integer picstars) {
		this.picstars = picstars;
	}

	public String getPictypename() {
		return pictypename;
	}

	public void setPictypename(String pictypename) {
		this.pictypename = pictypename;
	}

	public Integer getPictypeint() {
		return pictypeint;
	}

	public void setPictypeint(Integer pictypeint) {
		this.pictypeint = pictypeint;
	}


	
	
}
