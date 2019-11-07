package com.notice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import com.util.DBConn;

public class NoticeDAO {
	private Connection conn=DBConn.getConnection();
	
	public void insertNotice(NoticeDTO dto) {
		PreparedStatement pstmt=null;
		StringBuilder sb=new StringBuilder();
		
		try {
			sb.append("INSERT INTO notice(noticeNum, notice, userId, subject, content) ");
			sb.append(" VALUES(notice_seq.NEXTVAL, ?, ?, ?, ?) ");
			pstmt=conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1, dto.getNotice());
			pstmt.setString(2, dto.getUserId());
			pstmt.setString(3, dto.getSubject());
			pstmt.setString(4, dto.getContent());
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
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
