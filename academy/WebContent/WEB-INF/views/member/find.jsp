<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
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
<title>spring</title>

<link rel="stylesheet" href="<%=cp%>/resource/css/style.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/layout.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/jquery/css/smoothness/jquery-ui.min.css" type="text/css">

<style type="text/css">
@import url(//cdn.rawgit.com/hiun/NanumSquare/master/nanumsquare.css);
#background{
	background-image:url(/academy/resource/images/back1.jpg); 
    background-position: center;
    background-repeat: no-repeat;
   	background-size: 1350px 600px;

   	width: 1400px;
   	height: 550px;
  	margin: 0px auto 0px auto;

}



body{
	font-family: 'Nanum Square', sans-serif;
	font-size: 20px;
}

.container2 {
    width:100%;
    text-align:left;
	position: relative;
}
.body-container2 {
	margin-top : 120px;
	width: 1000px;
	clear: both;
	min-height: 500px;
	position: absolute;
	top: 80%;
	left: 37%;
		
	
}
.navigation2 {
	float: left;

}

.content2 {
	float: left;
	margin-left: 30px;
	margin-top: 30px;
	
}

.navigation2 td {
 	font-family: 'Nanum Square', sans-serif;
	font-size : 20px;
	border-radius: 2px;
	margin : 20px auto;
	padding : 10px;
	
}

#nav1 {
	font-weight: 800;
}

.loginImage {
   width: 40px;
   height: 40px;
   padding-top: 0px;
}

.loginInput {
   height: 35px;
   width: 280px;
   font-size: 18px;
   padding-top: 1px;
}

.word{
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

#confirmLogin{
	font-weight:bold;
	font-style:italic;
	font-size: 17px;
    color : red;
    text-align: center;
}

</style>
<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
<script type="text/javascript">


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

<div class="header">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>
<div id="background" class="container2">
<div class="container2">
    <div class="body-container2">
		<div class="navigation2" >
			<div class="body-title">
          
        	</div>
		</div>
		<div class="content2" style="margin-left: auto; margin-right: auto;">
		<h2 style="padding-left: 90px; color: #3598DB;">ID/PW 찾기</h2>
		
			<form name="idForm" method="post">
      ID 찾기
      <br><br>
      <table style="border-collapse: collapse; border-spacing: 0px;">
         <tr style="border-spacing: 0px; border-collapse: collapse;">
        
            <td>이름</td>
            <td><input class="loginInput" type="text" id="name" name="name" style="margin-bottom: 5px;"></td>
         </tr>
         <tr>
            <td>생년월일</td>
            <td><input class="loginInput" type="text" id="birth" name="birth" style="margin-bottom: 5px;"></td>
         </tr>
      </table>
      
      <div id="confirmLogin">${message}</div>
      <table style="width: 341px; padding-top: 5px">
         <tr>
         	<td><input type="hidden" name="mode" value="${mode}"></td>
            <td><button class="loginButton" type="button"
                  name="login" onclick="sendOk();" style="margin-left: 111px;">로그인</button></td>
            
        
         </tr>
             </table>
        </form>
        		<form name="pwdForm" method="post">
        		  PW 찾기
        		  <br><br>
      <table style="border-collapse: collapse; border-spacing: 0px;">
         <tr style="border-spacing: 0px; border-collapse: collapse;">
       
            <td>ID</td>
            <td><input class="loginInput" type="text" id="name" name="name" style="margin-bottom: 5px;"></td>
         </tr>
         <tr>
            <td>이메일</td>
            <td><input class="loginInput" type="text" id="email" name="email" style="margin-bottom: 5px;"></td>
         </tr>
      </table>
      
      <div id="confirmLogin">${message}</div>
     <table style="width: 341px; padding-top: 5px">
         <tr>
         	<td><input type="hidden" name="mode" value="${mode}"></td>
            <td><button class="loginButton" type="button"
                  name="login" onclick="sendOk();" style="margin-left: 111px;">로그인</button></td>
            
        
         </tr>
             </table>
        </form>
		</div>
	
    </div>
</div>
</div>
<div class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>

<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery.ui.datepicker-ko.js"></script>
</body>
</html>