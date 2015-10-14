package com.job.lr.entity;


/**
 * @author  
 * 
 * */
public class GeneralResponse {
	
	private Integer retCode=0;
	
	private String retInfo="成功";

	public Integer getRetCode() {
		return retCode;
	}

	public void setRetCode(Integer retCode) {
		this.retCode = retCode;
	}

	public void setRetInfo(String retInfo) {
		this.retInfo = retInfo;
	}

	public String getRetInfo() {
		return retInfo;
	}

	public void resp(String retInfo) {
		this.retInfo = retInfo;
	}

	public GeneralResponse(Integer retCode, String retInfo) {
		super();
		this.retCode = retCode;
		this.retInfo = retInfo;
	}

	public GeneralResponse() {
		super();
	}


	
}
