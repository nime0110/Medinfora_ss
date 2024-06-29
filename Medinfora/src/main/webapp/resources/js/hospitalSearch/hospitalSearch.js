let map;
let markers = [];

$(function() {
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

    
});

// 주변 병원 검색 함수
function searchHospitals(locPosition) {
    let places = new kakao.maps.services.Places();
    places.categorySearch('HP8', function(result, status) {
        if (status === kakao.maps.services.Status.OK) {
            for (let i = 0; i < result.length; i++) {
                displayMarker(result[i]);
            }
        }
    }, {
        location: locPosition,
        radius: 2000 // 검색 반경 설정 (2km)
    });
}

// 검색된 병원을 지도에 마커로 표시하는 함수
function displayMarker(place) {
    let marker = new kakao.maps.Marker({
        map: map,
        position: new kakao.maps.LatLng(place.y, place.x)
    });

    // 마커 클릭 이벤트
    kakao.maps.event.addListener(marker, 'click', function() {
        let infowindow = new kakao.maps.InfoWindow({zIndex:1});
        infowindow.setContent('<div style="padding:5px;font-size:12px;">' + place.place_name + '</div>');
        infowindow.open(map, marker);
    });

    // 마커 배열에 추가
    markers.push(marker);
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

// 수동 위치 입력을 통해 검색하는 함수 start
function searchManualLocation() {
    let address = $('#manualLocation').val();
    let geocoder = new kakao.maps.services.Geocoder();

    // 주소로 위치 검색
    geocoder.addressSearch(address, function(result, status) {
        if (status === kakao.maps.services.Status.OK) {
            let locPosition = new kakao.maps.LatLng(result[0].y, result[0].x); // 검색된 위치 객체 생성
            map.setCenter(locPosition); // 검색된 위치로 지도 중심 이동
            removeMarkers(); // 기존 마커 제거
            addMarker(locPosition, "검색 위치"); // 검색된 위치에 마커 추가

            // 주변 병원 검색
            searchHospitals(locPosition);
        } else {
            // 주소 검색 실패한 경우
            alert('주소를 찾을 수 없습니다');
        }
    });
}
// 수동 위치 입력을 통해 검색하는 함수 end 

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