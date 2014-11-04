<html>
	<head>
		<@ww.head/>
		<#if motivoSolicitacaoEpi.id?exists>
			<title>Editar Motivo da Solicitacao do EPI</title>
			<#assign formAction="update.action"/>
		<#else>
			<title>Inserir Motivo da Solicitacao do EPI</title>
			<#assign formAction="insert.action"/>
		</#if>
	
	<#assign validarCampos="return validaFormulario('form', new Array('descricao'), null)"/>	</head>
	<body>
		<@ww.actionerror />
		<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
			<@ww.hidden name="motivoSolicitacaoEpi.id" />
			<@ww.token/>

			<@ww.textfield required="true" label="Descrição" id="descricao" name="motivoSolicitacaoEpi.descricao" cssClass="inputNome" maxLength="100"/>
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnGravar"></button>
			<button onclick="window.location='list.action'" class="btnVoltar"></button>
		</div>
	</body>
</html>
