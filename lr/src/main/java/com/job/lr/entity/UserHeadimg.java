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
 * 用户头像管理类
 * 
 * @author ly
 * 
 * */
@Entity
@Table(name = "lr_userheadimg")
public class UserHeadimg extends IdEntity {
	
	private Integer	useing;
	
	private String	imgname;//名称
	
	private String	imgpath;//存放位置
	
	private String	imgpath2;//存放位置2  缩略图
	
	private String	imgpath3;//存放位置2
	
	private String  location;//位置
	
	private String  imgdescribe;//描述
	
	private Date	indate ;//写入日期
	
	private Integer	order;		//排序
	
	private String  longitude; //经度
	
	private String  latitude ; //纬度
	
	private Integer stars ; //星星数
	
	private String	typename;//类型名称
	
	private Integer	typeint;//类型
	
	private String	label;//标签
	
	
	public UserHeadimg() {
	}

	public UserHeadimg(Long id) {
		this.id = id;
	}

	


	public String getImgname() {
		return imgname;
	}

	public void setImgname(String imgname) {
		this.imgname = imgname;
	}

	public String getImgpath() {
		return imgpath;
	}

	public void setImgpath(String imgpath) {
		this.imgpath = imgpath;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getImgdescribe() {
		return imgdescribe;
	}

	public void setImgdescribe(String imgdescribe) {
		this.imgdescribe = imgdescribe;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public Integer getStars() {
		return stars;
	}

	public void setStars(Integer stars) {
		this.stars = stars;
	}

	// 设定JSON序列化时的日期格式
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getIndate() {
		return indate;
	}

	public void setIndate(Date indate) {
		this.indate = indate;
	}	
	
	

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getImgpath2() {
		return imgpath2;
	}

	public void setImgpath2(String imgpath2) {
		this.imgpath2 = imgpath2;
	}

	public String getImgpath3() {
		return imgpath3;
	}

	public void setImgpath3(String imgpath3) {
		this.imgpath3 = imgpath3;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public Integer getTypeint() {
		return typeint;
	}

	public void setTypeint(Integer typeint) {
		this.typeint = typeint;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	

	public Integer getUseing() {
		return useing;
	}

	public void setUseing(Integer useing) {
		this.useing = useing;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}