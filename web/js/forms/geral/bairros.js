
(function($) {

	var bairrosArray = new Array();
	
	var populaCidades = function() {
		if($("#uf").val() == "") {
			DWRUtil.useLoadingMessage('Carregando...');
			DWRUtil.removeAllOptions("cidade");
		} else {
			CidadeDWR.getCidades(createListCidades,$("#uf").val());
		}
	};
	
	var populaBairros = function() {
		BairroDWR.getBairros(createListBairros, $("#cidade").val());
	};
	
	var createListCidades = function (data) {
		DWRUtil.useLoadingMessage('Carregando...');
		DWRUtil.removeAllOptions("cidade");
		DWRUtil.addOptions("cidade", data);
	};

	var createListBairros = function(data) {
		$('#bairroNome').unautocomplete();
		bairrosArray = data;
		$("#bairroNome").autocomplete(bairrosArray);
		$("#bairroNome").focus();
	};
	
	$("#uf").change(populaCidades);
	$("#cidade").change(populaBairros);
	
	$(document).ready(function(){
		if($("#cidade").val() != "") {
			populaBairros();
		}
	});
})(jQuery);