<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String ctxPath = request.getContextPath();
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="<%= ctxPath %>/resources/css/header.css" />
<link rel="stylesheet" type="text/css" href="<%= ctxPath %>/resources/css/fontcss.cs"/>
<script type="text/javascript">
const frm = document.editFrm;
frm.method = "GET";
frm.action = "<%= ctxPath%>/mypage/memberList.info";
frm.submit();
</script>

<div class="container" style="padding:3% 0;">

	<p class="text-center nanum-b size-n"> 회원 전체 목록</p>
	



</div>