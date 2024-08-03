let currentPage = 1; // 현재 페이지
const contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));

$(function() {
    $("input:text[name='word']").bind("keyup", function(e){
        if(e.keyCode == 13){ // 엔터를 했을 경우
            goSearch();
        }
    });

    let category = $("#category").val();
    let type = $("#type").val();
    let word = $("#word").val();

    if (category !== '0' || type !== 'z' || word !== '') {
        $("select[name='category']").val(category);
        $("select[name='type']").val(type);
        $("input[name='word']").val(word);
    }

});

function gowrite() {
	location.href= contextPath + "/commu/commuWrite.bibo";
}

function goSearch() {
    const frm = document.commuList;
    frm.submit(); 
}

function listOneClick(cidx, currentShowPageNo, category, type, word) {
    location.href = contextPath + "/commu/commuView.bibo?cidx=" + cidx + "&currentShowPageNo=" + currentShowPageNo + "&category=" + category + "&type=" + type + "&word=" + word;
}