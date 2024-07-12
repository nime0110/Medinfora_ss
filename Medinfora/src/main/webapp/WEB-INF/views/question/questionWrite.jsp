<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String ctxPath = request.getContextPath();
%>

<style>


table input:focus, select {
  outline: none;
}


button.write {
  width: 120px;
  height: 50px;
  background-color: white;
  border: solid 1px var(--text-black-color);
  font-size: 1rem;
  border-radius: 0.5rem;
  
}

button.write_w:hover{
  background-color: var(--primary-background-color);
  color: var(--text-white-color);
  border: none;
  
}

button.write_c:hover{
  background-color: #8b95a1;
  border: none;
  
}


.container {
  padding: 0 10%;
}


.table tr {
  vertical-align: middle;
}

.table th {
  padding-left: 2%;
  background-color: #d8eaff;
}

.table select{
  border: 1px solid #d1d6db;
  height: 40px;
  padding-left: 15px;
}

.table input{
  border: 1px solid #d1d6db;
  padding-left: 15px;
  width: 100%;
  height: 40px;
}

.add_file{
  height: 40px;
  border: 1px solid #d1d6db;
  padding: 0 0 0 0.8rem;
  display: flex;
  border-radius: 0.6rem;
  background-color: #f9fafb;

}

.add_file:hover{
  cursor: pointer;

}

span#filechoice{
  padding: 0.3rem 1.5rem;
  border: 1px solid #d1d6db;
  border-radius: 0.5rem;
  margin-left: auto;
  background-color: #f2f4f6;
  color: black;
}

span#filechoice:hover{
  background-color: #d1d6db;
}

.warming {
	border: solid 1px red !important;
}



</style>


<script type="text/javascript">

$(document).ready(function() {
	$("input:file[name='filesrc']").hide();
	
	
	
	// 공개여부에 따라 비밀번호 활성화
	$("select[name='open']").on("change", function(e){
		// console.log($("select[name='open']").val());
		// console.log(typeof($("select[name='open']").val()));
		if($(e.target).val() == "2"){
			$("input:password[name='pwd']").prop("disabled", false);
		}
		else{
			$("input:password[name='pwd']").prop("disabled", true);
			$("input:password[name='pwd']").val("");

		}
	});
	
	// 파일명 보여주기
	$("input:file[name='filesrc']").change(function() {
        const fileName = $(this).val().split("\\").pop();
        $("span#filename").text(fileName);
    });
	
	
	// 변화 시 빨간거 없에기
	$("select[name='subject']").on("change",function(e){
		$(e.target).removeClass("warming");
	});
	
	$("input:text[name='title']").keyup(function(e){
		$(e.target).removeClass("warming");
	});
	
	$("textarea[name='content']").keyup(function(e){
		$(e.target).removeClass("warming");
	});
	
	$("input:password[name='pwd']").keyup(function(e){
		$(e.target).removeClass("warming");
	});
	
	

	
	<%-- 스마트 에디터 --%>
	var obj = [];
    
    //스마트에디터 프레임생성
    nhn.husky.EZCreator.createInIFrame({
        oAppRef: obj,
        elPlaceHolder: "content", // id가 content인 textarea에 에디터를 넣어준다.
        sSkinURI: "<%= ctxPath%>/resources/smarteditor/SmartEditor2Skin.html",
        htParams : {
            // 툴바 사용 여부 (true:사용/ false:사용하지 않음)
            bUseToolbar : true,            
            // 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
            bUseVerticalResizer : false,    
            // 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
            bUseModeChanger : true,
        }
    });
    
    $("button#write").click(function(){
    	gowrite(obj);
    });

});// end of $(document).ready(function()

		
// Function Declaration
function goback(){
	location.href="<%=ctxPath%>/questionList.bibo";
}
	
