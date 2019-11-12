<%@page import="com.saying.SayingDAO"%>
<%@page import="com.saying.SayingDTO"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
   String cp = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
<link href="https://fonts.googleapis.com/css?family=Sunflower:300&display=swap&subset=korean" rel="stylesheet">
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
@import url(//cdn.rawgit.com/hiun/NanumSquare/master/nanumsquare.css);
body{
  /* width: 1400px;*/
   height: 700px;
}

#container {
   width: 100%;
   height: 100%;
   background-image:url("back1.jpg");
   background-repeat: no-repeat;
   background-position: center center;
} 
#interface{
padding: 60px 0px 0px 60px;

}

.loginImage {
   width: 60px;
   height: 50px;
   padding-top: 3px;
}

.loginInput {
   height: 45px;
   width: 280px;
   font-size: 25px;
   padding-top: 1px;
}

.loginButton {
   font-family: 'Nanum Square', sans-serif;
   font-weight: bold;
   color: white;
   font-size: 15px;
   background-color: #3598DB;
   border: 0px;
   outline: 0px;
   text-align: center;
   border-radius: 3px;
   height: 32px;
   width: 100px;
   
}
.loginButton:active, .loginButton:focus, .loginButton:hover {
    background-color:#e6e6e6;
    border-color: #adadad;
    color: #333333;
    cursor: pointer;
}
.loginButton[disabled], fieldset[disabled] .loginButton {
    pointer-events: none;
    cursor: not-allowed;
    filter: alpha(opacity=65);
    -webkit-box-shadow: none;
    box-shadow: none;
    opacity: .65;
}

/* .boardMenu {
   padding: 0px 5px 5px 0px;
   color: black;
   text-decoration: none;
   font-size: 20px;
} */
.boardMenu {
	padding: 0px 5px 5px 0px;
	color : black;
	border: none;
	background: white;
	font-size : 20px;
}

#btnNotice, #btnBoard, #btnQna {
	padding: 0px 5px 5px 0px;
	color : black;
	border: none;
	background: white;
	font-size : 20px;
}

a {
   text-decoration: none;
   color: black;
}

.board {
   width: 450px;
   height: 200px;
   table-layout: fixed;
   font-size: 18px;
   border-collapse: collapse;
   border-spacing: 0px;
   text-align: center;
   table-layout: fixed;
   
}

.boardTop {
   background-color: #DDDDDD;
   border-top: 3px solid #AAAAAA;
   border-bottom: 1px solid #AAAAAA;
   height: 25px;
}

.boardMiddles {
   border-bottom: 1px solid #AAAAAA;
   height: 25px;
}

.boardBottom {
   border-bottom: 3px solid #AAAAAA;
   height: 25px;
}

.boardSubject {
   min-width: 260px;
   text-overflow: ellipsis;
   overflow: hidden;
   white-space: nowrap;
}
#confirmLogin{
   font-size: 15px;
   color : red;
}
</style>
<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
<script type="text/javascript">

//로그인 확인
function sendLogin() {
    var f = document.loginForm;

   var str = f.userId.value;
    if(!str) {
        alert("아이디를 입력하세요. ");
        f.userId.focus();
        return;
    }

    str = f.userPwd.value;
    if(!str) {
        alert("패스워드를 입력하세요. ");
        f.userPwd.focus();
        return;
    }

    f.action = "<%=cp%>/member/login_ok.do";
    f.submit();
}


</script>
</head>
<body>
      <div id="container">
      <div id="interface" style="width: 700px;">
      
     <c:if test="${empty sessionScope.member}">
     <form name="loginForm" method="post">
      <table style="border-collapse: collapse; border-spacing: 0px;">
         <tr style="border-spacing: 0px; border-collapse: collapse;">
            <td><img class="loginImage" src="<%=request.getContextPath() %>/resource/images/man.JPG"></td>
            <td><input class="loginInput" type="text" id="userId" name="userId" style="margin-bottom: 5px;"></td>
         </tr>
         <tr>
            <td><img class="loginImage" src="<%=request.getContextPath() %>/resource/images/key.JPG"></td>
            <td><input class="loginInput" type="password" id="userPwd" name="userPwd" style="margin-bottom: 5px;"></td>
         </tr>
      </table>
      
      <div id="confirmLogin">${message}</div>
      <table style="width: 361px; padding-top: 5px">
         <tr>
            <td><button class="loginButton" type="button"
                  name="login" onclick="sendLogin();">로그인</button></td>
            <td><button class="loginButton" type="button"
                  name="register" onclick="javascript:location.href='<%=cp%>/member/member.do';">회원가입</button></td>
            <td><button class="loginButton" type="button" name="find"
                  onclick="">ID/PWD 찾기</button></td>
         </tr>
             </table>
        </form>
      </c:if>
      
      <c:if test="${not empty sessionScope.member}">
         <table style="height: 200px; width: 400px; table-layout: fixed;" >
         <tr>
        <td style="padding-right: 25px;" ><img src="<%=cp%>/resource/images/login.png" height="85"> </td>
        <td style="font-size: 20px;">${sessionScope.member.userName} 님</td>
        <td><button class="loginButton" type="button"
                  name="logout" onclick="javascript:location.href='<%=cp%>/member/logout.do';">로그아웃</button></td>
         </tr>
    
      <tr>
       <td colspan="3" style="padding-left: 10px;">[오늘의 명언]</td>
      </tr>
      <tr>
       <td colspan="3" style="padding-left: 10px; font-weight:bold;">${sessionScope.member.wiseSaying}</td>
      </tr>
      
      </table>
      
      </c:if>
      
      <br> <br>
      <table>
       <!--   <tr>
            <td class="boardMenu"><a href="#">공지사항&nbsp;|</a></td>
            <td class="boardMenu"><a href="#">자유게시판&nbsp;|</a></td>
            <td class="boardMenu"><a href="#">Q/A&nbsp;</a></td>
         </tr> -->
         <tr>
         	<td class="boardMenu"><button id="btnNotice">공지사항</button></td>
         	<td class="boardMenu"><button id="btnBoard">자유게시판</button></td>
         	<td class="boardMenu"><button id="btnQna">Q/A</button></td>
         </tr>
      </table>
      
      <table class="board">
         <tr class="boardTop">
            <th style="width: 15%;">번호</th>
            <th style="width: 70%;">제목</th>
            <td style="width: 15%;"><a href="#">+more</a></td>
         </tr>
		
		<c:forEach var="dto" items="${list}">
			 <tr class="boardMiddles">
            <td>${dto.noticeNum}</td>
            <td class="boardSubject"><a href="${articleUrl}&noticeNum=${dto.noticeNum}">${dto.subject}</a></td>
            <td></td>
         </tr>
		</c:forEach>
        

      </table>
</div>
   </div>

</body>
</html>