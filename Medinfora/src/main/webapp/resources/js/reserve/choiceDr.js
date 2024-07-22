$(function() {
    $("input.inputbox").bind("keydown", function(e){
		if(e.keyCode == 13){ // 엔터를 했을 경우
			HPSearch();
		}
	});
})      // end of $(function() {---------------

$(document).on('click','.resulthp',(e) => {

    const closeset = $(e.target).closest(".resulthp");
    // 부모 엘리먼트 btn_card로 고정하기위해 작성

    // 각각의 체크박스와 커스텀 박스를 선언
    const ckeckeditem = closeset.find(".hj_custom-checkbox");
    const customitem = closeset.find(".hj_custom-checkbox-label");

    if(!ckeckeditem.is(':checked')){ //체크 하지 않았던 경우
        checkOnlyOne(ckeckeditem,customitem);
        $(".btn_card").removeClass("choiceHospital");
        $(".hospital_name").removeClass("choicefontcolor");
        $(".hospital_addr").removeClass("choicefontcolor");
        closeset.addClass("choiceHospital");
        closeset.find(".hospital_name").addClass("choicefontcolor");
        closeset.find(".hospital_addr").addClass("choicefontcolor");

    }else{ //이미 체크 한 경우
        ckeckeditem.prop("checked",false);
        customitem.prop("checked",false);
        closeset.removeClass("choiceHospital");
        closeset.find(".hospital_name").removeClass("choicefontcolor");
        closeset.find(".hospital_addr").removeClass("choicefontcolor");
    }

    $("input[name='hidx']").val(closeset.find('.hidx').text());
    $("input[name='sel_hpname'").val(closeset.find(".hospital_name").text());
});

$(document).on("mouseover",$(".resulthp"),(e)=>{
    const closeset = $(e.target).closest(".resulthp");

    const title = closeset.find(".hospital_name");

    title.addClass("hospital_name_hover");
}).on("mouseover",$(".resulthp"),(e)=>{
    
    const closeset = $(e.target).closest(".resulthp");
        
    const title = closeset.find(".hospital_name");

    title.removeClass("hospital_name_hover");
});

$(document).on('click','.page-link',() => {
    $("input[name='hidx']").val("");
})

/**
 * 체크박스 하나만 선택 가능하게 하는 함수
 * @param {object} ckeckeditem orgin checkBox
 * @param {object} customitem custom checkbox
 */
function checkOnlyOne(ckeckeditem,customitem) {
  
    const checkboxes1 = document.querySelectorAll(".hj_custom-checkbox"); // 체크박스
    const checkboxes2 = document.querySelectorAll(".hj_custom-checkbox-label"); // 커스텀
    
    checkboxes1.forEach((cb) => {
        cb.checked = false;
    })

    checkboxes2.forEach((cb) => {
        cb.checked = false;
    })

    ckeckeditem.prop("checked",true);
    customitem.prop("checked",true);

}   // end of function checkOnlyOne(ckeckeditem,customitem) {----------------------

function hpSelectNext(){
	if($("input[name='hidx']").val() == ""){
        alert("선택된 병원이 존재하지 않습니다.");
        return;
    }

    const hidx = $("input[name='hidx']").val();

    const frm = document.hpinfo;
    frm.action = "choiceDay.bibo";
    frm.method = "post";
    frm.submit();

}	// end of function hpSelectNext(){----------------------