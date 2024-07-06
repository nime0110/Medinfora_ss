<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <% String ctxPath = request.getContextPath(); %>
 <link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/notice/notice.css" />
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/notice/noticeMedia.css" />
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/header.css" />
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
<div class="notice-container">

<div class="board_title1">
    <span class="nanum-eb size-b">${noticedto.title}</span>
</div>

<div class="notice-info">
    <span class="notice-date">날짜: ${noticedto.writeday}</span>
	<span class="notice-viewcount">조회수: ${noticedto.viewcnt}</span>
	
	<div class="notice-attachment">
    <c:if test="${noticedto.filename != null}">
        <span class="attachment-label">첨부파일: </span>
        <span class="attachment-filename">${noticedto.filename}</span>
    </c:if>
</div>

<hr>
</div>



<div class="nanum-n notice-content">
    <p>${noticedto.content}</p>
</div>

<%-- <div class="notice-button text-center mb-5">
<button type="button" class="notice-button01 btn btn-lg mr-5"onclick="location.href='<%= ctxPath %>/notice/noticeList.bibo'">목록으로 돌아가기</button>
<button type="button" class="notice-button02 btn btn-lg" onclick="location.href='<%= ctxPath %>/notice/noticeEdit.bibo?seq=${noticedto.nidx}'">글 수정하기</button>
<button type="button" class="notice-button03 btn" onclick="location.href='<%= ctxPath %>/notice/noticeDel.bibo?seq=${noticedto.nidx}'">글 삭제하기</button>
</div> 
--%>

<div class="notice-button text-center mb-5">
    <button type="button" class="notice-button01 btn btn-lg mr-5" onclick="location.href='<%= ctxPath %>/notice/noticeList.bibo'">목록으로 돌아가기</button>
    <c:if test="${sessionScope.loginuser.mIdx==0}">
        <button type="button" class="notice-button02 btn btn-lg" onclick="location.href='<%= ctxPath %>/notice/noticeEdit.bibo?seq=${noticedto.nidx}'">글 수정하기</button>
        <button type="button" class="notice-button03 btn" onclick="location.href='<%= ctxPath %>/notice/noticeDel.bibo?seq=${noticedto.nidx}'">글 삭제하기</button>
    </c:if>
</div>
<form name="goViewFrm" style="display:none;">
    <input type="hidden" name="seq" />
    <input type="hidden" name="goBackURL" />
</form>
</div>
</body>