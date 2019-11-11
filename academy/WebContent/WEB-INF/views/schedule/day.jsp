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
	font-weight: 700;
	font-size: 30px;
	font-family: 'Nanum Square', sans-serif;
	padding: 2px 4px 4px;
	text-align: center;
	position: relative;
	top: 4px;
}

.btnDate {
	display: inline-block;
	font-size: 10px;
	font-family: 'Nanum Square', sans-serif;
	color: #333333;
	padding: 3px 5px 5px;
	border: 1px solid #cccccc;
	background-color: #fff;
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
	font-size: 15px;
}
</style>

<script type="text/javascript" src="<%=cp%>/resource/js/util.js"></script>
<script type="text/javascript"
	src="<%=cp%>/resource/jquery/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript">
$(function(){
	$("#tab-day").addClass("active");
});

$(function(){
	var today="${today}";
	var date="${date}";
	$("#smallCalendar .textDate").each(function (i) {
        var s=$(this).attr("data-date");
        if(s==today) {
        	$(this).parent().css("background", "#ffffd9");
        }
        if(s==date) {
        	$(this).css("font-weight", "600");
        }
    });
});

function changeDate(date) {
	var url="<%=cp%>/cal/day.do?date="+date;
	location.href=url;
}


// 작은달력 날짜클릭-일일 일정보기
$(function(){
	$(".textDate").click(function(){
		var date=$(this).attr("data-date");
		var url="<%=cp%>/cal/day.do?date="+date;
		location.href=url;
	});
});

<c:if test="${not empty dto}">
$(function(){
	$("#btnUpdate").click(function(){
		// 폼 reset
		$("form[name=scheduleForm]").each(function(){
			this.reset();
		});
		
		$("#form-sday").datepicker({showMonthAfterYear:true});
		$("#form-eday").datepicker({showMonthAfterYear:true});
		
		var date1="${dto.sday}";
		var date2="${dto.eday}";
		if(date2=="") {
			date2=date1;
			$("#form-eday").val(date2);
		}
		
		$("#form-sday").datepicker("option", "defaultDate", date1);
		$("#form-eday").datepicker("option", "defaultDate", date2);
	
		
		$('#schedule-dialog').dialog({
			  modal: true,
			  height: 650,
			  width: 600,
			  title: '스케쥴 수정',
			  close: function(event, ui) {
			  }
		});
	});
});

