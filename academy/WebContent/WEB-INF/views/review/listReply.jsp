<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String cp=request.getContextPath();
%>
<script type="text/javascript">
	jQuery(document).ready(function(){
		drawStar();
		
	});
	
	function drawStar(){
		
		var length = jQuery('.rate').length;
		
		for(var i=0; i<length ; i++){
			var starCnt = parseFloat(jQuery('.rate:eq('+i+')').html()) * 2;
			
			for(var j=0; j<starCnt; j++){
		   		jQuery(".draw-star-rate:eq("+i+")>.star").eq(j).addClass("on");
		   	}
		}
	}
</script>

<c:if test="${replyCount!=0}">
<table style='width: 100%; height : 50px; margin: 10px auto 30px; border-spacing: 0px;'>
<tr height="35">
    <td>
       <div style="clear: both;">
           <div style="float: left; width :210px;"><span style="color: #3EA9CD; font-weight: bold;">리뷰 총 ${replyCount}개</span> <span>[${pageNo}/${total_page} 페이지]</span></div>
           <div style="float: right; text-align: right;"></div>
       </div>
    </td>
</tr>

<c:forEach var="vo" items="${listReply}">
    <tr height='35' style='background: #eee;'>
       <td width='10%' style='padding:5px 5px; border:1px solid #cccccc; border-right:none;'>
           <span><b>${vo.userName}</b></span>
        </td>
        <td width='40%' style="padding:5px 5px; border:1px solid #cccccc; border-left:none; border-right:none;">
        	<span class='draw-star-rate' style="cursor: text !important;">
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
			<span style="margin-left:10px;">평점: <span class='rate'>${vo.rate}</span> 점  </span>
        </td>
       <td width='40%' style='padding:5px 5px; border:1px solid #cccccc; border-left:none;' align='right'>
           <span>${vo.created}</span> |
<c:if test="${sessionScope.member.userId==vo.userId || sessionScope.member.userId=='admin'}">		   
          <span class="deleteReply" style="cursor: pointer;" data-replyNum='${vo.replyNum}' data-pageNo='${pageNo}'>삭제</span>
</c:if>		   
<c:if test="${sessionScope.member.userId!=vo.userId && sessionScope.member.userId!='admin'}">		   
          <span class="notifyReply">신고</span>
</c:if>	
        </td>
    </tr>
    <tr>
        <td colspan='2' valign='top' style='padding:5px 5px; height: 130px;'>
                ${vo.content}
        </td>
    </tr>
</c:forEach>

     <tr height="10">
         <td colspan='2'>
             ${paging}
         </td>
     </tr>
</table>
</c:if>