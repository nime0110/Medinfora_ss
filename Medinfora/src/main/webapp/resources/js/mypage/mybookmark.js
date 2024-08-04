let currentPage = 1; // 현재 페이지
const contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));

$(function() {
    searchList(1);
    $("input:text[name='word']").bind("keyup", function(e){
        if(e.keyCode == 13){ // 엔터를 했을 경우
            e.preventDefault(); 
            searchList(1);
        }
    });
    $("form").on("submit", function(e) {
        e.preventDefault(); 
    });
});

function searchList(currentPage) {
    let category = $("select[name='category']").val();
    let type = $("select[name='type']").val();
    let word = $("input[name='word']").val().trim();
    console.log("category: " + category, "type: " + type, "word: " + word);
    $.ajax({
        url: "bookmarklist.bibo",
        data: {"category": category, 
                "type": type, 
                "word": word, 
                "currentShowPageNo": currentPage
                }, 
        dataType: "json",
        success: function(json) {
            console.log(JSON.stringify(json));
            let v_html = ``;
            if (json.length > 0) {
                json.forEach((item, index) => {
                    v_html += `<div class="row text-center py-3 nanum-n size-s b_border list--item" onclick="listOneClick('${item.cidx}')">
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
                removedisplayPagination();
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
  
    // 한 페이지 그룹에 표시할 페이지 수
    let pageSize = 5;  
    let pageGroup = Math.ceil(currentPage / pageSize);
  
    let lastPage = pageGroup * pageSize;  
    let firstPage = lastPage - (pageSize - 1);  
  
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
            if (i == currentPage) {
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
            goViewComment(page);
            $('#rpageNumber .page-link').removeClass('active');
            $(this).addClass('active');
        });
    }
  }
function removedisplayPagination() {
    $('#rpageNumber').empty();
}


function listOneClick(cidx) {
    location.href = contextPath + "/commu/commuView.bibo?cidx=" + cidx;
}