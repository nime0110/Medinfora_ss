<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String ctxPath = request.getContextPath();
%>

<link rel="stylesheet" type="text/css" href="<%= ctxPath %>/resources/css/notice/notice.css" />
<link rel="stylesheet" type="text/css" href="<%= ctxPath %>/resources/css/notice/noticeMedia.css" />
<link rel="stylesheet" type="text/css" href="<%= ctxPath %>/resources/css/header.css" />
<script type="text/javascript" src="<%= ctxPath %>/resources/js/notice/notice.js"></script>

<style type="text/css">
     .modal {
        display: none;
        position: fixed;
        z-index: 1;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        overflow: auto;
        background-color: rgb(0,0,0);
        background-color: rgba(0,0,0,0.4);
    }

    .modal-content {
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        background-color: #fefefe;
        padding: 20px;
        border: 1px solid #888;
        width: 80%;
        max-width: 500px;
    }

    .close {
        color: #aaa;
        float: right;
        font-size: 28px;
        font-weight: bold;
    }

    .close:hover,
    .close:focus {
        color: black;
        text-decoration: none;
        cursor: pointer;
    }

    .notice-buttons {
        display: flex;
        justify-content: center;
        gap: 10px;
    }
</style>

<script type="text/javascript">
    $(document).ready(function() {
        // Open the modal
        $("#btnOpenModal").click(function() {
            $("#deleteModal").show();
        });

        // Close the modal
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

         <div class="notice-attachment">
            <c:if test="${noticedto.filename != null}">
                <span class="attach_sh">
                    <img src="<%= ctxPath %>/resources/img/sh_attach.png" style="width: 20px; height: 20px;">
                    첨부파일 : 
                </span>
                <a href="<%= ctxPath %>/download.bibo?nidx=${noticedto.nidx}" class="attachment-filename">${noticedto.orgname}</a>
                
            </c:if>
        </div>
    </div>


    <hr>

    <div class="nanum-n notice-content" style="height: auto;">
        <p>${noticedto.content}</p>
        <c:if test="${noticedto.filename.endsWith('.jpg') || noticedto.filename.endsWith('.jpeg') || noticedto.filename.endsWith('.png') || noticedto.filename.endsWith('.gif')}">
                    <img src="<%= ctxPath %>/resources/files/${noticedto.filename}" alt="${noticedto.orgname}" style="max-width: 100%; height: auto;" />
                </c:if>
    </div>

    <div class="notice-button text-center mb-5">
        <button type="button" class="btn btn-secondary btn-sm" onclick="location.href='<%= ctxPath %>/notice/noticeList.bibo'">돌아가기</button>
        <c:if test="${sessionScope.loginuser.mIdx == 0}">
            <button type="button" class="btn btn-secondary btn-sm mr-3" onclick="location.href='<%= ctxPath %>/notice/noticeEdit.bibo?seq=${noticedto.nidx}'">수정하기</button>
            <button type="button" class="btn btn-secondary btn-sm" id="btnOpenModal">삭제하기</button>
        </c:if>
    </div>

    <!-- The Modal -->
    <div id="deleteModal" class="modal modal-dialog">
        <div class="modal-content">
            <span class="close">&times;</span>
            <h2>정말로 글을 삭제하시겠습니까?</h2>
            <form name="delFrm">
                <input type="hidden" name="nidx" value="${noticedto.nidx}" />
                <div class="notice-buttons">
                    <button type="button" class="btn btn-secondary" id="btnDelete">삭제완료</button>
                    <button type="button" class="btn btn-secondary" id="btnCancel">취소</button>
                </div>
            </form>
        </div>
    </div>
</div>

<form name="goViewFrm" style="display:none;">
    <input type="hidden" name="seq" />
    <input type="hidden" name="goBackURL" />
</form>
</div>
</body>
