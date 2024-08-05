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


    // URL 매개변수로부터 sort 값을 가져옴
    const urlParams = new URLSearchParams(window.location.search);


    $('#sort-button').on('click', function() {
        $('#sort-dropdown').toggle();
    });

    let sort;
    sort = $('#sort-dropdown a:first').data('sort');
    if(urlParams.has('sort')) {
        sort = urlParams.get('sort'); 
    } 

    $("input[name='sort']").val(sort);
    $('#sort-button span').text($('#sort-dropdown a[data-sort="' + sort + '"]').text());
    $('#sort-dropdown a').on('click', function(event) {
        event.preventDefault();
        
        // 선택된 정렬 옵션
        const sortType = $(this).data('sort');
        sort = sortType;
        // 정렬 옵션을 선택한 경우
        if (sortType !== '') {
            $('#sort-type').val(sortType);
            $('#sort-button span').text($(this).text());
        } 
        $("input[name='sort']").val(sort);
        // 드롭다운 메뉴를 닫음
        $('#sort-dropdown').hide();
        goSearch();
    });
    
    // 드롭다운 이외의 영역을 클릭하면 드롭다운 닫기
    $(document).on('click', function(event) {
        if (!$(event.target).closest('.sort-container').length) {
            $('#sort-dropdown').hide();
        }
    });




});

function gowrite() {
	location.href= contextPath + "/commu/commuWrite.bibo";
}

function goSearch() {
    const frm = document.commuList;
    frm.submit(); //commuList.bibo
}

function listOneClick(cidx, currentShowPageNo, sort, category, type, word) {
    location.href = contextPath + "/commu/commuView.bibo?cidx=" + cidx + "&currentShowPageNo=" + currentShowPageNo + "&sort=" + sort + "&category=" + category + "&type=" + type + "&word=" + word;
}