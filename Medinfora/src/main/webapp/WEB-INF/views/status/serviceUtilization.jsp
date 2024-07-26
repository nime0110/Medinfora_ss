<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<% String ctxPath = request.getContextPath(); %>

<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/status/serviceUtilization.css">
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/status/serviceUtilizationMedia.css">

<script src="https://cdnjs.cloudflare.com/ajax/libs/echarts/5.5.0/echarts.min.js"></script>
<script type="text/javascript" src="<%= ctxPath%>/resources/js/status/serviceUtilization.js"></script>


<c:if test="${sessionScope.loginuser != null}">
	<div class="personalChart mb-3">
		<span class="nanum-b name">
			&nbsp;&nbsp;${sessionScope.loginuser.name}&nbsp;님
		</span>
		<div class="nanum-n my-2">
			&nbsp;
			<i class="fa-solid fa-arrow-right"></i>
			&nbsp;동일 연령대 및 성별에 따른 의료서비스율(2017년, 2018년, 2019년)
		</div>
		<div id="byAge">
			<div class="text-center nanum-b size-n">
				연령&nbsp;
				<span class="age"></span>
			</div>
			<div class="row justify-content-center">
				<div id="first" class="year col-12 col-md-4"></div>
				<div id="second" class="year col-12 col-md-4"></div>
				<div id="third" class="year col-12 col-md-4"></div>
			</div>
		</div>
		<hr class="smSizehr">
		<div id="byGener">
			<div class="text-center nanum-b size-n">
				성별&nbsp;
				<span class="gender"></span>
			</div>
			<div class="row justify-content-center">
				<div id="gender_fi" class="year col-12 col-md-4"></div>
				<div id="gender_se" class="year col-12 col-md-4"></div>
				<div id="gender_th" class="year col-12 col-md-4"></div>
			</div>
		</div>
	</div>
<hr>
</c:if>


<span class="nanum-b size-n name mt-5">
	특정 연도의 연령별 입원 비율 (%)
</span>
<%-- 특정 연도 연령별 입원 비율 --%>
<div id="totalChart" class="pt-5 text-center"></div>