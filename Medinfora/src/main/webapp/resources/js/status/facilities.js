
// 시도별 일반 입원실 시설 현황
document.addEventListener("DOMContentLoaded", function () {
  var chartDom = document.getElementById("main");
  var myChart = echarts.init(chartDom);

  var option = {
    xAxis: {
      type: "category",
      data: [
        "서울",
        "부산",
        "대구",
        "인천",
        "광주",
        "대전",
        "울산",
        "세종",
        "경기",
        "강원",
        "충북",
        "전남",
        "경북",
        "경남",
        "제주",
      ],
    },
    yAxis: {
      type: "value",
    },
    series: [
      {
        data: [
          120333, 86667, 51555, 45974, 50768, 29860, 19498, 2797, 181379, 21728,
          35804, 50123, 52778, 54259, 80472, 6621,
        ],
        type: "bar",
      },
    ],
  };

  myChart.setOption(option);
});



