<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% String ctxPath = request.getContextPath(); %>


<script src="<%= ctxPath%>/resources/node_modules/jquery/dist/jquery.min.js"></script>
<link rel="stylesheet" href="<%=ctxPath%>/resources/css/reigster/register.css">
<style>

</style>
    
    <script type="text/javascript">
        function findId() {
        	 const name = $("#name").val().trim();
             const email = $("#email").val().trim();
             if (name === "" || email === "") {
                 alert("이름과 이메일을 모두 입력해주세요.");
                 return;
            }
            if (confirm("아이디를 찾으시겠습니까?")) {
                $.ajax({
                	url: "<%= ctxPath %>/login/findId",
                    type: "POST",
                    data: { name: name, email: email },
                    success: function(response) {
                        if (response.success) {
                            $("#result").text("회원님의 아이디는 " + response.userid + "입니다.");
                        } else {
                            $("#result").text("조회 결과가 없습니다.");
                        }
                    },
                    error: function() {
                        alert("아이디 찾기에 실패하였습니다.");
                    }
                });
            }
        }    
        
        function goBack() {
            history.back();
        }
    </script>

<div class="jh_container">
	<div class="registerArea">
		<form id="registerFrm" name="registerFrm">
			<input type="hidden" name="join" value=""/>
			<p class="size-n nanum-eb" align="center">아이디 찾기</p>
    
        <div class="form-group">
        	<lable for="name">이름</lable>
        	 <input type="text" class="form-control" id="name" name="name" placeholder="이름 입력">
            <label for="email">이메일</label>
            <input type="text" class="form-control" id="email" name="email" placeholder="이메일 입력">
        </div>
        <button class="btn btn-primary" onclick="findId()">아이디 찾기</button>
         <p id="result" class="mt-3">
            <c:if test="${!empty success}">
                <c:choose>
                    <c:when test="${success}">
                        회원님의 아이디는 ${userid}입니다.
                    </c:when>
                    <c:otherwise>
                        조회 결과가 없습니다.
                    </c:otherwise>
                </c:choose>
            </c:if>
        </p>
        
       <div class="mt-3">
                <button type="button" class="btn btn-secondary" onclick="goBack()">이전 페이지로 돌아가기</button>
                <button type="button" class="btn btn-secondary" onclick="location.href='findPwd'">비밀번호 찾기</button>
            </div>
        </form>
    </div>
</div>