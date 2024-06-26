
// 현재 spring tool suite 3에서 사용이 불가능한 문법이라 vs코드로 실행해야 합니다

function xmlToJson(xml) {
   
    var obj = {};

    if (xml.nodeType == 1) {
        if (xml.attributes.length > 0) {
        obj["@attributes"] = {};
            for (var j = 0; j < xml.attributes.length; j++) {
                var attribute = xml.attributes.item(j);
                obj["@attributes"][attribute.nodeName] = attribute.nodeValue;
            }
        }
    } else if (xml.nodeType == 3) {
        obj = xml.nodeValue;
    }

    if (xml.hasChildNodes()) {
        for(var i = 0; i < xml.childNodes.length; i++) {
            var item = xml.childNodes.item(i);
            var nodeName = item.nodeName;
            if (typeof(obj[nodeName]) == "undefined") {
                obj[nodeName] = xmlToJson(item);
            } else {
                if (typeof(obj[nodeName].push) == "undefined") {
                    var old = obj[nodeName];
                    obj[nodeName] = [];
                    obj[nodeName].push(old);
                }
                obj[nodeName].push(xmlToJson(item));
            }
        }
    }

    return obj;
    
}

function Getmt(){ //CODE_MST

    const data = {
        "serviceKey" : "FzzJYoZ92N9TAoOwp7jsOueaLGcTXz/qLiRly5OoXEqvsqSKzGR5aYqGm9CxasSqjlZmwMulWX/0oxlOv4ZWow=="
    }

    $.ajax({
        url : "http://apis.data.go.kr/B552657/CodeMast/info",
        method : "get",
        data : data,
        async : false,
        success : function(xml){
            const json = xmlToJson(xml);
            json.response.body.items.item.forEach(element => {
                console.log(element);
            });
        },
        error : function(errcode){
            console.log("ERR :"+errcode);
        }

    })

}

function saveJsonToFile(jsonData, filename) { //json데이터, 파일이름
    const dataStr = JSON.stringify(jsonData,  null, 2); // 2 -> 들여쓰기  
    const blob = new Blob([dataStr], { type: 'application/json' }); //파일객체생성
    const url = URL.createObjectURL(blob); //객체 url
    /* 앵커태그 생성 */
    const a = document.createElement('a');
    a.href = url; 
    a.download = filename; //파일이름
    document.body.appendChild(a); //본문추가
    a.click(); //이벤트 트리거
    document.body.removeChild(a); //다운로드한다음지움
    URL.revokeObjectURL(url); //url 해제
}

function GetApi() {
    const data = {
        "serviceKey": "FzzJYoZ92N9TAoOwp7jsOueaLGcTXz/qLiRly5OoXEqvsqSKzGR5aYqGm9CxasSqjlZmwMulWX/0oxlOv4ZWow==",
        "QD": "D002",
        "QZ": "A",
        "numOfRows": "309",
        "pageNo":"1"
    };

    $.ajax({
        url: "http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire",
        method: "get",
        data: data,
        async: false,
        success: function (xml) {
            const json = xmlToJson(xml);
            if (json.response?.body?.items?.item) { //옵셔널 체이닝=> 값이 비어있을 경우가 있어서 필요(참조나 기능이 undefined 또는 null 일 수 있을 때)
                const filteredData = json.response.body.items.item.map(element => ({
                    hpname: element.dutyName?.["#text"] || '', // 병원이름
                    hpaddr: element.dutyAddr?.["#text"] || '', // 병원주소
                    hptel: element.dutyTel1?.["#text"] || '', // 병원연락처 
                    classcode: "D002", //진료과목코드
                    agency: "종합병원", 
                    wgs84Lon: element.wgs84Lon?.["#text"] || '', // 경도
                    wgs84Lat: element.wgs84Lat?.["#text"] || '', // 위도

                    // --- [오픈시간] ---
                    starttime1: element.dutyTime1s?.["#text"] || '', // 월요일
                    starttime2: element.dutyTime2s?.["#text"] || '', // 화요일
                    starttime3: element.dutyTime3s?.["#text"] || '', // 수요일
                    starttime4: element.dutyTime4s?.["#text"] || '', // 목요일
                    starttime5: element.dutyTime5s?.["#text"] || '', // 금요일
                    starttime6: element.dutyTime6s?.["#text"] || '', // 토요일
                    starttime7: element.dutyTime7s?.["#text"] || '', // 일요일
                    starttime8: element.dutyTime8s?.["#text"] || '', // 공휴일
                    
                    // --- [마감시간] ---
                    endtime1: element.dutyTime1c?.["#text"] || '', // 월요일
                    endtime2: element.dutyTime2c?.["#text"] || '', // 화요일
                    endtime3: element.dutyTime3c?.["#text"] || '', // 수요일
                    endtime4: element.dutyTime4c?.["#text"] || '', // 목요일
                    endtime5: element.dutyTime5c?.["#text"] || '', // 금요일
                    endtime6: element.dutyTime6c?.["#text"] || '', // 토요일
                    endtime7: element.dutyTime7c?.["#text"] || '', // 일요일
                    endtime8: element.dutyTime8c?.["#text"] || ''  // 공휴일

                }));
                saveJsonToFile(filteredData, 'api_hospital_search_D002A.json'); // 파일 이름 설정
            } else {
                console.error('Invalid JSON structure', json);
            }
        
            /*json.response.body.items.item.forEach(element => {
                //console.log(element.dutyDiv, element.dutyDivNam);
            });*/
            //saveJsonToFile(json, 'api_hospital_search_A.json'); 파일이름 설정
            //saveJsonToFile(json.response.body.items.item, 'api_hospital_search_B.json'); //파일이름 설정
        },
        error: function (errcode) {
            console.log("ERR :" + errcode);
        }
    });
}

window.onload = () => {
    GetApi();
}




