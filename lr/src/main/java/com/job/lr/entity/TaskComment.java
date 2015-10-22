package com.job.lr.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "lr_task_comment")
public class TaskComment extends IdEntity {
	
	private String comment;
	
	private User user;
	
	private Task task;
	
	private TaskApplyRecord apply;
	
	private Date commentDate;
	
	private String sts = "A";
	
	private Integer starLevel;

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@ManyToOne 
	@JoinColumn(name = "task_id")
	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	@ManyToOne
	@JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}
	
	
	
	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	public Integer getStarLevel() {
		return starLevel;
	}

	@ManyToOne
	@JoinColumn(name = "apply_id")
	public TaskApplyRecord getApply() {
		return apply;
	}

	public void setApply(TaskApplyRecord apply) {
		this.apply = apply;
	}

	public void setStarLevel(Integer starLevel) {
		this.starLevel = starLevel;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}	

}
