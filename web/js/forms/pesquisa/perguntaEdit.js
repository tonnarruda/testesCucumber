var AspectosArray = new Array();

(function($) {

	var populaAspectos = function() {
		var aspectoId = $("#questionario").val();
		if(aspectoId != "")	{
			AspectoDWR.getAspectos(aspectoId, function(data) {
				AspectosArray = data;
				$("#aspecto").autocomplete(AspectosArray);
			});
		}
	};
	
	$('#help_aspecto').qtip({
		content: '<strong>Aspectos</strong><br />Em uma pesquisa pode ser necessário avaliar vários Aspectos. <br/>Por exemplo: uma pesquisa de clima organizacional pode organizar<br/> as perguntas em dois aspectos: Ambiente e Relacionamento.<br/> Os aspectos podem ou não ser exibidos na hora da aplicação da pesquisa.'
		, style: { width: '100px' }
	});
	
	

	exibeComentario();
	exibePorTipo();
	populaAspectos();
	
})(jQuery);