<html>
	<head>
		<@ww.head/>
		<#if grupoAC.id?exists>
			<title>Editar Grupo AC</title>
			<#assign formAction="update.action"/>
		<#else>
			<title>Inserir Grupo AC</title>
			<#assign formAction="insert.action"/>
		</#if>
	
	<#assign validarCampos="return validaFormulario('form', new Array('descricao', 'codigo'))"/>
	</head>
	<body>
		<@ww.actionerror />
		<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
		
			<@ww.textfield label="Descrição" name="grupoAC.descricao" id="descricao" required="true"  cssStyle="width: 150px;" maxLength="20"/>
			<@ww.textfield label="Código" name="grupoAC.codigo" id="codigo" required="true" cssStyle="width: 25px;" maxLength="3"/>
			
			<@ww.hidden name="grupoAC.id" />
			<@ww.token/>
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnGravar"></button>
			<button onclick="window.location='list.action'" class="btnVoltar"></button>
		</div>
	</body>
</html>
