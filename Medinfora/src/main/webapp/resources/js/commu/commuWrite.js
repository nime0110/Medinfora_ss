const contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
let file_arr = []; //첨부파일 업로드 배열

$(function() {
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

	// 파일목록 제거  
	$(document).on("click", "span.delete", function(e){
		let idx = $("span.delete").index($(e.target));
	
		file_arr.splice(idx,1); // file_arr 에서 파일을 제거 
	
		$(e.target).parent().remove(); // <div class='fileList'> 태그삭제   
	});
	// 첨부파일 업로드 end -- 

	// 글쓰기 버튼 클릭시
	$("button#write").click(function(){
		write(obj);
	});

}); //end of $(function(){ ----------------------

function goback(){
	location.href = contextPath + "/commu/commuList.bibo";
}


function fileUpload(files) {
	if(files != null && files != undefined){
		console.log("files.length 는 => " + files.length);     
		/*
		for(let i=0; i<files.length; i++){
					const f = files[i];
					const fileName = f.name;  // 파일명
					const fileSize = f.size;  // 파일크기
					console.log("파일명 : " + fileName);
					console.log("파일크기 : " + fileSize);
		} // end of for------------------------
			*/
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

function write(obj){
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
				
				let content = $("textarea[name='content']").val().trim();
				content = content.replace(/&nbsp;/gi, ""); // 공백(&nbsp;)을 "" 으로 변환 
				   
				content = content.substring(content.indexOf("<p>")+3);
				content = content.substring(0, content.indexOf("</p>"));
				
				console.log(content);
				   
			  if(content.trim().length == 0){
					alert("내용을 입력하세요");
					return;
				} else{
					success = true;
			  }
			}
		}
		
	}

	if(success){

		let formData = new FormData($("form[name='commuwrite']").get(0)); // $("form[name='commuwrite']").get(0) 폼 에 작성된 모든 데이터 보내기 
      
		if(file_arr.length > 0) { // 파일첨부가 있을 경우 
				
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
			url : contextPath + "/commu/commuWriteEnd.bibo",
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
