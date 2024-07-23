<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<% String ctxPath = request.getContextPath(); %>

<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/status/serviceUtilization.css">

<script src="https://cdnjs.cloudflare.com/ajax/libs/echarts/5.5.0/echarts.min.js"></script>
<script type="text/javascript" src="<%= ctxPath%>/resources/js/status/serviceUtilization.js"></script>

<div class="personalChart mb-3">
	<div class="byAge">
	</div>
	<div class="byGener">
	</div>
</div>
<%-- 특정 연도 연령별 입원 비율 --%>
<div id="totalChart"></div>