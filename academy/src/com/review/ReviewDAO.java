package com.review;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class ReviewDAO {
private Connection conn=DBConn.getConnection();
		
	public int dataCount() {
		int result = 0;
		
		return result;
	}
	
	public int dataCount(String condition, String keyword) {
		int result = 0;
		
		return result;
	}
	
	public List<ReviewDTO> listLecture(int offset, int rows) {
		List<ReviewDTO> list = new ArrayList<ReviewDTO>();
		
		return list;
	}
	
	public List<ReviewDTO> listLecture(int offset, int rows, String condition, String keyword) {
		List<ReviewDTO> list = new ArrayList<ReviewDTO>();
		
		return list;
	}
	
	// 이전강의
	public ReviewDTO preReadLecture(int lecNum, String condition, String keyword) {
		ReviewDTO dto = null;
				
		return dto;
	}
	
	// 다음강의
	public ReviewDTO nextReadLecture(int lecNum, String condition, String keyword) {
		ReviewDTO dto = null;
		
		return dto;
	}
	
	public List<ReviewDTO> listReview(int reNum) { // 강의 리뷰 리스트
		List<ReviewDTO> list = new ArrayList<ReviewDTO>();
		
		return list;
	}
	
	public void insertReview(ReviewDTO dto) { // 강의 리뷰 등록
		
	}
	
	
	
	public void deleteReview(int reNum, String userId) { // 강의 리뷰 삭제(관리자만)
		
	}
}
