package com.qna;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.util.MyServlet;
import com.util.MyUtil;

@WebServlet("/qna/*")
public class QnaServlet extends MyServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		if(info==null) {
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		}
		
		String uri=req.getRequestURI();
		
		if(uri.indexOf("list.do")!=-1) {
			list(req, resp);
		}
	}
	
	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글 리스트
		MyUtil util=new MyUtil();
		QnaDAO dao=new QnaDAO();
		String cp=req.getContextPath();
		
		// 파라미터로 넘어온 페이지 번호
		String page = req.getParameter("page");
		int current_page = 1;
		if (page != null)
			current_page = Integer.parseInt(page);

		// 검색
		String condition = req.getParameter("condition");
		String keyword = req.getParameter("keyword");
		if (condition == null) {
			condition = "subject";
			keyword = "";
		}
		// GET 방식은 검색 문자열을 인코딩해서 파라미터가 보냄으로 다시 디코딩이 필요
		if (req.getMethod().equalsIgnoreCase("GET")) {
			keyword = URLDecoder.decode(keyword, "utf-8");
		}

		// 데이터 개수
		int dataCount;
		if (keyword.length() == 0)
			dataCount = dao.dataCount();
		else
			dataCount = dao.dataCount(condition, keyword);

		// 전체페이지수
		int rows = 10;
		int total_page = util.pageCount(rows, dataCount);
		if (current_page > total_page)
			current_page = total_page;

		// 게시물 가져오기
		int offset = (current_page - 1) * rows;

		List<QnaDTO> list;
		if (keyword.length() == 0)
			list = dao.listQna(offset, rows);
		else
			list = dao.listQna(offset, rows, condition, keyword);

		// 리스트 글번호 만들기
		int listNum, n = 0;
		for(QnaDTO dto : list) {
			listNum = dataCount - (offset + n);
			dto.setListNum(listNum);
			n++;
		}

		String query = "";
		if (keyword.length() != 0) {
			query = "condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
		}

		// 페이징처리
		String listUrl = cp + "/qna/list.do";
		String articleUrl = cp + "/qna/article.do?page=" + current_page;
		if (query.length() != 0) {
			listUrl += "?" + query;
			articleUrl += "&" + query;
		}

		String paging = util.paging(current_page, total_page, listUrl);

		req.setAttribute("list", list);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("page", current_page);
		req.setAttribute("total_page", total_page);
		req.setAttribute("articleUrl", articleUrl);
		req.setAttribute("paging", paging);
		req.setAttribute("condition", condition);
		req.setAttribute("keyword", keyword);
		
		forward(req, resp, "/WEB-INF/views/qna/list.jsp");
	}
	
	protected void createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글등록 폼
		req.setAttribute("mode", "created");
		forward(req, resp, "/WEB-INF/views/qna/created.jsp");
	}
	
	protected void createdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글등록
		String cp=req.getContextPath();
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp+"/qna/list.do");
			return;
		}
		
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		QnaDAO dao=new QnaDAO();
		QnaDTO dto=new QnaDTO();
		dto.setSubject(req.getParameter("subject"));
		dto.setContent(req.getParameter("content"));
		dto.setUserId(info.getUserId());
		
		dao.insertQna(dto, "created");
		
		resp.sendRedirect(cp+"/qna/list.do");
	}
	
	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글보기
		QnaDAO dao=new QnaDAO();
		String cp=req.getContextPath();
		
		int qnaNum = Integer.parseInt(req.getParameter("qnaNum"));
		String page = req.getParameter("page");
		
		String condition = req.getParameter("condition");
		String keyword = req.getParameter("keyword");
		if(condition==null) {
			condition="subject";
			keyword="";
		}
		keyword = URLDecoder.decode(keyword, "utf-8");
		
		String query="page="+page;
		if(keyword.length()!=0) {
			query+="&condition="+condition+"&keyword="+URLEncoder.encode(keyword, "utf-8");
		}
		
		dao.updateHitCount(qnaNum);
		QnaDTO dto=dao.readQna(qnaNum);
		if(dto==null) {
			resp.sendRedirect(cp+"/qna/list.do?"+query);
			return;
		}
		
		// dto.setContent(dto.getContent().replaceAll("\n", "<br>"));
		MyUtil util=new MyUtil();
		dto.setContent(util.htmlSymbols(dto.getContent()));
		
		QnaDTO preReadDto=dao.preReadQna(dto.getGroupNum(),
				dto.getOrderNo(), condition, keyword);
		QnaDTO nextReadDto=dao.nextReadQna(dto.getGroupNum(),
				dto.getOrderNo(), condition, keyword);
	
		req.setAttribute("dto", dto);
		req.setAttribute("preReadDto", preReadDto);
		req.setAttribute("nextReadDto", nextReadDto);
		req.setAttribute("query", query);
		req.setAttribute("page", page);
		
		forward(req, resp, "/WEB-INF/views/qna/article.jsp");
	}
	
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 수정 폼
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		String cp=req.getContextPath();
		QnaDAO dao=new QnaDAO();
		
		String page=req.getParameter("page");
		String condition = req.getParameter("condition");
		String keyword = req.getParameter("keyword");
		if(condition==null) {
			condition="subject";
			keyword="";
		}
		keyword = URLDecoder.decode(keyword, "utf-8");
		String query="page="+page;
		if(keyword.length()!=0) {
			query+="&condition="+condition+"&keyword="+URLEncoder.encode(keyword, "utf-8");
		}
		
		int qnaNum=Integer.parseInt(req.getParameter("qnaNum"));
		QnaDTO dto=dao.readQna(qnaNum);
		
		if(dto==null) {
			resp.sendRedirect(cp+"/qna/list.do?"+query);
			return;
		}
		
		// 게시물을 올린 사용자가 아니면
		if(! dto.getUserId().equals(info.getUserId())) {
			resp.sendRedirect(cp+"/qna/list.do?"+query);
			return;
		}
		
		req.setAttribute("dto", dto);
		req.setAttribute("page", page);
		req.setAttribute("condition", condition);
		req.setAttribute("keyword", keyword);
		req.setAttribute("mode", "update");
		
		forward(req, resp, "/WEB-INF/views/qna/created.jsp");
	}
	
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 수정 완료
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		String cp=req.getContextPath();
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp+"/qna/list.do");
			return;
		}
		
		QnaDAO dao=new QnaDAO();
		
		String page=req.getParameter("page");
		String condition = req.getParameter("condition");
		String keyword = req.getParameter("keyword");
		String query="page="+page;
		if(keyword.length()!=0) {
			query+="&condition="+condition+"&keyword="+URLEncoder.encode(keyword, "utf-8");
		}
		
		QnaDTO dto=new QnaDTO();
		dto.setQnaNum(Integer.parseInt(req.getParameter("qnaNum")));
		dto.setSubject(req.getParameter("subject"));
		dto.setContent(req.getParameter("content"));
		
		dao.updateQna(dto, info.getUserId());
		
		resp.sendRedirect(cp+"/qna/list.do?"+query);
	}
	
	
	protected void replyForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 답변 폼
		QnaDAO dao=new QnaDAO();
		String cp=req.getContextPath();
		
		int qnaNum = Integer.parseInt(req.getParameter("qnaNum"));
		String page = req.getParameter("page");
		
		QnaDTO dto=dao.readQna(qnaNum);
		if(dto==null) {
			resp.sendRedirect(cp+"/qna/list.do?page="+page);
			return;
		}
		
		String s="["+dto.getSubject()+"] 에 대한 답변입니다.\n";
		dto.setContent(s);
		
		req.setAttribute("mode", "reply");
		req.setAttribute("dto", dto);
		req.setAttribute("page", page);
	
		forward(req, resp, "/WEB-INF/views/qna/created.jsp");
	}
	
	
	protected void replySubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 답변 완료
		String cp=req.getContextPath();
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp+"/qna/list.do");
			return;
		}
		
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		QnaDAO dao=new QnaDAO();
		QnaDTO dto=new QnaDTO();
		
		dto.setSubject(req.getParameter("subject"));
		dto.setContent(req.getParameter("content"));
		dto.setGroupNum(Integer.parseInt(req.getParameter("groupNum")));
		dto.setOrderNo(Integer.parseInt(req.getParameter("orderNo")));
		dto.setDepth(Integer.parseInt(req.getParameter("depth")));
		dto.setParent(Integer.parseInt(req.getParameter("parent")));
		
		dto.setUserId(info.getUserId());
		
		dao.insertQna(dto, "reply");
		
		String page=req.getParameter("page");
		
		resp.sendRedirect(cp+"/qna/list.do?page="+page);		
		
	}
	
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 삭제
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");

		String cp=req.getContextPath();
		QnaDAO dao=new QnaDAO();
		
		String page=req.getParameter("page");
		String condition = req.getParameter("condition");
		String keyword = req.getParameter("keyword");
		if(condition==null) {
			condition="subject";
			keyword="";
		}
		keyword = URLDecoder.decode(keyword, "utf-8");
		String query="page="+page;
		if(keyword.length()!=0) {
			query+="&condition="+condition+"&keyword="+URLEncoder.encode(keyword, "utf-8");
		}
		
		int qnaNum=Integer.parseInt(req.getParameter("qnaNum"));
		QnaDTO dto=dao.readQna(qnaNum);
		
		if(dto==null) {
			resp.sendRedirect(cp+"/qna/list.do?"+query);
			return;
		}
		
		// 게시물을 올린 사용자나 admin이 아니면
		if(! dto.getUserId().equals(info.getUserId()) && ! info.getUserId().equals("admin")) {
			resp.sendRedirect(cp+"/qna/list.do?"+query);
			return;
		}
		
		dao.deleteQna(qnaNum);
		resp.sendRedirect(cp+"/qna/list.do?"+query);
	}

}
