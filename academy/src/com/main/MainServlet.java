package com.main;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.BoardDAO;
import com.board.BoardDTO;
import com.notice.NoticeDAO;
import com.notice.NoticeDTO;
import com.util.MyServlet;

@WebServlet("/main/*")
public class MainServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri=req.getRequestURI();
		
		if(uri.indexOf("main.do")!=-1) {
			noticeList(req, resp);
		}else if(uri.indexOf("boardMain.do")!=-1) {
			boardList(req, resp);
		}else if(uri.indexOf("qnaMain.do")!=-1) {
			qnaList(req, resp);
		}
	}
	
	protected void noticeList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		NoticeDAO dao = new NoticeDAO();
		String cp = req.getContextPath();
		
		List<NoticeDTO> list;
		list = dao.listNotice();
		
		String articleUrl = cp+"/notice/article.do?page=1";

		req.setAttribute("list", list);
		req.setAttribute("articleUrl", articleUrl);
		
		forward(req, resp, "/WEB-INF/views/main/main.jsp");
		
	}
	
	protected void boardList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BoardDAO dao = new BoardDAO();
		String cp = req.getContextPath();
		
		
		List<BoardDTO> list;
		list = dao.listBoard(0, 5);
		
		String articleUrl = cp+"/board/article.do?page=1";

		req.setAttribute("list", list);
		req.setAttribute("articleUrl", articleUrl);
		
		forward(req, resp, "/WEB-INF/views/main/main.jsp");
		
	}
	
	protected void qnaList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		NoticeDAO dao = new NoticeDAO();
		String cp = req.getContextPath();
		
		List<NoticeDTO> list;
		list = dao.listNotice();
		
		String articleUrl = cp+"/notice/article.do?page=1";

		req.setAttribute("list", list);
		req.setAttribute("articleUrl", articleUrl);
		
		forward(req, resp, "/WEB-INF/views/main/main.jsp");
		
	}
}
