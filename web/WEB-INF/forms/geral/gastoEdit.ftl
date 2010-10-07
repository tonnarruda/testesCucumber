<html>
<head>
<@ww.head/>
<#if gasto.id?exists>
	<title>Editar Investimentos da Empresa</title>
	<#assign formAction="update.action"/>
	<#assign accessKey="A"/>
<#else>
	<title>Inserir Investimentos da Empresa</title>
	<#assign formAction="insert.action"/>
	<#assign accessKey="I"/>
</#if>

<#assign validarCampos="return validaFormulario('form', new Array('nome','grupo'), null)"/>
</head>
<body>
<@ww.actionerror />
<@ww.actionmessage />
<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST">
	<@ww.textfield required="true" label="Nome" name="gasto.nome" id="nome" cssClass="inputNome" maxLength="100"/>
	<@ww.select required="true" label="Grupo de Investimentos" name="gasto.grupoGasto.id" id="grupo" list="grupoGastos" listKey="id" listValue="nome" headerValue="[Selecione um Grupo]" headerKey=""/>
	<@ww.checkbox label="Eventual (Não Importável)" name="gasto.naoImportar" labelPosition="left"/>
	<@ww.hidden name="gasto.id" />
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