<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String ctxPath = request.getContextPath();
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" type="text/css" href="<%= ctxPath %>/resources/css/fontcss.css"/>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<style>
.modal-dialog {
    display: flex;
    align-items: flex-start;
    min-height: calc(100% - 1rem);
    padding-top: 10vh;
    max-width: 45%; /* 모달 다이얼로그의 최대 너비를 화면의 80%로 설정 */
    margin: 0 auto; /* 중앙 정렬 */
}

.modal-content {
    border-radius: 15px;
    padding: 20px;
    background-color: #fff;
    box-shadow: 0 5px 15px rgba(0, 0, 0, 0.5);
    width: 100%; /* 모달 컨텐츠를 다이얼로그 너비에 맞춤 */
}




@media (max-width: 768px) {
    .modal-dialog {
        max-width: 95%; /* 작은 화면에서는 화면의 95% 너비 사용 */
    }
}

div.pagebar{
	display: grid;
    place-items: center;
	margin-top: 10px;
}
</style>
<script type="text/javascript">
$(document).ready(function(){
	
	
	
// 페이지 번호가 있는 지 확인
    if (document.querySelector("input[name='pageNo']")) {
    
    } else {
        console.error("페이지 번호를 찾을 수 없습니다");
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

    // 회원 관리 버튼 클릭 이벤트
    $(document).on("click", ".btnManage", function(){
        const userid = $(this).data("userid");
        $.ajax({
            url: "<%= ctxPath %>/mypage/getMemberDetail.bibo",
            type: "GET",
            data: { userid: userid },
            dataType: "json",
            success: function(response) {
                if (response.success) {
                    // 모달에 데이터 채우기
                    $('#useridText').text(response.userid);
                    $('#nameText').text(response.name);
                    $('#emailText').text(response.email);
                    $('#mobileText').text(response.mobile);
                    $('#addressText').text(response.address);
                    $('#detailAddressText').text(response.detailAddress);
                    $('#userid').val(response.userid);
                    $('#mIdx').val(response.mbr_division);

                    // 회원 구분 설정
                    let memberType = '';
                    switch(response.mbr_division) {
                        case 0: memberType = '관리자'; break;
                        case 1: memberType = '일반회원'; break;
                        case 2: memberType = '의료종사자'; break;
                        case 3: memberType = '일반회원(휴면)'; break;
                        case 4: memberType = '의료종사자(휴면)'; break;
                        case 8: memberType = '정지회원'; break;
                        case 9: memberType = '탈퇴회원'; break;
                        default: memberType = '일반회원';
                    }
                    $('#memberTypeText').text(memberType);
                    // QNA 글 올린 갯수 
                    
                    if (response.mbr_division == 1) {
                        $('#memberWriteCount').text(response.postCount);
                        $('#memberWriteCount').closest('tr').show();
                    } else {
                        $('#memberWriteCount').closest('tr').hide();
                    }
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

    // 모달 닫기
    $(".close, button[data-dismiss='modal']").click(function() {
        $("#memberDetailModal").modal('hide');
    });

   


    // 회원 정지
    $("#btnStop").click(function() {
        const userid = $("#userid").val();
        if (confirm("정말로 회원을 정지 시키겠습니까?")) {
            $.ajax({
                url: "<%= ctxPath %>/mypage/StopMember.bibo",
                type: "GET",
                data: { userid: userid },
                success: function(response) {
                    var data = JSON.parse(response);
                    if (data.success) {
                        alert("회원이 성공적으로 정지되었습니다.");
                        $("#memberDetailModal").modal('hide');
                        location.reload(); // 페이지를 새로고침하여 변경사항을 반영
                    } else {
                        alert(data.message);
                    }
                },
                error: function(xhr, status, error) {
                    alert("회원 정지에 실패했습니다.");
                }
            });
        }
    });

    // 검색 조건에 따른 알림 표시 및 검색 제한
    $("select[name='subject']").change(function(){
        if ($(this).val() == '2') { // 의료회원 선택
        
            $("input[name='word']").attr("placeholder", "병원 이름을 입력하세요");
        } else {
            $("input[name='word']").attr("placeholder", "성명이나 ID를 검색하세요");
        }
    });
});

function searchList() {
    const frm = document.memberListForm;
    frm.pageNo.value = 1; // 검색 시 첫 페이지로 이동 
    frm.method = "GET";
    frm.action = "<%= ctxPath %>/mypage/memberList.bibo"; 
    frm.submit();
}

function Page(pageNo) {
   
   //  const frm = document.forms['memberListForm'];
    const frm = document.memberListForm;
 <%--  if (frm) {
        const pageNoInput = frm.elements['pageNo'];
        if (pageNoInput) {
            pageNoInput.value = pageNo || 1;
           
            frm.method = "GET";
            frm.action = "<%= ctxPath %>/mypage/memberList.bibo"; 
            frm.submit();
        } else {
           
        }
    } else {
     
    } --%>
    frm.pageNo.value = pageNo;
    frm.method = "GET";
    frm.action = "<%= ctxPath %>/mypage/memberList.bibo"; 
    frm.submit();
}

<%--  엑셀 파일로 다운로드 시작 --%>
$(document).ready(function() {
    // 엑셀 파일로 다운로드
    $("button#btnExcel").click(function(){
       	
    	const arr_userid = new Array();
       	
       	$("input:checkbox[name='userid']:checked").each(function(index, item){
       		add_userid.push($(item).val());
       	});
       	
       	const namebar = arr_userid.join()
       	
       	console.log("확인용 userid"  + arr_userid );
       	
      	const frm = document.memberListForm;
       
       	
        frm.method = "post"; 
        frm.action = "<%= ctxPath %>/mypage/downloadExcelFile.bibo";
       
        frm.submit();
    });
});
	

</script>

<style>
.search_ch {
	height: 40px;
	border: none;
	border-radius: 0.5rem;
}

.sel_0 {
	width: 200px;
	margin-right: 2rem;
	padding: 0 40px 0 20px;
}
.sel_1 {
	width: 40%;
	padding-left: 20px;
}

</style>

<div class="container" style="padding:3% 0;">
<p class="text-center nanum-b size-n">회원 전체 목록</p>
    <form name="excel_upload_frm" method="post" action="<%= ctxPath %>/mypage/downloadExcelFile.bibo" enctype="multipart/form-data">
    <button type="submit" class="btn btn-secondary btn-sm" id="btnExcel">엑셀파일 다운로드</button> 
</form>
    <form name="memberListForm" method="post" action="<%= ctxPath %>/mypage/memberList.bibo">
    <input type="hidden" name="pageNo" value="${currentPageNo != null ? currentPageNo : 1}"/>
    <fieldset>
        <div class="namebar p-4 searchBar" align="center" style="background-color: var(--object-skyblue-color);">
            <span>
                <select class="sclist search_ch sel_0 nanum-b" name="subject">
                    <option value='0'>전체</option>
                    <option value='1'>일반회원명</option>
                    <option value='2'>병원명</option>
                </select>
            </span>
            <span>
                <input class="inputsc search_ch sel_1 nanum-b" name="word" type="text" placeholder="성명이나 ID를 검색하세요" autocomplete="none" value="${requestScope.word}"/>
            </span>
            <span>
                <button class="jh_btn_design search nanum-eb size-s" type="button" onclick="searchList()" style="margin: 1px; width: 100px;" >검색</button>
            </span>
        </div>
    </fieldset>
</form>

   <!--  <input type="hidden" name="userid" /> -->
    
  
 
   

<br>

<table id="memberTbl" class="table">
    <thead>
        <tr>
            <th>아이디</th>
            <th>회원 유형</th>
            <th  style="text-align:center;">성명</th>
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
                    <td style="text-align:center;">${member.name}</td>
                    <td>
                        <c:choose>
                            <c:when test="${member.gender == 1}">남</c:when>
                            <c:otherwise>여</c:otherwise>
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
     <%-- 페이지 바 --%>
	<div class="pagebar w-100 d-flex justify-content-center pt-3">${pagebar}</div>
</div>

<!-- 회원 상세정보 모달 -->
<div class="modal fade" id="memberDetailModal" tabindex="-1" role="dialog" aria-labelledby="memberDetailModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="memberDetailModalLabel">회원 상세 정보</h5>
            </div>
            <div class="modal-body">
                <form id="editMemberForm">
                    <table class="table table-bordered">
                        <tr>
                            <th>아이디</th>
                            <td id="useridText"></td>
                        </tr>
                        <tr>
                            <th>성명</th>
                            <td id="nameText"></td>
                        </tr>
                        <tr>
                            <th>이메일</th>
                            <td id="emailText"></td>
                        </tr>
                        <tr>
                            <th>연락처</th>
                            <td id="mobileText"></td>
                        </tr>
                        <tr>
                            <th>주소</th>
                            <td id="addressText"></td>
                        </tr>
                        <tr>
                            <th>상세주소</th>
                            <td id="detailAddressText"></td>
                        </tr>
                        <tr>
                            <th>회원 구분</th>
                            <td id="memberTypeText"></td>
                        </tr>
                        <tr>
                        	<th>작성 글 수</th><!-- QnA에 글 올린 갯수 -->
                        	<td id="memberWriteCount"></td>
                        </tr>
                    </table>
                    <input type="hidden" id="userid" name="userid">
                    <input type="hidden" id="mIdx" name="mIdx">
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
             
                <button type="button" class="btn btn-danger" id="btnStop">정지시키기</button>
            </div>
        </div>
   
	
    </div>
</div>

