
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
		
		if (jQuery("#cep").length && jQuery("#cidade").length)
		{
			jQuery("#cidade option").each(function() {
				if (jQuery(this).text() == resultadoCEP["cidade"])
				{
					jQuery("#cidade").val(jQuery(this).val());
					jQuery("#cidade").change();
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