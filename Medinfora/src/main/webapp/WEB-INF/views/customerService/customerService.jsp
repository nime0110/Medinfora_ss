<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% String ctxPath = request.getContextPath(); %>

<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/customerService/customerService.css" />

<script type="text/javascript" src="<%= ctxPath%>/resources/js/customerService/customerService.js"></script>

<script>
//대화내용 전송버튼을 누른 경우
async function goSend() {
    const content = $("#userInput").val().trim();
    const chatContent = $("#chatContent");

    if (content === "") {
        alert("내용을 입력해주세요.");
        $("#userInput").focus();
        return;
    }

    // 채팅기록 올리기
    const userMessage = $("<div>").addClass("user-message").text(content);
    chatContent.append(userMessage);

    $("#userInput").val("").focus();

    // 스크롤을 가장 아래로 이동
    chatContent.scrollTop(chatContent[0].scrollHeight);

    // 대화내용 전송(GPT 에게)
    await sendToGPT(content);
}

//대화내용 전송(GPT 에게)
async function sendToGPT(message) {
    try {

        const response = await fetch('<%= ctxPath %>/api/chat.bibo', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ message: message }),
        });

        if (response.ok) {	// 응답한 경우
            const data = await response.json();
            const chatContent = $("#chatContent");
            
            // 대답 내용 올리기
            const gptMessage = $("<div>").addClass("gpt-message").text(data.reply);
            chatContent.append(gptMessage);
            
         	// 스크롤을 가장 아래로 이동
            chatContent.scrollTop(chatContent[0].scrollHeight);
        } 
    } catch (error) {
        console.error('Error:', error);
    }	// end of try~catch---------------------------
}
</script>

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
            </div>
            <div class="sendbox text-center">
	            <input type="text" id="userInput" placeholder="궁금한 사항을 입력해주세요."/>
	            <button id="sendBtn" class="btn nanum-b" onclick="goSend()">전송</button>
            </div>
        </div>
	</div>
</div>
