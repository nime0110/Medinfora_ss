const ctxPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));

$(function(){

    let setoption = getStatus();

    let opdata = getdata(setoption);

    if(setoption.t=="t0"){
        chartloadT0(opdata);
    }else{
        chartloadT1(opdata);
    }

    $('.selectbox').on("change",function(){

        echarts.init(document.getElementById('chartin'), null, {
            renderer: 'canvas',
            useDirtyRect: false,
            xAxis: [{
                show: false
            }]
        });
        setoption = getStatus();
        opdata = getdata(setoption);
        if(setoption.t=="t0"){
            chartloadT0(opdata);
        }else{
            chartloadT1(opdata);
        }

    });

});

function getStatus(){

    const opt = $('select[name="opt"]').val();
    const opu = $('select[name="opu"]').val();
    const opr = $('select[name="opr"]').val();

    return {t:opt,u:opu,r:opr};

}

function getdata(setoption){

    let result;

    $.ajax({
        url : ctxPath+"/mypage/getlogdata.bibo",
        async: false,
        dataType : 'json',
        data : setoption,
        success : function(json){
            if(setoption.t=="t0"){
                const xAxis = json.xAxis;
                const series = [];
                for(let i=0;i<json.series.length;i++){
                    series.push(Number(json.series[i]));
                }

                result = {"xAxis":xAxis,"series":series};
            }else{

                const data = [];
                json.forEach((element,index)=>{
                    data.push({"value":Number(element.value),"name":element.name});
                });

                result = data;
            }
        },
		error:function(request){
			alert("code : " + request.status);
		}
    });

    return result;

}

function chartloadT0(opdata){

    let myChart = echarts.init(document.getElementById('chartin'))
   
	let option = {
        xAxis: {
            type: 'category',
            data: opdata.xAxis
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
            data: opdata.series,
            type: 'line',
            smooth: true
            }
        ]
    };
    myChart.setOption(option);
    
}

function chartloadT1(opdata){

    let myChart = echarts.init(document.getElementById('chartin'))

    option = {
        tooltip: {
          trigger: 'item'
        },
        legend: {
          top: '5%',
          left: 'center'
        },
        series: [
          {
            name: '인기 검색어',
            type: 'pie',
            radius: ['40%', '70%'],
            avoidLabelOverlap: false,
            itemStyle: {
              borderRadius: 10,
              borderColor: '#fff',
              borderWidth: 2
            },
            label: {
              show: false,
              position: 'center'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: 40,
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
            },
            data: opdata
          }
        ]
    };
      
    myChart.setOption(option);
}