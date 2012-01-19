<html>
	<head>
		<@ww.head/>
		<#if tipoDespesa.id?exists>
			<title>Editar Tipo de Despesa</title>
			<#assign formAction="update.action"/>
		<#else>
			<title>Inserir Tipo de Despesa</title>
			<#assign formAction="insert.action"/>
		</#if>
	
	<#assign validarCampos="return validaFormulario('form', new Array('descricao'))"/>
	</head>
	<body>
		<@ww.actionerror />
		<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
			<@ww.hidden name="tipoDespesa.id" />
			<@ww.hidden name="tipoDespesa.empresa.id" />
			<@ww.textfield label="Descrição" name="tipoDespesa.descricao" id="descricao" cssStyle="width: 282px;" maxLength="40" required="true"/>
			<@ww.token/>
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnGravar"></button>
			<button onclick="window.location='list.action'" class="btnVoltar"></button>
		</div>
	</body>
</html>
