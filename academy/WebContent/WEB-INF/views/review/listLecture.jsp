<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<select name="lecCode">
  <c:forEach var="vo" items="${listLecture}">
     <option value="${vo.lecCode}">${vo.lecName}</option>
  </c:forEach>
</select>