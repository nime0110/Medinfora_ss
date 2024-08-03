<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% String ctxPath = request.getContextPath(); %>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/commu/commuView.css" />

<style>

.contentarea{
	display: flex !important;
	flex-wrap: wrap;
	justify-content: center;
}

div.innerimg{
	margin-top:50px;
	margin-bottom: 50px;
	width: 100%;
	display: flex;
	justify-content: center;
}

div.content{
	margin-bottom: 100px;
	width: 100%;
	padding: 100px;
}

div.btnset{
	display: flex;
	gap : 10px;
	margin: 0;
	padding: 0;
}

div.dbtn{
	background-color: var(--grey800);
	color: var(--blue50);
	padding: 5px 12px;
}

div.dbtn:hover{
	cursor:pointer;
	background-color: var(--grey600);
}

</style>

<script type="text/javascript">

function goback(){
	history.go(-1);
}

function del(nidx){
	let cf = confirm("해당 뉴스를 삭제하시겠습니까?");
	
	if(cf){
		location.href="<%=ctxPath%>/news/del.bibo?nidx="+nidx;
	}
}

</script>

<div class="commu-container">

    <div class="title_area">
        <span class="nanum-eb">${requestScope.ndto.title}</span>
        <span class="nanum-eb">🕛${requestScope.ndto.registerday}</span>
    </div>

    <div class="contentarea">
    	<div class="innerimg">
    		<img src="${requestScope.ndto.imgsrc}">
    	</div>
    	<div class="content">
        ${requestScope.ndto.content}
        </div>
    </div>
    
    <div class="btnset">
	    <div class="dbtn" onclick="goback()">목록으로</div> 
	    <c:if test="${sessionScope.loginuser.mIdx eq 0}">
	    	<div class="dbtn" onclick="del('${requestScope.ndto.nidx}')">삭제하기</div>
	    </c:if>
    </div>
    
</div>
