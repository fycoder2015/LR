package com.job.lr.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "lr_category")
public class Category extends IdEntity {
	
	private String category_name;
	
	private String sts="A";
	
	private Integer cateLevel;
	
	private String alias;
	
	private String explain;
	
	private Category ancesCate;

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
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
