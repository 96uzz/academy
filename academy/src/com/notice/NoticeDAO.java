package com.notice;

import java.sql.Connection;
import java.util.List;

import com.util.DBConn;

public class NoticeDAO {
	private Connection conn=DBConn.getConnection();
	
	public int insertNotice(NoticeDTO dto) {
		return 0;
	}
	
	public int dataCount(String condition, String keyword) {
		return 0;
	}
	
	// 공지글
	public List<NoticeDTO> listNotice() {
		return null;
	}
	
	public List<NoticeDTO> listNotice(int offset, int rows) {
		return null;
	}
	
	public List<NoticeDTO> listNotice(int offset, int rows, String condition, String keyword) {
		return null;
	}
	
	public NoticeDTO readNotice(int num) {
		return null;
	}
	
	// 이전글
	public NoticeDTO preReadNotie(int num, String condition, String keyword) {
		return null;
	}
	
	// 다음글
	public NoticeDTO nextReadNotice(int num, String condition, String keyword) {
		return null;
	}
	
	public void updateHitCount(int num) {
		
	}
	
	public void updateNotice(NoticeDTO dto) {
		
	}
	
	public void deleteNotice(int num, String userId) {
		
	}
}
