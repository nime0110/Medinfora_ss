<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%String ctxPath = request.getContextPath();%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/login/find.css">
<script type="text/javascript" src="<%= ctxPath%>/resources/js/login/find.js"></script>


<div class="findContainer">

	<h2 class="nanum-eb size-n my-4" id="finditem">아이디찾기</h2>
	<div class="qwer">
		<c:if test='${requestScope.wf == "1"}'>
		<label class="label_st" for="in_name">
			<input id="in_name" name="in_name" type="text" placeholder="성명 또는 병원명을 입력해주세요" />
			<button class="reset_btn" type="button">X</button>
		</label>
		</c:if>
		
		<c:if test='${requestScope.wf == "2"}'>
		<label class="label_st" for="in_id">
			<input id="in_id" name="in_id" type="text" placeholder="아이디를 입력해주세요" />
			<button class="reset_btn" type="button">X</button>
		</label>
		</c:if>

	</div>
	<div class="qwer mb-4" style="text-align: left;">
		<c:if test='${requestScope.wf == "1"}'>
		<p id="cmt_name">성명 또는 병원명을 올바르게 입력해주세요.</p>
		</c:if>
		
		<c:if test='${requestScope.wf == "2"}'>
		<p id="cmt_name">아이디를 올바르게 입력해주세요.</p>
		</c:if>
		
	</div>


	<div class="qwer">
		<label class="label_st" for="in_email"> 
			<input id="in_email" name="in_email" type="text" placeholder="회원정보에 등록된 이메일" />
			<button class="reset_btn" type="button">X</button>
			<button class="rounded-pill btn btn-dark nanum-b btn_st" type="button" id="authcodebtn" >인증번호 요청</button>
		</label>

	</div>
	<div class="qwer mb-4" style="text-align: left;">
		<p id="cmt_email">이메일을 정확히 입력해 주세요.</p>
	</div>


	<div class="qwer mb-4" id="codeArea">
		<label class="label_st" for="in_code">
			<input id="in_code" type="text" placeholder="인증코드를 입력하세요" />
			<button class="reset_btn" type="button">X</button>
			<span id="timer"></span>
			<button class="rounded-pill btn btn-outline-dark nanum-b btn_st" type="button" id="correctcodebtn">인증 확인</button>
		</label>
		<input type="hidden" name="authcode" />
		<input type="hidden" name="wf" value="${requestScope.wf}"/>
	</div>

	<div class="qwer mb-4" style="text-align: left;">
		<p id="cmt_code">인증번호를 입력해 주세요.</p>
	</div>


	<button class="next_button nanum-b" id="find" type="button">다음 단계</button>
	
	
	<div class="qwer b_red mt-5" id="findresult" >
		<div id="findid">
			<span class="nanum-b size-eb">회원님의 아이디는&nbsp;&nbsp;<span id="result" style="color: blue; font-weight: bold;"></span>&nbsp;&nbsp; 입니다.</span>
		</div>
	</div>
	
	<%-- 비밀번호 변경 넘겨줄 값 --%>
	<form id="changepwd" method="post" action="<%=ctxPath%>/login/changepwd.bibo">
		<input type="hidden" name="userid" />
	</form>



</div>