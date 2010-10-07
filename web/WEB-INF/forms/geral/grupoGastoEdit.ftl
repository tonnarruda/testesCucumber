<html>
<head>
<@ww.head/>
<#if grupoGasto.id?exists>
	<title>Editar Grupo de Investimentos</title>
	<#assign formAction="update.action"/>
	<#assign accessKey="A"/>
<#else>
	<title>Inserir Grupo de Investimentos</title>
	<#assign formAction="insert.action"/>
	<#assign accessKey="I"/>
</#if>

<#assign validarCampos="return validaFormulario('form', new Array('nome'), null)"/>
</head>
<body>
	<@ww.actionerror />
	<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST">
		<@ww.textfield required="true" label="Nome do Grupo" name="grupoGasto.nome" id="nome" cssClass="inputNome" maxLength="100"/>
		<@ww.hidden name="grupoGasto.id" />

	<@ww.token/>
	</@ww.form>


	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnGravar" accesskey="${accessKey}">
		</button>
		<button onclick="window.location='list.action'" class="btnCancelar" accesskey="V">
		</button>
	</div>
</body>
</html>