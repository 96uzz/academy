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
import com.qna.QnaDAO;
import com.qna.QnaDTO;
import com.util.MyServlet;

@WebServlet("/main/*")
public class MainServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri=req.getRequestURI();
		
		if(uri.indexOf("main.do")!=-1) {
			main(req, resp);
		}
	}
	
	protected void main(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		NoticeDAO dao1 = new NoticeDAO();
		BoardDAO dao2 = new BoardDAO();
		QnaDAO dao3	= new QnaDAO();
		
		String cp = req.getContextPath();
		
		List<NoticeDTO> noticeList;
		noticeList = dao1.listNotice(0,5);
		String noticeUrl = cp+"/notice/article.do?page=1";
		
		List<BoardDTO> boardList;
		boardList = dao2.listBoard(0,5);
		String boardUrl = cp+"/board/article.do?page=1";
		
		List<QnaDTO> qnaList;
		qnaList = dao3.listQna(0, 5);
		String qnaUrl = cp+"/qna/article.do?page=1";
		
		req.setAttribute("noticeList", noticeList);
		req.setAttribute("noticeUrl", noticeUrl);
		req.setAttribute("boardList", boardList);
		req.setAttribute("boardUrl", boardUrl);
		req.setAttribute("qnaList", qnaList);
		req.setAttribute("qnaUrl", qnaUrl);
		
		forward(req, resp, "/WEB-INF/views/main/main.jsp");
	}


}
