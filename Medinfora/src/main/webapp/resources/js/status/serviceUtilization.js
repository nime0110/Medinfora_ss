// xml 을 json 타입으로 변환
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
}   // end of function xmlToJson(xml) {----------------

// 의료서비스율 데이터 가져오기
function Getdata(){

    $.ajax({
        url : "http://apis.data.go.kr/1352000/ODMS_STAT_12/callStat12Api?serviceKey=t18H0wc1LlracvIWqfFfc8Y4aWsblSv9Bvsntzf26kWqSnakUeTEZG1u27WFuRWVe819MPwqiiJW2wfD7YyPIg%3D%3D",
        method : "get",
        async : true,
        success : function(xml){
            const json = xmlToJson(xml);
            
            const data_arr = {};

            json.response.body.items.item.forEach(element => {
                
                const dvs = element.dvs['#text'];           // 연령대
                const year = element.year['#text'];         // 연도
                const adms = parseFloat(element.adms['#text']);  // 입원률 (숫자로 변환)

                // 연령대 변환
                const ageRange = extractAgeRange(dvs);

                if(ageRange != null){   // 연령대 변환이 가능한 경우
        
                    const yearNum = parseInt(year);

                    if (!data_arr[ageRange]) {
                        data_arr[ageRange] = [`${ageRange}`, null, null, null];
                    }
            
                    // 각 년도의 값으로 업데이트
                    if (yearNum === 2017) {
                        data_arr[ageRange][1] = adms;
                    } else if (yearNum === 2018) {
                        data_arr[ageRange][2] = adms;
                    } else if (yearNum === 2019) {
                        data_arr[ageRange][3] = adms;
                    }

                }   // end of if(ageRange != null){------

            });     // end of json.response.body.items.item.forEach(element => {-------
            let result = Object.values(data_arr);

            createChart(result);

        },
        error : function(errcode){
            console.log("error :"+errcode);
        }
    })
}   // end of function Getdata(){-----------
Getdata();
// 연령대 변환 함수
function extractAgeRange(dvsText) {
    const match = dvsText.match(/연령__(\d+~\d+)세/);
    const match_2 = dvsText.match(/연령__(\d+)/);
    
    if (match) {
        return match[1];    // 15~19
    } else {
        if(match_2){
            return match_2[1];  // 60
        }
        return null;        // 매칭되지 않으면 null 반환
    }
}

///////////////////////////////////////////////////////////////////

function createChart(result){
    var chartDom = document.getElementById('totalChart');
    var myChart = echarts.init(chartDom);
    var option;

    const jsonString = '["product", "2017", "2018", "2019"]';

    const data_arr = [];
    data_arr.push(JSON.parse(jsonString));

    result.forEach(item => {
        data_arr.push(item);
    });

    setTimeout(function () {
    option = {
        title: {
        text: '특정 연도의 연령별 입원 비율 (%)'
        },
        legend: {},
        tooltip: {
        trigger: 'axis',
        showContent: false
        },
        dataset: {
        source: data_arr
        },
        xAxis: { type: 'category' },
        yAxis: { gridIndex: 0 },
        grid: { top: '55%' },
        series: [
        {
            type: 'line',
            smooth: true,
            seriesLayoutBy: 'row',
            emphasis: { focus: 'series' }
        },
        {
            type: 'line',
            smooth: true,
            seriesLayoutBy: 'row',
            emphasis: { focus: 'series' }
        },
        {
            type: 'line',
            smooth: true,
            seriesLayoutBy: 'row',
            emphasis: { focus: 'series' }
        },
        {
            type: 'line',
            smooth: true,
            seriesLayoutBy: 'row',
            emphasis: { focus: 'series' }
        },
        {
            type: 'line',
            smooth: true,
            seriesLayoutBy: 'row',
            emphasis: { focus: 'series' }
        },
        {
            type: 'line',
            smooth: true,
            seriesLayoutBy: 'row',
            emphasis: { focus: 'series' }
        },
        {
            type: 'pie',
            id: 'pie',
            radius: '30%',
            center: ['50%', '25%'],
            emphasis: {
            focus: 'self'
            },
            label: {
            formatter: '{b}: {@2017} ({d}%)'
            },
            encode: {
            itemName: 'product',
            value: '2017',
            tooltip: '2017'
            }
        }
        ]
    };
    myChart.on('updateAxisPointer', function (event) {
        const xAxisInfo = event.axesInfo[0];
        if (xAxisInfo) {
        const dimension = xAxisInfo.value + 1;
        myChart.setOption({
            series: {
            id: 'pie',
            label: {
                formatter: '{b}: {@[' + dimension + ']} ({d}%)'
            },
            encode: {
                value: dimension,
                tooltip: dimension
            }
            }
        });
        }
    });
    myChart.setOption(option);
    });

    option && myChart.setOption(option);
}

