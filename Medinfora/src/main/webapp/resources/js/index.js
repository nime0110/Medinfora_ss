window.onload = () => {

    const ctxPath = $("#getCtxPath").text();

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
        tgremoveclass();

    });

    $('.dh-section-serachbar').on("click",function(){

        $('.pop_search').removeClass("fadeout");
        tgremoveclass();

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

    $("#logoimport").on("click",function(){

        location.href = `${ctxPath}/index.bibo`;

    });

    const tg1m = $('.tg1m');
    const tg1 = $('.tg1');

    const tg2m = $('.tg2m');
    const tg2 = $('.tg2');

    tg1m.on("mouseenter",function(){

        $('.pop_search').addClass("fadeout");
        tg2.addClass("fadeout");
        tg1.removeClass("fadeout");

    });

    tg2m.on("mouseenter",function(){

        $('.pop_search').addClass("fadeout");
        tg1.addClass("fadeout");
        tg2.removeClass("fadeout");

    });

    const head_container = $(".mainheader");

    head_container.on("mouseleave",function(){
        tgremoveclass();
    })
    
    // 동혁 END

    
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
jQuery(function() {
    $(".sh-card").on("click", function () {
        var url = $(this).data("url");
        window.location.href = url;
    });
})

function tgremoveclass(){
    $('.tg1').addClass("fadeout");
    $('.tg2').addClass("fadeout");
}