<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String ctxPath = request.getContextPath();
%>

<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/commu/commuView.css" />
<script type="text/javascript" src="<%= ctxPath%>/resources/js/commu/commuView.js"></script>

<div class="commu-container">

    <div class="board_title1">
        <span class="nanum-eb">${cbdto.title}</span>
    </div>

    <div class="commu-info">
        <span class="commu-userid">작성자: ${cbdto.userid}</span>
        <span class="commu-date">날짜: ${cbdto.writeday}</span>
        <span class="commu-viewcount">조회수: ${cbdto.viewcnt}</span>
        
        <div class="commu-info2">
			<c:if test="${not empty sessionScope.loginuser && sessionScope.loginuser.userid == requestScope.cbdto.userid}">
				<button type="button" class="btn btn-secondary btn-sm mr-3" onclick="javascript:location.href='<%= ctxPath%>/commu/commuEdit.bibo?cidx=${requestScope.cbdto.cidx}'">
					글수정하기
				</button>
				<button type="button" class="btn btn-secondary btn-sm" id="btnOpenModal">삭제하기</button>
			</c:if>        
        </div>
         
         <div class="commu-attachment">
         	<c:if test="${not empty requestScope.fileList}">
                <span class="attach-file">
                    <img src="<%= ctxPath %>/resources/img/sh_attach.png" style="width: 20px; height: 20px;">
                    	첨부파일 : 
                </span>
				<c:forEach var="cfdto" items="${requestScope.fileList}" varStatus="status">
                <a href="<%= ctxPath %>/commu/download.bibo?cidx=${cbdto.cidx}&originFilename=${cfdto.orgname}&filename=${cfdto.fileName}" class="attachment-filename">
				    ${cfdto.orgname}<c:if test="${!status.last}">,</c:if>                
                </a>
				</c:forEach>
            </c:if>
        </div>
    </div>
    
    <div class="nanum-n commu-content" style="height: auto;">
        <p>${cbdto.content}</p>
    </div>
    
	<c:if test="${not empty sessionScope.loginuser}">
		<h3 style="margin-top: 50px;">댓글쓰기</h3>
		<form name="addWriteFrm" id="addWriteFrm" style="margin-top: 20px;">
			<table class="table" style="width: 1024px">
				<tr style="height: 30px;">
					<th width="10%">성명</th>
					<td>
						<input type="hidden" name="fk_userid" value="${sessionScope.loginuser.userid}" readonly /> 
					</td>
				</tr>
				<tr style="height: 30px;">
					<th>댓글내용</th>
					<td>
						<input type="text" name="content" size="100" maxlength="1000"/>
						<input type="hidden" name="parentSeq" value="${requestScope.boardvo.seq}" readonly/>
					</td>
				</tr>
				<tr>
                  	<th colspan="2">
                     	<button type="button" class="btn btn-success btn-sm mr-3" onclick="goAddWrite()">댓글쓰기 확인</button>
                     	<button type="reset" class="btn btn-success btn-sm">댓글쓰기 취소</button>
                  	</th>
               	</tr>
			</table>
		</form>
	</c:if>
		
    <table class="table" style="width: 1024px; margin-top: 2%; margin-bottom: 3%;">
      <thead>
	      <tr>
	         <th style="text-align: center;">내용</th>
	         <th style="width: 8%;  text-align: center;">작성자</th>
	         <th style="width: 12%; text-align: center;">작성일자</th>
	         <th style="width: 12%; text-align: center;">수정/삭제</th>
	      </tr>
      </thead>
      <tbody id="commentDisplay"></tbody>
    </table>
	<%-- #155. 댓글페이지바가 보여지는 곳  시작--%>
	<div style="display: flex; margin-bottom: 50px;">
         <div id="pageBar" style="margin: auto; text-align: center;"></div>
      </div>
	<%-- #155. 댓글페이지바가 보여지는 곳 끝 --%>
</div>


<%-- 글 삭제 모달 --%>
<div id="deleteModal" class="modal modal-dialog">
    <div class="modal-content nanum-n">
        <span class="close nanum-n" style="text-align: right;">&times;</span>
        <h2>정말로 글을 삭제하시겠습니까?</h2>
        <form name="delFrm" style=" display: inline-block; text-align: center;">
            <div>
          		<input type="hidden" name="cidx" value="${cbdto.cidx}" />
	            <div class="nanum-n commu-buttons">
	               <button type="button" class="btn btn-secondary" id="btnDelete">삭제</button>
	               <button type="button" class="btn btn-secondary" id="btnCancel">취소</button>
	            </div>
            </div>
        </form>
    </div>
</div>

<div class="commu-button text-center mt-3">
	<button type="button" class="commu-btn" onclick="location.href='<%= ctxPath %>${requestScope.goBackURL}'">돌아가기</button>
</div>

<form name="goViewFrm" style="display:none;">
    <input type="text" name="cidx" />
    <input type="text" name="goBackURL" />
</form>

</body>
>