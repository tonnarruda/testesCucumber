window.jsonCEP = "";

(function($) {

	var bairrosArray = new Array();
	
	var populaCidades = function() {
		if($("#uf").val() == "") {
			dwr.util.useLoadingMessage('Carregando...');
			dwr.util.removeAllOptions("cidade");
		} else {
			CidadeDWR.getCidades($("#uf").val(), createListCidades);
		}
	};
	
	var populaBairros = function() {
		BairroDWR.getBairros($("#cidade").val(), createListBairros);
	};
	
	var createListCidades = function (data) {
		dwr.util.useLoadingMessage('Carregando...');
		dwr.util.removeAllOptions("cidade");
		dwr.util.addOptions("cidade", data);
		
		
		if (jQuery("#cidade").length && (jsonCEP != ''))
		{
			jQuery("#cidade option").each(function() {
				if (jQuery(this).text() == jsonCEP.cidade)
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