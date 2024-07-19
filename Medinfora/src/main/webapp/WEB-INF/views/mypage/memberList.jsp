<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String ctxPath = request.getContextPath();
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="<%= ctxPath %>/resources/css/header.css" />
<link rel="stylesheet" type="text/css" href="<%= ctxPath %>/resources/css/fontcss.cs"/>
<script type="text/javascript">
$(document).ready(function(){
	   
	
	   // === 부서번호 체크박스 유지시키기 시작 === //
	   const str_deptId = "${requestScope.str_deptId}";
	   // console.log("확인용 : str_deptId => "+str_deptId);
	   // 확인용 : str_deptId => 20,30,50,90
	   
	   if(str_deptId != ""){
	      const arr_deptId = str_deptId.split(",");
	      
	      $("input:checkbox[name='deptId']").each(function(index, elmt){
	         for(let i=0; i<arr_deptId.length; i++){
	            if($(elmt).val() == arr_deptId[i]){
	               $(elmt).prop("checked", true);
	               break;
	            } 
	         }// end of for----
	      });
	   }
	   // === 부서번호 체크박스 유지시키기 끝 === //
	   
	   // == 성별 체크박스 유지시키기 시작 == //
	   const gender = "${requestScope.gender}";
	   
	   if(gender != ""){
	      $("select[name='gender']").val(gender);
	   }
	   // == 성별 체크박스 유지시키기 끝 == //
	      
	        
	
	   
	    // 검색하기 버튼 클릭시
	   $("button#btnSearch").click(function(){
	         
	       // const arr_deptId = [];
	        // 또는
	        const arr_deptId = new Array();
	         
	        $("input:checkbox[name='deptId']:checked").each(function(index, item){
	           arr_deptId.push($(item).val());            
	        });
	         
	        const str_deptId = arr_deptId.join()
	         
	        // console.log("확인용 str_deptId : ", str_deptId);
	        // 확인용 str_deptId :  -9999,30,40,50
	         
	        const frm = document.searchFrm;
	     
	        
const frm = document.editFrm;
// 이거 고치셈 frm.str_deptId.value = str_deptId;
frm.method = "GET";
frm.action = "<%= ctxPath%>/mypage/memberList.info";
frm.submit();
    const userid = "${requestScope.userid}";
    if(userid != ""){
        const arr_userid = userid.split(",");
        $("input:checkbox[name='userid']").each(function(index, elmt){
            for(let i=0; i<arr_userid.length; i++){
                if($(elmt).val() == arr_userid[i]){
                    $(elmt).prop("checked", true);
                    break;
                }
            }
        });
    }
    const mbr_division = "${requestScope.mbr_division}";
    if(mbr_division != ""){
        $("select[name='mbr_division']").val(mbr_division);
    }
    $("button#btnSearch").click(function(){
        const arr_userid = [];
        $("input:checkbox[name='userid']:checked").each(function(index, item){
            arr_userid.push($(item).val());
        });
        const userid = arr_userid.join();
        const frm = document.searchFrm;
        frm.userid.value = userid;
        frm.method = "GET";
        frm.action = "<%= ctxPath %>/mypage/memberList.bibo";
        frm.submit();
    });

    $(document).on("click", ".btnManage", function(){
        const userid = $(this).data("userid");
        $.ajax({
            url: "<%=ctxPath%>/mypage/getMemberDetail.bibo",
            type: "GET",
            data: { userid: userid },
            dataType: "json",
            success: function(response) {
                if (response.success) {
                    // 모달에 데이터 채우기
                    $('#userid').val(response.userid);
                    $('#name').val(response.name);
                    $('#email').val(response.email);
                    $('#mobile').val(response.mobile);
                    $('#address').val(response.address);
                    $('#detailAddress').val(response.detailAddress);
                    $('#mbr_division').val(response.mbr_division);
                    // 모달 표시
                    $('#memberDetailModal').modal('show');
                } else {
                    alert(response.message);
                }
            },
            error: function(xhr, status, error) {
                console.error("AJAX 요청 실패:", status, error);
                alert("회원 정보를 불러오는데 실패했습니다.");
            }
        });
    });
    $(".close, button[data-dismiss='modal']").click(function(){
        $("#memberDetailModal").modal('hide');
    });
    $("#btnSave").click(function(){
        const formData = $("#editMemberForm").serialize();
        $.ajax({
            url: "<%= ctxPath %>/mypage/saveMemberDetail.bibo",
            type: "POST",
            data: formData,
            success: function(response) {
                var data = JSON.parse(response);
                if (data.success) {
                    alert("회원 정보가 성공적으로 저장되었습니다.");
                    $("#memberDetailModal").modal('hide');
                    
                    location.reload(); // 페이지를 새로고침하여 변경사항을 반영
                } else {
                    alert(data.message);
                }
            },
            error: function(xhr, status, error) {
                alert("회원 정보를 저장하는데 실패했습니다.");
            }
        });
    });

    $("#btnDelete").click(function(){
        const userid = $("#userid").val();
        if (confirm("정말로 회원을 탈퇴시키겠습니까?")) {
            $.ajax({
                url: "<%= ctxPath %>/mypage/deleteMember.bibo",
                type: "GET",
                data: { userid: userid },
                success: function(response) {
                    var data = JSON.parse(response);
                    if (data.success) {
                        alert("회원이 성공적으로 탈퇴되었습니다.");
                        $("#memberDetailModal").modal('hide');
                        location.reload(); // 페이지를 새로고침하여 변경사항을 반영
                    } else {
                        alert(data.message);
                    }
                },
                error: function(xhr, status, error) {
                    alert("회원 탈퇴에 실패했습니다.");
                }
            });
        }
    });
});
</script>

<div class="container" style="padding:3% 0;">

	<p class="text-center nanum-b size-n"> 회원 전체 목록</p>
	
<form name="searchFrm">
    <c:if test="${not empty requestScope.mbrList}">
        <span style="display: inline-block;">회원 구분 </span>
        <c:forEach var="userid" items="${requestScope.mbrList}" varStatus="status">
            <label for="${status.index}">
                <c:if test="${userid == -9999}">
                    회원 없음
                </c:if>
                <c:if test="${userid != -9999}">
                    ${userid}
                </c:if>
            </label>
            <input type="checkbox" id="${status.index}" name="userid" value="${userid}">&nbsp;&nbsp;
        </c:forEach>
    </c:if><form>
		<fieldset>
			<div class="p-4 searchBar" align="center">
				<span>
					<select class="sclist search_ch sel_0 nanum-b">
						   <option value="">회원 선택</option>
        <option value="0">관리자</option>
        <option value="1">일반회원</option>
        <option value="2">의료종사자</option>
        <option value="3">일반회원(휴면)</option>
        <option value="4">의료종사자(휴면)</option>
        <option value="8">정지회원</option>
        <option value="9">탈퇴회원</option>
					</select>
				</span>
				<span>
					<input class="inputsc search_ch sel_1 nanum-b" name="search" type="text" placeholder="검색어를 입력해주세요." />
					<input type="text" style="display: none;"/>		<%-- 전송방지 --%>
				</span>
				<span>
					<button class="jh_btn_design search nanum-eb size-s" type="button">검색</button>
				</span>
			</div>
		</fieldset>
	</form>
    <input type="hidden" name="userid" />
    
  
    <button type="button" class="btn btn-secondary btn-sm">엑셀파일 업로드 </button> 
   

<br>

<table id="memberTbl" class="table">
    <thead>
        <tr>
            <th>아이디</th>
            <th>회원 유형</th>
            <th>성명</th>
            <th>성별</th>
            <th>가입일자</th>
            <th>관리</th>
        </tr>
    </thead>
    <tbody>
        <c:if test="${not empty memberList}">
            <c:forEach var="member" items="${memberList}">
                <tr>
                    <td>${member.userid}</td>
                    <td>
                         <c:choose>
 						   <c:when test="${member.mIdx == 0}">관리자</c:when>
 						     <c:when test="${member.mIdx == 1}">일반회원</c:when>
                            <c:when test="${member.mIdx == 2}">의료종사자</c:when>
                              <c:when test="${member.mIdx == 3}">일반회원(휴면)</c:when>
                                <c:when test="${member.mIdx == 4}">의료종사자(휴면)</c:when>
                                  <c:when test="${member.mIdx == 8}">정지회원</c:when>
                                    <c:when test="${member.mIdx == 9}">탈퇴회원</c:when>
                            <c:otherwise>일반회원</c:otherwise>
                        </c:choose>
                    </td>
                    <td>${member.name}</td>
                    <td>
                        <c:choose>
                            <c:when test="${member.gender == 1}">M</c:when>
                            <c:otherwise>F</c:otherwise>
                        </c:choose>
                    </td>
                    <td>${member.registerday}</td>
                    <td>
                        <button type="button" class="btn btn-info btn-sm btnManage" data-userid="${member.userid}">관리</button>
                    </td>
                </tr>
            </c:forEach>
        </c:if>
    </tbody>
</table>
</div>

<!-- 회원 상세정보 모달 -->
<div class="modal fade" id="memberDetailModal" tabindex="-1" role="dialog" aria-labelledby="memberDetailModalLabel" aria-hidden="true">
<div class="modal-dialog" role="document">
    <div class="modal-content">
        <div class="modal-header">
            <h5 class="modal-title" id="memberDetailModalLabel">회원 상세 정보</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
        <div class="modal-body">
            <form id="editMemberForm">
                <div class="form-group">
                    <label for="userid">아이디</label>
                    <input type="text" class="form-control" id="userid" name="userid" readonly>
                </div>
                <div class="form-group">
                    <label for="name">성명</label>
                    <input type="text" class="form-control" id="name" name="name">
                </div>
                <div class="form-group">
                    <label for="email">이메일</label>
                    <input type="email" class="form-control" id="email" name="email">
                </div>
                <div class="form-group">
                    <label for="mobile">연락처</label>
                    <input type="text" class="form-control" id="mobile" name="mobile">
                </div>
                <div class="form-group">
                    <label for="address">주소</label>
                    <input type="text" class="form-control" id="address" name="address">
                </div>
                <div class="form-group">
                    <label for="detailAddress">상세주소</label>
                    <input type="text" class="form-control" id="detailAddress" name="detailAddress">
                </div>
                <div class="form-group">
                    <label for="mbr_division">회원 구분</label>
                    <select class="form-control" id="mbr_division" name="mbr_division">
                        <option value="0">관리자</option>
                        <option value="1">일반회원</option>
                        <option value="2">의료종사자</option>
                           <option value="8">정지회원</option>
                        <option value="9">탈퇴회원</option>
                    </select>
                </div>
            </form>
        </div>
           <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
                <button type="button" class="btn btn-primary" id="btnSave">저장</button>
                <button type="button" class="btn btn-danger" id="btnDelete">탈퇴시키기</button>
            </div>
        </div>
    </div>
</div>
</div>