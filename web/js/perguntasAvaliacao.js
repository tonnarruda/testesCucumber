function validaRespostas(camposObrigatorios, camposValidos, submete, validaObj, validaMultipla, validaSubjetiva, validaNota)
{
	var ultimoPerguntaId = " ";
	var perguntasSemResposta = new Array();
	var temRespostaMarcada = false;
	var opcaoResposta = null;
	
	jQuery("[class*=opcaoResposta]").each(function(index) {
		
		console.log(this);
		
		// pegando o elemento para validar e o id da pergunta para comparar
		opcaoResposta = jQuery(this);
		var perguntaId = opcaoResposta.attr('class').split(',')[0].substr(13);
		
		if (perguntaId != (ultimoPerguntaId) && ultimoPerguntaId != " ")
		{
			// verificamos se ja passamos por todas as respostas da pergunta anterior 
			// e nenhuma nao foi respondida
			if (!temRespostaMarcada)
				perguntasSemResposta.push(ultimoPerguntaId);
				
			temRespostaMarcada = false;
		}
		
		ultimoPerguntaId = perguntaId;
		
		if (!temRespostaMarcada)
			temRespostaMarcada = validaRespostaMarcada(opcaoResposta, validaObj, validaMultipla, validaSubjetiva, validaNota);
	});
	
	// verifica a última pergunta.
	if (!temRespostaMarcada && ultimoPerguntaId.trim()!='')
		perguntasSemResposta.push(ultimoPerguntaId);
	
	// indicando quais nao foram marcadas
	
	jQuery("[class^=pergunta]").css("backgroundColor", "#FFF").css("padding", "0");
	
	if (perguntasSemResposta.length > 0)
	{
		jQuery(perguntasSemResposta).each( function() {
			jQuery(".pergunta" + this).css("backgroundColor", "#FFEEC2").css("padding", "3px");
		});
		
		jAlert('Responda as perguntas indicadas.');
		
		return false;
	}

	if(submete)
		return  validaFormulario('form',camposObrigatorios, camposValidos);
	else
		return true;
}

function validaRespostaMarcada(resposta, validaObj, validaMultipla, validaSubjetiva, validaNota)
{
	if (resposta.is('input:radio'))
	{
		if(validaObj)
			return resposta.attr('checked');
	}
	else if (resposta.is('input:checkbox'))
	{
		if(validaMultipla)
			return resposta.attr('checked');
	}
	else if (resposta.is('textarea'))
	{
		if(validaSubjetiva)
			return resposta.val().length > 0;
	}
	else if (resposta.is('select'))
	{
		if(validaNota)
			return resposta.val() != "";
	}
	
	return true;
}