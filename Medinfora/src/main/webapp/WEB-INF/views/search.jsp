<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% String ctxPath = request.getContextPath(); %>
<link rel="stylesheet" href="<%=ctxPath%>/resources/css/search.css">

<script>

function hospitaldetail(hidx){
	
	location.href = `<%=ctxPath%>/hpsearch/hospitalSearch.bibo?hidx=\${hidx}`;
	
}

</script>

<div id="searchcontainer">
	<div id="searchsidebar">
		<div class="sidebaroption selectoption">
			<span>통합검색</span>
		</div>
		<div class="sidebaroption">
			<span>병원검색</span>
		</div>
		<div class="sidebaroption">
			<span>Q&amp;A</span>
		</div>
		<div class="sidebaroption">
			<span>공지사항</span>
		</div>
	</div>
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
					
					<div class="showmore">
						<span>검색결과 더보기</span>
					</div>
					
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
					
					<div class="showmore">
						<span>검색결과 더보기</span>
					</div>
					
				</div>
			</c:if>
			
			<c:if test="${requestScope.searchlist.ndtolist != null}">
				<div class="contentsub">
				
					<div class="c_subtitle">
						<span>공지사항</span>
					</div>
					<c:forEach var="ndto" items="${requestScope.searchlist.ndtolist}">
						<div class="content_h">
							<div class="content_place">
								<div class="content_title">${ndto.title}</div>
								<div class="content_content">${ndto.content}</div>
								<div class="content_writeday">${ndto.writeday}</div>
							</div>
						</div>
					</c:forEach>
					
					<div class="showmore">
						<span>검색결과 더보기</span>
					</div>
				
				</div>
			</c:if>
			
		</c:if>
	</div>
</div>

