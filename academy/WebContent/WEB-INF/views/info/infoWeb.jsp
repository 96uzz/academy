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

<script type="text/javascript" src="<%=cp%>/resource/js/util.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-1.12.4.min.js"></script>

<style type="text/css">
@import url(//cdn.rawgit.com/hiun/NanumSquare/master/nanumsquare.css);

#background{
	background-image:url(/academy/resource/images/back_opacity.png); 
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

.container2 {
    width:100%;
    text-align:left;
}
.body-container2 {
	margin : auto;
	width: 1300px;
	clear: both;
	min-height: 500px;
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

</style>
<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
<script type="text/javascript">


</script>
</head>
<body>

<div class="header">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>

<div id="background" class="container2">
    <div class="body-container2">
		<div class="navigation2" >
			<div class="body-title">
            <h3><span><img src="<%=cp%>/resource/images/menu-button.png" style="height: 21px;"/></span>   홈페이지 소개 </h3>
        	</div>
			<table>
				<tr><td><a href="<%=cp%>/info/infoWeb.do" id="nav1">조장 인사말</a></td></tr>
				<tr><td><a href="<%=cp%>/info/history.do" id="nav2">연혁</a></td></tr>
				<tr><td><a href="<%=cp%>/info/direction.do" id="nav3">찾아오시는 길</a></td></tr>
				<tr><td><a href="<%=cp%>/info/group.do" id="nav4">역할 분담</a></td></tr>
			</table>
		</div>
		<div class="content2" style="font-size: 30px; font-weight: 800; color: #3598DB;">
			조장 인사말
			<br><br>
			<table style="border-collapse: collapse; border-spacing: 0; font-size: 18px; color: black; width: 800px;">
				<tr style="width: 100%;">
					<td style="width: 300px; height: 200px;">
						<img src="<%=cp%>/resource/images/infoWeb.png">
					</td>
					<td style="width: 450px; height: 100%;">
						<p style="font-size: 25px; color: blue;">승훈교육정보센터에 오신 여러분 환영합니다.</p>
						<br><br>
						<p style="font-size: 33px;">국내최대규모 승훈에서</p>
						<p style="font-size: 33px;">당신의 꿈을 찾아가세요!</p>
						<br>
						<p>우수한 학원과 강의를 찾고자하면 바로 이곳</p>
					</td>
				</tr>
				<tr height="50px;"></tr>
				<tr>
					<td style="width:100%; border: 1px solid black;" colspan="2">
						<p>무슨말을 채워넣어야 할까,,,</p>
					</td>
					<td></td>
				</tr>
			</table>
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