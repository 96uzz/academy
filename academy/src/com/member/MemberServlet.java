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
	protected void process(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri=req.getRequestURI();
		
		// uri에 따른 작업 구분
		if(uri.indexOf("login.do")!=-1) {
			loginForm(req, resp);
		} else if(uri.indexOf("login_ok.do")!=-1) {
			loginSubmit(req, resp);
		} else if(uri.indexOf("logout.do")!=-1) {
			logout(req, resp);
		} else if(uri.indexOf("member.do")!=-1) {
			memberForm(req, resp);
		} else if(uri.indexOf("member_ok.do")!=-1) {
			memberSubmit(req, resp);
		} else if(uri.indexOf("pwd.do")!=-1) {
			pwdForm(req, resp);
		} else if(uri.indexOf("pwd_ok.do")!=-1) {
			pwdSubmit(req, resp);
		} else if(uri.indexOf("update_ok.do")!=-1) {
			updateSubmit(req, resp);
		} else if(uri.indexOf("userIdCheck.do")!=-1) {
			userIdCheck(req, resp);
		}
	}

	private void loginForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 로그인 폼
		String path="/WEB-INF/views/member/login.jsp";
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
		if(dto!=null) {
			if(dto.getUserPwd().equals(userPwd)) { //비밀번호가 일치하면
				//로그인 성공
				//세션의 유지시간을 20분으로 설정(기본 : 30분)
				session.setMaxInactiveInterval(20*60); //초단위
				
				//세션에 로그인 정보 저장
				SessionInfo info = new SessionInfo();
				info.setUserId(dto.getUserId());
				info.setUserName(dto.getUserName());
				
				//세션에 member이라는 이름으로 session info 객체를 저장
				session.setAttribute("member", info);
				
				//메인화면으로 리다이렉트
				resp.sendRedirect(cp);
				return;
			}
		}
		
		//로그인이 실패한 경우
		String msg = "아이디 또는 패스워드가 일치하지 않습니다.";
		req.setAttribute("message", msg);
		
		forward(req,resp,"/WEB-INF/views/main/main.jsp"); //포워드 할 떄 /는 cp 포함
		
		
	}
	
	private void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 로그아웃
		HttpSession session = req.getSession();
		String cp = req.getContextPath();
		
		//세션에 저장된 정보 지우기
		session.removeAttribute("member");
		
		//세션 초기화
		session.invalidate();
		
		//메인화면으로 리다이렉트
		resp.sendRedirect(cp);
	}
	
	private void memberForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 회원가입폼

		String path="/WEB-INF/views/member/member.jsp";
		
		forward(req, resp, path);
	}

	private void memberSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 회원가입 처리
		/*
		MemberDAO dao = new MemberDAO();
		MemberDTO dto = new MemberDTO();
		
		dto.setUserId(req.getParameter("userId"));
		dto.setUserName(req.getParameter("userName"));
		dto.setUserPwd(req.getParameter("userPwd"));
	
		dto.setBirth(req.getParameter("birth"));

		dto.setEmail1(req.getParameter("email1"));
		dto.setEmail2(req.getParameter("email2"));
		dto.setEmail(dto.getEmail1()+"@"+dto.getEmail2());
		
		dto.setTel1(req.getParameter("tel1"));
		dto.setTel2(req.getParameter("tel2"));
		dto.setTel3(req.getParameter("tel3"));
		dto.setTel(dto.getTel1()+"-"+dto.getTel2()+"-"+dto.getTel3());
		
		dto.setZip(req.getParameter("zip"));
		dto.setAddr1(req.getParameter("addr1"));
		dto.setAddr2(req.getParameter("addr2"));
		
		//회원가입이 100% 성공한다는 보장 x 그래서 예외처리 해야함
		try {
			dao.insertMember(dto);
		} catch (Exception e) {
			String message = "회원 가입이 실패했습니다."	;
			
			req.setAttribute("title", "회원 가입");
			req.setAttribute("mode", "created");
			req.setAttribute("message", message);
			
			forward(req,resp,"/WEB-INF/views/member/member.jsp"); 
			return;
		}
		
		/*insert, delete, update할 떄 포워딩 안하는 것이 원칙.
		StringBuffer sb = new StringBuffer();
		sb.append("<b>"+dto.getUserName()+" <b>님 회원가입이 완료되었습니다.<br>");
		sb.append("메인화면으로 이동하여 로그인하시기 바랍니다.<br>");
		
		req.setAttribute("title", "회원 가입");
		req.setAttribute("message", sb.toString());
		
		forward(req,resp,"/WEB-INF/views/member/complete.jsp");
		
		
		//main 화면으로 리다이렉트
		String cp = req.getContextPath();
		String url = cp;
		resp.sendRedirect(url);
		
		*/
		
	}
	
	private void pwdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 패스워드 확인 폼
		/*
		HttpSession session = req.getSession();
		String cp = req.getContextPath();
		
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		if(info==null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		String mode = req.getParameter("mode");
		if(mode.equals("update")) {
			req.setAttribute("title", "회원 정보 수정");
		} else {
			req.setAttribute("title", "회원 탈퇴");
		}
		req.setAttribute("mode", mode);
		
		forward(req, resp, "/WEB-INF/views/member/pwd.jsp");
		*/
	}

	private void pwdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 패스워드 확인
		/*
		HttpSession session = req.getSession();
		String cp = req.getContextPath();
		
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		if(info==null) { //로그인이 안된 경우
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		MemberDAO dao = new MemberDAO();
		MemberDTO dto = dao.readMember(info.getUserId());
		
		if(dto==null) {
			session.invalidate();
			resp.sendRedirect(cp);
			return;
		}
		
		String userPwd = req.getParameter("userPwd");
		String mode = req.getParameter("mode");
		if(! dto.getUserPwd().equals(userPwd)) {
			if(mode.equals("update")) {
				req.setAttribute("title", "회원 정보 수정");
			} else {
				req.setAttribute("title", "회원 탈퇴");
			}
			req.setAttribute("mode", mode);
			req.setAttribute("message", "<span style='color:red;'>패스워드가 일치하지 않습니다.</span>");
			
			forward(req, resp, "/WEB-INF/views/member/pwd.jsp");
			return;
		}
		
		if(mode.equals("delete")) {
			//회원 탈퇴
			
		} else if(mode.equals("update")) {
			//회원 정보 수정 폼
			req.setAttribute("title", "회원 정보 수정");
			req.setAttribute("dto", dto);
			req.setAttribute("mode", "update");
			
			forward(req, resp, "/WEB-INF/views/member/member.jsp");
		}
		*/
		
	}

	
	private void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 회원정보 수정 완료
		/*
		HttpSession session = req.getSession();
		String cp = req.getContextPath();
		
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		if(info==null) { //로그인이 안된 경우
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		//회원 정보 수정
		
		MemberDAO dao = new MemberDAO();
		MemberDTO dto = new MemberDTO();
		
		dto.setUserId(req.getParameter("userId"));
		dto.setUserName(req.getParameter("userName"));
		dto.setUserPwd(req.getParameter("userPwd"));		
		dto.setBirth(req.getParameter("birth"));

		dto.setEmail1(req.getParameter("email1"));
		dto.setEmail2(req.getParameter("email2"));
		dto.setEmail(dto.getEmail1()+"@"+dto.getEmail2());
		
		dto.setTel1(req.getParameter("tel1"));
		dto.setTel2(req.getParameter("tel2"));
		dto.setTel3(req.getParameter("tel3"));
		dto.setTel(dto.getTel1()+"-"+dto.getTel2()+"-"+dto.getTel3());
		
		dto.setZip(req.getParameter("zip"));
		dto.setAddr1(req.getParameter("addr1"));
		dto.setAddr2(req.getParameter("addr2"));
		
		try {
			dao.updateMember(dto);
		} catch (Exception e) {
			String message = "회원 수정이 실패했습니다."	;
			
			req.setAttribute("title", "회원 정보 수정");
			req.setAttribute("mode", "update");
			req.setAttribute("message", message);
			
			forward(req,resp,"/WEB-INF/views/member/member.jsp");
			return;
		}
		
		resp.sendRedirect(cp);
		
		/*
		StringBuffer sb = new StringBuffer();
		sb.append("<b>"+dto.getUserName()+" <b>님 회원 정보 수정이 완료되었습니다.<br>");
		
		req.setAttribute("title", "회원 정보 수정");
		req.setAttribute("message", sb.toString());
		
		forward(req,resp,"/WEB-INF/views/member/complete.jsp"); //포워딩 하면 실행 전의 주소가 주소창에 출력
		*/
	}
	
	private void userIdCheck(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 아이디 중복 검사

	}	
}
