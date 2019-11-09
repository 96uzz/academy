package com.notice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class NoticeDAO {
	private Connection conn=DBConn.getConnection();
	
	public void insertNotice(NoticeDTO dto) {
		PreparedStatement pstmt=null;
		StringBuilder sb=new StringBuilder();
		
		try {
			sb.append("INSERT INTO notice(noticeNum, notice, userId, subject, content, ");
			sb.append(" saveFilename, originalFilename, filesize) ");
			sb.append(" VALUES(notice_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?) ");
			pstmt=conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1, dto.getNotice());
			pstmt.setString(2, dto.getUserId());
			pstmt.setString(3, dto.getSubject());
			pstmt.setString(4, dto.getContent());
			pstmt.setString(5, dto.getSaveFilename());
			pstmt.setString(6, dto.getOriginalFilename());
			pstmt.setLong(7, dto.getFilesize());
			
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
	
	// 공지글 리스트
	public List<NoticeDTO> listNotice() {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuilder sb=new StringBuilder();
		List<NoticeDTO> list=new ArrayList<NoticeDTO>();
		
		try {
			sb.append("SELECT noticeNum, subject, hitCount, saveFilename, created FROM notice ");
			
			pstmt=conn.prepareStatement(sb.toString());
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				NoticeDTO dto=new NoticeDTO();
				dto.setNoticeNum(rs.getInt("noticeNum"));
				dto.setSubject(rs.getString("subject"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setSaveFilename(rs.getString("saveFilename"));
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
	
	public List<NoticeDTO> listNotice(int offset, int rows) {
		List<NoticeDTO> list = new ArrayList<NoticeDTO>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuilder sb=new StringBuilder();
		
		try {
			sb.append("SELECT noticeNum, n.userId, subject, hitCount, saveFilename, created ");
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
				dto.setUserId(rs.getString("userId"));
				dto.setSubject(rs.getString("subject"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setSaveFilename(rs.getString("saveFilename"));
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
			sb.append("SELECT noticeNum, n.userId, subject, hitCount, saveFilename, created ");
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
				dto.setSaveFilename(rs.getString("saveFilename"));
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
	
	
	
	public NoticeDTO readNotice(int noticeNum) {
		NoticeDTO dto = null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuilder sb=new StringBuilder();
		
		try {
			sb.append("SELECT noticeNum, n.userId, subject, hitCount, content, saveFilename, originalFilename, created, filesize, notice ");
			sb.append(" FROM notice n JOIN member m ON n.userId=m.userId");
			sb.append(" WHERE noticeNum = ? ");
			
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setInt(1, noticeNum);
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto=new NoticeDTO();
				
				dto.setNoticeNum(rs.getInt("noticeNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setSubject(rs.getString("subject"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setContent(rs.getString("content"));
				dto.setSaveFilename(rs.getString("saveFilename"));
				dto.setOriginalFilename(rs.getString("originalFilename"));
				dto.setCreated(rs.getString("created"));
				dto.setFilesize(rs.getLong("filesize"));
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
	public NoticeDTO preReadNotice(int noticeNum, String condition, String keyword) {
		NoticeDTO dto=null;

        PreparedStatement pstmt=null;
        ResultSet rs=null;
        StringBuffer sb = new StringBuffer();

        try {
            if(keyword.length() != 0) {
                sb.append("SELECT noticeNum, subject FROM notice n JOIN member m ON n.userId=m.userId  ");
                if(condition.equalsIgnoreCase("created")) {
                	keyword=keyword.replaceAll("-", "");
                	sb.append(" WHERE (TO_CHAR(created, 'YYYYMMDD') = ?)  ");
                } else {
                	sb.append(" WHERE (INSTR(" + condition + ", ?) >= 1)  ");
                }
                sb.append("         AND (noticeNum > ? )  ");
                sb.append(" ORDER BY noticeNum ASC  ");
                sb.append(" FETCH  FIRST  1  ROWS  ONLY");

                pstmt=conn.prepareStatement(sb.toString());
                pstmt.setString(1, keyword);
                pstmt.setInt(2, noticeNum);
			} else {
                sb.append("SELECT noticeNum, subject FROM notice n JOIN member m ON n.userId=m.userId  ");                
                sb.append(" WHERE noticeNum > ?  ");
                sb.append(" ORDER BY noticeNum ASC  ");
                sb.append(" FETCH  FIRST  1  ROWS  ONLY");

                pstmt=conn.prepareStatement(sb.toString());
                pstmt.setInt(1, noticeNum);
			}

            rs=pstmt.executeQuery();

            if(rs.next()) {
                dto=new NoticeDTO();
                dto.setNoticeNum(rs.getInt("noticeNum"));
                dto.setSubject(rs.getString("subject"));
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
    
        return dto;
	}
	
	// 다음글
	public NoticeDTO nextReadNotice(int noticeNum, String condition, String keyword) {
		NoticeDTO dto=null;

        PreparedStatement pstmt=null;
        ResultSet rs=null;
        StringBuffer sb = new StringBuffer();

        try {
            if(keyword.length() != 0) {
                sb.append("SELECT noticeNum, subject FROM notice n JOIN member m ON n.userId=m.userId  ");
                if(condition.equalsIgnoreCase("created")) {
                	keyword=keyword.replaceAll("-", "");
                	sb.append(" WHERE (TO_CHAR(created, 'YYYYMMDD') = ?)  ");
                } else {
                	sb.append(" WHERE (INSTR(" + condition + ", ?) >= 1)  ");
                }
                sb.append("         AND (noticeNum < ? )  ");
                sb.append(" ORDER BY noticeNum DESC  ");
                sb.append(" FETCH  FIRST  1  ROWS  ONLY");

                pstmt=conn.prepareStatement(sb.toString());
                pstmt.setString(1, keyword);
                pstmt.setInt(2, noticeNum);
			} else {
                sb.append("SELECT noticeNum, subject FROM notice n JOIN member m ON n.userId=m.userId  ");                
                sb.append(" WHERE noticeNum < ?  ");
                sb.append(" ORDER BY noticeNum DESC  ");
                sb.append(" FETCH  FIRST  1  ROWS  ONLY");

                pstmt=conn.prepareStatement(sb.toString());
                pstmt.setInt(1, noticeNum);
			}

            rs=pstmt.executeQuery();

            if(rs.next()) {
                dto=new NoticeDTO();
                dto.setNoticeNum(rs.getInt("noticeNum"));
                dto.setSubject(rs.getString("subject"));
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
    
        return dto;
	}
	
	public void updateHitCount(int noticeNum) {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE notice SET hitCount=hitCount+1 WHERE noticeNum=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, noticeNum);
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
	}
	
	public void updateNotice(NoticeDTO dto) {
		PreparedStatement pstmt = null;
		StringBuilder sb=new StringBuilder();
		
		try {
			sb.append("UPDATE notice SET notice=?, subject=?, content=?, saveFilename=?, originalFilename=?, filesize=? ");
			sb.append(" WHERE noticeNum=? AND userId=?");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1, dto.getNotice());
			pstmt.setString(2, dto.getSubject());
			pstmt.setString(3, dto.getContent());
			pstmt.setString(4, dto.getSaveFilename());
			pstmt.setString(5, dto.getOriginalFilename());
			pstmt.setLong(6, dto.getFilesize());
			pstmt.setInt(7, dto.getNoticeNum());
			pstmt.setString(8, dto.getUserId());
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
	}
	
	public void deleteNotice(int noticeNum, String userId) {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			if(userId.equals("admin")) {
				sql="DELETE FROM notice WHERE noticeNum = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, noticeNum);
			} else {
				sql="DELETE FROM notice WHERE noticeNum = ? AND userId=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, noticeNum);
				pstmt.setString(2, userId);
			}
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
	}
}
