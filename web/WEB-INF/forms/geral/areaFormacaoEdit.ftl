<html>
<head>
<@ww.head/>
<#if areaFormacao.id?exists>
	<title>Editar Área de Formação</title>
	<#assign formAction="update.action"/>
	<#assign buttonLabel="<u>A</u>tualizar"/>
	<#assign accessKey="A"/>
<#else>
	<title>Inserir Área de Formação</title>
	<#assign formAction="insert.action"/>
	<#assign buttonLabel="<u>I</u>nserir"/>
	<#assign accessKey="I"/>
</#if>
<#assign validarCampos="return validaFormulario('form', new Array('nome'), null)"/>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST">
		<@ww.textfield label="Nome" name="areaFormacao.nome" id="nome" required="true" cssClass="inputNome" maxLength="250" cssStyle="width: 500px;"/>
		<@ww.hidden label="Id" name="areaFormacao.id" />
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