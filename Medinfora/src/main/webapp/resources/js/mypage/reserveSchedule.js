// word(예약자명) 에 대한 정보 보여주기
function ShowInfo(checkin){

	$.ajax({

        url:"getPatientInfo.bibo"
        , data: {checkin:checkin}
        , dataType:"json"
        , success: function(json){
            // 모달 내용 업데이트
            $('#modal-name').text(json.name);
            $('#modal-patient_name').text(json.name);
            $('#modal-mobile').text(json.mobile);
            $('#modal-email').text(json.email);
            $('#modal-address').text(json.address);
            $('#modal-birthday').text(json.birthday);
			if(json.gender == "1"){
				$('#modal-gender').text("남자");
			}
			if(json.gender == "2"){
				$('#modal-gender').text("여자");
			}

            $('#PatientInfoModal').modal('show');
        }
        , error:function(request){
			alert("code: "+request.status);
		}

    })  // end of $.ajax({---------------

}	// end of function ShowInfo(word){-----------------