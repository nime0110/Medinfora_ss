<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<% String ctxPath = request.getContextPath(); %>

<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/mypage/mdreserve.css">
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/mypage/reserveMedia.css">
<script type="text/javascript" src="<%= ctxPath%>/resources/js/mypage/mdreserve.js"></script>

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
					<button class="jh_btn_design search nanum-eb size-s" type="button" onclick="RESearch()">검색</button>
				</span>
			</div>
		</fieldset>
	</form>
	<%-- 진료예약 리스트 --%>
	<div class="reserveBox"></div>
	<%-- 페이징바 --%>
	<div id="ReserveHP_PageBar" class="w-100 d-flex justify-content-center pt-3"></div>
</div>

<%-- 모달 --%>
<div class="modal fade" id="ChangeRcodeModal" tabindex="-1" role="dialog" aria-labelledby="ChangeRcodeModalLabel" aria-hidden="true">
	<div class="modal-dialog" role="document">
    	<div class="modal-content">
      		<div class="modal-header">
        		<h5 class="modal-title" id="ChangeRcodeModalLabel">접수 정보</h5>
      			<button type="button" class="btn" id="closeModalButton" data-dismiss="modal">
      				<i class="fa-solid fa-xmark"></i>
      			</button>
      		</div>
      		<div class="modal-body">
      			<span id="modal-name" class="nanum-b size-n"></span>
  				<div class="row-table" data-title="진료예약 정보">
	   				<table>
	   					<%-- 접수번호, 성명, 연락처, 현재 진료현황, 예약일시, 진료일시를 띄워준다. --%>
	   					<tbody>
	    					<tr>
	    						<th scope="row">접수번호</th>
	    						<td id="modal-ridx"></td>
	    					</tr>
	    					<tr>
			      				<th scope="row">연락처</th>
			      				<td id="modal-mobile"></td>
			      			</tr>
			      			<tr>
			      				<th scope="row">진료 현황</th>
			      				<td id="modal-rStatus"></td>
			      			</tr>
			      			<tr>
			      				<th scope="row">예약 일시</th>
			      				<td id="modal-reportday"></td>
			      			</tr>
			      			<tr>
			      				<th scope="row">진료 일시</th>
			      				<td id="modal-checkin"></td>
			      			</tr>
	   					</tbody>
	   				</table>
  				</div>
  				<div class="row-table" data-title="진료현황 변경">
  					<span class="nanum-b size-n modal-mini-title">진료현황 변경</span> 
  					<div>
				      	<select class="changers search_ch nanum-b">
							<option>접수신청</option>
							<option>접수완료</option>
							<option>진료완료</option>
						</select>	
					</div>
  				</div>
  				<div class="row-table" data-title="진료현황 변경 버튼">
  					<button type="button" class="btn btnStyle" data-dismiss="modal">변경</button>
  				</div>
   			</div>
   		</div>
  	</div>
</div>
<%-- 모달 끝 --%>
<form name="ChangeRstatus">
	<input type="hidden" name="rStatus" value="" />
	<input type="hidden" name="ridx" value="" />
</form>
