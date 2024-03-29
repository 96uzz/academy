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

<script type="text/javascript" src="<%=cp%>/resource/js/util.js"></script>
<script type="text/javascript"
	src="<%=cp%>/resource/jquery/js/jquery-1.12.4.min.js"></script>

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
	padding: 10px;
}

#nav1 {
	font-weight: 800;
}


</style>

<script type="text/javascript">
<c:if test="${sessionScope.member.userId=='admin'}">
function deleteAcademy(academyNum) {
	if(confirm("게시물을 삭제 하시겠습니까 ?")) {
		var url="<%=cp%>/acs/delete.do?academyNum=" + academyNum+ "&${query}";

		location.href = url;
		}
	}
	</c:if>
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
							style="height: 21px;" /></span> 교육기관 검색
					</h3>
				</div>
				<table>
					<tr>
						<td><a href="<%=cp%>/ac/list.do" id="nav1">학원 검색</a></td>
					</tr>
					<tr>
						<td><a href="<%=cp%>/lts/list.do" id="nav2">강의 검색</a></td>
					</tr>
					<tr>
						<td><a href="<%=cp%>/cal/list.do" id="nav2">일정 검색</a></td>
					</tr>
				</table>
			</div>
			<div class="content2"
				style="font-size: 30px; font-weight: 800; color: #3598DB; width: 800px;">
				<p>학원 상세정보</p>
				<br>


				<table id="t1"
					style="border-collapse: collapse; border-spacing: 0; border-bottom: 1px solid #3598DB; border-top: 1px solid #3598DB; font-size: 18px; color: black; width: 100%;">
					<tr height="35"
						style="border-top: 1px solid #3598DB; border-bottom: 1px solid #3598DB;">
						<th colspan="3" align="center">${dto.acaName}</th>
					</tr>

					<tr height="35" style="border-bottom: 1px solid #3598DB;">
						<th align="left" style="padding-left: 5px; width: 110px;">작성자
						</th>
						<td align="left" style="padding-left: 5px;">&nbsp;관리자</td>
						<td align="right" style="padding-right: 5px;">${dto.created}
							| 조회 ${dto.hitCount}</td>
					</tr>

					<tr height="35" style="border-bottom: 1px solid #3598DB;">
						<th align="left" style="padding-left: 5px;">교육장위치</th>
						<td align="left" style="padding-left: 5px;">
							&nbsp;${dto.acaAddress}</td>
						<td width="50%" align="left" style="padding-left: 5px;"></td>
					</tr>

					<tr height="35" style="border-bottom: 1px solid #3598DB;">
						<td align="left" style="padding-left: 5px;">전화번호</td>
						<td align="left" style="padding-left: 5px;">&nbsp;${dto.acaTel}</td>
						<td width="50%" align="left" style="padding-left: 5px;"></td>
					</tr>

					<tr height="35" style="border-bottom: 1px solid #3598DB;">
						<td align="left" style="padding-left: 5px;">교육구분</td>
						<td align="left" style="padding-left: 5px;">&nbsp;${dto.acaDiv}</td>
						<td width="50%" align="left" style="padding-left: 5px;"></td>
					</tr>

					<tr style="border-bottom: 1px solid #3598DB; width: 100%">
						<th style="padding: 10px 5px; vertical-align: top;">
							<p>교육기관 소개</p>

						</th>
						<td colspan="3" style="padding: 10px 5px; vertical-align: top;">
							<p>&nbsp;${dto.acaIntro}</p>
						</td>
					</tr>
					<tr height="35" style="border-bottom: 1px solid #3598DB;">
						<td colspan="3" align="left" style="padding-left: 5px;">이전글 &nbsp;
							<c:if test="${not empty preReadDto}">
								<a
									href="<%=cp%>/acs/article.do?${query}&acaNum=${preReadDto.acaNum}">${preReadDto.acaName}</a>
							</c:if>
						</td>
					</tr>

					<tr height="35" style="border-bottom: 1px solid #3598DB;">
						<td colspan="3" align="left" style="padding-left: 5px;">다음글 &nbsp;
							<c:if test="${not empty nextReadDto}">
								<a
									href="<%=cp%>/acs/article.do?${query}&acaNum=${nextReadDto.acaNum}">${nextReadDto.acaName}</a>
							</c:if>
						</td>
					</tr>
				</table>

				<table
					style="width: 100%; margin: 0px auto 20px; border-spacing: 0px;">
					<tr height="45">
						<td width="300" align="left"><c:if
								test="${sessionScope.member.userId==dto.userId}">
								<button type="button" class="loginButton"
									onclick="javascript:location.href='<%=cp%>/acs/update.do?acaNum=${dto.acaNum}&page=${page}';">수정</button>
							</c:if> <c:if
								test="${sessionScope.member.userId==dto.userId || sessionScope.member.userId=='admin'}">
								<button type="button" class="loginButton"
									onclick="deleteAcademy('${dto.acaNum}');">삭제</button>
							</c:if></td>

						<td align="right">
							<button type="button" class="loginButton"
								onclick="javascript:location.href='<%=cp%>/acs/list.do?${query}';">리스트</button>
						</td>
					</tr>
				</table>


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