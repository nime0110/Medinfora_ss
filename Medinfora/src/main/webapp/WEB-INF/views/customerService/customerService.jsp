<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% String ctxPath = request.getContextPath(); %>

<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/customerService/customerService.css" />

<script type="text/javascript" src="<%= ctxPath%>/resources/js/customerService/customerService.js"></script>

<div class="customerServiceContainer mx-auto mt-5">
	<div class="text-center">
		<div class="img_container mt-5">
			<img src="<%=ctxPath %>/resources/img/customerService_icon.png" class="icon serviceIcon"/>
		</div>
		<span class="nanum-b size-n">&nbsp;&nbsp;Medinfora&nbsp;문의</span>
		<hr>
	</div>
	<div class="gptContainer">
		<%-- 채팅공간 --%>
		<div id="chatBox">
            <div id="chatContent" class="d-flex flex-column">
            	<%-- 채팅내용 --%>
            	<div class="user-message">안녕</div>
            	<div class="gpt-message">안녕하세요.</div>
            </div>
            <div class="sendbox text-center">
	            <input type="text" id="userInput" placeholder="궁금한 사항을 입력해주세요."/>
	            <button id="sendBtn" class="btn nanum-b" onclick="goSend()">전송</button>
            </div>
        </div>
	</div>
</div>
