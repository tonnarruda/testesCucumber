<html>
	<head>
		<@ww.head/>
		<#if tamanhoEPI.id?exists>
			<title>Editar Tamanho de EPI/Fardamento</title>
			<#assign formAction="update.action"/>
		<#else>
			<title>Inserir Tamanho de EPI/Fardamento</title>
			<#assign formAction="insert.action"/>
		</#if>
	
	<#assign validarCampos="return validaFormulario('form', new Array('descricao'), null)"/>	</head>
	<body>
		<@ww.actionerror />
		<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
			<@ww.hidden name="tamanhoEPI.id" />
			<@ww.token/>

			<@ww.textfield required="true" label="Descrição" id="descricao" name="tamanhoEPI.descricao" cssClass="inputNome" maxLength="30"/>
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnGravar"></button>
			<button onclick="javascript: executeLink('list.action');" class="btnVoltar"></button>
		</div>
	</body>
</html>
