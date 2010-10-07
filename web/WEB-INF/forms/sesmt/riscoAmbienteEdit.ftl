<html>
	<head>
		<@ww.head/>
		<#if riscoAmbiente.id?exists>
			<title>Editar RiscoAmbiente</title>
			<#assign formAction="update.action"/>
		<#else>
			<title>Inserir RiscoAmbiente</title>
			<#assign formAction="insert.action"/>
		</#if>
	
	<#assign validarCampos="return validaFormulario('form', new Array())"/>
	</head>
	<body>
		<@ww.actionerror />
		<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
			<@ww.hidden name="riscoAmbiente.id" />
			<@ww.token/>
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnGravar"></button>
			<button onclick="window.location='list.action'" class="btnVoltar"></button>
		</div>
	</body>
</html>
