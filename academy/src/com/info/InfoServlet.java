package com.info;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.MyServlet;

@WebServlet("/info/*")
public class InfoServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override

	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");

		String cp = req.getContextPath();

		String uri = req.getRequestURI();
		if (uri.indexOf("infoWeb.do") != -1) {
			infoWeb(req, resp);
		} else if(uri.indexOf("history.do")!=-1) {
			history(req, resp);
		} else if(uri.indexOf("group.do")!=-1) {
			group(req, resp);
		} else if(uri.indexOf("direction.do")!=-1) {
			direction(req, resp);
		}
	}

	protected void infoWeb(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		forward(req, resp, "/WEB-INF/views/info/infoWeb.jsp");
	}
	
	protected void history(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		forward(req, resp, "/WEB-INF/views/info/history.jsp");
	}
	
	protected void group(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		forward(req, resp, "/WEB-INF/views/info/group.jsp");
	}
	
	protected void direction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		forward(req, resp, "/WEB-INF/views/info/direction.jsp");
	}

}
