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

<script src="https://kit.fontawesome.com/efe4b4f562.js" crossorigin="anonymous"></script>
<script type="text/javascript" src="<%=cp%>/resource/js/util.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript">

</script>

<style type="text/css">
@import url(//cdn.rawgit.com/hiun/NanumSquare/master/nanumsquare.css);

#background{
	background-image:url(/academy/resource/images/back_opacity.png); 
    background-position: center;
    background-repeat: no-repeat;
   	background-size: 1350px 600px;
   	width: 1400px;
   	min-height: 550px;
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

.star{
  display:inline-block;
  width: 30px;
  height: 60px;
  cursor: pointer;
}
.star_left{
  background: url(http://gahyun.wooga.kr/main/img/testImg/star.png) no-repeat 0 0; 
  background-size: 60px; 
  margin-right: -3px;
}
.star_right{
  background: url(http://gahyun.wooga.kr/main/img/testImg/star.png) no-repeat -30px 0; 
  background-size: 60px; 
  margin-left: -3px;
}

.star_left2 {
  background: url(http://gahyun.wooga.kr/main/img/testImg/star.png) no-repeat 0 0; 
  background-size: 60px; 
  margin-right: -4px;
}

.star_right2 {
  background: url(http://gahyun.wooga.kr/main/img/testImg/star.png) no-repeat -30px 0; 
  background-size: 60px; 
  margin-left: -4px;
}

.star.on{
  background-image: url(http://gahyun.wooga.kr/main/img/testImg/star_on.png);
}

/* .small{ */
/* 	background-size: 30px !important;  */
/* 	margin-right: -3px !important; */
/* 	height: 25px !important; */
/* 	cursor: text !important; */
/* 	width: 15px !important; */
/* } */

/* .small.star_right{ */
/* 	background:url(http://gahyun.wooga.kr/main/img/testImg/star.png) no-repeat -14px 0; */
/* } */


</style>
<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
<script type="text/javascript">


jQuery(document).ready(function(){
	drawAvgStar();
	
	jQuery(".star-box>.star").on('click',function(){
		var idx = jQuery(this).index();
	   	
		jQuery(".star-box>.star").removeClass("on");
	   	
		for(var i=0; i<=idx; i++){
	   		jQuery(".star-box>.star").eq(i).addClass("on");
	   	}
		
		jQuery('#star_score').html(jQuery('.star-box>.star.on').length/2);
	});
});


function drawAvgStar(){
	var starCnt = Math.round(parseFloat(jQuery('.avg_rate').html()) * 2);
	
	for(var j=0; j<starCnt; j++){
   		jQuery(".avg-draw-star-rate>.star").eq(j).addClass("on");
   	}
}

function deleteReview(reNum) {
	console.log(query);
	    var query = "reNum="+reNum+"&${query}";
	    var url = "<%=cp%>/review/deleteReview.do?" + query;
		
	    if(confirm("위 자료를 삭제 하시 겠습니까 ? "))
	    	location.href=url;
	}

function updateReview(reNum) {
	 var page = "${page}";
	 var query = "reNum="+reNum+"&page="+page+"&rows="+${rows};
	 var url = "<%=cp%>/review/update.do?" + query;

	 location.href=url;
}
	</script>

	<script type="text/javascript">
	
	//-------------------------------------
	// 댓글
	function login() {
		location.href="<%=cp%>/member/login.do";
	}

	// 댓글 리스트
	$(function(){
		listPage(1);
	});

	function listPage(page) {
		var url="<%=cp%>/review/listReply.do";
		var query="reNum=${dto.reNum}&pageNo="+page;
		
		$.ajax({
			type:"get"
			,url:url
			,data:query
			,success:function(data) {
				$("#listReply").html(data);
			}
		    ,beforeSend :function(jqXHR) {
		    	jqXHR.setRequestHeader("AJAX", true);
		    }
		    ,error:function(jqXHR) {
		    	if(jqXHR.status==403) {
		    		login();
		    		return;
		    	}
		    	console.log(jqXHR.responseText);
		    }
		});
	}

	// 댓글 등록
	$(function(){
		$(".btnSendReply").click(function(){
			var reNum="${dto.reNum}";
			// var content=$(".boxTA:first").val();
			var $tb = $(this).closest("table");
			var content=$tb.find("textarea").val().trim();
			if(! content) {
				$tb.find("textarea").focus();
				return;
			}
			content = encodeURIComponent(content);
			
			var query="reNum="+reNum+"&content="+content+"&rate="+jQuery('#star_score').html();
			var url="<%=cp%>/review/insertReply.do";
			$.ajax({
				type:"post"
				,url:url
				,data:query
				,dataType:"json"
				,success:function(data) {
					$tb.find("textarea").val("");
					$('#star_score').html('0');
					jQuery(".star-box>.star").removeClass("on");
					
					var state=data.state;
					if(state=="true") {
						listPage(1);
					} 
				}
			    ,beforeSend :function(jqXHR) {
			    	jqXHR.setRequestHeader("AJAX", true);
			    }
			    ,error:function(jqXHR) {
			    	if(jqXHR.status==403) {
			    		login();
			    		return;
			    	}
			    	console.log(jqXHR.responseText);
			    }
			});
		});
	});

	// 댓글 삭제
	$(function(){
		$("body").on("click", ".deleteReply", function(){
			if(! confirm("게시물을 삭제하시겠습니까 ? "))
			    return;
			
			var url="<%=cp%>/review/deleteReply.do";
			var replyNum=$(this).attr("data-replyNum");
			var page=$(this).attr("data-pageNo");
			
			$.ajax({
				type:"post"
				,url:url
				,data:{replyNum:replyNum}
				,dataType:"json"
				,success:function(data) {
					listPage(page);
				}
			    ,beforeSend :function(jqXHR) {
			    	jqXHR.setRequestHeader("AJAX", true);
			    }
			    ,error:function(jqXHR) {
			    	if(jqXHR.status==403) {
			    		login();
			    		return;
			    	}
			    	console.log(jqXHR.responseText);
			    }
			});
		});
	});
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
           <h3><span><img src="<%=cp%>/resource/images/menu-button.png" style="height: 21px;"/></span> 커뮤니티 </h3>
        	</div>
			<table>
				<tr><td><a href="<%=cp%>/board/list.do" id="nav2">자유 게시판</a></td></tr>
				<tr><td><a href="<%=cp%>/review/list.do" id="nav1">강의 평가</a></td></tr>
			</table>
		</div>
		<div class="content2" style="font-size: 30px; font-weight: 800; color: #3598DB; width: 900px;">
			<p>강의 평가<p>
		
			<!--  여기부터 소스 -->
			<table style="border-collapse: collapse; border-spacing: 0; border-bottom: 1px solid #3598DB; border-top: 1px solid #3598DB; font-size: 17px; color: black; width: 100%;">
				<tr height="35" style="border-top: 1px solid #3598DB; border-bottom: 1px solid #3598DB;">
			    	<td colspan="2" align="center">
				  	${dto.lecName}
			    	</td>
				</tr>
			
			<tr height="35" style="border-bottom: 1px solid #3598DB;">
			    <td width="60%" align="left" style="padding-left: 5px; font-size: 20px;">
			    	전체 평점 : <span class="avg_rate">${dto.rate}</span>
			    </td>
			      <td>
			      <span class='avg-draw-star-rate' style="cursor: text !important;">
	        		<span class="draw star star_left small"></span>
				  	<span class="draw star star_right small"></span>
				
				  	<span class="draw star star_left small"></span>
				  	<span class="draw star star_right small"></span>
				
				  	<span class="draw star star_left small"></span>
				  	<span class="draw star star_right small"></span>
				
					<span class="draw star star_left small"></span>
					<span class="draw star star_right small"></span>
					
					<span class="draw star star_left small"></span>
					<span class="draw star star_right small"></span>
	        		</span>   
			    </td>
			</tr>
			
			<tr style="border-bottom: 1px solid #3598DB;">
			  <td colspan="2" align="left" style="padding: 10px 5px;" valign="top" height="80">
			      ${dto.content}
			   </td>
			</tr>
			
			</table>
			
			<table style="width: 100%; margin: 0px auto 20px; border-spacing: 0px;">
			<tr height="45">
			    <td width="300" align="left">
			    	<c:if test="${sessionScope.member.userId=='admin'}">
			          <button type="button" class="loginButton" onclick="updateReview('${dto.reNum}');">수정</button>
			       </c:if>
			       <c:if test="${sessionScope.member.userId=='admin'}">			    
			          <button type="button" class="loginButton" onclick="deleteReview('${dto.reNum}');">삭제</button>
			       </c:if>
			    </td>
			
			    <td align="right">
			        <button type="button" class="loginButton" onclick="javascript:location.href='<%=cp%>/review/list.do?${query}';">리스트</button>
			    </td>
			</tr>
			</table>
        

 	    
            <table style="width: 100%; margin: 0px auto 20px; border-spacing: 0px;">
            <tr height="100"></tr>
            <tr height='45'> 
	            <td align='left'>
	            	<span style="font-size: 25px; width:800px; color: black"> 수강 후기를 남겨주세요</span>
	            </td>
            </tr>
            <tr height='30'> 
	            <td>
	            	<div class="star-box">
	            		<span class="star star_left2"></span>
					  	<span class="star star_right2"></span>
					
					  	<span class="star star_left2"></span>
					  	<span class="star star_right2"></span>
					
					  	<span class="star star_left2"></span>
					  	<span class="star star_right2"></span>
					
						<span class="star star_left2"></span>
						<span class="star star_right2"></span>
						
						<span class="star star_left2"></span>
						<span class="star star_right2"></span>
					</div>
					<span id="star_score" style="font-size: 30px; color: black;">0</span>점
			    	<!--  jQuery('#star_score').html()  -->
	            </td>
	           
            </tr>
            <tr>
               <td>
                    <textarea class='boxTA' style='width:100%; height: 80px;'></textarea>
                </td>
            </tr>
            <tr>
               <td align='right'>
                    <button type='button' class='loginButton btnSendReply' >댓글 등록</button>
                </td>
            </tr>
            </table>
            
            <div id="listReply" style="width: 900px; font-size: 17px; color: black;"></div>
 	   </div>
 	    
        <!--  여기까지 메인 -->
    </div>
</div>

<div class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>

<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery.ui.datepicker-ko.js"></script>
</body>
</html>