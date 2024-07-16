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
						<option>병원명</option>
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
	<div class="reserveBox"></div>
	<%-- 페이징바 --%>
	<div id="ReserveHP_PageBar" class="w-100 d-flex justify-content-center pt-3"></div>
</div>