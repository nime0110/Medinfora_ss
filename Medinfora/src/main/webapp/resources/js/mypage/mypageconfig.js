function infoChange(ctxPath){

    const mobile = $("#mobileId");
    const mobileVal = mobile.val();

    const regExp =  new RegExp(/^[0][0-9]{8,10}$/g);

    const bool = regExp.test(mobileVal);	

    if(!bool){
        $("div#mobile_waring").html("전화번호 형식에 맞지 않습니다.");
        mobile.val("");
        return;
    }else{
        $("div#mobile_waring").empty();
        $("#mobileIdout").val(mobileVal);

        console.log($("#mobileIdout").val());

        const frm = document.configForm;
        frm.action = `${ctxPath}/mypage/updatemember.bibo`;
        frm.submit();
    }

}