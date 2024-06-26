<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<% String ctxPath = request.getContextPath(); %>

<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources//css/reserve/choiceDr.css" />
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources//css/reserve/choiceDrMedia.css" />

<script type="text/javascript" src="<%= ctxPath%>/resources/js/reserve/choiceDr.js"></script>

<div class="hj_container">
	<div class="reserveContent">
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
	    <div class="div_proc text-center mb-5">
	        <button type="button" class="btn_proc btn btn-lg mr-5" onclick="javascript:history.back()">취소</button>
	        <button type="button" class="btn_proc btn btn-lg" onclick="javascript:location.href='<%= ctxPath%>/reserve/choiceDay.bibo'">다음</button>
	    </div>
	</div>
</div>