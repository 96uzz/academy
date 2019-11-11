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
	height: 29px;
	width: 90px;
}

.loginButton:hover {
	cursor: pointer;
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

.body-board {
	background: rgba(255, 255, 255, 0.7);
	width: 600px;
	height: 750px;
	${mode=="update" ? "margin-left:60px;" : "margin-left: 360px;"}
}

.help-block {
	font-size: 17px;
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
            <h3><span><img src="<%=cp%>/resource/images/menu-button.png" style="height: 21px;"/></span>   마이 페이지 </h3>
        	</div>
			<table>
				<tr><td><a href="<%=cp%>/member/interlecture.do">관심강좌</a></td></tr>
				<tr><td><a href="<%=cp%>/member/pwd.do?mode=update">정보 수정</a></td></tr>
				<tr><td><a href="<%=cp%>/member/takinglecture.do" id="nav1">수강중인 강좌</a></td></tr>
			</table>
		</div>
	
		<div class="content2" style="margin-left: auto; margin-right: auto;">
		<h2 style="margin-left:30px; color: #3598DB;">수강중인 강좌</h2>
			<br>
			<span style="font-weight: bold;">${sessionScope.member.userName}</span> 님이 수강중인 강좌입니다.
			<br><br>	
		<table style="border-collapse: collapse; border-spacing: 0; background: rgba(255, 255, 255, 0.7);
		 border-bottom: 1px solid black; border-top: 1px solid black; font-size: 18px; color: black; width: 100%;">
            <tr style="border-bottom: 1px solid black; background-color: #3598DB;">
               <td style="width: 50px; text-align: center;">분류</td>      
               <td style="width: 200px; text-align: center;">강좌</td>
               <td style="width: 200px; text-align: center;">학원</td>
               <td style="width: 150px; text-align: center;">주소</td>
               <td style="width: 50px; text-align: center;">정원</td>
               <td style="width: 120px; text-align: center;">개강일</td>         
               <td style="width: 120px; text-align: center;">종강일</td>
            </tr>
  			<tr style="border-bottom: 1px solid black; height: 40px; text-align: center;">
               <td>IT</td>
               <td><a href="#">레슬링 입문</a></td>
               <td><a href="#">구로구청레슬링</a></td>
               <td>서울시 구로구</td>      
               <td>20</td>
               <td>2019/11/04</td>
               <td>2020/04/03</td>
            </tr>






<!-- 
         <c:forEach var="dto" items="${list}">
            <tr style="border-bottom: 1px solid black; height: 40px; text-align: center;">
               <td>${dto.acaDiv}</td>
               <td>${dto.lecName}</td>
               <td>${dto.acaName}</td>
               <td>${dto.acaAddress}</td>      
               <td>${dto.lecLemit }</td>
               <td>${dto.lecStartDate}</td>
               <td>${dto.lecEndDate}</td>
            </tr>
         </c:forEach>   
 -->		
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