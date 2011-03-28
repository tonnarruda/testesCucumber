function abas(value, direcao, edicao, _qtdAbas)
{
	var link = 0;
	
	var qtdAbas = 5; //Quantidade de abas existentes.
	
	if  (_qtdAbas != 'undefined' && _qtdAbas != null)
	{
		qtdAbas = _qtdAbas;
	}

	if (value == -1)
	{
		var atual = jQuery('div [id^="content"]:visible').attr('class');

		if (direcao == 'A')
		{
			jQuery('#abas > div:visible').each(function(){
			    var a = this.id.replace('aba','');
			    link = a;
			    if(a > atual)
			        return false;
			    
			});
		}
		else
		{
			jQuery('#abas > div:visible').each(function(){
			    var a = this.id.replace('aba','');
			    if(atual == a)
			        return false;
			    
			    link = a;
			});
		}

		document.getElementById('aba').value = link;
	}
	else
	{
		document.getElementById('aba').value = value;
		link = value;
	}

	var ultimaAba;
	jQuery('#abas div:visible').each(function(){
		var i = this.id.replace('aba','');
		
		document.getElementById('content' + i).style.display	= link == i ? "block"				: "none";
		document.getElementById('aba' + i).style.background		= link == i ? "#F6F6F6"				: "#D9D9D9";
		document.getElementById('aba' + i).style.borderBottom	= link == i ? "1px solid #F6F6F6"	: "1px solid #CCCCCC";
		
		ultimaAba = i;
	});

	ajustaBotoes(link, ultimaAba);
}

	function ajustaBotoes(link, ultimaAba)
	{
		document.getElementById('voltar').disabled		= link == 1;
		document.getElementById('voltar').className		= link == 1 ? 'btnVoltarDesabilitado' : 'btnVoltar';
	
		document.getElementById('avancar').disabled		= link == ultimaAba;
		document.getElementById('avancar').className	= link == ultimaAba ? 'btnAvancarDesabilitado' : 'btnAvancar';
	
		jQuery('#gravarModuloExterno').attr("disabled", link != ultimaAba); 
		jQuery('#gravarModuloExterno').attr('class', link == ultimaAba ? 'btnGravar': 'btnGravarDesabilitado');
	}