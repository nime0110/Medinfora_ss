<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	String ctxPath = request.getContextPath();
%>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/commu/commuList.css" />
<script type="text/javascript" src="<%= ctxPath%>/resources/js/commu/commuList.js"></script>

<div class="container" style="margin-top: 100px;">
	<div class="py-4" align="center">
		<h2 class="nanum-eb size-n">커뮤니티 게시판</h2>
	</div>
	<form name="commuList" enctype="multipart/form-data">
		<input type="hidden" name="PageNo"/>
		<fieldset>
			<div class="p-4" id="search_flexbox" align="center">
				<span>
					<select class="search_ch sel_0 nanum-b" name="category">
						<option value='0'>구분</option>
						<option value='1'>임신&middot;성고민</option>
						<option value='2'>다이어트&middot;헬스</option>
						<option value='3'>마음 건강</option>
						<option value='4'>탈모 톡톡</option>
						<option value='5'>피부 고민</option>
						<option value='6'>뼈와 관절</option>
						<option value='7'>영양제</option>
						<option value='8'>질환 고민</option>
						<option value='9'>자유게시판</option>						
					</select>
				</span>
				<span class="">
					<select class="search_ch sel_1 nanum-b" name="type">
						<option value='z'>전체</option>
						<option value='title'>글제목</option>
						<option value='content'>글내용</option>
						<option value='userid'>작성자</option>
					</select>
				</span>
				
				<span>
					<input class="search_ch sel_2 nanum-b" name="word" type="text" placeholder="검색어를 입력해주세요." autocomplete="none"/>
				</span>
				<span>
					<button class="jh_btn_design search nanum-eb size-s" type="button" onclick="searchList()">검색</button>
				</span>
			</div>		
		</fieldset>
	</form>
	

	<div>
		<!-- 타이틀 -->
		<div class="mt-4 px-3 subject">
			<div class="row text-center py-3 nanum-eb size-s">
				<span class="col-2">구분</span>
				<span class="col-5">제목</span>
				<span class="col-2">작성자</span>
				<span class="col-2">작성일자</span>
				<span class="col-1">조회수</span>
			</div>
		</div>
		
		<div class="mb-5 px-3" id="commuArea">
			<c:if test="${not empty requestScope.CommuBoardList}">
				<c:forEach var="cbdto" items="${requestScope.CommuBoardList}"  varStatus="status">
					<div class="row text-center py-3 nanum-n size-s b_border">
						<input type="hidden" value="${cbdto.cidx}" name="no${status.index}"/>
						<span class="col-2">
							${cbdto.category}
						</span>
						<span class="col-5" align="left">
							${cbdto.title}
							<c:forEach var="seq" items="${requestScope.fileSeqList}"  varStatus="status">
								<c:if test="${cbdto.cidx == seq}"><i class="fa-solid fa-paperclip" style="color: #535965;"></i></c:if>
							</c:forEach>
						</span>
						<span class="col-2">${cbdto.userid}</span>
						<span class="col-2">${cbdto.writeday}</span>
						<span class="col-1">${cbdto.viewcnt}</span>
					</div>
				</c:forEach>
			</c:if>
		</div>
	
	</div>
	
	<%-- 페이지 바 --%>
	<div class="pagebar" style="text-align: center;"></div>
	
	<div class="py-5 text-center">
		<button class="write nanum-eb size-s" type="button" onclick="gowrite()">등록</button>
	</div>
</div>