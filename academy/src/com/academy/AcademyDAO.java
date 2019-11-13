
package com.academy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;


public class AcademyDAO {
	private Connection conn=DBConn.getConnection();
	
	public int insertAcademy(AcademyDTO dto) {
		int result=0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql="INSERT INTO academy(acaNum, acaName, acaWeb, acaTel, acaDiv, acaIntro, acaAddress, userId) VALUES (aca_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, dto.getAcaName());
			pstmt.setString(2, dto.getAcaWeb());
			pstmt.setString(3, dto.getAcaTel());
			pstmt.setString(4, dto.getAcaDiv());
			pstmt.setString(5, dto.getAcaIntro());
			pstmt.setString(6, dto.getAcaAddress());
			pstmt.setString(7, dto.getUserId());
			
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
			sql="SELECT NVL(COUNT(*), 0) FROM academy ";
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
			sql="SELECT NVL(COUNT(*), 0) FROM academy ";
			if(condition.equalsIgnoreCase("acaName")) {
				sql += " WHERE INSTR(acaName, ?) >= 1 ";
			} else if(condition.equalsIgnoreCase("acaDiv")) {
				sql += " WHERE INSTR(acaDiv, ?) >= 1 ";
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
	
	public List<AcademyDTO> listAcademy() {
		List<AcademyDTO> list = new ArrayList<AcademyDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("SELECT acaNum, acaName, acaTel, acaAddress, acaDiv, ");
			sb.append("  a.userId, TO_CHAR(created, 'YYYY-MM-DD') created ");
			sb.append("  FROM academy a JOIN member m ON a.userId=m.userId ");
			sb.append("  ORDER BY acaNum DESC ");
			
			pstmt=conn.prepareStatement(sb.toString());
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				AcademyDTO dto = new AcademyDTO();
				dto.setAcaNum(rs.getInt("acaNum"));
				dto.setAcaName(rs.getString("acaName"));
				dto.setAcaTel(rs.getString("acaTel"));
				dto.setAcaAddress(rs.getString("acaAddress"));
				dto.setAcaDiv(rs.getString("acaDiv"));
				dto.setUserId(rs.getString("userId"));
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
	
	public List<AcademyDTO> listAcademy(int offset, int rows) {
		List<AcademyDTO> list = new ArrayList<AcademyDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("SELECT acaNum, acaName, acaTel, acaAddress, acaDiv, ");
			sb.append("  TO_CHAR(created, 'YYYY-MM-DD') created, a.userId ");
			sb.append("  FROM academy a JOIN member m ON a.userId=m.userId ");
			sb.append("  ORDER BY acaNum DESC ");
			sb.append("  OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
			
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setInt(1, offset);
			pstmt.setInt(2, rows);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				AcademyDTO dto = new AcademyDTO();
				dto.setAcaNum(rs.getInt("acaNum"));
				dto.setAcaName(rs.getString("acaName"));
				dto.setAcaTel(rs.getString("acaTel"));
				dto.setAcaAddress(rs.getString("acaAddress"));
				dto.setAcaDiv(rs.getString("acaDiv"));
				dto.setCreated(rs.getString("created"));
				dto.setUserId(rs.getString("userId"));
				
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
	
	public List<AcademyDTO> listAcademy(int offset, int rows, String condition, String keyword) {
		List<AcademyDTO> list = new ArrayList<AcademyDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("SELECT acaNum, acaName, acaTel, acaAddress, acaDiv, ");
			sb.append(" TO_CHAR(created, 'YYYY-MM-DD') created, a.userId ");
			sb.append(" FROM academy a JOIN member m ON a.userId=m.userId ");
			if(condition.equalsIgnoreCase("acaName")) {
				sb.append(" WHERE INSTR(acaName, ?) >= 1 ");
			} else {
				sb.append(" WHERE INSTR("+condition+", ?) >= 1 ");
			}
			sb.append("ORDER BY acaNum DESC ");
			sb.append("OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, keyword);
			pstmt.setInt(2, offset);
			pstmt.setInt(3, rows);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				AcademyDTO dto = new AcademyDTO();
				dto.setAcaNum(rs.getInt("acaNum"));
				dto.setAcaName(rs.getString("acaName"));
				dto.setAcaTel(rs.getString("acaTel"));
				dto.setAcaAddress(rs.getString("acaAddress"));
				dto.setAcaDiv(rs.getString("acaDiv"));
				dto.setCreated(rs.getString("created"));
				dto.setUserId(rs.getString("userId"));
				
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
	
	public AcademyDTO readAcademy(int num) {
		AcademyDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("SELECT acaNum, acaName, acaDiv, acaAddress, acaTel, ");
			sb.append(" a.userId, acaWeb, acaIntro, created, hitCount FROM academy a ");
			sb.append(" JOIN member m ON a.userId = m.userId ");
			sb.append(" WHERE acaNum = ? ");
			
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setInt(1, num);
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto=new AcademyDTO();
				dto.setAcaNum(rs.getInt("acaNum"));
				dto.setAcaName(rs.getString("acaName"));
				dto.setAcaDiv(rs.getString("acaDiv"));
				dto.setAcaAddress(rs.getString("acaAddress"));
				dto.setAcaTel(rs.getString("acaTel"));
				dto.setUserId(rs.getString("userId"));
				dto.setAcaWeb(rs.getString("acaWeb"));
				dto.setAcaIntro(rs.getString("acaIntro"));
				dto.setCreated(rs.getString("created"));
				dto.setHitCount(rs.getInt("hitCount"));
				
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
	public AcademyDTO preReadAcademy(int num, String condition, String keyword) {
		AcademyDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			if(keyword.length() !=0) {
				sb.append("SELECT acaNum, acaName FROM academy a JOIN member m ON a.userId=m.userId ");
				if(condition.equalsIgnoreCase("created")) {
					keyword=keyword.replaceAll("-", "");
					sb.append(" WHERE (TO_CHAR(created, 'YYYYMMDD') = ?) ");
				} else {
					sb.append(" WHERE (INSTR(" + condition + ", ?) >= 1) ");
				}
				sb.append("      AND (acaNum > ? )  ");
				sb.append(" ORDER BY acaNum ASC  ");
				sb.append(" FETCH FIRST 1 ROWS ONLY ");
				
				pstmt=conn.prepareStatement(sb.toString());
				pstmt.setString(1, keyword);
				pstmt.setInt(2, num);
			} else {
				sb.append("SELECT acaNum, acaName FROM academy a JOIN member m ON a.userId=m.userId ");
				sb.append(" WHERE acaNum > ?  ");
				sb.append(" ORDER BY acaNum ASC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY");
				
				pstmt=conn.prepareStatement(sb.toString());
				pstmt.setInt(1, num);
			}
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto=new AcademyDTO();
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
	
	// 다음글
	public AcademyDTO nextReadAcademy(int num, String condition, String keyword) {
		AcademyDTO dto=null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			if(keyword.length() !=0) {
				sb.append("SELECT acaNum, acaName FROM academy a JOIN member m ON a.userId=m.userId ");
				if(condition.equalsIgnoreCase("created")) {
					keyword=keyword.replaceAll("-", "");
					sb.append(" WHERE (TO_CHAR(created, 'YYYYMMDD') = ?) ");
				} else {
					sb.append(" WHERE (INSTR(" + condition + ", ?) >= 1) ");
				}
				sb.append("      AND (acaNum < ? ) ");
				sb.append(" ORDER BY acaNum DESC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY");
				
				pstmt=conn.prepareStatement(sb.toString());
				pstmt.setString(1, keyword);
				pstmt.setInt(2, num);
			} else {
				sb.append("SELECT acaNum, acaName FROM academy a JOIN member m ON a.userId=m.userId ");
				sb.append(" WHERE acaNum < ? ");
				sb.append(" ORDER BY acaNum DESC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY");
				
				pstmt=conn.prepareStatement(sb.toString());
				pstmt.setInt(1, num);
			}
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto=new AcademyDTO();
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
	public void updateHitCount(int num) {
		PreparedStatement pstmt =null;
		String sql;
		
		try {
			sql = "UPDATE academy SET hitCount=hitCount+1 WHERE acaNum = ? ";
			
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
	
	public void updateAcademy(AcademyDTO dto) {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE academy SET acaName=?, acaDiv=?, acaIntro=?, acaAddress=?, acaWeb=?, acaTel=? ";
			sql += " WHERE acaNum=? AND userId=? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getAcaName());
			pstmt.setString(2, dto.getAcaDiv());
			pstmt.setString(3, dto.getAcaIntro());
			pstmt.setString(4, dto.getAcaAddress());
			pstmt.setString(5, dto.getAcaWeb());
			pstmt.setString(6, dto.getAcaTel());
			pstmt.setInt(7, dto.getAcaNum());
			pstmt.setString(8, dto.getUserId());
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
	
	public void deleteAcademy(int num, String userId) {
		PreparedStatement pstmt =null;
		String sql;
		
		try {
			if(userId.equals("admin")) {
				sql="DELETE FROM academy WHERE acaNum = ? ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, num);
				pstmt.executeUpdate();
			} else {
				sql="DELETE FROM academy WHERE acaNum = ? AND userId = ? ";
			
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
}
