<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String ctxPath = request.getContextPath();
%>

<style type="text/css">

.b_red {
   border: solid 1px red;
}
 
.b_blue {
   border: solid 1px blue;
}
 
.b_black {
   border: solid 1px black;
}

.subject {
   border-top: 3px solid black;
   border-bottom: 1px solid black;
   margin-top: 2%;
 }
 
button.golist {
   width: 120px;
   height: 50px;
   background-color: white;
   border: solid 1px var(--text-black-color);
   font-size: 1rem;
   border-radius: 0.5rem;
   color: var(--text-black-color);
   
}

button.golist:hover{
   background-color: var(--primary-background-color);
   color: var(--text-white-color);
   border: none;
   
}

.container {
  padding: 0 4%;
}

.menu{
  height: 60px;
  display: flex;
  background-color: antiquewhite;
  border-top: solid 1px black;
  border-bottom: solid 1px #2222227a;
  justify-content: center;
}

.titleName{
  padding: 0 3rem;
}

.lineG {
    /* display: inline-block; */
    width: 2px;
    height: 20px;
    margin-top: 2px;
    background: #2222227a;
    /* border-top: 1px solid #cbcecf;
    border-bottom: 1px solid #cbcecf; */
}

textarea.plusq{
  height: 150px;
  resize: none;
}

button#upload, button#cancle, button#showAnswerArea {
  border-radius: 0.5rem;
  width: 100px;
  height: 40px;
  border: solid 1px #bfbfbf;
  margin: 10px 0;
  background-color: var(--text-white-color);
}

button#upload {
  margin-right: 10px;
}

img#answericon{
	width: 4.7rem;
}

</style>


<script>





</script>


<div class="container b_black py-5" style="margin-top: 100px;">
	<div class="py-4" align="center">
		<h2 class="nanum-eb size-n">묻고 답하기</h2>
	</div>

	<div>
		<input type="hidden" name="qidx" />
	</div>
	<div class="subject py-4" align="center">
		<span class="nanum-eb" style="font-size: 1.7rem;">질문제목</span>
	</div>
	<div class="menu nanum-b py-3">
		<span class="titleName">작성자&nbsp;&nbsp;:&nbsp;&nbsp;<span>홍길동</span></span>
		<span class="lineG"></span> <span class="titleName">구분&nbsp;&nbsp;:&nbsp;&nbsp;<span>건강정보</span></span>
		<span class="lineG"></span> <span class="titleName">작성일&nbsp;&nbsp;:&nbsp;&nbsp;<span>2024-01-01</span></span>
		<span class="lineG"></span> <span class="titleName">진행상태&nbsp;&nbsp;:&nbsp;&nbsp;<span>답변중</span></span>
		<span class="lineG"></span> <span class="titleName">조회수&nbsp;&nbsp;:&nbsp;&nbsp;<span>7</span></span>
	</div>

	<div class="b_red d-flex py-5">
		<div class="text-center" style="width: 12%;">
			
			<i class="fa-regular fa-circle-question fa-4x" style="color: #006cfa;"></i>

		</div>
		<div class="py-2 b_blue w-75">
			<span class="nanum-b"> 안녕하세요. 엑셀 업로드 문의 드립니다. <br> 내부
				시스템관리 >> 메뉴 목록관리 >> 일괄 등록 페이지에서 엑셀 파일 업로드가 안됩니다. <br>
				Enterprise Business 4.2에서 이런 현상이 없습니다. <br> 프로그램에 어떤한 수정도 한 것이
				없습니다. <br> 해결된 소스를 받아 볼 수 있을까요? <br> 오류 메세지는 첨부합니다.
			</span>

			<div class="mt-3 w-75" style="background-color: aquamarine;">
				<span class="nanum-b">첨부파일</span>
				<div class="mt-1 nanum-b" name="file">
					<i class="fa-solid fa-paperclip" style="color: #535965;"></i> <span>파일명.jpg</span>
				</div>
			</div>

		</div>
	</div>

	<div style="text-align: center;">
		<button class="nanum-b" id="showAnswerArea" type="button">답변&nbsp;등록</button>
	</div>

	<div id="answerArea" class="mx-5 mb-3">
		<form class="answer" name="answer">
			<input type="hidden" value="" name="aidx" /> <input type="hidden"
				value="" name="userid" />
			<textarea class="form-control plusq" name="content"
				placeholder="답변 내용을 입력하세요." maxlength="150"></textarea>
			<div style="text-align: right;">
				<button class="nanum-b" id="upload" type="button">등록</button>
				<button class="nanum-b" id="cancle" type="button">취소</button>
			</div>
		</form>
	</div>

	<hr>

	<div class="b_black">
		<div class="b_blue d-flex pt-5 mb-3">
			<div class="text-center" style="width: 12%;">
				<img id="answericon" src="<%=ctxPath%>/resources/img/answer_icon.svg" />

			</div>
			<!-- 답변 내용 -->
			<div class="py-2 b_red w-75">
				<span class="nanum-b"> 안녕하세요. 답변드립니다. <br> 어쪼구 저쪼구 <br>
					Enterprise Business 4.2에서 이런 현상이 없습니다.

				</span>
				<div class="b_blue mt-5 mb-3">
					여기에 병원정보 들어간다			
				</div>

			</div>
			<!-- 답변일자 -->
			<div class="mx-3">
				<span class="nanum-n">2024-01-12</span>
			</div>
		</div>

		<!-- 추가질문답변 내용 -->
		<!-- 내용아 없으면 숨기고 있으면 보여줌 -->
		<!-- 답변자에경우 본 답변자와 동일해야함 -->
		<div class="mx-5 mb-3 p-3" style="background-color: bisque;">
			<div>
				<h5 class="nanum-b">이 답변의 추가 Q&A</h5>
				<h6>질문자와 답변자가 추가로 묻고 답하며 지식을 공유할 수 있습니다.</h6>
			</div>
			<div class="py-2">
				<span>질문자&nbsp;&nbsp;<span name="aqwriteday">2024-12-01</span></span>
				<div>질문내용</div>
			</div>
			<div class="py-2">
				<span>답변자&nbsp;&nbsp;<span name="anwriteday">2024-12-02</span></span>
				<div>답변내용</div>
			</div>

		</div>

		<!-- 해당 답변에 추가 질문하는고 -->
		<div class="mx-5">
			<button class="mb-2 btn btn-light" type="button" id="addquestion_btn">추가질문</button>
		</div>

		<div class="mx-5 mb-3">

			<form name="addquestion">
				<input type="hidden" value="" name="aidx" /> <input type="hidden"
					value="" name="qidx" />
				<textarea class="form-control plusq" name="plusq"
					placeholder="추가 질문할 내용을 입력하세요." maxlength="70"></textarea>
				<div style="text-align: right;">
					<button class="nanum-b" id="upload" type="button">등록</button>
					<button class="nanum-b" id="cancle" type="button">취소</button>
				</div>
			</form>

			<form name="addanswer">
				<input type="hidden" value="" name="aidx" /> <input type="hidden"
					value="" name="qidx" />
				<textarea class="form-control plusq" name="plusa"
					placeholder="추가 답변할 내용을 입력하세요." maxlength="70"></textarea>
				<div style="text-align: right;">
					<button class="nanum-b" id="upload" type="button">등록</button>
					<button class="nanum-b" id="cancle" type="button">취소</button>
				</div>
			</form>
		</div>

	</div>


	<div class="my-5 text-center b_blue">
		<button class="jh_btn_design golist nanum-eb" type="button">목&nbsp;록</button>
	</div>




</div>