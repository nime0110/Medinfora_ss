const contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
let file_arr = []; //첨부파일 업로드 배열

$(function() {

		//기존 값 넣어주기
	let category_val = $("#category_val").val();
	let title_val = $("#title_val").val();
	let content_val = $("#content_val").text();

	$("select[name='category']").val(category_val);
	$("input:text[name='title']").val(title_val);
	$("textarea[name='content']").val(content_val);


	$("input:file[name='attach']").hide();
	//변화시 부분 없애기
	$("select[name='category']").on("change",function(e){
		$(e.target).removeClass("warning");
	});
	
	$("input:text[name='title']").keyup(function(e){
		$(e.target).removeClass("warning");
	});
	
	$("textarea[name='content']").keyup(function(e){
		$(e.target).removeClass("warning");
	});
	
	$("input:password[name='pwd']").keyup(function(e){
		$(e.target).removeClass("warning");
	});
	
		
	//스마트에디터 구현 start ---- 
	var obj = [];
	
	//스마트에디터 프레임생성
	nhn.husky.EZCreator.createInIFrame({
			oAppRef: obj,
			elPlaceHolder: "content", // id가 content인 textarea에 에디터를 넣어준다.
			sSkinURI: contextPath + "/resources/smarteditor/SmartEditor2Skin.html",
			htParams : {
					// 툴바 사용 여부 (true:사용/ false:사용하지 않음)
					bUseToolbar : true,            
					// 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
					bUseVerticalResizer : false,    
					// 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
					bUseModeChanger : true,
			}
	});
	//스마트에디터 구현 end ---- 


	// 첨부파일 업로드 start -- 	
	$("div#fileDrop").on("dragenter", function(e){ //드롭대상인 박스 안에 Drag 한 파일이 최초로 들어왔을 때 
			e.preventDefault();
			e.stopPropagation();
	}).on("dragover", function(e){ // 드롭대상인 박스 안에 Drag 한 파일이 머물러 있는 중일 때
			e.preventDefault();
			e.stopPropagation();
			$(this).css("background-color", "#ffd8d8");
	}).on("dragleave", function(e){ //  Drag 한 파일이 드롭대상인 박스 밖으로 벗어났을 때  
			e.preventDefault();
			e.stopPropagation();
			$(this).css("background-color", "#fff");
	}).on("drop", function(e){      /* "drop" 이벤트는 드롭대상인 박스 안에서 Drag 한것을 Drop(Drag 한 파일(객체)을 놓는것) 했을 때. 필수이벤트이다. */
			e.preventDefault();
			var files = e.originalEvent.dataTransfer.files;  
			fileUpload(files);
			$(this).css("background-color", "#fff");
	});

	// 파일 직접 선택시 
	$("input:file[name='attach']").change(function(e) {
		var files = e.target.files;  
		//const fileName = $(this).val().split("\\").pop();
		fileUpload(files);
	});
	//기존에 들어있던 파일도 업데이트 함수에 포함

	// 파일목록 제거  
	$(document).on("click", "span.delete", function(e){
		
		// 기존에 있던 파일이 아니라 새로운 파일일 시에, 그러니까 file_arr 에 들어간 파일일 경우
		console.log("file_arr.length => " + file_arr.length);
		if(file_arr.length > 0) {
			let idx = $("span.delete").index($(e.target));
			file_arr.splice(idx,1); // file_arr 에서 파일을 제거 
			$(e.target).parent().remove(); // <div class='fileList'> 태그삭제   
			return;
		}
		

		let DelfileName = $("#fileName").val();
		let cidx = $("#cidx").val();

		deleteFile(DelfileName, cidx);
		$(e.target).parent().remove(); // <div class='fileList'> 태그삭제   
	});
	// 첨부파일 업로드 end -- 

	// 글쓰기 버튼 클릭시
	$("button#update").click(function(){
		update(obj);
	});

}); //end of $(function(){ ----------------------


