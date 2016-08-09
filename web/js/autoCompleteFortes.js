function ajaxData(urlFind){
//	var text = typeof text == "undefined" ? request.term : text;
	return function( request, response ) {
				$.ajax({
					url: urlFind,
					dataType: "json",
				type: "POST",
				data: { 
					descricao: request.term
				},
				success: function( data ) {
					response( $.map( data, function( item ) {
						return {
							label: item.value.replace(
								new RegExp(
									"(?![^&;]+;)(?!<[^<>]*)(" +
									$.ui.autocomplete.escapeRegex(request.term) +
									")(?![^<>]*>)(?![^&;]+;)", "gi"
								), "<strong>$1</strong>" ),
							id: item.id,
							value: item.value
						}
					}));
				}
			});
		};
}

var renderData = function(ul, item) {
	return $( "<li></li>" )
				.data( "item.autocomplete", item )
				.append( "<a>" + item.label + "</a>" )
				.appendTo( ul );
}