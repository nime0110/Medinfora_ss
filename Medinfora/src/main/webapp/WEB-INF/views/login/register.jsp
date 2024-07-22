<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% 
	String ctxPath = request.getContextPath();
%>

<%-- daterangepicker 관련 --%>
<script type="text/javascript" src="https://cdn.jsdelivr.net/jquery/latest/jquery.min.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>
<link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />

<%-- 카카오 주소 검색 관련 --%>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<%-- jh 만든 css 및 js --%>
<link rel="stylesheet" href="<%=ctxPath%>/resources/css/reigster/register.css">
<script type="text/javascript" src="<%=ctxPath%>/resources/js/register/register.js"></script>


<script type="text/javascript">
jQuery(function(){
	
	const join = "${requestScope.join}";
	
	// 오늘 날짜 구하기
    const date = new Date();

    const year = date.getFullYear();
    const month = date.getMonth() + 1;
    const day = date.getDate();

    let today = year+"-"+month+"-"+day;
    
    <%-- 카카오 값 받아와서 입력해주기 --%>
	const k_name = "${sessionScope.kakaoInfo.name}";
	const k_email = "${sessionScope.kakaoInfo.email}";
	const k_birthday = "${sessionScope.kakaoInfo.birthday}";
	const k_mobile = "${sessionScope.kakaoInfo.mobile}";
	const k_gender = "${sessionScope.kakaoInfo.gender}";
	
	console.log(k_name);
	
    if(join== "1"){
    	$("input:radio[id='male']").prop("checked", true);
    }
	
	
    if(join == "3"){
    	today = k_birthday;
    	$("input:text[name='name']").val(k_name);
    	$("input:text[name='email']").val(k_email);
    	$("input:text[name='mobile']").val(k_mobile);
    	
    	if(k_gender == "male"){
    		$("input:radio[id='male']").prop("checked", true);
    	}
    	else if(k_gender == "female"){
    		$("input:radio[id='female']").prop("checked", true);
    	}
		else{
			$("input:radio[id='male']").prop("checked", true);
		}
    }

	// 달력 관련
    $("input:text[name='birthday']").daterangepicker({
        "singleDatePicker": true,
        "showDropdowns": true,
        "autoApply": true,  // 자동입력
        "locale": {
            "format": "YYYY-MM-DD",
            "customRangeLabel": "Custom",
            "weekLabel": "W",
            "daysOfWeek": ["일","월","화","수","목","금","토"],
            "monthNames": ["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"],
            "firstDay": 1
        },
        "startDate": today,
        "endDate": today,
        "maxDate": today,
        "opens": "center"

      }, function(start, end, label) {
          // console.log('New date range selected: ' + start.format('YYYY-MM-DD') + ' to ' + end.format('YYYY-MM-DD') + ' (predefined range: ' + label + ')');
      });
	
	
});// end of $(document).ready(fucntion()---
		
function goback(){
	location.href="<%=ctxPath%>/index.bibo";
}


function searchMedical(){
	
    const url = "<%=ctxPath%>/register/searchmedical.bibo"
	const setting = "menubar=no,location=no,resizable=no,scrollbars=yes,status=no,top=100, left=100, width=700,height=500";
    
    window.open(url, 'searchMedical', setting);
    
}


function searchMedical_IN(medicalInfo){
	
	const hidx = medicalInfo.hidx;
	const hpname = medicalInfo.hpname;
	const hpaddr = medicalInfo.hpaddr;
	const hptel = medicalInfo.hptel;
	const addr = medicalInfo.addr;
	const detailAddr = medicalInfo.detailAddr;
	
	// Addtional Code START
	$.ajax({
		url : "<%=ctxPath%>/register/checkhidx.bibo",
		data : {"hidx":hidx},
		dataType : "json",
		async : true,
		success:function(json){
			if(json.checkHidx){
				$("p#address_cmt").html("이미 가입된 병원입니다.");
			}else{
				$("input:hidden[name='hidx']").val(hidx);
				$("input:text[name='name']").val(hpname);
				$("input:hidden[name='hpaddr']").val(hpaddr);
				$("input:text[name='mobile']").val(hptel);
				$("input:text[name='address']").val(addr);
				$("input:text[name='detailAddress']").val(detailAddr);
				$("p#address_cmt").html("");
			}
		},
		error:function(request){
			alert("code : " + request.status);
		}
	});
	
	return;
}


