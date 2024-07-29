<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<% String ctxPath = request.getContextPath(); %>


<script src="https://cdnjs.cloudflare.com/ajax/libs/echarts/5.5.0/echarts.min.js"></script>

<!-- 시도별 일반 입원실 시설 현황  -->    
<script type="text/javascript" src="<%= ctxPath%>/resources/js/status/facilities.js"></script>


<c:if test="${sessionScope.loginuser != null}">
			<div class="loginuserchart mb-3">
			<span class="nanum-b name">&nbsp;&nbsp;${sessionScope.loginuser.name}&nbsp;님 지역의 주요 시설 현황
			</span>
			<div class="nanum-n my-2">
				
		</div>	

	</div>
</c:if>
<hr>
<div style="text-align:center;">
<span class="nanum-b size-n name mt-5">
	시도별 일반 입원실 시설 현황
</span>

</div>
<div id="main" style="width: 100%; height: 600px;"></div>
