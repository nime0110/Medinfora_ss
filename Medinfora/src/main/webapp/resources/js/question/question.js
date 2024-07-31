
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

    // 추가질답수정
    $("div#updateaddA").hide();

    // 답변수정
    $("div.answerUpdate").hide();



});// end of $(document).ready(function(){---


// 질문 관련
// 질문수정
function questionupdate(){
    const frm = document.questionUpdate;
    frm.action = "questionUpdate.bibo";
    frm.submit();
}

// 질문 삭제
function questionDelete(qidx){
    if(confirm("질문을 정말로 삭제하겠습니까?")){
        $.ajax({
            url:"questionDelete.bibo",
            type:"post",
            data:{"qidx":qidx
                 ,"userid":$("input#writer").val()
                },
            dataType:"json",
            success:function(json){
                console.log(JSON.stringify(json));
    
                if(json.result == '1'){
                    alert("질문이 삭제되었습니다.");
                    location.href = "questionList.bibo";
                }
    
            },
            error: function(request, status, error){
                alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
            }
    
        });
    }
    else{
        return;
    }
}



// 답변 관련
function answercanle(){
    $("textarea[name='content']").val("");
    $("div#answerArea").hide();
    $("button#showAnswerArea").show();
}



function answerupload(){
    const content = $("textarea[name='content']").val();

    if(content.trim() == ""){
        $("textarea[name='content']").css("border","solid 4px #ff9999");

        $("textarea[name='content']").val("");
        return;
    }


    $.ajax({
        url:"answerWrite.bibo",
        type:"post",
        data:{"qidx":$("input:hidden[name='answer']").val()
             ,"userid":$("input:hidden[name='userid']").val()
             ,"content":$("textarea[name='content']").val()

            },
        dataType:"json",
        success:function(json){
            console.log(JSON.stringify(json));

            if(json.result == '1'){
                alert("답변이 등록되었습니다.");
                location.reload();
            }

        },
        error: function(request, status, error){
			alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		}


    });

}

function showUpdateQ(index){
    $(`div#answerUpdate${index}`).show();
    $(`div#answer_btn${index}`).hide();
}

function answerUpcanle(index){
    
    $(`div#answer_btn${index}`).show();

   const $div = $(`div#answerUpdate${index}`);
   const orgin = $div.find("input#orgincotent").val();
   $div.find("textarea[name='content']").val(orgin);
   $div.hide();

}

function answerUpdate(index){
    const $div = $(`div#answerUpdate${index}`);
    const $Stextarea = $div.find("textarea[name='content']");

    if($Stextarea.val().trim() == ""){
        $Stextarea.css("border","solid 4px #ff9999");
        $Stextarea.val("");
        return;
    }

    $.ajax({
        url:"answerUdate.bibo",
        type:"post",
        data:{"aidx":$div.find("input:hidden[name='aidx']").val()
             ,"userid":$div.find("input:hidden[name='userid']").val()
             ,"content":$Stextarea.val()
            },
        dataType:"json",
        success:function(json){
            console.log(JSON.stringify(json));

            if(json.result == '1'){
                alert("답변이 수정되었습니다.");
                location.reload();
            }

        },
        error: function(request, status, error){
			alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		}

    });
}

function answeDelete(index){
    const $div = $(`div#answerUpdate${index}`);

    if(confirm("답변을 정말로 삭제하겠습니까?")){
        $.ajax({
            url:"answerDelete.bibo",
            type:"post",
            data:{"aidx":$div.find("input:hidden[name='aidx']").val()
                 ,"userid":$div.find("input:hidden[name='userid']").val()
            },
            dataType:"json",
            success:function(json){
                console.log(JSON.stringify(json));
    
                if(json.result == '1'){
                    alert("답변이 삭제되었습니다.");
                    location.reload();
                }
    
            },
            error: function(request, status, error){
                alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
            }
    
        });
    }
    else{
        return;
    }

    

}





