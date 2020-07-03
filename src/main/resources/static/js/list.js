$( function() {
	var availableTags = [];
	var obj = {};
	$("tr").each(function(i){
		$(this).children().each(function(j){
			obj[j] = $(this).text();
		});
		console.log(obj);
		availableTags.push(obj[0].replace(/\n/g,"").replace(/\t/g,""));
		console.log(obj[0]);
	});
	
	console.log(availableTags);
	$( "#searchWord" ).autocomplete({
		source: availableTags
	});
} );
