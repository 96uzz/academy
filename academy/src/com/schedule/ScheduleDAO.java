package com.schedule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class ScheduleDAO {
	private Connection conn = DBConn.getConnection();

	public List<ScheduleDTO> listMonth(String startDay, String endDay) {
		List<ScheduleDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		try {
			sb.append("SELECT lecNum, lecName, acaName, lecStartDate, lecEndDate, lecLimit, lecIntro, l.acaNum ");
			sb.append("  FROM lecture l ");
			sb.append("  	JOIN academy a on l.acaNum=a.acaNum ");
			sb.append("  WHERE  ");
			sb.append("     ( ");
			sb.append("        ( ");
			sb.append("           TO_DATE(lecStartDate, 'YYYYMMDD') >= TO_DATE(?, 'YYYYMMDD') ");
			sb.append("               AND TO_DATE(lecStartDate, 'YYYYMMDD') <= TO_DATE(?, 'YYYYMMDD')  ");
			sb.append("               OR TO_DATE(lecEndDate, 'YYYYMMDD') <= TO_DATE(?, 'YYYYMMDD')  ");
			sb.append("         )");         
			sb.append("    ) ");
			sb.append("  ORDER BY lecStartDate ASC, lecNum DESC ");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, startDay);
			pstmt.setString(2, endDay);
			pstmt.setString(3, endDay);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				ScheduleDTO dto = new ScheduleDTO();
				dto.setNum(rs.getInt("lecNum"));
				dto.setLecName(rs.getString("lecName"));
				dto.setAcaName(rs.getString("acaName"));
				dto.setSday(rs.getString("lecStartDate"));
				dto.setEday(rs.getString("lecEndDate"));
				dto.setLecLimit(rs.getInt("lecLimit"));
				dto.setMemo(rs.getString("lecIntro"));
				dto.setAcaNum(rs.getString("acaNum"));
				
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}

		return list;
	}

	public int insertSchedule(ScheduleDTO dto) {
		int result = 0;
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();

		try {
			sb.append("INSERT INTO lecture( ");
			sb.append(" lecCode, lecName, lecNum, lecStartDate,lecEndDate,lecLimit,acaNum,lecIntro,userId) ");
			sb.append(" VALUES(lec_seq.nextval, ?,?,?,?,?,?,?,'admin') ");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, dto.getLecName());
			pstmt.setInt(2, dto.getNum());
			pstmt.setString(3, dto.getSday());
			pstmt.setString(4, dto.getEday());
			pstmt.setInt(5, dto.getLecLimit());
			pstmt.setString(6, dto.getAcaNum());
			pstmt.setString(7, dto.getMemo());

			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return result;
	}

	public List<ScheduleDTO> listDay(String date, String userId) {
		List<ScheduleDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		try {
			sb.append("SELECT lecNum, lecName, acaName, lecStartDate, lecEndDate, lecLimit, lecIntro, l.acaNum ");
			sb.append("  FROM lecture l ");
			sb.append("  	JOIN academy a on l.acaNum=a.acaNum ");
			sb.append("  WHERE  ");
			sb.append("     ( ");
			sb.append("        ( ");
			sb.append("           TO_DATE(lecStartDate, 'YYYYMMDD') = TO_DATE(?, 'YYYYMMDD') ");
			sb.append("              OR (lecEndDate IS NOT NULL AND TO_DATE(lecStartDate, 'YYYYMMDD') <= TO_DATE(?, 'YYYYMMDD') AND TO_DATE(lecEndDate, 'YYYYMMDD') >= TO_DATE(?, 'YYYYMMDD'))   ");
			sb.append("         )");         
			sb.append("    ) ");
			sb.append("  ORDER BY lecNum DESC ");
			

			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setString(1, date);
			pstmt.setString(2, date);
			pstmt.setString(3, date);

			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				ScheduleDTO dto = new ScheduleDTO();
				dto.setNum(rs.getInt("lecNum"));
				dto.setLecName(rs.getString("lecName"));
				dto.setAcaName(rs.getString("acaName"));
				dto.setSday(rs.getString("lecStartDate"));
				dto.setEday(rs.getString("lecEndDate"));
				dto.setLecLimit(rs.getInt("lecLimit"));
				dto.setMemo(rs.getString("lecIntro"));
				dto.setAcaNum(rs.getString("acaNum"));
			
				list.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}

		return list;
	}

	public ScheduleDTO readSchedule(int num) {
		ScheduleDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		try {
			sb.append("SELECT lecNum, lecName, acaName, lecStartDate, lecEndDate, lecLimit, lecIntro, l.acaNum ");
			sb.append("  FROM lecture l ");
			sb.append("  	JOIN academy a on l.acaNum=a.acaNum ");
			sb.append("  WHERE  lecNum=?");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, num);

			//String period;
			String s;
			rs = pstmt.executeQuery();
			if (rs.next()) {
				dto = new ScheduleDTO();
				dto.setNum(rs.getInt("lecNum"));
				dto.setLecName(rs.getString("lecName"));
				dto.setAcaName(rs.getString("acaName"));
				
				dto.setSday(rs.getString("lecStartDate"));
				s = dto.getSday().substring(0, 4) + "-" + dto.getSday().substring(4, 6) + "-"
						+ dto.getSday().substring(6);
				dto.setSday(s);
				
				dto.setEday(rs.getString("lecEndDate"));
				if (dto.getEday() != null && dto.getEday().length() == 8) {
					s = dto.getEday().substring(0, 4) + "-" + dto.getEday().substring(4, 6) + "-"
							+ dto.getEday().substring(6);
					dto.setEday(s);
				}
				dto.setLecLimit(rs.getInt("lecLimit"));
				dto.setMemo(rs.getString("lecIntro").replace("\r\n", "<br>"));

				dto.setAcaNum(rs.getString("acaNum"));
				
		
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}

		return dto;
	}

	public int updateSchedule(ScheduleDTO dto) {
		int result = 0;
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();

		try {
			sb.append("UPDATE lecture SET ");
			sb.append(" lecName = ?, lecStartDate =?, lecEndDate = ?,  ");
			sb.append(" lecLimit = ?, acaNum = ?, lecIntro = ? ");
			sb.append(" where lecNum = ? ");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setString(1, dto.getLecName());
			pstmt.setString(2, dto.getSday());
			pstmt.setString(3, dto.getEday());
			pstmt.setInt(4, dto.getLecLimit());
			pstmt.setString(5, dto.getAcaNum());
			pstmt.setString(6, dto.getMemo());
			pstmt.setInt(7, dto.getNum());


			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return result;
	}

	public int deleteSchedule(int num) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "DELETE FROM lecture WHERE lecNum=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);

			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return result;
	}
	
}
