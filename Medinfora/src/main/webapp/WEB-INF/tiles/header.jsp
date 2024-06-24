<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<header>
  <div class="header-container">
    <div class="logo">
      <img src="img/logo-main.png" alt="LOGO">
    </div>
    <button class="navbar-toggler" type="button" onclick="toggleMenu()">
      <span class="navbar-toggler-icon"></span>
    </button>
    <nav>
      <ul id="navbarNav">
        <li class="nanum-n size-s"><a class="dh_nav_item" href="#">의료 통계 보기</a></li>
        <li class="nanum-n size-s"><a class="dh_nav_item" href="#">건강 정보 보기</a></li>
        <li class="nanum-n size-s"><a class="dh_nav_item" href="#">진료 예약하기</a></li>
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
    <div class="login">
      <a id="loginModal" class="nanum-b size-s intarget">로그인</a>
    </div>
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