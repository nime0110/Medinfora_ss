<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<% String ctxPath = request.getContextPath(); %>

<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/status/serviceUtilization.css">

<script src="https://cdnjs.cloudflare.com/ajax/libs/echarts/5.5.0/echarts.min.js"></script>
<script type="text/javascript" src="<%= ctxPath%>/resources/js/status/serviceUtilization.js"></script>

<c:if test="${sessionScope.loginuser != null}">
	<div class="personalChart mb-3">
		<div id="byAge"></div>
		<div id="byGener"></div>
	</div>
</c:if>

<%-- 특정 연도 연령별 입원 비율 --%>
<div id="totalChart"></div>