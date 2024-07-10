<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<% String ctxPath = request.getContextPath(); %>

<link rel="stylesheet" href="<%=ctxPath%>/resources/css/myinfo/myinfoconfig.css">
<script src="<%=ctxPath%>/resources/js/mypage/mypageconfig.js"></script>

<form style="margin: 0;" name ="configForm" method="post">
	<input name="userid" value="${sessionScope.loginuser.userid}" />
	<input id="mobileIdout"  name="mobile" />
	<input id="pwdIdout" name="pwd" />
</form>

<div class="infoitem">

	<div class="inneritem">
		<div class="sidetitle">
			내정보
		</div>
		<div class="main_contain">
		
			<div class="item_title">
				이름
			</div>
			<div class="item_input">
				${sessionScope.loginuser.name}
			</div>
			
			<div class="item_title">
				회원상태
			</div>
			<div class="item_input">
				<c:if test="${sessionScope.loginuser.mIdx==0}">
					관리자
				</c:if>
				<c:if test="${sessionScope.loginuser.mIdx==1}">
					일반회원
					<c:if test="${sessionScope.loginuser.loginmethod==1}">
						&nbsp;(카카오)
					</c:if>
				</c:if>
				<c:if test="${sessionScope.loginuser.mIdx==2}">
					의료회원
				</c:if>
			</div>
			
			<div class="item_title">
				이메일
			</div>
			<div class="item_input">
				${sessionScope.loginuser.email}
			</div>
			
			<div class="item_title">
				연락처
			</div>
			<div class="item_input">
				<input id="mobileId" type="text" class="item_inputtag" name="mobile" value="${sessionScope.loginuser.mobile}" />
			</div>
			<div class="item_title">
				</div>
				<div class="item_input" id="mobile_waring" style="color: #DC3545;"></div>
			
			<c:if test="${sessionScope.loginuser.mIdx==1}">
				<div class="item_title">
					주소
				</div>
				<div class="item_input">
					<input type="text" class="item_inputtag" name="address" value="${sessionScope.loginuser.address}" readonly="readonly" />
				</div>
				<div class="item_title">
				</div>
				<div class="item_input">
					<input type="text" class="item_inputtag" name="detailaddress" value="${sessionScope.loginuser.detailAddress}" />
				</div>
			</c:if>
			
			<div class="save_liner">
				<button type="button" class="item_btn" onclick="javascript:infoChange();">수정</button>
			</div>
		</div>
	</div>
	<div class="inneritem">
		<div class="sidetitle">
			비밀번호 변경
		</div>
		<div class="main_contain">
			<div class="item_title">
				현재 비밀번호
			</div>
			<div class="item_input">
				<input type="password" class="item_inputtag" name="Nowpwd" />
			</div>
			<div class="item_title">
				새 비밀번호
			</div>
			<div class="item_input">
				<input type="password" class="item_inputtag" name="pwd" />
			</div>
			<div class="item_title">
				새 비밀번호
			</div>
			<div class="item_input">
				<input type="password" class="item_inputtag" name="pwdRepect" />
			</div>
			<div class="save_liner">
				<button type="button" class="item_btn">수정</button>
			</div>
		</div>
	</div>

</div>