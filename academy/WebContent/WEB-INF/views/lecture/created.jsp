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

<script type="text/javascript">
    function sendOk() {
        var f = document.boardForm;


    	var str = f.acaName.value;
        if(!str) {
            alert("기관명을 입력하세요. ");
            f.acaName.focus();
            return;
        }
        
    	var str = f.lecName.value;
        if(!str) {
            alert("과정명을 입력하세요. ");
            f.lecName.focus();
            return;
        }
        
        var str = f.lecStartDate.value;
        if(!str) {
            alert("교육시작일을 입력하세요. ");
            f.lecStartDate.focus();
            return;
        }
        
        var str = f.lecEndDate.value;
        if(!str) {
            alert("교육종료일을 입력하세요. ");
            f.lecEndDate.focus();
            return;
        }
        
        var str = f.lecLimit.value;
        if(!str) {
            alert("정원을 입력하세요. ");
            f.lecLimit.focus();
            return;
        }
        
        var str = f.lecIntro.value;
        if(!str) {
            alert("소개를 입력하세요. ");
            f.lecIntro.focus();
            return;
        }

    	f.action="<%=cp%>/lts/${mode}_ok.do";

        f.submit();
    }
    
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
            	<h3><span><img src="<%=cp%>/resource/images/menu-button.png" style="height: 21px;"/></span>   교육 정보 </h3>
        	</div>
			<table>
				<tr><td><a href="<%=cp%>/acs/list.do" id="nav1">교육기관 검색</a></td></tr>
				<tr><td><a href="<%=cp%>/lts/list.do" id="nav2">교육과정 검색</a></td></tr>
			</table>
		</div>
		<div class="content2" style="font-size: 30px; font-weight: 800; color: #3598DB; width: 800px;">
			<p>교육과정 등록</p>
			<br>
        
       
			<form name="boardForm" method="post" >
			  <table style="border-collapse: collapse; border-spacing: 0; border-bottom: 1px solid black; border-top: 1px solid black; font-size: 18px; color: black; width: 100%;">
			  <tr align="left" height="40" style="border-bottom: 1px solid #cccccc;"> 
			      <td width="100" bgcolor="#3598DB" style="text-align: center;">작성자</td>
			      <td style="padding-left:10px;"> 
			          ${sessionScope.member.userName}
			      </td>
			  </tr>
			  
			  <tr align="left" height="40" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;"> 
			      <td width="100" bgcolor="#3598DB" style="text-align: center;">기관명</td>
			      <td style="padding-left:10px;"> 
			          <input type="text" name="acaName" maxlength="100" class="boxTF" style="width: 95%;" value="${dto.acaName}">
			      </td>
			  </tr>
			  
			  
			  
			  <tr align="left" height="40" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;"> 
			      <td width="100" bgcolor="#3598DB" style="text-align: center;">과정명</td>
			      <td style="padding-left:10px;"> 
			          <input type="text" name="lecName" maxlength="100" class="boxTF" style="width: 95%;" value="${dto.lecName}">
			      </td>
			  </tr>
			  
			  <tr align="left" height="40" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;"> 
			      <td width="100" bgcolor="#3598DB" style="text-align: center;">교육시작일</td>
			      <td style="padding-left:10px;"> 
			          <input type="text" name="lecStartDate" maxlength="100" class="boxTF" style="width: 95%;" value="${dto.lecStartDate}">
			      </td>
			  </tr>
			  
			  <tr align="left" height="40" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;"> 
			      <td width="100" bgcolor="#3598DB" style="text-align: center;">교육종료일</td>
			      <td style="padding-left:10px;"> 
			          <input type="text" name="lecEndDate" maxlength="100" class="boxTF" style="width: 95%;" value="${dto.lecEndDate}">
			      </td>
			  </tr>

			  <tr align="left" height="40" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;"> 
			      <td width="100" bgcolor="#3598DB" style="text-align: center;">정원</td>
			      <td style="padding-left:10px;"> 
			          <input type="text" name="lecLimit" maxlength="100" class="boxTF" style="width: 95%;" value="${dto.lecLimit}">
			      </td>
			  </tr>
			
			  <tr align="left" style="border-bottom: 1px solid #cccccc;"> 
			      <td width="100" bgcolor="#3598DB" style="text-align: center; padding-top:5px;" valign="top">교육과정 소개</td>
			      <td valign="top" style="padding:5px 0px 5px 10px;"> 
			          <textarea name="lecIntro" rows="12" class="boxTA" style="width: 95%;">${dto.lecIntro}</textarea>
			      </td>
			  </tr>
			
			  </table>
				
				
			  <table style="width: 100%; margin: 0px auto; border-spacing: 0px;">
			     <tr height="45"> 
			      <td align="center" >
			      	<c:if test="${mode=='update'}">
			      		<input type="hidden" name="page" value="${page}">
			      		<input type="hidden" name="lecCode" value="${dto.lecCode}">
			      	
			      	</c:if>
			        <button type="button" class="loginButton" onclick="sendOk();">${mode=='update'?'수정완료':'등록하기'}</button>
			        <button type="reset" class="loginButton">다시입력</button>
			        <button type="button" class="loginButton" onclick="javascript:location.href='<%=cp%>/lts/list.do';">${mode=='update'?'수정취소':'등록취소'}</button>

				  </td>
			    </tr>
			  </table>
			</form>
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