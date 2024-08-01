$(document).ready(function(){

    const ageArray = [{range:"연령대", values:"15117AC001102+15117AC001103+15117AC001104+15117AC001105+15117AC001106+15117AC001107+15117AC001108+15117AC001109+15117AC001110+15117AC001111+15117AC001112+15117AC001113+15117AC001114+15117AC001115+15117AC001116+15117AC001117+15117AC001118+15117AC001119+"}
                     ,{range:"전체", values:"15117AC001102+15117AC001103+15117AC001104+15117AC001105+15117AC001106+15117AC001107+15117AC001108+15117AC001109+15117AC001110+15117AC001111+15117AC001112+15117AC001113+15117AC001114+15117AC001115+15117AC001116+15117AC001117+15117AC001118+15117AC001119+"}
                     ,{range:"10세미만", values:"15117AC001102+15117AC001103+"}
                     ,{range:"10대", values:"15117AC001104+15117AC001105+"}
                     ,{range:"20대", values:"15117AC001106+15117AC001107+"}
                     ,{range:"30대", values:"15117AC001108+15117AC001109+"}
                     ,{range:"40대", values:"15117AC001110+15117AC001111+"}
                     ,{range:"50대", values:"15117AC001112+15117AC001113+"}
                     ,{range:"60대", values:"15117AC001114+15117AC001115+"}
                     ,{range:"70대", values:"15117AC001116+15117AC001117+"}
                     ,{range:"80세이상", values:"15117AC001118+15117AC001119+"}
                    ];


    const cancerArray = [{name:"암종류", values:"15117AC000101+"}
                         ,{name:"전체", values:"15117AC000101+"}
                         ,{name:"입술,구강 및 인두", values:"15117AC000102+"}
                         ,{name:"식도", values:"15117AC000103+"}
                         ,{name:"위", values:"15117AC000104+"}
                         ,{name:"대장", values:"15117AC000105+"}
                         ,{name:"간", values:"15117AC000106+"}
                         ,{name:"담낭 및 기타 담도", values:"15117AC000107+"}
                         ,{name:"췌장", values:"15117AC000108+"}
                         ,{name:"후두", values:"15117AC000109+"}
                         ,{name:"폐", values:"15117AC000110+"}
                         ,{name:"유방", values:"15117AC000111+"}
                         ,{name:"자궁경부", values:"15117AC000112+"}
                         ,{name:"자궁체부", values:"15117AC000113+"}
                         ,{name:"난소", values:"15117AC000114+"}
                         ,{name:"전립선", values:"15117AC000115+"}
                         ,{name:"고환", values:"15117AC000116+"}
                         ,{name:"신장", values:"15117AC000117+"}
                         ,{name:"방광", values:"15117AC000118+"}
                         ,{name:"뇌 및 중추신경계", values:"15117AC000119+"}
                         ,{name:"갑상선", values:"15117AC000120+"}
                         ,{name:"호지킨 림프종", values:"15117AC000121+"}
                         ,{name:"비호지킨 림프종", values:"15117AC000122+"}
                         ,{name:"다발성 골수종", values:"15117AC000123+"}
                         ,{name:"백혈병", values:"15117AC000124+"}
                         ,{name:"기타 암", values:"15117AC000125+"}
                        ];

    let cancerArea = ``;
    let ageArea = ``;

    for(const cancer of cancerArray){
        cancerArea += `<option value='${cancer.values}'>${cancer.name}</option>`;
    }

    for(const age of ageArray){
        ageArea += `<option value='${age.values}'>${age.range}</option>`;
    }

    $("select[name='agedata']").html(ageArea);
    $("select[name='cancerdata']").html(cancerArea);

    // returnData();
    getData();
    
    

});

