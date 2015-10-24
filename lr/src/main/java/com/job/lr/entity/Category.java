package com.job.lr.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "lr_category")
public class Category extends IdEntity {
	
	private String categoryName;
	
	private String sts="A";
	
	private Integer cateLevel=1;
	
	private String alias;
	
	private String explain;
	
	private Category ancesCate;

	private Integer groupId;
	
	
	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	public Integer getCateLevel() {
		return cateLevel;
	}

	public void setCateLevel(Integer cateLevel) {
		this.cateLevel = cateLevel;
	}

	@ManyToOne
	@JoinColumn(name = "ances_cate_id")
	public Category getAncesCate() {
		return ancesCate;
	}

	public void setAncesCate(Category ancesCate) {
		this.ancesCate = ancesCate;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}
	
	
}
