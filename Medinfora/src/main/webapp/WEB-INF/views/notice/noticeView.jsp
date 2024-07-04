<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <% String ctxPath = request.getContextPath(); %>
 <link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/notice/notice.css" />
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/notice/noticeMedia.css" />
<script type="text/javascript" src="<%= ctxPath%>/resources/js/notice/notice.js"></script>
  
<body>
<script type="text/javascript">

	$(document).ready(function(){
	
		function goView(seq){
			const goBackURL = "${requestScope.goBackURL}";
			
			const frm = document.goViewFrm;
			frm.seq.value = seq;
			frm.goBackURL.Value=goBackURL;
			
			frm.method = "post";
			frm.action = "<%=ctxPath%>/view_2.action";
			frm.submit();
		}
	})
	


</script>
	 <div class="board_title">
	 	<h2> 글 내용 보기 </h2>
	      <span class="nanum-eb size-b">여기에 글이 들어올 예정입니다 </span>
	  </div>
	 <div class="board-item-datee">
	 
	    <span class="board-item-date">2024.04.30</span>
	 </div> 
	 
	 <div class="content">
            <p>#안녕하세요. 토스페이먼츠입니다.</p>
            <p>‘24년 6월 3일부터 아래와 같이 개정된 개인정보 처리방침이 시행될 예정입니다.</p>
      </div>
      
<button onclick="">목록으로 돌아가기</button>
	 
<button onclick="">글 수정하기</button>

<button onclick="">글 삭제하기</button>
     
</body>