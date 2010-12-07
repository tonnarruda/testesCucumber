window.jsonCEP = "";

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
		
		
		if (jQuery("#cidade").length && (jsonCEP != ''))
		{
			jQuery("#cidade option").each(function() {
				if (jQuery(this).text() == jsonCEP.query.results.cep.cidade)
				{
					jQuery("#cidade").val(jQuery(this).val());
					jQuery("#cidade").change();
					return;
				}
			});
		}
	};

	var createListBairros = function(data) {
		$('#bairroNome').unautocomplete();
		bairrosArray = data;
		$("#bairroNome").autocomplete(bairrosArray);
	};
	
	$("#uf").change(populaCidades);
	$("#cidade").change(populaBairros);
	
	$(document).ready(function(){
		if($("#cidade").val() != "") {
			populaBairros();
		}
	});
})(jQuery);