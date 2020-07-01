$(function() {
	setTelephone("#tel1");
	setTelephone("#tel2");
	setTelephone("#tel3");

	function setTelephone(id) {
		var tel = $(id).val().split(",");
		console.log(tel);
		var arrayNum = id.replace("#tel", "");
		$(id).val(tel[parseInt(arrayNum) - 1]);
	}

	$("#zipCode").on("keyup", function() {
		var zipCode = $("#zipCode").val();
		if(!zipCode.match("^[0-9]{3}-[0-9]{4}$")){
			$("#address").val("");
			return;
		}
		$.ajax({
			url : "http://zipcoda.net/api",
			dataType : "jsonp",
			data : {
				zipcode : $("#zipCode").val()
			},
			async : true
		}).done(function(data) {
			$("#address").val(data.items[0].pref + data.items[0].address);
		}).fail(function(XMLHttpRequest, textStatus, errorThrown) {
			console.log("XMLHttpRequest : " + XMLHttpRequest.status);
			console.log("textStatus     : " + textStatus);
			console.log("errorThrown    : " + errorThrown.message);
		});
	});
});