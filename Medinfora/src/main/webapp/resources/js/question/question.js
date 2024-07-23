
$(document).ready(function(){

    $("div#answerArea").hide();
    $("div#commentAnswer").hide();

    // 답변등록
    $("button#showAnswerArea").click((e)=>{
        $(e.target).hide();
        $("div#answerArea").show();
    });

    $("textarea[name='content']").keydown((e)=>{
        $(e.target).css("border","");
    });

    $("textarea[name='qcontent']").keydown((e)=>{
        $(e.target).css("border","");
    });


    // 추가질답
    $("div#addQuestionArea").hide();
    $("form[name='addA']").hide();




});


function answercanle(){
    $("textarea[name='content']").val("");
    $("div#answerArea").hide();
    $("button#showAnswerArea").show();
}


// 답변 등록
function answerupload(){
    const content = $("textarea[name='content']").val();

    if(content.trim() == ""){
        $("textarea[name='content']").css("border","solid 4px #ff9999");

        $("textarea[name='content']").val("");
        return;
    }


    $.ajax({
        url:"answerWrite.bibo",
        data:{"qidx":$("input:hidden[name='answer']").val()
             ,"userid":$("input:hidden[name='userid']").val()
             ,"content":$("textarea[name='content']").val()

            },
        dataType:"json",
        success:function(json){
            console.log(JSON.stringify(json));

        },
        error: function(request, status, error){
			alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		}


    });


}

function showaddArea(index){
    const $divs = $("div#addQuestionArea");

    console.log(index);

    const $Sdiv = $divs.eq(index);
    $Sdiv.show();

}


function addupload(index){
    const $Sform = $("form#addQ").eq(index);
    const $Stextarea = $("textarea#acontent").eq(index);

    if($Stextarea.val().trim() == ""){
        $Stextarea.css("border","solid 4px #ff9999");
        $Stextarea.val("");
        return;
    }


}


function addcancle(index){

    console.log(index);

    const $Sdiv = $("div#addQuestionArea").eq(index)
    const $Stextarea = $("textarea#qcontent").eq(index);

    $Stextarea.val("");
    $Sdiv.hide();
    
}

function showaddanswerArea(index){
    const $Sform = $("form[name='addA']").eq(index);
    $Sform.show();
}


function addAnswerupload(index){
    const $Sform = $("form#addA").eq(index);
    const $Stextarea = $("textarea#qcontent").eq(index);

    if($Stextarea.val().trim() == ""){
        $Stextarea.css("border","solid 4px #ff9999");
        $Stextarea.val("");
        return;

    }

}

function addAnswercancle(index){
    const $Sform = $("form#addA").eq(index);
    const $Stextarea = $("textarea#qcontent").eq(index);

    $Stextarea.val("");
    $Sform.hide();
}
