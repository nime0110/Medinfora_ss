const contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));

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
                v_html += `<div class="row text-center py-3 nanum-n size-s b_border">
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
        $("div#commuArea").html(v_html);
    },
    error: function(status, error) {
        console.error("AJAX 요청 실패: ", status, error);
    }
	});

}