<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<% String ctxPath = request.getContextPath(); %>

<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources//css/reserve/choiceDr.css" />
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/reserve/choiceDrMedia.css" />

<script type="text/javascript" src="<%= ctxPath%>/resources/js/reserve/choiceDr.js"></script>

<div class="hj_container">
	<div class="reserveContent pt-3">
	    <div class="reserveTitlediv mt-5 pb-3">
	        <span class="reserve_title nanum-b size-b">온라인 진료예약</span>
	    </div>
	    <div class="mediseq">
	        <ul class="reserve_seq my-5">
	            <li class="reserve_li choicedr">
	            	<span class="reserve_licontent nanum-b">병원 선택</span>
	            </li>
	            <li class="reserve_li choiceday">
	            	<span class="reserve_licontent nanum-n">진료일시 선택</span>
	            </li>
	        </ul>
	    </div>
	    
    	<form name="#" method="get">
	    	<div class="searchhp">
	    
	    		<div class="location_select">
	    		
		    		<span class="searchoicename">병원위치</span>
		            
		            <select name="city" id="city" class="selectbox loc_sel">
		                <option>시/도 선택</option>
		                <option value="seoul">서울특별시</option>
		                <option value="busan">부산광역시</option>
		            </select>
		            
		            <select name="loc" id="lod" class="selectbox loc_sel">
		                <option>시/군구 선택</option>
		                <option value="seoul">서울</option>
		                <option value="gungi">경기</option>
		            </select>
		            
	    		</div>
	            
	            <div class="class_select">
	            
	            	<span class="searchoicename">진료과목</span>
		            <select name="dept" id="dept" class="selectbox inlinesearch">
		                <option>진료과목 선택</option>
		                <option value="internal">내과</option>
		                <option value="surgery">외과</option>
		            </select>
	            
	            </div>
	            
	           	<div class="name_input">
	           		
	           		<span class="searchoicename">병원명</span>
	               	<input text="type" class="inputbox" name="" placeholder="병원명을 입력하세요."/>
	               	<button type="button" class="searchhpbtn">검색</button>
	           	</div>
           	
        	</div>
        
     	</form>
	    
	    <div class="exam_choiceDr">
            <div class="btn_card">
            	<div class="card_top">
            		<h4 class="hospital_name">1등병원</h4>
                	<input type="checkbox" class="hj_custom-checkbox">
                	<label class="hj_custom-checkbox-label"></label>
            	</div>
                <p class="hospital_addr">
                    	경기도 부천시 원미구 부천로 91, ,부흥로373번길18, 부흥로377(기존디에스병원A동) (심곡동)
                </p>
            </div>
            
            <div class="btn_card">
            	<div class="card_top">
            		<h4 class="hospital_name">2등병원</h4>
                	<input type="checkbox" class="hj_custom-checkbox">
                	<label class="hj_custom-checkbox-label"></label>
            	</div>
                <p class="hospital_addr">
                    	경기도 부천시 원미구 부천로 91, ,부흥로373번길18, 부흥로377(기존디에스병원A동) (심곡동)
                </p>
            </div>
            
            <div class="btn_card">
            	<div class="card_top">
            		<h4 class="hospital_name">3등병원</h4>
                	<input type="checkbox" class="hj_custom-checkbox">
                	<label class="hj_custom-checkbox-label"></label>
            	</div>
                <p class="hospital_addr">
                    	경기도 부천시 원미구 부천로 91, ,부흥로373번길18, 부흥로377(기존디에스병원A동) (심곡동)
                </p>
            </div>
            
            <div class="btn_card">
            	<div class="card_top">
            		<h4 class="hospital_name">4등병원</h4>
                	<input type="checkbox" class="hj_custom-checkbox">
                	<label class="hj_custom-checkbox-label"></label>
            	</div>
                <p class="hospital_addr">
                    	경기도 부천시 원미구 부천로 91, ,부흥로373번길18, 부흥로377(기존디에스병원A동) (심곡동)
                </p>
            </div>
	    </div>
	    <%-- ================================================================================================== --%>
	    <%-- == 페이징바 === --%>
	    <div class="w-100 d-flex justify-content-center pt-3">
	        <ul class="pagination reserve_pagebar nanum-n size-s">
	            <li class="page-item">
	                <a class="page-link" href="#" aria-label="Previous">
	                    <span aria-hidden="true">&laquo;</span>
	                </a>
	            </li>
	            <li class="page-item">
	                <a class="page-link nowPage" href="#">1</a>
	            </li>
	            <li class="page-item">
	                <a class="page-link" href="#">2</a>
	            </li>
	            <li class="page-item">
	                <a class="page-link" href="#">3</a>
	            </li>
	            <li class="page-item">
	                <a class="page-link" href="#" aria-label="Next">
	                    <span aria-hidden="true">&raquo;</span>
	                </a>
	            </li>
	        </ul>
	    </div>
	    <%-- ================================================================================================== --%>	    
	    <div class="div_proc text-center mb-5">
	        <button type="button" class="btn_proc btn btn-lg mr-5" onclick="javascript:history.back()">취소</button>
	        <button type="button" class="btn_proc btn btn-lg" onclick="javascript:location.href='<%= ctxPath%>/reserve/choiceDay.bibo'">다음</button>
	    </div>
	</div>
</div>