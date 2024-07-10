<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% String ctxPath = request.getContextPath(); %>

<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/reserve/choiceDay.css">
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/reserve/choiceDayMedia.css">
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/reserve/fullCalendar.css">

<script src='<%= ctxPath%>/resources/node_modules/fullcalendar/dist/index.global.min.js'></script>
<script type="text/javascript" src="<%= ctxPath%>/resources/js/reserve/choiceDay.js"></script>
<script>

	const dateList = ${requestScope.dateList};

	const origindata = [];
	
	jQuery(function(){
		
		for(let i=0; i<dateList.length; i++){
			origindata.push({title:'예약가능',start:dateList[i]});
		}
		
		var calendarEl = document.getElementById('calendar');

	    var calendar = new FullCalendar.Calendar(calendarEl, {

	      headerToolbar: {
	        left: 'prev',
	        center: 'title',
	        right: 'next'
	      },
	      locale: "ko",
	      eventClick: function(arg) { // 이벤트를 클릭했을때 발생하는 함수! 여기서 Ajax처리를 할수있다
	        
	        const eventDate = JSON.stringify(arg.event._instance.range.start).substring(1,11); // 해당 이벤트의 날짜

	        const sendDate = {"hidx":"${requestScope.hidx}","date":eventDate};
	        
	        searchTimes(sendDate);
	        
	      },
	      editable: false, 		// 데이터 수정 불가능
	      events: origindata
	    });

	    calendar.render();
	    
	    const day = new Date();
	    const year = day.getFullYear();
	    let month = day.getMonth() + 1;
	    let date = day.getDate();
	    
	    if(month < 10){
	        month = "0"+month;
	    }
	    if(date < 10){
	        date = "0"+date;
	    }
	    
	    const date_str = year + "-" + month + "-" + date;
	   	
	    searchTimes({"hidx":"${requestScope.hidx}","date":date_str});
	   	
	}) // end of jQuery(function(){})-------------------
	
	function searchTimes(sendDate){
		
		$.ajax({
        	url:'<%= ctxPath%>/reserve/selectDay.bibo'
        	, type:"post"
        	, data: sendDate
        	, dataType:"json"
        	, success:function(json){
        		console.log(json)
        	}
        	, error: function(request, status, error){
	        	alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		    }
        })
		
	}

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