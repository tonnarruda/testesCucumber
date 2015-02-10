<html>
<head>
<@ww.head/>
<#if motivoSolicitacao.id?exists>
	<title>Editar Motivo de Solicitação</title>
	<#assign formAction="update.action"/>
	<#assign accessKey="A"/>
<#else>
	<title>Inserir Motivo de Solicitação</title>
	<#assign formAction="insert.action"/>
	<#assign accessKey="I"/>
</#if>

<#assign validarCampos="return validaFormulario('form', new Array('descricao'), null)"/>
</head>
<body>
	<@ww.actionerror />
	<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST">
		<@ww.textfield required="true" label="Descrição" id="descricao" name="motivoSolicitacao.descricao" cssClass="inputNome" maxLength="100"/>
		<@ww.checkbox label="Considerar para cálculo de turnover" name="motivoSolicitacao.turnover" labelPosition="left"/>
		
		<@ww.hidden label="Id" name="id" />
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos};"  class="btnGravar" accesskey="${accessKey}"></button>
		<button onclick="window.location='list.action'" class="btnCancelar" accesskey="V"></button>
	</div>
</body>
</html>