<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	String ctxPath = request.getContextPath();
%>

<link rel="stylesheet" type="text/css" href="<%= ctxPath %>/resources/css/notice/notice.css" />
<link rel="stylesheet" type="text/css" href="<%= ctxPath %>/resources/css/notice/noticeMedia.css" />

<script type="text/javascript" src="<%= ctxPath %>/resources/js/notice/notice.js"></script>
<script type="text/javascript" src="<%= ctxPath %>/resources/smarteditor/js/HuskyEZCreator.js" charset="utf-8"></script>
<script type="text/javascript">


  $(document).ready(function(){
	  
	  // === 스마트 에디터 구현 시작 ===
	  var obj = [];
	    
	  nhn.husky.EZCreator.createInIFrame({
	        oAppRef: obj,
	        elPlaceHolder: "content",
	        sSkinURI: "<%= ctxPath%>/resources/smarteditor/SmartEditor2Skin.html",
	        htParams : {
	            bUseToolbar : true,
	            bUseVerticalResizer : true,
	            bUseModeChanger : true,
	        }
	    });
	   
	   // 수정완료 버튼
	   $("button#btnUpdate").click(function(){
		   
		   obj.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);
		   
		   const title = $("input:text[name='title']").val().trim();
		   if(title == ""){
			   alert("글제목을 입력하세요!!");
			   $("input:text[name='title']").val("");
			   return;
		   }
		   
		   let content_val = $("textarea[name='content']").val().trim();
		   content_val = content_val.replace(/&nbsp;/gi, "");
		   content_val = content_val.substring(content_val.indexOf("<p>")+3);
		   content_val = content_val.substring(0, content_val.indexOf("</p>"));
		   
		   if(content_val.trim().length == 0){
			   alert("글내용을 입력하세요!!");
			   return;
		   }
		   
		let img_val = $("")
		   
		   const fileInput = $("input[name='attach']").val();
           console.log("fil" , $("input[name='attach']").val());
		 
       	/* 	if (fileInput.length > 0) {
	           const file = fileInput.files[0];
	       
	           $("input[name='filename']").val(file.name);
	           $("input[name='orgname']").val(file.name);
	           $("input[name='filesize']").val(file.size); 
	       }
		   */
           const frm = document.editFrm;
           frm.method = "post";
           frm.action = "<%= ctxPath%>/notice/editEnd.bibo";
           frm.submit();
	   });

	   $("#btnDeleteFile").click(function() {
		    if(confirm("첨부파일을 삭제하시겠습니까?")) {
		        $("input[name='deleteFile']").val("true");
		        $("#currentFile").hide();
		        $("#attach").val(""); // 파일 입력 필드 초기화
		    }
		});

	   
	   
	   
  });// end of $(document).ready(function(){})-----------

</script>

<div class="board_wrap">
    <div class="board_title11">
		   <p class="nanum-eb size-b">글 수정</p>
     </div>
      <div style="display: flex;">
        <div style="margin: auto; padding-left: 3%;">
  
<form name="editFrm" method="post" enctype="multipart/form-data" action="<%=ctxPath %>/notice/editEnd.bibo">
    <input type="hidden" name="nidx" value="${noticedto.nidx}" />
    <input type="hidden" name="deleteFile" value="false" />
    <table style="width: 1024px" class="table table-bordered">
        <tr>
            <th style="width: 15%;">제목</th>
            <td>
                <input type="text" name="title" size="103" maxlength="200" value="${noticedto.title}" />
            </td>
        </tr>
        <tr>
            <th style="width: 15%;">내용</th>
            <td>
                <textarea style="width: 100%; height: 612px;" name="content" id="content">${requestScope.noticedto.content}</textarea>
            </td>
        </tr>
    <tr>
    <th style="width: 15%;">파일첨부</th>
    <td>
        <input id="attach" type="file" name="attach" />
        <div id="currentFile" class="notice-attachment">
            <c:if test="${not empty noticedto.filename}">
                <span class="attach_sh">
                    <img src="<%=ctxPath%>/resources/img/sh_attach.png" style="width: 20px; height: 20px;">첨부파일 :
                </span>
                <p>${noticedto.orgname} <button type="button" id="btnDeleteFile">X</button></p>
            </c:if>
            
            
        </div>
        <input type="hidden" name="deleteFile" value="false" />
    </td>
</tr>
    </table>
    <div class="--font" style="margin: 20px;">
        <button type="button" class="btn btn-secondary btn-sm mr-3" id="btnUpdate">글수정</button>
        <button type="button" class="btn btn-secondary btn-sm" onclick="javascript:history.back()">취소</button>  
    </div>
</form>
     </div>
  </div>
</div>
