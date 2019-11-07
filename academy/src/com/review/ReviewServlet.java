package com.review;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;

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

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		if (info == null) {
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		}

		String uri = req.getRequestURI();

		if (uri.indexOf("list.do") != -1) {
			list(req, resp);
		} else if (uri.indexOf("review.do") != -1) {
			review(req, resp);
		} else if (uri.indexOf("createdreview_ok.do") != -1) {
			reviewCreatedSubmit(req, resp);
		} else if (uri.indexOf("deletereview.do") != -1) {
			reviewDelete(req, resp);
		}

	}

	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
	}


	protected void review(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

	protected void reviewCreatedSubmit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

	}

	protected void reviewDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
}
