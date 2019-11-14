package com.notice;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.util.FileManager;
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
		} else if (uri.indexOf("delete.do")!=-1) {
			delete(req, resp);
		} else if (uri.indexOf("deleteFile.do")!=-1) {
			deleteFile(req, resp);
		} else if (uri.indexOf("download.do")!=-1) {
			download(req, resp);
		}
	}
	
	// ������ ����Ǵ� ���
	private String getFilePathname(HttpServletRequest req) throws ServletException {
		String s=null;
		
		HttpSession session=req.getSession();
		String root=session.getServletContext().getRealPath("/"); // ������ �Խñ��� ������ ���
		s=root+"uploads"+File.separator+"notice";
		
		return s;
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
		
		// ������
//		List<NoticeDTO> listNotice=null;
//		if(current_page==1) {
//			listNotice=dao.listNotice();
//			for(NoticeDTO dto : listNotice) {
//				dto.setCreated(dto.getCreated().substring(0,10));
//			}
//		}
		
		// SimpleDateFormat : ���ڿ��� ��¥�� / ��¥�� ���ڿ���
		long gap;
		Date curDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		// ����Ʈ �۹�ȣ �����
		int listNum, n=0;
		for(NoticeDTO dto : list) {
			listNum = dataCount-(offset+n);
			dto.setListNum(listNum);
			
			try {
				Date date=sdf.parse(dto.getCreated());
				// gap=(curDate.getTime()-date.getTime()/(1000*60*60*24)); // �Ϸ�
				gap=(curDate.getTime()-date.getTime())/(1000*60*60); // �ѽð�
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
//		req.setAttribute("listNotice", listNotice);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("total_page", total_page);
		req.setAttribute("rows", rows); // �� ȭ�鿡 ���̴� ����
		req.setAttribute("condition", condition);
		req.setAttribute("keyword", keyword);
		req.setAttribute("articleUrl", articleUrl);
		
		forward(req, resp, "/WEB-INF/views/notice/list.jsp");
		
	}
	
	protected void createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SessionInfo info=loginUser(req);
		String cp=req.getContextPath();
		if(info==null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		if(! info.getUserId().equals("admin")) {
			resp.sendRedirect(cp+"/notice/list.do");
			return;
		}
		
		req.setAttribute("mode", "created");
		forward(req, resp, "/WEB-INF/views/notice/created.jsp");
	}

	protected void createdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SessionInfo info=loginUser(req);
		String cp=req.getContextPath();
		
		if(info==null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		String pathname=getFilePathname(req);
		File f=new File(pathname);
		if(! f.exists()) {
			f.mkdirs();
		}
		String encType="utf-8";
		int maxSize=5*1024*1024;
		
		MultipartRequest mreq=null;
		mreq=new MultipartRequest(req, pathname, maxSize, encType, new DefaultFileRenamePolicy());
		
		// �Ķ���� �Ѱ� �޾� ��� ����
		NoticeDTO dto=new NoticeDTO();
		NoticeDAO dao=new NoticeDAO();
		
		if(mreq.getParameter("notice")!=null) {
			dto.setNotice(Integer.parseInt(mreq.getParameter("notice")));
		}
		dto.setUserId(info.getUserId());
		dto.setSubject(mreq.getParameter("subject"));
		dto.setContent(mreq.getParameter("content"));
		
		if(mreq.getFile("upload")!=null) { // ������ �������� ���� ���� �ֱ� ������
			dto.setSaveFilename(mreq.getFilesystemName("upload"));
			dto.setOriginalFilename(mreq.getOriginalFileName("upload"));
			dto.setFilesize(mreq.getFile("upload").length());
		}
		
		dao.insertNotice(dto);
		
		resp.sendRedirect(cp+"/notice/list.do");
		
	}
	
	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// �ۺ���
		
		NoticeDAO dao=new NoticeDAO();
		String cp=req.getContextPath();
			
		int noticeNum=Integer.parseInt(req.getParameter("noticeNum"));
		String page=req.getParameter("page");
			
		String condition=req.getParameter("condition");
		String keyword=req.getParameter("keyword");
		if(condition==null) {
				condition="subject";
				keyword="";
		}
		keyword=URLDecoder.decode(keyword, "utf-8");

		String query="page="+page;
		if(keyword.length()!=0) {
			query+="&condition="+condition+"&keyword="+URLEncoder.encode(keyword, "UTF-8");
		}

		// ��ȸ��
		dao.updateHitCount(noticeNum);
		
		// �Խù� ��������
		NoticeDTO dto=dao.readNotice(noticeNum);
		if(dto==null) {
			resp.sendRedirect(cp+"/notice/list.do?"+query);
			return;
		}
		
		dto.setContent(dto.getContent().replaceAll("\n", "<br>"));
		
		// ������/������
		NoticeDTO preReadDto = dao.preReadNotice(dto.getNoticeNum(), condition, keyword);
		NoticeDTO nextReadDto = dao.nextReadNotice(dto.getNoticeNum(), condition, keyword);
		
		req.setAttribute("dto", dto);
		req.setAttribute("preReadDto", preReadDto);
		req.setAttribute("nextReadDto", nextReadDto);
		req.setAttribute("query", query);
		req.setAttribute("page", page);
		
		forward(req, resp, "/WEB-INF/views/notice/article.jsp");	
	}
	
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SessionInfo info=loginUser(req);
		String cp=req.getContextPath();
		
		if(info==null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		String page=req.getParameter("page");
		int noticeNum=Integer.parseInt(req.getParameter("noticeNum"));
		
		NoticeDAO dao=new NoticeDAO();
		NoticeDTO dto=dao.readNotice(noticeNum);
		if(dto==null || ! info.getUserId().equals(dto.getUserId())) {
			resp.sendRedirect(cp+"/member/login.do?page="+page);
			return;
		}
		
		req.setAttribute("mode", "update");
		req.setAttribute("dto", dto);
		req.setAttribute("page", page);
		
		forward(req, resp, "/WEB-INF/views/notice/created.jsp");
		
	}
	
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SessionInfo info=loginUser(req);
		String cp=req.getContextPath();
		
		if(info==null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}

		String pathname=getFilePathname(req);
		File f=new File(pathname);
		if(! f.exists()) {
			f.mkdirs();
		}
		
		String encType="utf-8";
		int maxSize=5*1024*1024;
		
		MultipartRequest mreq=null;
		mreq=new MultipartRequest(req, pathname, maxSize, encType, new DefaultFileRenamePolicy());	
		
		NoticeDAO dao=new NoticeDAO();
		NoticeDTO dto=new NoticeDTO();

		dto.setUserId(info.getUserId());
		dto.setNoticeNum(Integer.parseInt(mreq.getParameter("noticeNum")));
		if(mreq.getParameter("notice")!=null) {
		dto.setNotice(Integer.parseInt(mreq.getParameter("notice")));
		}
		dto.setSubject(mreq.getParameter("subject"));
		dto.setContent(mreq.getParameter("content"));
		dto.setSaveFilename(mreq.getParameter("saveFilename"));
		dto.setOriginalFilename(mreq.getParameter("originalFilename"));
		dto.setFilesize(Long.parseLong(mreq.getParameter("filesize")));
		
		if(mreq.getFile("upload")!=null) { // ÷�� ������ �������
			if(dto.getSaveFilename().length()!=0) { // ���� ������ ������ ���������
				FileManager.doFiledelete(pathname, dto.getSaveFilename());
			}
			dto.setSaveFilename(mreq.getFilesystemName("upload"));
			dto.setOriginalFilename(mreq.getOriginalFileName("upload"));
			dto.setFilesize(mreq.getFile("upload").length());
		}
		
		String page=mreq.getParameter("page");
		dao.updateNotice(dto);
		resp.sendRedirect(cp+"/notice/list.do?page="+page);
	}
	
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SessionInfo info=loginUser(req);
		String cp=req.getContextPath();
		
		if(info==null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		int noticeNum=Integer.parseInt(req.getParameter("noticeNum"));
		String page=req.getParameter("page");
		String condition=req.getParameter("condition");
		String keyword=req.getParameter("keyword");
		if(condition==null) {
			condition="subject";
			keyword="";
		}
		keyword=URLDecoder.decode(keyword, "utf-8");
		
		String query="page"+page;
		if(keyword.length()!=0) {
			query+="&condition="+condition+"&keyword="+URLEncoder.encode(keyword,"utf-8");
		}
		
		String pathname=getFilePathname(req);
		NoticeDAO dao=new NoticeDAO();
		NoticeDTO dto=dao.readNotice(noticeNum);
		if(dto==null) {
			resp.sendRedirect(cp+"/notice/list.do?"+query);
			return;
		}
		if(dto.getSaveFilename()!=null) {
			FileManager
			.doFiledelete(pathname, dto.getSaveFilename());
		}
		
		dao.deleteNotice(noticeNum, info.getUserId());
		
		resp.sendRedirect(cp+"/notice/list.do?"+query);

	}
	
	private void download(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		NoticeDAO dao = new NoticeDAO();
		String pathname = getFilePathname(req);
		
		int noticeNum = Integer.parseInt(req.getParameter("noticeNum"));
		boolean b = false;
		NoticeDTO dto = dao.readNotice(noticeNum);
		
		if(dto!=null) {
			b=FileManager.doFiledownload(dto.getSaveFilename(), dto.getOriginalFilename(), pathname, resp);
		}
		
		// ������ ������ �ٿ�ε尡 ���� �����Ƿ� �̶� ���â�� ����ش�. history.back(); = �ڷε��ư���
		if(! b) {
			resp.setContentType("text/html;charset=utf-8");
			PrintWriter out=resp.getWriter();
			out.print("<script>alert('���� �ٿ�ε忡 �����߽��ϴ�.');history.back();</script>");
		}
	}

	private void deleteFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SessionInfo info=loginUser(req);
		String cp=req.getContextPath();
		
		if(info==null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		int noticeNum = Integer.parseInt(req.getParameter("noticeNum"));
		String page=req.getParameter("page");
		String pathname = getFilePathname(req);
		
		NoticeDAO dao = new NoticeDAO();
		NoticeDTO dto = dao.readNotice(noticeNum);
		
		// ���� ������ ������ ������ ����� DB������ ���������
		if(dto!=null&&dto.getSaveFilename()!=null) {
			FileManager.doFiledelete(pathname, dto.getSaveFilename());
			dto.setSaveFilename("");
			dto.setOriginalFilename("");
			dto.setFilesize(0);
			dao.updateNotice(dto);
		}
		
		resp.sendRedirect(cp+"/notice/update.do?noticeNum="+noticeNum+"&page="+page);
		
	}
}
