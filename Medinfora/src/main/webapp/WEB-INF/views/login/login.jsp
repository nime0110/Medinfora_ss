<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%String ctxPath = request.getContextPath();%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>login</title>

<%-- Jquery --%>
<script src="<%= ctxPath%>/resources/node_modules/jquery/dist/jquery.min.js"></script>

<%-- JS --%>
<script type="text/javascript" src="<%= ctxPath%>/resources/js/index.js"></script>

<%-- BootStrap --%>
<script src="<%= ctxPath%>/resources/node_modules/bootstrap/dist/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="<%= ctxPath%>/resources/node_modules/bootstrap/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

<%-- stylesheet --%>


<%-- Google Font --%>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap" rel="stylesheet">
<link rel="stylesheet" href="<%= ctxPath%>/resources/css/fontcss.css">

<%-- Font Awesome 6 Icons --%>
<script src="https://kit.fontawesome.com/f1e9f47e08.js" crossorigin="anonymous"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">

<style type="text/css">
.loginContainer{
  width: 100%;
  /* height: 700px; */
  /* border: solid 1px red; */
  text-align: center;
  margin: 10% auto 0 auto;
}

.borderBlue{
  border: solid 1px blue;
}

.borderGreen{
  border: solid 1px green;
}

.borderRed{
  border: solid 1px red;
}

/* .loginInfo:hover {
  cursor: pointer;
  color: blue;
  font-weight: bold;

} */

.magin_info{
  margin: 15px auto;
  width: 75%;
  height: 58px;
  display: flex;
}

.loginFrm {
  /* 로그인 form */
  margin: 5% auto;
}

.login_button{
  /* 로그인 버튼 */
  width: 75%;
  height: 58px;
  background-color: #3f80ea;
  border: none;
  color: #fff;
  font-size: 1.5rem;
}


.login_insert {
  /* 아이디,비밀번호 입력칸 */
  padding-left: 8%;
  border: solid 1px #5a647577;

}

.login_idpw_register button{
  /* 회원가입 아이디찾기 비밀번호찾기 */
  padding: 0;
  background-color: #fefefe;
  border: none;
  margin: 10px 5px;

}

.login_idpw_register .lineG {
  /* 회원가입 아이디찾기 비밀번호찾기 구문하는 | 이거 */
    display: inline-block;
    width: 1px;
    height: 13px;
    margin: 0 11px;
    background: #989ea0;
    border-top: 1px solid #cbcecf;
    border-bottom: 1px solid #cbcecf;
}

.login_idpw_register button:hover{
  /* 회원가입 아이디찾기 비밀번호찾기 호버시 */
  font-weight: bold;
}

#kakao_login_btn {
  /* 카카오 로그인 버튼 */
  width: 75%;
  height: 58px;
  margin-bottom: 50px;
  margin-top: -10px;
  padding-top: 11px;
  padding-bottom: 10px;
  border: none;
  background-color: #fee500;
  line-height: 22px;
}

div#loaderArr{
  	position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(255, 255, 255, 0.8);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 9999;
}


/* -- CSS 로딩화면 구현 시작(bootstrap 에서 가져옴) 시작 -- */    
div.loader {
	border: 12px solid #f3f3f3;
   	border-radius: 50%;

    border-top: 12px solid blue;
    
    border-bottom: 12px solid blue;

   	width: 100px;
   	height: 100px;
   	-webkit-animation: spin 2s linear infinite; /* Safari */
   	animation: spin 2s linear infinite;

}

.msg_capslock{
	padding-bottom: 12px;
	color : #DC3545;
	font-weight: bold;
}

/* Safari */
@-webkit-keyframes spin {
	0% { -webkit-transform: rotate(0deg); }
	100% { -webkit-transform: rotate(360deg); }
}
  
@keyframes spin {
	0% { transform: rotate(0deg); }
	100% { transform: rotate(360deg); }
}
/* -- CSS 로딩화면 구현 끝(bootstrap 에서 가져옴) 끝 -- */


</style>



<!-- 카카오 로그인 관련 
<script src="https://t1.kakaocdn.net/kakao_js_sdk/2.7.2/kakao.min.js" integrity="sha384-TiCUE00h649CAMonG018J2ujOgDKW/kVWlChEuu4jK2vxfAAD0eZxzCKakxg55G4" crossorigin="anonymous"></script>
-->

<script type="text/javascript">

