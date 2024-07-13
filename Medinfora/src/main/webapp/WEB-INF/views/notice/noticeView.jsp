<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String ctxPath = request.getContextPath();
%>

<link rel="stylesheet" type="text/css" href="<%= ctxPath %>/resources/css/notice/notice.css" />
<link rel="stylesheet" type="text/css" href="<%= ctxPath %>/resources/css/notice/noticeMedia.css" />
<link rel="stylesheet" type="text/css" href="<%= ctxPath %>/resources/css/header.css" />
<script type="text/javascript" src="<%= ctxPath %>/resources/js/notice/notice.js"></script>

<style>
      
    </style>

<script type="text/javascript">
    $(document).ready(function() {
        // Open the modal
        $("#btnOpenModal").click(function() {
            $("#deleteModal").show();
        });

        
        $(".close, #btnCancel").click(function() {
            $("#deleteModal").hide();
        });

        // Delete confirmation
        $("#btnDelete").click(function() {
            const frm = document.delFrm;
            frm.method = "post";
            frm.action = "<%= ctxPath %>/notice/delEnd.bibo";
            frm.submit();   
        });
    });
</script>

<div class="notice-container">

    <div class="board_title1">
        <span class="nanum-eb size-b">${noticedto.title}</span>
    </div>

    <div class="notice-info">
        <span class="notice-date">날짜: ${noticedto.writeday}</span>
        <span class="notice-viewcount">조회수: ${noticedto.viewcnt}</span>
        <div class="notice-info2">
 <c:if test="${sessionScope.loginuser.mIdx == 0}">
            <button type="button" class="btn btn-secondary btn-sm mr-3" onclick="location.href='<%= ctxPath %>/notice/noticeEdit.bibo?seq=${noticedto.nidx}'">수정하기</button>
            <button type="button" class="btn btn-secondary btn-sm" id="btnOpenModal">삭제하기</button>
        </c:if>
        </div>
         <div class="notice-attachment">
            <c:if test="${noticedto.filename != null}">
                <span class="attach_sh">
                    <img src="<%= ctxPath %>/resources/img/sh_attach.png" style="width: 20px; height: 20px;">
                    첨부파일 : 
                </span>
                <a href="<%= ctxPath %>/notice/download.bibo?nidx=${noticedto.nidx}" class="attachment-filename">${noticedto.orgname}</a>
                
            </c:if>
        </div>
    </div>


    <hr>

    <div class="nanum-n notice-content" style="height: auto;">
        <p>${noticedto.content}</p>
<%--         <c:if test="${noticedto.filename.endsWith('.jpg') || noticedto.filename.endsWith('.jpeg') || noticedto.filename.endsWith('.png') || noticedto.filename.endsWith('.gif')}">
                    <img src="<%= ctxPath %>/resources/files/${noticedto.filename}" alt="${noticedto.filename}"  style="max-width: 100%; height: auto; display: block; margin-left: auto; margin-right: auto;" />
                </c:if> --%>
    </div>

    <div class="notice-button text-center">
        <button type="button" class="notice-btn" onclick="location.href='<%= ctxPath %>/notice/noticeList.bibo'">돌아가기</button>
           
           </div>
              <div class="prev-next-links">
        <table id="nextBoard">
            <tr>
                <td class="--font">이전</td>
                <td>
                    <c:if test="${not empty prevNotice}">
                        <a href="<c:url value='/notice/view.bibo?nidx=${prevNotice.nidx}'/>" class="prev-link">${prevNotice.title}</a>
                    </c:if>
                    <c:if test="${empty prevNotice}">
                        <span class="--font">이전글이 없습니다.</span>
                    </c:if>
                </td>
            </tr>
            <tr class="--font" style="border-top: 1px solid #dbdbdb;">
                <td class="--font">다음</td>
                <td>
                    <c:if test="${not empty nextNotice}">
                        <a href="<c:url value='/notice/view.bibo?nidx=${nextNotice.nidx}'/>" class="next-link">${nextNotice.title}</a>
                    </c:if>
                    <c:if test="${empty nextNotice}">
                        <span>다음글이 없습니다.</span>
                    </c:if>
                </td>
            </tr>
        </table>
    </div>
       
    
    

   
    <div id="deleteModal" class="modal modal-dialog">
        <div class="modal-content nanum-n">
            <span class="close nanum-n" style="text-align: right;">&times;</span>
            <h2>정말로 글을 삭제하시겠습니까?</h2>
            <form name="delFrm" style=" display: inline-block; text-align: center;">
                <div>
              
                <input type="hidden" name="nidx" value="${noticedto.nidx}" />
                 <br>
                 <div class="nanum-n notice-buttons">
                    <button type="button" class="btn btn-secondary" id="btnDelete">삭제</button>
                    <button type="button" class="btn btn-secondary" id="btnCancel">취소</button>
                </div>
                
                </div>
            </form>
        </div>
    </div>
</div>

<form name="goViewFrm" style="display:none;">
    <input type="hidden" name="seq" />
    <input type="hidden" name="goBackURL" />
</form>

</body>
