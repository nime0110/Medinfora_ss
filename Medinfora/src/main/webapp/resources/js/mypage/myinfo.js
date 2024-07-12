function infoChange(midx){

    const mobile = $("input:text[name='mobile']");

    let address = "";
    let detailaddress = "";

    if(midx=="1"){
        address = $("#address").val().trim();
        detailaddress = $("#detailaddress").val().trim();
    }

    const regExp =  new RegExp(/^[0][0-9]{8,10}$/g);

    const bool = regExp.test(mobile.val());

    if(!bool){
        $("div#mobile_waring").html("전화번호 형식에 맞지 않습니다.");
        mobile.val("");
        mobile.trigger("focus");
        return;
    }else if(midx=="1"
        &&(address == null || address == "" || detailaddress == null || detailaddress == "")){
        $("address_waring").html("주소를 올바르게 입력해주세요.");
        return;
    }else{
        const frm = document.configForm;
        frm.action = `updatemember.bibo`;
        frm.submit();
    }

}

function search(){
    // 카카오 주소 검색
    new daum.Postcode({
        oncomplete: function(data) {
            let addr = ''; 
            let extraAddr = '';
            if (data.userSelectedType === 'R') { 
                addr = data.roadAddress;
            } else { 
                addr = data.jibunAddress;
            }
            if(data.userSelectedType === 'R'){
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                if(extraAddr !== ''){
                    extraAddr = ' (' + extraAddr + ')';
                }
            }
            // 주소 정보를 해당 필드에 넣는다.
            document.getElementById("address").value = addr+extraAddr;
            self.close();

            $("#detailaddress").val("");
            $("#detailaddress").trigger("focus");

        }
    }).open();
    

}	