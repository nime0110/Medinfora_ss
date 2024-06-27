<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%String ctxPath = request.getContextPath();%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">

	$(document).ready(function(){
		
		// 로그인창 열기
		$("a#loginModal").click(function(){
			$("div#loginModalArr").fadeIn();
		 	$("iframe#loginPage").attr('src', '<%=ctxPath %>/login/login.bibo');
		});
		
		// 로그인창 닫기
		$("span.jh_login_close").click(function(){
			$("div#loginModalArr").fadeOut();
		});
		
		// 창 외부 클릭 시 로그인창 닫기
	 	$(window).click(function(e) {
       		if (e.target.id == "loginModalArr") {
            	$("div#loginModalArr").fadeOut();
	        }
    	});
		
		
		$(window).on("message", function(e){
			 const loginData = e.originalEvent.data;
			 
			 const userid = loginData.userid;
			 const pwd = loginData.pwd;
			 
			 // console.log(userid);
			 // console.log(pwd);
			 
			 if(userid != null && pwd != null ){
				 
				 $.ajax({
					url:"<%=ctxPath%>/login/loginEnd.bibo",
					type:"post",
					data:{"userid":userid
						 ,"pwd":pwd},
					dataType:"json",
					success:function(json){
						
						if(json.isExistUser == "true"){
							if(Number(json.pwdchangegap) >= 3){
								alert("비밀번호를 변경하신지 3개월이 지났습니다.");
								// 변경하는 페이지로 이동 만들어야 함
							}
							else{
								alert(json.message);
								location.href="javascript:location.reload(true)";
							}
							
						}
						else if(json.isExistUser == "freeze"){
							alert(json.message);
							// 휴면 해제하는 페이지로 이동 만들어야함
						}
						else if(json.isExistUser == "suspended"){
							alert(json.message);
						}
						else{ // 로그인 실패
							alert(json.message);
						}
					},
					error: function(request, status, error){
			            alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			     	}
					
				 });
				 
			 }
			 
			 
		});
		
	});

</script>

<link rel="stylesheet" href="<%=ctxPath %>/resources/css/header.css">

<div id="getCtxPath" style="display: none;"><%=ctxPath %></div>

<header>
  <div class="header-container">
    <div class="logo" id="logoimport">
      <img src="<%= ctxPath%>/resources/img/logo-main.png" alt="LOGO">
    </div>
    <button class="navbar-toggler" type="button" onclick="toggleMenu()">
      <span class="navbar-toggler-icon"></span>
    </button>
    <nav>
      <ul id="navbarNav">
        <li class="nanum-n size-s"><a class="dh_nav_item" href="<%=ctxPath%>/statistics/medistatistic.bibo">의료 통계 보기</a></li>
        <li class="nanum-n size-s"><a class="dh_nav_item" href="#">건강 정보 보기</a></li>
        <li class="nanum-n size-s"><a class="dh_nav_item" href="<%=ctxPath%>/reserve/choiceDr.bibo">진료 예약하기</a></li>
      </ul>
    </nav>
    <div class="input_text">
      <label class="my-auto">
        <form class="dh-section-form" name="serachFrm">
          <input class="dh-section-serachbar my-sm-0 nanum-b" type="text" name="store_search" id="store_search" placeholder='검색어를 입력하세요' required="required">
        </form>
      </label>
      <span class="DH-section-searchBtn" id="btnSearch"><i class="DH-section-searchBtni fa-solid fa-magnifying-glass"></i>
      </span>
    </div>
    
    <%-- 로그인 여부에 따라 버튼 내용 달라짐 --%>
    <c:if test="${empty sessionScope.loginuser}">
	    <div class="login">
	      <a id="loginModal" class="nanum-b size-s intarget">로그인</a>
	    </div>
    </c:if>
    <%-- 로그아웃 추가해야함 --%>
    <c:if test="${not empty sessionScope.loginuser}">
	    <div class="login">
	      <a href="<%=ctxPath%>/login/logout.bibo" class="nanum-b size-s intarget">로그아웃</a>
	    </div>
    </c:if>
    
  </div>
  <div class="pop_search fadeout">
    <div class="pop_title nanum-n">인기검색어</div>
    <ul class="pop_ul_dh">
      <li>1.&nbsp;<p class="nanum-n">오늘뭐먹지</p></li>
      <li>2.&nbsp;<p class="nanum-n">오늘뭐먹지</p></li>
      <li>3.&nbsp;<p class="nanum-n">오늘뭐먹지</p></li>
      <li>4.&nbsp;<p class="nanum-n">오늘뭐먹지</p></li>
      <li>5.&nbsp;<p class="nanum-n">오늘뭐먹지</p></li>
    </ul>
  </div>
</header>

<aside class="fixed-nav">
  <ul>
    <li>
      <div class="icon"></div>
      <a href="#" class="nanum-b size-s">내 정보</a>
    </li>
    <li>
      <div class="icon"></div>
      <a href="#"  class="nanum-b size-s">Q&A</a>
    </li>
    <li>
      <div class="icon"></div>
      <a href="#"  class="nanum-b size-n">이용안내</a>
    </li>
    <li>
      <div class="icon"></div>
      <a href="#" class="nanum-b size-n">고객센터</a>
    </li>
  </ul>
</aside>


<%-- 로그인 모달 추가 시작 --%>
<c:if test="${empty sessionScope.loginuser}">
	<div id="loginModalArr" class="jh_login_modal">
	  <div class="jh_modal_content rounded-5">
	    <span class="jh_login_close">&times;</span>
	    <iframe id="loginPage" src="<%=ctxPath %>/login/login.bibo" frameborder="0"></iframe>
	  </div>
	</div>
</c:if>
<%-- 로그인 모달 추가 끝 --%>