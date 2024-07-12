<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 

<%String ctxPath = request.getContextPath();%>

<!DOCTYPE html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>MEDINFORA</title>
	
	<%-- Jquery --%>
	<script src="<%= ctxPath%>/resources/node_modules/jquery/dist/jquery.min.js"></script>
	
	<%-- JS --%>
	<script type="text/javascript" src="<%= ctxPath%>/resources/js/index.js"></script>
	
	<%-- BootStrap --%>
	<script src="<%= ctxPath%>/resources/node_modules/bootstrap/dist/js/bootstrap.min.js"></script>
	<link rel="stylesheet" href="<%= ctxPath%>/resources/node_modules/bootstrap/dist/css/bootstrap.min.css">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
	
	<link rel="stylesheet" href="<%=ctxPath%>/resources/css/mypage/index.css">
	
	<%-- Google Font --%>
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap" rel="stylesheet">
	<link rel="stylesheet" href="<%= ctxPath%>/resources/css/fontcss.css">
	
	<%-- Font Awesome 6 Icons --%>
	<script src="https://kit.fontawesome.com/f1e9f47e08.js" crossorigin="anonymous"></script>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
	<style>
	
	/* 스타일 clear 공통 코드 start */
	
		body {
		    font-family: 'Nanum Gothic', sans-serif;
		    margin: 0;
		    padding: 0;
		}
		
		li {
		    list-style: none;
		}
		
		a {
		    text-decoration: none;
		    color: black;
		}
		
		.viewtoggle{
			display:none;
			padding-left: 24px;
		}
		
		.tog_ico{
			display: block;
			font-size: 20pt;
			background-color: var(--navy700);
			border-radius: 50%;
			width: 40px;
			height: 40px;
			text-align: center;
			color: var(--grey50);
	        position: absolute;
	        z-index: 50;
		}
		
		.tog_ico:hover{
			background-color: var(--navy500);
			cursor: pointer;
		}
		
		@media (max-width: 768px) {
	    .viewtoggle{
	        display: block;
	    }
	
	}
		
	</style>
	
	<script type="text/javascript">
	
		let togStatus = 0;
	
		function toggleOn(){
			
			if(togStatus === 0){
	
				$("#info_sidebar").show();
				togStatus = 1;
				$(".tog_ico").html('<i class="fa-solid fa-xmark"></i>');
				
			}else{
				
				$("#info_sidebar").hide();
				togStatus = 0;
				$(".tog_ico").html('<i class="fa-solid fa-plus"></i>');
				
			}
			
		}// end of function toggleOn()
		
		// window의 크기 변경 감지 해주는 이벤트
		window.onresize = function() {
			
			const width = window.innerWidth;	
			  
			if(width>768){
				$("#info_sidebar").show();
				togStatus = 1;
				$(".tog_ico").html('<i class="fa-solid fa-xmark"></i>');
			}else{
				$("#info_sidebar").hide();
				togStatus = 0;
				$(".tog_ico").html('<i class="fa-solid fa-plus"></i>');
			}
			  
		} // window.onresize
	
	</script>

</head>
<body>
	<tiles:insertAttribute name="header" />
	<div style="margin-top: 75px;"></div>
	<div class="viewtoggle">
		<span class="tog_ico" onclick="toggleOn()"><i class="fa-solid fa-plus"></i></span>
	</div>
	<div id="info_container">
		<tiles:insertAttribute name="sidebar" />
		<div id="info_content">
			<tiles:insertAttribute name="content" />
        </div>
    </div>
	<tiles:insertAttribute name="footer" />
</body>

</html>