jQuery(document).ready(function() {

	jQuery.fn.hideBox = function () {
	    jQuery(this).each(function () {
			jQuery(this).find('h4 > a:first').addClass('fechado');
			jQuery(this).find('ul:first').hide();
        });
	};
	jQuery.fn.showBox = function () {
	    jQuery(this).each(function () {
			jQuery(this).find('h4 > a:first').removeClass('fechado');
			jQuery(this).find('ul:first').show();
        });
	};
	jQuery.fn.toggleBox = function () {
	    jQuery(this).each(function () {
			jQuery(this).find('h4 > a:first').toggleClass('fechado');
			jQuery(this).find('ul:first').toggle();
        });
	};

	jQuery("#sortable").sortable({
		cursor: "move",
		distance: 10,
		delay:30,
		update: function (event, ui){
			var link = ui.item.find('a.linkBox:first');
			link.unbind("click");
			link.one("click", function (event) { 
				event.stopImmediatePropagation();
				jQuery(this).click(clickHandler);
			}); 
		} 
	});
	
	var clickHandler = function (event){		
		jQuery(this).parents('li:first').toggleBox();		
	};
	
	jQuery('.linkBox').click(clickHandler);

	ordenaBoxes();
});

function gravarConfiguracao(usuarioId)
{
	var caixasOrdenadas = [];
	var caixasStatus = [];
	
	jQuery(".cabecalhoBox").each(function(index)
	{
		var classNameCabecalho = jQuery(this).attr("id");
		
	    caixasOrdenadas.push(classNameCabecalho);
		caixasStatus.push(jQuery("#box" + classNameCabecalho).css("display") == "block");
	});
	
	dwr.engine.setErrorHandler(errorGravarConfiguracaoPerformance);
	ConfiguracaoPerformanceDWR.gravarConfiguracao(usuarioId, caixasOrdenadas, caixasStatus, retorno);
}

function retorno()
{
	jAlert("Layout gravado com sucesso.");
}

function errorGravarConfiguracaoPerformance()
{
	jAlert("Ocorreu um erro ao gravar o layout.");
}

function ordenaBoxes()
{
	jQuery(configPerformanceBoxes).each(function (i, config){
		var li = jQuery('#' + config.caixa).appendTo(jQuery('#sortable'));
		if (!config.aberta)
			li.hideBox();
	});	
}