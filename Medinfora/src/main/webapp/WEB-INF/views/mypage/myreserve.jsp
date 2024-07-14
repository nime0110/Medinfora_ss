<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% String ctxPath = request.getContextPath(); %>

<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/mypage/myreserve.css">
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/mypage/reserveMedia.css">
<script type="text/javascript" src="<%= ctxPath%>/resources/js/mypage/myreserve.js"></script>

<div class="hj_container">
	<div class="py-4" align="center">
		<h2 class="nanum-eb size-n">진료 예약 현황</h2>
	</div>
	<%-- 검색  --%>
	<form>
		<fieldset>
			<div class="p-4 searchBar" align="center">
				<span>
					<select class="sclist search_ch sel_0 nanum-b">
						<option>전체</option>
						<option>환자명</option>
						<option>진료현황</option>
					</select>
				</span>
				<span>
					<input class="inputsc search_ch sel_1 nanum-b" name="search" type="text" placeholder="검색어를 입력해주세요." />
					<input type="text" style="display: none;"/>		<%-- 전송방지 --%>
				</span>
				<span>
					<button class="jh_btn_design search nanum-eb size-s" type="button">검색</button>
				</span>
			</div>
		</fieldset>
	</form>
	<%-- 진료예약 리스트 --%>
	<div class="reserveBox">
		<div class="mt-4 px-3 subject">
			<div class="row text-center py-3 nanum-eb size-s">
				<span class="col-3 col-lg-2">진료예약일시</span>
				<span class="col-3 col-lg-2">병원명</span>
				<span class="col-3 col-lg-2">전화번호</span>
				<span class="col-3 col-lg-2">예약신청일</span>
				<span class="col-3 col-lg-2">진료현황</span>
				<span class="col-3 col-lg-2">접수취소</span>
			</div>
		</div>
		<%-- 진료예약 리스트 값 --%>
		<div class="mb-5 px-3">
			<div class="row text-center py-3 nanum-n size-s b_border">
				<span class="col-3 col-lg-2">2024-07-11 14:00</span>
				<span class="col-3 col-lg-2">행복한재활의학과의원</span>
				<span class="col-3 col-lg-2">02-1234-5678</span>
				<span class="col-3 col-lg-2">2024-07-07 17:00</span>
				<span class="col-3 col-lg-2">
					<span class="rscode p-1 nanum-b">접수완료</span>
				</span>
				<span class="col-2">
					<button class="rscancle nanum-eb size-s" type="button">취소</button>
				</span>
			</div>
		</div>
	</div>
	<%-- 페이징바 --%>
	<div id="ReserveHP_PageBar" class="w-100 d-flex justify-content-center pt-3"></div>
</div>