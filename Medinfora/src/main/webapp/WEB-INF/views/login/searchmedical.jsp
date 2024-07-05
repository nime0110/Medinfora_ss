<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String ctxPath = request.getContextPath();
 %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%-- Jquery --%>
<script src="<%= ctxPath%>/resources/node_modules/jquery/dist/jquery.min.js"></script>

<%-- JS --%>
<script type="text/javascript" src="<%= ctxPath%>/resources/js/index.js"></script>

<%-- BootStrap --%>
<script src="<%= ctxPath%>/resources/node_modules/bootstrap/dist/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="<%= ctxPath%>/resources/node_modules/bootstrap/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

<%-- stylesheet --%>
<style type="text/css">

.searchFrm{
	height: 3rem;
	
}

.searchType{
	width: 100px;
	border-radius: 1rem 0 0 1rem;
}

.searchWord {
	border: none;
	border-top: solid 1px gray;
	border-bottom: solid 1px gray;
	border-radius: 0;
}

button#searchbtn{
	border-radius: 0 1rem 1rem 0;
}

.searchList{
	border-top: solid 1px #595959;
	border-bottom: solid 1px #595959;
}

.searchList:hover{
	cursor: pointer;
	background-color: #f2f2f2;
}


</style>


<%-- Google Font --%>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap" rel="stylesheet">
<link rel="stylesheet" href="<%= ctxPath%>/resources/css/fontcss.css">

<%-- Font Awesome 6 Icons --%>
<script src="https://kit.fontawesome.com/f1e9f47e08.js" crossorigin="anonymous"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">

<script type="text/javascript">
$(document).ready(function(){

	$("div#displayList").hide();
	$("div.info_body").hide();

	$("input:text[name='searchWord']").keyup((e)=>{
		const w_length = $(e.target).val().trim().length;

		if(w_length == 0){
			$("div#displayList").hide();
			$("div.info_body").show();
		}
		else{
			$("div.info_body").hide();

			if( $("select.searchType").val() == "hpname" || $("select.searchType").val() == "hpaddr"){
				$.ajax({
					url:"<%=ctxPath%>/register/autoWord.bibo",
					data:{"searchType":$("select[name='searchType']").val()
						 ,"searchWord":$("input[name='searchWord']").val()},
					dataType:"json",
					success:function(json){
						if(json.length > 0){
							let add_html = ``;
								
								$.each(json, function(index, item){
									const autoWord = item.autoWord;
									
											
								    const idx = autoWord.toLowerCase().indexOf($("input[name='searchWord']").val().toLowerCase());
									
									const len = $("input[name='searchWord']").val().length; 
								
								    const result = autoWord.substring(0, idx) + "<span style='color:navy; font-weight: bold;'>"+autoWord.substring(idx, idx+len)+"</span>"+ autoWord.substring(idx+len); 
								    
								    add_html += `<span style='cursor:pointer;' class='result'>\${result}</span><br>`;
								    
								});// end of $.each()----------------------
								
								// const input_width = $("input[name='searchWord']").css("width"); // 검색어 input 태그 width 값 알아오기
								
								// $("div#displayList").css({"width":input_width}); // 검색결과 div 의 width 크기를 검색어 입력 input 태그의 width 와 일치시키기 
								
								$("div#displayList").html(add_html);
								$("div#displayList").show();

						}
					},
					error: function(request, status, error){
						alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
					}
				});

			}
		}

	});// end of $("input:text[name='searchWord']").keyup((e)=>{ ----
	
		
	$(document).on("click", "span.result", function(e){
		const autoword = $(e.target).text();
		$("input[name='searchWord']").val(autoword); // 텍스트박스에 검색된 결과의 문자열을 입력해준다.
		$("div#displayList").hide();
	});




});// end of $(document).ready(function(){ ----

//Function Declaration
function goSearch(currentPageNo){

	const searchType = $("select[name='searchType']").val().trim();
	const searchWord = $("input:text[name='searchWord']").val().trim();

	if(searchWord == ""){
		return;
	}

	

	$.ajax({
		url:"<%=ctxPath%>/register/searchmedicalEnd.bibo"
		data:{"searchType":searchType
			 ,"searchWord":searchWord
			 ,"currentPageNo":currentPageNo},
		dataType:"json",
		success:function(json){
			alert("검색된거 들어왔음");
		},
		error: function(request, status, error){
			alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		}

	});


}// end of function goSearch(){---


</script>

</head>
<body>
<div class="m-5" style="border: solid 1px red;">
<p class="nanum-eb size-n">병원 찾기</p>
<form class="mt-3 d-flex searchFrm" name="searchFrm">
	<select class="searchType" name="searchType">
		<option value="hpname">병원명</option>
		<option value="hpaddr">주소</option>
	</select>
	<input class="form-control searchWord" type="text" name="searchWord" size="70" autocomplete="off" /> 
	<input type="text" style="display: none;"/> <%-- form 태그내에 input 태그가 오로지 1개 뿐일경우에는 엔터를 했을 경우 검색이 되어지므로 이것을 방지하고자 만든것이다. --%> 
	<button id="searchbtn" type="button" class="btn btn-primary" onclick="goSearch('1')"><span><i class="fa-solid fa-magnifying-glass"></i></span></button> 
</form>

<%-- === 검색어 입력시 자동글 완성하기  === --%>
<div id="displayList" style="border:solid 1px gray; border-top:none; height:200px; overflow:auto;"></div>
<div class="info_body">
	<p class="p-3"><span class="nanum-eb h4">tip</span>&nbsp;&nbsp;<span class="nanum-n">병원이름 검색 시 주소가 맞는지 확인하시어 선택바랍니다.</span></p>
</div>

<%-- 검색한 결과 병원리스트를 보여준다.--%>
<div class="gd mt-3">
	<div class="nanum-eb size-s">검색결과&nbsp;&nbsp;<span>x&nbsp;건</span></div>
	<div class="mt-3">
		<%-- 여기 이제 포문 돌리고 페이징 처리 --%>
		<div class="searchList py-3">
			<div class="nanum-eb size-s"> 서울병원의원</div>
			<div class="nanum-n size-s">서울특별시 서초구 서초중앙로 26, 1층 109호 (서초동, 래미안 서초유니빌)</div>
		</div>
	</div>


</div>

<%-- 페이지바 처리 --%>
<div class="pagebar" align="center">페이지 바</div>



</div>

















</body>
</html>