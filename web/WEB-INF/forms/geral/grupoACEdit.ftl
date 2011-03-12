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
			
			<@ww.textfield label="Usuário AC" name="grupoAC.acUsuario" id="acUsuario" cssClass="inputNome" maxLength="50"/>
			<@ww.password label="Senha AC" name="grupoAC.acSenha" id="acSenha" cssStyle="width:100px;" maxLength="15" after="*Para manter a senha, deixe o campo em branco."/>
			<@ww.textfield label="URL WS" name="grupoAC.acUrlSoap" id="acUrlSoap" cssClass="inputNome" maxLength="100"/>
			<@ww.textfield label="URL WSDL" name="grupoAC.acUrlWsdl" id="acUrlWdsl" cssClass="inputNome" maxLength="100"/>
			
			<@ww.hidden name="grupoAC.id" />
			<@ww.token/>
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnGravar"></button>
			<button onclick="window.location='list.action'" class="btnVoltar"></button>
		</div>
	</body>
</html>
