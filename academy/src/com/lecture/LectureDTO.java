package com.lecture;

public class LectureDTO {
	private int listNum;
	private int lecCode;
	private int lecNum;
	private String lecName;
	private String lecStartDate;
	private String lecEndDate;
	private int lecLimit;
	private int acaNum;
	private String lecIntro;
	private String created;
	private int hitCount;
	private String userId;
	private Long gap;
	
	public Long getGap() {
		return gap;
	}
	public void setGab(Long gap) {
		this.gap = gap;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public int getHitCount() {
		return hitCount;
	}
	public void setHitCount(int hitCount) {
		this.hitCount = hitCount;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getLecIntro() {
		return lecIntro;
	}
	public void setLecIntro(String lecIntro) {
		this.lecIntro = lecIntro;
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
	
	
}
