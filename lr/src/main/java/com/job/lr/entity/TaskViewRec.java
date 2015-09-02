package com.job.lr.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springside.modules.utils.Clock;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "lr_task_view_rec")
public class TaskViewRec extends IdEntity {

	private Long userId;
	
	private Long taskId;
	
	private Date viewDate;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getViewDate() {
		return viewDate;
	}

	public void setViewDate(Date viewDate) {
		this.viewDate = viewDate;
	}

	public TaskViewRec(Long userId, Long taskId) {
		super();
		this.userId = userId;
		this.taskId = taskId;
		this.viewDate = Clock.DEFAULT.getCurrentDate();
	}

	public TaskViewRec() {
		super();
	}
	
}
