<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String ctxPath = request.getContextPath(); %>

   <div id="info_sidebar">
       <div class="sidebar_title">
           <i class="fa-regular fa-compass"></i>&nbsp;메뉴
       </div>
       <hr class="sidebar_divideline">
       <div class="sidebar_list">
           <div class="sidebar_intro">
               <span class="sideber_username">사용자</span>&nbsp;님,<br>
               반갑습니다!
           </div>
           <div class="sidebar_list_title">
               일반 메뉴&nbsp;<i class="fa-solid fa-circle-info"></i>
           </div>
           <ul class="sidebar_list_ul">
               <li class="sidebar_list_li">내 정보수정</li>
               <li class="sidebar_list_li">내 검색내역</li>
               <li class="sidebar_list_li">회원 탈퇴</li>
           </ul>
           <div class="sidebar_list_title">
               회원 메뉴&nbsp;<i class="fa-solid fa-user"></i>
           </div>
           <ul class="sidebar_list_ul">
               <li class="sidebar_list_li">내 문의사항</li>
               <li class="sidebar_list_li">진료 예약 내역</li>
           </ul>
           <div class="sidebar_list_title">
               의료인 메뉴&nbsp;<i class="fa-solid fa-user-doctor"></i>
           </div>
           <ul class="sidebar_list_ul">
               <li class="sidebar_list_li">내 답변내용</li>
               <li class="sidebar_list_li">진료 예약 열람</li>
               <li class="sidebar_list_li">진료 일정 관리</li>
           </ul>
       </div>
       <div class="sidebar_list">
           <div class="sidebar_list_title">
               관리자 메뉴&nbsp;<i class="fa-solid fa-user-tie"></i>
           </div>
           <ul class="sidebar_list_ul">
               <li class="sidebar_list_li">회원관리</li>
               <li class="sidebar_list_li">공지사항 작성</li>
               <li class="sidebar_list_li">검색 통계</li>
           </ul>
       </div>
   </div>