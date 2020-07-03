$( function() {
	var availableTags = [];
	var obj = {};
	$("tr").each(function(i){
		$(this).children().each(function(j){
			obj[j] = $(this).text();
		});
		availableTags.push(obj[0].replace(/\n/g,"").replace(/\t/g,""));
	});
	
	$( "#searchWord" ).autocomplete({
		source: availableTags
	});
} );
