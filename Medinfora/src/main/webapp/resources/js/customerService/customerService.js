$(document).ready(function(){

    $("input#userInput").bind("keyup", function(e){
		if(e.keyCode == 13){ // 엔터를 했을 경우
			goSend();
		}
	});

})  // end of $(document).ready(function(){-----------

////////////////////////////////////////////////////////////////

function goSend(){
    const content = $("input#userInput").val().trim();
    const chatContent = document.getElementById('chatContent');

    if(content == ""){
        alert("내용을 입력해주세요.");
        $("input#userInput").focus();
        return;
    }

    // 채팅기록 올리기
    const userMessage = document.createElement('div');
    userMessage.textContent = userInput.value;
    userMessage.className = 'user-message';
    chatContent.appendChild(userMessage);
    $("input#userInput").val("");

    // 대화내용 전송

}
