<html>
	<head>
		<@ww.head/>
		<#if fasePcmat.id?exists>
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
			<@ww.hidden name="fasePcmat.id" />
			<@ww.hidden name="fasePcmat.empresa.id" />
			<@ww.token/>
			
			<@ww.textfield label="Descrição" name="fasePcmat.descricao" id="descricao" required="true" cssClass="inputNome" maxLength="200" cssStyle="width:606px;"/>
			
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnGravar"></button>
			<button onclick="window.location='list.action'" class="btnVoltar"></button>
		</div>
	</body>
</html>
