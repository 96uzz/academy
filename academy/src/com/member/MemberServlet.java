package com.member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.util.MyServlet;

@WebServlet("/member/*")
public class MemberServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");

		String uri = req.getRequestURI();

		// uri�� ���� �۾� ����
		if (uri.indexOf("login.do") != -1) {
			loginForm(req, resp);
		} else if (uri.indexOf("login_ok.do") != -1) {
			loginSubmit(req, resp);
		} else if (uri.indexOf("logout.do") != -1) {
			logout(req, resp);
		} else if (uri.indexOf("member.do") != -1) {
			memberForm(req, resp);
		} else if (uri.indexOf("member_ok.do") != -1) {
			memberSubmit(req, resp);
		} else if (uri.indexOf("userIdCheck.do") != -1) {
			userIdCheck(req, resp);
		} else if (uri.indexOf("termsOfUse.do") != -1) {
			termsOfUse(req, resp);
		}
	}

	private void loginForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// �α��� ��
		String path = "/WEB-INF/views/member/login.jsp";
		forward(req, resp, path);
	}

	private void loginSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// �α��� ó��
		// ���� ��ü. ���� ������ ������ ����(�α��� ����, ���� ��) �ʹ� ���� ������ ���� x

		HttpSession session = req.getSession();

		MemberDAO dao = new MemberDAO();
		String cp = req.getContextPath();

		String userId = req.getParameter("userId");
		String userPwd = req.getParameter("userPwd");

		MemberDTO dto = dao.readMember(userId);
		if (dto != null) {
			if (dto.getUserPwd().equals(userPwd)) { // ��й�ȣ�� ��ġ�ϸ�
				// �α��� ����
				// ������ �����ð��� 20������ ����(�⺻ : 30��)
				session.setMaxInactiveInterval(20 * 60); // �ʴ���

				// ���ǿ� �α��� ���� ����
				SessionInfo info = new SessionInfo();
				info.setUserId(dto.getUserId());
				info.setUserName(dto.getUserName());

				// ���ǿ� member�̶�� �̸����� session info ��ü�� ����
				session.setAttribute("member", info);

				// ����ȭ������ �����̷�Ʈ
				resp.sendRedirect(cp);
				return;
			}
		}

		// �α����� ������ ���
		String msg = "���̵� �Ǵ� �н����尡 ��ġ���� �ʽ��ϴ�.";
		req.setAttribute("message", msg);

		forward(req, resp, "/WEB-INF/views/member/login.jsp"); // ������ �� �� /�� cp ����

	}

	private void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// �α׾ƿ�
		HttpSession session = req.getSession();
		String cp = req.getContextPath();

		// ���ǿ� ����� ���� �����
		session.removeAttribute("member");

		// ���� �ʱ�ȭ
		session.invalidate();

		// ����ȭ������ �����̷�Ʈ
		resp.sendRedirect(cp);
	}

	private void memberForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// ȸ��������

		req.setAttribute("title", "ȸ�� ����");
		req.setAttribute("mode", "created");

		String path = "/WEB-INF/views/member/member.jsp";

		forward(req, resp, path);
	}

	private void memberSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// ȸ������ ó��

		MemberDAO dao = new MemberDAO();
		MemberDTO dto = new MemberDTO();

		dto.setUserId(req.getParameter("userId"));
		dto.setUserName(req.getParameter("userName"));
		dto.setUserPwd(req.getParameter("userPwd"));

		dto.setBirth(req.getParameter("birth"));

		dto.setEmail1(req.getParameter("email1"));
		dto.setEmail2(req.getParameter("email2"));
		dto.setEmail(dto.getEmail1() + "@" + dto.getEmail2());

		dto.setTel1(req.getParameter("tel1"));
		dto.setTel2(req.getParameter("tel2"));
		dto.setTel3(req.getParameter("tel3"));
		dto.setTel(dto.getTel1() + "-" + dto.getTel2() + "-" + dto.getTel3());

		dto.setZip(req.getParameter("zip"));
		dto.setAddr1(req.getParameter("addr1"));
		dto.setAddr2(req.getParameter("addr2"));
		dto.setLecCode(req.getParameter("lecCode"));

		// ȸ�������� 100% �����Ѵٴ� ���� x �׷��� ����ó�� �ؾ���
		try {
			dao.insertMember(dto);
		} catch (Exception e) {
			String message = "ȸ�� ������ �����߽��ϴ�.";

			req.setAttribute("title", "ȸ�� ����");
			req.setAttribute("mode", "created");
			req.setAttribute("message", message);

			forward(req, resp, "/WEB-INF/views/member/member.jsp");
			return;
		}

		
		 //insert, delete, update�� �� ������ ���ϴ� ���� ��Ģ. 
		  StringBuffer sb = new StringBuffer();
		  sb.append("<b>"+dto.getUserName()+" <b>���� ȸ�������� �Ϸ�Ǿ����ϴ�.<br>");
		  sb.append("����ȭ������ �̵��Ͽ� �α����Ͻñ� �ٶ��ϴ�.<br>");
		  
		  req.setAttribute("title", "ȸ������ �Ϸ�");
		  req.setAttribute("message", sb.toString());
		  
		  forward(req, resp,"/WEB-INF/views/member/complete.jsp");
		  
		 
		  // main ȭ������ �����̷�Ʈ 
//		  String cp = req.getContextPath(); String url = cp;
//		  resp.sendRedirect(url);
		  
		 

	}

	
	private void userIdCheck(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// ���̵� �ߺ� �˻�

	}

	private void termsOfUse(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// ȸ����� ��
		String path = "/WEB-INF/views/member/termsofuse.jsp";
		forward(req, resp, path);
	}

}
