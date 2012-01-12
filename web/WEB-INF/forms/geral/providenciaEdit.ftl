<html>
	<head>
		<@ww.head/>
		<#if providencia.id?exists>
			<title>Editar Providência</title>
			<#assign formAction="update.action"/>
		<#else>
			<title>Inserir Providência</title>
			<#assign formAction="insert.action"/>
		</#if>
	
	<#assign validarCampos="return validaFormulario('form', new Array('descricao'))"/>
	</head>
	<body>
		<@ww.actionerror />
		<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
			<@ww.hidden name="providencia.id" />
			<@ww.hidden name="providencia.empresa.id" />
			<@ww.textfield label="Descrição" name="providencia.descricao" id="descricao" cssStyle="width: 500px;" maxLength="100" required="true"/>
			<@ww.token/>
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnGravar"></button>
			<button onclick="window.location='list.action'" class="btnVoltar"></button>
		</div>
	</body>
</html>
