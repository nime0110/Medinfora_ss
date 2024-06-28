var map;
var markers = [];

$(function() {
    // 지도 컨테이너와 옵션 설정
    var mapContainer = document.getElementById('map'),
        mapOption = {
            center: new kakao.maps.LatLng(37.566535, 126.9779692), // 초기 중심 좌표 (서울 시청)
            level: 3 // 초기 확대 레벨
        };

    // 지도 생성
    map = new kakao.maps.Map(mapContainer, mapOption);

    // HTML5의 geolocation으로 사용자 위치 가져오기
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
            var lat = position.coords.latitude, // 위도
                lon = position.coords.longitude; // 경도
            var locPosition = new kakao.maps.LatLng(lat, lon); // 위치 객체 생성

            // 현재 위치로 지도 중심 이동
            map.setCenter(locPosition);

            // 현재 위치에 마커 추가
            addMarker(locPosition, "현재 위치");

            // 주변 병원 검색
            searchHospitals(locPosition);
        }, function(error) {
            // 위치 정보를 가져오는데 실패한 경우
            alert('위치 정보를 가져올 수 없습니다.');
        });
    } else {
        // Geolocation API를 지원하지 않는 경우
        alert('GPS를 지원하지 않습니다');
    }

    // Enter 키로 수동 위치 검색
    $('#manualLocation').on('keydown', function(e) {
        if (e.keyCode == 13) {
            searchManualLocation();
        }
    });
});

// 주변 병원 검색 함수
function searchHospitals(locPosition) {
    var places = new kakao.maps.services.Places();
    places.categorySearch('HP8', function(result, status) {
        if (status === kakao.maps.services.Status.OK) {
            for (var i = 0; i < result.length; i++) {
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
    var marker = new kakao.maps.Marker({
        map: map,
        position: new kakao.maps.LatLng(place.y, place.x)
    });

    // 마커 클릭 이벤트
    kakao.maps.event.addListener(marker, 'click', function() {
        var infowindow = new kakao.maps.InfoWindow({zIndex:1});
        infowindow.setContent('<div style="padding:5px;font-size:12px;">' + place.place_name + '</div>');
        infowindow.open(map, marker);
    });

    // 마커 배열에 추가
    markers.push(marker);
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

// 지도에서 모든 마커를 제거하는 함수
function removeMarkers() {
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
    }
    markers = [];
}

// 수동 위치 입력을 통해 검색하는 함수
function searchManualLocation() {
    var address = $('#manualLocation').val();
    var geocoder = new kakao.maps.services.Geocoder();

    // 주소로 위치 검색
    geocoder.addressSearch(address, function(result, status) {
        if (status === kakao.maps.services.Status.OK) {
            var locPosition = new kakao.maps.LatLng(result[0].y, result[0].x); // 검색된 위치 객체 생성
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