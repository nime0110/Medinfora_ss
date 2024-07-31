<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 	String ctxPath = request.getContextPath(); 
	String key = "ODNiYmJmMWVlZjNkZTMwMzQ1Njk0Y2YxN2Q3NzFhZTM=";
%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>

<%-- kosis 관련 --%>

<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/status/cancerincidence.css">
<script type="text/javascript" src="<%= ctxPath%>/resources/js/status/cancerincidence.js"></script>

<%-- 차트 APEXCHART 사용 --%>
<script src="https://cdn.jsdelivr.net/npm/apexcharts"></script>

<style>
.b_red {
	border: solid 1px red;
}

.b_blue {
	border: solid 1px blue;
}

.b_black {
	border: solid 1px black;
}
</style>

<script type="text/javascript">
</script>


<div class="b_black">

	<div class="py-4" align="center">
		<h2 class="nanum-eb size-n">연도별 암발생현황(~2021)</h2>
	</div>

	<%-- 검색 구역 --%>
	<div>
		<input type="hidden" value="<%=key%>" name="key"/>
		<div class="p-4 searchBar">
			<span>
				<select class="search_ch sel_year nanum-b" name="yeardata">
					<option value='2021'>연도</option>
					<c:forEach var="year" items="${requestScope.yearList}">
						<option value='${year}'>${year}년</option>
					</c:forEach>
				</select>
			</span>
			<span>
				<select class="search_ch sel_cancer nanum-b" name="cancerdata"></select>
			</span>
			
			<span>
				<select class="search_ch sel_age nanum-b" name="agedata"></select>
			</span>

			<span>
				<button class="jh_btn_design search nanum-eb size-s" type="button" onclick="getData()">검색</button>
			</span>
		      
		</div>
	</div>
	
	<%-- 차트구역 --%>
	<div class="my-5 b_blue" id="chart">차트</div>

	<%-- 데이터구역 --%>
	<div class="my-5 b_red">데이터</div>

</div>