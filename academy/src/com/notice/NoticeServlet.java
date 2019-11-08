package com.notice;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.util.MyServlet;
import com.util.MyUtil;

@WebServlet("/notice/*")
public class NoticeServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri=req.getRequestURI();
		if(uri.indexOf("list.do")!=-1) {
			list(req, resp);
		} else if (uri.indexOf("created.do")!=-1) {
			createdForm(req, resp);
		} else if (uri.indexOf("created_ok.do")!=-1) {
			createdSubmit(req, resp);
		} else if (uri.indexOf("article.do")!=-1) {
			article(req, resp);
		} else if (uri.indexOf("update.do")!=-1) {
			updateForm(req, resp);
		} else if (uri.indexOf("update_ok.do")!=-1) {
			updateSubmit(req, resp);
		}  else if (uri.indexOf("delete.do")!=-1) {
			delete(req, resp);
		}  
	}
	
	private SessionInfo loginUser(HttpServletRequest req) throws ServletException {
		SessionInfo info=null;
		HttpSession session=req.getSession();
		
		info=(SessionInfo)session.getAttribute("member");
		
		return info;
	}

	
	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		NoticeDAO dao = new NoticeDAO();
		MyUtil util = new MyUtil();
		String cp = req.getContextPath();
		
		String page = req.getParameter("page");
		int current_page=1;
		if(page!=null) {
			current_page=Integer.parseInt(page);
		}
		
		int rows = 10;
		String srows = req.getParameter("rows");
		if(srows!=null) {
			rows = Integer.parseInt(srows);
		}
		
		String condition = req.getParameter("condition");
		String keyword = req.getParameter("keyword");
		if(condition==null) {
			condition="subject";
			keyword="";
		}
		if(req.getMethod().equalsIgnoreCase("GET")) {
			keyword=URLDecoder.decode(keyword, "utf-8");
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
		
		List<NoticeDTO> list;
		if(keyword.length()==0) {
			list = dao.listNotice(offset, rows);
		} else {
			list = dao.listNotice(offset, rows, condition, keyword);
		}
		
		// 공지글
		List<NoticeDTO> listNotice=null;
		if(current_page==1) {
			listNotice=dao.listNotice();
			for(NoticeDTO dto : listNotice) {
				dto.setCreated(dto.getCreated().substring(0,10));
			}
		}
		
		// SimpleDateFormat : 문자열을 날짜로 / 날짜를 문자열로
		long gap;
		Date curDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		// 리스트 글번호 만들기
		int listNum, n=0;
		for(NoticeDTO dto : list) {
			listNum = dataCount-(offset+n);
			dto.setListNum(listNum);
			
			try {
				Date date=sdf.parse(dto.getCreated());
				// gap=(curDate.getTime()-date.getTime()/(1000*60*60*24)); // 하루
				gap=(curDate.getTime()-date.getTime())/(1000*60*60); // 한시간
				dto.setGap(gap);
			} catch (Exception e) {
			}
			dto.setCreated(dto.getCreated().substring(0,10));
			n++;
		}
		
		String query = "rows="+rows;
		if(keyword.length()!=0) {
			query+="&conditon="+condition+"&keyword="+URLEncoder.encode(keyword, "UTF-8");
		}
		
		String listUrl = cp+"/notice/list.do?"+query;
		String articleUrl = cp+"/notice/article.do?page="+current_page+"&"+query;
		
		
		String paging = util.paging(current_page, total_page, listUrl);
		
		req.setAttribute("list", list);
		req.setAttribute("paging", paging);
		req.setAttribute("page", current_page);
		req.setAttribute("listNotice", listNotice);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("total_page", total_page);
		req.setAttribute("rows", rows); // 한 화면에 보이는 갯수
		req.setAttribute("condition", condition);
		req.setAttribute("keyword", keyword);
		req.setAttribute("articleUrl", articleUrl);
		
		forward(req, resp, "/WEB-INF/views/notice/list.jsp");
		
	}
	
	protected void createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}

	protected void createdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
}
