<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String ctxPath = request.getContextPath();
%>

<style type="text/css">
    .modal {
        display: none; /* Hidden by default */
        position: fixed; /* Stay in place */
        z-index: 1; /* Sit on top */
        left: 0;
        top: 0;
        width: 100%; /* Full width */
        height: 100%; /* Full height */
        overflow: auto; /* Enable scroll if needed */
        background-color: rgb(0,0,0); /* Fallback color */
        background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
        padding-top: 60px;
    }

    .modal-content {
        background-color: #fefefe;
        margin: 5% auto; /* 15% from the top and centered */
        padding: 20px;
        border: 1px solid #888;
        width: 80%; /* Could be more or less, depending on screen size */
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
            if(confirm("정말로 글을 삭제하시겠습니까?")) {
                const frm = document.delFrm;
                frm.method = "post";
                frm.action = "<%= ctxPath %>/notice/delEnd.bibo";
                frm.submit();   
            }
        });
    });
</script>

<div style="text-align: center;">
    <h2>글삭제</h2>
    <button type="button" id="btnOpenModal" class="btn btn-secondary">삭제하기</button>
</div>

<!-- The Modal -->
<div id="deleteModal" class="modal">
    <div class="modal-content">
        <span class="close">&times;</span>
        <h2>정말로 글을 삭제하시겠습니까?</h2>
        <form name="delFrm">
            <input type="hidden" name="nidx" value="${nidx}" />
            <div class="notice-buttons">
                <button type="button" class="btn btn-secondary" id="btnDelete">삭제완료</button>
                <button type="button" class="btn btn-secondary" id="btnCancel">취소</button>
            </div>
        </form>
    </div>
</div>
