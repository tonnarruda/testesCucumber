<html>
	<head>
		<@ww.head/>
		<#if obra.id?exists>
			<title>Editar Obra</title>
			<#assign formAction="update.action"/>
		<#else>
			<title>Inserir Obra</title>
			<#assign formAction="insert.action"/>
		</#if>
	
		<#assign validarCampos="return validaFormulario('form', new Array('nome','endereco'))"/>
	</head>
	<body>
		<@ww.actionerror />
		<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
			<@ww.hidden name="obra.id" />
			<@ww.hidden name="obra.empresa.id" />
			<@ww.token/>
			
			<@ww.textfield label="Nome" name="obra.nome" id="nome" required="true" cssClass="inputNome" maxLength="100" cssStyle="width:500px;"/>
			<@ww.textfield label="Endereço" name="obra.endereco" id="endereco" required="true" cssClass="inputNome" maxLength="200" cssStyle="width:500px;"/>
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnGravar"></button>
			<button onclick="window.location='list.action'" class="btnVoltar"></button>
		</div>
	</body>
</html>