// 수정완료
$(function(){
	$("#btnScheduleSendOk").click(function(){
		
		if(! check()) {
			return;
		}
		
		var query=$("form[name=scheduleForm]").serialize();
		var url="<%=cp%>/cal/update.do";
		
		$.ajax({
			type:"post"
			,url:url
			,data:query
			,dataType:"json"
			,success:function(data) {
				var state=data.state;
				if(state=="true") {
					var date="${date}";
					var num="${dto.num}";
					location.href="<%=cp%>/cal/day.do?date="+date+"&num="+num;
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

// 수정 대화상자 닫기
$(function(){
	$("#btnScheduleSendCancel").click(function(){
		$('#schedule-dialog').dialog("close");
	});
});

// 수정내용 유효성 검사
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

//시간 형식 유효성 검사
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

function deleteOk(num) {
	if(confirm("일정을 삭제 하시 겠습니까 ? ")) {
		var date="${date}";
		var url="<%=cp%>/cal/delete.do?date="+date+"&num="+num;
		location.href=url;
	}
}

</c:if>

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
				<div id="Right"
					style="margin: auto 100px; padding-left: 30px; padding-rigth: 0px; width: 600px; box-sizing: border-box;">

					<table style="width: 100%; border-spacing: 0px;">
						<tr height="35">
							<td align="left"><span class="titleDate">${year}년
									${month}월${day}일 강좌</span></td>
						</tr>
					</table>
					<br>
					<c:if test="${empty dto}">
						<table
							style="width: 100%; margin-top: 5px; border-spacing: 0px; border-collapse: collapse;">
							<tr height="35">
								<td align="center">등록된 일정이 없습니다.</td>
							</tr>
						</table>
					</c:if>
					<c:if test="${not empty dto}">
						<table
							style="width: 100%; margin-top: 5px; border-spacing: 0px; border-collapse: collapse;">
							<tr height="35"
								style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;">
								<td width="200" style="text-align: center; background: #3598DB;">
									<label style="font-weight: 900; color: white">강의명</label>
								</td>
								<td style="text-align: left; padding-left: 7px;">
									<p style="margin-top: 1px; margin-bottom: 1px;">
										${dto.lecName}</p>
								</td>
							</tr>
							<tr height="35"
								style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;">
								<td width="200" style="text-align: center; background: #3598DB;">
									<label style="font-weight: 900; color: white">학원명</label>
								</td>
								<td style="text-align: left; padding-left: 7px;">
									<p style="margin-top: 1px; margin-bottom: 1px;">
										${dto.acaName}</p>
								</td>
							</tr>
							<tr height="35"
								style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;">
								<td width="200" style="text-align: center; background: #3598DB;">
									<label style="font-weight: 900; color: white">강의 시작 날짜</label>
								</td>
								<td style="text-align: left; padding-left: 7px;">
									<p style="margin-top: 1px; margin-bottom: 1px;">
										${dto.sday}</p>
								</td>
							</tr>
							<tr height="35"
								style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;">
								<td width="200" style="text-align: center; background: #3598DB;">
									<label style="font-weight: 900; color: white">강의 종료 날짜</label>
								</td>
								<td style="text-align: left; padding-left: 7px;">
									<p style="margin-top: 1px; margin-bottom: 1px;">
										${dto.eday}</p>
								</td>
							</tr>
							<tr height="35"
								style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;">
								<td width="200" style="text-align: center; background: #3598DB;">
									<label style="font-weight: 900; color: white">강의 수강 가능
										인원</label>
								</td>
								<td style="text-align: left; padding-left: 7px;">
									<p style="margin-top: 1px; margin-bottom: 1px;">
										${dto.lecLimit}</p>
								</td>
							</tr>
							<tr height="35"
								style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;">
								<td width="200" style="text-align: center; background: #3598DB;">
									<label style="font-weight: 900; color: white">강의 소개</label>
								</td>
								<td style="text-align: left; padding-left: 7px;">
									<p style="margin-top: 1px; margin-bottom: 1px;">
										${dto.memo}</p>
								</td>
							</tr>
							<tr height="45">
								<td colspan="2" align="right" style="padding-right: 5px;">
									<button type="button" id="btnList" class="btn"
										onclick="javascript:location.href='<%=cp%>/cal/list.do';">리스트</button>
									<c:if test="${sessionScope.member.userId=='admin'}">
										<td colspan="2" align="right" style="padding-right: 5px;">
											<button type="button" id="btnUpdate" class="btn">수정</button>
											<button type="button" id="btnDelete" class="btn"
												onclick="deleteOk('${dto.num}');">삭제</button>
										</td>
									</c:if>
								</td>
							</tr>

						</table>
					</c:if>


				</div>
			</div>
		</div>
		<c:if test="${not empty dto}">
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
										maxlength="100" class="boxTF" style="width: 95%;"
										value="${dto.lecName}">
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
									<input type="text" name="lecNum" id="form-lecNum"
										maxlength="100" class="boxTF" style="width: 95%;"
										value="${dto.num}">
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
									<input type="text" name="sday" value="${dto.sday}"
										id="form-sday" maxlength="10" class="boxTF"
										readonly="readonly" style="width: 25%; background: #ffffff;">
									<input type="text" name="stime" id="form-stime" maxlength="5"
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
									<input type="text" name="eday" value="${dto.eday}"
										id="form-eday" maxlength="10" class="boxTF"
										readonly="readonly" style="width: 25%; background: #ffffff;">
									<input type="text" name="etime" id="form-etime" maxlength="5"
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
									<input type="text" name="lecLimit" id="form-lecLimit"
										maxlength="100" class="boxTF" style="width: 95%;"
										value="${dto.lecLimit}">
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
									<input type="text" name="acaNum" id="form-acaNum"
										maxlength="100" class="boxTF" style="width: 95%;"
										value="${dto.acaNum}">
								</p>
							</td>
						</tr>
						<tr>
							<td width="100" valign="top"
								style="text-align: right; padding-top: 5px;"><label
								style="font-weight: 900;">강좌 소개</label></td>
							<td style="padding: 0 0 15px 15px;">
								<p style="margin-top: 1px; margin-bottom: 5px;">
									<textarea name="memo" id="form-memo" class="boxTA"
										style="width: 93%; height: 70px;">${dto.memo}</textarea>
								</p>
							</td>
						</tr>

						<tr height="45">
							<td align="center" colspan="2"><input type="hidden"
								name="num" value="${dto.num}">
								<button type="button" class="btn" id="btnScheduleSendOk">수정완료</button>
								<button type="button" class="btn" id="btnScheduleSendCancel">수정취소</button>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</c:if>
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