// 추가질답 관련
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


    $.ajax({
        url:"addQAUpload.bibo",
        type:"post",
        data:{"aidx":$Sform.find("input[name='aidx']").val()
             ,"qnastatus":$Sform.find("input[name='qnastatus']").val()
             ,"cntnum":$Sform.find("input[name='cntnum']").val()
             ,"qcontent":$Sform.find("textarea[name='qcontent']").val()
            },
        dataType:"json",
        success:function(json){
            console.log(JSON.stringify(json));

            if(json.result == '1'){
                alert("질문이 등록되었습니다.");
                location.reload();
            }

        },
        error: function(request, status, error){
			alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		}


    });


}

function addcancle(index){

    console.log(index);

    const $Sdiv = $("div#addQuestionArea").eq(index)
    const $Stextarea = $("textarea#qcontent").eq(index);

    $Stextarea.val("");
    $Sdiv.hide();
    
}


function showaddanswerArea(index){
    const $Sform = $(`form#addA${index}`);
    $Sform.show();
}


function addAnswerupload(index){
    const $Sform = $(`form#addA${index}`);
    const $Stextarea = $Sform.find("textarea#qcontent");

    if($Stextarea.val().trim() == ""){
        $Stextarea.css("border","solid 4px #ff9999");
        $Stextarea.val("");
        return;
    }


    $.ajax({
        url:"addQAUpload.bibo",
        type:"post",
        data:{"aidx":$Sform.find("input[name='aidx']").val()
             ,"qnastatus":$Sform.find("input[name='qnastatus']").val()
             ,"cntnum":$Sform.find("input[name='cntnum']").val()
             ,"qcontent":$Sform.find("textarea[name='qcontent']").val()
            },
        dataType:"json",
        success:function(json){
            console.log(JSON.stringify(json));

            if(json.result == '1'){
                alert("답변이 등록되었습니다.");
                location.reload();
            }

        },
        error: function(request, status, error){
			alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		}
    });

}

function addAnswercancle(index){
    const $Sform = $(`form#addA${index}`);
    const $Stextarea = $Sform.find("textarea#qcontent");

    $Stextarea.val("");
    $Sform.hide();
}


function showUpdateaddQ(index){

    const $Sdivs = $(`div#addanswerArea${index}`);
    $Sdivs.find("div#addanswer").hide();
    $Sdivs.find("div#updateaddA").show();
}


function addAnswerupdate(index){
    const $Sdivs = $(`div#addanswerArea${index}`);

    const $textarea = $Sdivs.find("textarea#qcontent");

    if($textarea.val().trim() == ""){
        $textarea.css("border","solid 4px #ff9999");
        $textarea.val("");
        return;
    }

    $.ajax({
        url:"addQADelete.bibo",
        type:"post",
        data:{"qaidx":$Sdivs.find("input[name='qaidx']").val()
             ,"qcontent":$Sdivs.find("textarea[name='qcontent']").val()
            },
        dataType:"json",
        success:function(json){
            console.log(JSON.stringify(json));

            if(json.result == '1'){
                alert("답변이 수정되었습니다.");
                location.reload();
            }
           
        },
        error: function(request, status, error){
			alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		}
    });


}

function addAnswerdelete(index){

    const $Sdivs = $(`div#addanswerArea${index}`);

    if(confirm("삭제하시겠습니까?")){
        $.ajax({
            url:"addQADelete.bibo",
            type:"get",
            data:{"qaidx":$Sdivs.find("input[name='qaidx']").val()},
            dataType:"json",
            success:function(json){
                console.log(JSON.stringify(json));
    
                if(json.result == '1'){
                    alert("답변이 삭제되었습니다.");
                    location.reload();
                }
               
            },
            error: function(request, status, error){
                alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
            }
        });
    }
    else{
        return;
    }

}




function addupdatecancle(index){

    const $Sdivs = $(`div#addanswerArea${index}`);

    const orgin = $Sdivs.find("span#qcontent").html();

    $Sdivs.find("div#addanswer").show();

    $Sdivs.find("textarea#qcontent").val(orgin);
    $Sdivs.find("div#updateaddA").hide();

    
}