</script>




<input type="hidden" name="ctxPath" value="<%=ctxPath%>"/>
<!-- 회원가입 form -->
<div class="jh_container">
	<div class="registerArea">
		<form id="registerFrm" name="registerFrm">
			<input type="hidden" name="join" value="${requestScope.join}"/>
			<p class="size-n nanum-eb" align="center">회원가입</p>
			
			<%-- 일반 및 의료(카카오제외)만  활성화 --%>
			<c:if test="${not empty requestScope.join and requestScope.join ne 3}">
				<%-- 아이디 --%>
				<label class="nanum-eb mb-1" for="userid">아이디</label><span class="nstar">&nbsp;&ast;</span>
				<div class="idArea">
					<input class="form-control insert_w" type="text" name="userid" id="userid" placeholder="아이디 입력(문자, 숫자  6-20자)"/>
					<button class="isExistCheck rounded-3" id="isExistCheck_userid" type="button">중복확인</button>
					<%-- 중복확인 된건지 표식 남기기 --%>
					<input name="idCheck_click" value="false" type="hidden" />
				</div>
				<p class="mb-4 nanum-n size-s" id="id_cmt"></p>
				
				<%-- 비밀번호 --%>
				<div class="pwdArea mb-4">
					<label class="nanum-eb mb-1" for="pwd">비밀번호</label><span class="nstar">&nbsp;&ast;</span>
					<input class="form-control insert_w" type="password" name="pwd" id="pwd" placeholder="문자, 숫자, 특수문자(!@#$%^&*) 포함 8-16자"/>
					<p class="mb-4 nanum-n size-s" id="pwd_cmt"></p>
					
					<label class="nanum-eb mb-1" for="pwd_check">비밀번호 확인</label><span class="nstar">&nbsp;&ast;</span>
					<input class="form-control insert_w" type="password" id="pwd_check" placeholder="비밀번호 재입력"/>
					<p class="nanum-n size-s" id="pwd_check_cmt"></p>
				</div>
			</c:if>
			
			<%-- 이메일 모두 활성화 --%>
			<label class="nanum-eb mb-1" for="email">이메일</label><span class="nstar">&nbsp;&ast;</span>
			<div class="emailArea">
				<input class="form-control insert_w" type="text" name="email" id="email" placeholder="이메일 입력"/>
				<button class="isExistCheck" id="isExistCheck_email" type="button">중복확인</button>
				<%-- 중복확인 된건지 표식 남기기 --%>
				<input name="emailCheck_click" value="false" type="hidden" />
			</div>
			<p class="nanum-n size-s mb-4" id="email_cmt"></p>
			
			
			<%-- 이름 모두 활성화(의료에 경우 병원선택으로 입력할 수 있도록 함)--%>
			<%-- 일반유저 --%>
			<c:if test="${not empty requestScope.join and requestScope.join ne 2}">
				<div class="nameArea">
					<label class="nanum-eb mb-1" for="name" id="name">이름</label><span class="nstar">&nbsp;&ast;</span>
					<input class="form-control insert_w" type="text" name="name" id="name" placeholder="이름을 입력하세요(최대 12글자)"/>
				</div>
				<p class="nanum-n size-s mb-4" id="name_cmt"></p>
			</c:if>

			<%-- 의료유저  병원명 --%>
			<c:if test="${not empty requestScope.join and requestScope.join eq 2}">
				<label class="nanum-eb mb-1" for="searchMedical_btn" id="name">병원명</label><span class="nstar">&nbsp;&ast;</span>
				<div class="d-flex">
					<input class="form-control insert_w" type="text" name="name" id="name" placeholder="병원검색을 통해 선택하세요" disabled/>
					<button class="isExistCheck rounded-3" id="searchMedical_btn" type="button" onclick="searchMedical()">병원검색</button>
					<input type="hidden" name="hidx" value="" />
				</div>
				<p class="nanum-n size-s mb-4" id="name_cmt"></p>
			</c:if>
			
			
			<%-- 연락처 모두 활성화 (의료에 경우 병원선택으로 입력할 수 있도록 함)--%>
			<%-- 일반유저  --%>
			<c:if test="${not empty requestScope.join and requestScope.join ne 2}">
				<div class="mobileArea mb-4">
					<label class="nanum-eb mb-1" for="mobile">전화번호</label><span class="nstar">&nbsp;&ast;</span>
					<input class="form-control insert_w" type="text" name="mobile" id="mobile" placeholder="전화번호 입력('-' 제외 입력)"/>
					<p class="nanum-n size-s mb-4" id="mobile_cmt"></p>
				</div>
			</c:if>
			<%-- 의료유저 --%>
			<c:if test="${not empty requestScope.join and requestScope.join eq 2}">
				<div class="mobileArea mb-4">
					<label class="nanum-eb mb-1" for="searchMedical_btn">전화번호</label><span class="nstar">&nbsp;&ast;</span>
					<input class="form-control insert_w" type="text" name="mobile" id="mobile" placeholder="병원검색을 통해 선택하세요" disabled/>
					<p class="nanum-n size-s mb-4" id="mobile_cmt"></p>
				</div>
			</c:if>
			
			<%-- 주소 모두 활성화 (단, 의료에 경우 주소검색 버튼 카카오주소검색 아님 --%>
			<c:if test="${not empty requestScope.join and requestScope.join ne 2}">
				<label class="nanum-eb mb-1" for="addressSearch">주소</label><span class="nstar">&nbsp;&ast;</span>
				<div class="addressArea mb-2">
					<input class="form-control insert_w" type="text" name="address" id="address" placeholder="주소 입력" disabled/>
					<button class="isExistCheck rounded-3" id="addressSearch" type="button" onclick="search()">주소검색</button>
				</div>
				<input class="form-control" type="text" name="detailAddress" id="detailAddress" placeholder="상세주소 입력"/>
			</c:if>
			<c:if test="${not empty requestScope.join and requestScope.join eq 2}">
				<label class="nanum-eb mb-1" for="searchMedical_btn">주소</label><span class="nstar">&nbsp;&ast;</span>
				<input class="form-control mb-2" type="text" name="address" id="address" placeholder="병원검색을 통해 선택하세요" disabled/>
				<input class="form-control" type="text" name="detailAddress" id="detailAddress" placeholder="상세주소" disabled/>
				<input type="hidden" name="hpaddr" value="" />
			</c:if>
			<p class="nanum-n size-s mb-4" id="address_cmt"></p>
			
			
			<%-- 일반(카카오포함)만  활성화 --%>
			<c:if test="${not empty requestScope.join and requestScope.join ne 2}">
				<%-- 성별 --%>
				<div class="genderArea mb-4">
					<label class="nanum-eb mb-1">성별</label>
					  
					<label class="mx-5 gender" for="male">
						<span class="nanum-n mx-2">남자</span>
						<input class="mx-2" type="radio" name="gender" id="male" value="1" />
					</label>
					
					<label class="nanum-n gender" for="female">
						<span>여자</span>
						<input class="mx-2" type="radio" name="gender" id="female" value="2" />
					</label>
				</div>
			
				<%-- 생년월일 --%>
				<div class="birthArea mb-5">
			  		<label class="nanum-eb my-1" for="birthday">생년월일</label>
				  	<input class="form-control inser_date" type="text" id="birthday" name="birthday" />
				  	<label class="mx-1 pt-2" for="birthday"><i class="fa-solid fa-calendar-days"></i></label>
				</div>
			</c:if>
				
				<div class="text-center pt-5">
					<button class="isExistCheck nanum-eb" type="button" onclick="register()" style="background-color: #6666ff; margin-right: 50px;"> 가입하기</button>
					<button class="isExistCheck nanum-eb" type="button" onclick="goback()" style="background-color: #fb5e5e;">취소</button>
				</div>
			
		
		</form>
	</div>
</div>