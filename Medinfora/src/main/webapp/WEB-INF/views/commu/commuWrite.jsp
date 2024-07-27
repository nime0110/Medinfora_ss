<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String ctxPath = request.getContextPath();
%>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/commu/commuWrite.css" />
<script type="text/javascript" src="<%= ctxPath%>/resources/js/commu/commuWrite.js"></script>


<div class="container" style="margin-top: 100px;">
	<div class="py-4" align="center">
		<h2 class="nanum-eb size-n">글 작성하기</h2>
	</div>

	<form name="commuwrite" enctype="multipart/form-data">
		<table class="table" style="width: 100%;">
			<colgroup>
				<col style="width: 15%;">
				<col style="width: 60%;">
				<col style="width: 1%;">
			</colgroup>
			<tr class="" >
				<th style="border-top:solid 1px #d1d6db;">구분&nbsp;<span style="color: red;">&ast;</span></th>
				<td style="border-top:solid 1px #d1d6db;">
					<select class="col-4" name="category">
						<option value='0'>구분</option>
						<option value='1'>임신&middot;성고민</option>
						<option value='2'>다이어트&middot;헬스</option>
						<option value='3'>마음 건강</option>
						<option value='4'>탈모 톡톡</option>
						<option value='5'>피부 고민</option>
						<option value='6'>뼈와 관절</option>
						<option value='7'>영양제</option>
						<option value='8'>질환 고민</option>
						<option value='9'>자유게시판</option>		
					</select>
					<span class="e_cmt" name="e_cmt"></span>
				</td>
			</tr>

			<tr class="">
				<th>제목&nbsp;<span style="color: red;">&ast;</span></th>
					<td>
						<input class="nanum-n" type="text" name="title" placeholder="제목을 입력하세요" />
					</td>
				</tr>
			<tr class="">
				<th scope="row">내용&nbsp;<span style="color: red;">&ast;</span></th>
				<td>
					 <textarea style="width: 100%; height: 612px;" name="content" id="content"></textarea>
				</td>
			</tr>
			<tr class="">
				<th>첨부파일</th>
					<td>
						<label class="add_file w-100" for="attach">
							<span style="padding-top: 0.4rem;" id='filename'>업로드할 파일을 선택하세요</span>
							<span class="nanum-b" id="filechoice">파일 선택</span>
						</label>
							<input id="attach" name="attach" type="file" />
							<div id="fileDrop" class="fileDrop border border-secondary"></div>
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
