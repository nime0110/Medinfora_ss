<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% String ctxPath = request.getContextPath(); %>

<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/hpsearch/hpsearch.css" />

<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=a85d4c332f523d2ef9be7ec67b43ff8e&libraries=services"></script>
<script type="text/javascript" src="<%= ctxPath%>/resources/js/hospitalSearch/hospitalSearch.js"></script>


<!-- 중앙 div -->
<div align="center">
    <div style="font-size: 20pt; margin-bottom: 20px;">우리동네 근처 병원 찾기</div>
	<div id="searchBox">
	<div class="dropdown_hpsearch">
		<select id="si-do">
		  <option value="">시/도 선택</option>
		  <option value="서울특별시">서울특별시</option>
		  <option value="경기도">경기도</option>
		</select>
		
		<select id="si-gun-gu">
		  <option value="">시/군/구 선택</option>
		</select>
		
		<select id="dong">
		  <option value="">읍/면/동 선택</option>
		</select>
	</div>
	<div class="dropdown_hpsearch">
		<select id="classcode">
			<option value="">진료과목 선택</option>
			<option value="D001">내과</option>
			<option value="D002">소아청소년과</option>
			<option value="D004">정신건강의학과</option>
			<option value="D005">피부과</option>
			<option value="D006">외과</option>
			<option value="D008">정형외과</option>
			<option value="D011">산부인과</option>
			<option value="D012">안과</option>
			<option value="D013">이비인후과</option>
			<option value="D022">가정의학과</option>
	    </select>
		<select id="agency">
			<option value="">병원 유형 선택</option>
			<option value="종합병원">종합병원</option>
			<option value="병원">병원</option>
			<option value="의원">의원</option>
		</select>

	   
	<input type="text" id="manualLocation" placeholder="병원명을 입력하세요">
	<button onclick="searchManualLocation()">검색</button>

	</div>
	<!-- 카카오맵 설정  -->
    <div id="map" style="width:95%; height:600px;"></div>
</div>    
    
<div id="hospitalList"></div>

