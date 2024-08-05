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

    let categoryarr = ['임신·성고민', '다이어트·헬스', '마음 건강', '탈모 톡톡', '피부 고민', '뼈와 관절', '영양제', '질환 고민', '자유게시판']; // 카테고리 배열

    $('.category-span').each(function() {
        let categoryView = $(this).text().trim(); // 현재 요소의 텍스트를 가져옴
        for (let i = 0; i < categoryarr.length; i++) {
            if (categoryView === categoryarr[i]) { // 텍스트가 배열의 값과 일치하는지 확인
                $(this).addClass('category' + i); // 일치하는 경우 클래스 추가
            }
        }
    });
    



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