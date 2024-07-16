let map;
let clusterer;
let markers = [];
let infowindows = [];
let overlays = [];
let positionArr = [];  
let markerImageArr = []; 
let openInfowindow = null;
let openOverlay = null; 
const contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));


$(function() {
    /* 비동기 처리 코드 
    $.ajaxSetup({
		async : false 
	});
    */
    // 로딩 이미지 숨기기
    $("div#loaderArr").hide();     

    $('#closeModalButton').click(function(){
        $('#hospitalDetailModal').modal('hide');
    });

    const tabButtons = $('.tab-button');
    const tabContents = $('.tab-content');

    $('#map_box').addClass('active');

    tabButtons.on('click', function() {
        const tab = $(this).data('tab');

        tabButtons.removeClass('active');
        $(this).addClass('active');

        tabContents.removeClass('active');
        $(`#${tab}`).addClass('active');
    });

    // 지도 컨테이너와 옵션 설정
    let mapContainer = document.getElementById('map'),
        mapOption = {
            center: new kakao.maps.LatLng(37.566535, 126.9779692), // 초기 중심 좌표 (서울 시청)
            level: 12 // 초기 확대 레벨
        };

    // 지도 생성 
    map = new kakao.maps.Map(mapContainer, mapOption);

   
    // 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤 생성함.    
    let mapTypeControl = new kakao.maps.MapTypeControl();
    map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT); 

    let zoomControl = new kakao.maps.ZoomControl();
    map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
	
    kakao.maps.event.addListener(map, 'zoom_changed', function () {
        level = map.getLevel();
    });    

    clusterer = new kakao.maps.MarkerClusterer({
        map: map, 
        averageCenter: true, 
        minLevel: 5, 
    });

    // 통계 start
    
    
       // 차트를 표시할 DOM 요소를 가져옴
    var dom = document.getElementById('hp_chart');
    
    // ECharts 인스턴스를 초기화
    var myChart = echarts.init(dom, null, {
      renderer: 'canvas',  // 렌더러 설정
      useDirtyRect: false  // 더티 렉트 사용 여부 설정
    });
    let hpdata = new Array();

    // 진료과목 데이터 가져오기 
	$.ajax({
		url:contextPath + "/getclasscode.bibo",
		async:false,
		dataType:"json",
		success:function(json){
            $.each(json, function(index, item){
                hpdata.push({value: 200, name: item.classname});
            });
         
		},
		error:function(request){
			alert("code : " + request.status);
		}
	}) 



    var option;

    // 차트 옵션 설정
    option = {
      tooltip: {
        trigger: 'item' 
      },
      legend: {
        top: '5%', 
        left: 'center' 
      },
      series: [
        {
          name: 'Access From', 
          type: 'pie',  
          radius: ['40%', '70%'],  
          avoidLabelOverlap: true,  
          itemStyle: {
            borderRadius: 10,  
            borderColor: '#fff',  
            borderWidth: 2, 
          },
          label: {
            show: false,  
            position: 'center' 
          },
          emphasis: {  
            label: {  
              show: true, 
              fontSize: 40, 
              fontWeight: 'bold' 
            }
          },
          labelLine: {
            show: false  
          },
          data: hpdata
        }
      ]
    };


    if (option && typeof option === 'object') {
      myChart.setOption(option);
    }


   var clickedIndex;

	myChart.on('click', function (params) {

	  myChart.dispatchAction({
	    type: 'downplay',
	    seriesIndex: 0
	  });
	

	  myChart.dispatchAction({
	    type: 'highlight',
	    seriesIndex: 0,
	    dataIndex: params.dataIndex
	  });

	  clickedIndex = params.dataIndex;
	});
	

	myChart.on('mouseover', function (params) {
	  if (clickedIndex !== undefined && params.dataIndex === clickedIndex) {
	  } else { 
	    myChart.dispatchAction({
	      type: 'downplay',
	      dataIndex: clickedIndex
	    });
	  }
	});
	

	myChart.on('mouseout', function (params) {
	  if (clickedIndex !== undefined) {

	    myChart.dispatchAction({
	      type: 'highlight',
	      seriesIndex: 0,
	      dataIndex: clickedIndex
	    });
	  }
	});

    window.addEventListener('resize', myChart.resize);

    // 통계 end 
    

    
    
});

