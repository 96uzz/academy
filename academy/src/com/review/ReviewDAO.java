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
	
	// ��������
	public ReviewDTO preReadLecture(int lecNum, String condition, String keyword) {
		ReviewDTO dto = null;
				
		return dto;
	}
	
	// ��������
	public ReviewDTO nextReadLecture(int lecNum, String condition, String keyword) {
		ReviewDTO dto = null;
		
		return dto;
	}
	
	public List<ReviewDTO> listReview(int reNum) { // ���� ���� ����Ʈ
		List<ReviewDTO> list = new ArrayList<ReviewDTO>();
		
		return list;
	}
	
	public void insertReview(ReviewDTO dto) { // ���� ���� ���
		
	}
	
	
	
	public void deleteReview(int reNum, String userId) { // ���� ���� ����(�����ڸ�)
		
	}
}
