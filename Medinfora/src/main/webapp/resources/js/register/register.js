

$(document).ready(function(){

    const ctxPath = $("input:hidden[name='ctxPath']").val();

    // 오늘 날짜 구하기
    const date = new Date();

    const year = date.getFullYear();
    const month = date.getMonth() + 1;
    const day = date.getDate();

    const today = year+"-"+month+"-"+day;

	// 달력 관련
    $("input:text[name='birthday']").daterangepicker({
        "singleDatePicker": true,
        "showDropdowns": true,
        "autoApply": true,  // 자동입력
        "locale": {
            "format": "YYYY-MM-DD",
            "customRangeLabel": "Custom",
            "weekLabel": "W",
            "daysOfWeek": ["일","월","화","수","목","금","토"],
            "monthNames": ["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"],
            "firstDay": 1
        },
        "startDate": today,
        "endDate": today,
        "opens": "center"

      }, function(start, end, label) {
          // console.log('New date range selected: ' + start.format('YYYY-MM-DD') + ' to ' + end.format('YYYY-MM-DD') + ' (predefined range: ' + label + ')');
      });
    

    // 아이디 유효성 검사
    $("input:text[name='userid']").blur((e)=>{

        $("input:hidden[name='idCheck_click']").val("false");
        $("p#id_cmt").empty();

        const regExp =  new RegExp(/^[a-z0-9]{6,20}$/g);

        const bool = regExp.test($(e.target).val());	

        if(!bool){
            $("p#id_cmt").html("아이디 형식에 맞지 않습니다.");
            $(e.target).val("");
        }
        else{
            $("p#id_cmt").empty();
            // 바로 중복확인 해버려? 버튼누르는 걸로 갑니다. 버그잡기 쉻음..
        }

    });


    // 아이디 중복검사
    $("button#isExistCheck_userid").click(function(){
        const userid = $("input:text[name='userid']").val().trim();
        const type = "userid";

        if(userid == ""){
            alert("아이디를 입력하세요.");
        }
        else{
            isExistCheck(userid, type);
        }

        

    });


    // 비밀번호 유효성 검사
    $("input:password[name='pwd']").blur((e)=>{

        $("p#pwd_cmt").empty();
        $("p#pwd_check_cmt").empty();

        const regExp = new RegExp(/^.*(?=^.{8,16}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*].*$/g); 
        // 숫자/문자/특수문자 포함 형태의 8~15자리 이내의 암호 정규표현식 객체 생성
        const bool = regExp.test($(e.target).val());	
        
        if(!bool){
            $("p#pwd_cmt").html("비밀번호 형식에 맞지 않습니다.");
            $(e.target).val("");
        }
        else{
            $("p#id_cmt").empty();
        }

    });

    // 비밀번호 확인 검사
    $("input#pwd_check").blur((e)=>{

        const pwd = $("input:password[name='pwd']").val().trim();

        $("p#pwd_check_cmt").empty();

        if($(e.target).val != pwd){
            $("p#pwd_check_cmt").html("비밀번호가 일치하지 않습니다.");
            $(e.target).val("");
            $("input:password[name='pwd']").val("");
        }
        else{
            $("p#pwd_check_cmt").empty();
        }
    });


    // 이름 유효성 검사
    $("input:text[name='name']").blur((e)=>{
        $("p#name_cmt").empty();

        const regExp =  new RegExp(/^[가-힣]{2,12}$/g);

        const bool = regExp.test($(e.target).val());	

        if(!bool){
            $("p#name_cmt").html("이름 형식에 맞지 않습니다.");
            $(e.target).val("");
        }
        else{
            $("p#name_cmt").empty();
            
        }

    });


    // 이메일 유효성 검사
    // 인증번호 발송 해 말아?
    $("input:text[name='email']").blur((e)=>{

        $("input:hidden[name='emailCheck_click']").val("false");
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

    // 이메일 중복검사
    $("button#isExistCheck_email").click(function(){
        const email = $("input:text[name='email']").val().trim();
        const type = "email";

        if(email == ""){
            alert("이메일을 입력하세요.");
        }
        else{
            isExistCheck(email, type);
        }

        

    });



    // 전화번호 유효성 검사
    // 인증번호는 안함 기관전화도 포함해서 사용하기 때문에
    $("input:text[name='mobile']").blur((e)=>{
        $("p#mobile_cmt").empty();

        const regExp =  new RegExp(/^[0][0-9]{8,10}$/g);

        const bool = regExp.test($(e.target).val());	

        if(!bool){
            $("p#mobile_cmt").html("전화번호 형식에 맞지 않습니다.");
            $(e.target).val("");
        }
        else{
            $("p#mobile_cmt").empty();
            
        }

    });



    // 이제와서 해야하는건 다입력했는지 검사 후 인서트 해야합니다.
    // 그리고, 회원 폼 넘기기 전에 어떤 회원이 가입하는지 확인 해야합니다. 
    // 이걸로 회원 구분해서 띄어주는거 다르게 나오도록 해야합나다.
    // 그리고, 카카오 로그인 시 없으면 값을 폼으로 넘겨야 합니다.








});



// Function Declaration
function search(){
    // 카카오 주소 검색
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            let addr = ''; // 주소 변수
            let extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if(data.userSelectedType === 'R'){
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraAddr !== ''){
                    extraAddr = ' (' + extraAddr + ')';
                }
                // 조합된 참고항목을 해당 필드에 넣는다.
                // document.getElementById("extraAddress").value = extraAddr;
            
            } else {
                // document.getElementById("extraAddress").value = '';
            }

            // 주소 정보를 해당 필드에 넣는다.
            document.getElementById("address").value = addr+extraAddr;

            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("detailAddress").focus();
        }
    }).open();




}// end of function search(){---


function isExistCheck(value, type){

    const url = "isexistcheckjson.bibo";

    $.ajax({
        url:url,
        type:"post",
        data:{"value":value
             ,"type":type},
        dataType:"json",
        success:function(json){

            if(type == "userid"){
                if(json.result == "true"){
                    $("p#id_cmt").html("이미 사용중인 아이디 입니다.");
                    $("input:text[name='userid']").val("");
                }
                else{
                    $("p#id_cmt").html("사용가능한 아이디 입니다.");
                    $("input:hidden[name='idCheck_click']").val("true");
                }
            }
            else if(type == "email"){
                if(json.result == "true"){
                    $("p#email_cmt").html("이미 사용중인 이메일 입니다.");
                    $("input:text[name='email']").val("");
                }
                else{
                    $("p#email_cmt").html("사용가능한 이메일 입니다.");
                    $("input:hidden[name='emailCheck_click']").val("true");
                }
            }
            
        },
        error: function(request, status, error){
            alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
        }
        

    });


}// end of function isExistCheck(value, type)---