package com.member;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.saying.SayingDAO;
import com.saying.SayingDTO;
import com.util.MyServlet;

import net.sf.json.JSONObject;

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
		} else if (uri.indexOf("pwd.do") != -1) {
			pwdForm(req, resp);
		} else if (uri.indexOf("pwd_ok.do") != -1) {
			pwdSubmit(req, resp);
		} else if (uri.indexOf("update_ok.do") != -1) {
			updateSubmit(req, resp);
		} else if (uri.indexOf("interlecture.do") != -1) {
			interLecture(req, resp);
		} else if (uri.indexOf("takinglecture.do") != -1) {
			takingLecture(req, resp);
		} else if (uri.indexOf("delete.do") != -1) {
			delete(req, resp);
		} else if (uri.indexOf("find.do") != -1) {
			find(req, resp);
		}  else if (uri.indexOf("finduserid.do") != -1) {
			findUserId(req, resp);
		} else if (uri.indexOf("findpwd.do") != -1) {
			findUserPwd(req, resp);
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
				
				int num = 0;
				num = (int)(Math.random()*53)+1;
				SayingDAO dao2 = new SayingDAO();
				SayingDTO dto2 = dao2.readSaying(num);
				info.setWiseSaying(dto2.getWiseSaying());
				
				req.setAttribute("num", num);	
				req.setAttribute("dto2", dto2);
			
				
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

		// insert, delete, update�� �� ������ ���ϴ� ���� ��Ģ.
		StringBuffer sb = new StringBuffer();
		sb.append("<b>" + dto.getUserName() + " <b>���� ȸ�������� �Ϸ�Ǿ����ϴ�.<br>");
		sb.append("����ȭ������ �̵��Ͽ� �α����Ͻñ� �ٶ��ϴ�.<br>");

		req.setAttribute("title", "ȸ������ �Ϸ�");
		req.setAttribute("message", sb.toString());

		forward(req, resp, "/WEB-INF/views/member/complete.jsp");

		// main ȭ������ �����̷�Ʈ
//		  String cp = req.getContextPath(); String url = cp;
//		  resp.sendRedirect(url);

	}

	private void userIdCheck(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// ���̵� �ߺ� �˻�
		String userId = req.getParameter("userId");
		MemberDAO dao = new MemberDAO();
		MemberDTO dto = dao.readMember(userId);
		String passed = "false";

		if (dto == null)
			passed = "true";

		// json : {"Ű1" : "��1", "Ű2" : "��2", ...}�� ����
		JSONObject job = new JSONObject();
		job.put("passed", passed);

		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		out.print(job.toString());
	}

	private void termsOfUse(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// ȸ����� ��
		String path = "/WEB-INF/views/member/termsofuse.jsp";
		forward(req, resp, path);
	}

	private void pwdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// �н����� Ȯ�� ��

		HttpSession session = req.getSession();
		String cp = req.getContextPath();

		SessionInfo info = (SessionInfo) session.getAttribute("member");
		if (info == null) {
			resp.sendRedirect(cp + "/member/login.do");
			return;
		}

		String mode = req.getParameter("mode");
		if (mode.equals("update")) {
			req.setAttribute("title", "ȸ�� ���� ����");
		} else {
			req.setAttribute("title", "ȸ�� Ż��");
		}
		req.setAttribute("mode", mode);

		forward(req, resp, "/WEB-INF/views/member/pwd.jsp");

	}

	private void pwdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// �н����� Ȯ��

		HttpSession session = req.getSession();
		String cp = req.getContextPath();

		SessionInfo info = (SessionInfo) session.getAttribute("member");
		if (info == null) { // �α����� �ȵ� ��� resp.sendRedirect(cp+"/member/login.do");
			resp.sendRedirect(cp + "/member/login.do");
			return;
		}

		MemberDAO dao = new MemberDAO();
		MemberDTO dto = dao.readMember(info.getUserId());

		if (dto == null) {
			session.invalidate();
			resp.sendRedirect(cp);
			return;
		}

		String userPwd = req.getParameter("userPwd");
		String mode = req.getParameter("mode");
		if (!dto.getUserPwd().equals(userPwd)) {
			if (mode.equals("update")) {
				req.setAttribute("title", "ȸ�� ���� ����");
			}
			req.setAttribute("mode", mode);
			req.setAttribute("message", "<span style='color:red;'>�н����尡 ��ġ���� �ʽ��ϴ�.</span>");

			forward(req, resp, "/WEB-INF/views/member/pwd.jsp");
			return;
		}

		req.setAttribute("title", "ȸ�� ���� ����");
		req.setAttribute("dto", dto);
		req.setAttribute("mode", "update");

		forward(req, resp, "/WEB-INF/views/member/member.jsp");
		return;

	}

	private void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// ȸ������ ���� �Ϸ�

		HttpSession session = req.getSession();
		String cp = req.getContextPath();

		SessionInfo info = (SessionInfo) session.getAttribute("member");
		if (info == null) { // �α����� �ȵ� ��� resp.sendRedirect(cp+"/member/login.do");
			return;
		}

		// ȸ�� ���� ����

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

		try {
			dao.updateMember(dto);
		} catch (Exception e) {
			String message = "ȸ�� ������ �����߽��ϴ�.";

			req.setAttribute("title", "ȸ�� ���� ����");
			req.setAttribute("mode", "update");
			req.setAttribute("message", message);

			forward(req, resp, "/WEB-INF/views/member/member.jsp");
			return;
		}

		resp.sendRedirect(cp);

		/*
		 * StringBuffer sb = new StringBuffer();
		 * sb.append("<b>"+dto.getUserName()+" <b>�� ȸ�� ���� ������ �Ϸ�Ǿ����ϴ�.<br>");
		 * 
		 * req.setAttribute("title", "ȸ�� ���� ����"); req.setAttribute("message",
		 * sb.toString());
		 * 
		 * forward(req,resp,"/WEB-INF/views/member/complete.jsp"); //������ �ϸ� ���� ���� �ּҰ�
		 * �ּ�â�� ���
		 */
	}

	private void interLecture(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// ���ɰ���
		HttpSession session = req.getSession();
		String cp = req.getContextPath();

		SessionInfo info = (SessionInfo) session.getAttribute("member");
		if (info == null) {
			resp.sendRedirect(cp + "/member/login.do");
			return;
		}

		String path = "/WEB-INF/views/member/interlecture.jsp";
		forward(req, resp, path);

	}

	private void takingLecture(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// �������� ����
		HttpSession session = req.getSession();
		String cp = req.getContextPath();

		SessionInfo info = (SessionInfo) session.getAttribute("member");
		if (info == null) {
			resp.sendRedirect(cp + "/member/login.do");
			return;
		}
		String userId = info.getUserId();
		MemberDAO dao = new MemberDAO();
		MemberDTO dto = dao.takingLecList(userId);

		req.setAttribute("dto", dto);

		String path = "/WEB-INF/views/member/takinglecture.jsp";
		forward(req, resp, path);
	}

	private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=UTF-8");
		String cp = req.getContextPath();

		String userId = req.getParameter("userId");
		MemberDAO dao = new MemberDAO();
		dao.deleteMember(userId);

		HttpSession session = req.getSession();

		// ���ǿ� ����� ���� �����
		session.removeAttribute("member");

		// ���� �ʱ�ȭ
		session.invalidate();

		PrintWriter out = resp.getWriter();
		out.println("<script>alert('Ż��Ϸ�'); location.href='" + cp + "';</script>");

	}

	private void find(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = "/WEB-INF/views/member/find.jsp";
		forward(req, resp, path);

	}
	
	private void findUserId(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userId = req.getParameter("userId");
		String birth = req.getParameter("birth");
		String userName = req.getParameter("userName");
		String email = req.getParameter("email");	
		
		MemberDAO dao = new MemberDAO();
		
		String foundUserId = dao.findUserId(userName, birth);
		if(foundUserId!=null) {
			req.setAttribute("message", "ȸ������ ID�� "+foundUserId+"�Դϴ�.");
		} else {
			req.setAttribute("message", "��ġ�ϴ� ID�� �����ϴ�.");
		}
		req.setAttribute("userId", userId);
		req.setAttribute("birth", birth);
		req.setAttribute("userName", userName);
		req.setAttribute("email", email);
				
		String path = "/WEB-INF/views/member/find.jsp";
		forward(req, resp, path);

	}

	private void findUserPwd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userId = req.getParameter("userId");
		String birth = req.getParameter("birth");
		String userName = req.getParameter("userName");
		String email = req.getParameter("email");	
		
		MemberDAO dao = new MemberDAO();
		
		String foundUserPwd = dao.findUserPwd(userId, email);
		if(foundUserPwd!=null) {
			req.setAttribute("message","ȸ������ ��й�ȣ�� "+foundUserPwd+"�Դϴ�.");
		} else {
			req.setAttribute("message", "��ġ�ϴ� ��й�ȣ�� �����ϴ�.");
		}
		
		req.setAttribute("userId", userId);
		req.setAttribute("birth", birth);
		req.setAttribute("userName", userName);
		req.setAttribute("email", email);
				
		String path = "/WEB-INF/views/member/find.jsp";
		forward(req, resp, path);

	}
}
