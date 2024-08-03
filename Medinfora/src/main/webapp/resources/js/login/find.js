$(document).ready(function(){

    const cmt_name = $("p#cmt_name");
    const cmt_email = $("p#cmt_email");
    const cmt_code = $("p#cmt_code")

    cmt_name.hide();
    cmt_email.hide();
    cmt_code.hide();

    // 이름 유효성
    $("input:text[name='in_name']").blur((e)=>{
        cmt_name.hide();

        if($(e.target).val().trim() == ""){
            $(e.target).val("");
            cmt_name.show();
        }
        else{
            cmt_name.hide();
        }

    });


    // 이메일 유효성 검사
    $("input:text[name='in_email']").blur((e)=>{

        $("p#email_cmt").empty();

        const regExp =  new RegExp(/^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/);

        const bool = regExp.test($(e.target).val());	

        if(!bool){
            $("p#email_cmt").html("이메일 형식에 맞지 않습니다.");
            $(e.target).val("");
        }
        else{
            $("p#email_cmt").empty();
            
        }

    });
    







});// end of $(document).ready(function(){