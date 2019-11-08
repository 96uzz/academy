package com.notice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
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
	public int dataCount() {
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM notice ";
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				result=rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return result;
	}
	
	public int dataCount(String condition, String keyword) {
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM notice WHERE INSTR(subject, ?) >= 1 ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			
			rs=pstmt.executeQuery();
			if(rs.next()) {
				result=rs.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return result;
	}
	
	// 공지글
	public List<NoticeDTO> listNotice() {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		List<NoticeDTO> list=new ArrayList<NoticeDTO>();
		
		try {
			sql="SELECT noticeNum, notice, userId, subject, content, hitCount FROM notice";
			
			pstmt=conn.prepareStatement(sql);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				NoticeDTO dto=new NoticeDTO();
				dto.setNoticeNum(rs.getInt("noticeNum"));
				dto.setNotice(rs.getInt("notice"));
				dto.setUserId(rs.getString("userId"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
				dto.setHitCount(rs.getInt("hitCount"));
				list.add(dto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return list;
	}
	
	public List<NoticeDTO> listNotice(int offset, int rows) {
		List<NoticeDTO> list = new ArrayList<NoticeDTO>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuilder sb=new StringBuilder();
		
		try {
			sb.append("SELECT noticeNum, notice, n.userId, subject, hitCount, created ");
			sb.append(" FROM notice n JOIN member m ON n.userId=m.userId");
			sb.append(" ORDER BY noticeNum DESC");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
			pstmt=conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1, offset);
			pstmt.setInt(2, rows);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				NoticeDTO dto=new NoticeDTO();
				
				dto.setNoticeNum(rs.getInt("noticeNum"));
				dto.setNotice(rs.getInt("notice"));
				dto.setUserId(rs.getString("userId"));
				dto.setSubject(rs.getString("subject"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setCreated(rs.getString("created"));
				
				list.add(dto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		} 

		return list;
	}
	
	public List<NoticeDTO> listNotice(int offset, int rows, String condition, String keyword) {
		PreparedStatement pstmt=null;
		List<NoticeDTO> list = new ArrayList<NoticeDTO>();
		ResultSet rs=null;
		StringBuilder sb=new StringBuilder();
		
		try {
			sb.append("SELECT noticeNum, notice, n.userId, subject, hitCount, created ");
			sb.append(" FROM notice n JOIN member m ON n.userId=m.userId");
			sb.append(" WHERE INSTR(subject, ?)>=1");
			sb.append(" ORDER BY num DESC");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
			
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setString(1, keyword);
			pstmt.setInt(2, offset);
			pstmt.setInt(3, rows);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				NoticeDTO dto = new NoticeDTO();
				
				dto.setNoticeNum(rs.getInt("noticeNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setSubject(rs.getString("subject"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setNotice(rs.getInt("notice"));
				dto.setCreated(rs.getString("created"));
				
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return list;
	}
	
	
	
	public NoticeDTO readNotice(int num) {
		NoticeDTO dto = null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuilder sb=new StringBuilder();
		
		try {
			sb.append("SELECT noticeNum, notice, n.userId, subject, hitCount, created, content ");
			sb.append(" FROM notice n JOIN member m ON n.userId=m.userId");
			sb.append(" WHERE num = ? ");
			
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setInt(1, num);
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto=new NoticeDTO();
				
				dto.setNoticeNum(rs.getInt("noticeNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setCreated(rs.getString("created"));
				dto.setNotice(rs.getInt("notice"));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		} 

		return dto;
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
