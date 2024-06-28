$(function(){
    
    $("label.hj_custom-checkbox-label").on("click",(e) => {
        e.stopPropagation();
        $("input.hj_custom-checkbox").prop("checked",false);
        $(e.target).prev().prop("checked", true);
        $("button.btn_card").removeClass("choiceHospital");
        $(e.target).parent("button.btn_card").addClass("choiceHospital");
    })  

    $("button.btn_card").on("click",(e) => {
        e.stopPropagation();
        $("input.hj_custom-checkbox").prop("checked",false);
        $(e.target).children("input.hj_custom-checkbox").prop("checked", true);
        $("button.btn_card").removeClass("choiceHospital");
        $(e.target).addClass("choiceHospital");
    })

    $("h4.hospital_name").on("click",(e) => {
        e.stopPropagation();
        $("input.hj_custom-checkbox").prop("checked",false);
        $(e.target).next().prop("checked", true); 
        $("button.btn_card").removeClass("choiceHospital");
        $(e.target).parent("button.btn_card").addClass("choiceHospital");

    })

    $("p.hospital_addr").on("click",(e) => {
        e.stopPropagation();
        $("input.hj_custom-checkbox").prop("checked",false);
        $(e.target).prev().prev().prop("checked", true);
        $("button.btn_card").removeClass("choiceHospital");
        $(e.target).parent("button.btn_card").addClass("choiceHospital");
    })

})