$(function() {
    Page(1);

    $("input.inputsc").bind("keydown", function(e){
		if(e.keyCode == 13){ // 엔터를 했을 경우
			RESearch();
		}
	});

})      // end of $(function() {---------------

$(document).on('change','.sclist',(e) => {

    const sclist = $(e.target).val();

    if(sclist == "진료현황"){
        $("input.inputsc").attr('placeholder','접수신청, 접수완료, 진료완료 중 하나를 입력하세요.');
    }
    else if(sclist == "환자명"){
        $("input.inputsc").attr('placeholder','환자명을 입력해주세요.');
    }
    else{
        $("input.inputsc").val("");
        $("input.inputsc").attr('placeholder','검색어를 입력해주세요.');
        RESearch();
    }
    

})  // end of $(document).on('change','.search_ch',(e) => {---------------

///////////////////////////////////////////////////////////////

function RESearch(){

    const sclist = $(".sclist").val();
    const inputsc = $("input.inputsc").val().trim();

    if(sclist != "전체"){
        if(inputsc == ""){
            alert("검색 내용이 존재하지 않습니다.");
            return;
        }
    }
    if(sclist == "진료현황"){
        if(inputsc != "접수신청" && inputsc != "접수완료" && inputsc != "진료완료"){
            alert("접수신청, 접수완료, 진료완료 중 하나를 입력하세요.");
            return;
        }
    }
    
    if(inputsc != ""){
        if (!isNaN(inputsc)) {  // 입력한 값이 숫자인 경우
            const length = inputsc.length;
            if(length != 8){
                alert("진료예약일시나 예약신청일은 숫자만 8글자로 입력해주세요.");
                return;
            }
        }
    }
    Page(1);

}   // end of function RESearch(){------------------

function Page(currentShowPageNo){
    
    const sclist = $("select.sclist").val();
    const inputsc = $("input.inputsc").val();

    $.ajax({

        url: "mdreserveList.bibo"
        , data : {"sclist":sclist
            , "inputsc":inputsc
            , "currentShowPageNo":currentShowPageNo}
        , dataType:"json"
        , success:function(json){
            if(json.length > 0){
                let v_html = `<div>
                                <div class="mt-4 px-3 subject">
                                    <div class="row text-center py-3 nanum-eb size-s">
                                        <span class="col-2">진료예약일시</span>
                                        <span class="col-2">환자명</span>
                                        <span class="col-2">전화번호</span>
                                        <span class="col-2">예약신청일</span>
                                        <span class="col-2">진료현황</span>
                                        <span class="col-2">진료현황 변경</span>
                                    </div>
                                </div>
                                <div class="reserveBox mb-5 px-3">`;
                $.each(json, function(index, item){
                    v_html += `<div class="row text-center py-3 nanum-n size-s b_border">
						            <span class="col-2">${item.checkin}</span>
						            <span class="col-2">${item.name}</span>
						            <span class="col-2">${item.mobile}</span>
						            <span class="col-2">${item.reportday}</span>
						            <span class="col-2">
                                        <span class="rscode p-1 nanum-b">`;
                    if(item.rcode == "1"){
                        v_html +=  `접수 신청`;
                    }
                    if(item.rcode == "2"){
                        v_html +=  `접수 완료`;
                    }
                    if(item.rcode == "3"){
                        v_html +=  `진료 완료`;
                    }
                        v_html += `     </span>
                                    </span> 
                                    <span class="col-2">
                                        <button class="rschange nanum-eb size-s" type="button">변경</button>
                                    </span>
                                </div>`;
                })  // end of $.each(json, function(index, item){---------

                v_html += `</div></div>`
                $("div.reserveBox").html(v_html);

                /* === 페이지바 === */
				const blockSize = 10;
				let loop = 1;
				let pageNo = Math.floor(((currentShowPageNo - 1)/blockSize)) * blockSize + 1;
				let totalPage = json[0].totalPage;
				
				let pageBar = `<ul class='pagination hj_pagebar nanum-n size-s'>`;
				
				if(pageNo != 1) {
					pageBar += "<li class='page-item'>" 
							+ " 	<a class='page-link' href='javascript:Page("+(pageNo-1)+")>" 
							+ "	    	<span aria-hidden='true'>&laquo;</span>" 
							+ "	    </a>" 
							+ "</li>";
				}
				
				while(!(loop>blockSize || pageNo > totalPage)) {
					if(pageNo == currentShowPageNo) {
						pageBar += "<li class='page-item'>"
								+ "		<a class='page-link nowPage'>"+pageNo+"</a>" 
								+ "</li>";
					}
					else{
						pageBar += "<li class='page-item'>"
								+ "		<a class='page-link' href='javascript:Page("+pageNo+")'>" +pageNo+"</a>" 
								+ "</li>";
					}
					loop++;
					pageNo++;
				}
				
				if(pageNo <= totalPage) {
					pageBar += "<li class='page-item'>"
							+ "		<a class='page-link' href='javascript:Page("+pageNo+")'>"
							+ "	    	<span aria-hidden='true'>&raquo;</span>"
							+ "	    </a>"
							+ "</li>";
				}
				
				pageBar += "</ul>";
                $("div#ReserveHP_PageBar").html(pageBar);
            }
            else{
                v_html = `예약이 존재하지 않습니다.`;
                $("div.reserveBox").html(v_html);
                let pageBar = ``;
                $("div#ReserveHP_PageBar").html(pageBar);
            }   // end of if~else----------
        }
        , error:function(request,status,error){
			alert("code: "+request.status);
		}

    })  // end of $.ajax({--------------------------

}   // end of function Page(currentShowPageNo){-----------------