//파일 업로드 함수
function fileUpload(files) {
	if(files != null && files != undefined){

		let html = "";
		const f = files[0]; 
		let fileSize = f.size/1024/1024;  /* 파일의 크기는 MB로 나타내기 위하여 /1024/1024 하였음 */
		
		if(fileSize >= 10) {
			alert("10MB 이상인 파일은 업로드할 수 없습니다.");
			$(this).css("background-color", "#fff");
			return;
		} else {
			file_arr.push(f); 
			const fileName = f.name; // 파일명	
		
			fileSize = fileSize < 1 ? fileSize.toFixed(3) : fileSize.toFixed(1);
			// 파일크기가 1MB 미만이라면 소수점 3째자리까지 나타내고, 1MB 이상이라면 소수점 1째자리까지 나타냄
			html += 
						"<div class='fileList'>" +
								"<span class='delete'>❌</span>" +   
								"<span class='fileName'>"+fileName+"</span>" +
								"<span class='fileSize'>"+fileSize+" MB</span>" +
								"<span class='clear'></span>" + 
						"</div>";
			$("div#fileDrop").append(html);
		}
	}// end of if(files != null && files != undefined)--------------------------
}

function update(obj){
	// 스마트에디터 검사	
	
	let success = false;
	
	if(obj== null || obj==""){
		return;
	}
	else {
		// 구분
		const category = $("select[name='category']");
		if(category.val() == "0"){
			alert("구분을 선택하세요");
			category.addClass("warning");
			return;
		}
		else{
			// 글제목
			const title = $("input:text[name='title']");
			if(title.val().trim() == ""){
				alert("제목을 입력하세요");
				title.addClass("warning");
				return;
			}
			else{
				// 글내용
				obj.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);
				
				// 글내용 유효성 검사(스마트에디터를 사용할 경우)
				let content_val = $("textarea[name='content']").val().trim();
				
				// 공백 &nbsp; 을 ""으로 변환
				content_val = content_val.replace(/&nbsp;/gi, "").trim();

				// HTML 태그를 제거
				content_val = content_val.replace(/<\/?[^>]+(>|$)/g, "").trim();

				if(content_val.length == 0) {
						alert("글내용을 입력하세요");
						return; // 종료
				} else{
					success = true;
			  }
			}
		}
		
	}

	if(success){

		let formData = new FormData($("form[name='commuwrite']").get(0)); // $("form[name='commuwrite']").get(0) 폼 에 작성된 모든 데이터 보내기 
      
		if(file_arr.length > 0) { // 파일첨부가 있을 경우 
			console.log("file_arr.length => " + file_arr.length);
			// 첨부한 파일의 총합의 크기가 10MB 이상 이라면 메일 전송을 하지 못하게 막는다.
			let sum_file_size = 0;
			for(let i=0; i<file_arr.length; i++) {
					sum_file_size += file_arr[i].size;
			}// end of for---------------
				
			if( sum_file_size >= 10*1024*1024 ) { // 첨부한 파일의 총합의 크기가 10MB 이상 이라면 
					alert("첨부한 파일의 총합의 크기가 10MB 이상이라서 파일을 업로드할 수 없습니다.!!");
				return; // 종료
			}
			else { // formData 속에 첨부파일 넣어주기
				file_arr.forEach(function(item){
							formData.append("file_arr", item);  // 첨부파일 추가하기.  "file_arr" 이 키값이고  item 이 밸류값인데 file_arr 배열속에 저장되어진 배열요소인 파일첨부되어진 파일이 되어진다.    
																									// 같은 key를 가진 값을 여러 개 넣을 수 있다.(덮어씌워지지 않고 추가가 된다.)
					});
			}
		}

		
		$.ajax({
			url : contextPath + "/commu/commuUpdateEnd.bibo",
			type : "post",
			data : formData,
			dataType:"json",
			contentType: false,
      processData: false,
			success:function(json){
					if(json.result == 1) {
						alert("글 등록에 성공했습니다.");
						location.href = contextPath + "/commu/commuList.bibo"; 
					}
					else {
						alert("글 등록에 실패했습니다.");
						location.href = contextPath + "/commu/commuWrite.bibo";
					}
			},
			error: function(request, status, error){
	alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		}
	});

	}
	else{
		alert("글 작성에 실패했습니다.");
		return;
	}
}

function deleteFile(fileName, cidx) {
	$.ajax({
		type: "post",
		url: contextPath + "/commu/commuDeleteFile.bibo",	
		data: {fileName: fileName, cidx: cidx},
		dataType: "json",
		success: function (json) {
			if (json.result != 1) {
				alert("파일 삭제 실패");
			} 
		}, 
		error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		}
	});
}
