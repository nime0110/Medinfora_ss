<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% String ctxPath = request.getContextPath(); %>

<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/reserve/choiceDay.css">

<script src='<%= ctxPath%>/resources/node_modules/fullcalendar/dist/index.global.min.js'></script>
<script type="text/javascript" src="<%= ctxPath%>/resources/js/reserve/choiceDay.js"></script>
<script>

	let calTitle = "";

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
	      dayCellContent: function (info) {
	    	    var number = document.createElement("a");
	    	    number.classList.add("fc-daygrid-day-number");
	    	    number.innerHTML = info.dayNumberText.replace("일",'');
	    	    if(info.view.type === "dayGridMonth"){
	    	        return {
	    	            html: number.outerHTML
	    	        };
	    	    }
	    	    return {
	    	        domNodes: []
	    	    };
	      },
	      locale: "ko",
	      fixedWeekCount : false,
	      eventClick: function(arg) { // 이벤트를 클릭했을때 발생하는 함수! 여기서 Ajax처리를 할수있다
	        
	    	$("form[name='choiceFrm'] > input[name='time']").val("");
	        const eventDate = JSON.stringify(arg.event._instance.range.start).substring(1,11); // 해당 이벤트의 날짜
	        
	        const DateNode = arg.el.parentNode.parentNode.parentNode.querySelector("a.fc-daygrid-day-number").childNodes[0];
	        
	        const DateAllNode = $("a.fc-daygrid-day-number");
	        
	        DateAllNode.each((idx,item)=>{
	        	
	        	item.classList.remove("checkbold");
	        	
	        });
	        
	        DateNode.classList.add("checkbold");
	        
	        const sendDate = {"hidx":"${requestScope.hidx}","date":eventDate};
	        
	        searchTimes(sendDate);
	        
	      },
	      editable: false, 		// 데이터 수정 불가능
	      events: origindata
	    });

	    calendar.render();
	    
	    // 첫로딩 셀랙트 START
	    
	    replaceTitle();
	    
	    const todayDate = $('.fc-day-today').find("a.fc-daygrid-day-number").find("a")[0];
	    
	    todayDate.classList.add("checkbold");
	    
		// 첫로딩 셀랙트 END
	    
	    $('.fc-next-button').on("click",function(){
	    	btnUpreplace();
	    });
	    
	    $('.fc-prev-button').on("click",function(){
	    	btnDownreplace();
	    });
	    
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
        		$("h3.selectDay").text(sendDate.date);
        		let v_html = ``;
        		$.each(json, function(index, item){
        			
        			v_html +=`<button type="button" class="timebtn">
                        			<span class="exTimebtn">\${item}</span>
                        	  </button>`;
        		})	// end of $.each(json, function(index, item){-----------
        			
        		$("div.choiceTime").html(v_html);
        			
        	}
        	, error: function(request, status, error){
	        	alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		    }
        })		//  end of $.ajax({------------------------------
		
	}	// end of function searchTimes(sendDate){--------------------------------
	
	function replaceTitle(){
		
		// Document가 로딩 될때 년월을 -으로 바꾸고 그 값을 저장하는 Function
		
		const titleEl = $('.fc-toolbar-title');
		let titleText = titleEl.text().replace("년"," -").replace("월",'');
		
		titleEl.text(titleText);
		calTitle = titleText;
			
	}
	
	function btnUpreplace(){
		
		// 달력 next 버튼을 눌렀을때 - 으로 재설정 해주는 Function
		
		const titleEl = $('.fc-toolbar-title');
		
		let year = Number(calTitle.substring(0,4));
		let month = Number(calTitle.substring(7));
		
		if(month == 12){
			year = year + 1;
			month = 1;
		}else{
			month = month + 1;
		}
		
		const replaceTitle = year+" - "+month;
		
		calTitle = replaceTitle;
		
		titleEl.text(replaceTitle);
		
	}
	
	function btnDownreplace(){
		
		// 달력 prev 버튼을 눌렀을때 - 으로 재설정 해주는 Function
		
		const titleEl = $('.fc-toolbar-title');
		
		let year = Number(calTitle.substring(0,4));
		let month = Number(calTitle.substring(7));
		
		if(month == 1){
			year = year - 1;
			month = 12;
		}else{
			month = month - 1;
		}
		
		const replaceTitle = year+" - "+month;

		calTitle = replaceTitle;
		
		titleEl.text(replaceTitle);
		
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
	    
	    <div id="hospitaltitle">
	    	${requestScope.hpname}
	    </div>
	    
        <div class="div_choiceDay">
        
            <div class="reserve_day">
            	
               	 <div id='calendar'></div>
               	 
            </div>
            
            <div class="choiceTimediv">
            
            	<div class="choiceTimedivineer">
            	
	                <h3 class="selectDay nanum-b size-n">
	                	<%-- 선택한 날짜 --%>
	                </h3>
	                <div class="choiceTime">
						<%-- 예약가능한 시간대 --%>
	                </div>
	                
                </div>
                
            </div>
            
        </div>
	        
        <div class="div_proc text-center mb-5">
	        <button type="button" class="btn_proc btn btn-lg mr-5" onclick="javascript:history.back()">뒤로</button>
	        <button type="button" class="reservationbtn btn_proc btn btn-lg">예약</button>
	    </div>
	    
    </div>
    
    <form name ="choiceFrm">
    	<input type="hidden" name="hidx" value="${requestScope.hidx}"/>
    	<input type="hidden" name="day" />
    </form>
    
</div>