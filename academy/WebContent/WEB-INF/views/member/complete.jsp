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

#background {
	background-image: url(/academy/resource/images/back1.jpg);
	background-position: center;
	background-repeat: no-repeat;
	background-size: 1350px 600px;
	width: 1400px;
	height: 550px;
	margin: 0px auto 0px auto;
}

.messageBox {
	width: 450px;
	min-height: 150px;
	line-height: 100px;
	border: 1px solid #DAD9FF;
	padding: 5px;
	color: #333333;
	font-size: 14px;
	text-align: center;
	background: rgba(255, 255, 255, 0.7);
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
	width: 150px;
}

.loginButton:active, .loginButton:focus, .loginButton:hover {
	background-color: #e6e6e6;
	border-color: #adadad;
	color: #333333;
	cursor: pointer;
}

.loginButton[disabled], fieldset[disabled] .loginButton {
	pointer-events: none;
	cursor: not-allowed;
	filter: alpha(opacity = 65);
	-webkit-box-shadow: none;
	box-shadow: none;
	opacity: .65;
}
</style>

<script type="text/javascript"
	src="<%=cp%>/resource/jquery/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/js/util.js"></script>
</head>
<body>
	<div class="header">
		<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
	</div>
	<div id="background">
		<div class="container">
			<div>
				<div
					style="margin: 0px auto 0px; width: 450px; min-height: 350px;">

					<div style="text-align: center;">
					<br><br><br><br>	
						<span style="font-weight: bold; font-size: 30px; color: #424951;">${title}</span>
					</div>
						<br>
					<div class="messageBox">
						<div style="line-height: 150%; padding-top: 35px; font-size: 18px;">${message}</div>
						<div style="margin-top: 20px;">
							<button type="button"
								onclick="javascript:location.href='<%=cp%>/';"
								class="loginButton">메인화면으로 이동</button>
						</div>
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
</body>
</html>