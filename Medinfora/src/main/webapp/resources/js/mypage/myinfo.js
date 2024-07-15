function infoChange(midx){

    const mobile = $("input:text[name='mobile']");

    const address = $("input:text[name='address']").val();
    const detailaddress = $("input:text[name='detailaddress']").val();

    const regExp =  new RegExp(/^[0][0-9]{8,10}$/g);

    const bool = regExp.test(mobile.val());

    if(!bool){
        $("div#mobile_waring").html("전화번호 형식에 맞지 않습니다.");
        mobile.val("");
        mobile.trigger("focus");
        return;
    }else if(midx == "1"){
        if(address == "" || detailaddress == ""){
            $("div#address_waring").html("주소를 올바르게 입력해주세요.");
            return;
        }else{
        	const frm = document.configForm;
	        frm.action = `updatemember.bibo`;
	        frm.submit();
        }
    }else{
        const frm = document.configForm;
        frm.action = `updatemember.bibo`;
        frm.submit();
    }

}

function pwdChange(){

    const userid = $("input:hidden[name='userid']").val();
    const nowpwd = $("input:password[name='Nowpwd']").val();
    const pwd = $("input:password[name='pwd']").val();
    const pwdRepect = $("input:password[name='pwdRepect']").val();

    const regExp = new RegExp(/^.*(?=^.{8,16}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*].*$/g);
    const bool = regExp.test(pwd);

    $.ajax({
        url : "nowpwdCheck.bibo",
        method : "post",
        dataType : "json",
        async : true,
        data : {"userid":userid,"pwd":nowpwd},
        success : function(json){
        
            if(!json.isPwd){
                $("div#pwd_waring").html("");
                $("div#pwdRepect_waring").html("");
                $("div#Nowpwd_waring").html("틀린 비밀번호 입니다.");
                return;
            }else if(!bool){
                $("div#Nowpwd_waring").html("");
                $("div#pwdRepect_waring").html("");
                $("div#pwd_waring").html("비밀번호 형식에 맞지 않습니다.");
                return;
            }else if(pwd != pwdRepect){
                $("div#Nowpwd_waring").html("");
                $("div#pwd_waring").html("");
                $("div#pwdRepect_waring").html("비밀번호가 일치하지 않습니다.");
                return;
            }else{
                const frm = document.configForm;
                frm.action = `updatepwd.bibo`;
                frm.submit();
            }

        },
        error: function(request, status, error){
            alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
        }
    });

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