<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%String ctxPath = request.getContextPath();%>

<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/login/find.css">
<script type="text/javascript" src="<%= ctxPath%>/resources/js/login/find.js"></script>


<div class="findContainer b_black">

	<h2 class="nanum-eb size-n my-4" id="finditem">아이디찾기</h2>

	<div class="qwer">
		<label class="label_st" for="in_name">
			<input id="in_name" name="in_name" type="text" placeholder="성명 또는 병원명을 입력해주세요" />
			<button type="button" id="reset_btn">
				<i class="fa-solid fa-xmark fa-lg" style="color: #ffffff;"></i>
			</button>
		</label>

	</div>
	<div class="qwer mb-4" style="text-align: left;">
		<p class="mb-4" id="cmt_name">성명 또는 병원명 입력해주세요.</p>
	</div>


	<div class="qwer">
		<label class="label_st" for="in_email"> 
			<input id="in_email" name="in_email" type="text" placeholder="회원정보에 등록된 이메일" />
			<button type="button" id="reset_btn">
				<i class="fa-solid fa-xmark fa-lg" style="color: #ffffff;"></i>
			</button>
			<button class="rounded-pill btn btn-dark nanum-b btn_st"
				type="button">인증번호 요청</button>
		</label>

	</div>
	<div class="qwer mb-4" style="text-align: left;">
		<p id="cmt_email">이메일을 정확히 입력해 주세요.</p>
	</div>


	<div class="qwer mb-4" id="codeArea">
		<label class="label_st" for="in_code">
			<input id="in_code" type="text" placeholder="인증코드를 입력하세요" />
			<button type="button" id="reset_btn">
				<i class="fa-solid fa-xmark fa-lg" style="color: #ffffff;"></i>
			</button>
			<button class="rounded-pill btn btn-outline-dark nanum-b btn_st"
				type="button">인증 확인</button>
		</label>

	</div>

	<div class="qwer mb-4" style="text-align: left;">
		<p class="mb-5" id="cmt_code">인증번호를 입력해 주세요.</p>
	</div>


	<button class="next_button nanum-b" id="" type="button">다음 단계</button>



</div>