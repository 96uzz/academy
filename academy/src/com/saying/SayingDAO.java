package com.saying;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.util.DBConn;

public class SayingDAO {
	private Connection conn = DBConn.getConnection();
	
	public SayingDTO readSaying(int num) {
		SayingDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		
		try {
			sb.append("select num, wisesaying ");
			sb.append(" from wisesaying ");
			sb.append(" where num = ? ");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new SayingDTO();
				
				dto.setNum(rs.getInt("num"));
				dto.setWiseSaying(rs.getString("wisesaying"));
				
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
}
