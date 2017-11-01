var asterisco = "";

function decisoes(ast){
	asterisco = ast;
	decideTipoInscricao();
	decideCpfOuCnpj();
	decideQtdDiasAfastado();
	decideNumero();
	decideAtestado();
	
	if($('#obito').val() == "true")
		$('#dataObito').parent().parent().find('label').text('Data do Óbito:' +  asterisco);
	else
		$('#dataObito_button').hide();
}

function decideTipoInscricao(){
	valor = $('#tipoRegistardor').val();
	
	if(valor == '' || valor == 1){
		$('#tipoInscricao').attr('disabled',true).css('background', '#F6F6F6').val('');
		$('#tipoInscricao').parent().parent().find('label').text('Tipo de Inscrição');
		if(valor == 1)
			$('#iniciatCAT').val(1).attr('disabled',true).css('background', '#F6F6F6')
		else
			$('#iniciatCAT').val('').attr('disabled',true).css('background', '#F6F6F6')
	}else{
		$('#tipoInscricao').removeAttr('disabled').css('background', '#FFF');
		$('#tipoInscricao').parent().parent().find('label').text('Tipo de Inscrição' + asterisco);
		$('#iniciatCAT').val('').removeAttr('disabled').css('background', '#FFF');
	}
	
	decideCpfOuCnpj();
}

function decideCpfOuCnpj(){
	cpfOuCnpj = $('#tipoInscricao').val();
	if(cpfOuCnpj){
		$('#cpfOuCnpjRegistardor').removeAttr('disabled');
		if(cpfOuCnpj == 1){
			$('#cnpjRegistardor').css('background', '#FFF').removeAttr('disabled');
			$('#cnpjRegistardor').parent().parent().find('label').text('CNPJ' + asterisco);
			$('#cpfRegistardor').css('background', '#F6F6F6').attr('disabled', true);
			$('#cpfRegistardor').parent().parent().find('label').text('CPF');
		}else{
			$('#cpfRegistardor').css('background', '#FFF').removeAttr('disabled');
			$('#cpfRegistardor').parent().parent().find('label').text('CPF' + asterisco);
			$('#cnpjRegistardor').css('background', '#F6F6F6').attr('disabled', true);
			$('#cnpjRegistardor').parent().parent().find('label').text('CNPJ');	
		}
	}else{
		$('#cpfRegistardor').css('background', '#F6F6F6').attr('disabled', true);
		$('#cnpjRegistardor').css('background', '#F6F6F6').attr('disabled', true);
		$('#cnpjRegistardor').parent().parent().find('label').text('CNPJ');
		$('#cpfRegistardor').parent().parent().find('label').text('CPF');
	}
}

function decideObito(){
	if($('#tipo').val() && $('#tipo').val() == 3)
		$('#obito').attr('disabled',true).css('background', '#F6F6F6').val('true');
	else
		$('#obito').removeAttr('disabled').css('background', '#FFF').val('');
	
	decideDataObito();
}

function decideDataObito(){
	if($('#obito').val() && $('#obito').val() == 'true'){
		$('#dataObito').removeAttr('disabled').css('background', '#FFF').val('  /  /    ');
		$('#dataObito').parent().parent().find('label').text('Data do Óbito:' +  asterisco);
		$('#dataObito_button').show();
	}else{
		$('#dataObito').attr('disabled',true).css('background', '#F6F6F6').val('  /  /    ');
		$('#dataObito').parent().parent().find('label').text('Data do Óbito:');
		$('#dataObito_button').hide();
	}
}	

function decideQtdDiasAfastado(){
	if($('#gerouAfastamento').is(':checked'))
		$('#qtdDiasAfastado').removeAttr('disabled').css("background", "#FFF");
	else
		$('#qtdDiasAfastado').attr('disabled','true').css("background", "#F6F6F6");
}

function decideNumero(){
	if($('#emitiuCAT').is(':checked')){
		$('#numero, #dataCatOrigem').removeAttr('disabled').css("background", "#FFF");
		$('#dataCatOrigem').parent().parent().find('label').text('Data da CAT Origem:' + asterisco);
		$('#dataCatOrigem_button').show();
	}else{
		$('#numero, #dataCatOrigem').attr('disabled','true').css("background", "#F6F6F6");
		$('#dataCatOrigem').parent().parent().find('label').text('Data da CAT Origem:');
		$('#dataCatOrigem_button').hide();
	}
}

function decideAtestado(){
	ids = '#codigoCNES, #dataAtendimento, #horaAtendimento, #indicativoInternacao, #duracaoTratamentoEmDias, #indicativoAfastamento, #codCID, #descricaoComplementarLesa, ' +
	'#diagnosticoProvavel, #observacaoAtestado, #medicoNome, #orgaoDeClasse, #numericoInscricao, #ufAtestado';
	
	if($('#possuiAtestado').is(':checked')){
		$(ids).removeAttr('disabled').css("background", "#FFF");
		
		$('.atestado').each(function(){
			$(this).parent().parent().find('label').text($(this).parent().parent().find('label').text() + asterisco);
		});
		
		$('#divSelectDialogdescricaoNaturezaLesao0').parent().find('label').text($('#divSelectDialogdescricaoNaturezaLesao0').parent().find('label').text() + asterisco);
		document.getElementById('divSelectDialogdescricaoNaturezaLesao0').style.pointerEvents = 'auto'; 
	}else{
		$(ids).attr('disabled','true').css("background", "#F6F6F6");

		$('.atestado').each(function(){
			$(this).parent().parent().find('label').text($(this).parent().parent().find('label').text().replace('*', ''));
		});				
		
		$('#divSelectDialogdescricaoNaturezaLesao0').parent().find('label').text($('#divSelectDialogdescricaoNaturezaLesao0').parent().find('label').text().replace('*', ''));
		document.getElementById('divSelectDialogdescricaoNaturezaLesao0').style.pointerEvents = 'none';
	}
}