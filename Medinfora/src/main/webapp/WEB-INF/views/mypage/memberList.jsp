<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    String ctxPath = request.getContextPath();
    %>
<script type="text/javascript">
const frm = document.editFrm;
frm.method = "GET";
frm.action = "<%= ctxPath%>/mypage/memberList.info";
frm.submit();
</script>