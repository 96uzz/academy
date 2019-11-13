  package com.academy;


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

@WebServlet("/acs/*")
public class AcademyServlet extends MyServlet{
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
		HttpSession session=req.getSession();
		
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
			createdForm(req, resp);
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
		}
	}
	
	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		AcademyDAO dao = new AcademyDAO();
		MyUtil util = new MyUtil();
		String cp = req.getContextPath();
		
		String page = req.getParameter("page");
		int current_page = 1;
		if(page != null) {
			current_page = Integer.parseInt(page);
		}
		int rows = 10;
		String srows = req.getParameter("rows");
		if(srows!=null) {
			rows = Integer.parseInt(srows);
		}
		
		String condition=req.getParameter("condition");
		String keyword=req.getParameter("keyword");
		if(condition==null) {
			condition="acaName";
			keyword="";
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
		if(current_page > total_page) {
			current_page = total_page;
		}
		
		int offset = (current_page-1)*rows;
		if(offset<0) {
			offset = 0;
		}
		
		List<AcademyDTO> list;
		if(keyword.length()==0) {
			list = dao.listAcademy(offset, rows);
		} else
			list = dao.listAcademy(offset, rows, condition, keyword);
		/*
		List<AcademyDTO> listAcademy=null;
		if(current_page==1) {
			listAcademy = dao.listAcademy();
			for(AcademyDTO dto : listAcademy) {
				dto.setCreated(dto.getCreated().substring(0, 10));
			}
		}
		*/
		long gap;
		Date curDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
		int listNum,  n=0;
		for(AcademyDTO dto : list) {
			listNum = dataCount - (offset+n);
			dto.setListNum(listNum);
			try {
				Date date=sdf.parse(dto.getCreated());
				gap = (curDate.getTime() - date.getTime()) / (1000*60*60*24);
				dto.setGap(gap);
			} catch (Exception e) {
			}
			dto.setCreated(dto.getCreated().substring(0, 10));
			n++;
		}
		String query="rows="+rows;
		if(keyword.length()!=0) {
			query+="&condition="+condition+"&keyword="+URLEncoder.encode(keyword, "UTF-8");
		}
		
		String listUrl=cp+"/acs/list.do?"+query;
		String articleUrl=cp+"/acs/article.do?page="+current_page+"&"+query;
		String paging=util.paging(current_page, total_page, listUrl);
		
		req.setAttribute("list", list);
		// req.setAttribute("listAcademy", listAcademy);
		req.setAttribute("paging", paging);
		req.setAttribute("page", current_page);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("total_page", total_page);
		req.setAttribute("rows", rows);
		req.setAttribute("condition", condition);
		req.setAttribute("keyword", keyword);
		req.setAttribute("articleUrl", articleUrl);
		
		forward(req, resp, "/WEB-INF/views/academy/list.jsp");
	}
	
	protected void createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SessionInfo info = loginUser(req);
		String cp = req.getContextPath();
		
		if(info==null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		if(!info.getUserId().equals("admin")) {
			resp.sendRedirect(cp+"/acs/list.do");
			return;
		}
		
		req.setAttribute("mode", "created");
		forward(req, resp, "/WEB-INF/views/academy/created.jsp");
	}
	
	protected void createdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SessionInfo info = loginUser(req);
		String cp=req.getContextPath();
		
		if(info==null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		if(!info.getUserId().equals("admin")) {
			resp.sendRedirect(cp+"/acs/list.do");
			return;
		}
		
		AcademyDAO dao = new AcademyDAO();
		AcademyDTO dto = new AcademyDTO();
		
		dto.setUserId(info.getUserId());
		dto.setAcaName(req.getParameter("acaName"));
		dto.setAcaDiv(req.getParameter("acaDiv"));
		dto.setAcaIntro(req.getParameter("acaIntro"));
		dto.setAcaAddress(req.getParameter("acaAddress"));
		dto.setAcaWeb(req.getParameter("acaWeb"));
		dto.setAcaTel(req.getParameter("acaTel"));
		
		dao.insertAcademy(dto);
		
		resp.sendRedirect(cp+"/acs/list.do");
	}
	
	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SessionInfo info = loginUser(req);
		
		AcademyDAO dao = new AcademyDAO();
		String cp=req.getContextPath();
		
		if(info==null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		int num=Integer.parseInt(req.getParameter("acaNum"));
		String page=req.getParameter("page");
		
		String condition=req.getParameter("condition");
		String keyword=req.getParameter("keyword");
		if(condition==null) {
			condition="acaName";
			keyword="";
		}
		keyword=URLDecoder.decode(keyword, "utf-8");
		
		String query="page="+page;
		if(keyword.length()!=0) {
			query+="&condition="+condition+"&keyword="+URLEncoder.encode(keyword, "UTF-8");
		}
		
		dao.updateHitCount(num);
		
		AcademyDTO dto=dao.readAcademy(num);
		if(dto==null) {
			resp.sendRedirect(cp+"/acs/list.do?"+query);
			return;
		}
		
		dto.setAcaIntro(dto.getAcaIntro().replaceAll("\n", "<br>"));
		
		AcademyDTO preReadDto = dao.preReadAcademy(dto.getAcaNum(), condition, keyword);
		AcademyDTO nextReadDto = dao.nextReadAcademy(dto.getAcaNum(), condition, keyword);
		
		req.setAttribute("dto", dto);
		req.setAttribute("preReadDto", preReadDto);
		req.setAttribute("nextReadDto", nextReadDto);
		req.setAttribute("query", query);
		req.setAttribute("page", page);
		
		forward(req, resp, "/WEB-INF/views/academy/article.jsp");
	}
	
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SessionInfo info = loginUser(req);
		String cp = req.getContextPath();
		
		if(info==null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		String page=req.getParameter("page");
		int num=Integer.parseInt(req.getParameter("acaNum"));
		
		AcademyDAO dao = new AcademyDAO();
		AcademyDTO dto = dao.readAcademy(num);
		if(dto==null || ! info.getUserId().equals(dto.getUserId())) {
			resp.sendRedirect(cp+"/acs/list.do?page="+page);
			return;
		}
		
		req.setAttribute("mode", "update");
		req.setAttribute("dto", dto);
		req.setAttribute("page", page);
		
		forward(req, resp, "/WEB-INF/views/academy/created.jsp");
	}
	
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SessionInfo info = loginUser(req);
		String cp = req.getContextPath();
		if(info==null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		AcademyDAO dao = new AcademyDAO();
		AcademyDTO dto = new AcademyDTO();
		
		dto.setUserId(info.getUserId());
		dto.setAcaNum(Integer.parseInt(req.getParameter("acaNum")));
		dto.setAcaName(req.getParameter("acaName"));
		dto.setAcaDiv(req.getParameter("acaDiv"));
		dto.setAcaIntro(req.getParameter("acaIntro"));
		dto.setAcaAddress(req.getParameter("acaAddress"));
		dto.setAcaWeb(req.getParameter("acaWeb"));
		dto.setAcaTel(req.getParameter("acaTel"));
		
		dao.updateAcademy(dto);
		
		resp.sendRedirect(cp+"/acs/list.do");
	}
	
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SessionInfo info = loginUser(req);
		String cp = req.getContextPath();
		
		if(info==null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		String page = req.getParameter("page");
		int num = Integer.parseInt(req.getParameter("academyNum"));
		String condition=req.getParameter("condition");
		String keyword=req.getParameter("keyword");
		if(condition==null) {
			condition="acaName";
			keyword="";
		}
		keyword=URLDecoder.decode(keyword, "utf-8");
		
		String query="page="+page;
		if(keyword.length()!=0) {
			query+="&condition="+condition+"&keyword="+URLEncoder.encode(keyword, "UTF-8");
		}
		
		AcademyDAO dao = new AcademyDAO();
		AcademyDTO dto = dao.readAcademy(num);
		if(dto==null) {
			resp.sendRedirect(cp+"/acs/list.do?"+query);
			return;
		}
		dao.deleteAcademy(num, info.getUserId());
		
		resp.sendRedirect(cp+"/acs/list.do?num="+num+"&page="+page);
		
		
	}
}
