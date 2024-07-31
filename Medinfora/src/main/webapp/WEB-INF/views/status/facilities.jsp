<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% String ctxPath = request.getContextPath(); %>

<script src="https://cdnjs.cloudflare.com/ajax/libs/echarts/5.5.0/echarts.min.js"></script>

<!-- 시도별 일반 입원실 시설 현황  -->
<script type="text/javascript" src="<%= ctxPath%>/resources/js/status/facilities.js"></script>

<script type="text/javascript">
document.addEventListener("DOMContentLoaded", function () {
    // 사용자 로그인 여부 확인
	 var isLoggedIn = "${sessionScope.loginuser != null}";
	    if (isLoggedIn) {
	        var userAddress = "${sessionScope.loginuser.address}";
	        // 사용자 주소에서 '경기'와 같은 특정 부분 추출
	        var region = extractRegion(userAddress);
	        // 추출된 지역을 기반으로 데이터 가져오기
	        var data = getChartData(region);
	        if (data) {
	            var userChartDom = document.getElementById("main2");
	            var userChart = echarts.init(userChartDom);

	            var userOption = {
	                legend: {
	                    top: 'bottom'
	                },
	                toolbox: {
	                    show: true,
	                    feature: {
	                        mark: { show: true },
	                        dataView: { show: true, readOnly: false },
	                        restore: { show: true },
	                        saveAsImage: { show: true }
	                    }
	                },
	                series: [
	                    {
	                        name: region,
	                        type: 'pie',
	                        radius: [50, 250],
	                        center: ['50%', '50%'],
	                        roseType: 'area',
	                        itemStyle: {
	                            borderRadius: 8
	                        },
	                        data: data
	                    }
	                ]
	            };

	            userChart.setOption(userOption);
	        } else {
	            console.log("해당 회원은 주소가 없습니다!");
	        }
	    }
	});

	// 사용자 주소에서 특정 지역 추출
	function extractRegion(address) {
	    // 주소에서 첫 번째 부분(시/도를 나타내는 부분)을 추출
	    var region = address.split(" ")[0];
	    return region;
	}

	// 지역별 차트 데이터
	function getChartData(region) {
	    var chartData = {
	        '서울': [
	            { value: 25, name: '보건소(보건의료원)' },
	            { value: 33, name: '보건지소' },
	            { value: 1, name: '건강생활지원센터' },
	            { value: 0, name: '보건진료소소' }
	        ],
	        '부산': [
	            { value: 16, name: '보건소(보건의료원)' },
	            { value: 10, name: '보건지소' },
	            { value: 13, name: '건강생활지원센터' },
	            { value: 5, name: '보건진료소' }
	        ],
	        '대구': [
	            { value: 9, name: '보건소(보건의료원)' },
	            { value: 16, name: '보건지소' },
	            { value: 2, name: '건강생활지원센터' },
	            { value: 18, name: '보건진료소' }
	        ],
	        '인천': [
	            { value: 10, name: '보건소(보건의료원)' },
	            { value: 26, name: '보건지소' },
	            { value: 8, name: '건강생활지원센터' },
	            { value: 25, name: '보건진료소' }
	        ],
	        '광주': [
	            { value: 5, name: '보건소(보건의료원)' },
	            { value: 0, name: '보건지소' },
	            { value: 9, name: '건강생활지원센터' },
	            { value: 10, name: '보건진료소' }
	        ],
	        '대전': [
	            { value: 5, name: '보건소(보건의료원)' },
	            { value: 6, name: '보건지소' },
	            { value: 1, name: '건강생활지원센터' },
	            { value: 8, name: '보건진료소' }
	        ],
	        '울산': [
	            { value: 5, name: '보건소(보건의료원)' },
	            { value: 8, name: '보건지소' },
	            { value: 1, name: '건강생활지원센터' },
	            { value: 11, name: '보건진료소' }
	        ],
	        '세종': [
	            { value: 1, name: '보건소(보건의료원)' },
	            { value: 10, name: '보건지소' },
	            { value: 1, name: '건강생활지원센터' },
	            { value: 7, name: '보건진료소' }
	        ],
	        '경기': [
	            { value: 48, name: '보건소(보건의료원)' },
	            { value: 118, name: '보건지소' },
	            { value: 19, name: '건강생활지원센터' },
	            { value: 161, name: '보건진료소' }
	        ],
	        '강원': [
	            { value: 18, name: '보건소(보건의료원)' },
	            { value: 100, name: '보건지소' },
	            { value: 9, name: '건강생활지원센터' },
	            { value: 129, name: '보건진료소' }
	        ],
	        '충북': [
	            { value: 14, name: '보건소(보건의료원)' },
	            { value: 95, name: '보건지소' },
	            { value: 4, name: '건강생활지원센터' },
	            { value: 159, name: '보건진료소' }
	        ],
	        '충남': [
	            { value: 16, name: '보건소(보건의료원)' },
	            { value: 151, name: '보건지소' },
	            { value: 7, name: '건강생활지원센터' },
	            { value: 236, name: '보건진료소' }
	        ],
	        '전북': [
	            { value: 15, name: '보건소(보건의료원)' },
	            { value: 150, name: '보건지소' },
	            { value: 4, name: '건강생활지원센터' },
	            { value: 240, name: '보건진료소' }
	        ],
	        '전남': [
	            { value: 22, name: '보건소(보건의료원)' },
	            { value: 217, name: '보건지소' },
	            { value: 7, name: '건강생활지원센터' },
	            { value: 327, name: '보건진료소' }
	        ],
	        '경북': [
	            { value: 24, name: '보건소(보건의료원)' },
	            { value: 216, name: '보건지소' },
	            { value: 1, name: '건강생활지원센터' },
	            { value: 298, name: '보건진료소' }
	        ],
	        '경남': [
	            { value: 20, name: '보건소(보건의료원)' },
	            { value: 174, name: '보건지소' },
	            { value: 7, name: '건강생활지원센터' },
	            { value: 220, name: '보건진료소' }
	        ],
	        '제주': [
	            { value: 6, name: '보건소(보건의료원)' },
	            { value: 11, name: '보건지소' },
	            { value: 2, name: '건강생활지원센터' },
	            { value: 48, name: '보건진료소' }
	        ]
	    };

	    return chartData[region] || null;
	}

</script>

<c:if test="${sessionScope.loginuser != null}">
    <div class="loginuserchart mb-3">
        <span class="nanum-b name">&nbsp;&nbsp;${sessionScope.loginuser.name}&nbsp;님 지역보건의료기관 현황</span>
        <div class="nanum-n my-2" id="main2" style="width: 100%; height: 600px;"></div>    
    ${sessionScope.loginuser.address}
    </div>
    <hr>
</c:if>


<div style="text-align:center;">
    <span class="nanum-b size-n name mt-5">
        시도별 일반 입원실 시설 현황
    </span>
</div>
<div id="main" style="width: 100%; height: 600px;"></div>
