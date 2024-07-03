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
                    $('#city').val(city).change();
                    setTimeout(() => {
                        $('#local').val(region);
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
           // searchHpName();
        }
    });
    
    //시-도 부분이 바뀌면 업데이트
    $('#city').on('change', function() {
    	updateSigunGu();
    });
    
    // 시/군/구 부분이 바뀌면 동 업데이트
    $('#local').on('change', function() {	
        updateDong();
    });
    
    
    // 페이지네이션
    //loadHospitalPage(1); //1페이지로 로드
    
   
   
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
function addMarker(position, message,index) {
    let imageSrc = contextPath + '/images/marker' + index + '.png'; // 마커 이미지 경로 설정
    let imageSize = new kakao.maps.Size(24, 35); // 마커 이미지 크기 설정
    let markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);
    
    
    let marker = new kakao.maps.Marker({
        map: map,
        position: position,
         image: markerImage // 마커 이미지 설정
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
    let city_val = $('#city').val(); // 서울특별시, 경기도

    const city = { "city": city_val };
    
    //console.log("area:", area);
    
    $.ajax({
        url: contextPath + "/getlocalinfo.bibo",
        async: false,
        data: city, // 데이터 객체 전달
        dataType: "json",
        success: function(json) {
           // console.log(json);
            let v_html = `<option value="">시/군구 선택</option>`;
            for (let i = 0; i < json.length; i++) {
                if (json[i] != null) {	
                    v_html += `<option value="${json[i]}">${json[i]}</option>`;
                }
            }	// end of for---------
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
            console.log(json);
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
    let city = $('#city').val();
    let local = $('#local').val();
    let country = $('#country').val();
    
    let classcode = $('#classcode').val();
    let agency = $('#agency').val();
	let hpname = $('#searchHpname').val();
	let addr = city + " " + local;

	
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
        	console.log("json:", json);
            removeMarkers();
            $('#hospitalList').empty(); // 기존 병원 리스트 초기화
			let v_html = "";
			if(json.length > 0) {
	           json.forEach((item, index) => { // 병원의 위도, 경도를 위치 객체로 변환
	                let position = new kakao.maps.LatLng(item.wgs84Lat, item.wgs84Lon);
	                //addMarker(position, `${index + 1}. ${item.hpname}`, item.hpname); // 지도에 병원 위치 마커 추가
					
	
					
	                // 병원 리스트로 출력
	                v_html += `<div class="hospital-details">
	                			<input type="hidden" name="${item.hidx}"></input>
			                	<div class="hospital-label">${index + 1}</div>
			                    <h2 class="hospital-name">${item.hpname}</h2>
			                    <p class="hospital-type">${item.classname}</p>
			                    <p class="hospital-contact">TEL: ${item.hptel} </p>
			                    <p class="hospital-address">${item.hpaddr}</p>
			                	<button class="details-button">상세</button>
			               		</div>`;
	            });
             } else {
             	v_html += `검색된 의료기관이 없습니다.`;
             }
             $('#hospitalList').append(v_html);
             const totalPage = Math.ceil(json[0].totalCount / json[0].sizePerPage); 
             console.log("토탈페이지", totalPage); //3 나옴
          	 
          	 displayPagination(totalPage, pageNo);
        },
		    error: function(request, status, error){
		        alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		    }
    });   
}

function displayPagination(totalPage, currentPage) {
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