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
</script>

<div class="container" style="padding:3% 0;">

	<p class="text-center nanum-b size-n"> 회원 전체 목록</p>
	
<form name="searchFrm">
	<c:if test = "${not empty requestScope.mbrList}">
	
	<span style="display: inline-block; width: 150px; font-weight: bold;">회원 구분 </span>
	<c:forEach var="mbrId" items="${requestScope.mbrList}" varStatus="status">
		<label for="${status.index}">
			        <c:if test="${deptId == -9999}">
                   회원 없음
              </c:if>
              <c:if test="${deptId != -9999}">
                 ${deptId}
              </c:if>
              </label>
              <input type="checkbox" id="${status.index}" name="deptId" value="${deptId}">&nbsp;&nbsp;
       
           </c:forEach>
        </c:if>
        
        <input type="hidden" name="str_deptId" />
        
       <select name="gender" style="height: 30px; width: 120px; margin: 10px 30px 0 0;"> 
           <option value="">회원 선택</option>
           <option>관리자</option>
           <option>일반회원</option>
           <option>의료회원</option>
          </select>
          <button type="button" class="btn btn-secondary btn-sm" id="btnSearch">검색하기</button>
          &nbsp;&nbsp;
           <button type="button" class="btn btn-info btn-sm" id="btnSearchAjax">검색하기(ajax)</button>
          &nbsp;&nbsp;
          <button type="button" class="btn btn-success btn-sm" id="btnExcel">Excel파일로저장</button>
     </form>
     <br>
     
     
     
     <!-- ==== #209. 엑셀관련파일 업로드 하기 시작 ==== -->
   <form style="margin-bottom: 10px;" name="excel_upload_frm" method="post" enctype="multipart/form-data" >
      <input type="file" id="upload_excel_file" name="excel_file" accept=".csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" />
      <br>
      <button type="button" class="btn btn-info btn-sm" id="btn_upload_excel" style="margin-top: 1%;">Excel 파일 업로드 하기</button>
   </form>
   <!-- ==== 엑셀관련파일 업로드 하기 끝 ==== -->
   
     
     <table id="emptbl">
         <thead>
            <tr>
               <th>번호</th>
               <th>회원 유형</th>
               <th>성명 </th>
               <th>주소</th>
               <th>생년월일</th>
               <th>연락처</th>
               <th>성별</th>
               <th>가입일자</th>
               <th>회원상태</th>
            </tr>
         </thead>
         <tbody>
            <c:if test="${not empty requestScope.employeeList}">
               <c:forEach var="map" items="${requestScope.employeeList}">
                  <tr>
                  <td style="text-align: center;">${map.department_id}</td> <%-- department_id 은 hr_mapper_interface.xml 에서 정의해준 HashMap 의 키이다. --%>
                  <td>${map.department_name}</td>
                  <td style="text-align: center;">${map.employee_id}</td>
                  <td>${map.fullname}</td>
                  <td style="text-align: center;">${map.hire_date}</td>
                  <td style="text-align: right;"><fmt:formatNumber value="${map.monthsal}" pattern="#,###" /></td>
                  <td style="text-align: center;">${map.gender}</td>
                  <td style="text-align: center;">${map.age}</td>         
                  </tr>
               </c:forEach>
            </c:if>
        </tbody>
     </table>
     
  </div>  
</div>