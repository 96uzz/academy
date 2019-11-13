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

</style>
<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
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
				<tr><td><a href="<%=cp%>/info/infoWeb.do" id="nav3">인사말</a></td></tr>
				<tr><td><a href="<%=cp%>/info/history.do" id="nav4">연혁</a></td></tr>
				<tr><td><a href="<%=cp%>/info/direction.do" id="nav1">찾아오시는 길</a></td></tr>
				<tr><td><a href="<%=cp%>/info/group.do" id="nav2">역할 분담</a></td></tr>
			</table>
		</div>
		<div class="content2" style="font-size: 30px; font-weight: 800; color: #3598DB;">
			찾아오시는 길
			<br><br>
			<table style="border-collapse: collapse; border-spacing: 0; font-size: 18px; color: black; width: 800px; height: 100%;">
				<tr style=" width: 800px; height: 250px;">
					<td>
						<img src="<%=cp%>/resource/images/direction.png">
					</td>
				</tr>
			</table>
			<table style="border-collapse: collapse; border-spacing: 0; font-size: 18px; color: black; width: 800px; height: 100%;">
				<tr style="height: 30px;"></tr>
				
				<tr>
					<td style="width: 300px;" colspan="2">
						<p style="font-size: 30px; font-weight:900; color: red; display: inline-block; float: left;">|&nbsp;</p>
						<p style="font-size: 30px;">상세 지도</p>
					</td>
				</tr>
				<tr style="height: 50px;"></tr>
			</table>
			
			<table style="border-collapse: collapse; border-spacing: 0; font-size: 18px; color: black; width: 800px; height: 100%;">
				<tr style=" width: 800px; height: 500px;">
					<td>
						<div id="staticMap" style="width:100%;height:500px;"></div>  
					</td>
				</tr>
				<tr style="height: 30px;"></tr>
			</table>
			<table style="width: 800px; font-size: 21px; color: black; height: 100%" >
				<tr style=" width: 800px; height: 80px;">
					<td width="80px;" height="80px;">
						<img src="<%=cp%>/resource/images/subway2.png">
					</td>
					<td width="310px;">
						<p>2호선 홍대입구역 하차</p><br>
						<p>경의중앙선 홍대입구역 하차</p><br>
						<p>공항철도 홍대입구역 하차</p>
					</td>
					<td width="80px;" height="80px;" style="font-size: 30px;"> -> </td>
					<td width="80px;" height="80px;">
						<img src="<%=cp%>/resource/images/walking.png">
					</td>
					<td width="310px;">
						<p>2호선 1번 출구로 나와 직진 후</p><br>
						<p>월드컵 북로 방향으로 우회전</p><br>
						<p>조금 걸어 길 건너 농협 건물 2층</p>
					</td>
				<tr style="height: 100px;"></tr>
			</table>
		</div>
	
    </div>
</div>

<div class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=ea24f69cc8602cd4d0ce33868b3dd46d"></script>
<script type="text/javascript">
var markers = [
    {
        position: new kakao.maps.LatLng(37.556556, 126.919575), 
        text: '승훈' // text 옵션을 설정하면 마커 위에 텍스트를 함께 표시할 수 있습니다     
    }
];

var staticMapContainer  = document.getElementById('staticMap'), // 이미지 지도를 표시할 div  
    staticMapOption = { 
        center: new kakao.maps.LatLng(37.556556, 126.919575), // 이미지 지도의 중심좌표
        level: 3, // 이미지 지도의 확대 레벨
        marker: markers // 이미지 지도에 표시할 마커 
    };    

//이미지 지도를 생성합니다
var staticMap = new kakao.maps.StaticMap(staticMapContainer, staticMapOption);

</script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery.ui.datepicker-ko.js"></script>
</body>
</html>