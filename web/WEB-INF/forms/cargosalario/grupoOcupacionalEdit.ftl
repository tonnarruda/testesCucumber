<html>
<head>
<@ww.head/>
<#if grupoOcupacional.id?exists>
	<title>Editar Grupo Ocupacional</title>
	<#assign formAction="update.action"/>
	<#assign accessKey="A"/>
<#else>
	<title>Inserir Grupo Ocupacional</title>
	<#assign formAction="insert.action"/>
	<#assign accessKey="I"/>
</#if>

<#assign validarCampos="return validaFormulario('form', new Array('nome'), null)"/>
</head>
<body>
	<@ww.actionerror />
	<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST">
		<@ww.textfield label="Nome" required="true" name="grupoOcupacional.nome" id="nome" cssClass="inputNome" maxLength="100"/>
		<@ww.hidden name="grupoOcupacional.id" />
		<@ww.hidden name="grupoOcupacional.empresa.id" />

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