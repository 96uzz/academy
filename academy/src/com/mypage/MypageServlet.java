package com.mypage;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MypageServlet {

	
	
	
	
	private void pwdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 패스워드 확인 폼
		/*
		 * HttpSession session = req.getSession(); String cp = req.getContextPath();
		 * 
		 * SessionInfo info = (SessionInfo)session.getAttribute("member");
		 * if(info==null) { resp.sendRedirect(cp+"/member/login.do"); return; }
		 * 
		 * String mode = req.getParameter("mode"); if(mode.equals("update")) {
		 * req.setAttribute("title", "회원 정보 수정"); } else { req.setAttribute("title",
		 * "회원 탈퇴"); } req.setAttribute("mode", mode);
		 * 
		 * forward(req, resp, "/WEB-INF/views/member/pwd.jsp");
		 */
	}

	private void pwdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 패스워드 확인
		/*
		 * HttpSession session = req.getSession(); String cp = req.getContextPath();
		 * 
		 * SessionInfo info = (SessionInfo)session.getAttribute("member");
		 * if(info==null) { //로그인이 안된 경우 resp.sendRedirect(cp+"/member/login.do");
		 * return; }
		 * 
		 * MemberDAO dao = new MemberDAO(); MemberDTO dto =
		 * dao.readMember(info.getUserId());
		 * 
		 * if(dto==null) { session.invalidate(); resp.sendRedirect(cp); return; }
		 * 
		 * String userPwd = req.getParameter("userPwd"); String mode =
		 * req.getParameter("mode"); if(! dto.getUserPwd().equals(userPwd)) {
		 * if(mode.equals("update")) { req.setAttribute("title", "회원 정보 수정"); } else {
		 * req.setAttribute("title", "회원 탈퇴"); } req.setAttribute("mode", mode);
		 * req.setAttribute("message",
		 * "<span style='color:red;'>패스워드가 일치하지 않습니다.</span>");
		 * 
		 * forward(req, resp, "/WEB-INF/views/member/pwd.jsp"); return; }
		 * 
		 * if(mode.equals("delete")) { //회원 탈퇴
		 * 
		 * } else if(mode.equals("update")) { //회원 정보 수정 폼 req.setAttribute("title",
		 * "회원 정보 수정"); req.setAttribute("dto", dto); req.setAttribute("mode",
		 * "update");
		 * 
		 * forward(req, resp, "/WEB-INF/views/member/member.jsp"); }
		 */

	}

	private void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 회원정보 수정 완료
		/*
		 * HttpSession session = req.getSession(); String cp = req.getContextPath();
		 * 
		 * SessionInfo info = (SessionInfo)session.getAttribute("member");
		 * if(info==null) { //로그인이 안된 경우 resp.sendRedirect(cp+"/member/login.do");
		 * return; }
		 * 
		 * //회원 정보 수정
		 * 
		 * MemberDAO dao = new MemberDAO(); MemberDTO dto = new MemberDTO();
		 * 
		 * dto.setUserId(req.getParameter("userId"));
		 * dto.setUserName(req.getParameter("userName"));
		 * dto.setUserPwd(req.getParameter("userPwd"));
		 * dto.setBirth(req.getParameter("birth"));
		 * 
		 * dto.setEmail1(req.getParameter("email1"));
		 * dto.setEmail2(req.getParameter("email2"));
		 * dto.setEmail(dto.getEmail1()+"@"+dto.getEmail2());
		 * 
		 * dto.setTel1(req.getParameter("tel1")); dto.setTel2(req.getParameter("tel2"));
		 * dto.setTel3(req.getParameter("tel3"));
		 * dto.setTel(dto.getTel1()+"-"+dto.getTel2()+"-"+dto.getTel3());
		 * 
		 * dto.setZip(req.getParameter("zip")); dto.setAddr1(req.getParameter("addr1"));
		 * dto.setAddr2(req.getParameter("addr2"));
		 * 
		 * try { dao.updateMember(dto); } catch (Exception e) { String message =
		 * "회원 수정이 실패했습니다." ;
		 * 
		 * req.setAttribute("title", "회원 정보 수정"); req.setAttribute("mode", "update");
		 * req.setAttribute("message", message);
		 * 
		 * forward(req,resp,"/WEB-INF/views/member/member.jsp"); return; }
		 * 
		 * resp.sendRedirect(cp);
		 * 
		 * /* StringBuffer sb = new StringBuffer();
		 * sb.append("<b>"+dto.getUserName()+" <b>님 회원 정보 수정이 완료되었습니다.<br>");
		 * 
		 * req.setAttribute("title", "회원 정보 수정"); req.setAttribute("message",
		 * sb.toString());
		 * 
		 * forward(req,resp,"/WEB-INF/views/member/complete.jsp"); //포워딩 하면 실행 전의 주소가
		 * 주소창에 출력
		 */
	}

}
