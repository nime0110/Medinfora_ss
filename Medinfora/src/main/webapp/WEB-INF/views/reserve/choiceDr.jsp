<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<% String ctxPath = request.getContextPath(); %>

<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/reserve/choiceDr.css" />
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/reserve/choiceDrMedia.css" />

<script type="text/javascript" src="<%= ctxPath%>/resources/js/reserve/choiceDr.js"></script>

<script type="text/javascript">
$(document).ready(function(){
	
	searchHP();		<%-- 결과창 변경 --%>
	
	<%-- 시/도 데이터 가져오기 --%>
	$.ajax({
		url:"<%= ctxPath%>/getareainfo.bibo",
		async:false,
		dataType:"json",
		success:function(json){
			let v_html = `<option>시/도 선택</option>`;
			for(let i=0; i<json.length; i++){
				v_html +=`<option value="\${json[i]}">\${json[i]}</option>`;
			}	// end of for---------
			$("select#city").html(v_html);
		},
		error:function(request){
			alert("code : " + request.status);
		}
	})	// end of $.ajax({-------------
	
	<%-- 시/도 가 선택되거나 바뀐 경우 --%>
	$("select#city").on("change", function(e) {
		<%-- console.log($(this).val());	// 선택한 값 확인하기  --%>
		const area_val = $(this).val();
		
		<%-- 시/군구 데이터 가져오기 --%>
		const area ={"area":area_val};
		
		$.ajax({
			url:"<%= ctxPath%>/getlocalinfo.bibo",
			async:false,
			data:area,
			dataType:"json",
			success:function(json){
				let v_html = `<option>시/군구 선택</option>`;
				for(let i=0; i<json.length; i++){
					if(json[i]!=null){	<%-- 세종과 다른 시도 구분 --%>
						v_html +=`<option value="\${json[i]}">\${json[i]}</option>`;
					}
				}	// end of for---------
				$("select#lod").html(v_html);
			},
			error:function(request){
				alert("code : " + request.status);
			}
		})	// end of $.ajax({-------------
	})	// end of $("select#city").on("change", function(e) {------------
		
	<%-- 진료과목 데이터 가져오기  --%>
	$.ajax({
		url:"<%= ctxPath%>/getclasscode.bibo",
		async:false,
		dataType:"json",
		success:function(json){
			let v_html = `<option>진료과목 선택</option>`;
			for(let i=0; i<json.length; i++){
				v_html +=`<option value="\${json[i].classcode}">\${json[i].classname}</option>`;
			}	// end of for---------
			$("select#dept").html(v_html);
		},
		error:function(request){
			alert("code : " + request.status);
		}
	})	// end of $.ajax({-------------
	
})	// end of $(document).ready(function(){--------------
function HPSearch(){
	const frm = document.searchHospitalFrm;
	frm.submit();
}
</script>

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
	    
    	<form name="searchHospitalFrm">
	    	<div class="searchhp">
	    
	    		<div class="location_select">
	    		
		    		<span class="searchoicename">병원위치</span>
		            
		            <select name="city" id="city" class="selectbox loc_sel">
		                <%-- 시/도 데이터 --%>
		            </select>
		            
		            <select name="loc" id="lod" class="selectbox loc_sel">
						<option>시/군구 선택</option>
						<%-- 시/군구 데이터 --%>
		            </select>
		            
	    		</div>
	            
	            <div class="class_select">
	            
	            	<span class="searchoicename">진료과목</span>
		            <select name="dept" id="dept" class="selectbox inlinesearch">
						<%-- 진료과목 데이터 --%>
		            </select>
	            
	            </div>
	            
	           	<div class="name_input">
	           		
	           		<span class="searchoicename">병원명</span>
	               	<input text="type" class="inputbox" name="hpname" placeholder="병원명을 입력하세요."/>
	               	<button type="button" class="searchhpbtn" onclick="HPSearch()">검색</button>
	           	</div>
           	
        	</div>
        
     	</form>
	    
	    <div class="exam_choiceDr">
	    	<c:if test="${empty requestScope.mbHospitalList}">
	    		검색결과에 맞는 결과가 없습니다.
	    	</c:if>
	    	<c:if test="${not empty requestScope.mbHospitalList}">
	    		<c:forEach var="hospitalDTO" items="${requestScope.mbHospitalList}" varStatus="status">
	    			<div class="btn_card">
		            	<div class="card_top">
		            		<h4 class="hospital_name">${hospitalDTO.hpname}</h4>
		                	<input type="checkbox" class="hj_custom-checkbox">
		                	<label class="hj_custom-checkbox-label"></label>
		            	</div>
		                <p class="hospital_addr">
		                	${hospitalDTO.hpaddr}
		                </p>
		            </div>
	    		</c:forEach>
	    	</c:if>          
	    </div>
	    <%-- ================================================================================================== --%>
	    <%-- == 페이징바 === --%>
	    <div class="w-100 d-flex justify-content-center pt-3">
	        <ul class="pagination hj_pagebar nanum-n size-s">
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