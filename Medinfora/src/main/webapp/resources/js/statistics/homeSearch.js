
$(function(){
	
	var mapContainer = document.getElementById('map'),
	mapOption = {
	    center: new kakao.maps.LatLng(37.566535, 126.9779692), // 초기 중심 좌표 (서울 시청)
	    level: 3 // 초기 확대 레벨
	};
	var map = new kakao.maps.Map(mapContainer, mapOption);
	
	// HTML5의 geolocation으로 사용자 위치 가져오기
	
	if (navigator.geolocation) {
	    navigator.geolocation.getCurrentPosition(function(position) {
	        var lat = position.coords.latitude, // 위도
	            lon = position.coords.longitude; // 경도
	        
	        console.log(lat, lon); //위도, 경도
	        
	        var locPosition = new kakao.maps.LatLng(lat, lon), // 위치 객체 생성
	            message = '<div style="padding:5px;">현재 위치</div>';
	        
	        // 현재 위치로 지도 중심 이동
	        map.setCenter(locPosition);
	        
	        // 마커 및 인포윈도우 추가
	        var marker = new kakao.maps.Marker({
	            map: map,
	            position: locPosition
	        });
	        var infowindow = new kakao.maps.InfoWindow({
	            content: message,
	            removable: true
	        });
	        infowindow.open(map, marker);
	        
	        // 주변 병원 검색
	        searchHospitals(locPosition);
	    });
	} else {
	    alert('GPS를 지원하지 않습니다');
	}
	});
	
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
	        radius: 2000
	    });
	}
	
	function displayMarker(place) {
	    var marker = new kakao.maps.Marker({
	        map: map,
	        position: new kakao.maps.LatLng(place.y, place.x)
	    });
	
	    kakao.maps.event.addListener(marker, 'click', function() {
	        var infowindow = new kakao.maps.InfoWindow({zIndex:1});
	        infowindow.setContent('<div style="padding:5px;font-size:12px;">' + place.place_name + '</div>');
	        infowindow.open(map, marker);
	    });
	}
