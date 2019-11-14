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
	private String lecIntro;
	
	private int replyNum, num;
	private String userName; 
	private int answer;
	private int replyLike;
	
	private int answerCount, likeCount, disLikeCount;
	
	
	
	// °­ÁÂÀÇ ¸®ºä Á¤º¸
	private int reNum;
	private String userId;
	private String rate;
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

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getLecIntro() {
		return lecIntro;
	}

	public void setLecIntro(String lecIntro) {
		this.lecIntro = lecIntro;
	}

	public int getReplyNum() {
		return replyNum;
	}

	public void setReplyNum(int replyNum) {
		this.replyNum = replyNum;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getAnswer() {
		return answer;
	}

	public void setAnswer(int answer) {
		this.answer = answer;
	}

	public int getReplyLike() {
		return replyLike;
	}

	public void setReplyLike(int replyLike) {
		this.replyLike = replyLike;
	}

	public int getAnswerCount() {
		return answerCount;
	}

	public void setAnswerCount(int answerCount) {
		this.answerCount = answerCount;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public int getDisLikeCount() {
		return disLikeCount;
	}

	public void setDisLikeCount(int disLikeCount) {
		this.disLikeCount = disLikeCount;
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

	@Override
	public String toString() {
		return "ReviewDTO [listNum=" + listNum + ", lecCode=" + lecCode + ", lecNum=" + lecNum + ", lecName=" + lecName
				+ ", lecStartDate=" + lecStartDate + ", lecEndDate=" + lecEndDate + ", lecLimit=" + lecLimit
				+ ", acaNum=" + acaNum + ", acaName=" + acaName + ", lecIntro=" + lecIntro + ", replyNum=" + replyNum
				+ ", num=" + num + ", userName=" + userName + ", answer=" + answer + ", replyLike=" + replyLike
				+ ", answerCount=" + answerCount + ", likeCount=" + likeCount + ", disLikeCount=" + disLikeCount
				+ ", reNum=" + reNum + ", userId=" + userId + ", rate=" + rate + ", content=" + content + ", created="
				+ created + "]";
	}

}
