let map;
let clusterer;
let markers = [];
let infowindows = [];
let overlays = [];
let positionArr = [];  
let markerImageArr = []; //위치 마커 이미지 배열
let openInfowindow = null; // 열려있는 인포윈도우를 추적
let openOverlay = null; // 열려있는 오버레이를 추적
const contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2)); // 컨텍스트 패스 


$(function() {
    // 로딩 이미지 숨기기
    $("div#loaderArr").hide();     

    // 모달 닫기 버튼 클릭 이벤트
    $('#closeModalButton').click(function(){
        $('#hospitalDetailModal').modal('hide');
    });

    // 지도 컨테이너와 옵션 설정
    let mapContainer = document.getElementById('map'),
        mapOption = {
            center: new kakao.maps.LatLng(37.566535, 126.9779692), // 초기 중심 좌표 (서울 시청)
            level: 3 // 초기 확대 레벨
        };

    // 지도 생성 
    map = new kakao.maps.Map(mapContainer, mapOption);

    // 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성함.    
    let mapTypeControl = new kakao.maps.MapTypeControl();
    // 지도 타입 컨트롤을 지도에 표시함.
    map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT); 

    // 지도 확대 축소를 제어할 수 있는 줌 컨트롤을 생성함.   
    let zoomControl = new kakao.maps.ZoomControl();
    // 지도 확대 축소를 제어할 수 있는 줌 컨트롤을 지도에 표시함.
    map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
	
                  
    clusterer = new kakao.maps.MarkerClusterer({
        map: map, // 마커들을 클러스터로 관리하고 표시할 지도 객체 
        averageCenter: true, // 클러스터에 포함된 마커들의 평균 위치를 클러스터 마커 위치로 설정 
        minLevel: 5, // 클러스터 할 최소 지도 레벨 
    });





	// ****************** 시/도, 진료과목 데이터 가져오기 start ************* 
	// 시/도 데이터 가져오기 ajax
	$.ajax({
		url:contextPath + "/getcityinfo.bibo",
		async:false,
		dataType:"json",
		success:function(json){
			let v_html = `<option value="">시/도 선택</option>`;
			for(let i=0; i<json.length; i++){
				v_html +=`<option value="${json[i]}">${json[i]}</option>`;
			}	
			$("select#city").html(v_html);
		},
		error:function(request){
			alert("code : " + request.status);
		}
	}) 
	
	// 진료과목 데이터 가져오기 ajax
	$.ajax({
		url:contextPath + "/getclasscode.bibo",
		async:false,
		dataType:"json",
		success:function(json){
			let v_html = `<option value="">진료과목 선택</option>`;
     		json.forEach(item => { 
				v_html +=`<option value="${item.classcode}">${item.classname}</option>`;
			});
				
			$("select#classcode").html(v_html);
		},
		error:function(request){
			alert("code : " + request.status);
		}
	}) 

    // 검색부분 작성
    $('#searchHpName').on('keydown', function(e) {
        if (e.keyCode == 13) {
            searchHospitals(1);
        }
    });
    
    //시-도 부분이 바뀌면 업데이트
    $('#city').on('change', function() {
    	updateSigunGu();
        updateDong();
    });
    
    // 시/군/구 부분이 바뀌면 동 업데이트
    $('#local').on('change', function() {	
        updateDong();
    });
    
});

// 지도 확대, 축소 컨트롤에서 확대 버튼을 누르면 호출되어 지도를 확대하는 함수
window.zoomIn = function() {
    map.setLevel(map.getLevel() - 1);
}

// 지도 확대, 축소 컨트롤에서 축소 버튼을 누르면 호출되어 지도를 축소하는 함수
window.zoomOut = function() {
    map.setLevel(map.getLevel() + 1);
}


// 시 도 선택시 업데이트 되는 함수   start
function updateSigunGu() {
    let city_val = $('#city').val(); // 서울특별시, 경기도

    const city = { "city": city_val };
    
    //console.log("area:", area);
    
    $.ajax({
        url: contextPath + "/getlocalinfo.bibo",
        async: false,
        data: city, // 데이터 객체 전달
        dataType: "json",
        success: function(json) {
            let v_html = `<option value="">시/군구 선택</option>`;
            for (let i = 0; i < json.length; i++) {
                if (json[i] != null) {	
                    v_html += `<option value="${json[i]}">${json[i]}</option>`;
                }
            }	
            $("select#local").html(v_html);
        },
        error: function(request) {
            alert("code : " + request.status + "\nmessage : " + request.responseText);
        }
    });	// end of $.ajax({-------------
}
// 시 도 선택시 업데이트 되는 함수   end

