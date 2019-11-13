<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
   String cp = request.getContextPath();
%>
<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
<script type="text/javascript">
function termsOfUse(){
	 window.open("<%=cp%>/member/termsOfUse.do", "a", "width=1000, height=770, left=450, top=50"); 

}
function personalInfo(){
	 window.open("<%=cp%>/member/personalInfo.do", "a", "width=1000, height=770, left=450, top=50"); 

}

</script>
<a href="javascript:termsOfUse();">승훈교육정보센터 정책 및 약관</a>&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;
<a href="<%=cp%>/info/infoWeb.do">사이트 소개</a>&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;
<a href="javascript:personalInfo();">개인정보처리방침</a>&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;
<a href="https://forms.gle/ophthu68gAC6pB4e9" target="_blank">제휴 제안</a>&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;

<span style="font-weight: 900;">© 승훈교육정보센터 Corp.</span>
