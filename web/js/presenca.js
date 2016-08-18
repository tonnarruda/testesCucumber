function dialogCertificacaoLote(diaTurmaId, turmaId, controlarVencimentoCertificacaoPor, img){
	msg = "Existem colaboradores certificados. </br>" +
			"Deseja realmente descertificar esses colaboradores?</br>" +
			"Ao confirmar, o colaborador será descertificado " +
			"e caso exista notas de avaliações prática as mesmas serão excluídas.";
	
	$('#dialog').html(msg).dialog({ 	modal: true, 
											width: 500,
											maxHeight: 360,
											buttons: 
											[
											    {
											        text: "Confirmar",
											        click: function() { 
											        	desmarcarTodos(diaTurmaId, turmaId, controlarVencimentoCertificacaoPor, img);
														removeImg();
											        	$(this).dialog("close");									        
											        }
											    },
											    {
											        text: "Cancelar",
											        click: function() { $(this).dialog("close"); }
											    }
											]
										});
}

function dialogCertificacao(diaTurmaId, colaboradorTurmaId, certificadoEmTurmaPosterior, img){
	if(isAprovado(colaboradorTurmaId, (img.title != "Presente"))){
		setFrequencia(diaTurmaId, colaboradorTurmaId, certificadoEmTurmaPosterior, img);
	}else{
		certificacoesNomes = $("#certificacoesNomes_" + colaboradorTurmaId).attr('value');
		
		msg = "Este colaborador está certificado. </br>";
		if (certificacoesNomes != null)
			msg += "Certificado(s): " +  certificacoesNomes + "</br>";
			
		msg += "Deseja realmente descertificar esse colaborador?</br>" + 
			"Ao confirmar, o colaborador será descertificado " +
			"e caso exista notas de avaliações prática as mesmas serão excluídas.";
	
	
		$('#dialog').html(msg).dialog({ 	modal: true, 
												width: 500,
												maxHeight: 360,
												buttons: 
												[
												    {
												        text: "Confirmar",
												        click: function() { 
												        	$('#img_' + colaboradorTurmaId).remove();
												        	setFrequencia(diaTurmaId, colaboradorTurmaId, certificadoEmTurmaPosterior, img);
															$(this).dialog("close");									        
												        }
												    },
												    {
												        text: "Cancelar",
												        click: function() { $(this).dialog("close"); }
												    }
												]
											});
	}
}