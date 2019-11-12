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

<link rel="stylesheet" href="<%=cp%>/resource/css/tabs.css"
	type="text/css">

<style type="text/css">
/* 모달대화상자 타이틀바 */
.ui-widget-header {
	background: none;
	border: none;
	height: 35px;
	line-height: 35px;
	border-bottom: 1px solid #cccccc;
	border-radius: 0px;
}

.help-block {
	margin-top: 3px;
}

.titleDate {
	display: inline-block;
	font-weight: 600;
	font-size: 19px;
	padding: 2px 4px 4px;
	text-align: center;
	position: relative;
	top: 4px;
}

.btnDate {
	display: inline-block;
	font-size: 10px;
	color: white;
	padding: 3px 5px 5px;
	border: 1px solid #3598DB;
	background-color: #3598DB;
	text-align: center;
	cursor: pointer;
	border-radius: 2px;
}

.textDate {
	font-weight: 500;
	cursor: pointer;
	display: block;
	color: #333333;
}

.preMonthDate, .nextMonthDate {
	color: #aaaaaa;
}

.nowDate {
	color: #111111;
}

.saturdayDate {
	color: #0000ff;
}

.sundayDate {
	color: #ff0000;
}

/*일정 등록 제목*/

.scheduleSubjectStart {
	display: block;
	/*width:100%;*/
	width: 110px;
	margin: 1.5px 0;
	font-size: 13px;
	color: white;
	background: #3598DB;
	cursor: pointer;
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
}
.scheduleSubjectEnd {
	display: block;
	/*width:100%;*/
	width: 110px;
	margin: 1.5px 0;
	font-size: 13px;
	color: white;
	background: #F46F94;
	cursor: pointer;
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
}
/*일정 더보기 */
.scheduleMore {
	display: block;
	width: 110px;
	margin: 0 0 1.5px;
	font-size: 13px;
	color: #555555;
	cursor: pointer;
	text-align: right;
}

@import url(//cdn.rawgit.com/hiun/NanumSquare/master/nanumsquare.css);

body {
	font-family: 'Nanum Square', sans-serif;
	font-size: 20px;
}

.container2 {
	width: 100%;
	text-align: left;
}

.body-container2 {
	margin: 20px auto;
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
	/*margin-top: 30px;*/
}

.navigation2 td {
	font-family: 'Nanum Square', sans-serif;
	font-size: 20px;
	border-radius: 2px;
	margin: 20px auto;
	padding: 10px;
}

#nav3 {
	font-weight: 800;
}

#schedule-dialog {
	font-family: 'Nanum Square', sans-serif;
	font-size : 15px;
}
</style>

<script type="text/javascript" src="<%=cp%>/resource/js/util.js"></script>
<script type="text/javascript"
	src="<%=cp%>/resource/jquery/js/jquery-1.12.4.min.js"></script>

<script type="text/javascript">
$(function(){
	$("#tab-month").addClass("active");
});

$(function(){
	var today="${today}";
	$("#largeCalendar .textDate").each(function (i) {
        var s=$(this).attr("data-date");
        if(s==today) {
        	$(this).parent().css("background", "#ffffd9");
        }
    });
});


function changeDate(year, month) {
	<%-- var url="<%=cp%>/schedule/month.do?year="+year+"&month="+month; --%>
	var url="<%=cp%>/cal/list.do?year="+year+"&month="+month;
	location.href=url;
}

// 스케쥴 등록 -----------------------
// 등록 대화상자 출력
$(function(){
	$(".textDate").click(function(){
		// 폼 reset
		$("form[name=scheduleForm]").each(function(){
			this.reset();
		});
		
		$("#form-eday").closest("tr").show();
		
		var date=$(this).attr("data-date");
		date=date.substr(0,4)+"-"+date.substr(4,2)+"-"+date.substr(6,2);

		$("form[name=scheduleForm] input[name=sday]").val(date);
		$("form[name=scheduleForm] input[name=eday]").val(date);
		
		$("#form-sday").datepicker({showMonthAfterYear:true});
		$("#form-eday").datepicker({showMonthAfterYear:true});
		
		$("#form-sday").datepicker("option", "defaultDate", date);
		$("#form-eday").datepicker("option", "defaultDate", date);
		
		$('#schedule-dialog').dialog({
			  modal: true,
			  height: 650,
			  width: 600,
			  title: '강의 스케줄 등록',
			  close: function(event, ui) {
			  }
		});

	});
});

$(function(){
	

	$("#form-sday").change(function(){
		$("#form-eday").val($("#form-sday").val());
	});
	

	
});

