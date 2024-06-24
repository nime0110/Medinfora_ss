<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%String ctxPath = request.getContextPath();%>

<main>
  <section class="sim_promo_section">
    <div class="sim_promo_section_banner">
      <div class="sim_promo_section_banner_image">
        <!-- <img src=""> -->
        <h2 class="nanum-b size-n">사이트설명 이미지<br>홈페이지 소개글<br>1장</h2>
      </div>
    </div>
    <div class="sim_promo_section_services">
      <h3 class="nanum-b size-n">Quick Menu</h3>
      <div class="sim_promo_section_service_links">
        <a href="#">
          <div class="icon"><img src=""></div>
          <span class="nanum-b size-s">병원예약하기</span>
        </a>
        <a href="#">
          <div class="icon"><img src=""></div>
          <span class="nanum-b size-s">의원예약하기</span>
        </a>
        <a href="#">
          <div class="icon"><img src=""></div>
          <span class="nanum-b size-s">마이페이지</span>
        </a>
      </div>
      <div class="sim_promo_section_banner_notice">
        <div class="sim_promo_section_banner_notice_flexbox">
          <h1>🔔</h1>
          <h4 class="nanum-b size-n">알려드립니다</h4>
          <a href="#">더보기 →</a>
        </div>
        <ul class="nanum-n size-n">
          <li>팡고팡고<span>2022.09.29</span></li>
        </ul>
      </div>
    </div>
  </section> 
</main>
<!-- 성심 작업 영역 끝 -->
 
<!-- 승혜 작업 영역 시작 --> 
<!-- 승혜 작업 영역 시작 --> 
<div class="sh_section section_container_info" >
  <div class="subpage">
    <h1 class="title nanum-b size-b">Infora</h1>
    <div class="card-inner first">
      <a href="#" class="sh-card">
        <div class="sh_card-front">
          <img src="<%= ctxPath%>/resources/img/sh_hospital.png" alt="#">
          <h4>병원찾기</h4>
        </div>
        <div class="sh_card-text">
          <h4 class="card-title">병원찾기 <i class="bi bi-arrow-right"></i></h4>
          <p>
            주변 또는 찾고싶은 지역으로 병원을 검색할 수 있습니다.
          </p>
          <div class="img-content">
            <img src="<%= ctxPath%>/resources/img/sh_hospital.png" alt="#">
          </div>
        </div>
      </a>
      <a href="#" class="sh-card">
        <div class="sh_card-front">
          <img src="<%= ctxPath%>/resources/img/sh_medi.png" alt="#">
          <h4>약국 찾기</h4>
        </div>
        <div class="sh_card-text">
          <h4  class="card-title">약국 찾기 <i class="bi bi-arrow-right"></i></h4>
          <p>

            주변 또는 찾고 싶은 지역으로 약국을 검색할 수 있습니다
          </p>
          <div class="img-content">

            <img src="<%= ctxPath%>/resources/img/sh_medi.png" alt="#">
          </div>
        </div>
      </a>
      <a href="#" class="sh-card">
        <div class="sh_card-front">
          <img src="<%= ctxPath%>/resources/img/sh_emer.png" alt="#">
          <h4>응급실 찾기</h4>
        </div>
        <div class="sh_card-text">
          <h4 class="card-title">응급실 찾기 <i class="bi bi-arrow-right"></i></h4>
          <p>

            주변 또는 찾고 싶은 지역으로 검진기관을 검색할 수 있습니다
          </p>
          <div class="img-content">

            <img src="<%= ctxPath%>/resources/img/sh_emer.png" alt="#">
          </div>
        </div>
      </a>
      <a href="#" class="sh-card">
        <div class="sh_card-front">
          <img src="<%= ctxPath%>/resources/img/sh_findcl.png" alt="#">
          <h4>검진기관 찾기</h4>
        </div>
        <div class="sh_card-text">
          <h4 class="card-title">검진기관 찾기 <i class="bi bi-arrow-right"></i></h4>
          <p>

            주변 또는 찾고 싶은 지역으로 검진기관을 검색할 수 있습니다
          </p>
          <div class="img-content">

            <img src="<%= ctxPath%>/resources/img/sh_findcl.png" alt="#">
          </div>
        </div>
      </a>
    </div>
    <div class="card-inner first">
      <a href="#" class="sh-card">
        <div class="sh_card-front">
          <img src="<%= ctxPath%>/resources/img/sh_salemed.png" alt="#">
          <h4>구입 가능한 의약품</h4>
        </div>
        <div class="sh_card-text">
          <h4 class="card-title">구입 가능한 의약품 <i class="bi bi-arrow-right"></i></h4>
          <div class="img-content">

            <img src="<%= ctxPath%>/resources/img/sh_salemed.png" alt="#">
          </div>
        </div>
      </a>
      <a href="#" class="sh-card">
        <div class="sh_card-front">
          <img src="<%= ctxPath%>/resources/img/sh_eatmed.png" alt="#">
          <h4>의약품 복용법</h4>
        </div>
        <div class="sh_card-text">
          <h4 class="card-title">의약품 복용법 <i class="bi bi-arrow-right"></i></h4>
          <div class="img-content">

            <img src="<%= ctxPath%>/resources/img/sh_eatmed.png" style= "border-radius: 30px" alt="#">
          </div>
        </div>
      </a>
      <a href="#" class="sh-card">
        <div class="sh_card-front">
          <img src="<%= ctxPath%>/resources/img/sh_usemedi.png" alt="#">
          <h4>의약품 사용법</h4>
        </div>
        <div class="sh_card-text">
          <h4 class="card-title">의약품 사용법 <i class="bi bi-arrow-right"></i></h4>
          <div class="img-content">

            <img src="<%= ctxPath%>/resources/img/sh_usemedi.png" alt="#">
          </div>
        </div>
      </a>
      <a href="#" class="sh-card">
        <div class="sh_card-front">
          <img src="<%= ctxPath%>/resources/img/sh_tongae.png" alt="#">
          <h4>통계 자료</h4>
        </div>
        <div class="sh_card-text">
          <h4 class="card-title">통계 자료 <i class="bi bi-arrow-right"></i></h4>
          <div class="img-content">

            <img src="<%= ctxPath%>/resources/img/sh_tongae.png" alt="#">
          </div> 
        </div>
      </a>
    </div>  
    </div>