function getData(){
    const key = $("input:hidden[name='key']").val();
    const year = $("select[name='yeardata']").val();
    const cancer = $("select[name='cancerdata']").val();
    const age = $("select[name='agedata']").val();

    // console.log(year+" "+cancer+" "+age+" "+gender);

    // kosis url로 바로 요청시 CrossDomain으로 인해 오류 발생하므로 컨트롤러로 우회해서 값을 받아온다.
    // 요청에 필요한 값만 보내준다.

    $.ajax({
        url:"roundaboutway.bibo",
        data:{"key":key
             ,"cancer":cancer
             ,"gender":"11101SSB21+11101SSB22+"
             ,"age":age
             ,"year":year},
        dataType:"json",
        async : true,
        success:function(json){
            //console.log(JSON.stringify(json));

            // 데이터를 구별해서 넣어준다.

            // 저장할 배열
            const incidence_total = []; // 조발생률 총
            const occurrences_total = []; // 발생자수 총
            const incidence_woman = []; // 조발생률 여자
            const incidence_man = [];  // 조발생률 남자
            const incidence = [];  // 조발생률 연령대
            const occurrences_woman = []; // 발생자수 여자
            const occurrences_man = []; // 발생자수 남자
            const man = []; // 발생자수 남자
            const woman = []; // 발생자수 여자
            const man_i = []; // 조발생률 남자
            const woman_i = []; // 조발생률 여자

            const age = []; // 연령대

            let cancer = json[0].C1_NM; // 암종류
            cancer = cancer.substr(0, cancer.indexOf('('));

            const year = json[0].PRD_DE; // 기간


            // 경우의 수 1번
            // C1_NM = 모든암

            // 먼저 조발생률 과 발생자수 필터링
            $.each(json, function(index, item){

              if(item.ITM_NM == "조발생률"){
                incidence_total.push(item);
              }

              if(item.ITM_NM == "발생자수"){
                occurrences_total.push(item);
              }

            });

            // 조발생률 및 발생자수 성별 분리
            $.each(incidence_total, function(index, item){

              if(item.C2_NM == "남자"){
                incidence_man.push(item);
              }

              if(item.C2_NM == "여자"){
                incidence_woman.push(item);
              }
            });


            $.each(occurrences_total, function(index, item){

              if(item.C2_NM == "남자"){
                occurrences_man.push(item);
              }

              if(item.C2_NM == "여자"){
                occurrences_woman.push(item);
              }
            });

            // console.log(occurrences_man);
            // console.log(occurrences_woman);

            // console.log(incidence_man);
            // console.log(incidence_woman);

            if(incidence_man.length > 0 && incidence_woman.length > 0){

              $.each(incidence_man, function(index, item){
                const item2 = incidence_woman[index];

                let num1 = item.DT;
                let num2 = item2.DT;

                if(num1 == "-"){
                  num1 = 0;
                }

                if(num2 == "-"){
                  num2 = 0;
                }


                if(index % 2 == 0){ // 짝수번째 일때(시작 0부터)

                  const sum = Number(num1) + Number(num2);

                  incidence.push(Math.round(sum * 100)/100 );

                  let agerange = ageRangeChange(item.C3_NM);

                  age.push(agerange);

                }
                else{
                  const sum2 = Number(num1) + Number(num2);
                  incidence[incidence.length - 1] += Math.round(sum2 * 100)/100;
                }

              });

            }
            else if(incidence_woman.length <= 0){

              $.each(incidence_man, function(index, item){

                let num1 = item.DT;

                if(num1 == "-"){
                  num1 = 0;
                }

                if(index % 2 == 0){ // 짝수번째 일때(시작 0부터)

                  const sum = Number(num1);
                  incidence.push(Math.round(sum * 100)/100);

                  let agerange = ageRangeChange(item.C3_NM);

                  age.push(agerange);
                }
                else{
                  const sum2 = Number(num1);
                  incidence[incidence.length - 1] += Math.round(sum2 * 100)/100;
                }

              });

            }
            else if(incidence_man.length <= 0){

              $.each(incidence_woman, function(index, item){

                let num1 = item.DT;

                if(num1 == "-"){
                  num1 = 0;
                }

                if(index % 2 == 0){ // 짝수번째 일때(시작 0부터)
                  const sum = Number(num1);
                  incidence.push(Math.round(sum * 100)/100);

                  ageRangeChange(item.C3_NM);
                }
                else{
                  const sum2 = Number(num1);
                  incidence[incidence.length - 1] += Math.round(sum2 * 100)/100;
                }

              });

            }

            // 조발생률 남자
            $.each(incidence_man, function(index, item){

              let num1 = item.DT;

              if(num1 == "-"){
                num1 = Number(0);
              }

              if(index % 2 == 0){ // 짝수번째 일때(시작 0부터)

                const sum = Number(num1);
                man_i.push(Math.round(sum * 10)/10);

              }
              else{
                const sum2 = Number(num1);
                man_i[man_i.length - 1] += Math.round(sum2 * 10)/10;
              }

            });

            // 조발생률 여자
            $.each(incidence_woman, function(index, item){

              let num1 = item.DT;

              if(num1 == "-"){
                num1 = Number(0);
              }

              if(index % 2 == 0){ // 짝수번째 일때(시작 0부터)

                const sum = Number(num1);
                woman_i.push(Math.round(sum * 10)/10);

              }
              else{
                const sum2 = Number(num1);
                woman_i[woman_i.length - 1] += Math.round(sum2 * 10)/10;
              }

            });



            // 발생자수(연령대별 구하기)
            if(occurrences_man.length > 0 && occurrences_woman.length > 0){

              $.each(occurrences_man, function(index, item){
                const item2 = occurrences_woman[index];

                let num1 = item.DT;
                let num2 = item2.DT;

                if(num1 == "-"){
                  num1 = 0;
                }

                if(num2 == "-"){
                  num2 = 0;
                }


                if(index % 2 == 0){ // 짝수번째 일때(시작 0부터)
                  man.push(Math.round(Number(num1)*100)/100);
                  woman.push(Math.round(Number(num2)*100)/100);
                }
                else{
                  man[man.length - 1] += Math.round(Number(num1)*100)/100;
                  woman[woman.length - 1] += Math.round(Number(num2)*100)/100;
                }

              });

            }

            multichart(man, man_i, woman, woman_i, incidence, age, year, cancer);

            // 엑셀다운용 데이터
            $("input:hidden[name='man']").val(man.join(","));
            $("input:hidden[name='man_i']").val(man_i.join(","));
            $("input:hidden[name='woman']").val(woman.join(","));
            $("input:hidden[name='woman_i']").val(woman_i.join(","));
            $("input:hidden[name='age']").val(age.join(","));
            $("input:hidden[name='year']").val(year);
            $("input:hidden[name='cancer']").val(cancer);

        },
        error : function(errcode){
            console.log("error :"+errcode);
        }

    });

}


