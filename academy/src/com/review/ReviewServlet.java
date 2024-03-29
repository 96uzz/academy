package com.review;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.util.MyUtil;

import net.sf.json.JSONObject;

@WebServlet("/review/*")
public class ReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	protected void forward(HttpServletRequest req, HttpServletResponse resp, String path)
			throws ServletException, IOException {
		
		RequestDispatcher rd = req.getRequestDispatcher(path);
		rd.forward(req, resp);
	}

	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");

		// HttpSession session = req.getSession();
		// SessionInfo info = (SessionInfo) session.getAttribute("member");
		String uri = req.getRequestURI();

		//if (info != null) {
			if (uri.indexOf("list.do") != -1) {
				list(req, resp);
			} else if (uri.indexOf("review.do") != -1) {
				review(req, resp);
			} else if (uri.indexOf("createdReview.do") != -1) {
				createdForm(req, resp);
			} else if (uri.indexOf("created_ok.do") != -1) {
				createdSubmit(req, resp);
			} else if (uri.indexOf("insertReply.do") != -1) { //댓글 추가
				insertReply(req, resp);
			}else if (uri.indexOf("listReply.do") != -1) { // 댓글 리스트
				listReply(req, resp);
			} else if(uri.indexOf("listLectureChange.do")!=-1) {
				listLectureChange(req, resp);
			} else if (uri.indexOf("deleteReview.do") != -1) {
				deleteReview(req, resp);
			} else if (uri.indexOf("update.do") != -1) {
				updateForm(req, resp);
			} else if (uri.indexOf("update_ok.do") != -1) {
				updateSubmit(req, resp);
			} 
		/*	} 
			 else if (uri.indexOf("deleteReply.do") != -1) {
				// 댓글 삭제
				deleteReply(req, resp);
			} 
			
		
		
			
		if (info == null) {
			if (uri.indexOf("list.do") != -1) {
				list(req, resp);

			} else {
				forward(req, resp, "/WEB-INF/views/member/login.jsp");
				return;
			}
		*/
			//}
		}
	

	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ReviewDAO dao = new ReviewDAO();
		MyUtil util = new MyUtil();
		String cp = req.getContextPath();

		String page = req.getParameter("page");
		int current_page = 1;
		if (page != null) {
			current_page = Integer.parseInt(page);
		}

		int rows = 10;
		
		String srows = req.getParameter("rows");
		if (srows != null) {
			rows = Integer.parseInt(srows);
		}

		String condition = req.getParameter("condition");
		String keyword = req.getParameter("keyword");
		if (condition == null) {
			condition = "subject";
			keyword = "";
		}
		if (req.getMethod().equalsIgnoreCase("GET")) {
			keyword = URLDecoder.decode(keyword, "UTF-8");
		}

		int dataCount;
		if (keyword.length() == 0) {
			dataCount = dao.dataCount();
		} else {
			dataCount = dao.dataCount(condition, keyword);
		}

		int total_page = util.pageCount(rows, dataCount);
		if (current_page > total_page)
			current_page = total_page;

		int offset = (current_page - 1) * rows;
		if (offset < 0)
			offset = 0;

		List<ReviewDTO> list;
		if (keyword.length() == 0)
			list = dao.listLecture(offset, rows);
		else
			list = dao.listLecture(offset, rows, condition, keyword);

		int listNum, n = 0;
		for (ReviewDTO dto : list) {
			listNum = dataCount - (offset + n);
			dto.setListNum(listNum);
			n++;
		}

		String query = "rows=" + rows;
		if (keyword.length() != 0) {
			query += "&condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
		}

		String listUrl = cp + "/review/list.do?" + query;
		String articleUrl = cp + "/review/review.do?page=" + current_page + "&" + query;

		String paging = util.paging(current_page, total_page, listUrl);

		req.setAttribute("list", list);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("page", current_page);
		req.setAttribute("total_page", total_page);
		req.setAttribute("articleUrl", articleUrl);
		req.setAttribute("paging", paging);
		req.setAttribute("condition", condition);
		req.setAttribute("keyword", keyword);
		req.setAttribute("rows", rows);

		forward(req, resp, "/WEB-INF/views/review/list.jsp");
	}

	protected void createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session=req.getSession();
	    SessionInfo info=(SessionInfo)session.getAttribute("member");
		String cp=req.getContextPath();
		
		if(! info.getUserId().equals("admin")) {
			resp.sendRedirect(cp+"/review/list.do");
			return;
		}
		
		ReviewDAO dao = new ReviewDAO();
	
		List<ReviewDTO> listAcademy = dao.listAcademy();
		List<ReviewDTO> listLecture = null;
		if(listAcademy.size()>=0)
			listLecture = dao.listLecture(listAcademy.get(0).getAcaNum());
	
		
		req.setAttribute("mode", "created");
		req.setAttribute("listAcademy",listAcademy);
		req.setAttribute("listLecture",listLecture);
		forward(req, resp, "/WEB-INF/views/review/createdReview.jsp");
	}
	
	private void listLectureChange(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int acaNum=Integer.parseInt(req.getParameter("acaNum"));
		ReviewDAO dao = new ReviewDAO();
		List<ReviewDTO> listLecture = dao.listLecture(acaNum);
		req.setAttribute("listLecture",listLecture);
		forward(req, resp, "/WEB-INF/views/review/listLecture.jsp");
	}
	
	protected void createdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	     HttpSession session=req.getSession();
	     SessionInfo info=(SessionInfo)session.getAttribute("member");
		
	  	
	  	ReviewDAO dao = new ReviewDAO();
		ReviewDTO dto = new ReviewDTO();
		
		dto.setLecCode(Integer.parseInt(req.getParameter("lecCode")));
		dto.setAcaNum(Integer.parseInt(req.getParameter("acaNum")));
		dto.setAcaName(req.getParameter("acaName"));
		dto.setUserId(info.getUserId());
		dto.setRate(req.getParameter("rate"));
		dto.setContent(req.getParameter("content"));
		
		dao.insertBoard(dto, "created");
		
		String cp=req.getContextPath();
		resp.sendRedirect(cp+"/review/list.do");
	}
	
	//리뷰 글보기
	protected void review(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int reNum=Integer.parseInt(req.getParameter("reNum"));
		String page=req.getParameter("page");
		String rows=req.getParameter("rows");
		String condition=req.getParameter("condition");
		String keyword=req.getParameter("keyword");
		if(condition==null) {
			condition="subject";
			keyword="";
		}
		keyword=URLDecoder.decode(keyword, "UTF-8");
		
		String query="page="+page+"&rows="+rows;
		if(keyword.length()!=0) {
			query+="&condition="+condition+"&keyword="
		           +URLEncoder.encode(keyword, "UTF-8");
		}
		ReviewDAO dao = new ReviewDAO();
		ReviewDTO dto = dao.readReview(reNum);
		
		if(dto==null) {
			String cp=req.getContextPath();
			resp.sendRedirect(cp+"/review/list.do?"+query);
			return;
		}
		
		req.setAttribute("dto", dto);
		req.setAttribute("query", query);
		req.setAttribute("page", page);
		req.setAttribute("rows", rows);
		
		forward(req, resp, "/WEB-INF/views/review/review.jsp");

	}
	
	
	private void listReply(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 리플 리스트 - AJAX:TEXT
		ReviewDAO dao = new ReviewDAO();
		MyUtil util = new MyUtil();

		int num = Integer.parseInt(req.getParameter("reNum"));
		String pageNo = req.getParameter("pageNo");
		int current_page = 1;
		if (pageNo != null)
			current_page = Integer.parseInt(pageNo);

		int rows = 5;
		int total_page = 0;
		int replyCount = 0;

		replyCount = dao.dataCountReply(num);
		total_page = util.pageCount(rows, replyCount);
		if (current_page > total_page)
			current_page = total_page;

		int offset = (current_page - 1) * rows;
		
		// 리스트에 출력할 데이터
		List<ReviewDTO> listReply = dao.listReply(num, offset, rows);

		// 엔터를 <br>
		for (ReviewDTO dto : listReply) {
			dto.setContent(dto.getContent().replaceAll("\n", "<br>"));
		}

		// 페이징 처리(인수2개 짜리로 자바스크립트 listPage(page) 함수 호출)
		String paging = util.paging(current_page, total_page);
		
		
		req.setAttribute("listReply", listReply);
		req.setAttribute("pageNo", current_page);
		req.setAttribute("replyCount", replyCount);
		req.setAttribute("total_page", total_page);
		req.setAttribute("paging", paging);
		
		// 포워딩
		forward(req, resp, "/WEB-INF/views/review/listReply.jsp");
	}

	private void insertReply(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 리플 저장 - AJAX:JSON
		ReviewDAO dao = new ReviewDAO();
		ReviewDTO dto = new ReviewDTO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		dto.setUserId(info.getUserId());
		dto.setContent(req.getParameter("content"));
		dto.setReNum(Integer.parseInt(req.getParameter("reNum")));
		dto.setRate(req.getParameter("rate"));
		String answer = req.getParameter("answer");
		
		if (answer != null)
			dto.setAnswer(Integer.parseInt(answer));

		String state = "false";
		int result = dao.insertReply(dto);
		if (result == 1)
			state = "true";

		JSONObject job = new JSONObject();
		job.put("state", state);

		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		out.print(job.toString());
	}
	
	
	
	protected void deleteReview(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		String cp=req.getContextPath();

		
		String page = req.getParameter("page");
		String rows = req.getParameter("rows");
		String condition = req.getParameter("condition");
		String keyword = req.getParameter("keyword");
		if (condition == null) {
			condition = "subject";
			keyword = "";
		}
		keyword = URLDecoder.decode(keyword, "UTF-8");

		String query = "page=" + page + "&rows=" + rows;
		if (keyword.length() != 0) {
			query += "&condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
		}
		int reNum = Integer.parseInt(req.getParameter("reNum"));
		ReviewDAO dao = new ReviewDAO();
		ReviewDTO dto = dao.readReview(reNum);
		
		if (dto == null) {
			resp.sendRedirect(cp + "/review/list.do?" + query);
			return;
		}
		
		
		dao.deleteReview(reNum);

		resp.sendRedirect(cp + "/review/list.do?"+ query);
	}
	
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		String cp = req.getContextPath();

		int reNum = Integer.parseInt(req.getParameter("reNum"));
		String page = req.getParameter("page");
		String rows = req.getParameter("rows");
	
		ReviewDAO dao = new ReviewDAO();
		
		ReviewDTO dto = dao.readReview(reNum);
		
		List<ReviewDTO> listAcademy = dao.listAcademy();
		List<ReviewDTO> listLecture = null;
		if(listAcademy.size()>=0)
			listLecture = dao.listLecture(listAcademy.get(0).getAcaNum());
		
		
		if (dto == null) {
			resp.sendRedirect(cp + "/review/list.do?page=" + page + "&rows=" + rows);
			return;
		}

		req.setAttribute("listAcademy",listAcademy);
		req.setAttribute("listLecture",listLecture);
		req.setAttribute("dto", dto);
		req.setAttribute("page", page);
		req.setAttribute("rows", rows);
		req.setAttribute("mode", "update");

		forward(req, resp, "/WEB-INF/views/review/createdReview.jsp");
	}
	
	

	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		String cp = req.getContextPath();

		ReviewDAO dao = new ReviewDAO();
		ReviewDTO dto = new ReviewDTO();
		
		dto.setReNum(Integer.parseInt(req.getParameter("reNum")));
		dto.setLecCode(Integer.parseInt(req.getParameter("lecCode")));
		dto.setAcaNum(Integer.parseInt(req.getParameter("acaNum")));
		dto.setUserId(info.getUserId());
		dto.setContent(req.getParameter("content"));

		dao.updateReview(dto, "update");

		String page = req.getParameter("page");
		String rows = req.getParameter("rows");

		String url = cp + "/review/list.do?page=" + page + "&rows=" + rows;

		resp.sendRedirect(url);
	}
}
	
