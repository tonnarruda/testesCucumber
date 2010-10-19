<html>
<head>
<@ww.head/>
<#if tipoEPI.id?exists>
	<title>Editar Categoria de EPI</title>
	<#assign formAction="update.action"/>
<#else>
	<title>Inserir Categoria de EPI</title>
	<#assign formAction="insert.action"/>
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
		<button onclick="${validarCampos};" class="btnGravar"></button>
		<button onclick="window.location='list.action'" class="btnCancelar"></button>
	</div>
</body>
</html>