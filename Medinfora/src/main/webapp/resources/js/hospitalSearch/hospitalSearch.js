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

// 폴리곤 관련 변수
let detailMode = false; 
let level = '';
let polygons = [];
let polygonOverlays = [];


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

    // 지도 컨테이너와 옵션 설정
    let mapContainer = document.getElementById('map'),
        mapOption = {
            center: new kakao.maps.LatLng(37.566535, 126.9779692), // 초기 중심 좌표 (서울 시청)
            level: 12 // 초기 확대 레벨
        };

    // 지도 생성 
    map = new kakao.maps.Map(mapContainer, mapOption),
    polyOverlay = new kakao.maps.CustomOverlay({});
   
    // 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤 생성함.    
    let mapTypeControl = new kakao.maps.MapTypeControl();
    map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT); 

    let zoomControl = new kakao.maps.ZoomControl();
    map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
	
    
    kakao.maps.event.addListener(map, 'zoom_changed', function () {
        level = map.getLevel();

        console.log("level:", level);
        if (!detailMode && level <= 10) {
            detailMode = true;
            removePolygon();
            init(contextPath + "/resources/json/sig.json");
        } else if (detailMode && level > 10) {
            detailMode = false;
            removePolygon();
            init(contextPath + "/resources/json/sido.json");
        } else if (level <= 6) {
            removePolygon();
        } else if (level > 6 && level <= 9) {
            init(contextPath + "/resources/json/sig.json");
        }
    });    
    //폴리곤 생성
    init( contextPath + "/resources/json/sido.json") 
    
    clusterer = new kakao.maps.MarkerClusterer({
        map: map, 
        averageCenter: true, 
        minLevel: 5, 
    });

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

window.zoomIn = function() {
    map.setLevel(map.getLevel() - 1);
}

window.zoomOut = function() {
    map.setLevel(map.getLevel() + 1);
}

