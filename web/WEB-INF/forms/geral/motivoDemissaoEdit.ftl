<html>
<head>
<@ww.head/>
	<#if motivoDemissao.id?exists>
		<title>Editar Motivo de Desligamento</title>
		<#assign formAction="update.action"/>
		<#assign accessKey="A"/>
	<#else>
		<title>Inserir Motivo de Desligamento</title>
		<#assign formAction="insert.action"/>
		<#assign accessKey="I"/>
	</#if>
	<#assign validarCampos="return validaFormulario('form', new Array('motivo'), null)"/>
</head>
<body>
<@ww.actionerror />
	<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST">
		<@ww.textfield label="Motivo" name="motivoDemissao.motivo" id="motivo" size="50" required="true"/>
		<@ww.hidden name="motivoDemissao.id" />
	</@ww.form>


	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnGravar" accesskey="${accessKey}">
		</button>
		<button onclick="window.location='list.action'" class="btnCancelar" accesskey="V">
		</button>
	</div>
</body>
</html>