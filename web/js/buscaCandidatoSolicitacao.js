function populaCidades()
{
	if(document.getElementById('uf').value == "")
	{
		dwr.util.removeAllOptions("cidade");
	}else{
		dwr.util.useLoadingMessage('Carregando...');
		CidadeDWR.getCidades(document.getElementById("uf").value, createListCidades);
	}
}

function createListCidades(data)
{
	dwr.util.removeAllOptions("cidade");
	dwr.util.addOptions("cidade", data);
}


function populaCidadesCheckList()
{
	if(document.getElementById('uf').value != ""){
		dwr.util.useLoadingMessage('Carregando...');
		CidadeDWR.getCidadesCheckList(document.getElementById("uf").value, createListCidadesCheck);
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
	var candidatosIdsMarcados = $("#formCand input[name='candidatosId']:checked");
	
	if( candidatosIdsMarcados.size() < 1)
	{
		jAlert("Nenhum candidato selecionado!");
	}
	else
	{
		var cpfs = [];
		
		$(candidatosIdsMarcados).each(function(i, marcado){
			cpfs[i] = $(marcado).attr('cpf');
		});
		
		CandidatoDWR.findColaboradoresMesmoCpf( cpfs, function(colaboradores) {
			
			if( $(colaboradores).size() > 0 )
			{
				var msg = "";
				var clss = "";
				var candNome = "";

				msg += "<p>Foram encontrados colaboradores com o mesmo CPF de alguns candidatos selecionados.<br />"; 
				msg += "Desmarque os candidatos que não devem permanecer nessa triagem:</p>"; 
				msg += "<table class='dados'>";
				msg += "	<thead>";
				msg += "		<tr>";
				msg += "			<th width='30'></th>";
				msg += "			<th>Candidato</th>";
				msg += "			<th width='5' style='background-color:#f2f2f2'></th>";
				msg += "			<th width='220'>Colaborador</th>";
				msg += "			<th width='105'>CPF</th>";
				msg += "			<th width='85'>Desligado em</th>";
				msg += "		</tr>";
				msg += "	</thead>";
				msg += "	<tbody>";
				$(colaboradores).each(function(i, colaborador) {
					candNome = $("#formCand a[cpf='" + colaborador.cpf + "']").text();
					
					clss = i%2 == 0 ? 'odd' : 'even';
					msg += "<tr class='" + clss + "'>";
					msg += "	<td><input type='checkbox' name='candidatosPopupId' cpf='" + colaborador.cpf + "' value='" + colaborador.cpf + "' checked='checked' onclick='toggleCandidato(this.checked, \"" + colaborador.cpf + "\")'/></td>";
					msg += "	<td>" + candNome + "</td>";
					msg += "	<td style='background-color:#f2f2f2;'></td>";
					msg += "	<td>" + colaborador.nome + "</td>";
					msg += "	<td align='center'>" + colaborador.cpf + "</td>";
					msg += "	<td align='center'>" + colaborador.dataDesligamento + "</td>";
					msg += "</tr>";
				});
				msg += "	</tbody>";
				msg += "</table>";
				
				if ( !($('#popup').length) )
					$('body').append("<div id='popup'></div>");
				
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

function toggleCandidato(marcar, cpf)
{
	$("#formCand input[name='candidatosId'][cpf='" + cpf + "']").attr('checked', marcar);
	$("#popup input[name='candidatosPopupId'][cpf='" + cpf + "']").not(this).attr('checked', marcar);
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

function removerCandidatosDaSolicitacao(solicitacaoId, form)
{
	if(verificaCandidatoSolicitacaos())
	{
		document.formCandSolic.action='removerCandidatosDaSolicitacao.action?solicitacao.id='+solicitacaoId;
		document.formCandSolic.submit();
	}
	else
	{
		jAlert("Nenhum candidato selecionado!");
	}
}