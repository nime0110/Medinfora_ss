let currentPage = 1; // 현재 페이지
const contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));

$(function() {
  goViewComment(currentPage, function() {
    cmtmove();
  });
});
function cmtmove() {
  const hash = window.location.hash; 
  console.log("Hash:", hash);
  if (hash) {
    const cmtId = hash.substring(1); 
    const cmidx = cmtId.substring(4); 
    findPageByCmidx(cmidx);
    setTimeout(function() {
      const hashcmtId = $('#' + cmtId);
      if (hashcmtId.length) {
        console.log("Scroll to:", hashcmtId.offset().top);
        hashcmtId.addClass('highlight'); 
        $('html, body').animate({
          scrollTop: hashcmtId.offset().top
        }, 1000); 
        setTimeout(function() {
          hashcmtId.removeClass('highlight');
        }, 2000); 
      } else {
        console.log("hashcmtId가 존재하지 않습니다.");
      }
    }, 500);
  } else {
    console.log("URL 오류가 발생했습니다.");
  }
}

function findPageByCmidx(cmidx) {
  let cidx = $("#cidx").val();
  $.ajax({
    url: contextPath + '/commu/getCommentPage.bibo', 
    data: { "cidx": cidx, "cmidx": cmidx }, 
    dataType: 'json',
    success: function(json) {
      //console.log(JSON.stringify(json));
      const pageNo = json.pageNo; 
      goViewComment(pageNo);
    }
  });
}


