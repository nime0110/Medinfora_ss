window.onload = () => {

    // 혜정 START

    //noticeContentSubstr();
    
    const noticeitem = $(".hj_noticeitem");
 
    noticeitem.on("mouseenter", function(){
        $(this).children().children().children("h5").addClass("pointerNoitice");
        $(this).addClass("hj_cardEvent");
    }).on("mouseleave", function(){
        $(this).children().children().children("h5").removeClass("pointerNoitice");
        $(this).removeClass("hj_cardEvent");
    });

    // 혜정 END

    // 동혁 START

    const navitem = $(".dh_nav_item");

    navitem.on("mouseenter", function(){

        const hover = $(this);

        hover.addClass("pickitem");

    }).on("mouseleave", function(){
        
        const hover = $(this);

        hover.removeClass("pickitem");

    })

    $('.dh-section-serachbar').on("focus",function(){

        $('.pop_search').removeClass("fadeout");

    });

    $('.dh-section-serachbar').on("blur",function(){

        $('.pop_search').addClass("fadeout");

    });

    const divlogin = $(".login");

    divlogin.on("mouseenter", function(){

        const hover = $(this).find(".intarget");

        hover.addClass("ahover");

    }).on("mouseleave", function(){
        
        const hover = $(this).find(".intarget");

        hover.removeClass("ahover");

    })
    
    // 동혁 END



    // 지훈 START
    // 모달 열기
    $("a#loginModal").click(function() {
        $("div#loginModalArr").fadeIn();
        $("iframe#loginPage").attr('src', "login_form.html");
    });

    // 모달 닫기
        $("span.jh_login_close").click(function() {
        $("div#loginModalArr").fadeOut();

    });

    // 모달 외부 클릭 시 닫기
    $(window).click(function(e) {
        if (e.target.id == "loginModalArr") {
            $("div#loginModalArr").fadeOut();
        }
    });




    // 지훈 START
    
};

/**
 * 반응형일때 네이게이션바 목록화 시켜주는 함수
 * @param {} 없음
 */
function toggleMenu() {

    var nav = document.getElementById('navbarNav');
    
    if (nav.classList.contains('show')) {
        nav.classList.remove('show');
    } else {
        nav.classList.add('show');
    }
}

// 누르면 사이트 이동
$(document).ready(function () {
    $(".sh-card").on("click", function () {
      var url = $(this).data("url");
      window.location.href = url;
    });
  });
  