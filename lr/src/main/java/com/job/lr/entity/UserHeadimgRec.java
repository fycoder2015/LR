package com.job.lr.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springside.modules.utils.Clock;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 用户和头像的关系表
 * 保持历史纪录的
 * User 和  UserPicoo 的对应关系表
 * 
 * 	原来的UserHeadimg出现奇怪的事 这里不使用它了。
 * 
 * */
@Entity
@Table(name = "lr_user_headimg_rec")
public class UserHeadimgRec extends IdEntity {

	private Long userId;
	
	private Long headimgId;
	
	private Date viewDate;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}



	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getViewDate() {
		return viewDate;
	}

	public void setViewDate(Date viewDate) {
		this.viewDate = viewDate;
	}

	public UserHeadimgRec(Long userId, Long headimgId) {
		super();
		this.userId = userId;
		this.headimgId = headimgId;
		this.viewDate = Clock.DEFAULT.getCurrentDate();
	}

	public UserHeadimgRec() {
		super();
	}

	public Long getHeadimgId() {
		return headimgId;
	}

	public void setHeadimgId(Long headimgId) {
		this.headimgId = headimgId;
	}
	
	
	
}
