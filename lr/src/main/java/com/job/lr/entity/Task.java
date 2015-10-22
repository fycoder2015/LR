
package com.job.lr.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

//JPA标识
@Entity
@Table(name = "lr_task")
public class Task extends IdEntity {

	//兼职标题
	private String title;
	
	//工作描述
	private String description;
	
	private User user;
	
	private Enterprise enterprise;
	
	//性别
	private String gender;
	
	//时间要求
	private String timeRquirement;
	
	//任务所在区 通过区域对照码表定义
	private String district;
	
	//工资计算方式
	private String paymentCalcWay;
	
	//薪酬
	private Integer payment;
	
	//岗位要求
	private String jobRequirements;
	
	//联系电话
	private String phoneCall;
	
	//任务状态：开放、关闭
	private String jobSts="开放"; 
	
	//任务类型：全职 兼职
	private String jobType;
	
	//所属城市
	private String cityId="022";
	
	//创建时间
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	//pv page view

	//用来统计某项任务的被访问次数和访问人数的
	//每次浏览任务详情的时候，由客户端调用一下这个url
	private Long pv ;
	//uv user view
	private Long uv ;
	
	private String imageFileName;
	
	private Integer employeeCnt;
	
	private String jobClass;

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


	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getTimeRquirement() {
		return timeRquirement;
	}

	public void setTimeRquirement(String timeRquirement) {
		this.timeRquirement = timeRquirement;
	}


	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
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


	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	
	@Temporal(TemporalType.TIMESTAMP) 
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getPv() {
		return pv;
	}

	public void setPv(Long pv) {
		this.pv = pv;
	}

	public Long getUv() {
		return uv;
	}

	public void setUv(Long uv) {
		this.uv = uv;
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
	
	@ManyToOne
	@JoinColumn(name = "enterprise_id")
	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public Integer getEmployeeCnt() {
		return employeeCnt;
	}

	public void setEmployeeCnt(Integer employeeCnt) {
		this.employeeCnt = employeeCnt;
	}

	public String getJobClass() {
		return jobClass;
	}

	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
