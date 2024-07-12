$(document).on('click','.timebtn',(e) => {
    $("span.exTimebtn").removeClass("choiceReserveday");
    $("button.timebtn").removeClass("choiceReserveday2");
    $(e.currentTarget).find("span.exTimebtn").addClass("choiceReserveday");
    $(e.currentTarget).addClass("choiceReserveday2");
    const day = $("h3.selectDay").text();

    const time = $(e.currentTarget).children("span.exTimebtn").text();
    $("form[name='choiceFrm'] > input[name='day']").val(day + " " + time);
})  // end of $(document).on('click','.timebtn',(e) => {-------------------------

$(document).on('click','.reservationbtn',() => {
    const day = $("form[name='choiceFrm'] > input[name='day']").val();

    if(day == ""){
        alert("진료일시가 선택되지 않았습니다.");
        return;
    }
    if(confirm("예약하시겠습니까?")) {
        let frm = document.choiceFrm;
        frm.method="post"
        frm.action="insertReserve.bibo";
        frm.submit();
    }
})  // end of $(document).on('click','.reservationbtn',() => {----------------

