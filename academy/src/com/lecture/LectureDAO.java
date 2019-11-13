package com.lecture;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.util.DBConn;

public class LectureDAO {
	private Connection conn=DBConn.getConnection();
	
	public int insertLecture(LectureDTO dto) {
		int result=0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql="INSERT INTO lecture(lecCode, acaNum, lecName, lecNum, lecStartDate, lecendDate, lecLimit, lecIntro, userId) VALUES (lec_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getAcaNum());
			pstmt.setString(2, dto.getLecName());
			pstmt.setInt(3, dto.getLecNum());
			pstmt.setString(4, dto.getLecStartDate());
			pstmt.setString(5, dto.getLecEndDate());
			pstmt.setInt(6, dto.getLecLimit());
			pstmt.setString(7, dto.getLecIntro());
			pstmt.setString(8, dto.getUserId());
			
			result=pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null) 
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			
		}
		return result;
	}
	
	public int dataCount() {
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="SELECT NVL(COUNT(*), 0) FROM lecture ";
			pstmt=conn.prepareStatement(sql);
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
			sql="SELECT NVL(COUNT(*), 0) FROM lecture ";
			if(condition.equalsIgnoreCase("created")) {
				keyword=keyword.replaceAll("-", "");
				sql += " WHERE TO_CHAR(l.created, 'YYYYMMDD') = ? ";
			} else if(condition.equalsIgnoreCase("lecName")) {
				sql += " WHERE INSTR(l.lecName, ?) = 1 ";
			} else {
				sql += " WHERE INSTR("+condition+", ?) >= 1 ";
			}
			pstmt=conn.prepareStatement(sql);
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
	
	public List<LectureDTO> listLecture() {
		List<LectureDTO> list = new ArrayList<LectureDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("SELECT l.lecCode, l.lecNum, l.lecName, l.lecStartDate, l.lecEndDate, l.lecLimit, l.lecIntro, ");
			sb.append(" TO_CHAR(l.created, 'YYYY-MM-DD') created, l.userId, a.acaName");
			sb.append(" FROM lecture l JOIN member m ON l.userId=m.userId JOIN academy a ON l.acaNum=a.acaNum ");
			sb.append(" ORDER BY l.lecCode DESC ");
			
			pstmt=conn.prepareStatement(sb.toString());
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				LectureDTO dto = new LectureDTO();
				dto.setLecCode(rs.getInt("lecCode"));
				dto.setLecNum(rs.getInt("lecNum"));
				dto.setLecName(rs.getString("lecName"));
				dto.setLecStartDate(rs.getString("lecStartDate"));
				dto.setLecEndDate(rs.getString("lecEndDate"));
				dto.setLecLimit(rs.getInt("lecLimit"));
				dto.setLecIntro(rs.getString("lecIntro"));
				dto.setCreated(rs.getString("created"));
				dto.setUserId(rs.getString("userId"));
				dto.setAcaName(rs.getString("acaName"));;
				
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
	
	public List<LectureDTO> listLecture(int offset, int rows) {
		List<LectureDTO> list = new ArrayList<LectureDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("SELECT l.lecCode, l.lecNum, l.lecName, l.lecStartDate, l.lecEndDate, l.lecLimit, l.lecIntro, ");
			sb.append(" TO_CHAR(l.created, 'YYYY-MM-DD')created, l.hitCount, l.userId, a.acaName ");
			sb.append(" FROM lecture l JOIN member m ON l.userId=m.userId JOIN academy a ON l.acaNum=a.acaNum ORDER BY l.lecCode DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
			
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setInt(1, offset);
			pstmt.setInt(2, rows);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				LectureDTO dto = new LectureDTO();
				dto.setLecCode(rs.getInt("lecCode"));
				dto.setLecNum(rs.getInt("lecNum"));
				dto.setLecName(rs.getString("lecName"));
				dto.setLecStartDate(rs.getString("lecStartDate"));
				dto.setLecEndDate(rs.getString("lecEndDate"));
				dto.setLecLimit(rs.getInt("lecLimit"));
				dto.setLecIntro(rs.getString("lecIntro"));
				dto.setCreated(rs.getString("created"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setUserId(rs.getString("userId"));
				dto.setAcaName(rs.getString("acaName"));;
				
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
			if(pstmt!=null)
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
		}
		return list;
	}
	
	public List<LectureDTO> listLecture(int offset, int rows, String condition, String keyword) {
		List<LectureDTO> list = new ArrayList<LectureDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("SELECT l.lecCode, l.lecNum, l.lecName, l.lecStartDate, l.lecEndDate, l.lecLimit, l.lecIntro, ");
			sb.append(" TO_CHAR(l.created, 'YYYY-MM-DD') created, l.hitCount, l.userId, a.acaName FROM lecture l ");
			sb.append(" JOIN member m ON l.userId=m.userId JOIN academy a ON l.acaNum=a.acaNum ORDER BY l.lecCode DESC");
			if(condition.equalsIgnoreCase("Created")) {
				keyword = keyword.replaceAll("-", "");
				sb.append(" WHERE TO_CHAR(l.created, 'YYYYMMDD' = ? ");
			} else if(condition.equalsIgnoreCase("lecName")) {
				sb.append(" WHERE INSTR(l.lecName, ?) = 1 ");
			} else {
				sb.append(" WHERE INSTR("+condition+", ?) >= 1 ");
			}
			sb.append("ORDER BY l.lecCode DESC ");
			sb.append("OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, keyword);
			pstmt.setInt(2, offset);
			pstmt.setInt(3, rows);
			
			rs=pstmt.executeQuery();
			while(rs.next()) {
				LectureDTO dto = new LectureDTO();
				dto.setLecCode(rs.getInt("lecCode"));
				dto.setLecNum(rs.getInt("lecNum"));
				dto.setLecName(rs.getString("lecName"));
				dto.setLecStartDate(rs.getString("lecStartDate"));
				dto.setLecEndDate(rs.getString("lecEndDate"));
				dto.setLecLimit(rs.getInt("lecLimit"));
				dto.setLecIntro(rs.getString("lecIntro"));
				dto.setCreated(rs.getString("created"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setUserId(rs.getString("userId"));
				dto.setAcaName(rs.getString("acaName"));;
				
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
	
	public LectureDTO readLecture(int num) {
		LectureDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("SELECT l.lecCode, l.lecNum, l.lecName, l.lecStartDate, l.lecEndDate, l.lecLimit, l.lecIntro, ");
			sb.append(" l.created, l.hitcount, l.userId, a.acaNum, a.acaName ");
			sb.append(" FROM lecture l JOIN member m ON l.userId=m.userId JOIN academy a ON l.acaNum=a.acaNum ");
			sb.append(" WHERE l.lecCode = ? ");
			
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setInt(1, num);
			
			rs=pstmt.executeQuery();
			if(rs.next()) {
				dto=new LectureDTO();
				dto.setLecCode(rs.getInt("lecCode"));
				dto.setLecNum(rs.getInt("lecNum"));
				dto.setLecName(rs.getString("lecName"));
				dto.setLecStartDate(rs.getString("lecStartDate"));
				dto.setLecEndDate(rs.getString("lecEndDate"));
				dto.setLecLimit(rs.getInt("lecLimit"));
				dto.setLecIntro(rs.getString("lecIntro"));
				dto.setCreated(rs.getString("created"));
				dto.setHitCount(rs.getInt("hitcount"));
				dto.setUserId(rs.getString("userId"));
				dto.setAcaNum(rs.getInt("acaNum"));
				dto.setAcaName(rs.getString("acaName"));
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
	public LectureDTO preReadLecture(int num, String condition, String keyword) {
		LectureDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			if(keyword.length() !=0) {
				sb.append("SELECT l.lecCode, l.lecName , a.acaName FROM lecture l JOIN member m ON l.userId=m.userId JOIN academy a ON l.acaNum=a.acaNum ");
				if(condition.equalsIgnoreCase("created")) {
					keyword=keyword.replaceAll("-", "");
					sb.append(" WHERE (TO_CHAR(l.created, 'YYYYMMDD') = ?) ");
				} else {
					sb.append(" WHERE (INSTR(" + condition + ", ?) >= 1) ");
				}
				sb.append("       AND (l.lecCode > ? ) ");
				sb.append(" ORDER BY l.lecCode ASC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY ");
				
				pstmt=conn.prepareStatement(sb.toString());
				pstmt.setString(1, keyword);
				pstmt.setInt(2, num);
			} else {
				sb.append("SELECT l.lecCode, l.lecName , a.acaName FROM lecture l JOIN member m ON l.userId=m.userId JOIN academy a ON l.acaNum=a.acaNum ");
				sb.append(" WHERE l.lecCode > ? ");
				sb.append(" ORDER BY l.lecCode ASC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY");
				
				pstmt=conn.prepareStatement(sb.toString());
				pstmt.setInt(1, num);
			}
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto=new LectureDTO();
				dto.setLecCode(rs.getInt("lecCode"));
				dto.setLecName(rs.getString("lecName"));
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
	
	// 다음글
	public LectureDTO nextReadLecture(int num, String condition, String keyword) {
		LectureDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			if(keyword.length() !=0) {
				sb.append("SELECT l.lecCode, l.lecName , a.acaName FROM lecture l JOIN member m ON l.userId=m.userId JOIN academy a ON l.acaNum=a.acaNum ");
				if(condition.equalsIgnoreCase("created")) {
					keyword=keyword.replaceAll("-", "");
					sb.append(" WHERE (TO_CHAR(l.created, 'YYYYMMDD') = ?) ");
				} else {
					sb.append(" WHERE (INSTR(" + condition + ", ?) >= 1) ");
				}
				sb.append("       AND (l.lecCode < ? ) ");
				sb.append(" ORDER BY l.lecCode DESC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY ");
				
				pstmt=conn.prepareStatement(sb.toString());
				pstmt.setString(1, keyword);
				pstmt.setInt(2, num);
			} else {
				sb.append("SELECT l.lecCode, l.lecName , a.acaName FROM lecture l JOIN member m ON l.userId=m.userId JOIN academy a ON l.acaNum=a.acaNum ");
				sb.append(" WHERE l.lecCode < ? ");
				sb.append(" ORDER BY l.lecCode DESC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY");
				
				pstmt=conn.prepareStatement(sb.toString());
				pstmt.setInt(1, num);
			}
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto=new LectureDTO();
				dto.setLecCode(rs.getInt("lecCode"));
				dto.setLecName(rs.getString("lecName"));
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
	
	public void updateHitCount(int num) {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE lecture l SET hitCount=hitCount+1 WHERE l.lecCode = ? ";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, num);
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
	
	public void updateLecture(LectureDTO dto) {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE lecture l SET l.lecName=?, l.lecStartDate=?, l.lecEndDate=?, l.lecLimit=?, l.lecIntro=? ";
			sql += " WHERE l.lecCode=? AND l.userId=? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getLecName());
			pstmt.setString(2, dto.getLecStartDate());
			pstmt.setString(3,  dto.getLecEndDate());
			pstmt.setInt(4, dto.getLecLimit());
			pstmt.setString(5, dto.getLecIntro());
			pstmt.setInt(6, dto.getLecCode());
			pstmt.setString(7, dto.getUserId());
			
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
	
	public void deleteLecture(int num, String userId) {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			if(userId.equals("admin")) {
				sql="DELETE FROM lecture l WHERE l.lecCode = ? ";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, num);
				
				pstmt.executeUpdate();
			} else {
				sql="DELETE FROM lecture l WHERE l.lecCode = ? AND l.userId = ? ";
			
				pstmt=conn.prepareStatement(sql);
			
				pstmt.setInt(1, num);
				pstmt.setString(2, userId);
				
				pstmt.executeUpdate();
			}
			
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
	
	public List<AcademyDTO> listAcademy() {
		List<AcademyDTO> list = new ArrayList<AcademyDTO>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql = "SELECT acaNum, acaName FROM academy";
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				AcademyDTO dto = new AcademyDTO();
				dto.setAcaNum(rs.getInt("acaNum"));
				dto.setAcaName(rs.getString("acaName"));
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
	
}
