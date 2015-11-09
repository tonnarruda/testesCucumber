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
		<@ww.hidden name="motivoDemissao.id" />
		<@ww.textfield label="Motivo" name="motivoDemissao.motivo" id="motivo" size="50" maxLength="50" required="true"/>
		
		<#if exibeFlagTurnover>
			<@ww.checkbox label="Considerar para cálculo de turnover" name="motivoDemissao.turnover" labelPosition="left"/>
		</#if>
		<@ww.checkbox label="Redução de Quadro" name="motivoDemissao.reducaoDeQuadro" labelPosition="left"/>
		
	</@ww.form>


	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnGravar" accesskey="${accessKey}">
		</button>
		<button onclick="window.location='list.action'" class="btnCancelar" accesskey="V">
		</button>
	</div>
</body>
</html>