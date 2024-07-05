<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <% String ctxPath = request.getContextPath(); %>
 <link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/notice/notice.css" />
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/notice/noticeMedia.css" />
<script type="text/javascript" src="<%= ctxPath%>/resources/js/notice/notice.js"></script>
  

<body>
<script type="text/javascript">
    $(document).ready(function(){
        window.goView = function(seq){
            const goBackURL = "${goBackURL}";
            
            const frm = document.forms["goViewFrm"];
            frm.seq.value = seq;
            frm.goBackURL.value = goBackURL;
            
            frm.method = "post";
            frm.action = "<%= ctxPath %>/view_2.action";
            frm.submit();
        }
    });
</script>

<div class="board_title">
    <h2>글 내용 보기</h2>
    <span class="nanum-eb size-b">${noticedto.title}</span>
</div>
<div>${noticedto.viewcnt}</div>
<div class="board-item-date">
    <span class="board-item-date">${noticedto.writeday}</span>
</div>
<div class="content">
    <p>${noticedto.content}</p>
</div>

<button onclick="location.href='<%= ctxPath %>/notice/noticeList.bibo'">목록으로 돌아가기</button>
<button onclick="location.href='<%= ctxPath %>/notice/noticeEdit.bibo?seq=${noticedto.nidx}'">글 수정하기</button>
<button onclick="location.href='<%= ctxPath %>/notice/noticeDelete.bibo?seq=${noticedto.nidx}'">글 삭제하기</button>

<form name="goViewFrm" style="display:none;">
    <input type="hidden" name="seq" />
    <input type="hidden" name="goBackURL" />
</form>

</body>