window.zoomIn = function() {
    map.setLevel(map.getLevel() - 1);
}

window.zoomOut = function() {
    map.setLevel(map.getLevel() + 1);
}

let currentPage = 1; // 현재 페이지를 추적

// 시/군/구를 기반으로 병원 검색하면 리스트가 보이는 함수
function searchHospitals(pageNo) {
    clearAllwithmarker(); 
    clearClusterer(); 

    let checkbox = $('#check-status');
    let checkbox_val = ' ';
    if(checkbox.is(':checked')) { 
        checkbox_val = checkbox.val();
    }

    console.log(checkbox_val);

    let city = $('#city').val();
    let local = $('#local').val();
    let country = $('#country').val();
    let classcode = $('#classcode').val();
    let agency = $('#agency').val();
	let hpname = $('#searchHpname').val();
	let addr = city + " " + local;
    const localOptionLength = $('#local').find('option').length;

    if (localOptionLength > 1 && !country) { 
        updateDong();
    }

    if (!city ) {
        alert("시/도를 선택하세요");
        return;
    }
	
    if (!local ) {
        alert("시/군/구를 선택하세요");
        return;
    }
	
    $.ajax({
        url: contextPath +'/hpsearch/hpsearchAdd.bibo', 
        data: { 
        		addr: addr, 
        		country: country,
        		classcode: classcode, 
        		agency: agency,
        		hpname: hpname,
                checkStatus: checkbox_val,
        		currentShowPageNo: pageNo
        	   },
        dataType: "json",
        success: function(json) {
            removeMarkers();
            positionArr = []; 
            overlays = []; 

            $('#hospitalList').empty(); 
			let v_html = "";
			if(json.length > 0) {
	           json.forEach((item, index) => { 
                    let position = {};
	                position.latlng = new kakao.maps.LatLng(item.wgs84lat, item.wgs84lon);
                    // 인포윈도우
	                position.content = `<div class='mycontent' data-index="${index}">
									    	<div class="title"> ${item.hpname} </div>
									    	<div class="content"> 
									    		<div class="info">
									    			<strong>${item.classname}</strong>
									    		</div>
									    		<p class="addr">${item.hpaddr}</p>
                                                <button class="details-button" onclick="detailSearch(${index})">상세보기</button>
									    	</div>		    	 
                    				    </div>`;
                    position.hpname = item.hpname;
                    positionArr.push(position);	
                    const alphabetIndex = String.fromCharCode(65 + index); 
                    // 리스트 부분
                    v_html += `<div class="hospital-details" data-index="${index}">
                                <input type="hidden" name="${item.hidx}"></input>
                                <div class="index-name-flexbox">
                                    <div class="hospital-label nanum-n">${alphabetIndex}</div>
                                    <h2 class="hospital-name">${item.hpname}</h2>
                                    <p class="hospital-type">${item.classname}</p>
                                </div>
                                <div class="status-flexbox">
                                    ${item.status === "진료중" ? '<div class="day-on-circle"></div>' : ''}
                                    <p class="status ${item.status === "진료중" ? 'day-on' : 'day-off'}">${item.status}</p>
                                </div>
                                <p class="hospital-address nanum-n">${item.hpaddr}</p>
                                <button class="details-button nanum-n" onclick="detailSearch(${index})">상세보기</button>
                                </div>`;            
                    
	            }); //end of forEach -----------------------------------

                let imageArr = []; 
                markerImageArr = [];
                let bounds = new kakao.maps.LatLngBounds();
                markers = []; 
                overlays = []; 
                let infowindows = [];

                for (let i = 0; i < positionArr.length; i++) { 
                    
                    $('#hospitalList').children().eq(i).find('.hospital-label').removeClass('click-maker');
                    let imageSrc = contextPath + '/resources/img/marker/ico_marker' + (i + 1) + '_on.png'; 
                    imageArr.push(imageSrc);

                    let imageSize = new kakao.maps.Size(24, 35);
                    let markerImage = new kakao.maps.MarkerImage(imageArr[i], imageSize); 
                    markerImageArr.push(markerImage); 

                    // 마커 생성
                    let marker = new kakao.maps.Marker({
                        map: map,
                        position: positionArr[i].latlng, 
                        image: markerImageArr[i]
                    });               

                    clusterer.addMarkers(markers);
                    markers.push(marker);
       
                    // 모든 마커가 한 번에 보이도록 지도의 중심과 확대레벨을 설정
                    bounds.extend(positionArr[i].latlng); 
                    map.setBounds(bounds);

                    // 마커에 표시할 인포윈도우를 생성하기
                    let infowindow = new kakao.maps.InfoWindow({
                        content: positionArr[i].content, 
                        removable: true
                    });

                    infowindows.push(infowindow);
                    
                    kakao.maps.event.addListener(marker, 'click', function() { 
                        let level = map.getLevel() - 4;
                        map.setLevel(level, {anchor: this.getPosition()});

                        if (openInfowindow) {
                            openInfowindow.close();
                        }

                        infowindows[i].open(map, marker);
                        openInfowindow = infowindows[i];

                        if (openOverlay) {
                            openOverlay.setMap(null);
                        }
                        
                        const hplist = $('#hplist');
                        let hospitalItem = $('#hospitalList').children().eq(i);

                        let scrollPosition = hospitalItem.offset().top - hplist.offset().top + hplist.scrollTop();

                        hplist.scrollTop(scrollPosition);

                        $('#hospitalList').find('.hospital-label').removeClass('click-maker'); 
                        hospitalItem.find('.hospital-label').addClass('click-maker'); 

                    });
                                  
                } //end of for (let i = 0; i < positionArr.length; i++) ------------- 
                if (markers.length > 1) {
                    // 중복 마커 위치를 저장하기 위한 Map 객체 생성
                    let markerPositions = new Map();
                
                    for (let i = 0; i < markers.length; i++) {
                        let position = markers[i].getPosition().toString(); // 마커 위치를 문자열로 변환
                        if (!markerPositions.has(position)) {
                            markerPositions.set(position, []); // 새로운 위치인 경우 배열 생성
                        }
                        markerPositions.get(position).push(i); // 해당 위치에 마커 인덱스 추가
                    }
                
                    // 각 위치에 대해 중복 마커 확인
                    markerPositions.forEach((indices, position) => {
                        if (indices.length > 1) { // 중복 마커가 있는 경우
                            let combinedContent = `<div class="cb-box">`;
                            indices.forEach(index => {
                                combinedContent += `<div class="title cb-content" data-index="${index}"> ${positionArr[index].hpname} </div>`;
                            });
                            combinedContent += `</div>`;
                
                            // 중복 마커에 대한 오버레이 생성
                            let customOverlay = new kakao.maps.CustomOverlay({
                                content: `<div class="custom-overlay">${combinedContent}</div>`,
                                position: markers[indices[0]].getPosition(),
                                yAnchor: 1,
                                clickable: true
                            });
                
                            overlays.push(customOverlay);
                
                            // 중복 마커에 대해 클릭 이벤트 추가
                            indices.forEach(index => {
                                (function(marker, customOverlay) {
                                    kakao.maps.event.addListener(marker, 'click', function() {
                                        if (openOverlay) {
                                            openOverlay.setMap(null);
                                        }
                                        customOverlay.setMap(map);
                                        openOverlay = customOverlay;
                                    });
                                })(markers[index], customOverlay);
                            });
                        }
                    });
                }

                $(document).on('click', '.cb-content', function(event) {
                    
                    let index = $(this).data('index');
                    map.setCenter(positionArr[index].latlng);

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

                kakao.maps.event.addListener(map, 'click', function(event) {  
                    if (openInfowindow) {
                        openInfowindow.close();
                        openInfowindow = null; 
                    }
                    $('#hospitalList').find('.hospital-label').removeClass('click-maker'); 
                    if (overlays) {  
                        overlays.forEach(function(overlay) {
                            overlay.setMap(null);
                        });
                    }
                });
                    
                $('#hospitalList').on('click', '.hospital-details', function() {
                    let index = $(this).data('index');
                    map.setCenter(positionArr[index].latlng);
                    kakao.maps.event.trigger(markers[index], 'click');
                });
                
            } else {
                v_html += `<div id="no_searchList">
                <span>😥</span>
                <p>검색된 의료기관이 없습니다.</p>
                </div>`;
                removedisplayPagination();
            } // end of if(json.length > 0) -------------------------------
            
            $('#hospitalList').append(v_html);
            if(json.length > 0) {
                displayPagination(json[0].totalPage, pageNo);
            }
            removePolygon();
        }, //end of  success: function(json)  ------------------
        error: function(request, status, error){
            alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
        }
    });   
}

// 지도에 마커 추가하는 함수
function addMarker(position, message) {
    var marker = new kakao.maps.Marker({
        map: map,
        position: position
    });

    var infowindow = new kakao.maps.InfoWindow({
        content: '<div style="padding:5px;">' + message + '</div>'
    });
    infowindow.open(map, marker);

    // 마커 배열에 추가
    markers.push(marker);
}












function removedisplayPagination() {
    $('#rpageNumber').empty();
}


function displayPagination(totalPage, currentPage) {
    clearAllwithmarker(); 
    let paginationDiv = $('#rpageNumber');
    paginationDiv.empty();

    let pageGroup = Math.ceil(currentPage / 5);
    let lastPage = pageGroup * 5;
    let firstPage = lastPage - 4;

    if (lastPage > totalPage) {
        lastPage = totalPage;
    }

    if (totalPage > 0) {
        // 이전 페이지 그룹으로 이동
        if (firstPage > 1) {
            paginationDiv.append('<span class="page-link" data-page="' + (firstPage - 1) + '">[이전]</span>');
        }

        for (let i = firstPage; i <= lastPage; i++) {
            let link = $('<span class="page-link"></span>').text(i).attr('data-page', i);
            if (i === currentPage) {
                link.addClass('active');
            }
            paginationDiv.append(link);
        }

        // 다음 페이지 그룹으로 이동
        if (lastPage < totalPage) {
            paginationDiv.append('<span class="page-link" data-page="' + (lastPage + 1) + '">[다음]</span>');
        }

        $('#rpageNumber .page-link').on('click', function(e) {
            e.preventDefault();
            let page = $(this).data('page');
            searchHospitals(page);
            $('#rpageNumber .page-link').removeClass('active');
            $(this).addClass('active');
        });
    }
}

// 지도에서 모든 마커를 제거하는 함수
function removeMarkers() {
    for (let i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
    }
    markers = [];
}

// 클러스터러에서 모든 마커 제거하는 함수
function clearClusterer() {
    if (clusterer) {
        clusterer.clear(); 
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

 
// 상세보기 함수
function detailSearch(index) {
    let hidx = $('#hospitalList').children().eq(index).find('input').attr('name');
    $.ajax({
        url: contextPath + '/hpsearch/hpsearchDetail.bibo',
        data: {hidx: hidx},
        dataType: "json",
        success: function (json) {
            // 모달 내용 업데이트
            $('#modal-hpname').text(json.hpname);
            $('#modal-hpaddr').text(json.hpaddr);   
            $('#modal-hptel').text(json.hptel);
            $('#modal-classname').text(json.classname);
            $('#modal-agency').text(json.agency);
            // 모달 표시
            $('#hospitalDetailModal').modal('show');
            for (let i = 1; i <= 8; i++) {
                $('#modal-daytime' + i).text(json['time' + i]);
			}	
        }, //end of  success: function(json)  ------------------
        error: function(request, status, error){
            alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
        }
    }); //end of $.ajax({-------------
} //end of function detailSearch(index) ------------------
