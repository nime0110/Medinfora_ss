let map;
let markers = [];
const contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2)); // 컨텍스트 패스 

$(function() {
  
    // 지도 컨테이너와 옵션 설정
    var mapContainer = document.getElementById('map'),
        mapOption = {
            center: new kakao.maps.LatLng(37.566535, 126.9779692), // 초기 중심 좌표 (서울 시청)
            level: 3 // 초기 확대 레벨
        };

    // 지도 생성
    map = new kakao.maps.Map(mapContainer, mapOption);

    // 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성함.    
    var mapTypeControl = new kakao.maps.MapTypeControl();
    // 지도 타입 컨트롤을 지도에 표시함.
    map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT); 

    // 지도 확대 축소를 제어할 수 있는 줌 컨트롤을 생성함.   
    var zoomControl = new kakao.maps.ZoomControl();
    // 지도 확대 축소를 제어할 수 있는 줌 컨트롤을 지도에 표시함.
    map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
	


    // HTML5의 geolocation으로 사용자 위치 가져오기
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
            let lat = position.coords.latitude, // 위도
                lon = position.coords.longitude; // 경도
            let locPosition = new kakao.maps.LatLng(lat, lon); // 위치 객체 생성

            // 현재 위치로 지도 중심 이동
            map.setCenter(locPosition);

            // 현재 위치에 마커 추가
            addMarker(locPosition, "현재 위치");
            
            /*
            // Geocoder를 사용하여 주소 변환하는 코드 
    		let geocoder = new kakao.maps.services.Geocoder();
            geocoder.coord2Address(lon, lat, function(result, status) {
                if (status === kakao.maps.services.Status.OK) {
                
                	let address = result[0].address; 
                    console.log("address:", address);// 경기도 고양시 일산동구 일산로 도로명주소 
                	
                    let city = address.region_1depth_name; //시-도 단위 경기
                    let region = address.region_2depth_name; //구단위, 고양시 일산동구 / 백석동->3depth_name
                    
                    console.log("address:", address);
                    console.log("region:", region);
                    console.log("city:", city);
                    
                    // 시/도 및 시/군/구 선택 업데이트
                    $('#si-do').val(city).change();
                    setTimeout(() => {
                        $('#si-gun-gu').val(region);
                    }, 500); // 시/군/구 목록 업데이트 대기 시간
                }
            });
            */
            
        }, function(error) {
            // 위치 정보를 가져오는데 실패한 경우
            alert('위치 정보를 가져올 수 없습니다.');
        });
    } else {
        // Geolocation API를 지원하지 않는 경우
        alert('GPS를 지원하지 않습니다');
    }

	// ****************** 시/도, 진료과목 데이터 가져오기 start ************* 
	// 시/도 데이터 가져오기 ajax
	$.ajax({
		url:contextPath + "/getareainfo.bibo",
		async:false,
		dataType:"json",
		success:function(json){
			let v_html = `<option value="">시/도 선택</option>`;
			for(let i=0; i<json.length; i++){
				v_html +=`<option value="${json[i]}">${json[i]}</option>`;
			}	
			$("select#si-do").html(v_html);
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
           // searchHpName();
        }
    });
    
    //시-도 부분이 바뀌면 업데이트
    $('#si-do').on('change', function() {
    	updateSigunGu();
    });
    
    // 시/군/구 부분이 바뀌면 동 업데이트
    $('#si-gun-gu').on('change', function() {
        updateDong();
    });
    
    /*
    //검색조건변경시 병원검색
    $('#si-gun-gu, #dong, #classcode, #agency').on('change', function() {
        searchHospitals();
    });
    */
    
});

// 지도 확대, 축소 컨트롤에서 확대 버튼을 누르면 호출되어 지도를 확대하는 함수
window.zoomIn = function() {
    map.setLevel(map.getLevel() - 1);
}

// 지도 확대, 축소 컨트롤에서 축소 버튼을 누르면 호출되어 지도를 축소하는 함수
window.zoomOut = function() {
    map.setLevel(map.getLevel() + 1);
}




// 지도에 마커 추가하는 함수
function addMarker(position, message) {
    let marker = new kakao.maps.Marker({
        map: map,
        position: position
    });

    let infowindow = new kakao.maps.InfoWindow({
        content: '<div style="padding:5px;">' + message + '</div>'
    });
    infowindow.open(map, marker);

    // 마커 배열에 추가
    markers.push(marker);
}

// 지도에서 모든 마커를 제거하는 함수
function removeMarkers() {
    for (let i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
    }
    markers = [];
}

// 시 도 선택시 업데이트 되는 함수   start
function updateSigunGu() {
    let area_val = $('#si-do').val(); // 서울특별시, 경기도

    const area = { "area": area_val };
    
    console.log("area:", area);
    
    $.ajax({
        url: contextPath + "/getlocalinfo.bibo",
        async: false,
        data: area, // 데이터 객체 전달
        dataType: "json",
        success: function(json) {
            console.log(json);
            let v_html = `<option value="">시/군구 선택</option>`;
            for (let i = 0; i < json.length; i++) {
                if (json[i] != null) {	
                    v_html += `<option value="${json[i]}">${json[i]}</option>`;
                }
            }	// end of for---------
            $("select#si-gun-gu").html(v_html);
        },
        error: function(request) {
            alert("code : " + request.status + "\nmessage : " + request.responseText);
        }
    });	// end of $.ajax({-------------
}
// 시 도 선택시 업데이트 되는 함수   end




// 시/군/구를 기반으로 병원 검색하면 리스트가 보이는 함수!! 
function searchHospitals() {
    let sido = $('#si-do').val();
    let sigungu = $('#si-gun-gu').val();
    let classcode = $('#classcode').val();
    let agency = $('#agency').val();

	let addr = sido + sigungu;
	console.log("sido:", sido);
	console.log("si-gun-gu:", sigungu);
	console.log("~~addr:", addr);
	console.log("classcode:", classcode);
	console.log("agency:", agency);
	
	
    if (!sido ) {
        alert("시/도를 선택하세요");
        return;
    }
	
    if (!sigungu ) {
        alert("시/군/구를 선택하세요");
        return;
    }
	
    $.ajax({
        url: contextPath +'/hpsearch/hpsearchAdd.bibo', 
        data: { sido: sido,
        		sigungu: sigungu, 
        		classcode: classcode, 
        		agency: agency },
        dataType: "json",
        success: function(json) {
            removeMarkers();
            $('#hospitalList').empty(); // 기존 병원 리스트 초기화
            data.forEach(hospital => { // 병원의 위도, 경도를 위치 객체로 변환
                let position = new kakao.maps.LatLng(hospital.wgs84Lat, hospital.wgs84Lon);
                addMarker(position, hospital.hpname); // 지도에 병원 위치 마커 추가
				

                // 병원 리스트로 출력
                let v_html = `<li>${hospital.hpname}</li>`;
                $('#hospitalList').append(v_html);
            });
        }
    });
    
}

