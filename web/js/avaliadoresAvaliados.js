function abas(value, direcao, edicao)
{
	var link = 0;

	var qtdAbas = 2; //Quantidade de abas existentes.

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
		document.getElementById('aba' + i).style.background		= link == i ? "#E9E9E9"				: "#D5D5D5";
		document.getElementById('aba' + i).style.borderBottom	= link == i ? "1px solid #E9E9E9"	: "1px solid #000";
	}

	document.getElementById('voltar').disabled		= link == 1;
	document.getElementById('voltar').className		= link == 1 ? 'btnVoltarDesabilitado' : 'btnVoltar';

	document.getElementById('avancar').disabled		= link == qtdAbas;
	document.getElementById('avancar').className		= link == qtdAbas ? 'btnAvancarDesabilitado' : 'btnAvancar';

	if (!edicao)
	{
		document.getElementById('gravar').disabled		= link != qtdAbas;
		document.getElementById('gravar').className		= link != qtdAbas ? 'btnGravarDesabilitado' : 'btnGravar';
	}
}