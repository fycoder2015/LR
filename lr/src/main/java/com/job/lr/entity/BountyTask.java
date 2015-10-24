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

@Entity
@Table(name = "lr_bounty_task")
public class BountyTask extends IdEntity {

	//任务标题（简述）
	private String title;
	
	//任务描述
	private String description;
	
	//任务所属用户
	private User user;
	
	//性别要求
	private String gender;
	
	//创建时间
	private Date createDate;
	
	//任务时间
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date taskDate;
	
	//任务关闭时间
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date closeDate;


	//任务地点
	private String taskLocation;
	
	//酬谢方式
	private String paymentType;
	
	//任务状态
	private String sts = "开放";
	
	//现金酬谢金额
	private Integer bounty;
	
	private Integer auditFlag=0;

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

	
	@ManyToOne
	@JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Temporal(TemporalType.TIMESTAMP) 
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Temporal(TemporalType.TIMESTAMP) 
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+08:00")
	public Date getTaskDate() {
		return taskDate;
	}

	public void setTaskDate(Date taskDate) {
		this.taskDate = taskDate;
	}

	public String getTaskLocation() {
		return taskLocation;
	}

	public void setTaskLocation(String taskLocation) {
		this.taskLocation = taskLocation;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	@Temporal(TemporalType.TIMESTAMP) 
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+08:00")
	public Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}
	
	public Integer getBounty() {
		return bounty;
	}

	public void setBounty(Integer bounty) {
		this.bounty = bounty;
	}

	
	public Integer getAuditFlag() {
		return auditFlag;
	}

	public void setAuditFlag(Integer auditFlag) {
		this.auditFlag = auditFlag;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}
