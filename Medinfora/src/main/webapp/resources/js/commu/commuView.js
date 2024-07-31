let currentPage = 1; // 현재 페이지
const contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));

$(function() {
 
});


function del(cidx) {
  console.log("cidx : " + cidx);
	if(confirm("글을 삭제하시겠습니까?")){
		$.ajax({
				url: contextPath + "/commu/del.bibo",
				type:"post",
				data:{"cidx":cidx},
				dataType:"json",
				success:function(json){
						console.log(JSON.stringify(json));

						if(json.result == '1'){
								alert("글이 삭제되었습니다.");
								location.href = contextPath + "/commu/commuList.bibo";
						} else {
              alert("글 삭제에 실패했습니다.");
            }

				},
				error: function(request, status, error){
						alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
				}

      });
  } else{
      return;
  }
}