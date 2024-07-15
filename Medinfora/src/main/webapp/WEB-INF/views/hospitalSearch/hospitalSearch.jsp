<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% String ctxPath = request.getContextPath(); %>

<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/hpsearch/hpsearch.css" />

<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=a85d4c332f523d2ef9be7ec67b43ff8e&libraries=services,clusterer,drawing"></script>
<script type="text/javascript" src="<%= ctxPath%>/resources/js/hospitalSearch/hospitalSearch.js"></script>


<!-- 중앙 div -->
<div id="container">
<h1 class="nanum-b size-n">우리동네 근처 병원 찾기</h1>
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
		<br>
		<select id="classcode">
		  <!-- 진료과목 데이터 -->
	    </select>
		<select id="agency">
			<option value="">병원 유형 선택</option>
			<option value="종합병원">종합병원</option>
			<option value="병원">병원</option>
			<option value="의원">의원</option>
		</select>  
		<br> 
	    <div class="form-check">
	        <input class="form-check-input" type="checkbox" id="check-status" value="진료중">
	        <label class="form-check-label" for="check-status">진료중인 병원만 보기</label>
	    </div>
		<input type="text" id="searchHpname" placeholder="병원명을 입력하세요">
		<button onclick="searchHospitals(1)">검색</button>
	</div>
	
	<div id="tabs" class="mobile-only">
	    <button class="tab-button active" data-tab="map_box">지도</button>
	    <button class="tab-button" data-tab="hplist">리스트</button>
	</div>
	<!-- 카카오맵 / 리스트설정  -->
	
	<div id="flexbox_map">
		<div id="map_box" class="map_wrap tab-content">
		    <div id="map"></div>
		</div>
		<div id="hplist" class="tab-content">
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
        <h5 class="modal-title " id="hospitalDetailModalLabel">병·의원 상세정보</h5>
      </div>
      <div class="modal-body">
        <span id="modal-hpname" class="nanum-b size-n"></span> <!-- ㅇㅇㅇ의원 -->
      	
      	<div class="row-table" data-title="병의원 기본정보">
	      	<table>
	      		<!-- 병의원의 주소, 전화번호, 진료과목, 병원종류(종합병원/병원/의원)를 띄워준다 -->
	      		<tbody>
	      			<tr>
	      				<th scope="row">주소</th>
	      				<td id="modal-hpaddr"></td>
	      			</tr>
	      			<tr>
	      				<th scope="row">전화번호</th>
	      				<td id="modal-hptel"></td>
	      			</tr>
	      			<tr>
	      				<th scope="row">진료과목</th>
	      				<td id="modal-classname"></td>
	      			</tr>
	      			<tr>
	      				<th scope="row">종별</th>
	      				<td id="modal-agency"></td>
	      			</tr>
	      		</tbody>
	      	</table>
      	</div>
      	
      	<div class="row-table" data-title="진료시간">
      		<span class="nanum-b size-n modal-mini-title">진료 시간</span> 
	      	<table>
	      		<tbody>
	      			<tr>
	      				<th scope="col">월요일</th>
	      				<td scope="col" id="modal-daytime1"></td>
	      				<th scope="col">화요일</th>
	      				<td scope="col" id="modal-daytime2"></td>
	      			</tr>
	      			<tr>
	      				<th scope="col">수요일</th>
	      				<td scope="col" id="modal-daytime3"></td>
	      				<th scope="col">목요일</th>
	      				<td scope="col" id="modal-daytime4"></td>
	      			</tr>
	      			<tr>
	      				<th scope="col">금요일</th>
	      				<td scope="col" id="modal-daytime5"></td>
	      				<th scope="col">토요일</th>
	      				<td scope="col" id="modal-daytime6"></td>
	      			</tr>
	      			<tr>
	      				<th scope="col">일요일</th>
	      				<td scope="col" id="modal-daytime7"></td>
	      				<th scope="col">공휴일</th>
	      				<td scope="col" id="modal-daytime8"></td>
	      			</tr>
	      		</tbody>
	      	</table>
      	</div>
      	
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" id="closeModalButton" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>
</div> <!-- 이부분 추가함 -->
<!-- 모달 구조 end -->