</div>
<!-- 승혜 작업 영역 끝 -->
 
<!-- 지훈 작업 영역 시작 -->
<div id="FAQ" class="mb-5 sizearr">
  <h2 class="nanum-eb size-n mb-4">FAQ & 묻고 답하기</h2>
    <div class="accordion" id="accordionExample">
      <div class="accordion-item">
          <h2 class="accordion-header" id="headingOne">
              <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                  <span class="nanum-b">Q1.</span>&nbsp;
                  <span class="nanum-n">첫번째 질문 내용이 들어갈 예정</span>
              </button>
          </h2>
          <div id="collapseOne" class="accordion-collapse collapse show" aria-labelledby="headingOne" data-bs-parent="#accordionExample">
              <div class="accordion-body">
                  <i class="fa-regular fa-hand-point-right"></i>
                  <span class="nanum-b">A.&nbsp;첫번째 질문에 대한 답변이 들어갈 예정</span>
              </div>
          </div>
      </div>
      <div class="accordion-item">
          <h2 class="accordion-header" id="headingTwo">
              <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                  <span class="nanum-b">Q2.</span>&nbsp;
                  <span class="nanum-n">두번째 질문 내용이 들어갈 예정</span>
              </button>
          </h2>
          <div id="collapseTwo" class="accordion-collapse collapse" aria-labelledby="headingTwo" data-bs-parent="#accordionExample">
              <div class="accordion-body">
                  <i class="fa-regular fa-hand-point-right"></i>
                  <span class="nanum-b">A.&nbsp;두번째 질문에 대한 답변이 들어갈 예정</span>
              </div>
          </div>
      </div>
      <div class="accordion-item">
          <h2 class="accordion-header" id="headingThree">
              <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                  <span class="nanum-b">Q3.</span>&nbsp;
                  <span class="nanum-n">세번째 질문 내용이 들어갈 예정</span>
              </button>
          </h2>
          <div id="collapseThree" class="accordion-collapse collapse" aria-labelledby="headingThree" data-bs-parent="#accordionExample">
              <div class="accordion-body">
                  <i class="fa-regular fa-hand-point-right"></i>
                  <span class="nanum-b">A.&nbsp;세번째 질문에 대한 답변이 들어갈 예정</span>
              </div>
          </div>
      </div>
    </div>
  <div class="lookupdiv">
    <h3 class="nanum-n nanum-b lookupfnq">더보기</h3>
  </div>
