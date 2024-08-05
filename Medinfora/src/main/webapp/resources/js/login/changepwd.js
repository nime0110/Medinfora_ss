$(document).ready(function(){

    const cmt_pwd = $("p#cmt_pwd");
    const cmt_pwddp = $("p#cmt_pwddp");

    // 비밀번호 유효성 검사
    $("input:password[name='in_pwd']").blur((e)=>{

        cmt_pwd.hide();

        const regExp = new RegExp(/^.*(?=^.{8,16}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*].*$/g);
        // 숫자/문자/특수문자 포함 형태의 8~15자리 이내의 암호 정규표현식 객체 생성
        const bool = regExp.test($(e.target).val());	
        
        if(!bool){
            cmt_pwd.show();
            $(e.target).val("");
        }
        else{
            cmt_pwd.hide();
        }

    });

    // 비밀번호 확인 검사
    $("input#in_pwddp").blur((e)=>{

        const pwd = $("input:password[name='in_pwd']").val().trim();

        cmt_pwddp.hide();

        if($(e.target).val().trim() != pwd){
            cmt_pwddp.show();
            $(e.target).val("");
            $("input:password[name='in_pwd']").val("");
        }
        else{
            cmt_pwddp.hide();
        }
    });


});