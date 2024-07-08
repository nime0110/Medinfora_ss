<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% String ctxPath = request.getContextPath(); %>

<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/hpsearch/hpsearch.css?after" />

<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=a85d4c332f523d2ef9be7ec67b43ff8e&libraries=services,clusterer,drawing"></script>
<script type="text/javascript" src="<%= ctxPath%>/resources/js/hospitalSearch/hospitalSearch.js"></script>


<!-- 중앙 div -->
<div align="center">
    <h1 class="nanum-b size-b">우리동네 근처 병원 찾기</h1>
	<div id="searchBox">
	<div class="dropdown_hpsearch">
		<select id="city">
		  <!-- 시/도 데이터 -->
		</select>
		
		<select id="local">
		  <option value="">시/군/구 선택</option>
		  <!-- 시/군/구 데이터 -->
		</select>
		
		<select id="country">
		  <option value="">읍/면/동 선택</option>
		  <!-- 읍/면/동 데이터 -->
		</select>
	</div>
	<div class="dropdown_hpsearch">
		<select id="classcode">
		  <!-- 진료과목 데이터 -->
	    </select>
		<select id="agency">
			<option value="">병원 유형 선택</option>
			<option value="종합병원">종합병원</option>
			<option value="병원">병원</option>
			<option value="의원">의원</option>
		</select>

	   
	<input type="text" id="searchHpname" placeholder="병원명을 입력하세요">
	<button onclick="searchHospitals()">검색</button>

	</div>
	<!-- 카카오맵 / 리스트설정  -->
	<div id="flexbox_map">
		<div class="map_wrap">
		    <div id="map"></div>
		</div>
		<div id="hplist">
		    <ul id="hospitalList">
		        <!-- Sample List Items -->
		        <li>
		        	<div id="no_searchList">
		        		<span>🪄</span>
		            	<p> 지역을 설정하고 검색버튼을 눌러주세요.</p>
		        	</div>
		        </li>
		        <!-- More items can be added here -->
		    </ul>
		</div>
	</div>
    <div class="pagination" id="rpageNumber">
    </div>
</div>    
    
<div id="hospitalList"></div>

  	
<%-- 로더  --%>
<div id="loaderArr">
	<div class="loader"></div>
</div>

<!-- 모달 구조 start -->
<div class="modal fade" id="hospitalDetailModal" tabindex="-1" role="dialog" aria-labelledby="hospitalDetailModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="hospitalDetailModalLabel">병원 상세정보</h5>
      </div>
      <div class="modal-body">
        <p><strong>Hospital Name:</strong> <span id="modal-hpname"></span></p>
        <p><strong>Telephone:</strong> <span id="modal-hptel"></span></p>
        <p><strong>Address:</strong> <span id="modal-hpaddr"></span></p>
        <p><strong>Class Name:</strong> <span id="modal-classname"></span></p>
        <p><strong>Operating Hours:</strong> <span id="modal-operating-hours"></span></p>
        <p><strong>Status:</strong> <span id="modal-status"></span></p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>
<!-- 모달 구조 end -->

