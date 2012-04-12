<html>
	<head>
		<@ww.head/>
		<#if issue.id?exists>
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
			<@ww.hidden name="issue.id" />
			
			<@ww.textfield label="Título" name="issue.title" id="titulo" maxLength="200" required="true"/>
			<@ww.textarea label="Descrição" name="issue.body" cssStyle="width: 500px"/>
			
			<@ww.token/>
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnGravar"></button>
			<button onclick="window.location='list.action'" class="btnVoltar"></button>
		</div>
	</body>
</html>
