<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% String ctxPath = request.getContextPath(); %>

<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/reserve/choiceDay.css">
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/reserve/choiceDayMedia.css">

<script type="text/javascript" src="<%= ctxPath%>/resources/js/reserve/choiceDay.js"></script>

<div class="hj_container">
	<div class="reserveContent pt-3">
	    <div class="reserveTitlediv mt-5 pb-3">
	        <span class="reserve_title nanum-b size-b">온라인 진료예약</span>
	    </div>
	    <div class="mediseq">
	        <ul class="reserve_seq my-5">
	            <li class="reserve_li choicedr">
	            	<span class="reserve_licontent nanum-n">병원 선택</span>
	            </li>
	            <li class="reserve_li choiceday">
	            	<span class="reserve_licontent nanum-b">진료일시 선택</span>
	            </li>
	        </ul>
	    </div>
	    <%-- 달력에서 선택한 데이터를 어떻게 보내줄지 생각해야함(캘린더?) --%>
        <div class="div_choiceDay row mt-5">
            <div class="reserve_day col-md-6">
               	 달력들어올 예정
            </div>
            <div class="choiceTimediv col-md-6 pt-3 pl-5">
                <h3 class="nanum-b size-n">${requestScope.today_str}</h3>
                <div class="choiceTime row mt-3">
                    <button type="button" class="timebtn mb-3 btn btn-lg col-3">
                        <span class="exTimebtn">09:00</span>
                    </button>
                    <button type="button" class="timebtn mb-3 btn btn-lg col-3">
                        <span class="exTimebtn">09:30</span>
                    </button>
                    <button type="button" class="timebtn mb-3 btn btn-lg col-3">
                        <span class="exTimebtn">10:00</span>
                    </button>
                    <button type="button" class="timebtn mb-3 btn btn-lg col-3">
                        <span class="exTimebtn">10:30</span>
                    </button>
                    <button type="button" class="timebtn mb-3 btn btn-lg col-3">
                        <span class="exTimebtn">11:00</span>
                    </button>
                    <button type="button" class="timebtn mb-3 btn btn-lg col-3">
                        <span class="exTimebtn">11:30</span>
                    </button>
                    <button type="button" class="timebtn mb-3 btn btn-lg col-3">
                        <span class="exTimebtn">12:00</span>
                    </button>
                    <button type="button" class="timebtn mb-3 btn btn-lg col-3">
                        <span class="exTimebtn">12:30</span>
                    </button>
                </div>
            </div>
        </div>
        <div class="div_proc text-center mb-5">
	        <button type="button" class="btn_proc btn btn-lg mr-5" onclick="javascript:history.back()">뒤로</button>
	        <button type="button" class="reservationbtn btn_proc btn btn-lg">예약</button>
	    </div>
    </div>
</div>