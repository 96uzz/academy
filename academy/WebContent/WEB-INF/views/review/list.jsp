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

<script type="text/javascript" src="<%=cp%>/resource/js/util.js"></script>
<script type="text/javascript"
	src="<%=cp%>/resource/jquery/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript">
	function drawAvgStar() {
		var starCnt = Math.round(parseFloat(jQuery('#avg_rate').html()) * 2);

		for (var j = 0; j < starCnt; j++) {
			jQuery(".avg-draw-star-rate>.star").eq(j).addClass("on");
		}
	}

	function selectList() {
		var f = document.selectForm;
		f.submit();
	}

	function searchList() {
		var f = document.searchForm;
		f.submit();
	}
</script>

<style type="text/css">
@import url(//cdn.rawgit.com/hiun/NanumSquare/master/nanumsquare.css);

#background {
	background-image: url(/academy/resource/images/back_opacity.png);
	background-position: center;
	background-repeat: no-repeat;
	background-size: 1350px 600px;
	width: 1400px;
	height: 550px;
	margin: 0px auto 0px auto;
}

body {
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
	background-color: #3598DB;
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

.container2 {
	width: 100%;
	text-align: left;
}

.body-container2 {
	margin: auto;
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
	font-size: 20px;
	border-radius: 2px;
	margin: 20px auto;
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
			<div class="navigation2">
				<div class="body-title">
					<h3>
						<span><img src="<%=cp%>/resource/images/menu-button.png"
							style="height: 21px;" /></span> 커뮤니티
					</h3>
				</div>
				<table>
					<tr>
						<td><a href="<%=cp%>/board/list.do" id="nav2">자유 게시판</a></td>
					</tr>
					<tr>
						<td>
						<a href="<%=cp%>/review/list.do" id="nav1">강의 평가</a></td>
					</tr>
				</table>
			</div>
			<div class="content2"
				style="font-size: 30px; font-weight: 800; color: #3598DB; width: 800px;">
				<p>강의 평가<p>


					<!--  여기부터 소스 -->
				<table style="width: 100%;">
					<tr height="35">
						<td align="right">
							<form name="selectForm" action="<%=cp%>/review/list.do"
								method="post" onchange="selectList();">
								<select name="rows" class="selectField"
									style="margin-bottom: 5px;">
									<option value="5" ${rows==5? "selected='selected'" : ""}>5개</option>
									<option value="10" ${rows==10? "selected='selected'" : ""}>10개</option>
									<option value="20" ${rows==20? "selected='selected'" : ""}>20개</option>
								</select> <input type="hidden" name="condition" value="${condition}">
								<input type="hidden" name="keyword" value="${keyword}">
							</form>
						</td>
					</tr>
				</table>

				<table
					style="border-collapse: collapse; border-spacing: 0; border-bottom: 1px solid black; border-top: 1px solid black; font-size: 17px; color: black; width: 100%;">
					<tr align="center" height="35"
						style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc; background-color: #3598DB;">
						<th width="80">No</th>
						<th>과정명</th>
						<th width="200">기관명</th>
						<th width="100">평점</th>
					</tr>

					<c:forEach var="dto" items="${list}">
						<tr
							style="border-bottom: 1px solid #cccccc; height: 40px; text-align: center;">
							<td>${dto.reNum}</td>
							<td style="text-align: left;"><a
								href="${articleUrl}&reNum=${dto.reNum}">${dto.lecName}</a></td>
							<td>${dto.acaName}</td>
							<td>${dto.rate}</td>
						</tr>
					</c:forEach>
				</table>

				<table style="width: 100%; margin: 10px auto; border-spacing: 0px;">
					<tr height="40">
						<td align="left" width="100px">
							<button type="button" class="loginButton" onclick="javascript:location.href='<%=cp%>/review/list.do';">새로고침</button>
						</td>
						<td align="center">
							<form name="searchForm" action="<%=cp%>/review/list.do"
								method="post">
								<select name="condition" class="selectField">
									<option value="lecName" ${condition=="lecName"?"selected='selected'":"" }>강의명</option>
									<option value="acadiv" ${condition=="acadiv"?"selected='selected'":"" }>분야</option>
									<option value="acaName" ${condition=="acaName"?"selected='selected'":"" }>학원명</option>
								</select>
									<input type="text" name="keyword" class="boxTF" value="${keyword}">
									<input type="hidden" name="rows" value="${rows}">
									<button type="button" class="btn" onclick="searchList()">검색</button>
							</form>
						</td>
						<td align="right" width="100">
							<c:if test="${sessionScope.member.userId=='admin'}">
								<button type="button" class="loginButton" onclick="javascript:location.href='<%=cp%>/review/createdReview.do';">글올리기</button>
							</c:if>
						</td>
					</tr>
				</table>

				<table style="width: 100%; margin: 20px auto; border-spacing: 0px;">
					<tr height="35">
						<td align="center">${dataCount==0?"등록된 게시글이 없습니다.":paging}</td>
					</tr>
				</table>

			</div>
			<!--  여기까지 소스 -->
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