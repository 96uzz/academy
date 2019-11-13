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
				<tr><td><a href="<%=cp%>/info/infoWeb.do" id="nav2">인사말</a></td></tr>
				<tr><td><a href="<%=cp%>/info/history.do" id="nav1">연혁</a></td></tr>
				<tr><td><a href="<%=cp%>/info/direction.do" id="nav3">찾아오시는 길</a></td></tr>
				<tr><td><a href="<%=cp%>/info/group.do" id="nav4">역할 분담</a></td></tr>
			</table>
		</div>
		
		<div class="content2" style="font-size: 30px; font-weight: 800; color: #3598DB;">
			연혁
			<br><br>
			<table style="border-collapse: collapse; border-spacing: 0; font-size: 18px; color: black; width: 800px; height: 100%;">
				<tr style=" width: 800px; height: 250px;">
					<td>
						<img src="<%=cp%>/resource/images/history2.png">
					</td>
				</tr>
			</table>
			<table style="border-collapse: collapse; border-spacing: 0; font-size: 18px; color: black; width: 800px; height: 100%;">
				<tr style="height: 50px;"></tr>
				<tr>
					<td colspan="2" style="padding-left: 50px;">
						<p style="font-size: 30px; font-weight:900; color: red; display: inline-block; float: left;">|&nbsp;</p>
						<p style="font-size: 30px;">2019년</p>
						<p style="font-size: 20px; display: inline-block; float: left; width: 300px; opacity: 0;">2017.&nbsp;</p>
						<p style="font-size: 20px; display: inline-block; float: left; width: 300px; opacity: 0;">2017.&nbsp;</p>
						<p style="font-size: 20px; display: inline-block; float: left; width: 300px; opacity: 0;">2017.&nbsp;</p>
					</td>
					<td style="width: 100px;">
						<p style="font-size: 20px; display: inline-block; float: left; color: brown; font-weight: 500;">2019.&nbsp;</p>
						<p style="font-size: 20px; display: inline-block; float: left; opacity: 0;">2017.&nbsp;</p>
						<p style="font-size: 20px; display: inline-block; float: left; opacity: 0;">2017.&nbsp;</p>
						<p style="font-size: 20px; display: inline-block; float: left; opacity: 0;">2017.&nbsp;</p>
					</td>
					<td style="width: 350px;">
						<p style="font-size: 18px;">교육부 선정 모범적인 사이트 1위</p>
						<p style="font-size: 18px;">&nbsp; - 승훈교육정보센터</p>
						<p style="font-size: 18px;">SBC TV프로그램 '세상에 이런 사이트가?' 출연</p>
						<p style="font-size: 18px;">EBF 교육프로그램 'CSS뚜루룩딱' 모델 사이트</p>
					</td>
				</tr>
				<tr style="height: 50px;"></tr>
				<tr>
					<td colspan="2" style="padding-left: 50px;">
						<p style="font-size: 30px; font-weight:900; color: red; display: inline-block; float: left;">|&nbsp;</p>
						<p style="font-size: 30px;">2018년</p>
						<p style="font-size: 20px; display: inline-block; float: left; width: 300px; opacity: 0;">2017.&nbsp;</p>
						<p style="font-size: 20px; display: inline-block; float: left; width: 300px; opacity: 0;">2017.&nbsp;</p>
						<p style="font-size: 20px; display: inline-block; float: left; width: 300px; opacity: 0;">2017.&nbsp;</p>
					</td>
					<td style="width: 100px;">
						<p style="font-size: 20px; display: inline-block; float: left; color: brown; font-weight: 500;">2018.&nbsp;</p>
						<p style="font-size: 20px; display: inline-block; float: left; opacity: 0;">2017.&nbsp;</p>
						<p style="font-size: 20px; display: inline-block; float: left; opacity: 0;">2017.&nbsp;</p>
						<p style="font-size: 20px; display: inline-block; float: left; opacity: 0;">2017.&nbsp;</p>
					</td>
					<td style="width: 350px;">
						<p style="font-size: 18px;">고용노동부 기관평가 최우수 훈련기관 유지</p>
						<p style="font-size: 18px;">&nbsp; - 승훈교육정보센터</p>
						<p style="font-size: 18px;">고용노동부 훈련조회자평가 결과 S등급 선정</p>
						<p style="font-size: 18px;">고용노동부 교육훈련기관 인증평가 최고등급</p>
					</td>
				</tr>
				<tr style="height: 50px;"></tr>
				<tr>
					<td colspan="2" style="padding-left: 50px;">
						<p style="font-size: 30px; font-weight:900; color: red; display: inline-block; float: left;">|&nbsp;</p>
						<p style="font-size: 30px;">2017년</p>
						<p style="font-size: 20px; display: inline-block; float: left; width: 300px; opacity: 0;">2017.&nbsp;</p>
						<p style="font-size: 20px; display: inline-block; float: left; width: 300px; opacity: 0;">2017.&nbsp;</p>
						<p style="font-size: 20px; display: inline-block; float: left; width: 300px; opacity: 0;">2017.&nbsp;</p>
					</td>
					<td style="width: 100px;">
						<p style="font-size: 20px; display: inline-block; float: left; color: brown; font-weight: 500;">2017.&nbsp;</p>
						<p style="font-size: 20px; display: inline-block; float: left; opacity: 0;">2017.&nbsp;</p>
						<p style="font-size: 20px; display: inline-block; float: left; opacity: 0;">2017.&nbsp;</p>
						<p style="font-size: 20px; display: inline-block; float: left; opacity: 0;">2017.&nbsp;</p>
					</td>
					<td style="width: 350px;">
						<p style="font-size: 18px;">고용 노동부 기관평가 최우수 훈련기관 선정</p>
						<p style="font-size: 18px;">&nbsp; - 승훈교육정보센터</p>
						<p style="font-size: 18px;">한국산업인력공단 위탁교육기관</p>
						<p style="font-size: 18px;">교육부 선정 올해의 우수 사이트</p>
					</td>
					<tr style="height: 200px;"></tr>
				
				
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