<html>
	<head>
		<@ww.head/>
		<#if medidaSeguranca.id?exists>
			<title>Editar Medida de Segurança</title>
			<#assign formAction="update.action"/>
		<#else>
			<title>Inserir Medida de Segurança</title>
			<#assign formAction="insert.action"/>
		</#if>
	
	<#assign validarCampos="return validaFormulario('form', new Array('descricao'))"/>
	</head>
	<body>
		<@ww.actionerror />
		<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
			<@ww.hidden name="medidaSeguranca.id" />
			<@ww.hidden name="medidaSeguranca.empresa.id" />
			<@ww.token/>
			
			<@ww.textarea label="Descrição" id="descricao" name="medidaSeguranca.descricao"/>
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnGravar"></button>
			<button onclick="javascript: executeLink('list.action');" class="btnVoltar"></button>
		</div>
	</body>
</html>
