<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% String ctxPath = request.getContextPath(); %>

   <div id="info_sidebar">
       <div class="sidebar_title">
           <i class="fa-regular fa-compass"></i>&nbsp;메뉴
       </div>
       <hr class="sidebar_divideline">
       <div class="sidebar_list">
           <div class="sidebar_intro">
           	   <c:if test="${sessionScope.loginuser.mIdx == 0}">
               		<span class="sideber_username">관리자</span>&nbsp;님,<br>
           	   </c:if>
           	   <c:if test="${sessionScope.loginuser.mIdx != 0}">
               		<span class="sideber_username">${sessionScope.loginuser.name}</span>&nbsp;님,<br>
           	   </c:if>
				반갑습니다!
           </div>
           
           <div class="sidebar_list_title">
               일반 메뉴&nbsp;<i class="fa-solid fa-circle-info"></i>
           </div>
           <ul class="sidebar_list_ul">
               <li class="sidebar_list_li" onclick="location.href='<%=ctxPath%>/mypage/myinfo.bibo'">내 정보수정</li>
               <li class="sidebar_list_li">내 검색내역</li>
               <li class="sidebar_list_li">회원 탈퇴</li>
           </ul>
           
           <c:if test="${sessionScope.loginuser.mIdx==1}">
	           <div class="sidebar_list_title">
	               회원 메뉴&nbsp;<i class="fa-solid fa-user"></i>
	           </div>
	           <ul class="sidebar_list_ul">
	               <li class="sidebar_list_li">내 문의사항</li>
	               <li class="sidebar_list_li" onclick="location.href='<%=ctxPath%>/mypage/myreserve.bibo'">진료 예약 내역</li>
	           </ul>
           </c:if>
           
           <c:if test="${sessionScope.loginuser.mIdx==2}">
           <div class="sidebar_list_title">
               의료인 메뉴&nbsp;<i class="fa-solid fa-user-doctor"></i>
           </div>
           <ul class="sidebar_list_ul">
               <li class="sidebar_list_li">내 답변내용</li>
               <li class="sidebar_list_li" onclick="location.href='<%=ctxPath%>/mypage/mdreserve.bibo'">진료 예약 열람</li>
               <li class="sidebar_list_li">진료 일정 관리</li>
           </ul>
           </c:if>
           
           <c:if test="${sessionScope.loginuser.mIdx==0}">
           <div class="sidebar_list_title">
               관리자 메뉴&nbsp;<i class="fa-solid fa-user-tie"></i>
           </div>
           <ul class="sidebar_list_ul">
               <li class="sidebar_list_li">회원관리</li>
               <li class="sidebar_list_li" onclick="location.href='<%=ctxPath%>/notice/noticeWrite.bibo'">공지사항 작성</li>
               <li class="sidebar_list_li">검색 통계</li>
           </ul>
           </c:if>
           
       </div>
   </div>