function gowrite(obj){
	
	console.log(obj);
	
	let is_ok = false;
	
	if(obj== null || obj==""){
		return;
	}
	else{
		
		// 구분
		const subject = $("select[name='subject']");
		if(subject.val() == "0"){
			alert("구분을 선택하세요");
			subject.addClass("warming");
			return;
		}
		else{
			// 글제목
			const title = $("input:text[name='title']");
			if(title.val().trim() == ""){
				alert("제목을 입력하세요");
				title.addClass("warming");
				return;
			}
			else{
				// 글내용
				obj.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);
				
				let content = $("textarea[name='content']").val().trim();
				content = content.replace(/&nbsp;/gi, ""); // 공백(&nbsp;)을 "" 으로 변환 
				   
				content = content.substring(content.indexOf("<p>")+3);
				content = content.substring(0, content.indexOf("</p>"));
				
				console.log(content);
				   
			   	if(content.trim().length == 0){
					alert("내용을 입력하세요");
					return;
				}
			   	else{
			   		const open = $("select[name='open']").val();
			   	   	const pwd = $("input:password[name='pwd']");
			   	   	
			   	   	if(open == "2"){
			   	   		if(pwd.val() == ""){
			   	   			alert("비밀번호를 입력하세요");
			   	   			pwd.addClass("warming");
			   	   			return;
			   	   		}
			   	   		else{
			   	   			is_ok = true;
			   	   		}
			   	   	}
			   	   	else{
			   	   		is_ok = true;
			   	   	}
			   	}
			}
		}
		
	}

   	if(is_ok){
		const frm = document.qwrite;
	   	frm.method = "post";
	   	frm.action = "<%= ctxPath%>/questionWriteEnd.bibo";
	   	frm.submit();
   	}
   	else{
   		alert("안됐어요");
   		return;
   	}

	
}

	
</script>



<div class="container" style="margin-top: 100px;">
	<div class="py-4" align="center">
		<h2 class="nanum-eb size-n">묻고 답하기</h2>
	</div>



	<form name="qwrite" enctype="multipart/form-data">
		<table class="table" style="width: 100%;">
			<colgroup>
				<col style="width: 15%;">
				<col style="width: 60%;">
				<col style="width: 1%;">
			</colgroup>


			<tr class="" >
				<th style="border-top:solid 1px #d1d6db;">구분&nbsp;<span style="color: red;">&ast;</span></th>
				<td style="border-top:solid 1px #d1d6db;">
					<select class="col-4" name="subject">
						<option value="0">구분</option>
						<option value="1">건강상담</option>
						<option value="2">식생활,식습관</option>
						<option value="3">의약정보</option>
	
					</select>
					<span class="e_cmt" name="e_cmt"></span>
				</td>
			</tr>

			<tr class="">
				<th>질문제목&nbsp;<span style="color: red;">&ast;</span></th>
					<td>
						<input class="nanum-n" type="text" name="title" placeholder="제목을 입력하세요" />
					</td>
				</tr>

			<tr class="">
				<th scope="row">질문내용&nbsp;<span style="color: red;">&ast;</span></th>
				<td>
					 <textarea style="width: 100%; height: 612px;" name="content" id="content"></textarea>
				</td>
			</tr>

			<tr class="">
				<th>첨부파일</th>
					<td>
						<label class="add_file w-100" for="filesrc">
							<span style="padding-top: 0.4rem;" id='filename'>업로드할 파일을 선택하세요</span>
							<span class="nanum-b" id="filechoice">파일 선택</span>
						</label>
						<input id="filesrc" name="filesrc" type="file" />
					</td>
				</tr>

			<tr class="">
				<th scope="row">공개여부</th>
				<td>
					<select class="col-4" id="open" name="open">
						<option value="1">공개</option>
						<option value="2">비공개</option>
	
					</select>
				</td>
			</tr>

			<tr class="">
				<th scope="row">비밀번호</th>
					<td>
						<div class="col-4">
							<input name="pwd" type="password" placeholder="숫자 4자리" maxlength="4" disabled/>
						</div>
					</td>
				</tr>
		</table>
		<input type="hidden" name="userid" value="${sessionScope.loginuser.userid}"/>
		
	</form>

	<div class="text-center my-5">
		<button class="write mx-5 write_w nanum-b" id="write" >등록</button>
		<button class="write write_c nanum-b" onclick="goback()">취소</button>
	</div>


</div>





