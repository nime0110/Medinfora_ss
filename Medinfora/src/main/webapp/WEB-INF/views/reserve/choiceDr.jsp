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
	        <ul class="reserve_seq mt-5">
	            <li class="reserve_li choicedr">
	                <span class="reserve_licontent nanum-n size-n">병원 선택</span>
	            </li>
	            <li class="reserve_li">
	                <span class="reserve_licontent nanum-n size-n">진료일시 선택</span>
	            </li>
	        </ul>
	    </div>
	    <div class="search px-5 my-2">
	        <div class="py-3">
	            <span class="nanum-b tagloc">병원위치</span>
	            <select name="city" id="city" class="selectbox">
	                <option>시/도 선택</option>
	                <option value="seoul">서울특별시</option>
	                <option value="busan">부산광역시</option>
	            </select>
	            <select name="loc" id="lod" class="selectbox">
	                <option>시/군구 선택</option>
	                <option value="seoul">서울</option>
	                <option value="gungi">경기</option>
	            </select>
	        </div>
	        <div>
	            <span class="nanum-b">진료과목</span>
	            <select name="dept" id="dept" class="selectbox">
	                <option>진료과목 선택</option>
	                <option value="internal">내과</option>
	                <option value="surgery">외과</option>
	            </select>
	        </div>
	        <div class="searchHospital py-3 row justify-content-center">
	            <div class="col-xl-6 row justify-content-around">
	                <input text="type" class="col-md-8" placeholder="병원명을 입력하세요."/>
	                <button type="button" class="searchbtn colsearchbtn col-md-3 btn btn-lg">검색</button>
	            </div>
	        </div>
	        <div class="row justify-content-center">
	            <button type="button" class="col-4 searchbtn smsearchbtn btn btn-sm mb-2">검색</button>
	        </div>
	    </div>
	    <div class="choiceDr">
	        <span class="nanum-b size-n">병원선택</span>
	        <hr style="border: solid 3px black">
	    </div>
	    <div class="exam_choiceDr">
	        <div class="card-inner row">
	            <button class="card col-6 col-sm-3 btn_card" type="button">
	                <h4 class="hospital_name nanum-n">1등병원</h4>
	                <input type="checkbox" class="hj_custom-checkbox">
	                <label class="hj_custom-checkbox-label"></label>
	                <p class="hospital_addr">
	                    	경기도 부천시 원미구 부천로 91, ,부흥로373번길18, 부흥로377(기존디에스병원A동) (심곡동)
	                </p>
	            </button>
	            <button class="card col-6 col-sm-3 btn_card" type="button">
	                <h4 class="hospital_name nanum-n">2등병원</h4>
	                <input type="checkbox" class="hj_custom-checkbox">
	                <label class="hj_custom-checkbox-label"></label>
	                <p class="hospital_addr">서울특별시 서울~~</p>
	            </button>
	            <button class="card col-6 col-sm-3 btn_card" type="button">
	                <h4 class="hospital_name nanum-n">3등병원</h4>
	                <input type="checkbox" class="hj_custom-checkbox">
	                <label class="hj_custom-checkbox-label"></label>
	                <p class="hospital_addr">서울특별시 서울~~</p>
	            </button>
	            <button class="card col-6 col-sm-3 btn_card" type="button">
	                <h4 class="hospital_name nanum-n">4등병원</h4>
	                <input type="checkbox" class="hj_custom-checkbox">
	                <label class="hj_custom-checkbox-label"></label>
	                <p class="hospital_addr">서울특별시 서울~~</p>
	            </button>
	            <button class="card col-6 col-sm-3 btn_card" type="button">
	                <h4 class="hospital_name nanum-n">5등병원</h4>
	                <input type="checkbox" class="hj_custom-checkbox">
	                <label class="hj_custom-checkbox-label"></label>
	                <p class="hospital_addr">서울특별시 서울~~</p>
	            </button>
	            <button class="card col-6 col-sm-3 btn_card" type="button">
	                <h4 class="hospital_name nanum-n">6등병원</h4>
	                <input type="checkbox" class="hj_custom-checkbox">
	                <label class="hj_custom-checkbox-label"></label>
	                <p class="hospital_addr">서울특별시 서울~~</p>
	            </button>
	            <button class="card col-6 col-sm-3 btn_card" type="button">
	                <h4 class="hospital_name nanum-n">7등병원</h4>
	                <input type="checkbox" class="hj_custom-checkbox">
	                <label class="hj_custom-checkbox-label"></label>
	                <p class="hospital_addr">서울특별시 서울~~</p>
	            </button>
	            <button class="card col-6 col-sm-3 btn_card" type="button">
	                <h4 class="hospital_name nanum-n">8등병원</h4>
	                <input type="checkbox" class="hj_custom-checkbox">
	                <label class="hj_custom-checkbox-label"></label>
	                <p class="hospital_addr">서울특별시 서울~~</p>
	            </button>
	            <button class="card col-6 col-sm-3 btn_card" type="button">
	                <h4 class="hospital_name nanum-n">1등병원</h4>
	                <input type="checkbox" class="hj_custom-checkbox">
	                <label class="hj_custom-checkbox-label"></label>
	                <p class="hospital_addr">서울특별시 서울~~</p>
	            </button>
	            <button class="card col-6 col-sm-3 btn_card" type="button">
	                <h4 class="hospital_name nanum-n">2등병원</h4>
	                <input type="checkbox" class="hj_custom-checkbox">
	                <label class="hj_custom-checkbox-label"></label>
	                <p class="hospital_addr">서울특별시 서울~~</p>
	            </button>
	            <button class="card col-6 col-sm-3 btn_card" type="button">
	                <h4 class="hospital_name nanum-n">3등병원</h4>
	                <input type="checkbox" class="hj_custom-checkbox">
	                <label class="hj_custom-checkbox-label"></label>
	                <p class="hospital_addr">서울특별시 서울~~</p>
	            </button>
	            <button class="card col-6 col-sm-3 btn_card" type="button">
	                <h4 class="hospital_name nanum-n">4등병원</h4>
	                <input type="checkbox" class="hj_custom-checkbox">
	                <label class="hj_custom-checkbox-label"></label>
	                <p class="hospital_addr">서울특별시 서울~~</p>
	            </button>
	            <button class="card col-6 col-sm-3 btn_card" type="button">
	                <h4 class="hospital_name nanum-n">5등병원</h4>
	                <input type="checkbox" class="hj_custom-checkbox">
	                <label class="hj_custom-checkbox-label"></label>
	                <p class="hospital_addr">서울특별시 서울~~</p>
	            </button>
	            <button class="card col-6 col-sm-3 btn_card" type="button">
	                <h4 class="hospital_name nanum-n">6등병원</h4>
	                <input type="checkbox" class="hj_custom-checkbox">
	                <label class="hj_custom-checkbox-label"></label>
	                <p class="hospital_addr">서울특별시 서울~~</p>
	            </button>
	            <button class="card col-6 col-sm-3 btn_card" type="button">
	                <h4 class="hospital_name nanum-n">7등병원</h4>
	                <input type="checkbox" class="hj_custom-checkbox">
	                <label class="hj_custom-checkbox-label"></label>
	                <p class="hospital_addr">서울특별시 서울~~</p>
	            </button>
	            <button class="card col-6 col-sm-3 btn_card" type="button">
	                <h4 class="hospital_name nanum-n">8등병원</h4>
	                <input type="checkbox" class="hj_custom-checkbox">
	                <label class="hj_custom-checkbox-label"></label>
	                <p class="hospital_addr">대략 16개 보여줄 예정</p>
	            </button>
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