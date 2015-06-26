<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
	<@ww.head/>

	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>
		<#include "../ftl/mascarasImports.ftl" />

	<#if colaboradorOcorrencia.id?exists>
		<title>Editar Ocorrência do Colaborador</title>
		<#assign formAction="update.action"/>
		<#assign accessKey="A"/>
	<#else>
		<title>Inserir Ocorrência do Colaborador</title>
		<#assign formAction="insert.action"/>
		<#assign accessKey="I"/>
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
	<@ww.actionmessage />
	<@ww.form name="form" action="${formAction}" method="POST">

	<div style="font-weight:bold;">Colaborador: ${colaborador.nome}</div>
	<br>
		<@ww.select label="Ocorrência" name="colaboradorOcorrencia.ocorrencia.id" id="ocorrencia"  required="true" list="ocorrencias" listKey="id" listValue="descricao" headerValue="" headerKey="" liClass="liLeft"/>
		<@ww.datepicker label="Data de Início" value="${dataIni}" name="colaboradorOcorrencia.dataIni" id="dataIni"  required="true" liClass="liLeft" cssClass="mascaraData" />
		<@ww.datepicker label="Data de Término" value="${dataFim}" name="colaboradorOcorrencia.dataFim" id="dataFim" cssClass="mascaraData"/>
	
		<@authz.authorize ifAllGranted="ROLE_MOV_PROVIDENCIA">
			<@ww.select label="Providência" name="colaboradorOcorrencia.providencia.id" id="providencia" list="providencias" cssStyle="width:530px;" listKey="id" listValue="descricao" headerValue="Selecione..." headerKey=""/>
		</@authz.authorize>
		<@authz.authorize ifNotGranted="ROLE_MOV_PROVIDENCIA">
			<@ww.hidden name="colaboradorOcorrencia.providencia.id" />
		</@authz.authorize>
		
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