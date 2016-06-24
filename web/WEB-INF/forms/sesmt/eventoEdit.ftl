<html>
	<head>
		<@ww.head/>
		<#if evento.id?exists>
			<title>Editar Evento</title>
			<#assign formAction="update.action"/>
		<#else>
			<title>Inserir Evento</title>
			<#assign formAction="insert.action"/>
		</#if>
	
	<#assign validarCampos="return validaFormulario('form', new Array('nome'))"/>
	</head>
	<body>
		<@ww.actionerror />
		<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
			<@ww.textfield label="Nome" id="nome" name="evento.nome" cssClass="inputNome" maxLength="100" required="true"/>
			<@ww.hidden name="evento.id" />
			<@ww.token/>
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnGravar"></button>
			<button onclick="javascript: executeLink('list.action');" class="btnVoltar"></button>
		</div>
	</body>
</html>
