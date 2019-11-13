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

.bunhal {
	width: 150px;
	height:40px;
	
}

p {
	width: 150px;
	border-bottom: 1px solid orange;
}

.text_white {
	border: none;
	text-shadow: 0 0 6px blue;
	color: white;
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
				<tr><td><a href="<%=cp%>/info/infoWeb.do" id="nav2">조장 인사말</a></td></tr>
				<tr><td><a href="<%=cp%>/info/history.do" id="nav3">연혁</a></td></tr>
				<tr><td><a href="<%=cp%>/info/direction.do" id="nav4">찾아오시는 길</a></td></tr>
				<tr><td><a href="<%=cp%>/info/group.do" id="nav1">역할 분담</a></td></tr>
			</table>
		</div>
		<div class="content2" style="font-size: 30px; font-weight: 800; color: #3598DB;">
			역할 분담
			<br><br>
			<table style="border-collapse: collapse; border-spacing: 0; font-size: 18px; color: black; width: 800px; height: 100%; text-align: center;">
				<tr style=" width: 800px; height: 150px;">
					<td>
						<img src="<%=cp%>/resource/images/person.png">
					</td>
					<td>
						<img src="<%=cp%>/resource/images/person.png">
					</td>
					<td>
						<img src="<%=cp%>/resource/images/person.png">
					</td>
					<td>
						<img src="<%=cp%>/resource/images/person.png">
					</td>
					<td>
						<img src="<%=cp%>/resource/images/person.png">
					</td>
				</tr>
				<tr style="text-align: center; font-size: 25px; width: 800px;">
					<td><p class="text_white">현욱</p></td>
					<td><p class="text_white">지훈</p></td>
					<td><p class="text_white">규동</p></td>
					<td><p class="text_white">승훈</p></td>
					<td><p class="text_white">유진</p></td>
				</tr>
				<tr style="height: 20px;"></tr>
				<tr>
					<td class="bunhal"><p>홈페이지 소개 구현</p></td>
					<td class="bunhal"><p>커뮤니티 구현</p></td>
					<td class="bunhal"><p>마이페이지 구현</p></td>
					<td class="bunhal"><p>교육정보 구현</p></td>
					<td class="bunhal"><p>교육정보 구현</p></td>
				</tr>
				<tr>
					<td class="bunhal"><p>공지사항 게시판</p></td>
					<td class="bunhal"><p>자유 게시판</p></td>
					<td class="bunhal"><p>로그인/회원가입</p></td>
					<td class="bunhal"><p>학원 검색</p></td>
					<td class="bunhal"><p>일정 검색</p></td>
				</tr>
				<tr>
					<td class="bunhal"><p>Q/A게시판</p></td>
					<td class="bunhal"><p>강의 평가</p></td>
					<td class="bunhal"><p>관심강좌 /수강강좌</p></td>
					<td class="bunhal"><p>강의 검색</p></td>
					<td class="bunhal"><p>메인 화면 구현</p></td>
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