// 시군구 선택시 동이 업데이트 되는 함수   start
function updateDong() {
    let city_val = $('#city').val();
    let local_val = $('#local').val(); 
    const cityLocal = { "city": city_val, "local": local_val };
    $.ajax({
        url: contextPath + "/getcountryinfo.bibo",
        async: false,
        data: cityLocal, // 데이터 객체 전달
        dataType: "json",
        success: function(json) {
            let v_html = `<option value="">읍/면/동 선택</option>`;
            for (let i = 0; i < json.length; i++) {
                if (json[i] != null) {	
                    v_html += `<option value="${json[i]}">${json[i]}</option>`;
                }
            }	// end of for---------
            $("select#country").html(v_html);
        },
        error: function(request) {
            alert("code : " + request.status + "\nmessage : " + request.responseText);
        }
    });	// end of $.ajax({-------------
}
// 시 도 선택시 업데이트 되는 함수   end



var currentPage = 1; // 현재 페이지를 추적
// 시/군/구를 기반으로 병원 검색하면 리스트가 보이는 함수!! 
function searchHospitals(pageNo) {
    clearAllwithmarker(); // 인포윈도우와 오버레이 초기화
    clearClusterer(); // 클러스터러 초기화
    let city = '경기도' //$('#city').val();
    let local = '고양시 일산동구' //$('#local').val();
    let country = '백석동' //$('#country').val();
    let classcode = $('#classcode').val();
    let agency = $('#agency').val();
	let hpname = $('#searchHpname').val();
	let addr = city + " " + local;


    /*
    if (!city ) {
        alert("시/도를 선택하세요");
        return;
    }
	
    if (!local ) {
        alert("시/군/구를 선택하세요");
        return;
    }
    if (!country ) {
        alert("읍/면/동을 선택하세요");
        return;
    }
        */
	
    $.ajax({
        url: contextPath +'/hpsearch/hpsearchAdd.bibo', 
        data: { 
        		addr: addr, 
        		country: country,
        		classcode: classcode, 
        		agency: agency,
        		hpname: hpname,
        		currentShowPageNo: pageNo
        	   },
        dataType: "json",
        success: function(json) {
            removeMarkers();
            positionArr = []; // 기존 위치 배열 초기화
            overlays = []; // overlays 초기화 
            
            $('#hospitalList').empty(); // 기존 병원 리스트 초기화
			let v_html = "";
			if(json.length > 0) {
	           json.forEach((item, index) => { // 병원의 위도, 경도를 위치 객체로 변환
	           		var position = {}; // 위도경도랑 content 담음 

	                position.latlng = new kakao.maps.LatLng(item.wgs84lat, item.wgs84lon); // 위도, 경도
                    //인포윈도우에 들어갈 텍스트
	                position.content = `<div class='mycontent' data-index="${index}">
									    	<div class="title"> ${item.hpname} </div>
									    	<div class="content"> 
									    		<div class="info">
									    			<strong>${item.classname}</strong>
									    		</div>
									    		<p class="tel">
									    			<span>${item.hptel}</span>
									    		</p>
									    		<p class="add">								    		
										    		${item.hpaddr} 
									    		</p>
                                                <button class="details-button"  onclick="detailSearch(${index})">상세보기</button>
									    	</div>		    	 
                    				    </div>`;
                    // 병원 이름을 추가
                    position.hpname = item.hpname;
                    positionArr.push(position);	
                    const alphabetIndex = String.fromCharCode(65 + index); 
	                // 병원 리스트로 출력
                    v_html += `<div class="hospital-details" data-index="${index}">
                                <input type="hidden" name="${item.hidx}"></input>
                                <div class="hospital-label nanum-n">${alphabetIndex}</div>
                                <h2 class="hospital-name">${item.hpname}</h2>
                                <p class="hospital-type nanum-n">${item.classname}</p>
                                <p class="hospital-contact nanum-n">TEL: ${item.hptel} </p>
                                <p class="hospital-address nanum-n">${item.hpaddr}</p>
                                <button class="details-button nanum-n" onclick="detailSearch(${index})">상세보기</button>
                            </div>`;

                                
                                
	            }); //end of forEach -----------------------------------

                    
                let imageArr = []; // 이미지 경로를 저장하는 배열
                markerImageArr = [];
                let bounds = new kakao.maps.LatLngBounds(); // 마커 범위 
                markers = []; // 마커를 저장하는 배열
                overlays = []; // 커스텀 오버레이를 저장하는 배열
                let infowindows = []; // 인포윈도우를 저장하는 배열

                
                for (let i = 0; i < positionArr.length; i++) { //마커를 표시할 위치와 내용을 가지고 있는 객체 배열 positionArr
                    
                    $('#hospitalList').children().eq(i).find('.hospital-label').removeClass('click-maker');
                    let imageSrc = contextPath + '/resources/img/marker/ico_marker' + (i + 1) + '_on.png'; // 마커 이미지 경로 설정
                    imageArr.push(imageSrc); // 배열에 이미지 경로를 추가

                    //console.log("imageArr:", imageArr[i]);
                    //console.log("positionArr:", positionArr);
                    let imageSize = new kakao.maps.Size(24, 35); // 마커 이미지 크기 설정
                    let markerImage = new kakao.maps.MarkerImage(imageArr[i], imageSize); // 마커 이미지 생성
                    markerImageArr.push(markerImage); // 마커이미지 배열에 넣기

                    // 마커 생성
                    let marker = new kakao.maps.Marker({
                        map: map,
                        position: positionArr[i].latlng, // locPosition 좌표에 마커를 생성
                        image: markerImageArr[i]
                    });                  
                    // 클러스터러에 마커들을 추가합니다
                    clusterer.addMarkers(markers);
                    // 마커를 배열에 추가
                    markers.push(marker);
       
                    // 모든 마커가 한 번에 보이도록 지도의 중심과 확대레벨을 설정
                    bounds.extend(positionArr[i].latlng); 
                    map.setBounds(bounds);

                    // 마커에 표시할 인포윈도우를 생성하기
                    var infowindow = new kakao.maps.InfoWindow({
                        content: positionArr[i].content, 
                        removable: true
                    });
                    // 인포윈도우를 가지고 있는 객체배열에 넣기
                    infowindows.push(infowindow);


                    
                    // 마커 위에 인포윈도우를 표시하는 클릭 이벤트 
                    kakao.maps.event.addListener(marker, 'click', function() { 

                        var level = map.getLevel() - 2;
                        map.setLevel(level, {anchor: this.getPosition()});

                        // 열려있는 인포윈도우가 있으면 닫기
                        if (openInfowindow) {
                            openInfowindow.close();
                        }
                        infowindows[i].open(map, marker);
                        openInfowindow = infowindows[i];
                        
                        // 열려있는 오버레이가 있으면 닫기
                        if (openOverlay) {
                            openOverlay.setMap(null);
                        }
                        /*
                        // 오버레이 열기
                        if (overlays[i]) {  
                            overlays[i].setMap(openOverlay.map);
                            openOverlay = overlays[i];
                        }*/
                        
                        // 마커 클릭시 해당 병원의 리스트로 이동
                        const hplist = $('#hplist');
                        let hospitalItem = $('#hospitalList').children().eq(i);

                        // 병원 리스트의 스크롤 위치 계산
                        let scrollPosition = hospitalItem.offset().top - hplist.offset().top + hplist.scrollTop();

                        // 병원 리스트의 스크롤 위치 설정
                        hplist.scrollTop(scrollPosition);
                        
                        // 마커 클릭시 해당 병원 리스트 hospital-label에 css 클래스 추가
                        $('#hospitalList').find('.hospital-label').removeClass('click-maker'); // 모든 항목에서 클래스 제거
                        hospitalItem.find('.hospital-label').addClass('click-maker'); // 클릭한 항목에 클래스 추가

                        console.log("마커 클릭시 해당 병원의 리스트로 이동: ", hospitalItem.position().top);


                    });
                                  
                    
                } //end of for (let i = 0; i < positionArr.length; i++) ------------- 
                    


                
                //마커가 하나 이상일때 그 마커들의 위경도가 서로 같다면 이진탐색    1,2,3,4 순서로 생성되어있음 
                if (markers.length > 1) { 
                    for (let i = 0; i < markers.length; i++) {    
                        //console.log("markers[i].getPosition() :" + markers[i].getPosition());   //(37.64235645995963, 126.7878839598955)
                        for(let j=i+1; j<markers.length; j++){
                            //두 마커의 위경도가 같다면
                            if(markers[i].getPosition().equals(markers[j].getPosition())){
                                // 중복된 위치에 있는 병원명을 모두 추가
                                let combinedContent = `<div class="cb-box">`;
                                combinedContent += `<div class="title cb-content" data-index="${i}"> ${positionArr[i].hpname} </div>`; // 첫번째 중복 마커 병원명 추가
                                for (let k = j; k < markers.length; k++) { // 나머지 중복 마커 병원명 추가
                                    if(markers[i].getPosition().equals(markers[k].getPosition())){
                                        combinedContent += `<div class="title cb-content" data-index="${k}"> ${positionArr[k].hpname} </div>`;
                                    }
                                }
                                combinedContent += `</div>`;

                                // 중복된 병원명을 모두 포함한 커스텀 오버레이 생성
                                let customOverlay = new kakao.maps.CustomOverlay({
                                    content: `<div class="custom-overlay">${combinedContent}</div>`,
                                    position: markers[i].getPosition(),
                                    yAnchor: 1, // 위치 조정
                                    clickable: true // 클릭 가능하도록 설정  지도 이벤트를 막아준다. 
                                });

                                // 커스텀 오버레이 배열에 추가
                                overlays.push(customOverlay);

                                (function(marker, customOverlay) {
                                    // 마커 위에 커스텀 오버레이 표시하는 클릭 이벤트
                                    kakao.maps.event.addListener(marker, 'click', function() { 
                                        console.log('커스텀오버레이 마커 클릭됨:', marker.getPosition()); // 로그 추가
                                    // 열려있는 오버레이가 있으면 닫기
                                    if (openOverlay) {
                                        openOverlay.setMap(null);
                                    }
                                    customOverlay.setMap(map);        
                                    openOverlay = customOverlay;  
               
                                    });
                                })(markers[j], customOverlay);

 
                            }
                        }   
                    }
                }
                // 커스텀오버레이 안의 병원 이름 클릭 이벤트 추가
                $(document).on('click', '.cb-content', function(event) {
                   // event.stopPropagation(); // 이벤트 전파 막기
                    //event.stopImmediatePropagation(); // 즉시 전파 막기
                    
                    var index = $(this).data('index');
                    console.log('커스텀 오버레이 병원 이름 클릭됨:', index); // 로그 추가
                    map.setCenter(positionArr[index].latlng);

                    // 마커 클릭 이벤트 트리거
                    kakao.maps.event.trigger(markers[index], 'click');

                    // 커스텀 오버레이 유지
                    if(openOverlay != null) {  
                        openOverlay.setMap(map);
                    }

                    if (overlays[index]) {
                        for (let i = 0; i <=index; i++) {
                            overlays[i].setMap(map);
                            openOverlay = overlays[i];
                        }
                    }
                });

                // 지도를 클릭하면 인포윈도우/오버레이를 닫기
                kakao.maps.event.addListener(map, 'click', function(event) {  
                    if (openInfowindow) {
                        openInfowindow.close();
                        openInfowindow = null; // 열려있는 인포윈도우를 초기화
                    }
                    $('#hospitalList').find('.hospital-label').removeClass('click-maker'); // 병원 리스트 선택클래스 제거
                    // 열려있는 오버레이가 있으면 닫기
                    /*
                    if (openOverlay) {
                        openOverlay.setMap(null);
                        openOverlay = null; // 열려있는 오버레이 초기화
                    }
                        */

                    // 모든 오버레이를 닫기
                    if (overlays) {  
                        overlays.forEach(function(overlay) {
                            overlay.setMap(null);
                        });
                    }

                });
                    
                // 병원 리스트 항목 클릭 이벤트 추가
                $('#hospitalList').on('click', '.hospital-details', function() {
                    var index = $(this).data('index');
                    map.setCenter(positionArr[index].latlng);
                    
                    // // 인포윈도우 열기
                    // infowindows[index].open(map, markers[index]);
                    // openInfowindow = infowindows[index];

                    // 마커 클릭 이벤트 트리거
                    kakao.maps.event.trigger(markers[index], 'click');
                    // 열려있는 오버레이가 있으면 닫기
                    
                });
     
            } else {
                v_html += `<div id="no_searchList">
		        		<span>😥</span>
		            	<p>검색된 의료기관이 없습니다.</p>
		        	</div>`;
            } // end of if(json.length > 0) -------------------------------
            
            $('#hospitalList').append(v_html);
            const totalPage = Math.ceil(json[0].totalCount / json[0].sizePerPage); 
            //console.log("토탈페이지", totalPage); //3 나옴
            
            displayPagination(totalPage, pageNo);
        }, //end of  success: function(json)  ------------------
        error: function(request, status, error){
            alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
        }
    });   
}

