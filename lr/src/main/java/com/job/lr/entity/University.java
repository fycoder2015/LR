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


//大学
@Entity
@Table(name = "lr_university")
public class University extends IdEntity {
	
	private String city;

	private String university;
	@JsonIgnore  //不在json的返回值中显示
	private String  longitude; //经度
	@JsonIgnore  //不在json的返回值中显示
	private String  latitude ; //纬度
	
	private String place ;  //地点	
	@JsonIgnore  //不在json的返回值中显示
	private String sts;


	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getUniversity() {
		return university;
	}

	public void setUniversity(String university) {
		this.university = university;
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

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}


	
	
}
