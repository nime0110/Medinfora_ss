<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% String ctxPath = request.getContextPath(); %>

<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/reserve/choiceDay.css">
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/reserve/choiceDayMedia.css">

<script type="text/javascript" src="<%= ctxPath%>/resources/js/reserve/choiceDay.js"></script>
<script src='../dist/index.global.min.js'></script>
<script>

  // 이벤트 실제 불러온 데이터가 들어가는 부분이다 형식은 
  // [{"title":"이름","start":"날짜"},...{"title":"이름","start":"날짜"}] 형식으로 해야한다.
  data = [ 
    {
      title: '예약가능',
      start: '2023-01-02'
    },
    {
      title: '예약가능',
      start: '2023-01-03'
    }
  ];
      

  document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');

    var calendar = new FullCalendar.Calendar(calendarEl, {

      headerToolbar: { // 상단바 툴에 어떤 기능을 작성하는지에 대한 부분이다!
        left: 'prev',
        center: 'title',
        right: 'next'
      },
      initialDate: '2023-01-12', // 첫 기준이 되는 날짜를 선택할수 있다. (오늘날짜로 선택하면 됨!)
      locale: "ko", // 언어설정 "Korean"
      eventClick: function(arg) { // 이벤트를 클릭했을때 발생하는 함수! 여기서 Ajax처리를 할수있다
        
        const eventDate = JSON.stringify(arg.event._instance.range.start).substring(1,11); // 해당 이벤트의 날짜

        // hidx 정보와 eventDate를 Ajax를 통해 통신하면된다!

        const sendDate = {"hidx":"value(실제들어가야하는값)","date":eventDate};

        console.log(eventDate);

      },
      editable: false, // 데이터 수정이 가능한지 설정하는 부분 (true 면 변조가 가능하니 false로 한다)
      events: data
    });

    calendar.render();

  });

</script>



<div class="hj_container">
	<div class="reserveContent pt-3">
	    <div class="reserveTitlediv mt-5 pb-3">
	        <span class="reserve_title nanum-b size-b">온라인 진료예약</span>
	    </div>
	    <div class="mediseq">
	        <ul class="reserve_seq my-5">
	            <li class="reserve_li choicedr">
	            	<span class="reserve_licontent nanum-n">병원 선택</span>
	            </li>
	            <li class="reserve_li choiceday">
	            	<span class="reserve_licontent nanum-b">진료일시 선택</span>
	            </li>
	        </ul>
	    </div>
	    <%-- 달력에서 선택한 데이터를 어떻게 보내줄지 생각해야함(캘린더?) --%>
        <div class="div_choiceDay row mt-5">
            <div class="reserve_day col-md-6">
               	 <div id='calendar'>
               	 </div>
            </div>
            <div class="choiceTimediv col-md-6 pt-3 pl-5">
                <h3 class="nanum-b size-n">${requestScope.today_str}</h3>
                <div class="choiceTime row mt-3">
					${requestScope.availableTimeList}
                    <button type="button" class="timebtn mb-3 btn btn-lg col-3">
                        <span class="exTimebtn">09:00</span>
                    </button>
                    <button type="button" class="timebtn mb-3 btn btn-lg col-3">
                        <span class="exTimebtn">09:30</span>
                    </button>
                    <button type="button" class="timebtn mb-3 btn btn-lg col-3">
                        <span class="exTimebtn">10:00</span>
                    </button>
                    <button type="button" class="timebtn mb-3 btn btn-lg col-3">
                        <span class="exTimebtn">10:30</span>
                    </button>
                    <button type="button" class="timebtn mb-3 btn btn-lg col-3">
                        <span class="exTimebtn">11:00</span>
                    </button>
                    <button type="button" class="timebtn mb-3 btn btn-lg col-3">
                        <span class="exTimebtn">11:30</span>
                    </button>
                    <button type="button" class="timebtn mb-3 btn btn-lg col-3">
                        <span class="exTimebtn">12:00</span>
                    </button>
                    <button type="button" class="timebtn mb-3 btn btn-lg col-3">
                        <span class="exTimebtn">12:30</span>
                    </button>
                </div>
            </div>
        </div>
        <div class="div_proc text-center mb-5">
	        <button type="button" class="btn_proc btn btn-lg mr-5" onclick="javascript:history.back()">뒤로</button>
	        <button type="button" class="reservationbtn btn_proc btn btn-lg">예약</button>
	    </div>
    </div>
</div>