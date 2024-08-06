$(document).ready(function(){

    const cmt_name = $("p#cmt_name");
    const cmt_email = $("p#cmt_email");
    const cmt_code = $("p#cmt_code");

    $("div#findresult").hide();

    cmt_name.hide();
    cmt_email.hide();
    cmt_code.hide();

    $("button.reset_btn").hide();

    // 인증코드관련
    $("div#codeArea").hide();

    const in_name = $("input:text[name='in_name']");
    const in_id = $("input:text[name='in_id']");
    const in_email = $("input:text[name='in_email']");
    const in_code = $("input#in_code");

    // 구분자
    const wf = $("input:hidden[name='wf']").val();

    if(wf == "2"){
        $("h2#finditem").html("비밀번호 찾기");
    }


    // 이름 유효성
    in_name.blur((e)=>{
        cmt_name.hide();

        if($(e.target).val().trim() == ""){
            $(e.target).val("");
            cmt_name.show();
        }
        else{
            $(e.target).siblings('button.reset_btn').show();
            cmt_name.hide();
        }

    });

    // 아이디 유효성 검사
    $("input:text[name='in_id']").blur((e)=>{
        cmt_name.hide();

        const regExp =  new RegExp(/^[a-z0-9]{6,20}$/g);

        const bool = regExp.test($(e.target).val());	

        if(!bool){
            cmt_name.show();
            $(e.target).val("");
        }
        else{
            $(e.target).siblings('button.reset_btn').show();
            cmt_name.hide();
        }

    });


    // 이메일 유효성 검사
    in_email.blur((e)=>{

        cmt_email.hide();

        const regExp =  new RegExp(/^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/);

        const bool = regExp.test($(e.target).val());	

        if(!bool){
            cmt_email.show();
            $(e.target).val("");
        }
        else if($(e.target).val().trim() == "") {
            cmt_email.show();
        }
        else{
            $(e.target).siblings('button.reset_btn').show();
            cmt_email.hide();  
        }

    });

    $("button.reset_btn").click(function(e){
        $(e.target).siblings('input').val("");
        $(e.target).hide();
    });
    
    let interval_timer;
    let time = 300;

    $("button#authcodebtn").click(function(e){
        if(wf == "1"){
            if(in_name.val() == "" || in_email.val() == ""){
                return;
            }

            in_name.attr("disabled", true);
        }
        else if(wf == "2"){
            if(in_id.val() == "" || in_email.val() == ""){
                return;
            }

            in_id.attr("disabled", true);
        }

        
        in_email.attr("disabled", true);

        $("button.reset_btn").hide();

        $(this).attr("disabled", true);

        $("div#codeArea").show();

        time = 300;
        interval_timer = setInterval(timer, 1000);

        // 인증코드 메일 발송
        creatcode();


    });


    // === 타이머 함수 만들기 시작 === //
    

    const timer = function () {
        if (time < 0) {
            alert("인증시간이 종료되었습니다.\n 다시 인증을 진행하세요.");

            clearInterval(interval_timer); // 타이머 삭제하기
            // interval_timer 는 중단해야할 setInterval 함수를 가리키는 것이다.

            $("button#correctcodebtn").attr("disabled", true);
            $("input#in_code").val("");

            in_name.attr("disabled", false);
            in_id.attr("disabled", false);
            in_email.attr("disabled", false);

            $("button#authcodebtn").attr("disabled", false);

            $("div#codeArea").hide();

        } 
        else {
            let minute = "";
            let second = "";

            minute = parseInt(time / 60); // 소수부는 없애 버리고 정수부만 가져오는 것이다.
            if (minute < 10) {
                minute = "0" + minute;
            }

            second = time % 60; // time 을 60으로 나누었을때의 나머지
            if (second < 10) {
                second = "0" + second;
            }

            $("span#timer").html(`${minute}:${second}`).css("color","red");

            time--;
        }
    };
    // === 타이머 함수 만들기 끝 === //

    // 인증완료 확인용
    let iscorrect = 0;

    // 인증확인 버튼 클릭
    $("button#correctcodebtn").click(function(){
        const authcode = $("input:hidden[name='authcode']").val();

        if(in_code.val() == authcode){
            $("span#timer").html("");
            clearInterval(interval_timer);

            alert("인증이 완료되었습니다.");
            in_code.attr("disabled", true);
            $(this).attr("disabled", true);
            iscorrect = 1;
        }
        else if(in_code.val().trim() == ""){
            cmt_code.show();
        }
        else{
            alert("인증코드가 일치하지 않습니다.");
        }

    });

    
    $("button#find").click(function(){

        if(iscorrect == 0){
            alert("인증완료 후 가능합니다.");
            return;
        }
        else{

            let data = "";

            if(wf == "1"){
                data = in_name.val();
            }
            else if(wf == "2"){
                data = in_id.val();
            }


            $.ajax({
                url:"findEnd.bibo",
                type:"post",
                data:{"data":data,
                      "email":in_email.val(),
                      "wf":wf},
                dataType:"json",
                success:function(json){

                    if(json.result.userid == null){
                        alert("정보가 일치하지 않습니다.");
                        in_name.attr("disabled", false);
                        in_id.attr("disabled", false);
                        in_email.attr("disabled", false);

                        $("button#authcodebtn").attr("disabled", false);

                        $("div#codeArea").hide();
                        return;
                    }
                    else{
                        if(json.result.wf == "1"){
                            $("span#result").html(json.result.userid);
                            $("div#findresult").show();
                        }
                        else if(json.result.wf == "2"){
                            $("input:hidden[name='userid']").val(json.result.userid);
                            const frm = document.getElementById('changepwd');
                            frm.submit();
                        }
                    }
                    
                },
                error: function(request, status, error){
                    alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
                }


            });
        }

    });
        

    




});// end of $(document).ready(function(){


function creatcode(){
    const in_email = $("input:text[name='in_email']").val().trim();

    $.ajax({
        url:"sendAuthcode.bibo",
        type:"post",
        data:{"recipient":in_email},
        dataType:"json",
        success:function(json){
            console.log(JSON.stringify(json));

            if(json.result == "yes"){
                $("input:hidden[name='authcode']").val(json.code);
            }
            else{
                alert("인증코드 발송 실패하였습니다.\n다시 인증시도 바랍니다.")
                $("input:hidden[name='authcode']").val("");
            }
        },
        error: function(request, status, error){
            alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
        }
    });
}