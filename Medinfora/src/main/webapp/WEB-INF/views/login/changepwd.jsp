<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%String ctxPath = request.getContextPath();%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/login/find.css">
<script type="text/javascript" src="<%= ctxPath%>/resources/js/login/changepwd.js"></script>

<script type="text/javascript">
function changepwd(){
    const pwd = $("input:password[name='in_pwd']").val("");
    const pwddp = $("input:password[name='in_pwddp']").val("");

    if(pwd == "" || pwddp == ""){
        return;
    }
    else{
        $.ajax({
            url:"<%=ctxPath%>/login/changepwdEnd.bibo",
            type:"post",
            data:{"userid":$("input:hidden[name='userid']").val()
                 ,"pwd":pwd},
            dataType:"json",
            async:"false",
            success:function(json){
                
                if(json.result == "yes"){
                    alert("비밀번호 변경되었습니다.");
                    location.href = "<%=ctxPath%>/index.bibo";
                }

            },
            error: function(request, status, error){
                alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
            }
            
        });


    }

}// end of function search(){---




</script>


<div class="findContainer">

	<h2 class="nanum-eb size-n my-4" id="finditem">비밀번호 초기화</h2>
	<div class="qwer">
		<label class="label_st" for="in_pwd">
			<input id="in_pwd" name="in_pwd" type="password" placeholder="새로운 비밀번호를 입력해주세요" />
			<button class="reset_btn" type="button">X</button>
		</label>

	</div>
	<div class="qwer mb-4" style="text-align:left;">
		<p id="cmt_pwd">영문,숫자 특수기호 !@#$%^&* 만 사용가능합니다.</p>
	</div>


	<div class="qwer">
		<label class="label_st" for="in_pwddp"> 
			<input id="in_pwddp" name="in_pwddp" type="password" placeholder="새로운 비밀번호를 입력해주세요" />
			<button class="reset_btn" type="button">X</button>
		</label>

	</div>
	<div class="qwer mb-4" style="text-align: left;">
		<p id="cmt_pwddp">비밀번호가 일치하지 않습니다.</p>
	</div>
	<input name="userid" type="hidden" value="${requestScope.userid}"/>
	<button class="next_button nanum-b" type="button" onclick="changepwd()">비밀번호 변경</button>

</div>