package com.academy;

import java.sql.Connection;
import java.util.List;

import com.util.DBConn;


public class AcademyDAO {
	private Connection conn=DBConn.getConnection();
	
	public int insertAcademy(AcademyDTO dto) {
		return 0;
		
	}
	
	public int dataCount() {
		return 0;
		
	}
	
	public int dataCount(String condition, String keyword) {
		return 0;
		
	}
	
	public List<AcademyDTO> listAcademy(int offset, int rows) {
		return null;
		
	}
	
	public List<AcademyDTO> listAcademy(int offset, int rows, String condition, String keyword) {
		return null;
		
	}
	
	// 이전글
	public AcademyDTO preReadAcademy(int num, String condition, String keyword) {
		return null;
		
	}
	
	// 다음글
	public AcademyDTO nextReadAcademy(int num, String condition, String keyword) {
		return null;
		
	}
	
	public int updateAcademy(AcademyDTO dto) {
		return 0;
		
	}
	
	public int deleteBoard(int num, String userId) {
		return num;
		
	}
}
