<html>
	<head>
		<@ww.head/>
		<#if comoFicouSabendoVaga.id?exists>
			<title>Editar ComoFicouSabendoVaga</title>
			<#assign formAction="update.action"/>
		<#else>
			<title>Inserir ComoFicouSabendoVaga</title>
			<#assign formAction="insert.action"/>
		</#if>
	
	<#assign validarCampos="return validaFormulario('form', new Array('nome'))"/>
	</head>
	<body>
		<@ww.actionerror />
		<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
			<@ww.textfield label="Nome" name="comoFicouSabendoVaga.nome" id="nome" required="true" cssClass="inputNome" maxLength="250" cssStyle="width: 500px;"/>
			<@ww.hidden name="comoFicouSabendoVaga.id" />
			<@ww.token/>
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnGravar"></button>
			<button onclick="window.location='list.action'" class="btnVoltar"></button>
		</div>
	</body>
</html>
