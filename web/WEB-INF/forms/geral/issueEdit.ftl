<html>
	<head>
		<@ww.head/>
		<#if issue.number?exists>
			<title>Editar Issue</title>
			<#assign formAction="update.action"/>
		<#else>
			<title>Inserir Issue</title>
			<#assign formAction="insert.action"/>
		</#if>
	
	<#assign validarCampos="return validaFormulario('form', new Array('titulo'))"/>
	</head>
	<body>
		<@ww.actionerror />
		<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
			<@ww.hidden name="issue.number" />
			
			<@ww.textfield label="Título" name="issue.title" id="titulo" cssStyle="width: 600px" maxLength="200" required="true"/>
			<@ww.textarea label="Descrição" name="issue.body" id="body" cssStyle="width: 600px"/>
			
			<@ww.token/>
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnGravar"></button>
			<button onclick="window.location='list.action'" class="btnVoltar"></button>
		</div>
	</body>
</html>
