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

<link rel="stylesheet" href="<%=cp%>/resource/css/tabs.css" type="text/css">

<script type="text/javascript" src="<%=cp%>/resource/js/util.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-1.12.4.min.js"></script>

<script type="text/javascript">
$(function(){
	$("#tab-week").addClass("active");
});

$(function(){
	$("ul.tabs li").click(function() {
		tab = $(this).attr("data-tab");
		
		$("ul.tabs li").each(function(){
			$(this).removeClass("active");
		});
		
		$("#tab-"+tab).addClass("active");
		
		var url="<%=cp%>/schedule"	
		if(tab=="month") {
			url+="/month.do";
		} else if(tab=="week") {
			url+="/week.do";
		} else if(tab=="day") {
			url+="/day.do";
		} else if(tab=="year") {
			url+="/year.do";
		}
		
		location.href=url;
		
	});
});

</script>


</head>
<body>

<div class="header">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>
	
<div class="container">
    <div class="body-container" style="width: 900px;">
        <div class="body-title">
            <h3><span style="font-family: Webdings">2</span> 일정관리 </h3>
        </div>
        
        <div>

            <div style="clear: both;">
	           <ul class="tabs">
			       <li id="tab-month" data-tab="month">월별</li>
			       <li id="tab-week" data-tab="week">주별</li>
			       <li id="tab-day" data-tab="day">일자별</li>
			       <li id="tab-year" data-tab="year">년별</li>
			   </ul>
		   </div>
		
		   <div id="tab-content" style="clear:both; padding: 20px 0px 0px;">
		   </div>
		
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