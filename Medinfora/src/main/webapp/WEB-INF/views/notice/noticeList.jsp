<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String ctxPath = request.getContextPath();
%>
<link rel="stylesheet" type="text/css" href="<%=ctxPath%>/resources/css/notice/notice.css" />
<link rel="stylesheet" type="text/css" href="<%=ctxPath%>/resources/css/notice/noticeMedia.css" />
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/header.css" /> 
<link rel="stylesheet" type="text/css" href="<%=ctxPath%>/resources/css/footer.css" />
<script type="text/javascript"
	src="<%=ctxPath%>/resources/js/notice/notice.js"></script>

<script type="text/javascript">
function click_on(nidx){
    $("input[name='nidx']").val(nidx);
    const frm = document.goViewFrm;
    frm.method = "get";
    frm.action = "<%=ctxPath%>/notice/view.bibo";
		frm.submit();
	}
</script>

<body>
	<div class="board_title">
		<div class="notice-btn1 nanum-eb mb-5">
			<div class="notice0000">
				<p class="nanum-eb size-b">공지사항</p>
					<c:if test="${sessionScope.loginuser.mIdx==0}">
					<a class="notice-btn" href="<%=ctxPath%>/notice/noticeWrite.bibo">글 작성하기</a>
					</c:if>
			</div>
		</div>
	</div>


	<section class="board-container">
		
		<ul class="board-list">
		
			<div style="border-bottom: 1px solid black;">
			<div class="mt-4 px-2  text-center subject">
			<div class="row text-center py-3 nanum-eb size-s">
			<span class="col-8">제목</span>
			<span class="col-2">작성일자</span>
			<span class="col-2">조회수</span>
			</div>
			</div>
			<c:forEach var="notice" items="${noticeListdto}">
				<li class="board-item nanum-eb size-s px-2">
					<div class="row board-item-body" onclick="click_on('${notice.nidx}')">
						<span class="col-8 board-item-title nanum-eb text-center ">
							<p style="padding-top: 10px;">${notice.title}</p>
							<c:if test="${notice.filename != null}">
							<span class="attach_sh"> <img
								src="<%=ctxPath%>/resources/img/sh_attach.png"
								style="width: 20px; height: 21px;">
							</span>
						</c:if>
						
						</span>
						<span class="col-2 board-item-date text-center size-s " style="padding-top: 10px;">${notice.writeday}</span>
					
						  <span class="col-2 text-center notice-viewcount" style="padding-top: 10px;">${notice.viewcnt}</span>
							
					</div>
					
				</li>
			</c:forEach>
		</div>
		</ul>
		
		
	
	</section>

	<form name="goViewFrm">
		<input type="hidden" name="nidx" value="" />
	</form>

	<div class="w-100 d-flex justify-content-center pt-3">
		<c:out value="${pageBar}" escapeXml="false"/>
	</div>
</body>
</html>
