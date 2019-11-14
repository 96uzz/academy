package com.lecture;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.util.MyServlet;
import com.util.MyUtil;

@WebServlet("/lts/*")
public class LectureServlet extends MyServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void forward(HttpServletRequest req, HttpServletResponse resp, String path) throws ServletException, IOException {
		RequestDispatcher rd = req.getRequestDispatcher(path);
		rd.forward(req, resp);
	}

	private SessionInfo loginUser(HttpServletRequest req) throws ServletException {
		SessionInfo info = null;
		HttpSession session = req.getSession();
		
		info = (SessionInfo)session.getAttribute("member");
		return info;
	}
	
	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri=req.getRequestURI();
		if(uri.indexOf("list.do")!=-1) {
			list(req, resp);
		} else if(uri.indexOf("created.do")!=-1) {
			createdForm(req,resp);
		} else if(uri.indexOf("created_ok.do")!=-1) {
			createdSubmit(req, resp);		
		} else if(uri.indexOf("article.do")!=-1) {
			article(req, resp);
		} else if(uri.indexOf("update.do")!=-1) {
			updateForm(req, resp);
		} else if(uri.indexOf("update_ok.do")!=-1) {
			updateSubmit(req, resp);
		} else if(uri.indexOf("delete.do")!=-1) {
			delete(req,resp);
		} else if(uri.indexOf("interlecture.do")!=-1) {
			interLecture(req, resp);
		}
	}
	
	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LectureDAO dao = new LectureDAO();
		MyUtil util = new MyUtil();
		String cp = req.getContextPath();
		
		String page = req.getParameter("page");
		int current_page=1;
		if(page != null) {
			current_page = Integer.parseInt(page);
		}
		int rows = 10;
		String srows = req.getParameter("rows");
		if(srows!=null) {
			rows = Integer.parseInt(srows);
		}
		
		String condition = req.getParameter("condition");
		String keyword=req.getParameter("keyword");
		if(condition==null) {
			condition="lecName";
			keyword="";
		}
		if(req.getMethod().equalsIgnoreCase("GET")) {
			keyword=URLDecoder.decode(keyword, "UTF-8");
		}
		int dataCount;
		if(keyword.length()==0) {
			dataCount = dao.dataCount();
		}else {
			dataCount = dao.dataCount(condition, keyword);
		}
		
		int total_page = util.pageCount(rows, dataCount);
		if(current_page > total_page) {
			current_page = total_page;
		}
		int offset = (current_page-1)*rows;
		if(offset<0) {
			offset = 0;
		}
		
		List<LectureDTO> list;
		if(keyword.length()==0) {
			list = dao.listLecture(offset, rows);
		} else
			list = dao.listLecture(offset, rows, condition, keyword);
		/*
		List<LectureDTO> listLecture=null;
		if(current_page==1) {
			listLecture = dao.listLecture();
			for(LectureDTO dto : listLecture) {
				dto.setCreated(dto.getCreated().substring(0, 10));
			}
		}
		*/
		
		long gap;
		Date curDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		int listNum, n=0;
		for(LectureDTO dto : list) {
			listNum = dataCount - (offset+n);
			dto.setListNum(listNum);
			try {
				Date date=sdf.parse(dto.getCreated());
				gap = (curDate.getTime() - date.getTime()) / (1000*60*60*24);
				dto.setGab(gap);
			} catch (Exception e) {
			}
			dto.setCreated(dto.getCreated().substring(0, 10));
			n++;
		}
		String query="rows="+rows;
		if(keyword.length()!=0) {
			query+="&condition="+condition+"&keyword="+URLEncoder.encode(keyword, "UTF-8");
		}
		
		String listUrl=cp+"/lts/list.do?"+query;
		String articleUrl=cp+"/lts/article.do?page="+current_page+"&"+query;
		String paging=util.paging(current_page, total_page, listUrl);
		
		req.setAttribute("list", list);
		// req.setAttribute("listLecture", listLecture);
		req.setAttribute("paging", paging);
		req.setAttribute("page", current_page);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("total_page", total_page);
		req.setAttribute("rows", rows);
		req.setAttribute("condition", condition);
		req.setAttribute("keyword", keyword);
		req.setAttribute("articleUrl", articleUrl);
		
		forward(req, resp, "/WEB-INF/views/lecture/list.jsp");
	}
	
	protected void createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SessionInfo info = loginUser(req);
		String cp = req.getContextPath();
		
		if(info==null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		if(!info.getUserId().equals("admin")) {
			resp.sendRedirect(cp+"/lts/list.do");
			return;
		}
		
		LectureDAO dao = new LectureDAO();
		List<AcademyDTO> listAcademy = dao.listAcademy();
		
		req.setAttribute("mode", "created");
		req.setAttribute("listAcademy",listAcademy);
		forward(req, resp, "/WEB-INF/views/lecture/created.jsp");
	}
	
	
	protected void createdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SessionInfo info = loginUser(req);
		String cp=req.getContextPath();
		
		if(info==null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		if(!info.getUserId().equals("admin")) {
			resp.sendRedirect(cp+"/lts/list.do");
			return;
		}
		
		LectureDAO dao = new LectureDAO();
		LectureDTO dto = new LectureDTO();
		
		dto.setUserId(info.getUserId());
		dto.setAcaNum(Integer.parseInt(req.getParameter("acaNum")));
		dto.setLecName(req.getParameter("lecName"));
		dto.setLecStartDate(req.getParameter("lecStartDate"));
		dto.setLecEndDate(req.getParameter("lecEndDate"));
		dto.setLecLimit(Integer.parseInt(req.getParameter("lecLimit")));
		dto.setLecIntro(req.getParameter("lecIntro"));
		
		dao.insertLecture(dto);
		
		resp.sendRedirect(cp+"/lts/list.do");
	}
	
	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SessionInfo info = loginUser(req);
		
		LectureDAO dao = new LectureDAO();
		String cp=req.getContextPath();
		
		if(info==null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		int num=Integer.parseInt(req.getParameter("lecCode"));
		String page=req.getParameter("page");
		
		String condition=req.getParameter("condition");
		String keyword=req.getParameter("keyword");
		if(condition==null) {
			condition="lecName";
			keyword="";
		}
		keyword=URLDecoder.decode(keyword, "utf-8");
		
		String query="page="+page;
		if(keyword.length()!=0) {
			query+="&condition="+condition+"&keyword="+URLEncoder.encode(keyword, "UTF-8");
		}
		
		dao.updateHitCount(num);
		
		LectureDTO dto=dao.readLecture(num);
		if(dto==null) {
			resp.sendRedirect(cp+"/lts/list.do?"+query);
			return;
		}
		
		dto.setLecIntro(dto.getLecIntro().replaceAll("\n", "<br>"));
		
		LectureDTO preReadDto = dao.preReadLecture(dto.getLecCode(), condition, keyword);
		LectureDTO nextReadDto = dao.nextReadLecture(dto.getLecCode(), condition, keyword);
		
		req.setAttribute("dto", dto);
		req.setAttribute("preReadDto", preReadDto);
		req.setAttribute("nextReadDto", nextReadDto);
		req.setAttribute("query", query);
		req.setAttribute("page", page);
		
		forward(req, resp, "/WEB-INF/views/lecture/article.jsp");
	}
	
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SessionInfo info = loginUser(req);
		String cp = req.getContextPath();
		
		if(info==null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		String page=req.getParameter("page");
		int num=Integer.parseInt(req.getParameter("lecCode"));
		
		LectureDAO dao = new LectureDAO();
		LectureDTO dto = dao.readLecture(num);
		if(dto==null || ! info.getUserId().equals(dto.getUserId())) {
			resp.sendRedirect(cp+"/lts/list.do?page="+page);
			return;
		}
		
		List<AcademyDTO> listAcademy = dao.listAcademy();
		
		req.setAttribute("mode", "update");
		req.setAttribute("listAcademy",listAcademy);
		req.setAttribute("dto", dto);
		req.setAttribute("page", page);
		
		forward(req, resp, "/WEB-INF/views/lecture/created.jsp");
	
	}
	
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SessionInfo info = loginUser(req);
		String cp = req.getContextPath();
		if(info==null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		LectureDAO dao = new LectureDAO();
		LectureDTO dto = new LectureDTO();
		
		dto.setUserId(info.getUserId());
		dto.setAcaNum(Integer.parseInt(req.getParameter("acaNum")));
		dto.setLecCode(Integer.parseInt(req.getParameter("lecCode")));
		dto.setLecName(req.getParameter("lecName"));
		dto.setLecStartDate(req.getParameter("lecStartDate"));
		dto.setLecEndDate(req.getParameter("lecEndDate"));
		dto.setLecLimit(Integer.parseInt(req.getParameter("lecLimit")));
		dto.setLecIntro(req.getParameter("lecIntro"));
		
		
		dao.updateLecture(dto);
		
		resp.sendRedirect(cp+"/lts/list.do");
		
	}
	
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SessionInfo info = loginUser(req);
		String cp = req.getContextPath();
		
		if(info==null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		String page = req.getParameter("page");
		int num = Integer.parseInt(req.getParameter("lectureCode"));
		String condition=req.getParameter("condition");
		String keyword=req.getParameter("keyword");
		if(condition==null) {
			condition="lecName";
			keyword="";
		}
		keyword=URLDecoder.decode(keyword, "utf-8");
		
		String query="page="+page;
		if(keyword.length()!=0) {
			query+="&condition="+condition+"&keyword="+URLEncoder.encode(keyword, "UTF-8");
		}
		
		LectureDAO dao = new LectureDAO();
		LectureDTO dto = dao.readLecture(num);
		
		if(dto==null) {
			resp.sendRedirect(cp+"/lts/list.do?"+query);
			return;
		}
		dao.deleteLecture(num, info.getUserId());
		
		resp.sendRedirect(cp+"/lts/list.do?num="+num+"&page="+page);
	}
	
	protected void interLecture(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SessionInfo info = loginUser(req);
		String cp=req.getContextPath();
		
		if(info==null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		/*
		if(!info.getUserId().equals("admin")) {
			resp.sendRedirect(cp+"/lts/list.do");
			return;
		}
		*/
		
		LectureDAO dao = new LectureDAO();
		LectureDTO dto = new LectureDTO();
		
		//dto.setInterNum(Integer.parseInt(req.getParameter("interNum").trim()));
		dto.setAcaNum(Integer.parseInt(req.getParameter("acaNum")));
		dto.setLecCode(Integer.parseInt(req.getParameter("lecCode")));
		dto.setUserId(info.getUserId());
		
		dao.insertInterLecture(dto);
		
		resp.sendRedirect(cp+"/lts/list.do");
		

	}
}