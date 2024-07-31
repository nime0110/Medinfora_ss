$(document).ready(function(){
    page(1);

});// end of $(function(){ -----------------------


function page(currentPageNo){
    const subject = $("select[name='subject']").val();
    const type = $("select[name='type']").val();
    const word = $("input:text[name='word']").val().trim();

    $.ajax({
        url:"myQnA.bibo",
        data:{"subject":subject
             ,"type":type
             ,"word":word
             ,"currentPageNo":currentPageNo},
        dataType:"json",
        success:function(json){
            // console.log(JSON.stringify(json));

            let questionArea = ``;

            if(json.qdtoMap.qList.length > 0){

                $.each(json.qdtoMap.qList, function(index, item){
                    questionArea += `<div class="row text-center py-3 nanum-n size-s b_border" id="oneqdto">
                                        <input type="hidden" value="${item.qidx}" name="qidx"/>
                                        <input type="hidden" value="${item.userid}"/>
                                        <span class="col-2">`;
                    
                    if(item.subject == "1"){
                        questionArea += `건강상담`;
                    }
                    else if(item.subject == "2"){
                        questionArea += `식생활,식습관`;
                    }
                    else{
                        questionArea += `의약정보`;
                    }

                    questionArea += `</span>
                                        <span class="col-6" style="text-align:left;">${item.title}&nbsp;`;

                    if(item.imgsrc.trim() != ""){
                        questionArea += `<i class="fa-solid fa-paperclip" style="color: #535965;"></i>&nbsp;`;
                    }
                    
                    if(item.newwrite == "0"){
                        questionArea += `&nbsp;&nbsp;<i class="fa-solid fa-n fa-sm" style="color: #ffa34d;"></i>`;
                    }

                    questionArea += `&nbsp;</span>
								 <span class="col-2">`;
								 
                    if(item.acount == 0){
                        questionArea += `<span class="p-1 nanum-b" style="background-color: #f1bd81; border-radius: 10%; color: white;">
                                            답변&nbsp;중
                                        </span>`;
                    }
                    else if (item.acount != 0){
                        questionArea += `<span class="p-1 nanum-b" style="background-color: blue; border-radius: 10%; color: white;">
                                            답변완료
                                        </span>`;
                    }
                    
                    questionArea += `	</span>
                                        <span class="col-2">${item.writeday}</span>
                                    </div>`;

                });// end of $.each();

                // 질문리스트 넣어줌
                $("div#questionArea").html(questionArea);


                // 페이지바
                const blockSize = 10;
				let loop = 1;
				let pageNo = Math.floor(((currentPageNo - 1)/blockSize)) * blockSize + 1;
				let totalPage = json.qdtoMap.totalPage;

                let pageBar = `<ul class='pagination hj_pagebar nanum-n size-s'>`;
				
				if(pageNo != 1) {
					pageBar += "<li class='page-item'>" 
							+ " 	<a class='page-link' href='javascript:Page("+(pageNo-1)+")>" 
							+ "	    	<span aria-hidden='true'>&laquo;</span>" 
							+ "	    </a>" 
							+ "</li>";
				}
				
				while(!(loop>blockSize || pageNo > totalPage)) {
					if(pageNo == currentPageNo) {
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

                $("div.pagebar").html(pageBar);


                $("div#oneqdto").click(function(e){
                    const qidx = $(this).find("input:hidden[name='qidx']").val();
                    goView(qidx);
                });


            }// end of if 검색결과 있는 경우
            else{
                questionArea += `<div class="my-3 mx-2 nanum-n">문의하신 사항이 없습니다.</div>`;
                $("div#questionArea").html(questionArea);
                $("div.pagebar").html('');
            }

        },
        error: function(request, status, error){
			alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		}


    });




}