ageChart();
function ageChart(){

    let dataByage_fi = [];
    let dataByage_se = [];
    let dataByage_th = [];

    $.ajax({
        url : "getAge.bibo",
        async: true,
        dataType: "json",
        success: function(json) {
            if(json.age != '0'){    // 만나이 15세 이상인 경우

                $(".age").text(json.age.substring(4));
                let dvs = json.age;

                let addr = "https://apis.data.go.kr/1352000/ODMS_STAT_12/callStat12Api?serviceKey=t18H0wc1LlracvIWqfFfc8Y4aWsblSv9Bvsntzf26kWqSnakUeTEZG1u27WFuRWVe819MPwqiiJW2wfD7YyPIg%3D%3D&dvs="+dvs;

                $.ajax({
                    url: addr,
                    success:function(xml){
                        const json = xmlToJson(xml);

                        json.response.body.items.item.forEach(elmt => {

                            const year = elmt.year['#text'];
                            const outpt = elmt.outpt['#text'];
                            const adms = elmt.adms['#text'];
                            const none = elmt.none['#text'];

                            if(year=="2017"){
                                dataByage_fi.push({value:`${outpt}`, name:"외래"});
                                dataByage_fi.push({value:`${adms}`, name:"입원"});
                                dataByage_fi.push({value:`${none}`, name:"전혀 없음"});
                            }
                            else if(year=="2018"){
                                dataByage_se.push({value:`${outpt}`, name:"외래"});
                                dataByage_se.push({value:`${adms}`, name:"입원"});
                                dataByage_se.push({value:`${none}`, name:"전혀 없음"});
                            }
                            else if(year=="2019"){
                                dataByage_th.push({value:`${outpt}`, name:"외래"});
                                dataByage_th.push({value:`${adms}`, name:"입원"});
                                dataByage_th.push({value:`${none}`, name:"전혀 없음"});
                            }
                            
                            // 차트를 표시할 DOM 요소를 가져옴
                            var chartDom_fi = document.getElementById(`first`);
                            var chartDom_se = document.getElementById(`second`);
                            var chartDom_th = document.getElementById(`third`);

                            // ECharts 인스턴스를 초기화
                            var myChart_fi = echarts.init(chartDom_fi);
                            var myChart_se = echarts.init(chartDom_se);
                            var myChart_th = echarts.init(chartDom_th);

                            var option_fi = {
                                tooltip: {
                                    trigger: 'item'
                                  },
                                  legend: {
                                    top: '5%',
                                    left: 'center'
                                  },
                                  series: [
                                    {
                                      name: '2017',
                                      type: 'pie',
                                      radius: ['40%', '70%'],
                                      center: ['50%', '70%'],
                                      startAngle: 180,
                                      endAngle: 360,
                                      data: dataByage_fi
                                    }
                                  ]
                            }

                            var option_se = {
                                tooltip: {
                                    trigger: 'item'
                                  },
                                  legend: {
                                    top: '5%',
                                    left: 'center'
                                  },
                                  series: [
                                    {
                                      name: '2018',
                                      type: 'pie',
                                      radius: ['40%', '70%'],
                                      center: ['50%', '70%'],
                                      startAngle: 180,
                                      endAngle: 360,
                                      data: dataByage_se
                                    }
                                  ]
                            }
                            var option_th = {
                                tooltip: {
                                    trigger: 'item'
                                  },
                                  legend: {
                                    top: '5%',
                                    left: 'center'
                                  },
                                  series: [
                                    {
                                      name: '2019',
                                      type: 'pie',
                                      radius: ['40%', '70%'],
                                      center: ['50%', '70%'],
                                      startAngle: 180,
                                      endAngle: 360,
                                      data: dataByage_th
                                    }
                                  ]
                            }
                            // 차트에 옵션 설정
                            myChart_fi.setOption(option_fi);
                            myChart_se.setOption(option_se);
                            myChart_th.setOption(option_th);

                        })  // end of json.response.body.items.item.forEach(elmt => {-------------------
                        

                        // }
                    },
                    error: function(request) {
                        alert("code : " + request.status);
                    }

                })  // end of $.ajax({-------------------

            }
        },
        error: function(request) {
            alert("code : " + request.status);
        }

    })  // end of $.ajax({------------------

}   // end of function ageChart(){------------------