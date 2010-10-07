<html>
	<head>
		<@ww.head/>
		<#if afastamento.id?exists>
			<title>Editar Tipo de Afastamento</title>
			<#assign formAction="update.action"/>
		<#else>
			<title>Inserir Tipo de Afastamento</title>
			<#assign formAction="insert.action"/>
		</#if>
	
	<#assign validarCampos="return validaFormulario('form', new Array('descricao'))"/>
	</head>
	<body>
		<@ww.actionerror />
		<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
			<@ww.textfield label="Descrição" name="afastamento.descricao" id="descricao" cssClass="inputNome" maxLength="100" required="true"/>
			<@ww.checkbox label="" name="afastamento.inss" theme="simple"/>Afastamento pelo INSS
			<@ww.hidden name="afastamento.id" />
			<@ww.token/>
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnGravar"></button>
			<button onclick="window.location='list.action'" class="btnVoltar"></button>
		</div>
	</body>
</html>