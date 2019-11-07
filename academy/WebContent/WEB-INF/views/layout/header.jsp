<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
   String cp = request.getContextPath();
%>

<script type="text/javascript">
//엔터 처리
$(function(){
	   $("input").not($(":button")).keypress(function (evt) {
	        if (evt.keyCode == 13) {
	            var fields = $(this).parents('form,body').find('button,input,textarea,select');
	            var index = fields.index(this);
	            if ( index > -1 && ( index + 1 ) < fields.length ) {
	                fields.eq( index + 1 ).focus();
	            }
	            return false;
	        }
	     });
});
</script>
<html>
<head>
<link href="https://fonts.googleapis.com/css?family=Sunflower:300&display=swap&subset=korean" rel="stylesheet">
<meta charset="utf-8">
<style type="text/css">
@import url(//cdn.rawgit.com/hiun/NanumSquare/master/nanumsquare.css);
body{
	font-family: 'Nanum Square', sans-serif;
	font-size: 20px;
}
</style>
</head>
<body>
<div class="header-top">
   	 <div class="header-left">
        <p style="margin-top: 15px;">
            <a href="<%=cp%>/" style="text-decoration: none;">
                <span style="width: 350px; height: 150px; position: relative;">
                	<img src="<%=cp%>/resource/images/logo2.png">
                </span>
            </a>
        </p>
    </div>

    <div class="menu" style="margin:15px auto; width:800px;">
   		 <ul class="nav" style="margin-left: 50px;">
        <li>
            <a href="<%=cp%>/info/infoWeb.do" style="margin-left: 15px;">홈페이지소개</a>
            <ul>
                <li><a href="<%=cp%>/info/infoWeb.do">조장 인사말</a></li>
                <li><a href="<%=cp%>/info/history.do">연혁</a></li>
                <li><a href="<%=cp%>/info/direction.do">찾아오시는 길</a></li>
                <li><a href="<%=cp%>/info/group.do">역할 분담</a></li>
            </ul>
        </li>
			
        <li>
            <a href="<%=cp%>/acs/list.do">교육 정보</a>
            <ul>
                <li><a href="<%=cp%>/acs/list.do" style="margin-left: 150px;" onmouseover="this.style.marginLeft='150px';">학원 검색</a></li>
                <li><a href="<%=cp%>/lts/list.do">강의 검색</a></li>
                <li><a href="<%=cp%>/cal/list.do">일정 검색</a></li>
            </ul>
        </li>

        <li>
            <a href="<%=cp%>/board/list.do">커뮤니티</a>
            <ul>
	 			<li><a href="<%=cp%>/board/list.do" style="margin-left: 267px;" onmouseover="this.style.marginLeft='267px';">자유 게시판</a></li>
                <li><a href="<%=cp%>/review/list.do">강의 평가</a></li>
            </ul>
        </li>

        <li>
            <a href="<%=cp%>/notice/list.do">고객센터</a>
            <ul>
                <li><a href="<%=cp%>/notice/list.do" style="margin-left: 380px;" onmouseover="this.style.marginLeft='380px';">공지사항</a></li>
                <li><a href="<%=cp%>/qna/list.do">Q&amp;A</a></li>
            </ul>
        </li>
		
        
        <li>
            <a href="<%=cp%>/mypage/interlecture.do">마이 페이지</a>
            <ul>
                <li><a href="<%=cp%>/mypage/interlecture.do" style="margin-left: 305px;" onmouseover="this.style.marginLeft='305px';">관심 강좌</a></li>
                <li><a href="<%=cp%>/mypage/myinfo.do">정보 수정</a></li>
                <li><a href="<%=cp%>/mypage/takinglecture.do">수강중인 강좌</a></li>
            </ul>
        </li>
       
    </ul>     
    </div>
    
    <div class="header-right" style="width:150px;">
        <c:if test="${empty sessionScope.member}">
            <a href="<%=cp%>/member/login.do"><img src="<%=cp%>/resource/images/login.png" height="65"  style="margin:20px auto"></a>
            <a href="<%=cp%>/member/login.do">
            	<span style="font-size: 15px; float: right; margin: 45px auto;">로그인</span>
            </a>
        </c:if>
           <c:if test="${not empty sessionScope.member}">
           		<table>
           		<tr>
             <td>  <span style="color:blue; font-size: 15px; float: right; margin: 45px auto;">
               ${sessionScope.member.userName}님</span></td>
                  <td>  &nbsp;|&nbsp;</td>
               <td>  <a href="<%=cp%>/member/logout.do" style="font-size: 15px;">로그아웃</a></td>
</tr>

</table>
          </c:if>
    	</div>
 </div>	
</body>
</html>

   
