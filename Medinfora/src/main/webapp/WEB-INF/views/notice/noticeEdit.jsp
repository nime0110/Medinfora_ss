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
	  
	  <%-- === #167.-2 스마트 에디터 구현 시작 === --%>
	 	//전역변수
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
	            bUseVerticalResizer : true,    
	            // 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
	            bUseModeChanger : true,
	        }
	    });
	   <%-- === 스마트 에디터 구현 끝 === --%>
	   
	   // 수정완료 버튼
	   $("button#btnUpdate").click(function(){
		   
		   <%-- === 스마트 에디터 구현 시작 === --%>
			// id가 content인 textarea에 에디터에서 대입
	        obj.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);
		   <%-- === 스마트 에디터 구현 끝 === --%>
		   
		   // 글제목 유효성 검사
		   const title = $("input:text[name='title']").val().trim();
		   if(title == ""){
			   alert("글제목을 입력하세요!!");
			   $("input:text[name='title']").val("");
			   return; // 종료
		   }
		   
		   // 글내용 유효성 검사(스마트 에디터를 사용할 경우)
		   let content_val = $("textarea[name='content']").val().trim();
		   
		// alert(content_val); // content 에 공백만 여러개를 입력하여 쓰기할 경우 알아보는 것. 
		// <p>&nbsp; &nbsp; &nbsp;&nbsp;</p> 이라고 나온다.
		
		   content_val = content_val.replace(/&nbsp;/gi, ""); // 공백(&nbsp;)을 "" 으로 변환 
		   /*    
			     대상문자열.replace(/찾을 문자열/gi, "변경할 문자열");
			   ==> 여기서 꼭 알아야 될 점은 나누기(/)표시안에 넣는 찾을 문자열의 따옴표는 없어야 한다는 점입니다. 
			               그리고 뒤의 gi는 다음을 의미합니다.
			
			   g : 전체 모든 문자열을 변경 global
			   i : 영문 대소문자를 무시, 모두 일치하는 패턴 검색 ignore
		   */
		// alert(content_val);
		// <p>    </p>
		
		   content_val = content_val.substring(content_val.indexOf("<p>")+3);
		   content_val = content_val.substring(0, content_val.indexOf("</p>"));
		   
		   if(content_val.trim().length == 0){
			   alert("글내용을 입력하세요!!");
			   return; // 종료
		   }
		   
		  
		   
		   // 폼(form)을 전송(submit)
		   const frm = document.editFrm;
		   frm.method = "post";
		   frm.action = "<%= ctxPath%>/editEnd.bibo";
		   frm.submit();
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
    <!-- 다른 폼 요소들 -->
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
                <div class="notice-attachment">
    <c:if test="${noticedto.filename != null}">
   
        <span class="attach_sh">
        <img src="<%=ctxPath%>/resources/img/sh_attach.png"
								style="width: 20px; height: 20px;">첨부파일 : </span>
        <a href="<%= ctxPath %>/download.action?seq=${noticedto.nidx}" class="attachment-filename">${noticedto.orgname}</a>
        <c:if test="${noticedto.filename == 'image'}">
            <img src="<%= ctxPath %>/download.action?seq=${noticedto.nidx}" alt="${noticedto.orgname}" />
        </c:if>
    </c:if>
</div>
            </td>
        </tr>    
    </table>
    <div style="margin: 20px;">
        <button type="button" class="btn btn-secondary btn-sm mr-3" id="btnUpdate">글수정</button>
        <button type="button" class="btn btn-secondary btn-sm" onclick="javascript:history.back()">취소</button>  
    </div>
</form>
     </div>
  </div>
</div>
    