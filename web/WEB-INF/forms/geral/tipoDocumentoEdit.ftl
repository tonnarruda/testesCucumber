<html>
	<head>
		<@ww.head/>
		<#if tipoDocumento.id?exists>
			<title>Editar Tipo do Documento</title>
			<#assign formAction="update.action"/>
		<#else>
			<title>Inserir Tipo do Documento</title>
			<#assign formAction="insert.action"/>
		</#if>
	
	<#assign validarCampos="return validaFormulario('form', new Array('descricao'))"/>
	</head>
	<body>
		<@ww.actionerror />
		<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
			<@ww.textfield label="Descrição" name="tipoDocumento.descricao" id="descricao" cssStyle="width: 282px;" maxLength="40" required="true"/>
			
			<@ww.hidden name="tipoDocumento.id" />
			<@ww.token/>
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnGravar"></button>
			<button onclick="window.location='list.action'" class="btnVoltar"></button>
		</div>
	</body>
</html>
