const contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));

let currentPage = 1; // 현재 페이지

$(function() {
    let totalPage = $('#totalPage').val();
    console.log("totalPage: " + totalPage);

    displayPagination(totalPage, currentPage);
});

function gowrite() {
	location.href= contextPath + "/commu/commuWrite.bibo";
}

function searchList() {

	let category = $("select[name='category']").val();
	let type = $("select[name='type']").val();
	let word = $("input[name='word']").val();

	/*
	frm.method = "post";
	frm.action = contextPath + "/commu/commuList.bibo";
	frm.submit();
	*/
	$.ajax({
    url: contextPath + "/commu/commuSearch.bibo",
    data: {category: category, type: type, word: word},
    dataType: "json",
    success: function (json) {
        console.log(JSON.stringify(json));
        let v_html = ``;
        if(json.length > 0) {
            json.forEach((item, index) => {
                v_html += `<div class="row text-center py-3 nanum-n size-s b_border list--item">
                                <input type="hidden" value="${totalPage}" id="totalPage"/>
                               <span class="col-2">${item.category}</span>
                               <span class="col-5" align="left">${item.title}`;
                if(item.fileTrue) {
                    v_html += `<i class="fa-solid fa-paperclip" style="color: #535965;"></i>`;
                }
                v_html += `</span>
                           <span class="col-2">${item.userid}</span>
                           <span class="col-2">${item.writeday}</span>
                           <span class="col-1">${item.viewcnt}</span>
                           </div>`;
            });
        } else {
            v_html += `<div class="nosearch">검색 결과가 없습니다.</div>`;
        }
        displayPagination(json[0].totalPage, currentPage);
        $("div#commuArea").html(v_html);
    },
    error: function(status, error) {
        console.error("AJAX 요청 실패: ", status, error);
    }
	});

}


function removedisplayPagination() {
    $('#rpageNumber').empty();
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
            $('#rpageNumber .page-link').removeClass('active');
            $(this).addClass('active');
        });
    }
}