/*
	private void deleteReply(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 리플 또는 답글 삭제 - AJAX:JSON
		BoardDAO dao = new BoardDAO();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		int replyNum = Integer.parseInt(req.getParameter("replyNum"));

		String state = "false";
		int result = dao.deleteReply(replyNum, info.getUserId());
		if (result == 1)
			state = "true";

		JSONObject job = new JSONObject();
		job.put("state", state);

		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		out.print(job.toString());
	}
	
	
}
	
/*
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		String cp = req.getContextPath();

		int num = Integer.parseInt(req.getParameter("num"));
		String page = req.getParameter("page");
		String rows = req.getParameter("rows");

		BoardDAO dao = new BoardDAO();
		BoardDTO dto = dao.readBoard(num);

		if (dto == null) {
			resp.sendRedirect(cp + "/bbs/list.do?page=" + page + "&rows=" + rows);
			return;
		}

		if (!info.getUserId().equals(dto.getUserId())) {
			resp.sendRedirect(cp + "/bbs/list.do?page=" + page + "&rows=" + rows);
			return;
		}

		req.setAttribute("dto", dto);
		req.setAttribute("page", page);
		req.setAttribute("rows", rows);
		req.setAttribute("mode", "update");

		forward(req, resp, "/WEB-INF/views/bbs/created.jsp");
	}

	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		String cp = req.getContextPath();

		BoardDAO dao = new BoardDAO();
		BoardDTO dto = new BoardDTO();

		dto.setNum(Integer.parseInt(req.getParameter("num")));
		dto.setUserId(info.getUserId());
		dto.setSubject(req.getParameter("subject"));
		dto.setContent(req.getParameter("content"));

		dao.updateBoard(dto);

		String page = req.getParameter("page");
		String rows = req.getParameter("rows");

		String url = cp + "/bbs/list.do?page=" + page + "&rows=" + rows;

		resp.sendRedirect(url);
	}

	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		String cp = req.getContextPath();

		int num = Integer.parseInt(req.getParameter("num"));
		String page = req.getParameter("page");
		String rows = req.getParameter("rows");
		String condition = req.getParameter("condition");
		String keyword = req.getParameter("keyword");
		if (condition == null) {
			condition = "subject";
			keyword = "";
		}
		keyword = URLDecoder.decode(keyword, "UTF-8");

		String query = "page=" + page + "&rows=" + rows;
		if (keyword.length() != 0) {
			query += "&condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
		}

		BoardDAO dao = new BoardDAO();
		dao.deleteBoard(num, info.getUserId());

		resp.sendRedirect(cp + "/bbs/list.do?" + query);
	}

	
/*
	private void deleteReply(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 리플 또는 답글 삭제 - AJAX:JSON
		BoardDAO dao = new BoardDAO();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		int replyNum = Integer.parseInt(req.getParameter("replyNum"));

		String state = "false";
		int result = dao.deleteReply(replyNum, info.getUserId());
		if (result == 1)
			state = "true";

		JSONObject job = new JSONObject();
		job.put("state", state);

		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		out.print(job.toString());
	}
}
*/