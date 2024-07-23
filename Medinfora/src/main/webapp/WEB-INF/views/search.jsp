<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% String ctxPath = request.getContextPath(); %>
<link rel="stylesheet" href="<%=ctxPath%>/resources/css/search.css">

<script>

const totalcnt = ${requestScope.hcnt};
const totalview = Math.ceil(${requestScope.hcnt}/5);
let view = 2;

function hospitaldetail(hidx,hpname){
	
	location.href = `<%=ctxPath%>/hpsearch/hospitalSearch.bibo?hidx=\${hidx}&hpname=\${hpname}`;
	
}

function noticedetail(nidx){
	
	location.href = `<%=ctxPath%>/notice/view.bibo?nidx=\${nidx}`;
	
}

function reserve(hidx,sel_hpname){
	
	let f = document.createElement('form');
	    
    let obj1;
    obj1 = document.createElement('input');
    obj1.setAttribute('type', 'hidden');
    obj1.setAttribute('name', 'hidx');
    obj1.setAttribute('value', hidx);

	let obj2;
	obj2 = document.createElement('input');
	obj2.setAttribute('type', 'hidden');
	obj2.setAttribute('name', 'sel_hpname');
	obj2.setAttribute('value', sel_hpname);
    
    f.appendChild(obj1);
    f.appendChild(obj2);
    f.setAttribute('method', 'post');
    f.setAttribute('action', '<%=ctxPath%>/reserve/choiceDay.bibo');
    document.body.appendChild(f);
    f.submit();

}

function moreshow(search){
	
	let sn = 0;
	let en = 0;
	
	for(let i=1;i<=totalview;i++){
		let snv = 5*(i-1)+1;
		let env = 0;
		if(i!=totalview){
			env = 5*i;
		}else{
			env = totalcnt;
		}
		if(view==i){
			sn = snv;
			en = env;
			view++;
			break;
		}
	}
	
	const data = {"search":search,"sn":sn,"en":en};
	
	let addhtml = $('#htmlhospital').html();

	$.ajax({
		url : "<%=ctxPath%>/getmorehinfo.bibo",
		data : data,
		dataType:"json",
		success:function(json){
			json.forEach((element,idx)=>{
				addhtml += `
				<div class="content_h">
					<div class="content_place">
						<div class="content_hpname" onclick="hospitaldetail('\${element.hidx}','\${element.hpname}')">\${element.hpname} (\${element.agency})</div>
						<div class="content_hpaddress">\${element.hpaddr}</div>
						<div class="content_hpmobile">\${element.hptel}</div>
					</div>
				</div>`;
			});

			$('#htmlhospital').html(addhtml);
			if(view-1==totalview){
				$(".showmore").hide();
			}
		},
		error:function(request){
			alert("code : " + request.status);
		}
	});
	
}

</script>

<div id="searchcontainer">
	<div id="searchcontents">
		<c:if test='${requestScope.nosearch == 1}'>
			<div class="contenttitle">검색어를 입력해주세요.</div>
		</c:if>
		
		<c:if test='${requestScope.nosearch == 2}'>
			<div class="contenttitle">"${requestScope.search}"의 검색결과는 존재하지 않습니다.</div>
		</c:if>
		
		<c:if test='${requestScope.nosearch == 0}'>
		
			<div class="contenttitle">"${requestScope.search}" 의 검색결과</div>
			
			<c:if test="${requestScope.searchlist.hdtolist != null}">
				<div class="contentsub">
				
					<div class="c_subtitle">
						<span>병원</span>
					</div>
					<div id="htmlhospital">
						<c:forEach var="hdto" items="${requestScope.searchlist.hdtolist}">
							<div class="content_h">
								<div class="content_place">
									<div class="content_hpname" onclick="hospitaldetail('${hdto.hidx}','${hdto.hpname}')">${hdto.hpname} (${hdto.agency})</div>
									<div class="content_hpaddress">${hdto.hpaddr}</div>
									<div class="content_hpmobile">${hdto.hptel}</div>
								</div>
								<div class="content_tool">
									<c:if test="${hdto.member == true}">
										<button class="tool_btn" type="button" onclick="reserve('${hdto.hidx}','${hdto.hpname}')">예약하기</button>
									</c:if>
								</div>
							</div>
						</c:forEach>
					</div>
					
					<c:if test="${requestScope.hcnt > 5}">
						<div class="showmore" onclick="moreshow('${requestScope.search}')">
							<span>검색결과 더보기</span>
						</div>
					</c:if>
					
				</div>
				
			</c:if>
			
			<c:if test="${requestScope.searchlist.mqdtolist != null || requestScope.searchlist.madtolist != null}">
				<div class="contentsub">
				
					<div class="c_subtitle">
						<span>Q&amp;A</span>
					</div>
					<c:forEach var="mqdto" items="${requestScope.searchlist.mqdtolist}">
						<div class="content_q">
							<div class="content_place">
								<div class="content_title">${mqdto.title}</div>
								<div class="content_content">${mqdto.content}</div>
								<div class="content_writeday">${mqdto.writeday}</div>
							</div>
						</div>
					</c:forEach>
					<c:forEach var="madto" items="${requestScope.searchlist.madtolist}">
						<div class="content_q">
							<div class="content_place">
								<div class="content_title">${madto.title}</div>
								<div class="content_content">${madto.content}</div>
								<div class="content_writeday">${madto.writeday}</div>
							</div>
						</div>
					</c:forEach>
					
					<div class="showmore" onclick="location.href='<%=ctxPath%>/questionList.bibo'">
						<span>QNA 더보기</span>
					</div>
					
				</div>
			</c:if>
			
			<c:if test="${requestScope.searchlist.ndtolist != null}">
				<div class="contentsub">
				
					<div class="c_subtitle">
						<span>공지사항</span>
					</div>
					<c:forEach var="ndto" items="${requestScope.searchlist.ndtolist}" >
						<div class="content_h">
							<div class="content_place">
								<div class="content_title" onclick="noticedetail('${ndto.nidx}')">${ndto.title}</div>
								<div class="content_content">${ndto.content}</div>
								<div class="content_writeday">${ndto.writeday}</div>
							</div>
						</div>
					</c:forEach>
					
					<div class="showmore" onclick="location.href = '<%=ctxPath%>/notice/noticeList.bibo'">
						<span>공지사항 목록보기</span>
					</div>
				
				</div>
			</c:if>
			
		</c:if>
	</div>
</div>

