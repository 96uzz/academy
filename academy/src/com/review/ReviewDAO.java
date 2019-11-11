package com.review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class ReviewDAO {
private Connection conn=DBConn.getConnection();
		
	public int dataCount() {
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="SELECT NVL(COUNT(*), 0) FROM review";
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next())
				result=rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
				
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
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
        //검색(강의명, 분야, 학원명)
        
        try {
        	if(condition.equals("lecname")) {
        		keyword=keyword.replaceAll("-", "");
        		sql="SELECT NVL(COUNT(*), 0) FROM review r JOIN lecture l ON r.lecname= l.lecname WHERE INSTR(lecname, ?) = 1 ";
        	} else if(condition.equals("acadiv")) {
        		sql="SELECT NVL(COUNT(*), 0) FROM review r JOIN academy a ON r.userId=a.userId WHERE INSTR(acadiv, ?) = 1 ";
        	} else if(condition.equals("acaname")) {
        		sql="SELECT NVL(COUNT(*), 0) FROM review r JOIN academy a ON r.userId=a.userId WHERE INSTR(acaname, ?) = 1 ";
        	}else {
        		sql="SELECT NVL(COUNT(*), 0) FROM review r JOIN member m ON b.userId=m.userId WHERE INSTR(" + condition + ", ?) >= 1 ";
        	}
	
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1, keyword);

            rs=pstmt.executeQuery();

            if(rs.next())
                result=rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
				
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
        
        return result;
    }
	
	public List<ReviewDTO> listLecture(int offset, int rows) {
		List<ReviewDTO> list = new ArrayList<ReviewDTO>();
		
		return list;
	}
	
	public List<ReviewDTO> listLecture(int offset, int rows, String condition, String keyword) {
		List<ReviewDTO> list = new ArrayList<ReviewDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		StringBuffer sb = new StringBuffer();
		
		try {
			sb.append("SELECT renum, leccode, m.userId, ");
			sb.append("       rate, content, created, a.acaName ");
			sb.append(" FROM review r  ");
			sb.append(" JOIN member m ON r.userId=m.userId  ");
			sb.append(" JOIN lecture l ON l.leccode=r.leccode  ");
			sb.append(" JOIN academy a ON a.userId=r.userId ");
			sb.append(" ORDER BY groupNum DESC, orderNo ASC  ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
		
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, offset);
			pstmt.setInt(2, rows);
			
			rs=pstmt.executeQuery();
			
			while (rs.next()) {
				ReviewDTO dto=new ReviewDTO();
				dto.setReNum(rs.getInt("renum"));
				dto.setLecCode(rs.getInt("leccode"));
				dto.setUserId(rs.getString("userId"));
				dto.setRate(rs.getDouble("rate"));
				dto.setContent(rs.getString("content"));
				dto.setCreated(rs.getString("created"));
				dto.setAcaName(rs.getString("acaName"));
				
				list.add(dto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
				
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		
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
