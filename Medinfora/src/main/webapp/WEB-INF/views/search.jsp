<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% String ctxPath = request.getContextPath(); %>
<link rel="stylesheet" href="<%=ctxPath%>/resources/css/search.css">

<script>

let cnt = ${requestScope.hcnt};

function hospitaldetail(hidx){
	
	location.href = `<%=ctxPath%>/hpsearch/hospitalSearch.bibo?hidx=\${hidx}`;
	
}

function noticedetail(nidx){
	
	location.href = `<%=ctxPath%>/notice/view.bibo?nidx=\${nidx}`;
	
}

function moreshow(hcnt){
	
	let view = 0;
	
	if (cnt > 5){
		view = 5;
		cnt = cnt - 5;
	}else{
		view = cnt%5;
		cnt = 0;
	}
	
	const orginhtml = $('#htmlhospital').html();
	let addhtml = '';
	
	for(let i=0;i<view;i++){
		
		/*
		$.ajax({
			url : "/getmorehinfo.bibo",
			
		})*/
		
	}
	
}

</script>

<div id="searchcontainer">
	<div id="searchcontents">
		<c:if test='${requestScope.nosearch == 1}'>
			<div class="contenttitle">검색어를 입력해주세요.</div>
		</c:if>
		
		<c:if test='${requestScope.nosearch == 2}'>
			<div class="contenttitle">"${requestScope.search}"의 검색결과는 존재하지 않습니다.</div>
		</c:if>
		
		<c:if test='${requestScope.nosearch == 0}'>
		
			<div class="contenttitle">"${requestScope.search}" 의 검색결과</div>
			
			<c:if test="${requestScope.searchlist.hdtolist != null}">
				<div class="contentsub">
				
					<div class="c_subtitle">
						<span>병원</span>
					</div>
					<div id="htmlhospital">
						<c:forEach var="hdto" items="${requestScope.searchlist.hdtolist}">
							<div class="content_h">
								<div class="content_place">
									<div class="content_hpname" onclick="hospitaldetail('${hdto.hidx}')">${hdto.hpname} (${hdto.agency})</div>
									<div class="content_hpaddress">${hdto.hpaddr}</div>
									<div class="content_hpmobile">${hdto.hptel}</div>
								</div>
								<div class="content_tool">
									<c:if test="${hdto.member == true}">
										<button class="tool_btn" type="button">예약하기</button>
									</c:if>
								</div>
							</div>
						</c:forEach>
					</div>
					
					<c:if test="${requestScope.hcnt > 0}">
						<div class="showmore" onclick="moreshow()">
							<span>검색결과 더보기</span>
						</div>
					</c:if>
					
				</div>
				
			</c:if>
			
			<c:if test="${requestScope.searchlist.mqdtolist != null || requestScope.searchlist.madtolist != null}">
				<div class="contentsub">
				
					<div class="c_subtitle">
						<span>Q&amp;A</span>
					</div>
					<c:forEach var="mqdto" items="${requestScope.searchlist.mqdtolist}">
						<div class="content_q">
							<div class="content_place">
								<div class="content_title">${mqdto.title}</div>
								<div class="content_content">${mqdto.content}</div>
								<div class="content_writeday">${mqdto.writeday}</div>
							</div>
						</div>
					</c:forEach>
					<c:forEach var="madto" items="${requestScope.searchlist.madtolist}">
						<div class="content_q">
							<div class="content_place">
								<div class="content_title">${madto.title}</div>
								<div class="content_content">${madto.content}</div>
								<div class="content_writeday">${madto.writeday}</div>
							</div>
						</div>
					</c:forEach>
					
					<div class="showmore" onclick="location.href='<%=ctxPath%>/questionList.bibo'">
						<span>QNA 더보기</span>
					</div>
					
				</div>
			</c:if>
			
			<c:if test="${requestScope.searchlist.ndtolist != null}">
				<div class="contentsub">
				
					<div class="c_subtitle">
						<span>공지사항</span>
					</div>
					<c:forEach var="ndto" items="${requestScope.searchlist.ndtolist}" >
						<div class="content_h">
							<div class="content_place">
								<div class="content_title" onclick="noticedetail('${ndto.nidx}')">${ndto.title}</div>
								<div class="content_content">${ndto.content}</div>
								<div class="content_writeday">${ndto.writeday}</div>
							</div>
						</div>
					</c:forEach>
					
					<div class="showmore" onclick="location.href = '<%=ctxPath%>/notice/noticeList.bibo'">
						<span>공지사항 목록보기</span>
					</div>
				
				</div>
			</c:if>
			
		</c:if>
	</div>
</div>

