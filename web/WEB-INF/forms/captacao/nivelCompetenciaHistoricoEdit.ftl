<html>
	<head>
		<@ww.head/>
		<#if nivelCompetenciaHistorico.id?exists>
			<title>Editar NivelCompetenciaHistorico</title>
			<#assign formAction="update.action"/>
		<#else>
			<title>Inserir NivelCompetenciaHistorico</title>
			<#assign formAction="insert.action"/>
		</#if>
	
	<#assign validarCampos="return validaFormulario('form', new Array())"/>
	</head>
	<body>
		<@ww.actionerror />
		<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
			<@ww.hidden name="nivelCompetenciaHistorico.id" />
			<@ww.token/>
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnGravar"></button>
			<button onclick="window.location='list.action'" class="btnVoltar"></button>
		</div>
	</body>
</html>
