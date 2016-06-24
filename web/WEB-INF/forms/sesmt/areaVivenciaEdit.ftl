<html>
	<head>
		<@ww.head/>
		<#if areaVivencia.id?exists>
			<title>Editar Área de Vivência</title>
			<#assign formAction="update.action"/>
		<#else>
			<title>Inserir Área de Vivência</title>
			<#assign formAction="insert.action"/>
		</#if>
	
	<#assign validarCampos="return validaFormulario('form', new Array('nome'))"/>
	</head>
	<body>
		<@ww.actionerror />
		<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
			<@ww.hidden name="areaVivencia.id" />
			<@ww.hidden name="areaVivencia.empresa.id" />
			<@ww.token/>
			
			<@ww.textfield label="Nome" name="areaVivencia.nome" id="nome" required="true" cssClass="inputNome" maxLength="150" cssStyle="width:500px;"/>
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnGravar"></button>
			<button onclick="javascript: executeLink('list.action');" class="btnVoltar"></button>
		</div>
	</body>
</html>