</div>
<!-- 지훈 작업 영역 끝 -->
<!-- 혜정 작업 영역 시작 -->
<session class="hj_section row my-5 py-3 justify-content-center sizearr">
  <div class="hj_section_notice col-lg-2 mb-3">
    <div>
      <h2 class="nanum-b hj_h2_flex">
        공지사항&nbsp;
        <a class="hj_notice_plusicon">
          <i class="fa-solid fa-plus hj_plusicon"></i>
        </a>
      </h2>
      <p class="nanum-n size-s">Mediinfora의&nbsp;
        <span>최근소식을</span>&nbsp;
        <span>전해드립니다.</span>
      </p>
    </div>
    <a href= "" class="hj_notice_plus nanum-n size-s">더 많은 소식 보기
        <i class="fa-solid fa-plus hj_plusicon"></i>
    </a>
  </div>
  <div class="hj_section_noticelist row row-cols-1 row-cols-md-3 col-lg-10">
    <div class="col hj_cardMb">
      <div class="hj_noticeitem card">
        <a href="">
          <div class="card-body">
            <h5 class="card-title nanum-b">공지사항 제목</h5>
            <p class="card-text hj_notice_content nanum-n">내용 샬라샬라~~</p>
            <span>2024.06.18</span>
          </div>
        </a>
      </div>
    </div>
    <div class="col hj_cardMb">
      <div class="hj_noticeitem card">
        <a href="">
          <div class="card-body">
            <h5 class="card-title nanum-b">공지사항 제목</h5>
            <p class="card-text hj_notice_content nanum-n">크기우짜노크기우짜노크기우짜노크기우짜노크기우짜노크기우짜노크기우짜노크기우짜노크기우짜노크기우짜노
              크기우짜노크기우짜노크기우짜노크기우짜노크기우짜노크기우짜노크기우짜노크기우짜노크기우짜노크기우짜노크기우짜노크기우짜노크기우짜노크기우짜노
              크기우짜노크기우짜노크기우짜노크기우짜노크기우짜노크기우짜노크기우짜노크기우짜노크기우짜노크기우짜노크기우짜노크기우짜노크기우짜노크기우짜노
              크기우짜노크기우짜노크기우짜노크기우짜노크기우짜노크기우짜노크기우짜노크기우짜노크기우짜노크기우짜노크기우짜노크기우짜노크기우짜노크기우짜노
            </p>
            <span>2024.06.18</span>
          </div>
        </a>
      </div>
    </div>
    <div class="col hj_cardMb">
      <div class="hj_noticeitem card">
        <a href="">
          <div class="card-body">
            <h5 class="card-title nanum-b">공지사항 제목</h5>
            <p class="card-text hj_notice_content nanum-n">This is a longer card with supporting text below as a natural lead-in to additional content. This content is a little bit longer.
              This is a longer card with supporting text below as a natural lead-in to additional content. This content is a little bit longer.
              This is a longer card with supporting text below as a natural lead-in to additional content. This content is a little bit longer.
            </p>
            <span>2024.06.18</span>
          </div>
        </a>
      </div>
    </div>
  </div>
</session>