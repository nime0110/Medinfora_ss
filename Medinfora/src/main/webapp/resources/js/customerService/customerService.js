$(document).ready(function(){

    $("input#userInput").bind("keyup", function(e){
		if(e.keyCode == 13){ // 엔터를 했을 경우
			goSend();
		}
	});

})  // end of $(document).ready(function(){-----------
