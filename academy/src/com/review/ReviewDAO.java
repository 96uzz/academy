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
	
	// 리뷰 데이터 추가 ( 관리자용)
	public int insertBoard(ReviewDTO dto, String mode) {
		int result=0;
		PreparedStatement pstmt=null;
		String sql;
		
		
		try {
			sql = "INSERT INTO review(renum, leccode, userId, rate,content, created)"
					+ "  VALUES (review_seq.nextval, ?, ?, ?, ?, sysdate)";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getLecCode());
			pstmt.setString(2, dto.getUserId());
			pstmt.setString(3, dto.getRate());
			pstmt.setString(4, dto.getContent());
			
			result=pstmt.executeUpdate();
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
		
		return result;
	}
	
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
	
	// 게시물 리스트
	public List<ReviewDTO> listLecture(int offset, int rows) {
		List<ReviewDTO> list = new ArrayList<ReviewDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		StringBuffer sb = new StringBuffer();
		
		try {
			sb.append("SELECT r.renum, l.lecname, a.acaName, l.lecIntro, (select Round(avg(rate), 2) from reviewreply rr where r.renum= rr.renum) rate ");
			sb.append(" FROM review r  ");
			sb.append(" JOIN lecture l ON l.leccode=r.leccode  ");
			sb.append(" JOIN academy a ON l.acaNum = a.acaNum");
			sb.append(" ORDER BY renum DESC");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, offset);
			pstmt.setInt(2, rows);
		
			
			rs=pstmt.executeQuery();
			
			while (rs.next()) {
				ReviewDTO dto=new ReviewDTO();
				dto.setReNum(rs.getInt("renum"));
				dto.setLecName(rs.getString("lecName"));
				dto.setAcaName(rs.getString("acaName"));
				dto.setLecIntro(rs.getString("lecIntro"));
				dto.setRate(rs.getString("rate"));
				
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

	public List<ReviewDTO> listLecture(int offset, int rows, String condition, String keyword) {
		List<ReviewDTO> list = new ArrayList<ReviewDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		StringBuffer sb = new StringBuffer();
		
		try {
			sb.append("SELECT r.renum, l.lecname, a.acaName, rate, content, r.created ");
			sb.append(" FROM review r  ");
			sb.append(" JOIN lecture l ON l.leccode=r.leccode  ");
			sb.append(" JOIN academy a ON l.acaNum = a.acaNum");
			sb.append(" ORDER BY renum DESC");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
		
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, offset);
			pstmt.setInt(2, rows);
			
			rs=pstmt.executeQuery();
			
			while (rs.next()) {
				ReviewDTO dto=new ReviewDTO();
				dto.setReNum(rs.getInt("renum"));
				dto.setLecName(rs.getString("lecname"));
				dto.setAcaName(rs.getString("acaName"));
				dto.setRate(rs.getString("rate"));
				
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
	
	public ReviewDTO readReview(int reNum) { 
		ReviewDTO dto =null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			sb.append("SELECT r.renum, l.leccode, r.userId, (select ROUND(AVG(RATE),2) from reviewreply rr where r.renum = rr.renum) as rate, r.content, r.created, l.lecname  ");
			sb.append(" FROM review r JOIN lecture l on r.lecCode = l.lecCode  ");
			sb.append(" JOIN academy a on l.acaNum = a.acaNum ");
			sb.append(" WHERE renum = ? ");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, reNum);

			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto=new ReviewDTO();
				dto.setReNum(rs.getInt("renum"));
				dto.setLecCode(rs.getInt("leccode"));
				dto.setUserId(rs.getString("userid"));
				dto.setRate(rs.getString("rate"));
				dto.setContent(rs.getString("content"));
				dto.setCreated(rs.getString("created"));
				dto.setLecName(rs.getString("lecname"));
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
	//lecture테이블 읽기 (강좌명 jsp에 넣기 위함)
	public List<ReviewDTO> listLecture(ReviewDTO dto){
		List<ReviewDTO> list = new ArrayList<ReviewDTO>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql = "SELECT lecname, acaNum from lecture where acanum = ? ";
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1, dto.getAcaNum());
			
			rs=pstmt.executeQuery();
			while(rs.next()) {
				dto.setLecName(rs.getString("lecname"));
				dto.setAcaNum(rs.getInt("acaNum"));
				
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
	
	
	//academy테이블 읽기 (학원명 jsp에 넣기 위함)
		public List<ReviewDTO> listAcademy(){
			List<ReviewDTO> list = new ArrayList<ReviewDTO>();
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			String sql;
			
			try {
				sql = "SELECT acaNum, acaName FROM academy";
				pstmt=conn.prepareStatement(sql);
				rs=pstmt.executeQuery();
				while(rs.next()) {
					ReviewDTO dto = new ReviewDTO();
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
		//reviewreply 테이블 읽기 (rate를 jsp에 넣기 위함)
		public List<ReviewDTO> listReviewReply(ReviewDTO dto){
			List<ReviewDTO> list = new ArrayList<ReviewDTO>();
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			String sql;
			
			try {
				sql = "SELECT (select ROUND(AVG(RATE),2) from reviewreply rr where r.renum = rr.renum) rate from lecture where acanum = ? ";
				pstmt=conn.prepareStatement(sql);
				
				pstmt.setInt(1, dto.getAcaNum());
				
				rs=pstmt.executeQuery();
				while(rs.next()) {
					dto.setLecName(rs.getString("lecname"));
					dto.setAcaNum(rs.getInt("acaNum"));
					
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
	
	
    // 이전글
	public ReviewDTO preReadLecture(int renum, String condition, String keyword) {
		ReviewDTO dto = null;
		PreparedStatement pstmt=null;
	    ResultSet rs=null;
	    StringBuffer sb = new StringBuffer();
	    //검색(강의명, 분야, 학원명)
	    try {
            if(keyword!=null && keyword.length() != 0) {
                sb.append("SELECT renum, a.acaname FROM review r JOIN lecture l ON r.lecCode = l.lecCode join academy a on l.acanum = a.acanum ");
                if(condition.equals("lecname")) {
                	keyword=keyword.replaceAll("-", "");
                    sb.append(" WHERE ( INSTR(lecname, ?) = 1)  ");
                } else if(condition.equals("acadiv")) {
                	keyword=keyword.replaceAll("-", "");
                    sb.append(" WHERE (INSTR(acadiv, ?) = 1) ");
                } else if(condition.equals("acaName")) {
                	keyword=keyword.replaceAll("-", "");
                    sb.append(" WHERE (INSTR(acaName, ?) = 1) ");
                } else {
                    sb.append(" WHERE ( INSTR("+condition+", ?) > 0) ");
                }
                sb.append("            AND (renum > ? ) ");
                sb.append(" ORDER BY renum ASC ");
                sb.append(" FETCH  FIRST  1  ROWS  ONLY ");

                pstmt=conn.prepareStatement(sb.toString());
                pstmt.setString(1, keyword);
               	pstmt.setInt(2, renum);
            } else {
                sb.append("SELECT renum, a.acaname FROM review r join lecture l ON r.lecCode = l.lecCode join academy a on l.acanum = a.acanum ");
                sb.append(" WHERE renum > ? ");
                sb.append(" ORDER BY renum ASC ");
                sb.append(" FETCH  FIRST  1  ROWS  ONLY ");

                pstmt=conn.prepareStatement(sb.toString());
                pstmt.setInt(1, renum);
            }

            rs=pstmt.executeQuery();

            if(rs.next()) {
                dto=new ReviewDTO();
                dto.setReNum(rs.getInt("renum"));
                dto.setAcaName(rs.getString("acaname"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(rs!=null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
            }
                
            if(pstmt!=null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
        }
    
        return dto;
    }

    // 다음글
	public ReviewDTO nextReadLecture(int reNum, String condition, String keyword) {
		ReviewDTO dto = null;
		
		return dto;
	}
	
	public void insertReview(ReviewDTO dto) { // 강의 리뷰 등록
		
	}
	
	
	
	public void deleteReview(int reNum, String userId) { // 강의 리뷰 삭제(관리자만)
		
	}
	
	public int insertReply(ReviewDTO dto) {
		int result=0;
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql = "INSERT INTO reviewreply(replyNum, renum, userId, content, answer, created, rate) VALUES (RR_SEQ.NEXTVAL,?, ?, ?, ?,sysdate,?)";
			
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1, dto.getReNum());
			pstmt.setString(2, dto.getUserId());
			pstmt.setString(3, dto.getContent());
			pstmt.setInt(4, dto.getAnswer());
			pstmt.setString(5, dto.getRate());
			
			result=pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null)
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
		}
		
	return result;
	}
	
	// 게시물의 댓글 개수
		public int dataCountReply(int reNum) {
			int result=0;
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			String sql;
			
			try {
				sql="SELECT NVL(COUNT(*), 0) FROM reviewreply WHERE renum=? AND answer=0";
				pstmt=conn.prepareStatement(sql);
				pstmt.setInt(1, reNum);
				
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
	
	
		// 게시물 댓글 리스트
		public List<ReviewDTO> listReply(int reNum, int offset, int rows) {
			List<ReviewDTO> list=new ArrayList<>();
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			StringBuffer sb=new StringBuffer();
			
			try {
				sb.append("SELECT r.replyNum, m.userId, m.userName, r.content, r.answer, r.created, r.rate ");
				sb.append(" FROM reviewreply r ");
				sb.append("	JOIN member m ON r.userId = m.userId ");
				sb.append("	order by replynum desc ");
				sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
				
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setInt(1, offset);
				pstmt.setInt(2, rows);

				rs=pstmt.executeQuery();
				
				while(rs.next()) {
					ReviewDTO dto=new ReviewDTO();
					
					dto.setReplyNum(rs.getInt("replyNum"));
					dto.setUserId(rs.getString("userId"));
					dto.setUserName(rs.getString("userName"));
					dto.setContent(rs.getString("content"));
					dto.setAnswer(rs.getInt("answer"));
					dto.setCreated(rs.getString("created"));
					dto.setRate(rs.getString("rate"));
					
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
}
	
	

/*
	// 게시물 수정
	public int updateReview(ReviewDTO dto) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		sql="UPDATE bbs SET subject=?, content=? WHERE num=? AND userId=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getSubject());
			pstmt.setString(2, dto.getContent());
			pstmt.setInt(3, dto.getNum());
			pstmt.setString(4, dto.getUserId());
			result = pstmt.executeUpdate();
			
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
		return result;
	}
	
	// 게시물 삭제
	public int deleteBoard(int num, String userId) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			if(userId.equals("admin")) {
				sql="DELETE FROM bbs WHERE num=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, num);
				result = pstmt.executeUpdate();
			}else {
				sql="DELETE FROM bbs WHERE num=? AND userId=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, num);
				pstmt.setString(2, userId);
				result = pstmt.executeUpdate();
			}
			
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
		return result;
	}
*/
	
	
/*

	public ReplyDTO readReply(int replyNum) {
		ReplyDTO dto=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			sb.append("SELECT replyNum, num, r.userId, userName, content ,r.created ");
			sb.append(" FROM bbsReply r JOIN member1 m ON r.userId=m.userId  ");
			sb.append(" WHERE replyNum = ? ");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, replyNum);

			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto=new ReplyDTO();
				dto.setReplyNum(rs.getInt("replyNum"));
				dto.setNum(rs.getInt("num"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setContent(rs.getString("content"));
				dto.setCreated(rs.getString("created"));
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
	
	// 게시물의 댓글 삭제
	public int deleteReply(int replyNum, String userId) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		if(! userId.equals("admin")) {
			ReplyDTO dto=readReply(replyNum);
			if(dto==null || (! userId.equals(dto.getUserId())))
				return result;
		}
		
		sql="DELETE FROM bbsReply ";
		sql+="  WHERE replyNum IN  ";
		sql+="  (SELECT replyNum FROM bbsReply START WITH replyNum = ?";
		sql+="    CONNECT BY PRIOR replyNum = answer)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, replyNum);
			
			result = pstmt.executeUpdate();
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
		
		return result;
	}

	// 댓글의 답글 리스트
	public List<ReplyDTO> listReplyAnswer(int answer) {
		List<ReplyDTO> list=new ArrayList<>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			sb.append("SELECT replyNum, num, r.userId, userName, content, created, answer");
			sb.append(" FROM bbsReply r ");
			sb.append(" JOIN member1 m ON r.userId=m.userId");
			sb.append(" WHERE answer=?");
			sb.append(" ORDER BY replyNum DESC");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, answer);

			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				ReplyDTO dto=new ReplyDTO();
				
				dto.setReplyNum(rs.getInt("replyNum"));
				dto.setNum(rs.getInt("num"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setContent(rs.getString("content"));
				dto.setCreated(rs.getString("created"));
				dto.setAnswer(rs.getInt("answer"));
				
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
	
	// 댓글의 답글 개수
	public int dataCountReplyAnswer(int answer) {
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="SELECT NVL(COUNT(*), 0) FROM bbsReply WHERE answer=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, answer);
			
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
}
*/
