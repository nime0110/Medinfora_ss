<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%String ctxPath = request.getContextPath();%>

<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/status/view.css">

<div class="statusContainer mx-5">
	<div class="row row-cols-sm-3 g-4">
		<a href="<%=ctxPath%>/status/serviceUtilization.bibo">
			<div class="col">
		    	<div class="status_card card">
		    		<div>
		      			<img src="<%=ctxPath %>/resources/img/hospitalisationPatient.png" class="card-img-top p-5" />
		      		</div>
		      		<div class="card-body">
		       			<h5 class="card-title text-center nanum-b">의료서비스 이용률</h5>
		      		</div>
		    	</div>
		  	</div>
	  	</a>
	  	<a href="<%=ctxPath%>/status/facilities.bibo">
		  	<div class="col">
		    	<div class="status_card card">
		      		<img src="<%=ctxPath %>/resources/img/hospitalisation_icon.png" class="card-img-top p-5" />
		      		<div class="card-body">
		        		<h5 class="card-title text-center nanum-b">시도별 주요시설 현황</h5>
		      		</div>
		    	</div>
		  	</div>
	  	</a>
	  	<a href="<%=ctxPath%>/status/cancerincidence.bibo">
	  	<div class="col">
		    	<div class="status_card card">
		      		<img src="<%=ctxPath %>/resources/img/hospitalisation_icon.png" class="card-img-top p-5" />
		      		<div class="card-body">
		        		<h5 class="card-title text-center nanum-b">연도별 암발생자 현황</h5>
		    		</div>
		  		</div>
			</div>
		</a>
	</div>
</div>