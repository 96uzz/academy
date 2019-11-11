package com.board;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
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

@WebServlet("/board/*")
public class BoardServlet extends HttpServlet {
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

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String uri = req.getRequestURI();
		if (info != null) {
			if (uri.indexOf("list.do") != -1) {
				list(req, resp);
			} else if (uri.indexOf("created.do") != -1) {
				createdForm(req, resp);
			} else if (uri.indexOf("created_ok.do") != -1) {
				createdSubmit(req, resp);
			} else if (uri.indexOf("article.do") != -1) {
				article(req, resp);
			} else if (uri.indexOf("update.do") != -1) {
				updateForm(req, resp);
			} else if (uri.indexOf("update_ok.do") != -1) {
				updateSubmit(req, resp);
			} else if (uri.indexOf("reply.do") != -1) {
				replyForm(req, resp);
			} else if (uri.indexOf("reply_ok.do") != -1) {
				replySubmit(req, resp);
			} else if (uri.indexOf("delete.do") != -1) {
				delete(req, resp);
			}
		}

		if (info == null) {
			if (uri.indexOf("list.do") != -1) {
				list(req, resp);
			} else if (uri.indexOf("article.do") != -1) {
					article(req, resp);
			} else {
				forward(req, resp, "/WEB-INF/views/member/login.jsp");
				return;
			}
		}
	}

	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글리스트
		BoardDAO dao = new BoardDAO();
		MyUtil util = new MyUtil();
		String cp=req.getContextPath();
		
		String page = req.getParameter("page");
		int current_page= 1;
		if(page!=null) {
			current_page = Integer.parseInt(page);
		}
		
		int rows = 10;
		String srows = req.getParameter("rows");
		if(srows!=null) {
			rows = Integer.parseInt(srows);
		}
		
		String condition = req.getParameter("condition");
		String keyword = req.getParameter("keyword");
		if(condition==null) {
			condition = "subject";
			keyword = "";
		}
		if(req.getMethod().equalsIgnoreCase("GET")) {
			keyword=URLDecoder.decode(keyword, "UTF-8");
		}
		
		int dataCount;
		if(keyword.length()==0) {
			dataCount = dao.dataCount();
		} else {
			dataCount = dao.dataCount(condition, keyword);
		}
		
		int total_page = util.pageCount(rows, dataCount);
		if(current_page > total_page)
			current_page = total_page;
		
		// 게시물 가져오기
		int offset = (current_page-1)* rows;
		if(offset<0) offset = 0;

		List<BoardDTO> list;
		if (keyword.length() == 0)
			list = dao.listBoard(offset, rows);
		else
			list = dao.listBoard(offset, rows, condition, keyword);

		long gap;
		Date curDate = new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		// 리스트 글번호 만들기
		
		int listNum =0;
		int n=0;
		for (BoardDTO dto : list) {
			listNum = dataCount-(offset+n);
			dto.setListNum(listNum);
		
			try {
				Date date = sdf.parse(dto.getCreated());
				
				gap = (curDate.getTime() - date.getTime()) / (1000*60*60); //시간
				dto.setGap(gap);
				
			} catch (Exception e) {
			}
			dto.setCreated(dto.getCreated().substring(0, 10));
			n++;
			
		}
		
		
		String query = "rows="+rows;
		if (keyword.length() != 0) {
			query += "&condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
		}

		// 페이징처리
		String listUrl = cp + "/board/list.do?"+query;
		String articleUrl = cp + "/board/article.do?page=" + current_page+"&"+query;
		
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

		forward(req, resp, "/WEB-INF/views/board/list.jsp");
	}

	protected void createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글등록 폼
		req.setAttribute("mode", "created");
		forward(req, resp, "/WEB-INF/views/board/created.jsp");
	}

	protected void createdSubmit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 글등록
		String cp = req.getContextPath();
		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/board/list.do");
			return;
		}

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		BoardDAO dao = new BoardDAO();
		BoardDTO dto = new BoardDTO();
		dto.setSubject(req.getParameter("subject"));
		dto.setContent(req.getParameter("content"));
		dto.setUserId(info.getUserId());

		dao.insertBoard(dto, "created");

		resp.sendRedirect(cp + "/board/list.do");
	}

	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글보기
		BoardDAO dao = new BoardDAO();
		String cp = req.getContextPath();

		int boardNum = Integer.parseInt(req.getParameter("boardNum"));
		String page = req.getParameter("page");

		String condition = req.getParameter("condition");
		String keyword = req.getParameter("keyword");
		if (condition == null) {
			condition = "subject";
			keyword = "";
		}
		keyword = URLDecoder.decode(keyword, "utf-8");

		String query = "page=" + page;
		if (keyword.length() != 0) {
			query += "&condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
		}

		dao.updateHitCount(boardNum);
		BoardDTO dto = dao.readBoard(boardNum);
		if (dto == null) {
			resp.sendRedirect(cp + "/board/list.do?" + query);
			return;
		}

		// dto.setContent(dto.getContent().replaceAll("\n", "<br>"));
		MyUtil util = new MyUtil();
		dto.setContent(util.htmlSymbols(dto.getContent()));

		BoardDTO preReadDto = dao.preReadBoard(dto.getGroupNum(), dto.getOrderNo(), condition, keyword);
		BoardDTO nextReadDto = dao.nextReadBoard(dto.getGroupNum(), dto.getOrderNo(), condition, keyword);

		req.setAttribute("dto", dto);
		req.setAttribute("preReadDto", preReadDto);
		req.setAttribute("nextReadDto", nextReadDto);
		req.setAttribute("query", query);
		req.setAttribute("page", page);

		forward(req, resp, "/WEB-INF/views/board/article.jsp");
	}

	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 수정 폼
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();
		BoardDAO dao = new BoardDAO();

		String page = req.getParameter("page");
		String condition = req.getParameter("condition");
		String keyword = req.getParameter("keyword");
		if (condition == null) {
			condition = "subject";
			keyword = "";
		}
		keyword = URLDecoder.decode(keyword, "utf-8");
		String query = "page=" + page;
		if (keyword.length() != 0) {
			query += "&condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
		}

		int boardNum = Integer.parseInt(req.getParameter("boardNum"));
		BoardDTO dto = dao.readBoard(boardNum);

		if (dto == null) {
			resp.sendRedirect(cp + "/board/list.do?" + query);
			return;
		}

		// 게시물을 올린 사용자가 아니면
		if (!dto.getUserId().equals(info.getUserId())) {
			resp.sendRedirect(cp + "/board/list.do?" + query);
			return;
		}

		req.setAttribute("dto", dto);
		req.setAttribute("page", page);
		req.setAttribute("condition", condition);
		req.setAttribute("keyword", keyword);
		req.setAttribute("mode", "update");

		forward(req, resp, "/WEB-INF/views/board/created.jsp");
	}

	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 수정 완료
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();
		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/board/list.do");
			return;
		}

		BoardDAO dao = new BoardDAO();

		String page = req.getParameter("page");
		String condition = req.getParameter("condition");
		String keyword = req.getParameter("keyword");
		String query = "page=" + page;
		if (keyword.length() != 0) {
			query += "&condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
		}

		BoardDTO dto = new BoardDTO();
		dto.setBoardNum(Integer.parseInt(req.getParameter("boardNum")));
		dto.setSubject(req.getParameter("subject"));
		dto.setContent(req.getParameter("content"));

		dao.updateBoard(dto, info.getUserId());

		resp.sendRedirect(cp + "/board/list.do?" + query);
	}

	protected void replyForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 답변 폼
		BoardDAO dao = new BoardDAO();
		String cp = req.getContextPath();

		int boardNum = Integer.parseInt(req.getParameter("boardNum"));
		String page = req.getParameter("page");

		BoardDTO dto = dao.readBoard(boardNum);
		if (dto == null) {
			resp.sendRedirect(cp + "/board/list.do?page=" + page);
			return;
		}

		String s = "[" + dto.getSubject() + "] 에 대한 답변입니다.\n";
		dto.setContent(s);

		req.setAttribute("mode", "reply");
		req.setAttribute("dto", dto);
		req.setAttribute("page", page);

		forward(req, resp, "/WEB-INF/views/board/created.jsp");
	}

	protected void replySubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 답변 완료
		String cp = req.getContextPath();
		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/board/list.do");
			return;
		}

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		BoardDAO dao = new BoardDAO();
		BoardDTO dto = new BoardDTO();

		dto.setSubject(req.getParameter("subject"));
		dto.setContent(req.getParameter("content"));

		dto.setGroupNum(Integer.parseInt(req.getParameter("groupNum")));
		dto.setOrderNo(Integer.parseInt(req.getParameter("orderNo")));
		dto.setDepth(Integer.parseInt(req.getParameter("depth")));
		dto.setParent(Integer.parseInt(req.getParameter("parent")));

		dto.setUserId(info.getUserId());

		dao.insertBoard(dto, "reply");

		String page = req.getParameter("page");

		resp.sendRedirect(cp + "/board/list.do?page=" + page);

	}

	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 삭제
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();
		BoardDAO dao = new BoardDAO();

		String page = req.getParameter("page");
		String condition = req.getParameter("condition");
		String keyword = req.getParameter("keyword");
		if (condition == null) {
			condition = "subject";
			keyword = "";
		}
		keyword = URLDecoder.decode(keyword, "utf-8");
		String query = "page=" + page;
		if (keyword.length() != 0) {
			query += "&condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
		}

		int boardNum = Integer.parseInt(req.getParameter("boardNum"));
		BoardDTO dto = dao.readBoard(boardNum);

		if (dto == null) {
			resp.sendRedirect(cp + "/board/list.do?" + query);
			return;
		}

		// 게시물을 올린 사용자나 admin이 아니면
		if (!dto.getUserId().equals(info.getUserId()) && !info.getUserId().equals("admin")) {
			resp.sendRedirect(cp + "/board/list.do?" + query);
			return;
		}

		dao.deleteBoard(boardNum);
		resp.sendRedirect(cp + "/board/list.do?" + query);
	}
}