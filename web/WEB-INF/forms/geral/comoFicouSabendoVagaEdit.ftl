<html>
	<head>
		<@ww.head/>
		<#if comoFicouSabendoVaga.id?exists>
			<title>Editar Como Ficou Sabendo da Vaga</title>
			<#assign formAction="update.action"/>
		<#else>
			<title>Inserir Como Ficou Sabendo da Vaga</title>
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
			<button onclick="window.location='list.action'" class="btnCancelar"></button>
		</div>
	</body>
</html>
