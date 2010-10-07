<html>
<head>
<@ww.head/>
<#if tipoEPI.id?exists>
	<title>Editar Tipo de EPI</title>
	<#assign formAction="update.action"/>
	<#assign accessKey="A"/>
<#else>
	<title>Inserir Tipo de EPI</title>
	<#assign formAction="insert.action"/>
	<#assign accessKey="I"/>
</#if>

<#assign validarCampos="return validaFormulario('form', new Array('nome'), null)"/>

</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	<@ww.form name="form" action="${formAction}" validate="true"  onsubmit="${validarCampos}" method="POST">
		<@ww.textfield label="Nome" name="tipoEPI.nome" id="nome" cssClass="inputNome" maxLength="100"  required="true" />
		<@ww.hidden label="Id" name="tipoEPI.id" />
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