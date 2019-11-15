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
         sql = "INSERT INTO review(renum, leccode, userId, rate, content, created)"
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
   // lecture테이블 읽기 (강좌명 jsp에 넣기 위함)
   public List<ReviewDTO> listLecture(int acaNum){
      List<ReviewDTO> list = new ArrayList<ReviewDTO>();
      PreparedStatement pstmt=null;
      ResultSet rs=null;
      String sql;
      
      try {
         sql = "SELECT lecname, lecCode from lecture where acaNum = ? ";
         pstmt=conn.prepareStatement(sql);
         
         pstmt.setInt(1, acaNum);
         
         rs=pstmt.executeQuery();
         while(rs.next()) {
            ReviewDTO dto=new ReviewDTO();
            dto.setLecName(rs.getString("lecName"));
            dto.setLecCode(rs.getInt("lecCode"));
            
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
   
   public int deleteReview(int reNum) { // 강의 리뷰 삭제(관리자만)
      int result = 0;
      PreparedStatement pstmt = null;
      String sql;
      
      try {
         sql= "DELETE FROM REVIEW WHERE reNum = ? ";
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, reNum);
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
   
   
      // 게시물 마다 댓글 리스트
      public List<ReviewDTO> listReply(int reNum, int offset, int rows) {
         List<ReviewDTO> list=new ArrayList<>();
         PreparedStatement pstmt=null;
         ResultSet rs=null;
         StringBuffer sb=new StringBuffer();
         
         try {
            sb.append("SELECT r.replyNum, m.userId, m.userName, r.content, r.answer, r.created, r.rate, r.reNum ");
            sb.append(" FROM reviewreply r ");
            sb.append("   JOIN member m ON r.userId = m.userId ");
            sb.append("   WHERE renum = ? ");
            sb.append("   order by replynum desc ");
            sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
            
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, reNum);
            pstmt.setInt(2, offset);
            pstmt.setInt(3, rows);

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
               dto.setReNum(rs.getInt("reNum"));
               
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

   
      public int updateReview(ReviewDTO dto, String mode) {
         System.out.println(dto.toString());
         int result = 0;
         PreparedStatement pstmt = null;
         String sql;
         
       //  sql="UPDATE review SET lecCode=?,content=?,created=sysdate WHERE renum=?";
         sql="UPDATE review SET lecCode = ?, content=? WHERE renum=?";
         try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, dto.getLecCode());
            pstmt.setString(2, dto.getContent());
            pstmt.setInt(3, dto.getReNum());
         
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
}
