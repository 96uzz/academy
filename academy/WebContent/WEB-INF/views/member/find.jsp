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
<link
	href="https://fonts.googleapis.com/css?family=Sunflower:300&display=swap&subset=korean"
	rel="stylesheet">
<meta charset="UTF-8">
<title>spring</title>

<link rel="stylesheet" href="<%=cp%>/resource/css/style.css"
	type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/layout.css"
	type="text/css">
<link rel="stylesheet"
	href="<%=cp%>/resource/jquery/css/smoothness/jquery-ui.min.css"
	type="text/css">

<style type="text/css">
@import url(//cdn.rawgit.com/hiun/NanumSquare/master/nanumsquare.css);

#background {
	background-image: url(/academy/resource/images/back1.jpg);
	background-position: center;
	background-repeat: no-repeat;
	background-size: 1350px 600px;
	width: 1400px;
	height: 550px;
	margin: 0px auto 0px auto;
}

.idpw {
	width: 300px;
	margin: 20px;
	font-family: 'Nanum Square', sans-serif;
	font-size: 23px;
	font-weight: bold;
}

body {
	font-family: 'Nanum Square', sans-serif;
	font-size: 20px;
}

.container2 {
	width: 100%;
	position: relative;
}

.body-container2 {
	margin-top: 120px;
	width: 1000px;
	clear: both;
	min-height: 500px;
	position: absolute;
	top: 80%;
	left: 19%;
}

.navigation2 {
	float: left;
}

.content2 {
	float: left;
	margin-left: 30px;
}

.navigation2 td {
	font-family: 'Nanum Square', sans-serif;
	font-size: 20px;
	border-radius: 2px;
	margin: 20px auto;
	padding: 10px;
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

.word {
	font-family: 'Nanum Square', sans-serif;
	font-weight: bold;
	color: #3598DB;
	font-size: 20px;
	float: right;
	text-align: right;
	width: 90px;
	padding-right: 20px;
}

.loginButton {
   font-family: 'Nanum Square', sans-serif;
   font-weight: bold;
   color: black;
   font-size: 15px;
   background-color: white;
   border: 1px solid;
   outline: 0px;
   text-align: center;
   border-radius: 3px;
   height: 32px;
   width: 100px;
   
}
.loginButton:active, .loginButton:focus, .loginButton:hover {
    background-color:#3598DB;
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
#confirm{
	font-size: 19px;
    color : blue;
    width: 100%;
     text-align: center;
  }
.confirmLogin {
	font-weight: bold;
	font-style: italic;
	color: red;
	text-align: center;
}

#body-board {
	background: rgba(255, 255, 255, 0.7);
	width: 900px;
	height: 250px;
	padding-top: 30px;
}


</style>
<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
</head>
<body>
	<div class="header">
		<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
	</div>
	<div id="background" class="container2">
		<div class="container2">
			<div class="body-container2">
				<div class="navigation2">
					<div class="body-title"></div>
				</div>
				<div class="content2" style="margin-left: auto; margin-right: auto;">
					<h2 style="padding-left: 345px; color: #3598DB;">ID/PW 찾기</h2>
					<br> <br>
					<br>
					<div id=body-board>
						<form name="findForm" method="post">
							<table style="border-collapse: collapse; border-spacing: 0px;">
								<tr style="text-align: center;">
									<td colspan="2" style="padding-bottom: 30px;"><span
										class="idpw" style="padding-left: 100px;">ID 찾기</span></td>
									<td colspan="2" style="padding-bottom: 30px;"><span
										class="idpw" style="padding-left: 130px;">PW 찾기</span></td>
								</tr>

								<tr>
									<td><span class="word">이름</span></td>
									<td><input class="loginInput" type="text" id="userName"
										name="userName" style="margin-bottom: 5px;" placeholder="이름" value="${userName}"></td>
									<td style="padding-left: 40px;"><span class="word">ID</span></td>
									<td><input class="loginInput" type="text" id="userId"
										name="userId" style="margin-bottom: 5px;" placeholder="ID" value="${userId}"></td>


								</tr>
								<tr>
									<td><span class="word">생년월일</span></td>
									<td><input class="loginInput" type="text" id="birth"
										name="birth" style="margin-bottom: 5px;"
										placeholder="YYYYMMDD 꼴로 입력" value="${birth}"></td>
									<td><span class="word">이메일</span></td>
									<td><input class="loginInput" type="text" id="email"
										name="email" style="margin-bottom: 5px;"
										placeholder="abc123@example.com 꼴로 입력" value="${email}"></td>
								</tr>
							</table>


							
							<table style="width: 341px; padding-top: 5px">
								<tr>

									<td><button class="loginButton" type="button" name="login"
											onclick="find(1);" style="margin-left: 190px;">ID 찾기</button></td>
									<td><button class="loginButton" type="button" name="login"
											onclick="find(2);" style="margin-left: 335px;">PW 찾기</button></td>
								</tr>
							</table>
							<br>
							<div id="confirm" ${mode=="wrong"?"class='confirmLogin'":""}  ${mode=="wrong"?"style = 'color:red'":"style = 'color:blue'"}>${message }</div>

						</form>
					</div>
				</div>

			</div>
		</div>
	</div>
	<div class="footer">
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	</div>

<script type="text/javascript"
		src="<%=cp%>/resource/jquery/js/jquery-ui.min.js"></script>
<script type="text/javascript"
		src="<%=cp%>/resource/jquery/js/jquery.ui.datepicker-ko.js"></script>
		
<script type="text/javascript">

function find(index){
	var f = document.findForm;
	var str;
	
	if(index==1){
		f.action = '<%=cp%>/member/finduserid.do';
	} else if(index==2){
		f.action = '<%=cp%>/member/finduserpassword.do';
	}
	
	f.submit();
}
</script>	
</body>
</html>