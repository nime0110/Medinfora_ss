$(document).on("click",'.fc-list-event-title',(e) => {
	
	let target = $(e.target);
	let word = target.children().text();

	if(word == ""){
		word = target.text();
	}
	// word(예약자명) 에 대한 정보 보여주기
	alert(word);
	
});
	
$(document).on("click",'.fc-event-title',(e) => {
	
	let word = $(e.target).text();
	
	// word(예약자명) 에 대한 정보 보여주기
	alert(word);
	
})