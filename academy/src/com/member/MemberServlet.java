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

		// uri에 따른 작업 구분
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
		// 로그인 폼
		String path = "/WEB-INF/views/member/login.jsp";
		forward(req, resp, path);
	}

	private void loginSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 로그인 처리
		// 세선 객체. 세선 정보는 서버에 저장(로그인 정보, 권한 등) 너무 많은 정보는 저장 x

		HttpSession session = req.getSession();

		MemberDAO dao = new MemberDAO();
		String cp = req.getContextPath();

		String userId = req.getParameter("userId");
		String userPwd = req.getParameter("userPwd");

		MemberDTO dto = dao.readMember(userId);
		if (dto != null) {
			if (dto.getUserPwd().equals(userPwd)) { // 비밀번호가 일치하면
				// 로그인 성공
				// 세션의 유지시간을 20분으로 설정(기본 : 30분)
				session.setMaxInactiveInterval(20 * 60); // 초단위

				// 세션에 로그인 정보 저장
				SessionInfo info = new SessionInfo();
				info.setUserId(dto.getUserId());
				info.setUserName(dto.getUserName());

				// 세션에 member이라는 이름으로 session info 객체를 저장
				session.setAttribute("member", info);
				
				int num = 0;
				num = (int)(Math.random()*53)+1;
				SayingDAO dao2 = new SayingDAO();
				SayingDTO dto2 = dao2.readSaying(num);
				info.setWiseSaying(dto2.getWiseSaying());
				
				req.setAttribute("num", num);	
				req.setAttribute("dto2", dto2);
			
				
				// 메인화면으로 리다이렉트
				resp.sendRedirect(cp);
				return;
			}
		}

		// 로그인이 실패한 경우
		String msg = "아이디 또는 패스워드가 일치하지 않습니다.";
		req.setAttribute("message", msg);

		forward(req, resp, "/WEB-INF/views/member/login.jsp"); // 포워드 할 떄 /는 cp 포함

	}

	private void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 로그아웃
		HttpSession session = req.getSession();
		String cp = req.getContextPath();

		// 세션에 저장된 정보 지우기
		session.removeAttribute("member");

		// 세션 초기화
		session.invalidate();

		// 메인화면으로 리다이렉트
		resp.sendRedirect(cp);
	}

	private void memberForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 회원가입폼

		req.setAttribute("title", "회원 가입");
		req.setAttribute("mode", "created");

		String path = "/WEB-INF/views/member/member.jsp";

		forward(req, resp, path);
	}

	private void memberSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 회원가입 처리

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

		// 회원가입이 100% 성공한다는 보장 x 그래서 예외처리 해야함
		try {
			dao.insertMember(dto);
		} catch (Exception e) {
			String message = "회원 가입이 실패했습니다.";

			req.setAttribute("title", "회원 가입");
			req.setAttribute("mode", "created");
			req.setAttribute("message", message);

			forward(req, resp, "/WEB-INF/views/member/member.jsp");
			return;
		}

		// insert, delete, update할 떄 포워딩 안하는 것이 원칙.
		StringBuffer sb = new StringBuffer();
		sb.append("<b>" + dto.getUserName() + " <b>님의 회원가입이 완료되었습니다.<br>");
		sb.append("메인화면으로 이동하여 로그인하시기 바랍니다.<br>");

		req.setAttribute("title", "회원가입 완료");
		req.setAttribute("message", sb.toString());

		forward(req, resp, "/WEB-INF/views/member/complete.jsp");

		// main 화면으로 리다이렉트
