function populaCidades()
{
	if(document.getElementById('uf').value == "")
	{
		DWRUtil.removeAllOptions("cidade");
	}else{
		DWRUtil.useLoadingMessage('Carregando...');
		CidadeDWR.getCidades(createListCidades, document.getElementById("uf").value);
	}
}

function createListCidades(data)
{
	DWRUtil.removeAllOptions("cidade");
	DWRUtil.addOptions("cidade", data);
}


function populaCidadesCheckList()
{
	DWRUtil.useLoadingMessage('Carregando...');
	CidadeDWR.getCidadesCheckList(createListCidadesCheck, document.getElementById("uf").value);
}

function createListCidadesCheck(data)
{
	addChecks('cidadesCheck',data, 'populaBairros()');
}

function marcarDesmarcar(frm)
{
	var vMarcar;

	if (document.getElementById('md').checked)
	{
		vMarcar = true;
	}
	else
	{
		vMarcar = false;
	}

	with(frm)
	{
		for(i = 0; i < elements.length; i++)
		{
			if(elements[i].name == 'candidatosId' && elements[i].type == 'checkbox')
			{
				elements[i].checked = vMarcar;
			}
		}
	}
}

function verificaCandidatos()
{
	if(document.formCand.candidatosId.length == undefined)
	{
		return document.formCand.candidatosId.checked;
	}

	for(i = 0; i < document.formCand.candidatosId.length; i++)
	{
		if(document.formCand.candidatosId[i].checked)
			return true;
	}

	return false;
}

function prepareEnviarForm()
{
	if(verificaCandidatos())
	{
		
		document.formCand.submit();
	}
	else
	{
		jAlert("Nenhum candidato selecionado!");
	}
}

// ids de candidatoSolicitacao
function marcarDesmarcarCandidatoSolicitacao(frm)
{
	var vMarcar;
	
	if (document.getElementById('md').checked)
	{
		vMarcar = true;
	}
	else
	{
		vMarcar = false;
	}
	
	with(frm)
	{
		for(i = 0; i < elements.length; i++)
		{
			if(elements[i].name == 'candidatoSolicitacaoIdsSelecionados' && elements[i].type == 'checkbox')
			{
				elements[i].checked = vMarcar;
			}
		}
	}
}

function verificaCandidatoSolicitacaos()
{
	if(document.formCandSolic.candidatoSolicitacaoIdsSelecionados.length == undefined)
	{
		return document.formCandSolic.candidatoSolicitacaoIdsSelecionados.checked;
	}
	
	for(i = 0; i < document.formCandSolic.candidatoSolicitacaoIdsSelecionados.length; i++)
	{
		if(document.formCandSolic.candidatoSolicitacaoIdsSelecionados[i].checked)
			return true;
	}
	
	return false;
}

function prepareEnviarFormCandSolic()
{
	if(verificaCandidatoSolicitacaos())
	{
		
		document.formCandSolic.submit();
	}
	else
	{
		jAlert("Nenhum candidato selecionado!");
	}
}