function del(cidx) {
  let commentCount = $("#commentCount").val();

	if(confirm("글을 삭제하시겠습니까?")){
		$.ajax({
				url: contextPath + "/commu/del.bibo",
				type:"post",
				data:{"cidx":cidx, "commentCount":commentCount},
				dataType:"json",
				success:function(json){
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

function suggestionPost(userid, cidx) {

  if(userid == "") {
    alert("로그인이 필요한 서비스입니다.");
    return;
  }
  
  $.ajax({
    url: contextPath + "/commu/suggestionPost.bibo",
    data: {"userid":userid, "cidx":cidx},
    dataType:"json",
    success: function (json) {
      //console.log(JSON.stringify(json));
      if(json.alreadySuggestion == 1) {
        alert("이미 추천한 글입니다.");
      }
    },
    error: function(request, status, error){
        alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
    }
  });
}

function bookMark(userid, cidx) {

  if(userid == "") {
    alert("로그인이 필요한 서비스입니다.");
    return;
  }
  
  $.ajax({
    url: contextPath + "/commu/bookMark.bibo",
    data: {"userid":userid, "cidx":cidx},
    dataType:"json",
    success: function (json) {
      if(json.alreadyBookmark == 1) {
        if(confirm("북마크를 해제하시겠습니까?")) {
          delBookMark(userid, cidx);
        }
      } else {
        alert("북마크 되었습니다.");
        location.reload();
      }
    },
    error: function(request, status, error){
        alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
    }
  });
}

//북마크 해제 
function delBookMark(userid, cidx) {
  $.ajax({
    url: contextPath + "/commu/delBookMark.bibo",
    data: {"userid":userid, "cidx":cidx},
    dataType:"json",
    success: function (json) {
      if(json.n == 0) {
        alert("북마크 해제 실패");
      } else {
        location.reload();
      }
    },
    error: function(request, status, error){
        alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
    }
  });
}

/* 댓글쓰기 관련 start */
// 댓글쓰기 
function goAddWrite() {

  let cidx = $("#cidx").val();
  let userid = $("input:hidden[name='userid']").val();
  let content = $("textarea[name='content']").val();
  //마지막 페이지로 이동

  let currentShowPageNo = $(".pagination span:last-child").text();
  
  if(currentShowPageNo == "") {
    currentShowPageNo = 1;
  }
  if(content == "해당 댓글은 삭제되었습니다.") {
    alert("해당 내용으로 댓글 작성은 불가합니다.");
    $("textarea[name='content']").val("");
    return;
  }

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
          if(json.n == 0) {
            alert("댓글쓰기 실패");
          } else {
            goViewComment(currentShowPageNo);
          }
          $("textarea[name='content']").val(""); 
      },
      error: function(request, status, error){
          alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
      }
    });
} 

//대댓글쓰기
function goCommentPlusAdd(fk_userid, fk_cmidx, groupno, depthno) {

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
  $(document).on("click", ".cmt-plus-end", function(e) {
    
    if($(".recmt-content").val() == ""){
      alert("댓글을 입력해주세요.");
      return;
    }
  
    let cidx = $("#cidx").val();
    let userid = $("input:hidden[name='userid']").val();
    let content = $("textarea.recmt-content").val();
    
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
          "depthno" : depthno
              },
        dataType: "json",
        success: function (json) {
            if(json.n == 0) {
              alert("댓글쓰기 실패");
            } else {
              const currentShowPageNo = $(e.target).parent().parent().find("input.currentShowPageNo").val();
              goViewComment(currentShowPageNo);//페이징처리한댓글읽어오기
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
function goViewComment(currentPage, callback) {
  let cidx = $("#cidx").val();
  let loginuserid = $("#loginuserid").val();

  $.ajax({
      url: contextPath + "/commu/commentList.bibo",
      data: {"cidx": cidx, "pageNo": currentPage}, 
      dataType: "json",
      success: function(json) {
          let v_html = ``;
          if (json.length > 0) {
              json.forEach((item, index) => {
                v_html += `<li class="cmt-li cmt-li-${item.cmidx}" id="cmt-${item.cmidx}"> 
                            <div class="cmt-flexbox">`;
                
                if(item.depthno > 0){ 
                  v_html += `<div class="cmt-left">
                              <i class="fa-solid fa-reply cmt-rotate-icon"></i>
                            </div>`;
                }
                v_html += `<div class="cmt-right">`;

                v_html += `<div class="cmt-writecon">
                  <div class="cmt-writecon-left">
                  <a href="#popup_menu_area" class="member_0" onclick="return false">${item.userid}</a>
                  <span class="cmt-day">${item.writeday}</span>`;

                if(item.writeday !== item.updateday){
                  v_html += `<span class="cmt-day" style="color:blue;">마지막 수정 : ${item.updateday}</span>`;
                }
                v_html += `</div>
                          <div class="cmt-writecon-right">`;

                if(item.content !== "해당 댓글은 삭제되었습니다.") {
                    if(loginuserid === item.userid){
                      v_html += ` 
                      <button class="commu-button nanum-b cmt-updatebtn">수정</button>   
                      <input type='hidden' value='${item.cmidx}'/>
                      <button class="commu-button nanum-b cmt-deletebtn">삭제</button>`;
                    }

                    v_html += `<input type='hidden' value='${currentPage}' class='currentShowPageNo'/>`;
                    
                    if(loginuserid != "" && loginuserid != item.userid){
                      v_html += `<button class="commu-button nanum-b" onclick="goCommentPlusAdd('${item.userid}', '${item.cmidx}', '${item.groupno}', '${item.depthno}')" >답글</button>`;
                    }
                  }
                  v_html += `</div>
                              </div>
                              <div class="cmt-content">`;
                  if(item.fk_userid != "" && item.depthno > 1 && (item.fk_userid != item.userid)){ //자기자신한테 답댓글 안됨
                    v_html += `<span class="cmt-fkuserid">@${item.fk_userid}</span>`;
                  }

                  if(item.content == "해당 댓글은 삭제되었습니다.") {
                    v_html += `<p class="cmt-con-${item.cmidx} opacity-5">${item.content}</p>`;
                  } else {
                    v_html += `<p class="cmt-con-${item.cmidx}">
                              <a href="#cmt-li-${item.cmidx}" class="cmt-link">${item.content}</a></p>`;
                  }

                  v_html += `</div>
                            </div>
                            </div>
                            </li>`;
              });
              displayPagination(json[0].totalPage, currentPage);
          } else {
              v_html += ``;
          }
          $("#commentDisplay").html(v_html);
                // 콜백 함수가 있으면 호출
      if (typeof callback === 'function') {
        callback();
      }
      },
      error: function(status, error) {
          console.error("AJAX 요청 실패: ", status, error);
      }
  });
} //end of function goViewComment(currentShowPageNo)

//댓글 수정하기
let origin_comment_content = "";
$(document).on("click", $(".cmt-updatebtn"), function(e) {

  let cmidx = $(e.target).next().val();
  if($(e.target).text() == "수정") { //수정버튼 클릭시
    
    const content = $(".cmt-con-" + cmidx); 
    origin_comment_content = content.text(); //수정전 댓글내용

    content.html(`<textarea class="form-control" placeholder="댓글 내용을 입력하세요." id='comment_update${cmidx}' maxlength="150" ></textarea>`); //댓글내용을 수정할수 있도록 input태그를 만들어줌
    $("#comment_update"+cmidx).val(origin_comment_content); //수정전 댓글내용을 textarea에 넣어줌

    $(e.target).text("완료");
    $(e.target).next().next().text("취소");
    

  } else if($(e.target).text()=="완료") {
    const updatecontent = $("textarea#comment_update" + cmidx).val()//수정후 댓글내용
    
    let regex = /<[^>]*>/g;

    if (regex.test(updatecontent)) {
        alert('HTML 태그는 사용불가능합니다.');
        e.preventDefault();
    }

    const currentShowPageNo = $(e.target).parent().parent().find("input.currentShowPageNo").val();
    $.ajax({
      type: "post",
      url: contextPath + "/commu/commentUpdate.bibo",
      data: {"cmidx": cmidx, "content": updatecontent}, 
      dataType: "json",
      success: function(json) {
        $(e.target).text("수정").addClass("btn-secondary");
        $(e.target).next().next().text("삭제");
        goViewComment(currentShowPageNo);
      },
      error: function(status, error) {
          console.error("AJAX 요청 실패: ", status, error);
      }
    });
  }
});


//댓글 삭제하기
$(document).on("click", ".cmt-deletebtn", function(e) {
  let cmidx = $(e.target).prev().val();
  if($(e.target).text()=="취소") { 
    
    const content = $(".cmt-con-" + cmidx);
    content.html(origin_comment_content); // 원래 댓글 내용을 복원

    $(e.target).text("삭제");
    $(e.target).prev().prev().text("수정");

  }
  else if($(e.target).text()=="삭제") {
    if(confirm("댓글을 삭제하시겠습니까?")) {
      const currentShowPageNo = $(e.target).parent().parent().find("input.currentShowPageNo").val();
      $.ajax({
          type: "post",
          url: contextPath + "/commu/commentDelete.bibo",
          data: {"cmidx": cmidx}, 
          dataType: "json",
          success: function (json) {
            goViewComment(currentShowPageNo);//페이징처리한댓글읽어오기
          },
          error: function(request, status, error){
              alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
          }
        });
    }
  }
});

function displayPagination(totalPage, currentPage) {
  let paginationDiv = $('#rpageNumber');
  paginationDiv.empty();

  // 한 페이지 그룹에 표시할 페이지 수
  let pageSize = 5;  
  let pageGroup = Math.ceil(currentPage / pageSize);

  let lastPage = pageGroup * pageSize;  
  let firstPage = lastPage - (pageSize - 1);  

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
          if (i == currentPage) {
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


/* 댓글쓰기 관련 end */