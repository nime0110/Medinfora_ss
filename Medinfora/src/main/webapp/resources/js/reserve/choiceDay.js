$(function(){

    $("button.timebtn").on("click",(e) => {
        $("span.exTimebtn").removeClass("choiceReserveday");
        $(e.currentTarget).find("span.exTimebtn").addClass("choiceReserveday");
    })  
    
    $("button.reservationbtn").on("click",(e) => {
    	if(confirm("예약하시겠습니까?")) {
            alert("예약되었습니다.");
        }
    })
})