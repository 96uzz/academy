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

function memberOk() {
	var f = document.memberForm;
	var str;
	str = f.userId.value;
	str = str.trim();
	if(!str) {
		alert("아이디를 입력하세요. ");
		f.userId.focus();
		return;
	}
	if(!/^[a-z][a-z0-9_]{4,11}$/i.test(str)) { 
		alert("아이디는 5~12자의 영숫자이며 첫글자는 영문자이어야 합니다.");
		f.userId.focus();
		return;
	}
	f.userId.value = str;
	str = f.userPwd.value;
	str = str.trim();
	if(!str) {
		alert("패스워드를 입력하세요. ");
		f.userPwd.focus();
		return;
	}
	if(!/^(?=.*[a-z])(?=.*[!@#$%^*+=-]|.*[0-9]).{5,12}$/i.test(str)) { 
		alert("패스워드는 5~12자이며 하나 이상의 숫자나 특수문자가 포함되어야 합니다.");
		f.userPwd.focus();
		return;
	}
	f.userPwd.value = str;
	if(str!= f.userPwdCheck.value) {
        alert("패스워드가 일치하지 않습니다. ");
        f.userPwdCheck.focus();
        return;
	}
	
    str = f.userName.value;
	str = str.trim();
    if(!str) {
        alert("이름을 입력하세요. ");
        f.userName.focus();
        return;
    }
    f.userName.value = str;
    str = f.birth.value;
	str = str.trim();
    if(!str || !isValidDateFormat(str)) {
        alert("생년월일를 입력하세요[YYYY-MM-DD]. ");
        f.birth.focus();
        return;
    }
    
    str = f.tel1.value;
	str = str.trim();
    if(!str) {
        alert("전화번호를 입력하세요. ");
        f.tel1.focus();
        return;
    }
    str = f.tel2.value;
	str = str.trim();
    if(!str) {
        alert("전화번호를 입력하세요. ");
        f.tel2.focus();
        return;
    }
    if(!/^(\d+)$/.test(str)) {
        alert("숫자만 가능합니다. ");
        f.tel2.focus();
        return;
    }
    str = f.tel3.value;
	str = str.trim();
    if(!str) {
        alert("전화번호를 입력하세요. ");
        f.tel3.focus();
        return;
    }
    if(!/^(\d+)$/.test(str)) {
        alert("숫자만 가능합니다. ");
        f.tel3.focus();
        return;
    }
    
    str = f.email1.value;
	str = str.trim();
    if(!str) {
        alert("이메일을 입력하세요. ");
        f.email1.focus();
        return;
    }
    str = f.email2.value;
	str = str.trim();
    if(!str) {
        alert("이메일을 입력하세요. ");
        f.email2.focus();
        return;
    }
       
    
    
    var mode="${mode}";
    if(mode=="created") {
    	f.action = "<%=cp%>/member/member_ok.do";
    } else if(mode=="update") {
    	f.action = "<%=cp%>/member/update_ok.do";
    }
    f.submit();
}
function changeEmail() {
    var f = document.memberForm;
	    
    var str = f.selectEmail.value;
    if(str!="direct") {
        f.email2.value=str; 
        f.email2.readOnly = true;
       
    }
    else {
        f.email2.value="";
        f.email2.readOnly = false;
        f.email2.focus();
    }
}
$(function(){
	$("form input[name=userId]").change(function(){
		var $id = $(this);
		var userId = $id.val().trim();
		if(!/^[a-z][a-z0-9_]{4,9}$/i.test(userId)) { 
			$(this).focus();
			return false;
		}
		
		var url="<%=cp%>/member/userIdCheck.do";
		var query="userId="+userId;
		
		$.ajax({
			type:"POST",
			url:url,
			data:query,
			dataType:"json",
			success:function(data) {
				// console.log(data);
				var passed = data.passed;
				if(passed=="true"){
					var s = "<span style='color:blue;font-weight:bold;'>"
					+userId+"</span> 아이디는 사용가능합니다."
					$id.parent().next(".help-block").html(s);
				} else {
					var s = "<span style='color:red;font-weight:bold;'>"
						+userId+"</span> 아이디는 사용 불가능합니다."
						$id.parent().next(".help-block").html(s);
						$id.val("");
						$id.focus();
				}
			},
			error:function(e){
				console.log(e.responseText);
			}
		});
		
	});
});
function termsOfUse(){
	 window.open("termsOfUse.do", "a", "width=1000, height=770, left=450, top=50"); 

}
function cancel(){
	var f = document.deleteForm;
	
	
	if(confirm('정말로 탈퇴하시겠습니까? 탈퇴시 향후 3년간 재가입이 불가능합니다.')) {
		f.action = "<%=cp%>/member/delete.do";
		f.submit();
	} else {
		return;
	}

	
}
</script>
</head>
<body>

<div class="header">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>

<div id="background" class="container2">
    <div class="body-container2">
		<c:choose>
		<c:when test= "${mode eq 'update'}">
		<div class="navigation2" >
			<div class="body-title">
            <h3><span><img src="<%=cp%>/resource/images/menu-button.png" style="height: 21px;"/></span>   마이 페이지 </h3>
        	</div>
			<table>
				<tr><td><a href="<%=cp%>/member/interlecture.do">관심강좌</a></td></tr>
				<tr><td><a href="<%=cp%>/member/pwd.do?mode=update" id="nav1">정보 수정</a></td></tr>
				<tr><td><a href="<%=cp%>/member/takinglecture.do">수강중인 강좌</a></td></tr>
			</table>
		</div>
		</c:when>
		<c:otherwise>
		
		</c:otherwise>
		</c:choose>
		<div class="content2" style="margin-left: auto; margin-right: auto;">
					<h2 style="${mode=='update' ? 'padding-left: 30px;' : 'padding-left: 330px;'}  color: #3598DB;">${title}</h2>

					<div class="body-board">
						<form name="memberForm" method="post">
							<table
								style="width: 100%; margin: 20px auto 0px; border-spacing: 0px;">
								<tr>
									<td width="150" valign="top"
										style="text-align: right; padding-top: 5px;"><label
										style="font-weight: 900;"></label></td>
								</tr>
								<tr>
									<td width="150" valign="top"
										style="text-align: right; padding-top: 5px;"><label
										style="font-weight: 900;">아이디</label></td>
									<td style="padding: 0 0 15px 15px;">
										<p style="margin-top: 1px; margin-bottom: 5px;">
											<input type="text" name="userId" id="userId"
												value="${dto.userId}" style="width: 35%;"
												${mode=="update" ? "readonly='readonly' ":""} maxlength="15"
												class="boxTF" placeholder="아이디">
										</p>
										<p class="help-block">5~12자의 영숫자이며 첫글자는 영문자로 시작</p>
									</td>
								</tr>

								<tr>
									<td width="100" valign="top"
										style="text-align: right; padding-top: 5px;"><label
										style="font-weight: 900;">패스워드</label></td>
									<td style="padding: 0 0 15px 15px;">
										<p style="margin-top: 1px; margin-bottom: 5px;">
											<input type="password" name="userPwd" maxlength="15"
												class="boxTF" style="width: 35%;" placeholder="패스워드">
										</p>
										<p class="help-block">5~12자 이내이며 하나 이상의 숫자나 특수문자 포함</p>
									</td>
								</tr>

								<tr>
									<td width="100" valign="top"
										style="text-align: right; padding-top: 5px;"><label
										style="font-weight: 900;">패스워드 확인</label></td>
									<td style="padding: 0 0 15px 15px;">
										<p style="margin-top: 1px; margin-bottom: 5px;">
											<input type="password" name="userPwdCheck" maxlength="15"
												class="boxTF" style="width: 35%;" placeholder="패스워드 확인">
										</p>
										<p class="help-block"></p>
									</td>
								</tr>

								<tr>
									<td width="100" valign="top"
										style="text-align: right; padding-top: 5px;"><label
										style="font-weight: 900;">이름</label></td>
									<td style="padding: 0 0 15px 15px;">
										<p style="margin-top: 1px; margin-bottom: 5px;">
											<input type="text" name="userName" value="${dto.userName}"
												maxlength="30" class="boxTF" style="width: 35%;"
												${mode=="update" ? "readonly='readonly' ":""}
												placeholder="이름">
										</p>
									
									</td>
								</tr>

								<tr>
									<td width="100" valign="top"
										style="text-align: right; padding-top: 5px;"><label
										style="font-weight: 900;">생년월일</label></td>
									<td style="padding: 0 0 15px 15px;">
										<p style="margin-top: 1px; margin-bottom: 5px;">
											<input type="text" name="birth" value="${dto.birth}"
												maxlength="10" class="boxTF" style="width: 35%;"
												placeholder="생년월일">
										</p>
										<p class="help-block">2000-01-01 형식으로 입력</p>
									</td>
								</tr>

								<tr>
									<td width="100" valign="top"
										style="text-align: right; padding-top: 5px;"><label
										style="font-weight: 900;">이메일</label></td>
									<td style="padding: 0 0 15px 15px;">
										<p style="margin-top: 1px; margin-bottom: 5px;">

											<input type="text" name="email1" value="${dto.email1}"
												size="18" maxlength="30" class="boxTF"> @ <input
												type="text" name="email2" value="${dto.email2}" size="10"
												maxlength="30" class="boxTF" readonly="readonly"> <select
												name="selectEmail" onchange="changeEmail();"
												class="selectField">
												<option value="">선 택</option>
												<option value="naver.com"
													${dto.email2=="naver.com" ? "selected='selected'" : ""}>naver.com</option>
												<option value="hanmail.net"
													${dto.email2=="hanmail.net" ? "selected='selected'" : ""}>hanmail.net</option>
												<option value="hotmail.com"
													${dto.email2=="hotmail.com" ? "selected='selected'" : ""}>hotmail.com</option>
												<option value="gmail.com"
													${dto.email2=="gmail.com" ? "selected='selected'" : ""}>gmail.com</option>
												<option value="direct">직접입력</option>
											</select>
										</p>
									</td>
								</tr>

								<tr>
									<td width="100" valign="top"
										style="text-align: right; padding-top: 5px;"><label
										style="font-weight: 900;">전화번호</label></td>
									<td style="padding: 0 0 15px 15px;">
										<p style="margin-top: 1px; margin-bottom: 5px;">
											<select class="selectField" id="tel1" name="tel1">
												<option value="">선 택</option>
												<option value="010"
													${dto.tel1=="010" ? "selected='selected'" : ""}>010</option>
												<option value="011"
													${dto.tel1=="011" ? "selected='selected'" : ""}>011</option>
												<option value="016"
													${dto.tel1=="016" ? "selected='selected'" : ""}>016</option>
												<option value="017"
													${dto.tel1=="017" ? "selected='selected'" : ""}>017</option>
												<option value="018"
													${dto.tel1=="018" ? "selected='selected'" : ""}>018</option>
												<option value="019"
													${dto.tel1=="019" ? "selected='selected'" : ""}>019</option>
											</select> - <input type="text" name="tel2" value="${dto.tel2}"
												class="boxTF" maxlength="4" size="8" style="width: 10%;">
											- <input type="text" name="tel3" value="${dto.tel3}"
												class="boxTF" maxlength="4" size="8" style="width: 10%;">

										</p>
									</td>
								</tr>

								<tr>
									<td width="100" valign="top"
										style="text-align: right; padding-top: 5px;"><label
										style="font-weight: 900;">우편번호</label></td>
									<td style="padding: 0 0 15px 15px;">
										<p style="margin-top: 1px; margin-bottom: 5px;">
											<input type="text" name="zip" id="zip" value="${dto.zip}"
												class="boxTF" readonly="readonly" style="width: 10%;">
											<button type="button" class="btn" onclick="daumPostcode();">우편번호</button>
										</p>
									</td>
								</tr>

								<tr>
									<td width="100" valign="top"
										style="text-align: right; padding-top: 5px;"><label
										style="font-weight: 900;">주소</label></td>
									<td style="padding: 0 0 15px 15px;">
										<p style="margin-top: 1px; margin-bottom: 5px;">
											<input type="text" name="addr1" id="addr1"
												value="${dto.addr1}" maxlength="50" class="boxTF"
												style="width: 70%;" placeholder="기본 주소" readonly="readonly">
										</p>
										<p style="margin-bottom: 5px;">
											<input type="text" name="addr2" id="addr2"
												value="${dto.addr2}" maxlength="50" class="boxTF"
												style="width: 70%;" placeholder="나머지 주소">
										</p>
									</td>
								</tr>
								<tr>
									<td width="100" valign="top"
										style="text-align: right; padding-top: 5px;"><label
										style="font-weight: 900;">강의코드</label></td>
									<td style="padding: 0 0 15px 15px;">
										<p style="margin-top: 1px; margin-bottom: 5px;">
											<input type="text" name="lecCode" value="${dto.lecCode}"
												maxlength="7" class="boxTF" style="width: 20%;"
												placeholder="강의코드">
										</p>
										<p class="help-block">추후 입력가능</p>
									</td>
								</tr>

								<c:if test="${mode=='created'}">
									<tr>
										<td width="100" valign="top"
											style="text-align: right; padding-top: 5px;"><label
											style="font-weight: 900;"></label></td>
										<td style="padding: 0 0 15px 15px;">
											<p style="margin-top: 7px; margin-bottom: 5px;">
												<label> <input id="agree" name="agree"
													type="checkbox"
													onclick="form.sendButton.disabled = !checked"> <span
													style="font-weight: bold;"><a
														href="javascript:termsOfUse();">이용약관</a></span>에 동의합니다.
												</label>
											</p>
										</td>
									</tr>
								</c:if>
							</table>

							<table
								style="width: 100%; margin: 0px auto; border-spacing: 0px;">
								<tr height="45">
									<td align="center">
										<button type="button" name="sendButton" ${mode=="created"?"disabled='disabled'":""} 
											class="loginButton" onclick="memberOk();">${mode=="created"?"회원가입":"정보수정"}</button>
										<button type="reset" class="loginButton">다시입력</button>
										<button type="button" class="loginButton"
											onclick="javascript:location.href='<%=cp%>/';">${mode=="created"?"가입취소":"수정취소"}</button>
									</td>
								</tr>
								<tr height="30">
									<td align="center" style="color: blue;">${message}</td>
								</tr>
								
							</table>
							
							
						</form>
					
								<form name="deleteForm" method="post">
								<c:if test="${mode == 'update'}">
								<table style="width: 100%">
								<tr>
								<td style="float: right; font-size: 10px;"><a href="javascript:cancel()">회원탈퇴</a></td>
								<td><input type="hidden" name="userId" value="${sessionScope.member.userId}"></td>
								</tr>
								</table>
								</c:if>
					</form>
	
				
					</div>

				</div>
		
	
    </div>
</div>

<div class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script>
    function daumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var fullAddr = ''; // 최종 주소 변수
                var extraAddr = ''; // 조합형 주소 변수

                // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    fullAddr = data.roadAddress;

                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    fullAddr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 조합한다.
                if(data.userSelectedType === 'R'){
                    //법정동명이 있을 경우 추가한다.
                    if(data.bname !== ''){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있을 경우 추가한다.
                    if(data.buildingName !== ''){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
                    fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : '');
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('zip').value = data.zonecode; //5자리 새우편번호 사용
                document.getElementById('addr1').value = fullAddr;

                // 커서를 상세주소 필드로 이동한다.
                document.getElementById('addr2').focus();
            }
        }).open();
    }
</script>    
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery.ui.datepicker-ko.js"></script>
</body>
</html>