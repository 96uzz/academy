package com.interlecture;

public class InterlectureDTO {
	// 강좌 정보
	private int listNum;
	private int lecCode;
	private int lecNum;
	private String lecName;
	private String lecStartDate;
	private String lecEndDate;
	private int lecLimit;
	private String lecIntro;
	private int acaNum;

	// 관심강좌 고유번호와 회원 아이디
	private int interNum;
	private String userId;

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

	public String getLecIntro() {
		return lecIntro;
	}

	public void setLecIntro(String lecIntro) {
		this.lecIntro = lecIntro;
	}

	public int getAcaNum() {
		return acaNum;
	}

	public void setAcaNum(int acaNum) {
		this.acaNum = acaNum;
	}

	public int getInterNum() {
		return interNum;
	}

	public void setInterNum(int interNum) {
		this.interNum = interNum;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
