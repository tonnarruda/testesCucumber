<html>
<head>
<@ww.head/>
<#if dnt.id?exists>
	<title>Editar DNT</title>
	<#assign formAction="update.action"/>
	<#assign accessKey="A"/>
<#else>
	<title>Inserir DNT</title>
	<#assign formAction="insert.action"/>
	<#assign accessKey="I"/>
</#if>
<#if dnt?exists && dnt.data?exists>
	<#assign data = dnt.data?date/>
<#else>
	<#assign data = ""/>
</#if>
<#include "../ftl/mascarasImports.ftl" />
<#assign validarCampos="return validaFormulario('form', new Array('nome','data'), new Array('data'))"/>
</head>
<body>
	<@ww.actionerror />
	<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST">
		<@ww.textfield label="Nome" id="nome" name="dnt.nome" cssClass="inputNome" maxLength="100" required="true"/>
		<@ww.datepicker label="Data" id="data" name="dnt.data" required="true" cssClass="mascaraData" value="${data}"/>
		<@ww.hidden name="dnt.id" />
		<@ww.token/>
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnGravar" accesskey="${accessKey}"></button>
		<button onclick="window.location='list.action'" class="btnCancelar" accesskey="V"></button>
	</div>
</body>
</html>