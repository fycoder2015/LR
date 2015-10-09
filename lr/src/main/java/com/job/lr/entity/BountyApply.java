package com.job.lr.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "lr_bounty_apply")
public class BountyApply extends IdEntity {

	private User applyUser;
	
	private BountyTask bountyTask;
	
	private Long taskUserId;
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date applyDate;
	
	private String sts="W";

	@ManyToOne
	@JoinColumn(name = "apply_user_id")
	public User getApplyUser() {
		return applyUser;
	}

	public void setApplyUser(User applyUser) {
		this.applyUser = applyUser;
	}

	@ManyToOne
	@JoinColumn(name = "bounty_id")
	public BountyTask getBountyTask() {
		return bountyTask;
	}

	public void setBountyTask(BountyTask bountyTask) {
		this.bountyTask = bountyTask;
	}

	@Temporal(TemporalType.TIMESTAMP) 
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	public Long getTaskUserId() {
		return taskUserId;
	}

	public void setTaskUserId(Long taskUserId) {
		this.taskUserId = taskUserId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	
}
