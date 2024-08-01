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

.table-container {
    display: flex; 
    justify-content: center; 
}
table {
    border-collapse: collapse;
}
th, td {
    border: 1px solid black;
    padding: 10px;
    text-align: center;
}

div#datadown{
	text-align: right;
	width: 800px;
	margin-bottom: -40px;
}

.qwe{
	display: flex;
	justify-content: center !important; 
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
	<div class="my-5" id="chart"></div>

	<%-- 데이터구역 --%>
	<div class="nanum-eb text-center size-n" style="margin-bottom: -30px;">[&nbsp;데이터&nbsp;]</div>
	<div class="qwe">
		<div id="datadown" >
			<button class="btn btn-dark" type="button" onclick="downloadExcel()">엑셀파일</button>
		</div>
	</div>
	<div class="my-5 table-container" id="dataArea"></div>
	
	<%-- 데이터 다운용 데이터 --%>
	<form name="excelData">
		<input type="hidden" name="man" />
		<input type="hidden" name="man_i" />
		<input type="hidden" name="woman" />
		<input type="hidden" name="woman_i" />
		<input type="hidden" name="age" />
		<input type="hidden" name="year" />
		<input type="hidden" name="cancer" />
	</form>
</div>