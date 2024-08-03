<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<% String ctxPath = request.getContextPath(); %>

<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/mypage/reserveSchedule.css">

<script src='<%= ctxPath%>/resources/node_modules/fullcalendar/dist/index.global.min.js'></script>
<script type="text/javascript" src="<%= ctxPath%>/resources/js/mypage/reserveSchedule.js"></script>
<script>

const nameList = ${requestScope.nameList};
const timeList = ${requestScope.timeList};
const checkinEndList = ${requestScope.checkinEndList};

const data = [];

for(let i=0; i<nameList.length; i++){
	let name = nameList[i];
	let time = timeList[i];
	let endTime = checkinEndList[i];
	
	data.push({title:name,start:time,end:endTime});
}
  
document.addEventListener('DOMContentLoaded', function() {
	var calendarEl = document.getElementById('calendar');

    var calendar = new FullCalendar.Calendar(calendarEl, {
    	timeZone: 'SEOUL',
    	locale: "ko",
    	// 첫모습은 월 내용이 다 보이는 것으로 표시
      	initialView: 'dayGridMonth',

		// 버튼이름 커스텀
      	views: {
      		dayGridMonth: { buttonText: 'month' },
    		timeGridWeek: { buttonText: 'week' },
        	listDay: { buttonText: 'list day' },
        	listWeek: { buttonText: 'list week' }
      	},

      	// 헤더목록
      	headerToolbar: {
        	left: 'prev,next',
        	center: 'title',
        	right: 'dayGridMonth,timeGridWeek,listDay,listWeek'
      	},
      	editable: false,			// 데이터 수정 불가능하게 하기
      	dayMaxEvents: true,			// more 표시
      	allDaySlot: false, 			// all-day 영역 숨기기
      	displayEventEnd: false,		// end 시간 숨기기
      	events: data,
      	eventClick: function(arg) { // 이벤트를 클릭했을때 발생하는 함수! 여기서 Ajax처리를 할수있다
            
      		// 클릭한 이벤트 날짜와 시간
            const eventDate = JSON.stringify(arg.event._instance.range.start).substring(1,11) + " "
            				+ JSON.stringify(arg.event._instance.range.start).substring(12,17);
            // 날짜와 시간을 가지고 예약자의 정보 파악
            ShowInfo(eventDate);
            
         }

   });

   // 캘린더 생성
   calendar.render();
})	// end of document.addEventListener('DOMContentLoaded', function() {------

</script>

<div class="schedulebox">
	<%-- 달력들어올 예정 --%>
	<div id='calendar'></div>
</div>

<%-- 모달 --%>
<div class="modal fade" id="PatientInfoModal" tabindex="-1" role="dialog" aria-labelledby="PatientInfoModalLabel" aria-hidden="true">
	<div class="modal-dialog" role="document">
    	<div class="modal-content">
      		<div class="modal-header">
        		<h5 class="modal-title" id="PatientInfoModalLabel">예약자 정보</h5>
      		</div>
      		<div class="modal-body">
      			<span id="modal-name" class="nanum-b size-n"></span>
  				<div class="row-table" data-title="예약자 정보">
	   				<table>
	   					<%-- 성명, 연락처, 이메일, 주소를 띄워준다. --%>
	   					<tbody>
	    					<tr>
	    						<th scope="row">성명</th>
	    						<td id="modal-patient_name"></td>
	    					</tr>
	    					<tr>
			      				<th scope="row">연락처</th>
			      				<td id="modal-mobile"></td>
			      			</tr>
			      			<tr>
			      				<th scope="row">이메일</th>
			      				<td id="modal-email"></td>
			      			</tr>
			      			<tr>
			      				<th scope="row">주소</th>
			      				<td id="modal-address"></td>
			      			</tr>
			      			<tr>
			      				<th scope="row">생년월일</th>
			      				<td id="modal-birthday"></td>
			      			</tr>
			      			<tr>
			      				<th scope="row">성별</th>
			      				<td id="modal-gender"></td>
			      			</tr>
	   					</tbody>
	   				</table>
  				</div>
   			</div>
   		</div>
  	</div>
</div>
<%-- 모달 끝 --%>