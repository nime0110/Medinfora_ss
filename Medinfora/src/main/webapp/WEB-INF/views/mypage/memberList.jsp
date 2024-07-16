<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String ctxPath = request.getContextPath();
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" type="text/css" href="<%= ctxPath %>/resources/css/header.css" />
<link rel="stylesheet" type="text/css" href="<%= ctxPath %>/resources/css/fontcss.cs"/>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	// 회원 구분 체크박스 유지시키기
	const str_mbrId = "${requestScope.str_mbrId}";
	if(str_mbrId != ""){
		const arr_mbrId = str_mbrId.split(",");
		$("input:checkbox[name='mbrId']").each(function(index, elmt){
			for(let i=0; i<arr_mbrId.length; i++){
				if($(elmt).val() == arr_mbrId[i]){
					$(elmt).prop("checked", true);
					break;
				}
			}
		});
	}

	// 회원 구분 셀렉트 박스 유지시키기
	const mbr_division = "${requestScope.mbr_division}";
	if(mbr_division != ""){
		$("select[name='mbr_division']").val(mbr_division);
	}

	// 검색하기 버튼 클릭 시
	$("button#btnSearch").click(function(){
		const arr_mbrId = [];
		$("input:checkbox[name='mbrId']:checked").each(function(index, item){
			arr_mbrId.push($(item).val());
		});
		const str_mbrId = arr_mbrId.join();
		const frm = document.searchFrm;
		frm.str_mbrId.value = str_mbrId;
		frm.method = "GET";
		frm.action = "<%= ctxPath %>/emp/memberList.bibo";
		frm.submit();
	});
});
</script>

<div class="container" style="padding:3% 0;">
	<p class="text-center nanum-b size-n">회원 전체 목록</p>
	
	<form name="searchFrm">
		<c:if test="${not empty requestScope.mbrList}">
			<span style="display: inline-block; width: 150px; font-weight: bold;">회원 구분 </span>
			<c:forEach var="mbrId" items="${requestScope.mbrList}" varStatus="status">
				<label for="${status.index}">
					<c:if test="${mbrId == -9999}">
						회원 없음
					</c:if>
					<c:if test="${mbrId != -9999}">
						${mbrId}
					</c:if>
				</label>
				<input type="checkbox" id="${status.index}" name="mbrId" value="${mbrId}">&nbsp;&nbsp;
			</c:forEach>
		</c:if>
		<input type="hidden" name="str_mbrId" />
		<select name="mbr_division" style="height: 30px; width: 120px; margin: 10px 30px 0 0;">
			<option value="">회원 선택</option>
			<option value="0">관리자</option>
			<option value="1">일반회원</option>
			<option value="2">의료회원</option>
		</select>
		<button type="button" class="btn btn-secondary btn-sm" id="btnSearch">검색하기</button>
		&nbsp;&nbsp;
		<button type="button" class="btn btn-success btn-sm" id="btnExcel">Excel파일로 저장</button>
	</form>
	<br>

	<!-- 엑셀 파일 업로드 -->
	<form style="margin-bottom: 10px;" name="excel_upload_frm" method="post" enctype="multipart/form-data">
		<input type="file" id="upload_excel_file" name="excel_file" accept=".csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" />
		<br>
		<button type="button" class="btn btn-info btn-sm" id="btn_upload_excel" style="margin-top: 1%;">Excel 파일 업로드 하기</button>
	</form>

	<table id="memberTbl">
		<thead>
			<tr>
				<th>아이디</th>
				<th>회원 유형</th>
				<th>성명</th>
				<th>주소</th>
				<th>생년월일</th>
				<th>연락처</th>
				<th>성별</th>
				<th>가입일자</th>
				<th>회원 상태</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${not empty memberList}">
				<c:forEach var="member" items="${memberList}">
					<tr>
						<td>${member.userid}</td>
						<td><c:choose>
								<c:when test="${member.loginmethod == 0}">일반회원</c:when>
								<c:when test="${member.loginmethod == 1}">카카오회원</c:when>
								<c:otherwise>기타</c:otherwise>
							</c:choose>
						</td>
						<td>${member.name}</td>
						<td>${member.address} ${member.detailAddress}</td>
						<td>${member.birthday}</td>
						<td>${member.mobile}</td>
						<td><c:choose>
								<c:when test="${member.gender == 1}">남성</c:when>
								<c:otherwise>여성</c:otherwise>
							</c:choose>
						</td>
						<td>${member.registerday}</td>
						<td><c:choose>
								<c:when test="${member.requirePwdChange}">비밀번호 변경 필요</c:when>
								<c:otherwise>정상</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
</div>
