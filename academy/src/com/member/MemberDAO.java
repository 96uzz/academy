package com.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class MemberDAO {
	private Connection conn = DBConn.getConnection();

	public MemberDTO readMember(String userId) {
		MemberDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append(
					"select userId, userName, userPwd, to_char(birth,'YYYY-MM-DD') birth, email, tel, lecCode, zip, addr1, addr2 ");
			sb.append(" from member");
			sb.append(" where userId = ? ");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new MemberDTO();
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setUserPwd(rs.getString("userPwd"));
				dto.setBirth(rs.getString("birth"));
				dto.setEmail(rs.getString("email"));
				dto.setTel(rs.getString("tel"));
				dto.setLecCode(rs.getString("lecCode"));
				dto.setZip(rs.getString("zip"));
				dto.setAddr1(rs.getString("addr1"));
				dto.setAddr2(rs.getString("addr2"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return dto;

	}

	public void insertMember(MemberDTO dto) throws Exception {
		PreparedStatement pstmt = null;
		String sql;

		sql = "INSERT INTO member(userId, userName, userPwd, birth, email, tel, lecCode, zip, addr1, addr2 ) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, dto.getUserId());
		pstmt.setString(2, dto.getUserName());
		pstmt.setString(3, dto.getUserPwd());
		pstmt.setString(4, dto.getBirth());
		pstmt.setString(5, dto.getEmail());
		pstmt.setString(6, dto.getTel());
		pstmt.setString(7, dto.getLecCode());
		pstmt.setString(8, dto.getZip());
		pstmt.setString(9, dto.getAddr1());
		pstmt.setString(10, dto.getAddr2());

		pstmt.executeUpdate();

		try {

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
	}

	public void updateMember(MemberDTO dto) {
		PreparedStatement pstmt = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append("UPDATE member SET userName = ?, userPwd = ?, birth = ?");
			sb.append(", email = ?, tel = ?, lecCode = ?, zip = ?, addr1 = ?, addr2 = ? ");
			sb.append("WHERE userId = ?");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, dto.getUserName());
			pstmt.setString(2, dto.getUserPwd());
			pstmt.setString(3, dto.getBirth());
			pstmt.setString(4, dto.getEmail());
			pstmt.setString(5, dto.getTel());
			pstmt.setString(6, dto.getLecCode());
			pstmt.setString(7, dto.getZip());
			pstmt.setString(8, dto.getAddr1());
			pstmt.setString(9, dto.getAddr2());
			pstmt.setString(10, dto.getUserId());

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}

	}

	public void deleteMember(String userId) {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "DELETE FROM member WHERE userId = ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userId);

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();

				} catch (Exception e) {
				}
			}
		}
	}

	public List<MemberDTO> interLecList(String userId) {
		List<MemberDTO> list = new ArrayList<MemberDTO>();

		return list;

	}

	public MemberDTO takingLecList(String userId) {
		MemberDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append("SELECT acaDiv, acaName, acaAddress, a.acaNum, m.lecCode, lecName, lecStartDate, ");
			sb.append("lecEndDate, lecLimit ");
			sb.append(" FROM academy a ");
			sb.append(" INNER JOIN lecture l ON a.acaNum = l.acaNum  ");
			sb.append("INNER JOIN member m ON m.lecCode = l.lecCode WHERE m.userId = ?");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, userId);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new MemberDTO();
				dto.setAcaDiv(rs.getString("acaDiv"));
				dto.setLecName(rs.getString("lecName"));
				dto.setAcaName(rs.getString("acaName"));
				dto.setAcaAddress(rs.getString("acaAddress"));
				dto.setLecLimit(rs.getInt("lecLimit"));
				dto.setLecStartDate(rs.getString("lecStartDate"));
				dto.setLecEndDate(rs.getString("lecEndDate"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
		}
		return dto;
	}

	public String findUserId(String userName, String birth) {
		String userId = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
		sql = "SELECT userId FROM member WHERE userName = ? AND TO_CHAR(birth,'YYYYMMDD') = ?";
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, userName);
		pstmt.setString(2, birth);
				
		rs = pstmt.executeQuery();
		
		if(rs.next()) {
			userId = rs.getString("userId");
		}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
		}

		return userId;
	}

	public String findUserPwd(String userId, String email) {
		String userPwd = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
		sql = "SELECT userPwd FROM member WHERE userId= ? AND email = ?";
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, userId);
		pstmt.setString(2, email);
				
		rs = pstmt.executeQuery();
		
		if(rs.next()) {
			userPwd = rs.getString("userPwd");
		}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
		}

		return userPwd;
	}

}
