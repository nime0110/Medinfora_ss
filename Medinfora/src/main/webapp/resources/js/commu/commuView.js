let currentPage = 1; // 현재 페이지
const contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));

$(function() {
  goViewComment(currentPage);
});


function del(cidx) {
  console.log("cidx : " + cidx);
  let commentCount = $("#commentCount").val();
  console.log("commentCount : " + commentCount);

	if(confirm("글을 삭제하시겠습니까?")){
		$.ajax({
				url: contextPath + "/commu/del.bibo",
				type:"post",
				data:{"cidx":cidx, "commentCount":commentCount},
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

// 댓글쓰기 
function goAddWrite() {

  let cidx = $("#cidx").val();
  let userid = $("input:hidden[name='userid']").val();
  let content = $("textarea[name='content']").val();


  $.ajax({
      type: "post",
      url: contextPath + "/commu/addComment.bibo",
      data: {
        "cidx": cidx,
        "userid": userid,
        "content": content
             },
      dataType: "json",
      success: function (json) {
          //console.log(JSON.stringify(json));// {"n":1, "name":"엄정화} 또는 {"n":0, "name":"엄정화} 
          if(json.n == 0) {
            alert("댓글쓰기 실패");
          } else {
            //goReadComment(); // 페이징 처리 안 한 댓글 읽어오기
            goViewComment(1);// 페이징 처리한 댓글 읽어오기
          }
          
          $("textarea[name='content']").val(""); 
      },
      error: function(request, status, error){
          alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
      }
    });
} //end of function goAddWrite_noAttach() ------------


//대댓글쓰기
function goCommentPlusAdd(fk_userid, fk_cmidx, groupno, depthno) {

  //콘텐츠 생성
  let v_html = `<div class="recmt-write">
                  <textarea class="recmt-content" placeholder="댓글을 입력해주세요."></textarea>
                  <button class="commu-button nanum-b cmt-plus-end" >등록</button>
                </div>`;
  
  //대댓글 입력창이 열려있는지 확인
  if($(".recmt-write").length > 0){
    $(".recmt-write").remove();
  } else {
    $("#commentDisplay .cmt-li-"+fk_cmidx).append(v_html);
  }

  
  
  
  //버튼 클릭시 대댓글 등록
  $(document).on("click", ".cmt-plus-end", function() {
    
    //댓글 입력 안했을 경우 입력하라는 알림
    if($(".recmt-content").val() == ""){
      alert("댓글을 입력해주세요.");
      return;
    }
    let cidx = $("#cidx").val();
    let userid = $("input:hidden[name='userid']").val();

    let content = $("textarea.recmt-content").val();
    console.log("대댓글 content : " + content);
    
    $.ajax({
        type: "post",
        url: contextPath + "/commu/addComment.bibo",
        data: {
          "cidx": cidx,
          "userid": userid,
          "content": content,
          "fk_userid" : fk_userid,
          "fk_cmidx" : fk_cmidx,
          "groupno" : groupno,
          "depthno" : depthno,
              },
        dataType: "json",
        success: function (json) {
            //console.log(JSON.stringify(json));// {"n":1, "name":"엄정화} 또는 {"n":0, "name":"엄정화} 
            if(json.n == 0) {
              alert("댓글쓰기 실패");
            } else {
              //goReadComment(); // 페이징 처리 안 한 댓글 읽어오기
              goViewComment(1);// 페이징 처리한 댓글 읽어오기
            }
            
            $("textarea.recmt-content").val(""); 
            $("#comment-write").remove();
        },
        error: function(request, status, error){
            alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
        }
      });
  });

} 









//댓글보기
function goViewComment(currentPage) {
  let cidx = $("#cidx").val();
  let loginuserid = $("#loginuserid").val();

  
  console.log(currentPage);
  console.log("cidx : " + cidx);
  $.ajax({
      url: contextPath + "/commu/commentList.bibo",
      data: {"cidx": cidx, "pageNo": currentPage}, 
      dataType: "json",
      success: function(json) {
          console.log(JSON.stringify(json));
          let v_html = ``;
          if (json.length > 0) {
              json.forEach((item, index) => {
                v_html += `<li class="cmt-li cmt-li-${item.cmidx}"> 
                            <div class="cmt-flexbox">`;
                
                if(item.depthno > 0){ 
                    // depthno 가 0이 아닌 경우 답글이므로 들여쓰기
                    v_html += `<div class="cmt-left">
                                <i class="fa-solid fa-reply cmt-rotate-icon"></i>
                              </div>`;
                }
                  v_html += `<div class="cmt-right">`;

                  v_html += `<div class="cmt-writecon">
                  <div class="cmt-writecon-left">
                    <a href="#popup_menu_area" class="member_0" onclick="return false">${item.userid}</a>
                    <span class="cmt-day">${item.writeday}</span>`;

                  if(item.writeday != item.updateday){
                    v_html += `<span class="cmt-day" style="color:blue;">마지막 수정 : ${item.updateday}</span>`;
                  }
                  v_html += `
                            </div>
                            <div class="cmt-writecon-right">`;
                  if(loginuserid === item.userid){
                    v_html += ` 
                    <button class="commu-button nanum-b">수정</button>   
                    <button class="commu-button nanum-b">삭제</button>`;
                  }
                  //로그인한 사람만
                  if(loginuserid != "" && loginuserid != item.userid){
                  v_html += `<button class="commu-button nanum-b" onclick="goCommentPlusAdd('${item.userid}', '${item.cmidx}', '${item.groupno}', '${item.depthno}')" >답글</button>`;
                  }

                  v_html += ` </div>
                              </div>
                              <div class="cmt-content">  					
                                <p>`;
                  if(item.fk_userid != "" && item.depthno > 1 && (item.fk_userid != item.userid)){ //자기자신한테 답댓글 안됨
                    v_html += `<span class="cmt-fkuserid">@${item.fk_userid}</span>`;
                  }
                              
                  v_html +=  `${item.content}</p>
                              </div>
                            </div>
                            </div>
                            </li>`;
              });
              displayPagination(json[0].totalPage, currentPage);
          } else {
              v_html += ``;
          }
          $("#commentDisplay").html(v_html);
      },
      error: function(status, error) {
          console.error("AJAX 요청 실패: ", status, error);
      }
  });
} //end of function goViewComment(currentShowPageNo)

  
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
          goViewComment(page);
          $('#rpageNumber .page-link').removeClass('active');
          $(this).addClass('active');
      });
  }
}

function removedisplayPagination() {
  $('#rpageNumber').empty();
}