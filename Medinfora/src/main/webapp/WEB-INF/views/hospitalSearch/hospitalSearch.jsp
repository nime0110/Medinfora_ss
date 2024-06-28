<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% String ctxPath = request.getContextPath(); %>

<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/statistics/medistatistic.css" />

<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=a85d4c332f523d2ef9be7ec67b43ff8e&libraries=services"></script>
<script type="text/javascript" src="<%= ctxPath%>/resources/js/statistics/homeSearch.js"></script>

<style type="text/css">
	div#title {
		font-size: 20pt;
		padding: 12px 0;
	}
	
	div.mycontent {
  		width: 350px;
  		padding: 5px 3px;
  	}
  	
  	div.mycontent>.title {
  		font-size: 12pt;
  		font-weight: bold;
  		background-color: #d95050;
  		color: #fff;
  	}
  	
  	a {
  		text-decoration: none !important; 
  	}
</style>

<div align="center">
    <div style="font-size: 20pt; margin-bottom: 20px;">우리동네 근처 병원 찾기</div>
    <div id="map" style="width:95%; height:600px;"></div>
</div> 

    <div id="map"></div>
    <div id="searchBox">
        <input type="text" id="manualLocation" placeholder="위치를 입력하세요 (예: 서울시 강남구)">
        <button onclick="searchManualLocation()">검색</button>
    </div>
