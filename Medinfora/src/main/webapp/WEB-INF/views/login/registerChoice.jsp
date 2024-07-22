<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	String ctxPath = request.getContextPath();
%>

<style type="text/css">

.box_jh{
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 10% 0;
  
}

.join_select {
  height: 28rem;
  border-radius: 2rem;
  width: 25%;
  text-align: center;
}

.join_select:hover {
  cursor: pointer;
  transform: scale(1.1);
  /* z-index: 10; */
  transition: transform 0.1s linear
}

.nomaluser {
  margin-right: 10rem;
  background-color: var(--primary-background-color);
}

.mediclauser {
  background-color: var(--object-lightgreen-font-color);
}

.icon_area{
  background-color: white;
  margin: 15% 20%;
  vertical-align: middle;
  text-align: center;
  height: 12rem;
  border-radius: 50%;
  padding-top: 2.3rem;
}

.join_name {
  font-size: 2.3rem;
  color: var(--text-white-color);
}

.line {
  height: 5px;
  margin: -2% 40% 6% 40%;
  background: var(--text-white-color);
  border-top: 1px solid var(--text-white-color);
  border-bottom: 1px solid var(--text-white-color);
}

.comment{
  background-color: rgba(3, 3, 3, 0.212);
  margin: 0 10%;
  height: 2.5rem;
  color: var(--text-white-color);
  padding-top: 0.5rem;
  border-radius: 1.5rem;
  
}

</style>

<script type="text/javascript">

$(document).ready(function(){
	
	$("div#nomaluser").click(function(){
		location.href = "<%= ctxPath%>/register/register.bibo?join=1";
	});
	
	$("div#mediclauser").click(function(){
		location.href = "<%= ctxPath%>/register/register.bibo?join=2";
	});
	
	
	
});// end of $(document).ready(function()

</script>

<!-- 회원가입 form -->
<div class="container" style="margin-top: 100px;">
	<div class="box_jh">
		<div class="join_select nomaluser" id="nomaluser">
			<div class="icon_area">
				<i class="fa-solid fa-user fa-7x" style="color: #4780dd;"></i>
			</div>
			<p class="join_name nanum-eb">개인회원 가입</p>
			<div class="line"></div>
			<div class="comment nanum-n size-s">개인일 경우 선택</div>
		</div>
		
		<div class="join_select mediclauser" id="mediclauser">
			<div class="icon_area">
				<i class="fa-solid fa-hospital-user fa-7x" style="color: #26d9a3;"></i>
			</div>
			<p class="join_name nanum-eb">의료회원 가입</p>
			<div class="line"></div>
			<div class="comment nanum-n size-s">의료기관일 경우 선택</div>
		</div>
	</div>
</div>