$(document).ready(function(){

	$(".msg_capslock").hide();
	
	$("div#loaderArr").hide();
	
	if("${requestScope.isFail}"=='y'){
		$(".msg_capslock").text('아이디 또는 비밀번호가 존재하지 않습니다');
		$(".msg_capslock").show();
	}
	
	$("input:text[name='userid']").bind("keyup", function(e){
		if(e.keyCode == 13){ // 엔터를 했을 경우
			goLogin();
		}
	});
	
	$("input:password[name='pwd']").bind("keyup", function(e){
		if(e.keyCode == 13){ // 엔터를 했을 경우
			goLogin();
		}
	});
	
});

function goLogin(){
	
	const userid = $("input:text[name='userid']").val().trim();
	const pwd = $("input:password[name='pwd']").val().trim();
	
	if(userid == ""){
		  $(".msg_capslock").text('아이디를 입력해주세요.');
		  $(".msg_capslock").show();
		$("input:text[name='userid']").focus();
		return;
	}
	
	if(pwd == ""){
		  $(".msg_capslock").text('비밀번호를 입력해주세요.');
		  $(".msg_capslock").show();
		$("input:password[name='pwd']").focus();
		return;
	}
	
	if(userid != "" && pwd != ""){
		$("div#loaderArr").show();
		
		const loginData = {"userid":userid, "pwd":pwd};
		
		window.parent.postMessage(loginData, "http://localhost:9099");
	}
}// end of function goLogin()

function loginWithKakao(){
	// $("div#loaderArr").show();
	
	const url = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${requestScope.kakaoApiKey}&redirect_uri=http://localhost:9099/Medinfora/${requestScope.RedirectUri}&prompt=login";
	const setting = "menubar=no,location=no,resizable=no,scrollbars=yes,status=no,top=100, left=100, width=600,height=600";
	
	window.open(url, 'url', setting);
	
	
}// end of function loginWithKakao()

function loginWithKakaoEnd(iskakao, message){
	
	// alert(iskakao);
	// javascript:location.reload(true)
	// 또는 회원가입 url
	
	$("div#loaderArr").hide();
	
	if(iskakao != null){
		const kakaoLogin = {"iskakao":iskakao, "message":message };
		
		window.parent.postMessage(kakaoLogin, "http://localhost:9099");
	}
	
}

function nokakaoregister(){
	$("div#loaderArr").hide();
}

function checkCapsLock(event)  {
	  if (event.getModifierState("CapsLock")) {
		  $(".msg_capslock").text('Caps Lock이 활성화된 상태입니다.');
		  $(".msg_capslock").show();
	  } else {
		  $(".msg_capslock").hide();
	  }
}

  
</script>

</head>
<body>

<div class="loginContainer">
    
	<form name="loginFrm">
	    <h2 class="nanum-eb size-n my-4">Log in</h2>
	    <div class="magin_info">
     		<input class="form-control nanum-b size-s rounded-pill login_insert" type="text" name="userid" placeholder="ID를 입력해주세요."  maxlength="20"/>
	    </div>
	    <div class="magin_info">
      		<input class="form-control nanum-b size-s rounded-pill login_insert" onkeyup="checkCapsLock(event)" name="pwd" type="password" placeholder="비밀번호를 입력해주세요." maxlength="18"/>
	    </div>
	    <div class="msg_capslock">Caps Lock이 활성화된 상태입니다.</div>
	    <button class="rounded-pill login_button nanum-b" type="button" onclick="goLogin()">로그인</button>
	    <div class="login_idpw_register mb-4">
      		<button class="nanum-n" type="button" onclick="javascript:window.parent.goregister();">회원가입</button><span class="lineG">
	      	</span><button class="nanum-n" type="button" onclick="javascript:window.parent.gofindId();">아이디 찾기</button><span class="lineG">
	      	</span><button class="nanum-n" type="button">비밀번호 찾기</button>
	    </div>
	</form>

  	<button class="rounded-pill" id="kakao_login_btn" type="button" onclick="loginWithKakao()">
	    <svg fill="none" height="30" viewBox="0 0 30 30" width="30" xmlns="http://www.w3.org/2000/svg">
	        <title>kakao 로고</title>
	        <path clip-rule="evenodd" d="M15 7C10.029 7 6 10.129 6 13.989C6 16.389 7.559 18.505 9.932 19.764L8.933 23.431C8.845 23.754 9.213 24.013 9.497 23.826L13.874 20.921C14.243 20.958 14.618 20.978 15 20.978C19.971 20.978 24 17.849 24 13.989C24 10.129 19.971 7 15 7Z" fill="black" fill-rule="evenodd"></path>
	    </svg>
	    <span class="size nanum-b">카카오 간편 로그인</span>
  	</button>
  	
  	
  	<%-- 로더  --%>
  	<div id="loaderArr">
		<div class="loader"></div>
	</div>

</div>

</body>
</html>