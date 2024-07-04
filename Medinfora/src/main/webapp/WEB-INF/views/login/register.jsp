<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

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


<input type="hidden" name="ctxPath" value="<%=ctxPath%>"/>
<!-- 회원가입 form -->
<div class="jh_container">
	<div class="registerArea">
		<form id="registerFrm" name="registerFrm">
			<p class="size-n nanum-eb" align="center">회원가입</p>
			
			<%-- 일반 및 의료(카카오제외)만  활성화 --%>
			<label class="nanum-eb mb-1" for="userid">아이디</label><span class="nstar">&nbsp;&ast;</span>
			<div class="idArea">
				<input class="form-control insert_w" type="text" name="userid" id="userid" placeholder="아이디 입력(문자, 숫자  6-20자)"/>
				<button class="isExistCheck rounded-3" id="isExistCheck_userid" type="button">중복확인</button>
				<%-- 중복확인 된건지 표식 남기기 --%>
				<input name="idCheck_click" value="false" type="hidden" />
			</div>
			<p class="mb-4 nanum-n size-s" id="id_cmt"></p>
			
			<%-- 일반 및 의료(카카오제외)만  활성화 --%>
			<div class="pwdArea mb-4">
				<label class="nanum-eb mb-1" for="pwd">비밀번호</label><span class="nstar">&nbsp;&ast;</span>
				<input class="form-control insert_w" type="password" name="pwd" id="pwd" placeholder="문자, 숫자, 특수문자(!@#$%^&*) 포함 8-16자"/>
				<p class="mb-4 nanum-n size-s" id="pwd_cmt"></p>
				
				<label class="nanum-eb mb-1" for="pwd">비밀번호 확인</label><span class="nstar">&nbsp;&ast;</span>
				<input class="form-control insert_w" type="password" id="pwd_check" placeholder="비밀번호 재입력"/>
				<p class="nanum-n size-s" id="pwd_check_cmt"></p>
			</div>
			
			<!-- 이름 -->
			<div class="nameArea">
				<label class="nanum-eb mb-1" for="name">이름</label><span class="nstar">&nbsp;&ast;</span>
				<input class="form-control insert_w" type="text" name="name" id="name" placeholder="이름을 입력하세요(최대 12글자)"/>
			</div>
			<p class="nanum-n size-s mb-4" id="name_cmt"></p>
			
			<!-- 이메일-->
			<label class="nanum-eb mb-1" for="email">이메일</label><span class="nstar">&nbsp;&ast;</span>
			<div class="emailArea">
				<input class="form-control insert_w" type="text" name="email" id="email" placeholder="이메일 입력"/>
				<button class="isExistCheck" id="isExistCheck_email" type="button">중복확인</button>
				<%-- 중복확인 된건지 표식 남기기 --%>
				<input name="emailCheck_click" value="false" type="hidden" />
			</div>
			<p class="nanum-n size-s mb-4" id="email_cmt"></p>
			
			
			<!-- 연락처 -->
			<div class="mobileArea mb-4">
				<label class="nanum-eb mb-1" for="name">전화번호</label><span class="nstar">&nbsp;&ast;</span>
				<input class="form-control insert_w" type="text" name="mobile" id="mobile" placeholder="전화번호 입력('-' 제외 입력)"/>
				<p class="nanum-n size-s mb-4" id="mobile_cmt"></p>
			</div>
			
			<!-- 주소 -->
			<label class="nanum-eb mb-1" for="addressSearch">주소</label><span class="nstar">&nbsp;&ast;</span>
			<div class="addressArea mb-2">
				<input class="form-control insert_w" type="text" name="address" id="address" placeholder="주소 입력" disabled/>
				<button class="isExistCheck rounded-3" id="addressSearch" type="button" onclick="search()">주소검색</button>
			</div>
			<input class="form-control " type="text" name="detailAddress" id="detailAddress" placeholder="상세주소 입력"/>
			<p class="nanum-n size-s mb-4" id="address_cmt"></p>
			
			
			<%-- 일반(카카오포함)만  활성화 --%>
			<div class="genderArea mb-4">
				<label class="nanum-eb mb-1">성별</label>
				  
				<label class="mx-5 gender" for="male">
					<span class="nanum-n mx-2">남자</span>
					<input class="mx-2" type="radio" name="gender" id="male" value="male" />
				</label>
				
				<label class="nanum-n gender" for="female">
					<span>여자</span>
					<input class="mx-2" type="radio" name="gender" id="female" value="female" />
				</label>
			</div>
			
			<%-- 일반(카카오포함)만  활성화 --%>
			<div class="birthArea mb-5">
		  		<label class="nanum-eb my-1" for="birthday">생년월일</label>
			  	<input class="form-control inser_date" type="text" id="birthday" name="birthday" value="2022-01-01" />
			  	<label class="mx-1 pt-2" for="birthday"><i class="fa-solid fa-calendar-days"></i></label>
			</div>
			
			
			<div class="text-center">
				<button class="isExistCheck nanum-eb" type="button" onclick="register()" style="background-color: #6666ff; margin-right: 50px;"> 가입하기</button>
				<button class="isExistCheck nanum-eb" type="reset" style="background-color: #fb5e5e;">취소</button>
			</div>
		
		</form>
	</div>
</div>