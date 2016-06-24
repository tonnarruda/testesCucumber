<html>
	<head>
		<@ww.head/>
		<#if fase.id?exists>
			<title>Editar Fase</title>
			<#assign formAction="update.action"/>
		<#else>
			<title>Inserir Fase</title>
			<#assign formAction="insert.action"/>
		</#if>
	
	<#assign validarCampos="return validaFormulario('form', new Array('descricao'))"/>
	</head>
	<body>
		<@ww.actionerror />
		<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
			<@ww.hidden name="fase.id" />
			<@ww.hidden name="fase.empresa.id" />
			<@ww.token/>
			
			<@ww.textfield label="Descrição" name="fase.descricao" id="descricao" required="true" cssClass="inputNome" maxLength="200" cssStyle="width:606px;"/>
			
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnGravar"></button>
			<button onclick="javascript: executeLink('list.action');" class="btnVoltar"></button>
		</div>
	</body>
</html>
