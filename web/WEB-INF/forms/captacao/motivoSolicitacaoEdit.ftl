<html>
<head>
<@ww.head/>
<#if motivoSolicitacao.id?exists>
	<title>Editar Motivo de Solicitação de Pessoal</title>
	<#assign formAction="update.action"/>
	<#assign accessKey="A"/>
<#else>
	<title>Inserir Motivo de Solicitação de Pessoal</title>
	<#assign formAction="insert.action"/>
	<#assign accessKey="I"/>
</#if>

<#assign validarCampos="return validaFormulario('form', new Array('descricao'), null)"/>
</head>
<body>
	<@ww.actionerror />
	<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST">
		<@ww.textfield required="true" label="Descrição" id="descricao" name="motivoSolicitacao.descricao" cssClass="inputNome" maxLength="100"/>
		
		<#if exibeFlagTurnover>
			<@ww.checkbox label="Considerar para cálculo de turnover" name="motivoSolicitacao.turnover" labelPosition="left"/>
		</#if>
		
		<#if existeConfigQtdColaboradoresPorCargo>
			<@ww.checkbox label="Alertar quantidade limite de colaboradores por cargo ao criar solicitação de pessoal." name="motivoSolicitacao.considerarQtdColaboradoresPorCargo" labelPosition="left"/>
		<#else>
			<@ww.hidden name="motivoSolicitacao.considerarQtdColaboradoresPorCargo"/>	
		</#if>
		
		<@ww.hidden label="Id" name="id" />
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos};"  class="btnGravar" accesskey="${accessKey}"></button>
		<button onclick="window.location='list.action'" class="btnCancelar" accesskey="V"></button>
	</div>
</body>
</html>