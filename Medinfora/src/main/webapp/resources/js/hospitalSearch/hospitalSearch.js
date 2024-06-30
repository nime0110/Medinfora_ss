let map;
let markers = [];

$(function() {
    const contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2)); // 컨텍스트 패스 
 
    // 지도 컨테이너와 옵션 설정
    let mapContainer = document.getElementById('map'),
        mapOption = {
            center: new kakao.maps.LatLng(37.566535, 126.9779692), // 초기 중심 좌표 (서울 시청)
            level: 3 // 초기 확대 레벨
        };

    // 지도 생성
    map = new kakao.maps.Map(mapContainer, mapOption);

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

    // 검색부분 작성
    $('#manualLocation').on('keydown', function(e) {
        if (e.keyCode == 13) {
            searchManualLocation();
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
    
    //검색조건변경시 병원검색
    $('#si-gun-gu, #dong, #classcode, #agency').on('change', function() {
        searchHospitals();
    });


    
});

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
	let selectedSido =  $('#si-do').val(); //서울특별시 , 경기도
	let sigunGuList = { //추후 DB 내용대로
        '서울특별시': ['강남구', '서초구', '송파구'],
        '경기도': ['수원시', '성남시', '용인시']
    };
    
    $('#si-gun-gu').empty();
    $('#si-gun-gu').append('<option value="">시/군/구 선택</option>');

    if (selectedSido) {
        let guList = sigunGuList[selectedSido]; //'서울특별시' 선택 key => value 강남구,서초구,송파구
        //console.log(guList); 
        for (let i = 0; i < guList.length; i++) { 
            $('#si-gun-gu').append('<option value="' + guList[i] + '">' + guList[i] + '</option>');
        }
    }
}
// 시 도 선택시 업데이트 되는 함수   end

// 시/군/구 선택 시 동(읍/면/동) 목록 업데이트 함수 start
function updateDong() {
    let selectedSigungu = $('#si-gun-gu').val(); // 시/군/구 선택
    let dongList = { // 동 목록 (예시)
        '강남구': ['삼성동', '역삼동', '논현동'],
        '서초구': ['서초동', '반포동', '잠원동'],
        '송파구': ['잠실동', '문정동', '가락동'],
        '수원시': ['팔달구', '권선구', '영통구'],
        '성남시': ['분당구', '수정구', '중원구'],
        '용인시': ['수지구', '기흥구', '처인구']
    };

    $('#dong').empty();
    $('#dong').append('<option value="">동 선택</option>');

    if (selectedSigungu && dongList[selectedSigungu]) {
        let selectedDongList = dongList[selectedSigungu];
        for (let i = 0; i < selectedDongList.length; i++) {
            $('#dong').append('<option value="' + selectedDongList[i] + '">' + selectedDongList[i] + '</option>');
        }
    }
}
// 시/군/구 선택 시 동(읍/면/동) 목록 업데이트 함수  end

// 시/군/구를 기반으로 병원 검색 함수 
function searchHospitals() {
    let region = $('#si-gun-gu').val();
    let dong = $('#dong').val();
    let classcode = $('#classcode').val();
    let agency = $('#agency').val();

    if (!region || !dong) {
        return;
    }

    $.ajax({
        url: '/hospitalSearch/hpsearchAdd.bibo', 
        data: { region: region, 
        		classcode: classcode, 
        		agency: agency },
        success: function(data) {
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

