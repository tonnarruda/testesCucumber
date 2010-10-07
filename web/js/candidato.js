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
		if (direcao == 'A')
		{
			link = 	Number(document.getElementById('aba').value) * 1 + 1;
			document.getElementById('aba').value = link;
		}
		else
		{
			link = 	document.getElementById('aba').value * 1 - 1;
			document.getElementById('aba').value = link;
		}
	}
	else
	{
		document.getElementById('aba').value = value;
		link = value;
	}

	for(i = 1; i <= qtdAbas; i++)
	{
		document.getElementById('content' + i).style.display	= link == i ? "block"				: "none";
		document.getElementById('aba' + i).style.background		= link == i ? "#F6F6F6"				: "#D9D9D9";
		document.getElementById('aba' + i).style.borderBottom	= link == i ? "1px solid #F6F6F6"	: "1px solid #CCCCCC";
	}

	document.getElementById('voltar').disabled		= link == 1;
	document.getElementById('voltar').className		= link == 1 ? 'btnVoltarDesabilitado' : 'btnVoltar';

	document.getElementById('avancar').disabled		= link == qtdAbas;
	document.getElementById('avancar').className		= link == qtdAbas ? 'btnAvancarDesabilitado' : 'btnAvancar';
}