
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
        url: "postlist.bibo",
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
                    v_html += `<div class="row text-center py-3 nanum-n size-s b_border list--item">
                                   <input type="hidden" value="${item.cidx}" name="commuNo"/>
                                   <input type="hidden" value="${item.totalPage}" id="totalPage"/>
                                   <p class="col-2">
                                        <span class="category-span">${item.category}</span>
                                    </p>
                                   <span class="col-5" align="left" onclick="listOneClick('${item.cidx}')">${item.title}`;
                    if (item.fileTrue) {
                        v_html += `<i class="fa-solid fa-paperclip" style="color: #535965;"></i>`;
                    }
                    v_html += `</span>
                               <span class="col-1">${item.userid}</span>
                               <span class="col-1">${item.writeday}</span>
                               <span class="col-1">${item.viewcnt}</span>
                               <p class="col-2">
                                    <button type="button" class="btn btn-secondary btn-sm mr-3" onclick="edit('${item.cidx}')">
                                       글수정하기
                                   </button>
                                   <button type="button" class="btn btn-secondary btn-sm" onclick="del('${item.cidx}')">삭제하기</button>
                              </p>
                              </div>`;
                });
                displayPagination(json[0].totalPage, currentPage);
            } else {
                v_html += `<div class="nosearch"> 글이 없습니다. </div>`;
                removedisplayPagination();
            }
            $("div#commuArea").html(v_html);
            let categoryarr = ['임신·성고민', '다이어트·헬스', '마음 건강', '탈모 톡톡', '피부 고민', '뼈와 관절', '영양제', '질환 고민', '자유게시판']; // 카테고리 배열

            $('.category-span').each(function() {
                let categoryView = $(this).text().trim(); // 현재 요소의 텍스트를 가져옴
                for (let i = 0; i < categoryarr.length; i++) {
                    if (categoryView === categoryarr[i]) { // 텍스트가 배열의 값과 일치하는지 확인
                        $(this).addClass('category' + i); // 일치하는 경우 클래스 추가
                    }
                }
            });
        
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

function listOneClick(cidx) {
    location.href = contextPath + "/commu/commuView.bibo?cidx=" + cidx;
}

//수정하기
function del(cidx) {
    if(confirm("글을 삭제하시겠습니까?")) {
        $.ajax({
            type: "post",
            url: contextPath + "/commu/del.bibo?cidx=" + cidx,
            data: {"cidx": cidx},
            dataType: "json",
            success: function (json) {
                alert("글이 삭제되었습니다.");
                location.reload();
            }
        });
    }
}
function edit(cidx) {
    location.href= contextPath + "/commu/commuEdit.bibo?cidx=" + cidx;
}