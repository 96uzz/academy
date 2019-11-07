package com.lecture;

import java.sql.Connection;
import java.util.List;

import com.util.DBConn;

public class LectureDAO {
	private Connection conn=DBConn.getConnection();
	
	public int insertLecture(LectureDTO dto) {
		return 0;
	}
	
	public int dataCount() {
		return 0;
	}
	
	public int dataCount(String condition, String keyword) {
		return 0;
	}
	
	public List<LectureDTO> listLecture(int offset, int rows) {
		return null;
	}
	
	public List<LectureDTO> listLecture(int offset, int rows, String condition, String keyword) {
		return null;
	}
	
	// 이전글
	public LectureDTO preReadLecture(int num, String condition, String keyword) {
		return null;
	}
	
	// 다음글
	public LectureDTO nextReadLecture(int num, String condition, String keyword) {
		return null;
	}
	
	public int updateLecture(LectureDTO dto) {
		return 0;
	}
	
	public int deleteLecture(int num, String userId) {
		return num;
	}
	
}