function displayPagination(totalPage, currentPage) {
    clearAllwithmarker(); // 인포윈도우와 오버레이 초기화
    var paginationDiv = $('#rpageNumber');
    paginationDiv.empty();

    if (totalPage > 0) {
        // 처음 페이지로 이동
        paginationDiv.append('<span class="page-link" data-page="1">[맨처음]</span>');

        for (var i = 1; i <= totalPage; i++) {
            var link = $('<span class="page-link"></span>').text(i).attr('data-page', i);

            if (i === currentPage) {
                link.addClass('active');
            }

            paginationDiv.append(link);
        }

        // 마지막 페이지로 이동
        paginationDiv.append('<span class="page-link" data-page="' + totalPage + '">[마지막]</span>');
    }

    $('#rpageNumber .page-link').on('click', function(e) {
        e.preventDefault();
        var page = $(this).data('page');
        searchHospitals(page);

        $('#rpageNumber .page-link').removeClass('active');
        $(this).addClass('active');
    });
}

// ================ marker, infowindows start====================== 
// 지도에서 모든 마커를 제거하는 함수
function removeMarkers() {
    
    for (let i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
    }
    markers = [];
}

function clearClusterer() {
    if (clusterer) {
        clusterer.clear(); // 클러스터러에서 모든 마커 제거
    }
}