//		  String cp = req.getContextPath(); String url = cp;
//		  resp.sendRedirect(url);

	}

	private void userIdCheck(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 아이디 중복 검사
		String userId = req.getParameter("userId");
		MemberDAO dao = new MemberDAO();
		MemberDTO dto = dao.readMember(userId);
		String passed = "false";

		if (dto == null)
			passed = "true";

		// json : {"키1" : "값1", "키2" : "값2", ...}의 형태
		JSONObject job = new JSONObject();
		job.put("passed", passed);

		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		out.print(job.toString());
	}

	private void termsOfUse(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 회원약관 폼
		String path = "/WEB-INF/views/member/termsofuse.jsp";
		forward(req, resp, path);
	}

	private void pwdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 패스워드 확인 폼

		HttpSession session = req.getSession();
		String cp = req.getContextPath();

		SessionInfo info = (SessionInfo) session.getAttribute("member");
		if (info == null) {
			resp.sendRedirect(cp + "/member/login.do");
			return;
		}

		String mode = req.getParameter("mode");
		if (mode.equals("update")) {
			req.setAttribute("title", "회원 정보 수정");
		} else {
			req.setAttribute("title", "회원 탈퇴");
		}
		req.setAttribute("mode", mode);

		forward(req, resp, "/WEB-INF/views/member/pwd.jsp");

	}

	private void pwdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 패스워드 확인

		HttpSession session = req.getSession();
		String cp = req.getContextPath();

		SessionInfo info = (SessionInfo) session.getAttribute("member");
		if (info == null) { // 로그인이 안된 경우 resp.sendRedirect(cp+"/member/login.do");
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
				req.setAttribute("title", "회원 정보 수정");
			}
			req.setAttribute("mode", mode);
			req.setAttribute("message", "<span style='color:red;'>패스워드가 일치하지 않습니다.</span>");

			forward(req, resp, "/WEB-INF/views/member/pwd.jsp");
			return;
		}

		req.setAttribute("title", "회원 정보 수정");
		req.setAttribute("dto", dto);
		req.setAttribute("mode", "update");

		forward(req, resp, "/WEB-INF/views/member/member.jsp");
		return;

	}

	private void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 회원정보 수정 완료

		HttpSession session = req.getSession();
		String cp = req.getContextPath();

		SessionInfo info = (SessionInfo) session.getAttribute("member");
		if (info == null) { // 로그인이 안된 경우 resp.sendRedirect(cp+"/member/login.do");
			return;
		}

		// 회원 정보 수정

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
			String message = "회원 수정이 실패했습니다.";

			req.setAttribute("title", "회원 정보 수정");
			req.setAttribute("mode", "update");
			req.setAttribute("message", message);

			forward(req, resp, "/WEB-INF/views/member/member.jsp");
			return;
		}

		resp.sendRedirect(cp);

		/*
		 * StringBuffer sb = new StringBuffer();
		 * sb.append("<b>"+dto.getUserName()+" <b>님 회원 정보 수정이 완료되었습니다.<br>");
		 * 
		 * req.setAttribute("title", "회원 정보 수정"); req.setAttribute("message",
		 * sb.toString());
		 * 
		 * forward(req,resp,"/WEB-INF/views/member/complete.jsp"); //포워딩 하면 실행 전의 주소가
		 * 주소창에 출력
		 */
	}

	private void interLecture(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 관심강좌
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
		// 수강중인 강좌
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

		// 세션에 저장된 정보 지우기
		session.removeAttribute("member");

		// 세션 초기화
		session.invalidate();

		PrintWriter out = resp.getWriter();
		out.println("<script>alert('탈퇴완료'); location.href='" + cp + "';</script>");

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
			req.setAttribute("message", "회원님의 ID는 "+foundUserId+"입니다.");
		} else {
			req.setAttribute("message", "일치하는 ID가 없습니다.");
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
			req.setAttribute("message","회원님의 비밀번호는 "+foundUserPwd+"입니다.");
		} else {
			req.setAttribute("message", "일치하는 비밀번호가 없습니다.");
		}
		
		req.setAttribute("userId", userId);
		req.setAttribute("birth", birth);
		req.setAttribute("userName", userName);
		req.setAttribute("email", email);
				
		String path = "/WEB-INF/views/member/find.jsp";
		forward(req, resp, path);

	}
}
