package com.job.lr.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name = "lr_bounty_comment")
public class BountyComment extends IdEntity {

	private BountyApply apply;
	
	private String comment;
	
	private User commentUser;
	
	private Date commentDate;
	
	private Integer starLevel;
	
	private Long bountyUserId;

	private String sts="A";

	@ManyToOne
	@JoinColumn(name = "apply_id")
	public BountyApply getApply() {
		return apply;
	}

	public void setApply(BountyApply apply) {
		this.apply = apply;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@ManyToOne
	@JoinColumn(name = "comment_user_id")
	public User getCommentUser() {
		return commentUser;
	}

	public void setCommentUser(User commentUser) {
		this.commentUser = commentUser;
	}

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

	public void setStarLevel(Integer starLevel) {
		this.starLevel = starLevel;
	}

	public Long getBountyUserId() {
		return bountyUserId;
	}

	public void setBountyUserId(Long bountyUserId) {
		this.bountyUserId = bountyUserId;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