// 차트&데이터 보여주기
function multichart(man, man_i, woman, woman_i, incidence, age, year, cancer){


  // console.log(incidence);
  // console.log(man);
  // console.log(woman);
  // console.log(age);
  
  var options = {
    series: [{
      name: '남자',
      type: 'column',
      data: man
    }, {
      name: '여자',
      type: 'column',
      data: woman
    }, {
      name: '조발생률',
      type: 'line',
      data: incidence
    }],
    chart: {
      height: 350,
      type: 'line',
      stacked: false
    },
    dataLabels: {
      enabled: false
    },
    stroke: {
      width: [1, 1, 4] // 각 시리즈의 선 두께
    },
    title: {
      text: '암발생자현황('+year+'년, '+cancer+')',
      align: 'center',
      offsetY: 30,
      style: {
        fontSize: '1.5rem', // 제목 폰트 크기
        fontWeight: 'bold', // 제목 폰트 두께
        color: '#333' // 제목 색상
      }
    },
    xaxis: {
      categories: age
    },
    yaxis: [
      {
        // Income과 Cashflow는 동일한 Y축을 사용
        title: {
          text: "발생자수(명)",
          style: {
            color: '#008FFB'
          }
        },
        axisTicks: {
          show: true
        },
        axisBorder: {
          show: true,
          color: '#008FFB'
        },
        labels: {
          style: {
            colors: '#008FFB'
          },
          formatter: function(value) {
            return Math.round(value);
          }
        }
      },
      {
        // Revenue는 별도의 Y축을 사용
        opposite: true,
        title: {
          text: "조발생률(명/10만명)",
          style: {
            color: '#FEB019'
          }
        },
        axisTicks: {
          show: true
        },
        axisBorder: {
          show: true,
          color: '#FEB019'
        },
        labels: {
          style: {
            colors: '#FEB019'
          },
          formatter: function(value) {
            return Math.round(value);
          }
        }
      }
    ],
    tooltip: {
      fixed: {
        enabled: true,
        position: 'topLeft',
        offsetY: 30,
        offsetX: 60
      }
    },
    legend: {
      horizontalAlign: 'left',
      offsetX: 40
    }
  };

  $("div#chart").empty();
  $("div#dataArea").empty();


  let chart = new ApexCharts(document.querySelector("#chart"), options);
  chart.render();



  /*
  const incidence_total = []; // 조발생률 총
  const occurrences_total = []; // 발생자수 총
  const incidence_woman = []; // 조발생률 여자
  const incidence_man = [];  // 조발생률 남자
  const incidence = [];  // 조발생률
  const occurrences_woman = []; // 발생자수 여자
  const occurrences_man = []; // 발생자수 남자
  const man = []; // 발생자수 여자
  const woman = []; // 발생자수 여자
  const age = []; // 연령대
  */

  let dataTable = `<table class="table-bordered" id="datatable">
                    <thead style="background-color: var(--object-skyblue-color);">
                      <tr>
                          <th rowspan="2" width="150">암종류</th>
                          <th rowspan="2" width="100">성별</th>
                          <th rowspan="2" width="150">연령대</th>
                          <th colspan="2">${year}년</th>
                      </tr>
                      <tr>
                        <th width="150">발생자수(명)</th>
                        <th width="250">조발생률(명/10만명)</th>
                      </tr>
                    </thead>`;
  
  dataTable += `<tbody class="nanum-b">`;


  $.each(man, function(index, item){
    if(index == 0){
      dataTable += `<tr>
                      <td rowspan='${man.length+woman.length}'>${cancer}</td>
                      <td rowspan='${man.length}'>남자</td>
                      <td>${age[index]}</td>
                      <td>${item}</td>
                      <td>${Number(man_i[index]).toFixed(1)}</td>
                    </tr>`;
    }
    else{
      dataTable += `<tr>
                      <td>${age[index]}</td>
                      <td>${item}</td>
                      <td>${Number(man_i[index]).toFixed(1)}</td>
                    </tr>`;
    }
  });

  $.each(woman, function(index, item){
    if(index == 0){
      dataTable += `<tr>
                      <td rowspan='${woman.length}'>여자</td>
                      <td>${age[index]}</td>
                      <td>${item}</td>
                      <td>${Number(woman_i[index]).toFixed(1)}</td>
                    </tr>`;
    }
    else{
      dataTable += `<tr>
                      <td>${age[index]}</td>
                      <td>${item}</td>
                      <td>${Number(woman_i[index]).toFixed(1)}</td>
                    </tr>`;
    }
  });

  dataTable += `  </tbody>
		            </table>`;


  $("div#dataArea").html(dataTable);

}


function ageRangeChange(agenum){

  let ageRange = "";

  if(agenum == "0-4세"){
    ageRange = "10세미만";
  }
  else if(agenum == "10-14세"){
    ageRange = "10대";
  }
  else if(agenum == "20-24세"){
    ageRange = "20대";
  }
  else if(agenum == "30-34세"){
    ageRange = "30대";
  }
  else if(agenum == "40-44세"){
    ageRange = "40대";
  }
  else if(agenum == "50-54세"){
    ageRange = "50대";
  }
  else if(agenum == "60-64세"){
    ageRange = "60대";
  }
  else if(agenum == "70-74세"){
    ageRange = "70대";
  }
  else if(agenum == "80-84세"){
    ageRange = "80대이상";
  }

  return ageRange;

}


function downloadExcel(){
  const frm = document.excelData;
  frm.method = "post";
  frm.action = "downloadStatisics.bibo";
  frm.submit();
}