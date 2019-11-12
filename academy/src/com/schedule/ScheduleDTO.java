package com.schedule;

public class ScheduleDTO {
	private int num;
	private String userId;
	private String lecCode;
	private String lecName;
	private String acaName, acaNum;
	private String sday, eday;
	private int lecLimit;
	private String created;
	private String memo;
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getLecCode() {
		return lecCode;
	}
	public void setLecCode(String lecCode) {
		this.lecCode = lecCode;
	}
	public String getLecName() {
		return lecName;
	}
	public void setLecName(String lecName) {
		this.lecName = lecName;
	}
	public String getAcaName() {
		return acaName;
	}
	public void setAcaName(String acaName) {
		this.acaName = acaName;
	}
	public String getAcaNum() {
		return acaNum;
	}
	public void setAcaNum(String acaNum) {
		this.acaNum = acaNum;
	}
	public String getSday() {
		return sday;
	}
	public void setSday(String sday) {
		this.sday = sday;
	}
	public String getEday() {
		return eday;
	}
	public void setEday(String eday) {
		this.eday = eday;
	}
	public int getLecLimit() {
		return lecLimit;
	}
	public void setLecLimit(int lecLimit) {
		this.lecLimit = lecLimit;
	}
//	public String getLecIntro() {
//		return lecIntro;
//	}
//	public void setLecIntro(String lecIntro) {
//		this.lecIntro = lecIntro;
//	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	

	
	
	
	
	
}
