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
	if(document.getElementById('uf').value != ""){
		DWRUtil.useLoadingMessage('Carregando...');
		CidadeDWR.getCidadesCheckList(createListCidadesCheck, document.getElementById("uf").value);
	}
}

function createListCidadesCheck(data)
{
	addChecks('cidadesCheck',data,'populaBairros()');
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

function prepareEnviarForm()
{
	if ( !($('#popup').length) )
		$('body').append("<div id='popup'></div>");
	
	var candidatosIdsMarcados = $("input[name='candidatosId']:checked"); 
	
	if( candidatosIdsMarcados.size() < 1)
	{
		jAlert("Nenhum candidato selecionado!");
	}
	else
	{
		var candidatosIds = [];
		$(candidatosIdsMarcados).each(function(i, marcado){
			candidatosIds[i] = $(marcado).val();
		});
		
		CandidatoDWR.findExColaboradores( candidatosIds, function(colaboradores) {
			if( $(colaboradores).size() > 0 )
			{
				var msg = "";
				var clss = "";

				msg += "<p>Identificamos pelo CPF do(s) candidato(s) selecionado(s) os seguintes colaboradores:</p>"; 
				msg += "<table class='dados'>";
				msg += "	<thead>";
				msg += "		<tr>";
				msg += "			<th width='30'></th>";
				msg += "			<th>Candidato</th>";
				msg += "			<th width='220' style='background:#7BA6D3;'>Colaborador</th>";
				msg += "			<th width='105' style='background:#7BA6D3;'>CPF</th>";
				msg += "			<th width='85' style='background:#7BA6D3;'>Desligado em</th>";
				msg += "		</tr>";
				msg += "	</thead>";
				msg += "	<tbody>";
				$(colaboradores).each(function(i, colaborador) {
					clss = i%2 == 0 ? 'odd' : 'even';
					msg += "<tr class='" + clss + "'>";
					msg += "	<td><input type='checkbox' checked='checked' onclick='toggleCandidato(this.checked, " + colaborador.candidatoId + ")'/></td>";
					msg += "	<td>" + colaborador.candidatoNome + "</td>";
					msg += "	<td>" + colaborador.nome + "</td>";
					msg += "	<td align='center'>" + colaborador.cpf + "</td>";
					msg += "	<td align='center'>" + colaborador.dataDesligamento + "</td>";
					msg += "</tr>";
				});
				msg += "	</tbody>";
				msg += "</table>";
				
				$("#popup").html(msg)
							.dialog({	title: "Colaboradores ou ex-colaboradores detectados",
										modal: true, 
										width: 720,
										buttons: [
												    {
												        text: "Concluir",
												        click: function() { document.formCand.submit(); }
												    },
												    {
												        text: "Cancelar",
												        click: function() { $(this).dialog("close"); }
												    }
												] 
										});
			}
			else
				document.formCand.submit();
		});
	}
}

function toggleCandidato(marcar, candidatoId)
{
	$("input[name='candidatosId'][value='" + candidatoId + "']").attr('checked', marcar);
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