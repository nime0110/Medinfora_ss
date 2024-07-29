let currentPage = 1; // 현재 페이지
const contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));

$(function() {
    //let totalPage = $('#totalPage').val();
    //displayPagination(totalPage, currentPage);
		
    $("input:text[name='word']").bind("keyup", function(e){
        if(e.keyCode == 13){ // 엔터를 했을 경우
            goSearch();
        }
    });
    // 검색 조건 및 검색어 값 유지시키기

    let category = $("#category").val();
    let type = $("#type").val();
    let word = $("#word").val();

    console.log("category: " + category, "type: " + type, "word: " + word);
    if (category !== '0' || type !== 'z' || word !== '') {
        $("select[name='category']").val(category);
        $("select[name='type']").val(type);
        $("input[name='word']").val(word);
    }

    // 하나의 글 클릭시 
    $(document).on("click", ".list--item", function() {
        listOneClick($(this));
    });

    // 페이지네이션 클릭 이벤트
    /*
    $(document).on("click", ".page-link", function(e) {
        e.preventDefault();
        currentPage = $(this).data("page");
        searchList(currentPage);
    });
    */
    // 검색시 검색조건 및 검색어 값 유지시키기
});

function gowrite() {
	location.href= contextPath + "/commu/commuWrite.bibo";
}

	
function goSearch() {
    const frm = document.commuList;
    frm.submit(); 
}


/*
function searchList(currentPage) {
    let category = $("select[name='category']").val();
    let type = $("select[name='type']").val();
    let word = $("input[name='word']").val();

    $.ajax({
        url: contextPath + "/commu/commuSearch.bibo",
        data: {category: category, type: type, word: word, PageNo: currentPage}, // 여기를 수정
        dataType: "json",
        success: function(json) {
            console.log(JSON.stringify(json));
            let v_html = ``;
            if (json.length > 0) {
                json.forEach((item, index) => {
                    v_html += `<div class="row text-center py-3 nanum-n size-s b_border list--item">
                                   <input type="hidden" value="${item.cidx}" name="commuNo"/>
                                   <input type="hidden" value="${item.totalPage}" id="totalPage"/>
                                   <span class="col-2">${item.category}</span>
                                   <span class="col-5" align="left">${item.title}`;
                    if (item.fileTrue) {
                        v_html += `<i class="fa-solid fa-paperclip" style="color: #535965;"></i>`;
                    }
                    v_html += `</span>
                               <span class="col-2">${item.userid}</span>
                               <span class="col-2">${item.writeday}</span>
                               <span class="col-1">${item.viewcnt}</span>
                               </div>`;
                });
                displayPagination(json[0].totalPage, currentPage);
            } else {
                v_html += `<div class="nosearch">검색 결과가 없습니다.</div>`;
            }
            $("div#commuArea").html(v_html);
        },
        error: function(status, error) {
            console.error("AJAX 요청 실패: ", status, error);
        }
    });
}


function displayPagination(totalPage, currentPage) {
    let paginationDiv = $('#rpageNumber');
    paginationDiv.empty();

    let pageGroup = Math.ceil(currentPage / 5);
    let lastPage = pageGroup * 5;
    let firstPage = lastPage - 4;

    if (lastPage > totalPage) {
        lastPage = totalPage;
    }

    if (totalPage > 0) {
        // 이전 페이지 그룹으로 이동
        if (firstPage > 1) {
            paginationDiv.append('<span class="page-link" data-page="' + (firstPage - 1) + '">[이전]</span>');
        }

        for (let i = firstPage; i <= lastPage; i++) {
            let link = $('<span class="page-link"></span>').text(i).attr('data-page', i);
            if (i === currentPage) {
                link.addClass('active');
            }
            paginationDiv.append(link);
        }

        // 다음 페이지 그룹으로 이동
        if (lastPage < totalPage) {
            paginationDiv.append('<span class="page-link" data-page="' + (lastPage + 1) + '">[다음]</span>');
        }

        $('#rpageNumber .page-link').on('click', function(e) {
            e.preventDefault();
            let page = $(this).data('page');
            searchList(page);
            $('#rpageNumber .page-link').removeClass('active');
            $(this).addClass('active');
        });
    }
}

function removedisplayPagination() {
    $('#rpageNumber').empty();
}
*/
function listOneClick(obj) {
    let cidx = obj.find('input[name="commuNo"]').val();

    console.log("cidx: " + cidx);
    const frm = document.forms['searchFrm']; // 폼을 정확하게 가져옴

    // cidx 값 설정 확인
    frm.cidx.value = cidx;
    console.log("Form cidx value: " + frm.cidx.value);
    frm.method = "post";
    frm.action = contextPath + "/commu/commuView.bibo";
    frm.submit();





    //location.href = contextPath + "/commu/commuView.bibo?cidx=" + cidx;

}