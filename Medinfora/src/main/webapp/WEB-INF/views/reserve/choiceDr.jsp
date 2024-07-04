<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<% String ctxPath = request.getContextPath(); %>

<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/reserve/choiceDr.css" />
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/reserve/choiceDrMedia.css" />

<script type="text/javascript" src="<%= ctxPath%>/resources/js/reserve/choiceDr.js"></script>

<script type="text/javascript">

jQuery(function() {
	
	<%-- 시/도 데이터 가져오기 --%>
	$.ajax({
		url:"<%= ctxPath%>/getcityinfo.bibo",
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
		const city_val = $(this).val();
		
		<%-- 시/군구 데이터 가져오기 --%>
		const city ={"city":city_val};
		
		$.ajax({
			url:"<%= ctxPath%>/getlocalinfo.bibo",
			async:false,
			data:city,
			dataType:"json",
			success:function(json){
				let v_html = `<option>시/군구 선택</option>`;
				for(let i=0; i<json.length; i++){
					if(json[i]!=null){	<%-- 세종과 다른 시도 구분 --%>
						v_html +=`<option value="\${json[i]}">\${json[i]}</option>`;
					}
				}	// end of for---------
				$("select#loc").html(v_html);
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
			}	// end of for--------------
			$("select#dept").html(v_html);
		},
		error:function(request){
			alert("code : " + request.status);
		}
	})	// end of $.ajax({--------------

	Page(1);
	
})	// end of jQuery(function() {})--------------
//////////////////////////////////////////////////////////////////////////////////
function HPSearch(){
	
	const city = $("select[name='city']").val();
	const local = $("select[name='loc']").val();
	const classcode = $("select[name='dept']").val();
	const hpname = $("input[name='hpname']").val();

	Page(1);
}	// end of function HPSearch(){---------------------------------------

function Page(currentShowPageNo){

	const city = $("select[name='city']").val();
	const local = $("select[name='loc']").val();
	const classcode = $("select[name='dept']").val();
	const hpname = $("input[name='hpname']").val();
	
	$.ajax({
		url:"<%= ctxPath%>/reserve/choiceDrList.bibo",
		data:{"city":city,
			"local":local,
			"classcode":classcode,
			"hpname":hpname,
			"currentShowPageNo":currentShowPageNo},
		dataType:"json",
		success:function(json){
			if(json.length > 0){
				<%-- === 검색내용 === --%>
				let v_html = ``;
				$.each(json,function(index,item){
					v_html += `<div class="resulthp btn_card">
									<h5 class="hidx">\${item.hidx}</h5>
		            				<div class="card_top">
		            					<h4 class="hospital_name">\${item.hpname}</h4>
		                				<input type="checkbox" class="hj_custom-checkbox">
		                				<label class="hj_custom-checkbox-label"></label>
		            				</div>
		                			<p class="hospital_addr">
		                				\${item.hpaddr}
		                			</p>
		            			</div>`;
				})	// end of $.each(json,function(index,item){--------
				
				$("div.exam_choiceDr").html(v_html);
					
				<%-- === 페이지바 === --%>
				const blockSize = 3;
				let loop = 1;
				let pageNo = Math.floor(((currentShowPageNo - 1)/blockSize)) * blockSize + 1;
				let totalPage = json[0].totalPage;
				
				let pageBar = `<ul class='pagination hj_pagebar nanum-n size-s'>`;
				
				if(pageNo != 1) {
					pageBar += "<li class='page-item'>" 
							+ " 	<a class='page-link' href='javascript:Page("+(pageNo-1)+")>" 
							+ "	    	<span aria-hidden='true'>&laquo;</span>" 
							+ "	    </a>" 
							+ "</li>";
				}
				
				while(!(loop>blockSize || pageNo > totalPage)) {
					if(pageNo == currentShowPageNo) {
						pageBar += "<li class='page-item'>"
								+ "		<a class='page-link nowPage'>"+pageNo+"</a>" 
								+ "</li>";
					}
					else{
						pageBar += "<li class='page-item'>"
								+ "		<a class='page-link' href='javascript:Page("+pageNo+")'>" +pageNo+"</a>" 
								+ "</li>";
					}
					loop++;
					pageNo++;
				}
				
				if(pageNo <= totalPage) {
					pageBar += "<li class='page-item'>"
							+ "		<a class='page-link' href='javascript:Page("+pageNo+")'>"
							+ "	    	<span aria-hidden='true'>&raquo;</span>"
							+ "	    </a>"
							+ "</li>";
				}
				
				pageBar += "</ul>";
				
				$("div#ReserveHP_PageBar").html(pageBar);
			}
			else{
				v_html = `검색결과에 맞는 결과가 없습니다.`;
				$("div.exam_choiceDr").html(v_html);	
			
				let pageBar = ``;
				$("div#ReserveHP_PageBar").html(pageBar);	
			}
		},
		error:function(request,status,error){
			alert("code: "+request.status);
		}
	})	// end of $.ajax({-----
}	// end of function Page(currentShowPageNo){----------------------
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
		            <select name="loc" id="loc" class="selectbox loc_sel">
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
	               	<input type="text" class="inputbox" name="hpname" placeholder="병원명을 입력하세요."/>
	               	<button type="button" class="searchhpbtn" onclick="HPSearch()">검색</button>
	           	</div>
        	</div>
     	</form>
	    
	    <div class="exam_choiceDr">
	    </div>
	    <%-- ================================================================================================== --%>
	    <%-- == 페이징바 === --%>
	    <div id="ReserveHP_PageBar" class="w-100 d-flex justify-content-center pt-3">
	    </div>
	    <%-- ================================================================================================== --%>	    
	    <div class="div_proc text-center mb-5">
	        <button type="button" class="btn_proc btn btn-lg mr-5" onclick="javascript:history.back()">취소</button>
	        <button type="button" class="btn_proc btn btn-lg" onclick="javascript:hpSelectNext()">다음</button>
	    </div>
	    <form name="hpinfo">
	    	<input type="text" name="hidx" value="" />
	    </form>
	</div>
</div>