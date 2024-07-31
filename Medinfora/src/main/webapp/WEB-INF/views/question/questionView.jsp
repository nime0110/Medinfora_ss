<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String ctxPath = request.getContextPath();
%>

<script type="text/javascript" src="<%= ctxPath%>/resources/js/question/question.js"></script>

<style type="text/css">

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

textarea.plusa{
  height: 100px;
  resize: none;
  width: 80%;
}

button#upload, button#acancle, button#cancle, button#showAnswerArea {
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

.addq {
	background-color: #ffffff;
}

button#addanswer_btn{
	margin: 0 0.5rem 0 1rem;
}

.answer_btn{
	text-align: right;

}

a#download:hover {
	cursor: pointer;
}


div.wrap-text {
    word-wrap: break-word;
    white-space: normal;
}



div#medinfo {
	background-color: #d8eaff;
}

div.medispec{
	width: 80px; 
	text-align: right;
	margin-right: 0.5rem; 
	
}


</style>


<script>


function goList(){
	location.href="<%=ctxPath%>/questionList.bibo";
}

</script>


<div class="container b_black py-5" style="margin-top: 100px;">
	<div class="py-4" align="center">
		<h2 class="nanum-eb size-n">묻고 답하기</h2>
	</div>

	<div>
		<input type="hidden" name="qidx" />
	</div>
	<div class="subject py-4" align="center">
		<span class="nanum-eb" style="font-size: 1.7rem;">${requestScope.totalcontent.qdto.title}</span>
	</div>
	<div class="menu nanum-b py-3">
		<span class="titleName">작성자&nbsp;&nbsp;:&nbsp;&nbsp;<span>${requestScope.totalcontent.qdto.name}</span></span>
		<span class="lineG"></span> <span class="titleName">구분&nbsp;&nbsp;:&nbsp;&nbsp;
		<span>
			<c:if test="${requestScope.totalcontent.qdto.subject eq '1'}">
				건강상담
			</c:if>
			<c:if test="${requestScope.totalcontent.qdto.subject eq '2'}">
				식생활,식습관
			</c:if>
			<c:if test="${requestScope.totalcontent.qdto.subject eq '3'}">
				의약정보
			</c:if>
		</span></span>
		<span class="lineG"></span> <span class="titleName">작성일&nbsp;&nbsp;:&nbsp;&nbsp;<span>${requestScope.totalcontent.qdto.writeday}</span></span>
		<span class="lineG"></span> <span class="titleName">진행상태&nbsp;&nbsp;:&nbsp;&nbsp;
		<span>
			<c:if test="${requestScope.totalcontent.qdto.acount == '0'}">
				답변중
			</c:if>
			<c:if test="${requestScope.totalcontent.qdto.acount != '0'}">
				답변완료
			</c:if>
		</span></span>
		<span class="lineG"></span> <span class="titleName">조회수&nbsp;&nbsp;:&nbsp;&nbsp;<span>${requestScope.totalcontent.qdto.viewCount}</span></span>
	</div>

	<div class="d-flex py-5">
		<div class="text-center" style="width: 12%;">
			
			<i class="fa-regular fa-circle-question fa-4x" style="color: #006cfa;"></i>

		</div>
		<div class="py-2 w-75">
			<div class="nanum-b size-s wrap-text">${requestScope.totalcontent.qdto.content}</div>
			
			<c:if test="${not empty requestScope.totalcontent.qdto.filename}">
				<div class="mt-5 w-75" style="background-color: aquamarine;">
					<span class="nanum-b">첨부파일</span>
					<div class="mt-1 nanum-b" >
						<c:choose>
							<c:when test='${sessionScope.loginuser.mIdx == "2" or sessionScope.loginuser.userid == requestScope.totalcontent.qdto.userid}'>
								<i class="fa-solid fa-paperclip" style="color: #535965;"></i> 
								<a id="download" href="<%=ctxPath%>/question/filedownload.bibo?filename=${requestScope.totalcontent.qdto.filename}&originFilename=${requestScope.totalcontent.qdto.originFilename}&qidx=${requestScope.totalcontent.qdto.qidx}">${requestScope.totalcontent.qdto.originFilename}&nbsp;&nbsp;(<span><fmt:formatNumber pattern="#,###" value="${requestScope.totalcontent.qdto.size}"/></span>&nbsp;&nbsp;Byte)</a>
							</c:when>
							<c:otherwise>
								<i class="fa-solid fa-paperclip" style="color: #535965;"></i> <span>${requestScope.totalcontent.qdto.originFilename}&nbsp;&nbsp;(<span><fmt:formatNumber pattern="#,###" value="${requestScope.totalcontent.qdto.size}"/></span>&nbsp;&nbsp;Byte)</span>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</c:if>

		</div>
	</div>
	
	<%-- 질문 수정삭제 --%>
	<form name="questionUpdate" method="post">
		<input type="hidden" id="writer" name="writer" value="${requestScope.totalcontent.qdto.userid}">
		<input type="hidden" name="qidx" value="${requestScope.totalcontent.qdto.qidx}">
	</form>
	<c:if test="${not empty sessionScope.loginuser and  sessionScope.loginuser.userid eq requestScope.totalcontent.qdto.userid}">
		<div class="mx-5" style="text-align: right;">
			<button class="btn btn-outline-dark" type="button" id="addanswer_btn" onclick="questionupdate()">수정</button>
			<button class="btn btn-outline-danger" type="button" onclick="questionDelete(${requestScope.totalcontent.qdto.qidx})">삭제</button>
		</div>
	</c:if>
	
	<c:if test='${not empty sessionScope.loginuser and sessionScope.loginuser.mIdx == "2"}'>
		<%-- 조건은 의료진만 답변 --%>
		<div style="text-align: center;">
			<button class="nanum-b" id="showAnswerArea" type="button">답변&nbsp;등록</button>
		</div>
	</c:if>

	<div id="answerArea" class="mx-5 mb-3">
		<form class="answer" name="answer">
			<input type="hidden" value="${requestScope.totalcontent.qdto.qidx}" name="answer" />
			<input type="hidden" value="${sessionScope.loginuser.userid}" name="userid" />
			<textarea class="form-control plusq" name="content"
				placeholder="답변 내용을 입력하세요." maxlength="150"></textarea>
			<div style="text-align: right;">
				<button class="nanum-b" id="upload" type="button" onclick="answerupload()">등록</button>
				<button class="nanum-b" id="acancle" type="button" onclick="answercanle()">취소</button>
			</div>
		</form>
	</div>

	<hr>
	
	
	
	<c:forEach var="adto" items="${requestScope.totalcontent.adtoList}" varStatus="status">
	
		<div class="mb-3">
			<%-- 답변 정보 --%>
			<div class="d-flex pt-5 mb-3">
				<div class="text-center" style="width: 12%;">
					<img id="answericon" src="<%=ctxPath%>/resources/img/answer_icon.svg" />
				</div>
				<!-- 답변 내용 -->
				<div class="py-2 w-75">
					<span class="nanum-b">${adto.content}</span>
					<div class="b_blue mt-5 mb-3 p-2" id="medinfo">
						<div class="nanum-eb mb-3" style="font-size: 1.3rem;">답변자 정보</div>
						<input type="hidden" value="${adto.userid}" id="medi_userid"/>
						<div class="nanum-b d-flex mb-1" ><div class="medispec">병&nbsp;원&nbsp;명&nbsp;&nbsp;:</div><div>${adto.memberList[0].name}</div></div>
						<div class="nanum-b d-flex mb-1" ><div class="medispec">대표번호&nbsp;&nbsp;:</div><div>${adto.memberList[0].mobile}</div></div>
						<div class="nanum-b d-flex" ><div class="medispec">진료과목&nbsp;&nbsp;:</div><div>${adto.classcode}</div></div>
					</div>
				</div>
				<!-- 답변일자 -->
				<div class="mx-3">
					<span class="nanum-n">${adto.writeday}</span>
				</div>
			</div>
			
			<c:if test="${not empty sessionScope.loginuser and sessionScope.loginuser.userid eq adto.userid}">
				<%-- 답변 수정 --%>
				<div class="mb-2 mx-5" id="answer_btn${status.index}" style="text-align: right;">
					<button class="btn btn-outline-secondary btn-m" type="button" id="addanswer_btn" onclick="showUpdateQ(${status.index})">수정</button>
					<button class="btn btn-outline-danger btn-m" type="button" onclick="answeDelete(${status.index})">삭제</button>
				</div>
				
				<div id="answerUpdate${status.index}" class="mx-5 mb-3 answerUpdate">
					<input type="hidden" value="${adto.content}" id="orgincotent"/>
					<form class="answer" name="answer">
						<input type="hidden" value="${adto.aidx}" name="aidx"/>
						<input type="hidden" value="${sessionScope.loginuser.userid}" name="userid" />
						<textarea class="form-control plusq" name="content"
							placeholder="수정답변을 입력하세요." maxlength="150">${adto.content}</textarea>
						<div style="text-align: right;">
							<button class="nanum-b" id="upload" type="button" onclick="answerUpdate(${status.index})">등록</button>
							<button class="nanum-b" id="acancle" type="button" onclick="answerUpcanle(${status.index})">취소</button>
						</div>
					</form>
				</div>
			</c:if>
			
			<%-- 추가질답 --%>
			<div class="mx-5 mb-3 p-3" style="background-color: bisque;">
				<div>
					<h5 class="nanum-b">이 답변의 추가 Q&A</h5>
					<h6>질문자와 답변자가 추가로 묻고 답하며 지식을 공유할 수 있습니다.</h6>
				</div>
				<c:forEach var="add" items="${adto.addqnadtoList}" varStatus="addquestion">
					<c:if test='${add.qnastatus == "0"}'>
						<div class="py-2">
							<div class="d-flex mb-1">
								<div class="nanum-b">질문자&nbsp;&nbsp;<span class="nanum-n" name="aqwriteday">${add.writeday}</span></div>
								<c:if test="${not empty sessionScope.loginuser and sessionScope.loginuser.userid eq adto.userid}">
									<button class="btn btn-dark mx-3 btn-sm" type="button" onclick="showaddanswerArea(${addquestion.index})">추가답변</button>
								</c:if>
							</div>
							
							<div><span class="nanum-n" >${add.qcontent}</span></div>
							<form id="addA${addquestion.index}" name="addA">
								<input type="hidden" value="${adto.aidx}" name="aidx"/>
								<input type="hidden" value="1" name="qnastatus"/>
								<input type="hidden" value="${add.cntnum}" name="cntnum"/>
								<textarea class="form-control plusa" name="qcontent" id="qcontent"
									placeholder="추가 답변할 내용을 입력하세요." maxlength="70"></textarea>
								<div style="text-align: right; width: 80%;">
									<button class="nanum-b" id="upload" type="button" onclick="addAnswerupload(${addquestion.index})">등록</button>
									<button class="nanum-b" id="cancle" type="button" onclick="addAnswercancle(${addquestion.index})">취소</button>
								</div>
							</form>
						</div>
					</c:if>
					<c:if test='${add.qnastatus == "1"}'>
						<div class="py-1" id="addanswerArea${addquestion.index}">
							<div class="d-flex mb-1">
								<div class="nanum-b">답변자&nbsp;&nbsp;<span class="nanum-n" name="aqwriteday">${add.writeday}</span></div>
								<c:if test='${not empty sessionScope.loginuser and sessionScope.loginuser.userid eq adto.userid}'>
									<button class="btn btn-outline-dark btn-sm" type="button" id="addanswer_btn" onclick="showUpdateaddQ(${addquestion.index})">수정</button>
									<button class="btn btn-outline-danger btn-sm" type="button" onclick="addAnswerdelete(${addquestion.index})">삭제</button>
								</c:if>
							</div>
							
							<div id="addanswer"><span class="nanum-n" id="qcontent">${add.qcontent}</span></div>
							
							<div id="updateaddA">
								<input type="hidden" value="${add.qaidx}" name="qaidx"/>
								<textarea class="form-control plusa" name="qcontent" id="qcontent"	placeholder="수정답변 내용을 입력하세요." maxlength="70">${add.qcontent}</textarea>
								<div style="text-align: right; width: 80%;">
									<button class="nanum-b" id="upload" type="button" onclick="addAnswerupdate(${addquestion.index})">등록</button>
									<button class="nanum-b" id="cancle" type="button" onclick="addupdatecancle(${addquestion.index})">취소</button>
								</div>
							</div>
						</div>
					</c:if>
				</c:forEach>
				
			</div>
			
			
			<c:if test="${not empty sessionScope.loginuser and  sessionScope.loginuser.userid eq requestScope.totalcontent.qdto.userid}">
			<div class="mx-5">
				<%-- 질문자 경우에만 --%>
				<button class="mb-2 btn btn-light" type="button" id="addquestion_btn" onclick="showaddArea(${status.index})">추가질문</button>
			
				<div class="mb-3" id="addQuestionArea">
					<form id="addQ" name="addQ">
						<input type="hidden" value="${adto.aidx}" name="aidx" />				
						<input type="hidden" value="${adto.qnacnt+1}" name="cntnum" />
						<input type="hidden" value="0" name="qnastatus" />
						<textarea class="form-control plusq" name="qcontent" id="acontent"
							placeholder="추가 질문할 내용을 입력하세요." maxlength="70"></textarea>
						<div style="text-align: right;">
							<button class="nanum-b" id="upload" type="button" onclick="addupload(${status.index})">등록</button>
							<button class="nanum-b" id="cancle" type="button" onclick="addcancle(${status.index})">취소</button>
						</div>
					</form>
				</div>
				
			</div>
			</c:if>
		</div>

		<hr>
		
	</c:forEach>
	


	<div class="my-5 text-center">
		<button class="jh_btn_design golist nanum-eb" type="button" onclick="goList()">목&nbsp;록</button>
	</div>




</div>