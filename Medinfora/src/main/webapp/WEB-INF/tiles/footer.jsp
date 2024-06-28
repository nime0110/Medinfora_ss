<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String ctxPath = request.getContextPath(); %>

<link rel="stylesheet" href="<%=ctxPath %>/resources/css/footer.css">

<script>

$(function(){
	
	$('img.footerimgsrc').on("click",function(){
	    location.href = "<%=ctxPath%>/index.bibo";
	});
	
})

</script>

<footer id="indexfooter">
	<div id="footerimg">
		<img class="footerimgsrc" src="<%=ctxPath%>/resources/img/logo-main.png">
	</div>
	<div class="info-inner">
	  <p>서울특별시 마포구 서교동 447-5 풍성빌딩 2,3,4층</p>
	  <p>전화번호: 02-336-8546</p>
	  <p>&copy;Final Team3</p>
	  <ul>
	    <li><a href="">Privacy Policy</a></li>
	    <li><a href="">Terms of Service</a></li>
	    <li><a href="">Contact Us</a></li>
	  </ul>
	</div>
</footer>