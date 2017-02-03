
function verificaSelecao(turmaId, cursoId, colaboradoresIds, vencimentoPorCertificacao)
{
	if(colaboradoresIds.length > 0)
	{
		dwr.util.useLoadingMessage('Carregando...');
		ColaboradorTurmaDWR.checaColaboradorInscritoEmOutraTurma($("input[name='turma.id']").val(), $("input[name='turma.curso.id']").val(), colaboradoresIds,
			function(retornoCallBack){
				montaDialog(retornoCallBack, 400, 700, 'Os seguintes colaboradores já estão inscritos neste curso.<br />Deseja realmente incluí-los nesta turma?', vencimentoPorCertificacao);
		});
	}
	else
	{
		jAlert('Selecione um colaborador se deseja inserir participantes na turma.');
		return false;
	}
}

function verificaSeParticipaDeUmaCertificacao()
{
	ColaboradorTurmaDWR.verificaColaboradorCertificadoNaCertificacaoPreRequisito($("input[name='turma.curso.id']").val(), colaboradoresIds,
			function(retornoCallBack){
				montaDialog(retornoCallBack, 400, 575, 'Este curso está em uma certificação com pré-requisito.<br/> Existem colaboradores que não estão certificados. Deseja realmente incluí-los neste curso?', false);
	});
}

function montaDialog(msg, altura, largura, titulo, deveVerificarSeParticipaDeUmaCertificacao)
{
	if (msg != ""){
		$('<div>' + msg + '</div>').dialog({title: titulo,
											modal: true, 
											height: altura,
											width: largura,
											buttons: [
											    {
											        text: "Sim",
											        click: function() { $(this).dialog("close"); if(deveVerificarSeParticipaDeUmaCertificacao){ verificaSeParticipaDeUmaCertificacao();} else {document.formColab.submit();} }
											    },
											    {
											        text: "Não",
											        click: function() { $(this).dialog("close");}
											    }
											] 
											});
	}else{ 
		if(deveVerificarSeParticipaDeUmaCertificacao){
			verificaSeParticipaDeUmaCertificacao();
		}else{
			document.formColab.submit();
		}
	}
}