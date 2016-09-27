<div id="popupEnvioAmigo" title="Enviar anúncio de vaga para um amigo" style="display:none;">
	<h5></h5>
	<#assign nome=""/>
	<#if SESSION_CANDIDATO_NOME?exists>
		<#assign nome=SESSION_CANDIDATO_NOME/>
	</#if>

	<#assign email=""/>
	<#if SESSION_CANDIDATO_EMAIL?exists>
		<#assign email=SESSION_CANDIDATO_EMAIL/>
	</#if>
	
	<@ww.form name="formEnvioAmigo">
		<@ww.textfield label="Seu nome" name="nomeFrom" id="nomeFrom" cssStyle="width:320px;" maxLength="100" value="${nome}"/>
		<@ww.textfield label="Seu email" name="emailFrom" id="emailFrom" cssStyle="width:320px;" cssClass="mascaraEmail" maxLength="100" value="${email}"/>
		<@ww.textfield label="Nome do amigo" name="nomeTo" id="nomeTo" cssStyle="width:320px;" maxLength="100"/>
		<@ww.textfield label="Email do amigo" name="emailTo" id="emailTo" cssStyle="width:320px;" cssClass="mascaraEmail" maxLength="100"/>
	</@ww.form>
</div>

<script language='javascript'>
	function exibirFormAnuncioEmail(anuncioId, anuncioTitulo) 
	{
		$('#popupEnvioAmigo h5').text(anuncioTitulo);
	
		$('#popupEnvioAmigo').dialog({
			modal: true,
			width: 350,
			buttons: [ 
				{ text: "Cancelar", click: function() { $(this).dialog("close"); } }, 
				{ text: "Enviar", click: function() { enviarAnuncioEmail(anuncioId); } }
			]
		});
    }
    
    function enviarAnuncioEmail(anuncioId)
    {
    	var empresaId = null;
    	<#if empresaId?exists>
    		var empresaId 	= ${empresaId};
    	</#if>
    	
		var nomeFrom 	= $('#nomeFrom').val();
		var emailFrom 	= $('#emailFrom').val();
		var nomeTo 		= $('#nomeTo').val();
		var emailTo 	= $('#emailTo').val();
		
		if (validaFormulario(document.formEnvioAmigo, ['nomeFrom','emailFrom','nomeTo','emailTo'], ['emailFrom','emailTo'], true))
	     	SolicitacaoDWR.enviarAnuncioEmail(anuncioId, empresaId, nomeFrom, emailFrom, nomeTo, emailTo, function(retorno) { jAlert(retorno); $('#popupEnvioAmigo').dialog('close'); });
    }
</script>