<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% String ctxPath = request.getContextPath(); %>

<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/hpsearch/locHpsearch.css" />

<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=a85d4c332f523d2ef9be7ec67b43ff8e&libraries=services,clusterer,drawing"></script>
<script type="text/javascript" src="https://fastly.jsdelivr.net/npm/echarts@5.5.1/dist/echarts.min.js"></script>
<script type="text/javascript" src="<%= ctxPath%>/resources/js/hospitalSearch/locHospitalSearch.js"></script>


<!-- 중앙 div -->
<div id="container">
<h1 class="nanum-b size-n">우리동네 병원 찾기</h1>
	
    <div id="map"></div>
    
    <div id="tabs" class="mobile-only">
	    <button class="tab-button active" data-tab="map_box">통계</button>
	    <button class="tab-button" data-tab="hplist">리스트</button>
	</div>
	<div id="flexbox_map">
		<div id="map_box" class="map_wrap tab-content">
		    <div id="hp_chart"  style="height: 100%">통계들어감</div>
		</div>
		<div id="hplist" class="tab-content">
		    <ul id="hospitalList">
		        <li>
		        	<div id="no_searchList">
		        		<span>🪄</span>
		            	<p> 지역을 설정하고 검색버튼을 눌러주세요.</p>
		        	</div>
		        </li>
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

