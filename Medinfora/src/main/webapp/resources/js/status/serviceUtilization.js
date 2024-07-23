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

// document.addEventListener("DOMContentLoaded", () => {
//     const datag =  Getdata();
//     console.log(datag);
//     var chartDom = document.getElementById('totalChart');
//     var myChart = echarts.init(chartDom);
//     var option;

//     setTimeout(function () {
//     option = {
//         title: {
//         text: '특정 연도의 연령별 입원 비율 (%)'
//         },
//         legend: {},
//         tooltip: {
//         trigger: 'axis',
//         showContent: false
//         },
//         dataset: {
//         source: [
//             ['product', '2017', '2018', '2019'],
//             ['15-19', 2.7, 0.7, 0.5],
//             ['20-29', 2.6, 0.6, 1.4],
//             ['30-39', 3.3, 1.9, 2.6],
//             ['40-49', 5, 3, 2.8],
//             ['50-59', 5.1, 4.4, 4.2],
//             ['60', 11, 7.5, 7.8]
//         ]
//         },
//         xAxis: { type: 'category' },
//         yAxis: { gridIndex: 0 },
//         grid: { top: '55%' },
//         series: [
//         {
//             type: 'line',
//             smooth: true,
//             seriesLayoutBy: 'row',
//             emphasis: { focus: 'series' }
//         },
//         {
//             type: 'line',
//             smooth: true,
//             seriesLayoutBy: 'row',
//             emphasis: { focus: 'series' }
//         },
//         {
//             type: 'line',
//             smooth: true,
//             seriesLayoutBy: 'row',
//             emphasis: { focus: 'series' }
//         },
//         {
//             type: 'line',
//             smooth: true,
//             seriesLayoutBy: 'row',
//             emphasis: { focus: 'series' }
//         },
//         {
//             type: 'line',
//             smooth: true,
//             seriesLayoutBy: 'row',
//             emphasis: { focus: 'series' }
//         },
//         {
//             type: 'line',
//             smooth: true,
//             seriesLayoutBy: 'row',
//             emphasis: { focus: 'series' }
//         },
//         {
//             type: 'pie',
//             id: 'pie',
//             radius: '30%',
//             center: ['50%', '25%'],
//             emphasis: {
//             focus: 'self'
//             },
//             label: {
//             formatter: '{b}: {@2017} ({d}%)'
//             },
//             encode: {
//             itemName: 'product',
//             value: '2017',
//             tooltip: '2017'
//             }
//         }
//         ]
//     };
//     myChart.on('updateAxisPointer', function (event) {
//         const xAxisInfo = event.axesInfo[0];
//         if (xAxisInfo) {
//         const dimension = xAxisInfo.value + 1;
//         myChart.setOption({
//             series: {
//             id: 'pie',
//             label: {
//                 formatter: '{b}: {@[' + dimension + ']} ({d}%)'
//             },
//             encode: {
//                 value: dimension,
//                 tooltip: dimension
//             }
//             }
//         });
//         }
//     });
//     myChart.setOption(option);
//     });

//     option && myChart.setOption(option);
// });     // end of document.addEventListener("DOMContentLoaded", () => {---------