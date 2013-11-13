<html>
	<head>
		<#include "../ftl/mascarasImports.ftl" />
		<@ww.head/>
		<#if pcmat.id?exists>
			<title>Editar PCMAT</title>
			<#assign formAction="update.action"/>
		<#else>
			<title>Inserir PCMAT</title>
			<#assign formAction="insert.action"/>
		</#if>
	
	<#assign validarCampos="return validaFormulario('form', new Array())"/>
	</head>
	<body>
		<@ww.actionerror />
		<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
			<@ww.hidden name="pcmat.id" />
			<@ww.token/>

			<@ww.datepicker label="Data do PCMAT" name="pcmat.apartirDe" id="apartirDe" required="true" cssClass="mascaraData validaDataIni"/>
			<@ww.datepicker label="Data do início da obra" name="pcmat.dataIniObra" id="dataIniObra" required="true" cssClass="mascaraData validaDataIni"/>
			<@ww.datepicker label="Data do Fim da obra" name="pcmat.dataFimObra" id="dataFimObra" required="true" cssClass="mascaraData validaDataIni"/>
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnGravar"></button>
			<button onclick="window.location='list.action'" class="btnVoltar"></button>
		</div>
	</body>
</html>