// 시 도 선택시 업데이트 되는 함수  start
function updateSigunGu() {
    let city_val = $('#city').val();
    const city = { "city": city_val };
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
// 시 도 선택시 업데이트 되는 함수  end

// 시군구 선택시 동이 업데이트 되는 함수  start
function updateDong() {
    let city_val = $('#city').val();
    let local_val = $('#local').val(); 
    const cityLocal = { "city": city_val, "local": local_val };
    $.ajax({
        url: contextPath + "/getcountryinfo.bibo",
        async: false,
        data: cityLocal, 
        dataType: "json",
        success: function(json) {
            let v_html = `<option value="">읍/면/동 선택</option>`;
            for (let i = 0; i < json.length; i++) {
                if (json[i] != null) {	
                    v_html += `<option value="${json[i]}">${json[i]}</option>`;
                }
            }	// end of for---------
            $("select#country").html(v_html);
            
            $("select#country").off('change').on('change', function() {
                searchHospitals(1);
            });
        },
        error: function(request) {
            alert("code : " + request.status + "\nmessage : " + request.responseText);
        }
    });	// end of $.ajax({-------------  
}
// 시 도 선택시 업데이트 되는 함수   end

// 시/군/구 값을 기반으로 시/도를 설정하는 함수
function updateCityFromLocal(local) {
    $.ajax({
        url: contextPath + "/putSiGetdo.bibo",
        async: false,
        data: { "local": local },
        dataType: "json",
        success: function(json) {
            if(json.length > 1) {
                alert("시/도를 선택하세요.");
                let v_html = `<option value="">시/도 선택</option>`;
                for (let i = 0; i < json.length; i++) {
                    v_html += `<option value="${json[i].sido}">${json[i].sido}</option>`;
                }	
                $("select#city").html(v_html);
                
                $('#city').on('change', function() {
                    $('#local').val(local);
                    searchHospitals(1);
                });

            }  else {
                json.forEach(item => {  
                    $('#city').val(item.sido);
                    updateSigunGu();
                    $('#local').val(local);
                    updateDong();
                    searchHospitals(1);
                });
            }
        },
        error: function(request) {
            alert("code : " + request.status + "\nmessage : " + request.responseText);
        }
    });
}


var currentPage = 1; // 현재 페이지를 추적

// 시/군/구를 기반으로 병원 검색하면 리스트가 보이는 함수
function searchHospitals(pageNo) {
    clearAllwithmarker(); 
    clearClusterer(); 
    let city = $('#city').val();
    let local = $('#local').val();
    let country = $('#country').val();
    let classcode = $('#classcode').val();
    let agency = $('#agency').val();
	let hpname = $('#searchHpname').val();
	let addr = city + " " + local;

    console.log("country:", country);
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
	           		var position = {};
	                position.latlng = new kakao.maps.LatLng(item.wgs84lat, item.wgs84lon);
	                position.content = `<div class='mycontent' data-index="${index}">
									    	<div class="title"> ${item.hpname} </div>
									    	<div class="content"> 
									    		<div class="info">
									    			<strong>${item.classname}</strong>
									    		</div>
									    		<p class="tel">
									    			<span>${item.hptel}</span>
									    		</p>
									    		<p class="addr">								    		
										    		${item.hpaddr} 
									    		</p>
                                                <button class="details-button"  onclick="detailSearch(${index})">상세보기</button>
									    	</div>		    	 
                    				    </div>`;
                    position.hpname = item.hpname;
                    positionArr.push(position);	
                    const alphabetIndex = String.fromCharCode(65 + index); 
                    v_html += `<div class="hospital-details" data-index="${index}">
                                <input type="hidden" name="${item.hidx}"></input>
                                <div class="hospital-label nanum-n">${alphabetIndex}</div>
                                <h2 class="hospital-name">${item.hpname}</h2>
                                <p class="hospital-type nanum-n">${item.classname}</p>
                                <p class="hospital-contact nanum-n">TEL: ${item.hptel} </p>
                                <p class="hospital-address nanum-n">${item.hpaddr}</p>
                                <p class="status">${item.status}</p>
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
                    var infowindow = new kakao.maps.InfoWindow({
                        content: positionArr[i].content, 
                        removable: true
                    });

                    infowindows.push(infowindow);
                    
                    kakao.maps.event.addListener(marker, 'click', function() { 
                        var level = map.getLevel() - 2;
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
                    
                    var index = $(this).data('index');
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
                    var index = $(this).data('index');
                    map.setCenter(positionArr[index].latlng);
                    kakao.maps.event.trigger(markers[index], 'click');
                });
     
            } else {
                v_html += `<div id="no_searchList">
		        		<span>😥</span>
		            	<p>검색된 의료기관이 없습니다.</p>
		        	</div>`;
            } // end of if(json.length > 0) -------------------------------
            
            $('#hospitalList').append(v_html);
            const totalPage = Math.ceil(json[0].totalCount / json[0].sizePerPage); 
            
            displayPagination(totalPage, pageNo);
        }, //end of  success: function(json)  ------------------
        error: function(request, status, error){
            alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
        }
    });   
}

function displayPagination(totalPage, currentPage) {
    clearAllwithmarker(); 
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

// 모든 폴리곤을 지우는 함수
function removePolygon() { 
    for (let i = 0; i < polygons.length; i++) {
        polygons[i].setMap(null);
    }
    for (let i = 0; i < polygonOverlays.length; i++) {
        polygonOverlays[i].setMap(null);
    }
    polygons = [];
    polygonOverlays = [];
}

// 폴리곤 생성
function init(path) {
    $.getJSON(path, function (geojson) { 
        let units = geojson.features;
        areas = [];
        $.each(units, function (index, unit) { 
            let coordinates = []; 
            let name = ''; 
            let cd_location = '';
            coordinates = unit.geometry.coordinates; // 1개 지역의 영역을 구성하는 다각형의 모든 좌표 배열
            name = unit.properties.SIG_KOR_NM;
            cd_location = unit.properties.SIG_CD; 

            let ob = new Object();
            ob.name = name; 
            ob.path = []; 
            ob.location = cd_location;
            ob.parent = unit.properties.SIG_KOR_NM || "";

            $.each(coordinates[0], function (index, coordinate) { 
                ob.path.push(new kakao.maps.LatLng(coordinate[1], coordinate[0]));
            });
            areas[index] = ob;
        });
        // 지도에 영역데이터를 폴리곤으로 표시
        for (var i = 0, len = areas.length; i < len; i++) {
            displayArea(areas[i]);
        }
    }); //getJSON
}   //init

// 폴리곤 보여지기
function displayArea(area) {
    var polygon = new kakao.maps.Polygon({
        map: map,
        path: area.path,
        strokeWeight: 2,
        strokeColor: '#004c80',
        strokeOpacity: 0.8,
        fillColor: '#fff',
        fillOpacity: 0.7
    });
    polygons.push(polygon);

    // 폴리곤 중심 좌표
    let center = centroid(area.path);

    let polygonOverlay = new kakao.maps.CustomOverlay({
        position: center,
        content: `<div class="label nanum-b size-s" style="background-color: white; border: 1px solid black; border-radius: 3px; font-size:0.8rem;">${area.name}</div>`,
        yAnchor: 0.5
    });
    polygonOverlay.setMap(map);
    polygonOverlays.push(polygonOverlay); 

    kakao.maps.event.addListener(polygon, 'mouseover', function () {
        polygon.setOptions({ fillColor: '#09f' });
    });

    kakao.maps.event.addListener(polygon, 'mouseout', function () {
        polygon.setOptions({ fillColor: '#fff' });
    });

    kakao.maps.event.addListener(polygon, 'click', function () {

        let optionValues = new Array;
        let localExist = false;
        let level = map.getLevel(); 

        if (map.getLevel() > 10) {
            $('#city').val(area.name); 
            updateSigunGu();
            level = 8;
        } else if (map.getLevel() <= 10) {
            let local = area.name;
            $('#local option').each(function() {
                optionValues.push($(this).val());
            });
            optionValues.forEach(function(value) {
                if (local == value) { 
                    localExist = true;
                }
            });
            if (localExist) {
                $('#local').val(local);
                searchHospitals(1);
            } else { 
                updateCityFromLocal(local);
            }
            level = 6;
        }



        map.setLevel(level, {
            anchor: centroid(area.path),
            animate: { duration: 350 }
        });
        removePolygon();
    });
}

// 폴리곤 중심 좌표 계산 함수
function centroid(path) {
    var x = 0, y = 0, area = 0;

    for (var i = 0, len = path.length, j = len - 1; i < len; j = i++) {
        var p1 = path[i];
        var p2 = path[j];
        var f = p1.getLng() * p2.getLat() - p2.getLng() * p1.getLat();
        x += (p1.getLat() + p2.getLat()) * f;
        y += (p1.getLng() + p2.getLng()) * f;
        area += f * 3;
    }
    return new kakao.maps.LatLng(x / area, y / area);
}
 

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