// 등록
$(function(){
	$("#btnScheduleSendOk").click(function(){
		if(! check() ) { //유효성 검사 통과 안하면 return
			return;
		}
		
		var query=$("form[name=scheduleForm]").serialize();
		<%-- var url="<%=cp%>/schedule/insert.do"; --%>
		var url="<%=cp%>/cal/insert.do";

		$.ajax({
			type:"post"
			,url:url
			,data:query
			,dataType:"json"
			,success:function(data) {
				var state=data.state;
				if(state=="true") {
					var dd=$("#form-sday").val().split("-");
					var y=dd[0];
					var m=dd[1];
					if(m.substr(0,1)=="0") 	m=m.substr(1,1);
				
					<%-- location.href="<%=cp%>/schedule/month.do?year="+y+"&month="+m; --%>
					location.href="<%=cp%>/cal/list.do?year="+y+"&month="+m;
				}
			}
		    ,beforeSend :function(jqXHR) {
		    	jqXHR.setRequestHeader("AJAX", true);
		    }
		    ,error:function(jqXHR) {
		    	if(jqXHR.status==403) {
		    		location.href="<%=cp%>/member/login.do";
		    		return;
		    	}
		    	console.log(jqXHR.responseText);
		    }
		});
		
	});
});

// 등록 대화상자 닫기
$(function(){
	$("#btnScheduleSendCancel").click(function(){
		$('#schedule-dialog').dialog("close");
	});
});

// 등록내용 유효성 검사
function check() {
	if(! $("#form-subject").val()) {
		$("#form-subject").focus();
		return false;
	}

	if(! $("#form-sday").val()) {
		$("#form-sday").focus();
		return false;
	}

	if($("#form-eday").val()) {
		var s1=$("#form-sday").val().replace("-", "");
		var s2=$("#form-eday").val().replace("-", "");
		if(s1>s2) {
			$("#form-sday").focus();
			return false;
		}
	}
	
	return true;
}

// 시간 형식 유효성 검사
function isValidTime(data) {
	if(! /(\d){2}[:](\d){2}/g.test(data)) {
		return false;
	}
	
	var t=data.split(":");
	if(t[0]<0||t[0]>23||t[1]<0||t[1]>59) {
		return false;
	}

	return true;
}

// 스케쥴 제목 클릭 -----------------------
//시작 날짜
$(function(){
	$(".scheduleSubjectStart").click(function(){
		var date=$(this).attr("data-date");
		var num=$(this).attr("data-num");
		var url="<%=cp%>/cal/day.do?date="+date+"&num="+num;
		location.href=url;
	});
});
//종료 날짜
$(function(){
	$(".scheduleSubjectEnd").click(function(){
		var date=$(this).attr("data-date");
		var num=$(this).attr("data-num");
		var url="<%=cp%>/cal/day.do?date="+date+"&num="+num;
		location.href=url;
	});
});

//스케쥴 more(더보기) -----------------------
$(function(){
	$(".scheduleMore").click(function(){
		var date=$(this).attr("data-date");
		var url="<%=cp%>/cal/day.do?date="+date;
		location.href=url;
	});
});

</script>


