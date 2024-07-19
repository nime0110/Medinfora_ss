<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<% String ctxPath = request.getContextPath(); %>

<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/mypage/reserveSchedule.css">

<script src='<%= ctxPath%>/resources/node_modules/fullcalendar/dist/index.global.min.js'></script>
<script type="text/javascript" src="<%= ctxPath%>/resources/js/mypage/reserveSchedule.js"></script>
<script>

data = [ 
    {
      title: '양혜정1',
      start: '2024-07-20 14:00'
    },
    {
      title: '양혜정2',
      start: '2024-07-20 15:00'
    }
    
];
  
document.addEventListener('DOMContentLoaded', function() {
	var calendarEl = document.getElementById('calendar');

    var calendar = new FullCalendar.Calendar(calendarEl, {
    	timeZone: 'SEOUL',
    	locale: "ko",
      	initialView: 'listWeek',

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
      	editable: false,	// 데이터 수정 불가능하게 하기
      	events: data

   });

   // 캘린더 생성
   calendar.render();
});
</script>

<div class="schedulebox">
	<%-- 달력들어올 예정 --%>
	<div id='calendar'></div>
</div>