<html>
<head>
	<@ww.head/>

	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
	</style>
		<#include "../ftl/mascarasImports.ftl" />

	<#if colaboradorOcorrencia.id?exists>
		<title>Editar Ocorrência do Colaborador</title>
		<#assign formAction="update.action"/>
		<#assign accessKey="A"/>
		<#assign somenteLeitura = true />
	<#else>
		<title>Inserir Ocorrência do Colaborador</title>
		<#assign formAction="insert.action"/>
		<#assign accessKey="I"/>
		<#assign somenteLeitura = false />
	</#if>
	<#assign validarCampos="return validaFormulario('form', new Array('ocorrencia','dataIni'), new Array('dataIni','dataFim'))"/>

	<#assign dataIni = ""/>
	<#if colaboradorOcorrencia.dataIni?exists>
		<#assign dataIni = colaboradorOcorrencia.dataIni?date/>
	</#if>
	<#assign dataFim = ""/>
	<#if colaboradorOcorrencia.dataFim?exists>
		<#assign dataFim = colaboradorOcorrencia.dataFim?date/>
	</#if>

</head>
<body>
	<@ww.actionerror />
	<@ww.form name="form" action="${formAction}" method="POST">

	<div style="font-weight:bold;">Colaborador: ${colaborador.nome}</div>
	<br>
		<#if somenteLeitura>
			<@ww.select label="Ocorrência" disabled="true" name="colaboradorOcorrencia.ocorrencia.id" id="ocorrencia"  required="true" list="ocorrencias" listKey="id" listValue="descricao" headerValue="" headerKey="" liClass="liLeft"/>
			<@ww.textfield label="Data de Início" disabled= "true" id="dataIni" required="true" name="colaboradorOcorrencia.dataIni" value="${dataIni}" cssStyle="width:71px;" liClass="liLeft"/>

			<@ww.hidden name="colaboradorOcorrencia.ocorrencia.id" />
			<@ww.hidden name="colaboradorOcorrencia.dataIni" />
		<#else>
			<@ww.select label="Ocorrência" name="colaboradorOcorrencia.ocorrencia.id" id="ocorrencia"  required="true" list="ocorrencias" listKey="id" listValue="descricao" headerValue="" headerKey="" liClass="liLeft"/>
			<@ww.datepicker label="Data de Início" value="${dataIni}" name="colaboradorOcorrencia.dataIni" id="dataIni"  required="true" liClass="liLeft" cssClass="mascaraData" />
		</#if>
			<@ww.datepicker label="Data de Término" value="${dataFim}" name="colaboradorOcorrencia.dataFim" id="dataFim" cssClass="mascaraData"/>

		<@ww.textarea label="Observações" name="colaboradorOcorrencia.observacao" />

		<@ww.hidden name="colaboradorOcorrencia.id" />
		<@ww.hidden name="colaborador.id" />
		<@ww.hidden name="colaborador.codigoAC" />
		<@ww.token/>
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos};"  class="btnGravar" accesskey="${accessKey}"> </button>
		<button onclick="window.location='list.action?colaborador.id=${colaborador.id}'" class="btnCancelar" accesskey="V"></button>
	</div>

</body>
</html>