// 인포윈도우와 오버레이, 클러스터러 초기화 함수
function clearAllwithmarker() {
    if (openInfowindow) {
        openInfowindow.close();
        openInfowindow = null;
    }

    if (openOverlay) {
        openOverlay.setMap(null);
        openOverlay = null;
    }
}


// ================ marker, infowindows end ====================== 

// 상세보기 함수
function detailSearch(index) {
    let hidx = $('#hospitalList').children().eq(index).find('input').attr('name');
    //console.log("hidx:", hidx); 37516
    $.ajax({
        url: contextPath + '/hpsearch/hpsearchDetail.bibo',
        data: {hidx: hidx},
        dataType: "json",
        success: function (json) {
            console.log(JSON.stringify(json));
            /* 
            {"agency":"의원","hidx":77937,"hpname":"의료법인마리아의료재단마리아의원",
            "endtime4":"1700","endtime5":"1700","endtime6":"1200","endtime1":"1700",
            "hpaddr":"경기도 고양시 일산동구 중앙로 1060, 2,3층,4(일부)층 (백석동)",
            "endtime2":"1700","endtime3":"1700","starttime5":"0730","starttime6":"0730",
            "starttime3":"0730","starttime4":"0730","starttime1":"0730","hptel":"031-924-6555",
            "starttime2":"0730"}
            */
            // 시작시간과 종료시간을 시간 형식으로 변환
            for (let i = 1; i <= 8; i++) {
                let startkey = 'starttime' + i;
                let endkey = 'endtime' + i;
                // 존재하는 starttime 필드만 포맷팅
                // hasOwnProperty(key) => json 객체에 key 속성이 있는지 확인
                // json[key] => : json 객체의 key 속성의 값을 가져옴
                if (json.hasOwnProperty(startkey) && json[startkey]) { //시작시간존재시 끝나는시간 무조건 존재
                    json[startkey] = json[startkey].substring(0, 2) + "시 " + json[startkey].substring(2, 4) + "분";
                    json[endkey] = json[endkey].substring(0, 2) + "시 " + json[endkey].substring(2, 4) + "분";
                    $('#modal-daytime' + i).text(json[startkey] + " ~ " + json[endkey]);
                } else {
                    $('#modal-daytime' + i).text("휴진");
                }
            }
            //console.log("json.starttime1:", json.starttime1); // 07시 30분
            //console.log("json.endtime1:", json.endtime1); // 17시 00분


            
            // 모달 내용 업데이트
            $('#modal-hpname').text(json.hpname);
            $('#modal-hpaddr').text(json.hpaddr);   
            $('#modal-hptel').text(json.hptel);
            $('#modal-classname').text(json.classname);
            $('#modal-agency').text(json.agency);


            // 모달 표시
            $('#hospitalDetailModal').modal('show');


        }, //end of  success: function(json)  ------------------
        error: function(request, status, error){
            alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
        }
    }); //end of $.ajax({-------------
} //end of function detailSearch(index) ------------------
