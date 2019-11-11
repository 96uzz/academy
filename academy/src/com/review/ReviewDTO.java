package com.review;

public class ReviewDTO {
	// °­ÁÂ Á¤º¸
	private int listNum;
	private int lecCode;
	private int lecNum;
	private String lecName;
	private String lecStartDate;
	private String lecEndDate;
	private int lecLimit;
	private int acaNum;
	private String acaName;

	// °­ÁÂÀÇ ¸®ºä Á¤º¸
	private int reNum;
	private String userId;
	private double rate;
	private String content;
	private String created;

	
	
	public String getAcaName() {
		return acaName;
	}

	public void setAcaName(String acaName) {
		this.acaName = acaName;
	}

	public int getListNum() {
		return listNum;
	}

	public void setListNum(int listNum) {
		this.listNum = listNum;
	}

	public int getLecCode() {
		return lecCode;
	}

	public void setLecCode(int lecCode) {
		this.lecCode = lecCode;
	}

	public int getLecNum() {
		return lecNum;
	}

	public void setLecNum(int lecNum) {
		this.lecNum = lecNum;
	}

	public String getLecName() {
		return lecName;
	}

	public void setLecName(String lecName) {
		this.lecName = lecName;
	}

	public String getLecStartDate() {
		return lecStartDate;
	}

	public void setLecStartDate(String lecStartDate) {
		this.lecStartDate = lecStartDate;
	}

	public String getLecEndDate() {
		return lecEndDate;
	}

	public void setLecEndDate(String lecEndDate) {
		this.lecEndDate = lecEndDate;
	}

	public int getLecLimit() {
		return lecLimit;
	}

	public void setLecLimit(int lecLimit) {
		this.lecLimit = lecLimit;
	}

	public int getAcaNum() {
		return acaNum;
	}

	public void setAcaNum(int acaNum) {
		this.acaNum = acaNum;
	}

	public int getReNum() {
		return reNum;
	}

	public void setReNum(int reNum) {
		this.reNum = reNum;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

}