</head>
<body>

	<div class="header">
		<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
	</div>

	<div class="container2">
		<div class="body-container2">
			<div class="navigation2">
				<div class="body-title">
					<h3>
						<span><img src="<%=cp%>/resource/images/menu-button.png"
							style="height: 21px;" /></span> 교육 정보
					</h3>
				</div>
				<table>
					<tr>
						<td><a href="<%=cp%>/acs/list.do" id="nav1">학원 검색</a></td>
					</tr>
					<tr>
						<td><a href="<%=cp%>/lts/list.do" id="nav2">강의 검색</a></td>
					</tr>
					<tr>
						<td><a href="<%=cp%>/cal/list.do" id="nav3">일정 검색</a></td>
					</tr>

				</table>
			</div>
			<div class="content2">
				<div id="tab-content" style="clear: both; margin: auto 40px; padding-bottom:20px;">
						<p style="font-size: 30px; font-weight: 800; color: #3598DB; padding-top:20px;">일정 관리</p>
					<table style="width: 840px; margin: 0px auto; border-spacing: 0;">
						<tr height="60">
							<td width="200">&nbsp;</td>
							<td align="center"><span class="btnDate"
								onclick="changeDate(${todayYear}, ${todayMonth});">오늘</span> <span
								class="btnDate" onclick="changeDate(${year}, ${month-1});">＜</span>
								<span class="titleDate">${year}년 ${month}월</span> <span
								class="btnDate" onclick="changeDate(${year}, ${month+1});">＞</span>
							</td>
							<td width="200">&nbsp;</td>
						</tr>
					</table>

					<table id="largeCalendar"
						style="width: 840px; margin: 0px auto; border-spacing: 1px; background: #cccccc;">
						<tr align="center" height="30" bgcolor="#3598DB">
							<td width="120" style="color: #ff0000;">일</td>
							<td width="120" style="color: white;">월</td>
							<td width="120" style="color: white;">화</td>
							<td width="120" style="color: white;">수</td>
							<td width="120" style="color: white;">목</td>
							<td width="120" style="color: white;">금</td>
							<td width="120" style="color: #0000ff;">토</td>
						</tr>

						<c:forEach var="row" items="${days}">
							<tr align="left" height="120" valign="top" bgcolor="#ffffff">
								<c:forEach var="d" items="${row}">
									<td style="padding: 5px; box-sizing: border-box;">${d}</td>
								</c:forEach>
							</tr>
						</c:forEach>
					</table>

				</div>

			</div>

		</div>

		<div id="schedule-dialog" style="display: none;">
			<form name="scheduleForm">
				<table
					style="width: 100%; margin: 20px auto 0px; border-spacing: 0px; border-collapse: collapse;">
					<tr>
						<td width="100" valign="top"
							style="text-align: right; padding-top: 5px;"><label
							style="font-weight: 900;">강의명</label></td>
						<td style="padding: 0 0 15px 15px;">
							<p style="margin-top: 1px; margin-bottom: 5px;">
								<input type="text" name="subject" id="form-subject"
									maxlength="100" class="boxTF" style="width: 95%;">
							</p>
							<p class="help-block">* 강의명은 필수 입니다.</p>
						</td>
					</tr>
					
					<tr>
						<td width="100" valign="top"
							style="text-align: right; padding-top: 5px;"><label
							style="font-weight: 900;">강의 번호</label></td>
						<td style="padding: 0 0 15px 15px;">
							<p style="margin-top: 1px; margin-bottom: 5px;">
								<input type="text" name="lecNum" id="form-subject"
									maxlength="100" class="boxTF" style="width: 95%;">
							</p>
							<p class="help-block">* 2자리에서 5자리 숫자입니다.</p>
						</td>
					</tr>

					<tr>
						<td width="100" valign="top"
							style="text-align: right; padding-top: 5px;"><label
							style="font-weight: 900;">시작일자</label></td>
						<td style="padding: 0 0 15px 15px;">
							<p style="margin-top: 1px; margin-bottom: 5px;">
								<input type="text" name="sday" id="form-sday" maxlength="10"
									class="boxTF" readonly="readonly"
									style="width: 25%; background: #ffffff;"> <input
									type="text" name="stime" id="form-stime" maxlength="5"
									class="boxTF" style="width: 15%; display: none;"
									placeholder="시작시간">
							</p>
							<p class="help-block">* 시작날짜는 필수입니다.</p>
						</td>
					</tr>

					<tr>
						<td width="100" valign="top"
							style="text-align: right; padding-top: 5px;"><label
							style="font-weight: 900;">종료일자</label></td>
						<td style="padding: 0 0 15px 15px;">
							<p style="margin-top: 1px; margin-bottom: 5px;">
								<input type="text" name="eday" id="form-eday" maxlength="10"
									class="boxTF" readonly="readonly"
									style="width: 25%; background: #ffffff;"> <input
									type="text" name="etime" id="form-etime" maxlength="5"
									class="boxTF" style="width: 15%; display: none;"
									placeholder="종료시간">
							</p>
							<p class="help-block">종료일자는 선택사항이며, 시작일자보다 작을 수 없습니다.</p>
						</td>
					</tr>
					
					<tr>
						<td width="100" valign="top"
							style="text-align: right; padding-top: 5px;"><label
							style="font-weight: 900;">수강 가능 인원수</label></td>
						<td style="padding: 0 0 15px 15px;">
							<p style="margin-top: 1px; margin-bottom: 5px;">
								<input type="text" name="lecLimit" id="form-subject"
									maxlength="100" class="boxTF" style="width: 95%;">
							</p>
							<p class="help-block">* 수강 가능 인원수를 입력하세요</p>
						</td>
					</tr>
					<tr>
						<td width="100" valign="top"
							style="text-align: right; padding-top: 5px;"><label
							style="font-weight: 900;">학원 번호</label></td>
						<td style="padding: 0 0 15px 15px;">
							<p style="margin-top: 1px; margin-bottom: 5px;">
								<input type="text" name="acaNum" id="form-subject"
									maxlength="100" class="boxTF" style="width: 95%;">
							</p>
						</td>
					</tr>
					<tr>
			      		<td width="100" valign="top" style="text-align: right; padding-top: 5px;">
			            <label style="font-weight: 900;">강좌 소개</label>
			      		</td>
			     		 <td style="padding: 0 0 15px 15px;">
			       		 <p style="margin-top: 1px; margin-bottom: 5px;">
			            <textarea name="memo" id="form-memo" class="boxTA" style="width:93%; height: 70px;"></textarea>
			        	</p>
			     		 </td>
			 		 </tr>

					<tr height="45">
						<td align="center" colspan="2">
							<button type="button" class="btn" id="btnScheduleSendOk">일정등록</button>
							<button type="reset" class="btn">다시입력</button>
							<button type="button" class="btn" id="btnScheduleSendCancel">등록취소</button>
						</td>
					</tr>
				</table>
			</form>

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